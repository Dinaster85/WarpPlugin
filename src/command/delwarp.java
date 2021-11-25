package command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import warpplugin.main;

import java.io.File;
import java.util.ArrayList;

public class delwarp extends Command {
    public delwarp() {
        super("delwarp", "Removes a specified warp", "Usage: /delwarp [name]");
    }

    public ArrayList<String> createWarpArray(String[] warpList) {
        ArrayList<String> warpArray = new ArrayList<>();

        for (int i = 0; i < warpList.length; i++) {
            String warp = warpList[i];

            if (warp.startsWith(" ")) {
                warp = warp.substring(1);
            }

            warpArray.add(warp);
        }

        return warpArray;
    }

    @Override
    public boolean execute(CommandSender sender, String command, String[] args) {
        Config cfg = new Config(new File(main.getInstance().getDataFolder(), "config.yml"));
        cfg.reload();

        if (sender.isPlayer()) {
            Player player = (Player) sender;

            if (!player.hasPermission("command.delwarp")) {
                player.sendMessage("§cUnknown command: " + command + ". Please check that the command exists and that you have permission to use it.");
                return false;
            }
        }

        if (args.length == 1) {
            String warpName = args[0].toLowerCase();

            String rawList = cfg.getString("warp_list");
            String[] warpList = cfg.getString("warp_list").replace("[", "").replace("]", "").split(",");
            ArrayList<String> warpArray = createWarpArray(warpList);

            if (warpArray.contains(warpName)) {
                cfg.remove(warpName + "_x");
                cfg.remove(warpName + "_y");
                cfg.remove(warpName + "_z");
                cfg.remove(warpName + "_world");

                warpArray.remove(warpName);

                if (warpArray.isEmpty()) cfg.remove("warp_list");
                else cfg.set("warp_list", warpArray);
                cfg.save();

                sender.sendMessage("§aRemoved warp: §2" + args[0]);
            }

            else sender.sendMessage("§cUnknown warp: " + args[0] + ". Please check that the warp exists and that you have permission to access it.");
        }

        else sender.sendMessage("§c" + usageMessage);
        return false;
    }
}
