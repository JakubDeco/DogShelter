package sk.kosickaakademia.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sk.kosickaakademia.entity.Dog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Util {
    public String getJson(List<Dog> list){
        if (list == null || list.isEmpty())
            return "{}";

        JSONObject root = new JSONObject();
        JSONArray dogsArr = new JSONArray();

        for (Dog dog :
                list) {
            if (dog != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", dog.getId());
                jsonObject.put("name", dog.getName());
                jsonObject.put("age", dog.getAge());
                jsonObject.put("sex", dog.getSex().toString());
                jsonObject.put("breed", dog.getBreed());
                jsonObject.put("color", dog.getColor());
                jsonObject.put("arrival", dog.getArrival());

                dogsArr.add(jsonObject);
            }
        }

        // adding dateTime, dogs array and count
        root.put("dateTime", Util.getDateTime());
        root.put("dogs", dogsArr);
        root.put("count", dogsArr.size());

        return root.toJSONString();
    }

    public String getJson(Dog dog){
        if(dog != null){
            JSONObject root = new JSONObject();

            // creating users array key and it's content
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", dog.getId());
            jsonObject.put("name", dog.getName());
            jsonObject.put("age", dog.getAge());
            jsonObject.put("sex", dog.getSex().toString());
            jsonObject.put("breed", dog.getBreed());
            jsonObject.put("color", dog.getColor());
            jsonObject.put("arrival", dog.getArrival());

            JSONArray dogsArr = new JSONArray();
            dogsArr.add(jsonObject);
            root.put("dogs", dogsArr);

            root.put("count", dogsArr.size());

            // adding dateTime
            root.put("dateTime", Util.getDateTime());

            return root.toJSONString();
        }

        return "{}";
    }

    public static String getDateTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar dateTime = Calendar.getInstance();

        return sdf.format(dateTime.getTime());
    }

    public static String normalizeName(String name){
        if (name == null || name.isBlank())
            return "";
        name = name.trim();
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
