package javajarr.warps.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.Config;

import javajarr.warps.Main;

import java.util.ArrayList;
import java.util.List;

public class Delwarp extends Command {
    private final Config warps;

    public Delwarp(Config warps) {
        super("delwarp", Main.getTranslate("command-delwarp-description"), Main.getTranslate("command-delwarp-usage"));
        this.warps = warps;

        // Command parameters
        CommandParameter[] params = {
                CommandParameter.newEnum("name", getWarpNames())
        };
        // Set the command parameters
        addCommandParameters("default", params);
    }

    private String[] getWarpNames() {
        // Get all keys at the top level and convert them to lowercase
        List<String> warpKeys = new ArrayList<>(warps.getKeys(false));
        warpKeys.replaceAll(String::toLowerCase); // Convert to lowercase
        return warpKeys.toArray(new String[0]);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        // Check if the command sender is a player
        if (sender.isPlayer()) {
            // Check if the player has permission to execute this command
            Player player = (Player) sender;
            if (!player.hasPermission("command.delwarp")) {
                sender.sendMessage(Main.getTranslate("no-perms"));
                return false;
            }
        }

        // Check if there are too many or not enough arguments
        if (args.length != 1) {
            sender.sendMessage(usageMessage);
            return false;
        }

        // Get the exact warp name (key) in a case-insensitive manner (if it exists)
        String warpName = args[0];
        for (String key : warps.getKeys(false)) {
            if (key.equalsIgnoreCase(args[0])) {
                warpName = key;
                break;
            }
        }

        // Check if the warp exists (case-insensitive)
        if (warps.exists(warpName)) {
            // Remove the warp
            warps.remove(warpName);
            warps.save();
            warps.reload();

            sender.sendMessage(Main.getTranslate("remove-warp", warpName));
        }
        // The specified warp does not exist
        else {
            sender.sendMessage(Main.getTranslate("unknown-warp", warpName));
        }
        return false;
    }
}