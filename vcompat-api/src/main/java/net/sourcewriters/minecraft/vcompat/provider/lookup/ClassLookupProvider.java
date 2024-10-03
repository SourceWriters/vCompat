package net.sourcewriters.minecraft.vcompat.provider.lookup;

import java.util.Map.Entry;
import java.util.Optional;

import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookup;
import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.ClassLookupCache;
import net.sourcewriters.minecraft.vcompat.provider.lookup.handle.FakeLookup;
import net.sourcewriters.minecraft.vcompat.util.java.tools.ReflectionTools;
import net.sourcewriters.minecraft.vcompat.version.IVersion;

public final class ClassLookupProvider {

    private final ClassLookupCache cache;

    private boolean skip = false;

    public ClassLookupProvider() {
        this(new ClassLookupCache());
    }

    public ClassLookupProvider(final ClassLookupCache cache) {
        this.cache = cache;
    }

    /*
     * Delete
     */

    public void deleteAll() {
        cache.clear();
    }

    public void deleteByName(final String name) {
        cache.delete(name);
    }

    public void deleteByPackage(final String path) {
        final Entry<String, ClassLookup>[] array = cache.entries();
        for (final Entry<String, ClassLookup> entry : array) {
            if (!entry.getValue().getOwner().getPackageName().equals(path)) {
                continue;
            }
            cache.delete(entry.getKey());
        }
    }

    /*
     * Skip
     */

    public ClassLookupProvider require(final boolean skip) {
        this.skip = !skip;
        return this;
    }

    public ClassLookupProvider skip(final boolean skip) {
        this.skip = skip;
        return this;
    }

    public boolean skip() {
        return skip;
    }

    /*
     * Reflection
     */

    public ClassLookupCache getReflection() {
        return cache;
    }

    public ClassLookup createNMSLookup(final String name, final String path) {
        return skip ? FakeLookup.FAKE : cache.create(name, getNMSClass(path));
    }

    public ClassLookup createCBLookup(final String name, final String path) {
        return skip ? FakeLookup.FAKE : cache.create(name, getCBClass(path));
    }

    public ClassLookup createLookup(final String name, final String path) {
        return skip ? FakeLookup.FAKE : cache.create(name, getClass(path));
    }

    public ClassLookup createLookup(final String name, final Class<?> clazz) {
        return skip ? FakeLookup.FAKE : cache.create(name, clazz);
    }

    public Optional<ClassLookup> getOptionalLookup(final String name) {
        return cache.get(name);
    }

    public ClassLookup getLookup(final String name) {
        return cache.get(name).orElse(null);
    }

    public Class<?> getNMSClass(final String path) {
        return getClass(IVersion.VERSION.minecraftClassPath(path));
    }

    public Class<?> getCBClass(final String path) {
        return getClass(IVersion.VERSION.craftClassPath(path));
    }

    public Class<?> getClass(final String path) {
        return ReflectionTools.getClass(path);
    }
}