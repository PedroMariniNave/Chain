package com.zpedroo.voltzchain.listeners;

import com.zpedroo.voltzchain.VoltzChain;
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
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerGeneralListeners implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        if (!ArenaManager.getInstance().isOnArena(event.getEntity())) return;

        event.getDrops().clear();

        Player player = event.getEntity();
        Player killer = player.getKiller();

        player.spigot().respawn();

        if (ArenaManager.getInstance().isOnArena(killer)) {
            ArenaManager.getInstance().updateItems(killer);

            PlayerData killerData = DataManager.getInstance().getPlayerData(killer);
            killerData.addKills(1);
        }

        PlayerData playerData = DataManager.getInstance().getPlayerData(player);
        playerData.addDeaths(1);

        new BukkitRunnable() {
            @Override
            public void run() {
                ArenaManager.getInstance().leave(player);
            }
        }.runTaskLater(VoltzChain.get(), 0L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ArenaManager.getInstance().restoreInventory(player);
        DataManager.getInstance().savePlayerData(player);
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