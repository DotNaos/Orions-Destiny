package Burst.Engine.Source.Core.Saving;

import Burst.Engine.Source.Core.Component;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ComponentDeserializer implements JsonSerializer<Component>,
        JsonDeserializer<Component> {

    @Override
    public Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            return context.deserialize(element, Class.forName(type));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown element type: " + type, e);
        }
    }


    // @Override
    // public Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    //     JsonObject jsonObject = json.getAsJsonObject();
    //     String className = jsonObject.get("className").getAsString();
    //     Class<?> clazz;
    //     try {
    //         clazz = Class.forName(className);
    //     } catch (ClassNotFoundException e) {
    //         throw new JsonParseException("Class not found: " + className, e);
    //     }
    //     return context.deserialize(jsonObject, clazz);
    // }


    @Override
    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }
}
