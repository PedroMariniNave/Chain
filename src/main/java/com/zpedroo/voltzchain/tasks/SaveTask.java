package com.zpedroo.voltzchain.tasks;

import com.zpedroo.voltzchain.managers.DataManager;
import com.zpedroo.voltzchain.mysql.DBConnection;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.zpedroo.voltzchain.utils.config.Settings.SAVE_INTERVAL;

public class SaveTask extends BukkitRunnable {

    public SaveTask(Plugin plugin) {
        this.runTaskTimerAsynchronously(plugin, SAVE_INTERVAL * 20, SAVE_INTERVAL * 20);
    }

    @Override
    public void run() {
        DataManager.getInstance().saveAllPlayersData();
        DataManager.getInstance().getCache().setTopKills(DBConnection.getInstance().getDBManager().getTop());
    }
}