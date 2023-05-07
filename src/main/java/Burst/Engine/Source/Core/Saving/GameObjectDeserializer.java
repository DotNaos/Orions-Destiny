package Burst.Engine.Source.Core.Saving;

import Burst.Engine.Source.Core.Actor.Actor;
import com.google.gson.*;
import Burst.Engine.Source.Core.Component;

import java.lang.reflect.Type;


    public class GameObjectDeserializer implements JsonDeserializer<Actor> {
        @Override
        public Actor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();

            JsonArray components = jsonObject.getAsJsonArray("components");

            Actor go = new Actor(name);
            for (JsonElement e : components)
            {
                Component c = context.deserialize(e, Component.class);
                go.addComponent(c);
            }
            return go;
        }
    }