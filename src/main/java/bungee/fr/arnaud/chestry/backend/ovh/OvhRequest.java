package bungee.fr.arnaud.chestry.backend.ovh;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.Map;

public class OvhRequest {

    public <T> String createRequest(Map<T, T> args) {

        Gson gson = new Gson();
        String jsonString = gson.toJson(args);

        return jsonString;
    }
}
