package youyihj.herodotusutils.modsupport.modularmachinery;

import com.google.gson.*;
import net.minecraft.util.JsonUtils;
import youyihj.herodotusutils.modsupport.modularmachinery.block.BlockMMController;

import java.lang.reflect.Type;

public enum MachineJsonPreReader implements JsonDeserializer<BlockMMController> {
    INSTANCE;

    @Override
    public BlockMMController deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new BlockMMController(
                JsonUtils.getString(jsonObject, "registryname"),
                JsonUtils.getString(jsonObject, "localizedname"),
                Integer.parseInt(JsonUtils.getString(jsonObject, "color", "ffffff"), 16)
        );
    }
}
