package bungee.fr.arnaud.chestry.backend.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MongoDB {

    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;

    public void connect() {

        client = MongoClients.create("mongodb://root:A7DSbRt9es@91.206.229.149:27017");
        db = client.getDatabase("chestry");

        collection = db.getCollection("servers");
    }

    public void addServer(String name, int port, String player, String ip, Map<String, String> roles) {

        Document doc = new Document();
        doc.put("name", name);
        doc.put("port", port);
        doc.put("player", player);
        doc.put("ip", ip);
        doc.put("roles", roles);

        collection.insertOne(doc);
    }

    public boolean portIsUsed(int port) {

        Stream<Document> documentStream = StreamSupport.stream(collection.find().spliterator(), false);

        return documentStream.anyMatch(doc -> doc.get("port").equals(port));
    }
}
