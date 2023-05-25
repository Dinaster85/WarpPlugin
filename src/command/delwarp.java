package command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;

public class delwarp extends Command
{
    private final Config text, warps;
    public delwarp(Config text, Config warps) {
        super("delwarp", "Removes a specified warp", "Usage: /delwarp [name]");

        this.text = text;
        this.warps = warps;
    }

    @Override
    public boolean execute(CommandSender sender, String command, String[] args)
    {
        // Check if the command sender is a player
        if (sender.isPlayer())
        {
            // Check if the player has permission to execute this command
            Player player = (Player) sender;
            if (!player.hasPermission("command.delwarp"))
            {
                sender.sendMessage(text.getString("no-perms"));
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
            if (key.equalsIgnoreCase(args[0]))
            {
                warpName = key;
                break;
            }
        }

        // Check if the warp exists (case-insensitive)
        if (warps.exists(warpName))
        {
            // Remove the warp
            warps.remove(warpName);
            warps.save();
            warps.reload();

            sender.sendMessage(text.getString("remove-warp").replace("{warp}", warpName));
        }
        // The specified warp does not exist
        else {
            sender.sendMessage(text.getString("unknown-warp").replace("{warp}", warpName));
        }
        return false;
    }
}
