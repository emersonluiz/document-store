package br.com.emersonluiz.document.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;

import br.com.emersonluiz.document.dto.FileDescription;
import br.com.emersonluiz.document.dto.IdTO;
import br.com.emersonluiz.document.rest.ExceptionResource;
import br.com.emersonluiz.document.service.DocumentStoreService;

@RestController
@RequestMapping("/documents")
public class DocumentStoreResource extends ExceptionResource {

    @Inject
    DocumentStoreService documentStore;

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = { "multipart/form-data" }, produces = { "application/json" })
    public IdTO create(
            @RequestPart(value = "metadata", required = false) String metadata,
            @RequestPart("file") MultipartFile file) {
        Object id = documentStore.create(metadata, file);
        return new IdTO(id.toString());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> findOne(
            @PathVariable("id") String id) throws Exception {
        GridFSDBFile file = documentStore.findOne(id);
        return ResponseEntity.ok().contentLength(file.getLength())
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(new InputStreamResource(file.getInputStream()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) throws Exception {
        documentStore.delete(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/metadata/{field}/{value}", method = RequestMethod.GET)
    public List<FileDescription> findByMetadata(
            @PathVariable("field") String field,
            @PathVariable("value") String value) {
        List<FileDescription> list = documentStore.findByMetadata(field, value);
        return list;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<FileDescription> findAll() {
        List<FileDescription> list = documentStore.findAll();
        return list;
    }

}