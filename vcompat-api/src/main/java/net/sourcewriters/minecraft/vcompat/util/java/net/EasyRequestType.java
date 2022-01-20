package net.sourcewriters.minecraft.vcompat.util.java.net;

public enum EasyRequestType {

    GET,
    HEAD,
    PUT(true),
    POST(true),
    DELETE(true),
    OPTIONS(true),
    PATCH(true),
    CONNECT,
    TRACE,
    NONE;

    private final boolean output;

    private EasyRequestType() {
        output = false;
    }

    private EasyRequestType(boolean state) {
        output = state;
    }

    public boolean hasOutput() {
        return output;
    }

    public static EasyRequestType fromString(String value) {
        value = value.toUpperCase();
        for (EasyRequestType type : values()) {
            if (type.name().equals(value)) {
                return type;
            }
        }
        return NONE;
    }

}