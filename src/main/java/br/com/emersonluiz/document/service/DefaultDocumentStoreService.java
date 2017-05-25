package br.com.emersonluiz.document.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.multipart.MultipartFile;

import br.com.emersonluiz.document.dto.FileDescription;
import br.com.emersonluiz.document.exception.NotFoundException;
import br.com.emersonluiz.document.util.JsonUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

@Named
public class DefaultDocumentStoreService implements DocumentStoreService {

    @Value("${query.max.results}")
    private int maxResults;

    @Inject
    GridFsTemplate gridFsTemplate;

    @Override
    public Object create(String metadata, MultipartFile file) {

        try {
            Map<String, String> map = JsonUtil.jsonToString(metadata);
            DBObject data = new BasicDBObject();

            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    if (!entry.getKey().isEmpty()
                            && !entry.getValue().isEmpty()) {
                        data.put(entry.getKey(), entry.getValue());
                    }
                }
            }

            Object id = gridFsTemplate.store(file.getInputStream(),
                    file.getOriginalFilename(), file.getContentType(), data)
                    .getId();
            return id;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public GridFSDBFile findOne(String id) throws NotFoundException {
        GridFSDBFile file = gridFsTemplate.findOne(new Query(Criteria.where(
                "_id").is(id)));
        if (file == null) {
            throw new NotFoundException("File was not found");
        }
        return file;
    }

    @Override
    public void delete(String id) throws NotFoundException {
        findOne(id);
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
    }

    @Override
    public List<FileDescription> findByMetadata(String field, String value) {
        List<GridFSDBFile> files = gridFsTemplate.find(new Query(Criteria
                .where("metadata." + field).is(value)));
        return extractData(files);
    }

    @Override
    public List<FileDescription> findAll() {
        List<GridFSDBFile> files = gridFsTemplate.find(new Query()
                .limit(maxResults));
        return extractData(files);
    }

    private List<FileDescription> extractData(List<GridFSDBFile> files) {
        List<FileDescription> list = new ArrayList<FileDescription>();
        for (GridFSDBFile file : files) {
            FileDescription fd = new FileDescription(file.getId().toString(),
                    file.getFilename(), file.getContentType(), file.getLength());

            if (file.getMetaData() != null) {
                Set<String> keys = file.getMetaData().keySet();
                Map<String, String> map = new HashMap<String, String>();
                for (String key : keys) {
                    map.put(key, file.getMetaData().get(key).toString());
                }

                fd.setMetaData(map);
            }

            list.add(fd);
        }
        return list;
    }
}
