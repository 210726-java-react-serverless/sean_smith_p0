package com.revature.ClassManager.documents;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import com.revature.ClassManager.util.MongoClientFactory;
import com.revature.ClassManager.util.exceptions.DataSourceException;
import org.bson.Document;

import java.util.List;


public class registrationCatalog {

    private String className;
    private int classSize;
    private List<String> students;
    private ObjectMapper mapper = new ObjectMapper();

    public registrationCatalog(String className, int classSize){
        this.className = className;
        this.classSize = classSize;
    }

    public registrationCatalog(String className, String students){
        this.className = className;
        this.students.add(students);
    }


    public registrationCatalog(String className){
        this.className = className;
    }

    public registrationCatalog save(registrationCatalog newUser, String className) {

        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();

            MongoDatabase classDb = mongoClient.getDatabase("classes");
            try{
                classDb.createCollection(className);
            }catch (Exception e){
                System.out.println("Class already exists!");
            }

            return newUser;

        } catch (Exception e) {
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }


    public registrationCatalog delete(registrationCatalog newUser, String name) {

        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();

            MongoDatabase classDb = mongoClient.getDatabase("classes");
            classDb.getCollection(name).drop();

            return newUser;

        } catch (Exception e) {
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    public registrationCatalog find(registrationCatalog newUser, String className) {

        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();

            MongoDatabase classDb = mongoClient.getDatabase("classes");
            MongoCollection<Document> usersCollection = classDb.getCollection(className);
            Document newUserDoc = new Document("className", newUser.getClassName());

            System.out.println(usersCollection.find());
            return newUser;

        } catch (Exception e) {
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    public registrationCatalog register(registrationCatalog newUser, String classname) {

        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();

            MongoDatabase classDb = mongoClient.getDatabase("classes");
            MongoCollection<Document> usersCollection = classDb.getCollection(classname);
            Document newUserDoc = new Document("Students", newUser.getClassName());

            usersCollection.insertOne(newUserDoc);

            return newUser;

        } catch (Exception e) {
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    public registrationCatalog withdraw(registrationCatalog newUser, String className){
        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();

            MongoDatabase classDb = mongoClient.getDatabase("classes");
            MongoCollection<Document> usersCollection = classDb.getCollection(className);
            Document newUserDoc = new Document("Students", newUser.getClassName());

            usersCollection.deleteOne(newUserDoc);

            return newUser;

        } catch (Exception e) {
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassSize() {
        return classSize;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public void setClassSize(int classSize) {
        this.classSize = classSize;
    }
}
