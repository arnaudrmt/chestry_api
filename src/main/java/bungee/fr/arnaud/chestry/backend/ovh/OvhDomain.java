package bungee.fr.arnaud.chestry.backend.ovh;

import bungee.fr.arnaud.chestry.backend.ovh.api.OvhApi;
import bungee.fr.arnaud.chestry.backend.ovh.api.OvhApiException;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import java.util.Map;

public class OvhDomain {

    String endpoint = System.getenv("ovh_endpoint");
    String appKey = System.getenv("ovh_app_key");
    String appSecret = System.getenv("ovh_app_secret");
    String consumerKey = System.getenv("ovh_consumer_key");

    OvhApi api = new OvhApi(endpoint, appKey, appSecret, consumerKey);

    // Registering a DNS Entry

    public void registerDnsEntry(String name, int port) {

        try {

            // Creating the HTTP Request

            String body = createRequest(ImmutableMap.of(
                    "ttl", 0,
                    "fieldType", "SRV",
                    "target", "5 5 " + port + " play.chestry.io.",
                    "subDomain", "_minecraft._tcp." + name)
            );

            // Sending the request

            api.post("/domain/zone/chestry.io/record", body, true);

        } catch (OvhApiException e) {
            e.printStackTrace();
        }
    }

    // Method that helps create OVH Api request

    public <T> String createRequest(Map<T, T> args) {

        Gson gson = new Gson();
        String jsonString = gson.toJson(args);

        return jsonString;
    }
}
