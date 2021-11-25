package command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import warpplugin.main;

import java.io.File;

public class warplist extends Command {
    public warplist() {
        super("warplist", "Displays a list of available warps", "Usage: /warplist");
    }

    @Override
    public boolean execute(CommandSender sender, String command, String[] args) {
        Config cfg = new Config(new File(main.getInstance().getDataFolder(), "config.yml"));
        cfg.reload();

        if (args.length == 0) {
            if (cfg.getString("warp_list").equals("null") || !cfg.exists("warp_list")) sender.sendMessage("§cNo warps available.");
            else sender.sendMessage("§aWarps: §2" + cfg.getString("warp_list").replace("[", "").replace("]", ""));
        }

        else sender.sendMessage("§c" + usageMessage);
        return false;
    }
}
