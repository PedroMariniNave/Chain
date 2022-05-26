package com.zpedroo.voltzchain.managers;

import com.zpedroo.voltzchain.data.PlayerData;
<<<<<<< HEAD
import com.zpedroo.voltzchain.managers.cache.DataCache;
=======
import com.zpedroo.voltzchain.data.cache.DataCache;
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
import com.zpedroo.voltzchain.mysql.DBConnection;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class DataManager {

    private static DataManager instance;
    public static DataManager getInstance() { return instance; }

<<<<<<< HEAD
    private final DataCache dataCache = new DataCache();

    public DataManager() {
        instance = this;
    }

    public PlayerData getPlayerData(Player player) {
=======
    private DataCache dataCache;

    public DataManager() {
        instance = this;
        this.dataCache = new DataCache();
    }

    public PlayerData load(Player player) {
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
        PlayerData data = dataCache.getPlayerData().get(player);
        if (data == null) {
            data = DBConnection.getInstance().getDBManager().loadData(player);
            dataCache.getPlayerData().put(player, data);
        }

        return data;
    }

<<<<<<< HEAD
    public void savePlayerData(Player player) {
        PlayerData data = dataCache.getPlayerData().get(player);
        if (data == null || !data.isQueueUpdate()) return;
=======
    public void save(Player player) {
        PlayerData data = dataCache.getPlayerData().get(player);
        if (data == null) return;
        if (!data.isQueueUpdate()) return;
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1

        DBConnection.getInstance().getDBManager().saveData(data);
        data.setUpdate(false);
    }

<<<<<<< HEAD
    public void saveAllPlayersData() {
        new HashSet<>(dataCache.getPlayerData().keySet()).forEach(this::savePlayerData);
=======
    public void saveAll() {
        new HashSet<>(dataCache.getPlayerData().keySet()).forEach(this::save);
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
    }

    public DataCache getCache() {
        return dataCache;
    }
}