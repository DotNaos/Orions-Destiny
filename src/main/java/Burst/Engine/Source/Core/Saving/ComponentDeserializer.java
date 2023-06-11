package Burst.Engine.Source.Core.Saving;

import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Assets.AssetManager;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author GamesWithGabe
 * The ComponentDeserializer class is responsible for deserializing components from JSON.
 * It implements the JsonDeserializer interface and provides methods to deserialize components.
 * It is used by the AssetManager class to deserialize components from JSON files.
 * @see AssetManager
 * @see ActorComponent
 * @see JsonDeserializer
 *
 */
public class ComponentDeserializer implements JsonSerializer<ActorComponent>, JsonDeserializer<ActorComponent> {

  @Override
  public ActorComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    String type = jsonObject.get("type").getAsString();
    JsonElement element = jsonObject.get("properties");

    try {
      return context.deserialize(element, Class.forName(type));
    } catch (ClassNotFoundException e) {
      throw new JsonParseException("Unknown element type: " + type, e);
    }
  }


  /**
   * @param src       the object that needs to be converted to Json.
   * @param typeOfSrc the actual type (fully genericized version) of the source object.
   * @param context
   * @return
   */
  @Override
  public JsonElement serialize(ActorComponent src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject result = new JsonObject();
    result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
    result.add("properties", context.serialize(src, src.getClass()));
    return result;
  }
}
