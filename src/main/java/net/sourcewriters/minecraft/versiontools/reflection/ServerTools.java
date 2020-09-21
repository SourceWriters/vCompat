package net.sourcewriters.minecraft.versiontools.reflection;

import static net.sourcewriters.minecraft.versiontools.reflection.setup.ReflectionProvider.DEFAULT;
import static net.sourcewriters.minecraft.versiontools.utils.java.OptionTools.checkPresence;

import java.util.Optional;

import org.bukkit.Bukkit;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public abstract class ServerTools {

    public static void setMotd(String motd) {

        Optional<Reflect> optional0 = DEFAULT.getOptionalReflect("nmsDedicatedServer");
        Optional<Reflect> optional1 = DEFAULT.getOptionalReflect("cbCraftServer");

        if (checkPresence(optional0, optional1))
            return;

        Object minecraftServer = optional1.get().getFieldValue("minecraftServer", Bukkit.getServer());

        optional0.get().execute(minecraftServer, "setMotd", motd);

    }

}
