package commons.mongodb;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import bukkit.fr.arnaud.chestry.hub.pets.Pet;
import bukkit.fr.arnaud.chestry.hub.versions.MainVersion;
import bukkit.fr.arnaud.chestry.hub.versions.SubVersion;
import bukkit.fr.arnaud.chestry.hub.versions.VersionType;
import bukkit.fr.arnaud.chestry.hub.versions.VersionsManager;
import bungee.fr.arnaud.chestry.ChestryBungee;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MongoDB {

    private MongoDatabase db;
    private final ChestryBukkit chestryBukkit = ChestryBukkit.getInstance();
    private final ChestryBungee chestryBungee = ChestryBungee.getInstance();
    private final VersionsManager versionsManager = chestryBukkit.getVersionsManager();

    public void connect() {

        String user = System.getenv("mongo_user");
        String password = System.getenv("mongo_password");
        String ip = System.getenv("ip_address");
        String port = System.getenv("mongo_port");

        MongoClient client = MongoClients.create("mongodb://" + user + ":" + password + "@" + ip + ":" + port);
        db = client.getDatabase(System.getenv("mongo_db_name"));
    }

    // BungeeCord Methods

    public void addServer(String name, String ip, int port, String versionType, Date date, UUID player, Map<UUID, String> roles) {

        MongoCollection<Document> collection = db.getCollection(System.getenv("mongo_db_servers"));

        Document doc = new Document();
        doc.put("name", name);
        doc.put("ip", ip);
        doc.put("port", port);
        doc.put("version_type", versionType);
        doc.put("date_of_creation", date);
        doc.put("player", player);
        doc.put("roles", roles);

        collection.insertOne(doc);
    }

    public boolean portIsUsed(int port) {

        MongoCollection<Document> collection = db.getCollection(System.getenv("mongo_db_servers"));

        Stream<Document> documentStream = StreamSupport.stream(collection.find().spliterator(), false);

        return documentStream.anyMatch(doc -> doc.get("port").equals(port));
    }

    // Bukkit Methods

        // Version System

    public List<VersionType> getVersionTypes() {

        MongoCollection<Document> collection = db.getCollection(System.getenv("mongo_db_version_type"));

        List<VersionType> versionTypeList = StreamSupport.stream(collection.find().spliterator(), false)

                .map(document ->
                        new VersionType(document.getString("name"), document.getString("sub_version_type"),
                                document.getString("description"), document.getString("texture"),
                                document.getDouble("price")))

                .collect(Collectors.toList());

        return versionTypeList;
    }

    public List<MainVersion> getMainVersion() {

        MongoCollection<Document> collection = db.getCollection(System.getenv("mongo_db_main_versions"));

        List<MainVersion> mainVersionList = StreamSupport.stream(collection.find().spliterator(), false)
                .map(document -> {

                    VersionType versionType = versionsManager.getVersionTypesFromName
                            (document.getString("version_type"), document.getString("sub_version_type"));

                    return new MainVersion(document.getString("name"), document.getString("description"),
                            versionType, document.getString("texture"));

                })
                .collect(Collectors.toList());

        return mainVersionList;
    }

    public List<SubVersion> getSubVersion() {

        MongoCollection<Document> collection = db.getCollection(System.getenv("mongo_db_sub_versions"));

        List<SubVersion> subVersionList = StreamSupport.stream(collection.find().spliterator(), false)

                .map(document -> new SubVersion(document.getString("name"),
                        versionsManager.getMainVersionFromName(document.getString("main_version")),
                        versionsManager.getVersionTypeFromName(document.getString("version_type"))))

                .collect(Collectors.toList());

        return subVersionList;
    }

        // Pets System

    public List<Pet> getPets() {

        MongoCollection<Document> collection = db.getCollection(System.getenv("mongo_db_version_pets"));

        List<Pet> petList = StreamSupport.stream(collection.find().spliterator(), false)

                .map(document -> new Pet(document.getString("name"), document.getString("display_name"),
                        document.getString("texture"),
                        versionsManager.getVersionTypeFromName(document.getString("version_type")),
                        (HashMap) document.get("skins")))

                .collect(Collectors.toList());

        return petList;
    }
}
