package edu.eci.escualing.arep.LogServicesApp.servicesImpl;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class ServicesDataMongo {
    MongoClient conexion = null;
    public MongoDatabase database=null;
    public MongoCollection<Document> collection =null;

    public ServicesDataMongo() {
        System.out.println("----------213132123---------" );
        this.conexion =  new MongoClient("localhost",27017);
        this.database = conexion.getDatabase("logservice");
         
        // this.database.createCollection("Datos");
        this.collection= database.getCollection("Datos");
    }

    public String addDato(String dato,String date) {
        System.out.println("ENTRE 1");
       
        System.out.println(database.listCollectionNames()+" namesCollection");
        System.out.println("ENTRE 3");
      
        System.out.println("ENTRE");
        Document document = new Document("dato", dato);
        System.out.println("PASE EL DOCUMENTO");
        System.out.println("Collection created successfully"); 
//        System.out.println(this.collection.toString()+" AQUI ESTA COLLECTION :(");
        this.collection.insertOne(document);
        System.out.println("entre en añadir dato x2");
        return "Dato añadido";
    }

    public List<String> getAllDatos() {
    
        ArrayList<Document> temp = new ArrayList<>();
        ArrayList<String> dataList = new ArrayList<>();
        int limit = 1;

        for(int i=temp.size()-1; limit<11; i--){
            limit++;
            dataList.add("{ \"dato\":"+temp.get(i).get("dato")+", \"date\": \""+temp.get(i).get("date")+"\"}");
        }
        return dataList;
    }

}