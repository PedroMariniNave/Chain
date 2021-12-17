package com.zpedroo.voltzchain.listeners;

import com.zpedroo.voltzchain.data.PlayerData;
import com.zpedroo.voltzchain.managers.ArenaManager;
import com.zpedroo.voltzchain.managers.DataManager;
import com.zpedroo.voltzchain.utils.FileUtils;
import com.zpedroo.voltzchain.utils.config.Messages;
import com.zpedroo.voltzchain.utils.config.Settings;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerGeneralListeners implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(PlayerDeathEvent event) {
        if (!DataManager.getInstance().getCache().getFightingPlayers().contains(event.getEntity())) return;

        event.getDrops().clear();

        Player player = event.getEntity();
        Player killer = player.getKiller();

        ArenaManager.getInstance().updateItems(killer);
        ArenaManager.getInstance().leave(player);

        PlayerData playerData = DataManager.getInstance().load(player);
        PlayerData killerData = DataManager.getInstance().load(killer);

        playerData.addDeaths(1);
        killerData.addKills(1);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ArenaManager.getInstance().restoreInventory(player);
        DataManager.getInstance().save(player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (!DataManager.getInstance().getCache().getFightingPlayers().contains(event.getPlayer())) return;
        if (event.getPlayer().hasPermission(Settings.ADMIN_PERMISSION)) return;

        String executedCommand = event.getMessage().split(" ")[0].replace("/", "").toLowerCase();
        if (FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Whitelisted-Commands").contains(executedCommand)) return;
        if (StringUtils.equals(Settings.COMMAND, executedCommand)) return;
        if (Settings.ALIASES.contains(executedCommand)) return;

        Player player = event.getPlayer();

        event.setCancelled(true);
        player.sendMessage(Messages.BLACKLISTED_COMMAND);
    }
}