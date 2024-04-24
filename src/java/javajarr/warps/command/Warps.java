package javajarr.warps.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;

import javajarr.warps.Main;

import java.util.ArrayList;
import java.util.List;


public class Warps extends Command {
    private final Config warps;

    public Warps(Config warps) {
        super("warps", Main.getTranslate("command-warps-description"), Main.getTranslate("command-warps-usage"));
        this.warps = warps;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
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
            sender.sendMessage(Main.getTranslate("no-warps"));
            return false;
        }

        // Concatenate warp names with commas
        String warpNames = String.join(Main.getTranslate("warp-list-delimiter"), warpKeys);
        sender.sendMessage(Main.getTranslate("warp-list", warpNames));
        return false;
    }
}