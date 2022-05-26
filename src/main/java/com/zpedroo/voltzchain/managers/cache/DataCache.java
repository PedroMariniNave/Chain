package com.zpedroo.voltzchain.managers.cache;

import com.zpedroo.voltzchain.data.PlayerData;
import com.zpedroo.voltzchain.managers.ArenaManager;
import com.zpedroo.voltzchain.mysql.DBConnection;
import org.bukkit.entity.Player;

import java.util.*;

public class DataCache {

    private final Map<Player, PlayerData> playerData = new HashMap<>(32);
    private final Map<Player, ArenaManager.StoredInventory> storedInventories = new HashMap<>(16);
    private List<PlayerData> topKills = DBConnection.getInstance().getDBManager().getTop();
    private final Set<Player> fightingPlayers = new HashSet<>(16);;

    public Map<Player, PlayerData> getPlayerData() {
        return playerData;
    }

    public Map<Player, ArenaManager.StoredInventory> getStoredInventories() {
        return storedInventories;
    }

    public List<PlayerData> getTopKills() {
        return topKills;
    }

    public Set<Player> getFightingPlayers() {
        return fightingPlayers;
    }

    public void setTopKills(List<PlayerData> topKills) {
        this.topKills = topKills;
    }
}