package command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;

public class Setwarp extends Command
{
    private final Config text, warps;
    public Setwarp(Config text, Config warps)
    {
        super("setwarp", "Creates or updates an existing warp", "Usage: /setwarp [name]");
        this.text = text;
        this.warps = warps;

        // Command parameters
        CommandParameter[] params = {
                CommandParameter.newType("name", CommandParamType.STRING)
        };
        // Set the command parameters
        addCommandParameters("default", params);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args)
    {
        // Check if the command sender is a player
        if (!sender.isPlayer()) {
            sender.sendMessage(text.getString("console"));
            return false;
        }

        // Check if the player has permission to execute this command
        Player player = (Player) sender;
        if (!player.hasPermission("command.setwarp")) {
            sender.sendMessage(text.getString("no-perms"));
            return false;
        }

        // Check if there are too many or not enough arguments
        if (args.length != 1) {
            sender.sendMessage(usageMessage);
            return false;
        }

        // Get the exact warp name (key) in a case-insensitive manner (if it exists)
        String warpName = args[0];
        for (String key : warps.getKeys(false)) {
            if (key.equalsIgnoreCase(args[0]))
            {
                warpName = key;
                break;
            }
        }

        // Extract required warp data from the player
        Position pos = player.getPosition();
        String worldName = player.getLevel().getName();

        // Check if the warp already exists (case-insensitive)
        if (warps.exists(warpName))
        {
            // If the inputted warp name does not match the exact key name
            if (!warpName.equals(args[0]))
            {
                // Remove the old warp
                warps.remove(warpName);
                // Update the warp name
                warpName = args[0];
            }
            sender.sendMessage(text.getString("update-warp")
                    .replace("{warp}", warpName)
                    .replace("{x}", String.format("%.1f", pos.x))
                    .replace("{y}", String.format("%.1f", pos.y))
                    .replace("{z}", String.format("%.1f", pos.z)));
        }

        else
        {
            sender.sendMessage(text.getString("create-warp")
                    .replace("{warp}", warpName)
                    .replace("{x}", String.format("%.1f", pos.x))
                    .replace("{y}", String.format("%.1f", pos.y))
                    .replace("{z}", String.format("%.1f", pos.z)));
        }

        // Create or update the warp data
        warps.set(warpName + ".world", worldName);
        warps.set(warpName + ".x", pos.x);
        warps.set(warpName + ".y", pos.y);
        warps.set(warpName + ".z", pos.z);

        warps.save();
        warps.reload();

        return false;
    }
}