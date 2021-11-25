package warpplugin;

import cn.nukkit.plugin.PluginBase;

import command.warp;
import command.warplist;
import command.setwarp;
import command.delwarp;

public class main extends PluginBase {
    private static main instance;

    public static main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getServer().getCommandMap().register("warp", new warp());
        getServer().getCommandMap().register("warplist", new warplist());
        getServer().getCommandMap().register("setwarp", new setwarp());
        getServer().getCommandMap().register("delwarp", new delwarp());

        getServer().getCommandMap().getCommand("setwarp").setPermission("command.setwarp");
        getServer().getCommandMap().getCommand("delwarp").setPermission("command.delwarp");
    }
}
