package dev.array21.spawnpoint;

import dev.array21.dutchycore.DutchyCore;
import dev.array21.dutchycore.annotations.RegisterModule;
import dev.array21.dutchycore.module.PluginModule;
import dev.array21.dutchycore.module.file.ModuleConfiguration;
import dev.array21.spawnpoint.commands.SetWorldSpawnExecutor;
import dev.array21.spawnpoint.commands.WorldSpawnExecutor;
import org.bukkit.ChatColor;
import org.bukkit.permissions.PermissionDefault;

@RegisterModule(name = "Spawnpoint", version = "@VERSION@", author = "Dutchy76", infoUrl = "https://github.com/DutchyPlugins/Spawnpoint")
public class Spawnpoint extends PluginModule {

    @Override
    public void enable(DutchyCore plugin) {
        super.logInfo("Loading...");

        super.registerCommand("worldspawn", new WorldSpawnExecutor(this), this);
        super.registerPermissionNode("spawnpoint.teleport", PermissionDefault.TRUE, null, null);

        super.registerCommand("setworldspawn", new SetWorldSpawnExecutor(this), this);
        super.registerPermissionNode("spawnpoint.setspawn", PermissionDefault.OP, null, null);

        super.logInfo("Loaded");
    }

    public static String getMessagePrefix() {
        return String.format("%s[%sSpawnpoint%s]%s ", ChatColor.GRAY, ChatColor.AQUA, ChatColor.GRAY, ChatColor.RESET);
    }

}
