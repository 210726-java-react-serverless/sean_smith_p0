package com.revature.ClassManager.documents;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.*;

import com.mongodb.client.model.Projections;
import com.revature.ClassManager.util.MongoClientFactory;
import com.revature.ClassManager.util.exceptions.DataSourceException;
import com.sun.deploy.util.StringUtils;
import org.bson.Document;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static jdk.nashorn.internal.objects.NativeString.substr;


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

    public void showClasses() {

        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();

            MongoDatabase classDb = mongoClient.getDatabase("classes");

            MongoIterable<String> list = classDb.listCollectionNames();
            for (String name : list) {
                System.out.println(name);
            }

        } catch (Exception e) {
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    public registrationCatalog showRoster(registrationCatalog newUser, String className) {

        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();

            MongoDatabase classDb = mongoClient.getDatabase("classes");
            MongoCollection<Document> usersCollection = classDb.getCollection(className);
            Document newUserDoc = new Document("className", newUser.getClassName());

            FindIterable<Document> iterDoc = usersCollection.find();
            Iterator it = iterDoc.iterator();
            while (it.hasNext()) {
                String stNames = it.next().toString().substring(49);
                stNames = stNames.substring(0, stNames.length() - 2);
                System.out.println(stNames);
            }

        } catch (Exception e) {
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
        return newUser;
    }

    public boolean currentlyRegistered(registrationCatalog newUser, boolean reg, String className){
        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();

            MongoDatabase classDb = mongoClient.getDatabase("classes");
            MongoCollection<Document> usersCollection = classDb.getCollection(className);
            Document newUserDoc = new Document("className", newUser.getClassName());

            FindIterable<Document> iterDoc = usersCollection.find();
            Iterator it = iterDoc.iterator();
            while (it.hasNext()) {
                String stNames = it.next().toString().substring(49);
                stNames = stNames.substring(0, stNames.length() - 2);
                if(stNames.equals(newUser.getClassName())){
                    reg = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
        return reg;
    }

    public List<String> getAllCollections(List<String> classNames){
        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();

            MongoDatabase classDb = mongoClient.getDatabase("classes");

            MongoIterable<String> list = classDb.listCollectionNames();
            for (String name : list) {
                classNames.add(name);
            }
            return classNames;

        } catch (Exception e) {
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }


    }

    public registrationCatalog register(registrationCatalog newUser, String classname) {

        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();

            MongoDatabase classDb = mongoClient.getDatabase("classes");
            boolean collExists = false;
            for (final String name : classDb.listCollectionNames()) {
                if (name.equalsIgnoreCase(classname)) {
                    collExists = true;
                }
            }

            if(collExists){
                try{
                    MongoCollection<Document> usersCollection = classDb.getCollection(classname);
                    Document newUserDoc = new Document("Students", newUser.getClassName());
                    usersCollection.insertOne(newUserDoc);
                }catch (Exception e){
                    System.out.println("Student already registered");
                }

            }

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
