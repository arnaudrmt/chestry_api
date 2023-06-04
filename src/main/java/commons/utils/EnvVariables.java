package commons.utils;

public class EnvVariables {

    static {

        // Server

        String ip_address = "91.206.229.149";
        String domain_address = "chestry.io";

        // MongoDB

        String mongo_user = "root";
        String mongo_password = "A7DSbRt9es";
        String mongo_port = "27017";

        String mongo_db_name = "chestry";
        String mongo_db_collection = "servers";

        String mongo_db_main_versions = "main_versions";
        String mongo_db_version_type = "version_types";
        String mongo_db_sub_versions = "sub_versions";
        String mongo_db_version_pets = "version_pets";

        // OVH Api

        String ovh_endpoint = "ovh-eu";
        String ovh_appKey = "e1f988080fb90a87";
        String ovh_appSecret = "1122ed6c9cc5c7b411a6624d034df9f9";
        String ovh_consumerKey = "1eccfcfa304cf01ce7761032b454eb92";

        // Plugin Message

        String pm_bungee_channel = "chestry:bungee";
        String pm_bukkit_channel = "chestry:bukkit";
    }
}
