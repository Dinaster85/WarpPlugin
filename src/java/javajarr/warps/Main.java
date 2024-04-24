package javajarr.warps;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import javajarr.warps.command.Setwarp;
import javajarr.warps.command.Warp;
import javajarr.warps.command.Warps;
import javajarr.warps.event.Teleport;

import java.io.File;


public class Main extends PluginBase {
    private static Main instance;
    private Config config;
    private static Config translation;
    private String language_code;

    @SuppressWarnings("static-access")
    @Override
    public void onEnable() {
        instance = this;

        // Create resource file with default data
        this.saveDefaultConfig();this.config = new Config(new File(this.getDataFolder(), "config.yml"), Config.YAML);
        this.language_code = this.config.getString("language", "en");
        this.saveResource("translates/" + language_code + ".yml");
        this.translation = new Config(this.getDataFolder() + "/translates/" + language_code + ".yml", Config.YAML);

        // Initialize config objects
        Config warps = new Config(new File(getDataFolder(), "warps.yml"));
        // Load the warps file, so we can start writting to it
        warps.reload();

        // Register event listeners
        getServer().getPluginManager().registerEvents(new Teleport(), this);
        // Register commands
        getServer().getCommandMap().register("setwarp", new Setwarp(warps));
     

        getServer().getCommandMap().register("warp", new Warp(warps));
        getServer().getCommandMap().register("warps" , new Warps(warps));
    }

    
    public static Main getInstance() {
        return instance;
    }

    public static String getTranslate(String code) {
        return getTranslate(code, new String[]{});
    }
    public static String getTranslate(String code, String param) {
        return getTranslate(code, new String[]{param});
    }
    public static String getTranslate(String code, String[] params) {
        String message = translation.getString(code);
        if (message == null) {
            return "Translation not found for code: " + code;
        }
        for (int i = 0; i < params.length; i++) {
            message = message.replace("%" + (i + 1), params[i]);
        }
        return message;
    }
}