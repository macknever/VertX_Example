package net.globalrelay.starter.json;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonObjectExample {
    @Test
    void jsonObjectTest() {
        JsonObject jsonObject = new JsonObject().put("id", 1).put("name", "lawrence").put("man", true);

        assertEquals(1, jsonObject.getInteger("id"));
    }

    @Test
    void jsonObjectCanBeMapped() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("id", 1);
        jsonMap.put("name", "Lawrence");
        jsonMap.put("man", true);

        JsonObject jsonObject = new JsonObject(jsonMap);

        assertEquals(jsonObject.getMap(), jsonMap);

    }
}
