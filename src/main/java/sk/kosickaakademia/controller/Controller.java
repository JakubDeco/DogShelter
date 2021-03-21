package sk.kosickaakademia.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(path = "/dog/new")
    public ResponseEntity<String> addNewDog(@RequestBody String body){
        if (body == null || body.isBlank())
            return Response.status400("Bad request.");

        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(body);

            String name = (String) jsonObject.get("name");
            int age = Integer.parseInt(String.valueOf(jsonObject.get("age")));
            String sex = (String) jsonObject.get("sex");
            String breed = (String) jsonObject.get("breed");
            String color = (String) jsonObject.get("color");

            if (age < 0 || (!sex.equals("female") && !sex.equals("male")) || breed == null
                    || breed.isBlank() || color == null || color.isBlank()){
                return Response.status400("Bad request.");
            }

            int dogSex = sex.equals("male") ? 0 : 1;


            boolean dbsResponse = new Database().insertNewDog(new Dog(name, age, dogSex, breed, color));
            if (dbsResponse)
                return Response.status201("Dog successfully added.");
            else
                return Response.status500("Internal server error");

        } catch (ParseException e) {
            return Response.status400("Bad request.");
        }
    }
}
