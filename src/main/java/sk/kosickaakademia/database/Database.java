package sk.kosickaakademia.database;

import sk.kosickaakademia.entity.Dog;
import sk.kosickaakademia.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Database {
    private String url;
    private String user;
    private String password;

    public Database(){
        loadConfig("src/main/resources/configLocal.properties");
    }

    private void loadConfig(String filepath){
        try {
            InputStream inputStream = new FileInputStream(filepath);

            Properties properties=new Properties();

            properties.load(inputStream);

            url=properties.getProperty("url");
            user=properties.getProperty("user");
            password=properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url,user,password);
            Log.ok("Connection successful");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            Log.error(e.toString());
        }
        return null;
    }

    private void closeConnection(Connection connection){
        try {
            connection.close();
            Log.ok("Connection closed");
        } catch (SQLException e) {
            Log.error(e.toString());
        }
    }

    private List<Dog> executeSelect(PreparedStatement ps) {
        List<Dog> list = new ArrayList<>();
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                int sex = rs.getInt("sex");
                String breed = rs.getString("breed");
                String color = rs.getString("color");
                Date arrival = rs.getDate("arrival");

                list.add(new Dog(id, name, age, sex, breed, color, arrival));
            }
        } catch (SQLException e) {
            Log.error(e.toString());
        }
        return list;
    }

    public List<Dog> getAllDogs(){
        Log.info("Executing Database.getAllDogs");
        Connection connection = getConnection();

        if (connection != null){
            String query = "select * from dog";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                List<Dog> list = executeSelect(ps);
                closeConnection(connection);

                return list;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public boolean insertNewDog(Dog dog){
        Log.info("Executing Database.insertNewDog");
        Connection connection = getConnection();

        if (connection != null){
            String query ="insert into dog(name, age, sex, breed, color) values(?,?,?,?,?)";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, dog.getName());
                ps.setInt(2, dog.getAge());
                ps.setInt(3, dog.getSex().getValue());
                ps.setString(4, dog.getBreed());
                ps.setString(5, dog.getColor());

                return ps.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean deleteDogByID(int id){
        Log.info("Executing Database.deleteDogByID");
        Connection connection = getConnection();

        if (connection != null){
            String query ="delete * from dog where id=?";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, id);

                return ps.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean changeNameByID(int id, String name){
        Log.info("Executing Database.changeNameByID");
        Connection connection = getConnection();

        if (connection != null){
            String query ="update dog set name=? where id=?";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(2, id);
                ps.setString(1, name);

                return ps.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
