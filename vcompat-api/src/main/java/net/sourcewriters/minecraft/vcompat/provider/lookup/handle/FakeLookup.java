package net.sourcewriters.minecraft.vcompat.provider.lookup.handle;

public class FakeLookup extends ClassLookup {

    public static final FakeLookup FAKE = build();

    private FakeLookup() throws IllegalAccessException {
        super((Class<?>) null);
    }

    private static final FakeLookup build() {
        try {
            return new FakeLookup();
        } catch (IllegalAccessException e) {
            return null;
        }
    }

}