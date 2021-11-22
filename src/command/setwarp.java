package command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import plugin.main;

import java.util.ArrayList;
import java.io.File;

public class setwarp extends Command {
    public setwarp() {
        super("setwarp", "Creates or updates an existing warp", "Usage: /setwarp [name]");
    }

    public Position getPosition(Player player) {
        double x = player.getPosition().getX();
        double y = player.getPosition().getY();
        double z = player.getPosition().getZ();

        return new Position(x, y, z);
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

    public void saveData(Config cfg, Position position, String warpName, String worldName) {
        cfg.set(warpName + "_x", position.getX());
        cfg.set(warpName + "_y", position.getY());
        cfg.set(warpName + "_z", position.getZ());
        cfg.set(warpName + "_world", worldName);

        cfg.save();
    }

    @Override
    public boolean execute(CommandSender sender, String command, String[] args) {
        Config cfg = new Config(new File(main.getInstance().getDataFolder(), "config.yml"));
        cfg.reload();

        if (sender.isPlayer()) {
            Player player = (Player) sender;

            if (!player.hasPermission("command.setwarp")) {
                player.sendMessage("§cUnknown command: " + command + ". Please check that the command exists and that you have permission to use it.");
                return false;
            }

            if (args.length == 1) {
                Position pos = getPosition(player);

                String warpName = args[0].toLowerCase();
                String worldName = player.getLevel().getName();

                boolean initWarp = false;

                String[] warpList = cfg.getString("warp_list").replace("[", "").replace("]", "").split(",");
                ArrayList<String> warpArray = createWarpArray(warpList);

                if (!cfg.exists("warp_list")) {
                    cfg.set("warp_list", warpName);
                    initWarp = true;
                }

                else if (!warpArray.contains(warpName)) {
                    warpArray.add(warpName);
                    cfg.set("warp_list", warpArray);

                    initWarp = true;
                }

                if (!initWarp) sender.sendMessage("§aSet warp: §2" + args[0] + " §aat: §2" + pos.getFloorX() + ", " + pos.getFloorY() + ", " + pos.getFloorZ());
                else sender.sendMessage("§aAdded warp: §2" + args[0] + " §aat: §2" + pos.getFloorX() + ", " + pos.getFloorY() + ", " + pos.getFloorZ());

                saveData(cfg, pos, warpName, worldName);
            }

            else sender.sendMessage("§c" + usageMessage);
        }

        else sender.sendMessage("§cYou can only perform this command as a player");
        return false;
    }
}
