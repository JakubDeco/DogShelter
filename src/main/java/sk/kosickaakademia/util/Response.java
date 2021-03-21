package sk.kosickaakademia.util;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class Response {

    public static ResponseEntity<String> status200(String jsonStr){
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(jsonStr);
    }

    public static ResponseEntity<String> status400(String message){
        JSONObject response = new JSONObject();
        response.put("error", message);
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(response.toJSONString());
    }

    public static ResponseEntity<String> status404(String message){
        JSONObject response = new JSONObject();
        response.put("error", message);
        return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(response.toJSONString());
    }

    public static ResponseEntity<String> status500(String message){
        JSONObject response = new JSONObject();
        response.put("error", message);
        return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(response.toJSONString());
    }
}
