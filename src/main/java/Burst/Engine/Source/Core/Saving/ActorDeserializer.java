package Burst.Engine.Source.Core.Saving;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Physics.Components.Transform;
import com.google.gson.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;


public class ActorDeserializer implements JsonDeserializer<Actor> {
    @Override
    public Actor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        String name = jsonObject.get("name").getAsString();

        JsonArray components = jsonObject.getAsJsonArray("components");

        // Create a new actor of the correct type.
        Actor actor = null;
        try {
            actor = context.deserialize(jsonObject, Class.forName(type));
            actor.genID();
        } catch (Exception e ) {
            e.printStackTrace();
        }
        // If the name is not the default name, set it.
        // Otherwise, the name would contain an old ID.

        if (!name.contains("Actor "))
        {
            actor.setName(name);
        }
        for (JsonElement e : components) {
            ActorComponent ac = context.deserialize(e, ActorComponent.class);

            if (actor.getComponent(ac.getClass()) != null) {
                actor.removeComponent(ac.getClass());
            }
            actor.addComponent(ac);
        }

        return actor;
    }
}