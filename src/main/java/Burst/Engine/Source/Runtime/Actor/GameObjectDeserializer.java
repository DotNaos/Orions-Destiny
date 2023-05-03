package Burst.Engine.Source.Runtime.Actor;

import Burst.Engine.Source.Core.Physics.Components.Transform;
import com.google.gson.*;
import Burst.Engine.Source.Core.Component;

import java.lang.reflect.Type;

public class GameObjectDeserializer implements JsonDeserializer<GameObject> {
    @Override
    public GameObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {



        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();

        // TODO: FIX THIS
        JsonArray components = jsonObject.getAsJsonArray("Burst/components");

        GameObject go = new GameObject(name);
        for (JsonElement e : components) {
            Component c = context.deserialize(e, Component.class);
            go.addComponent(c);
        }
        go.transform = go.getComponent(Transform.class);
        return go;
    }
}
