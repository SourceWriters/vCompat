package net.sourcewriters.minecraft.versiontools.skin;

import java.io.Serializable;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.utils.NbtStorage;

public class Skin implements Serializable, NbtStorage<NbtCompound> {

    public static final Skin NONE = new Skin();

    /**
    * 
    */
    private static final long serialVersionUID = -6279824025820566499L;
    private final boolean editable;
    private String name;
    private String value;
    private String signature;
    private SkinModel model;

    private Skin() {
        this.editable = false;
    }

    public Skin(String name, String value, String signature) {
        this.name = name;
        this.editable = false;
        this.value = value;
        this.signature = signature;
        setModel(parseModel());
    }

    public Skin(String name, String value, String signature, boolean editable) {
        this.name = name;
        this.editable = editable;
        this.value = value;
        this.signature = signature;
        setModel(parseModel());
    }

    public boolean isEditable() {
        return editable;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (!isEditable()) {
            return;
        }
        this.value = value;
        setModel(parseModel());
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        if (!isEditable()) {
            return;
        }
        this.signature = signature;
    }

    public SkinModel getModel() {
        return model;
    }

    public void setModel(SkinModel model) {
        if (!isEditable()) {
            return;
        }
        this.model = model;
    }

    /**
     * @return Decoded skin url string, or null if skin value empty
     */
    public String getURL() {
        JsonObject textures = getTextures();

        if (textures.entrySet().size() != 0) {
            return textures.get("SKIN").getAsJsonObject().get("url").getAsString();
        }

        return null;
    }

    /**
     * @return UUID string of real skin owner
     */
    public String getOwnerUUID() {
        String decoded = Base64Coder.decodeString(value);
        JsonObject json = new JsonParser().parse(decoded).getAsJsonObject();
        return json.get("profileId").getAsString();
    }

    /**
     * @return Name of real skin owner
     */
    public String getOwnerName() {
        String decoded = Base64Coder.decodeString(value);
        JsonObject json = new JsonParser().parse(decoded).getAsJsonObject();
        return json.get("profileName").getAsString();
    }

    private SkinModel parseModel() {
        JsonObject textures = getTextures();

        if (textures.entrySet().size() != 0) {
            JsonObject skinObj = textures.get("SKIN").getAsJsonObject();
            if (skinObj.has("metadata")) {
                JsonObject metadata = skinObj.get("metadata").getAsJsonObject();
                if (metadata.has("model")) {
                    return SkinModel.SLIM;
                }
            }
        }

        return SkinModel.NORMAL;
    }

    private JsonObject getTextures() {
        String decoded = Base64Coder.decodeString(value);
        JsonObject json = new JsonParser().parse(decoded).getAsJsonObject();
        return json.get("textures").getAsJsonObject();
    }

    /*
    * 
    */

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Skin) {
            Skin skin = (Skin) obj;
            return skin.name.equals(name) && isSimilar(skin);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (signature.hashCode() << 16) + value.hashCode();
    }

    public boolean isSimilar(Skin skin) {
        return skin != null && skin.value.equals(value) && skin.signature.equals(signature);
    }

    /*
    * 
    */

    @Override
    public void fromNbt(NbtCompound nbt) {
        name = nbt.getString("name");
        value = nbt.getString("value");
        signature = nbt.getString("signature");
        model = SkinModel.fromString(nbt.getString("model"));
    }

    @Override
    public NbtCompound asNbt() {
        NbtCompound compound = new NbtCompound();
        compound.set("name", name);
        compound.set("value", value);
        compound.set("signature", signature);
        compound.set("model", model.name());
        return compound;
    }

}
