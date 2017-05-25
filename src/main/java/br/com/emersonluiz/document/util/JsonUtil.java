package br.com.emersonluiz.document.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static Map<String, String> jsonToString(String jsonString)
            throws JsonParseException, JsonMappingException, IOException {

        Map<String, String> map = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();

        map = mapper.readValue(jsonString, new TypeReference<HashMap<String, String>>() {
        });

        return map;
    }

}
