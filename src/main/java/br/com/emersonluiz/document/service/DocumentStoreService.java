package br.com.emersonluiz.document.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.com.emersonluiz.document.dto.FileDescription;
import br.com.emersonluiz.document.exception.NotFoundException;

import com.mongodb.gridfs.GridFSDBFile;

public interface DocumentStoreService {

    Object create(String metadata, MultipartFile file);

    GridFSDBFile findOne(String id) throws NotFoundException;

    void delete(String id) throws NotFoundException;

    List<FileDescription> findByMetadata(String field, String value);

    List<FileDescription> findAll();
}
