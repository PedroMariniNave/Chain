package com.zpedroo.voltzchain.data.cache;

import com.zpedroo.voltzchain.data.PlayerData;
import com.zpedroo.voltzchain.managers.ArenaManager;
import org.bukkit.entity.Player;

import java.util.*;

public class DataCache {

    private Map<Player, PlayerData> playerData;
    private Map<Player, ArenaManager.StoredInventory> storedInventories;
    private List<PlayerData> topKills;
    private Set<Player> fightingPlayers;

    public DataCache() {
        this.playerData = new HashMap<>(128);
        this.storedInventories = new HashMap<>(16);
        this.topKills = new ArrayList<>(10);
        this.fightingPlayers = new HashSet<>(16);
    }

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
}