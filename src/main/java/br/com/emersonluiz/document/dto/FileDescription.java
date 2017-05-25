package br.com.emersonluiz.document.dto;

import java.io.Serializable;
import java.util.Map;

public class FileDescription implements Serializable {

    private static final long serialVersionUID = -3538811232569449069L;

    private String id;

    private String fileName;

    private String contentType;

    private long size;

    private Map<String, String> metaData;

    public FileDescription() {
    }

    public FileDescription(String id, String fileName, String contentType,
            long size) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
    }

    public FileDescription(String id, String fileName, String contentType,
            long size, Map<String, String> metaData) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
        this.metaData = metaData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }
}
