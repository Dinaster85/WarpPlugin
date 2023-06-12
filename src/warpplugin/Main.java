package warpplugin;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import command.Setwarp;
import command.Delwarp;
import command.Warp;
import command.Warps;
import event.Teleport;

import java.io.File;

public class Main extends PluginBase
{
    private static Main instance;
    public static Main getInstance() {
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
        // Load the warps file, so we can start writting to it
        warps.reload();

        // Register event listeners
        getServer().getPluginManager().registerEvents(new Teleport(), this);
        // Register commands
        getServer().getCommandMap().register("setwarp", new Setwarp(text, warps));
        getServer().getCommandMap().register("delwarp", new Delwarp(text, warps));
        getServer().getCommandMap().register("warp", new Warp(text, warps));
        getServer().getCommandMap().register("warps", new Warps(text, warps));

        // Set command permissions
        getServer().getCommandMap().getCommand("setwarp").setPermission("command.setwarp");
        getServer().getCommandMap().getCommand("delwarp").setPermission("command.delwarp");
    }
}