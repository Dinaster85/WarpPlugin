package command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import warpplugin.main;

import java.util.ArrayList;
import java.util.List;

public class warp extends Command
{
    private final Config text, warps;
    public warp(Config text, Config warps) {
        super("warp", "Teleport to a warp", "Usage: /warp [name]");

        this.text = text;
        this.warps = warps;
    }

    @Override
    public boolean execute(CommandSender sender, String command, String[] args)
    {
        // Check if there are too many arguments
        if (args.length > 1) {
            sender.sendMessage(usageMessage);
            return false;
        }

        // Display warp list if no warp name is provided
        if (args.length == 0)
        {
            // Get all keys at the top level and sort them alphabetically
            List<String> warpKeys = new ArrayList<>(warps.getKeys(false));
            warpKeys.sort(String.CASE_INSENSITIVE_ORDER);

            // Check if there is any available warps
            if (warpKeys.isEmpty()) {
                sender.sendMessage(text.getString("no-warps"));
                return false;
            }

            // Concatenate warp names with commas
            String warpNames = String.join(text.getString("warp-list-delimeter"), warpKeys);
            sender.sendMessage(text.getString("warp-list").replace("{warps}", warpNames));
            return false;
        }

        // Get the exact warp name in a case-insensitive manner (if it exists)
        String warpName = args[0];
        for (String key : warps.getKeys(false)) {
            if (key.equalsIgnoreCase(args[0]))
            {
                warpName = key;
                break;
            }
        }

        // Check if the warp does not exist
        if (!warps.exists(warpName)) {
            sender.sendMessage(text.getString("unknown-warp").replace("{warp}", warpName));
            return false;
        }

        // Check if the command sender is a player
        if (!sender.isPlayer()) {
            sender.sendMessage(text.getString("console"));
            return false;
        }

        Player player = (Player) sender;
        String worldName = warps.getString(warpName + ".world");

        // Check if the player needs to switch worlds
        if (!player.getLevel().getName().equals(worldName)) {
            // Switch worlds
            player.switchLevel(main.getInstance().getServer().getLevelByName(worldName));
        }

        // Get warp coordinates
        double x = warps.getDouble(warpName + ".x");
        double y = warps.getDouble(warpName + ".y");
        double z = warps.getDouble(warpName + ".z");

        // Get current player pitch / yaw
        double pitch = player.getPitch();
        double yaw = player.getYaw();

        // Teleport player to position
        Position pos = new Position(x, y, z);
        player.setPosition(pos);
        player.sendPosition(pos, yaw, pitch, 2);

        sender.sendMessage(text.getString("teleport").replace("{warp}", warpName));
        return false;
    }
}