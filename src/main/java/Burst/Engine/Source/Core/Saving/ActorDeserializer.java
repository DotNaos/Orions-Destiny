package Burst.Engine.Source.Core.Saving;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Component;
import com.google.gson.*;

import java.lang.reflect.Type;


public class ActorDeserializer implements JsonDeserializer<Actor> {
    @Override
    public Actor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();

        JsonArray components = jsonObject.getAsJsonArray("components");


        Actor actor = new Actor();
        actor.setName(name);
        for (JsonElement e : components) {
            ActorComponent ac = context.deserialize(e, ActorComponent.class);
            actor.addComponent(ac);
        }
        return actor;
    }
}