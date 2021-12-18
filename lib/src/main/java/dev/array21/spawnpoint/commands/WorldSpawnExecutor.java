package dev.array21.spawnpoint.commands;

import dev.array21.dutchycore.module.commands.ModuleCommand;
import dev.array21.dutchycore.module.commands.ModuleTabCompleter;
import dev.array21.dutchycore.module.file.ModuleConfiguration;
import dev.array21.spawnpoint.Spawnpoint;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.regex.Pattern;

public class WorldSpawnExecutor implements ModuleCommand, ModuleTabCompleter {

    private Spawnpoint mod;

    public WorldSpawnExecutor(Spawnpoint mod) {
        this.mod = mod;
    }

    @Override
    public boolean fire(CommandSender sender, String[] args) {
        if(!sender.hasPermission("spawnpoint.teleport")) {
            sender.sendMessage(String.format("%s%sYou do not have permission to use this command!", Spawnpoint.getMessagePrefix(), ChatColor.RED));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(String.format("%s%sThis command may only be used in-game!", Spawnpoint.getMessagePrefix(), ChatColor.RED));
            return true;
        }

        ModuleConfiguration mc = this.mod.getModuleFileHandler().getModuleConfiguration();

        // Format: worldUuid:x:y:z:yaw:pitch
        String storedWorldSpawn = (String) mc.getValue("worldSpawn");

        if(storedWorldSpawn == null) {
            sender.sendMessage(String.format("%s%sNo World Spawn has been defined yet!", Spawnpoint.getMessagePrefix(), ChatColor.GOLD));
            return true;
        }

        String[] components = storedWorldSpawn.split(Pattern.quote(":"));
        UUID worldUuid = UUID.fromString(components[0]);
        World world = Bukkit.getWorld(worldUuid);

        int x = Integer.parseInt(components[1]);
        int y = Integer.parseInt(components[2]);
        int z = Integer.parseInt(components[3]);

        float yaw = Float.parseFloat(components[4]);
        float pitch = Float.parseFloat(components[5]);

        Location worldSpawn = new Location(world, x, y, z, yaw, pitch);
        Player player = (Player) sender;
        player.teleport(worldSpawn);

        sender.sendMessage(String.format("%s%sTeleporting...", Spawnpoint.getMessagePrefix(), ChatColor.GOLD));
        return true;
    }

    @Override
    public String[] complete(CommandSender sender, String[] args) {
        return null;
    }
}
