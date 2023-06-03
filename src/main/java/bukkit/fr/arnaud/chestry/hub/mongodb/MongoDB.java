package bukkit.fr.arnaud.chestry.hub.mongodb;

import bukkit.fr.arnaud.chestry.ChestryBukkit;
import bukkit.fr.arnaud.chestry.hub.versions.MainVersion;
import bukkit.fr.arnaud.chestry.hub.versions.SubVersion;
import bukkit.fr.arnaud.chestry.hub.versions.VersionType;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MongoDB {

    private MongoClient client;
    private MongoDatabase db;
    private MongoCollection<Document> collection;

    public void connect() {

        client = MongoClients.create("mongodb://root:A7DSbRt9es@91.206.229.149:27017");
        db = client.getDatabase("chestry");

        collection = db.getCollection("servers");
    }

    public List<VersionType> getVersionTypes() {

        MongoCollection<Document> collection = db.getCollection("version_types");

        List<VersionType> versionTypes = StreamSupport.stream(collection.find().spliterator(), false)
                .map(document -> new VersionType(document.getString("name"), document.getString("sub_version_type"), document.getString("description"), document.getString("texture"), document.getDouble("price")))
                .collect(Collectors.toList());

        return versionTypes;
    }

    public List<MainVersion> getMainVersion() {

        MongoCollection<Document> collection = db.getCollection("main_versions");

        List<MainVersion> mainVersions = StreamSupport.stream(collection.find().spliterator(), false)
                .map(document -> {

                    VersionType versionType = ChestryBukkit.getInstance().getVersionsManager().getVersionTypesFromName(document.getString("version_type"), document.getString("sub_version_type"));

                    return new MainVersion(document.getString("name"), document.getString("description"), versionType, document.getString("texture"));

                })
                .collect(Collectors.toList());

        return mainVersions;
    }

    public List<SubVersion> getSubVersion() {

        MongoCollection<Document> collection = db.getCollection("sub_versions");

        List<SubVersion> versionsAvailable = StreamSupport.stream(collection.find().spliterator(), false)
                .map(document -> new SubVersion(document.getString("name"), ChestryBukkit.getInstance().getVersionsManager().getMainVersionFromName(document.getString("main_version")), ChestryBukkit.getInstance().getVersionsManager().getVersionTypesFromName(document.getString("version_type"))))
                .collect(Collectors.toList());

        return versionsAvailable;
    }

    public boolean portIsUsed(int port) {

        Stream<Document> documentStream = StreamSupport.stream(collection.find().spliterator(), false);

        return documentStream.anyMatch(doc -> doc.get("port").equals(port));
    }
}
