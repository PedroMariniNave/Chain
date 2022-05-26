package com.zpedroo.voltzchain.listeners;

<<<<<<< HEAD
import com.zpedroo.voltzchain.VoltzChain;
=======
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
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
<<<<<<< HEAD
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerGeneralListeners implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        if (!ArenaManager.getInstance().isOnArena(event.getEntity())) return;
=======

public class PlayerGeneralListeners implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(PlayerDeathEvent event) {
        if (!DataManager.getInstance().getCache().getFightingPlayers().contains(event.getEntity())) return;
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1

        event.getDrops().clear();

        Player player = event.getEntity();
        Player killer = player.getKiller();

<<<<<<< HEAD
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
=======
        ArenaManager.getInstance().updateItems(killer);
        ArenaManager.getInstance().leave(player);

        PlayerData playerData = DataManager.getInstance().load(player);
        PlayerData killerData = DataManager.getInstance().load(killer);

        playerData.addDeaths(1);
        killerData.addKills(1);
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ArenaManager.getInstance().restoreInventory(player);
<<<<<<< HEAD
        DataManager.getInstance().savePlayerData(player);
=======
        DataManager.getInstance().save(player);
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
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