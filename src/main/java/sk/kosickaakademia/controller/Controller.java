package sk.kosickaakademia.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.kosickaakademia.database.Database;
import sk.kosickaakademia.entity.Dog;
import sk.kosickaakademia.util.Response;
import sk.kosickaakademia.util.Util;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class Controller {

    @GetMapping(path = "/dogs")
    public ResponseEntity<String> getAllDogs(){
        Database database = new Database();
        List<Dog> list = database.getAllDogs();
        String response = new Util().getJson(list);

        return Response.status200(response);
    }

    @PutMapping(path = "/dog/name")
    public ResponseEntity<String> changeDogName(@PathParam("id") int id, @PathParam("name") String name){
        if (id < 1 || name == null || name.isBlank()){
            return Response.status400("Bad request.");
        }

        Database database = new Database();
        if (database.changeNameByID(id, Util.normalizeName(name))){
            return Response.status200("{\"ok\": \"Name changed.\"}");
        } else {
            return Response.status400("Bad request.");
        }
    }

    @DeleteMapping(path = "/dog/delete")
    public ResponseEntity<String> deleteDog(@PathParam("id") int id){
        if (id < 1){
            return Response.status400("Bad request.");
        }

        Database database = new Database();
        if (database.deleteDogByID(id)){
            return Response.status200("{\"ok\": \"Dog record deleted.\"}");
        } else {
            return Response.status400("Bad request.");
        }
    }
}
