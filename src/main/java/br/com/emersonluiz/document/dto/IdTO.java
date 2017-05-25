package br.com.emersonluiz.document.dto;

import java.io.Serializable;

public class IdTO implements Serializable {

    private static final long serialVersionUID = -4002405148190102481L;

    private String id;

    public IdTO() {
    }

    public IdTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
