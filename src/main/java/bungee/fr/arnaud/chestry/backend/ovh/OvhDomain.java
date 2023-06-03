package bungee.fr.arnaud.chestry.backend.ovh;

import com.google.common.collect.ImmutableMap;

public class OvhDomain {

    String endpoint = "ovh-eu";
    String appKey = "e1f988080fb90a87";
    String appSecret = "1122ed6c9cc5c7b411a6624d034df9f9";
    String consumerKey = "1eccfcfa304cf01ce7761032b454eb92";

    OvhApi api = new OvhApi(endpoint, appKey, appSecret, consumerKey);

    public void registerRecord(String name, int port) {

        try {

            String body = new OvhRequest().createRequest(ImmutableMap.of(
                    "ttl", 0,
                    "fieldType", "SRV",
                    "target", "5 5 " + port + " play.chestry.io.",
                    "subDomain", "_minecraft._tcp." + name)
            );

            api.post("/domain/zone/chestry.io/record", body, true);

        } catch (OvhApiException e) {
            e.printStackTrace();
        }
    }
}
