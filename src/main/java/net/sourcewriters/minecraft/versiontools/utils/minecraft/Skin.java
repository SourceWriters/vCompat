package net.sourcewriters.minecraft.versiontools.utils.minecraft;

import java.io.IOException;
import java.io.Serializable;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.json.io.JsonParser;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.utils.NbtStorage;

public class Skin implements Serializable, NbtStorage<NbtCompound> {

    public static final Skin NONE = new Skin();
    private static final JsonParser PARSER = new JsonParser();

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

        if (textures.isEmpty()) {
            return ((JsonObject) textures.get("SKIN")).get("url").getValue().toString();
        }

        return null;
    }

    /**
     * @return UUID string of real skin owner
     */
    public String getOwnerUUID() {
        return (String) fromString(Base64Coder.decodeString(value)).get("profileId").getValue();
    }

    /**
     * @return Name of real skin owner
     */
    public String getOwnerName() {
        return (String) fromString(Base64Coder.decodeString(value)).get("profileName").getValue();
    }

    private SkinModel parseModel() {
        JsonObject textures = getTextures();

        if (!textures.isEmpty()) {
            JsonObject skinObj = (JsonObject) textures.get("SKIN");
            if (skinObj.has("metadata", ValueType.OBJECT)) {
                JsonObject metadata = (JsonObject) skinObj.get("metadata");
                if (metadata.has("model", ValueType.STRING)) {
                    return SkinModel.fromString((String) metadata.get("model").getValue());
                }
            }
        }

        return SkinModel.NORMAL;
    }

    private JsonObject getTextures() {
        return (JsonObject) fromString(Base64Coder.decodeString(value)).get("textures");
    }

    private JsonObject fromString(String value) {
        try {
            return (JsonObject) PARSER.fromString(value);
        } catch (IOException | ClassCastException exp) {
            return new JsonObject();
        }
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

    @Override
    public String toString() {
        return asNbt().toMSONString();
    }

}