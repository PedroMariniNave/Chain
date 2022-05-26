package com.zpedroo.voltzchain;

import com.zpedroo.voltzchain.commands.ChainCmd;
import com.zpedroo.voltzchain.listeners.PlayerGeneralListeners;
import com.zpedroo.voltzchain.managers.ArenaManager;
import com.zpedroo.voltzchain.managers.DataManager;
import com.zpedroo.voltzchain.mysql.DBConnection;
import com.zpedroo.voltzchain.tasks.SaveTask;
import com.zpedroo.voltzchain.utils.FileUtils;
import com.zpedroo.voltzchain.utils.menu.Menus;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;

import static com.zpedroo.voltzchain.utils.config.Settings.*;

public class VoltzChain extends JavaPlugin {

    private static VoltzChain instance;
    public static VoltzChain get() { return instance; }

    public void onEnable() {
        instance = this;
        new FileUtils(this);

        if (!isMySQLEnabled(getConfig())) {
            getLogger().log(Level.SEVERE, "MySQL are disabled! You need to enable it.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        new DBConnection(getConfig());
        new DataManager();
        new ArenaManager();
        new Menus();
        new SaveTask(this);

<<<<<<< HEAD
        registerListeners();
        registerCommand(COMMAND, ALIASES, new ChainCmd());
=======
        registerCommand(COMMAND, ALIASES, new ChainCmd());
        registerListeners();

>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
    }

    public void onDisable() {
        if (!isMySQLEnabled(getConfig())) return;

        try {
            ArenaManager.getInstance().restoreAllInventories();
<<<<<<< HEAD
            DataManager.getInstance().saveAllPlayersData();
=======
            DataManager.getInstance().saveAll();
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
            DBConnection.getInstance().closeConnection();
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, "An error occurred while trying to save data!");
            ex.printStackTrace();
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerGeneralListeners(), this);
    }

    private void registerCommand(String command, List<String> aliases, CommandExecutor executor) {
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            PluginCommand pluginCmd = constructor.newInstance(command, this);
            pluginCmd.setAliases(aliases);
            pluginCmd.setExecutor(executor);

            Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
            commandMap.register(getName().toLowerCase(), pluginCmd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Boolean isMySQLEnabled(FileConfiguration file) {
        if (!file.contains("MySQL.enabled")) return false;

        return file.getBoolean("MySQL.enabled");
    }
}