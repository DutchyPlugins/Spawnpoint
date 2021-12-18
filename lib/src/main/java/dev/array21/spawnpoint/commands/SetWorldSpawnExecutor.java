package dev.array21.spawnpoint.commands;

import dev.array21.dutchycore.module.commands.ModuleCommand;
import dev.array21.dutchycore.module.commands.ModuleTabCompleter;
import dev.array21.dutchycore.module.file.ModuleConfiguration;
import dev.array21.spawnpoint.Spawnpoint;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SetWorldSpawnExecutor implements ModuleCommand, ModuleTabCompleter {

    private Spawnpoint mod;

    public SetWorldSpawnExecutor(Spawnpoint mod) {
        this.mod = mod;
    }

    @Override
    public String[] complete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public boolean fire(CommandSender sender, String[] args) {
        if(!sender.hasPermission("spanwpoint.setspawn")) {
            sender.sendMessage(String.format("%s%sYou do not have permission to use this command!", Spawnpoint.getMessagePrefix(), ChatColor.RED));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(String.format("%s%sThis command may only be used in-game!", Spawnpoint.getMessagePrefix(), ChatColor.RED));
            return true;
        }

        Player player = (Player) sender;
        Location l = player.getLocation();

        UUID worldUuid = l.getWorld().getUID();

        int pX = l.getBlockX();
        int pY = l.getBlockY();
        int pZ = l.getBlockZ();

        float pYaw = l.getYaw();
        float pPitch = l.getPitch();

        String configString = String.format("%s:%d:%d:%d:%.2f:%.2f", worldUuid, pX, pY, pZ, pYaw, pPitch);

        ModuleConfiguration mc = this.mod.getModuleFileHandler().getModuleConfiguration();
        mc.setValue("worldSpawn", configString);
        mc.save();

        sender.sendMessage(String.format("%s%sWorld spawn has been set!", Spawnpoint.getMessagePrefix(), ChatColor.GOLD));
        return true;
    }

}
