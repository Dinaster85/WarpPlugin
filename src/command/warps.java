package command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class warps extends Command
{
    private final Config text, warps;
    public warps(Config text, Config warps) {
        super("warps", "Displays a list of available warps", "Usage: /warps");

        this.text = text;
        this.warps = warps;
    }

    @Override
    public boolean execute(CommandSender sender, String command, String[] args)
    {
        // Check if there are too many arguments
        if (args.length > 0) {
            sender.sendMessage(usageMessage);
            return false;
        }

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
}