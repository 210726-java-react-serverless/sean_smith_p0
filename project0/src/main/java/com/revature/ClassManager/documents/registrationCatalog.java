package com.revature.ClassManager.documents;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.revature.ClassManager.util.MongoClientFactory;
import com.revature.ClassManager.util.exceptions.DataSourceException;
import org.bson.Document;

public class registrationCatalog {

    private String className;
    private int classSize;

    public registrationCatalog(String className, int classSize){
        this.className = className;
        this.classSize = classSize;
    }

    public registrationCatalog save(registrationCatalog newUser) {

        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();

            MongoDatabase classDb = mongoClient.getDatabase("bookstore");
            MongoCollection<Document> usersCollection = classDb.getCollection("users");
            Document newUserDoc = new Document("className", newUser.getClassName())
                    .append("classSize", newUser.getClassSize());

            usersCollection.insertOne(newUserDoc);

            return newUser;

        } catch (Exception e) {
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    public static registrationCatalog findClass(String className) {

        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase classDatabase = mongoClient.getDatabase("bookstore");
            MongoCollection<Document> usersCollection = classDatabase.getCollection("users");
            Document queryDoc = new Document("username", className);
            Document authUserDoc = usersCollection.find(queryDoc).first();

            if (authUserDoc == null) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            registrationCatalog authUser = mapper.readValue(authUserDoc.toJson(), registrationCatalog.class);
            return authUser;

        } catch (JsonMappingException jme) {
            jme.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An exception occurred while mapping the document.", jme);
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

    public void setClassSize(int classSize) {
        this.classSize = classSize;
    }
}
