package warpplugin;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import command.setwarp;
import command.delwarp;
import command.warp;
import command.warps;

import java.io.File;

public class main extends PluginBase
{
    private static main instance;
    public static main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // Create resource file with default data
        this.saveResource("messages.yml");
        this.saveDefaultConfig();

        // Initialize config objects
        Config text = new Config(new File(getDataFolder(), "messages.yml"));
        Config warps = new Config(new File(getDataFolder(), "warps.yml"));

        // Register commands
        getServer().getCommandMap().register("setwarp", new setwarp(text, warps));
        getServer().getCommandMap().register("delwarp", new delwarp(text, warps));
        getServer().getCommandMap().register("warp", new warp(text, warps));
        getServer().getCommandMap().register("warps", new warps(text, warps));
        // Set command permissions
        getServer().getCommandMap().getCommand("setwarp").setPermission("command.setwarp");
        getServer().getCommandMap().getCommand("delwarp").setPermission("command.delwarp");
    }
}