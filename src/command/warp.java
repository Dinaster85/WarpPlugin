package command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import plugin.main;

import java.io.File;

public class warp extends Command {
    public warp() {
        super("warp", "Teleport to a warp", "Usage: /warp [name]");
    }

    @Override
    public boolean execute(CommandSender sender, String command, String[] args) {
        Config cfg = new Config(new File(main.getInstance().getDataFolder(), "config.yml"));
        cfg.reload();

        if (sender.isPlayer()) {
            Player player = (Player) sender;

            if (args.length == 1) {
                if (cfg.exists(args[0].toLowerCase() + "_world")) {
                    String warpName = args[0].toLowerCase();
                    String worldName = cfg.getString(args[0].toLowerCase() + "_world");

                    if (!player.getLevel().getName().equals(cfg.getString(args[0].toLowerCase() + "_world"))) {
                        player.switchLevel(main.getInstance().getServer().getLevelByName(worldName));
                    }

                    Position pos = new Position(cfg.getDouble(warpName + "_x"), cfg.getDouble(warpName + "_y"), cfg.getDouble(warpName + "_z"));
                    player.teleport(pos);

                    sender.sendMessage("§o§7Teleported to " + args[0]);
                }

                else sender.sendMessage("§cUnknown warp: " + args[0] + ". Please check that the warp exists and that you have permission to access it.");
            }

            else sender.sendMessage("§c" + usageMessage);
        }

        else sender.sendMessage("§cYou can only perform this command as a player");
        return false;
    }
}
