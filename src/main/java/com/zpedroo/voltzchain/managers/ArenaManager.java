package com.zpedroo.voltzchain.managers;

import com.zpedroo.voltzchain.objects.ArenaSettings;
import com.zpedroo.voltzchain.utils.FileUtils;
import com.zpedroo.voltzchain.utils.config.Messages;
import com.zpedroo.voltzchain.utils.serialization.ItemSerialization;
import com.zpedroo.voltzchain.utils.serialization.LocationSerialization;
<<<<<<< HEAD
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
=======
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
<<<<<<< HEAD
import org.jetbrains.annotations.NotNull;
=======
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1

import java.util.HashSet;

public class ArenaManager extends DataManager {

    private static ArenaManager instance;
    public static ArenaManager getInstance() { return instance; }

<<<<<<< HEAD
    private final ArenaSettings arenaSettings = loadSettings();

    public ArenaManager() {
        instance = this;
=======
    private ArenaSettings arenaSettings;

    public ArenaManager() {
        instance = this;
        this.arenaSettings = loadSettings();
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
    }

    public void join(Player player) {
        if (getCache().getFightingPlayers().contains(player)) {
            player.sendMessage(Messages.ALREADY_ON_ARENA);
            return;
        }

        if (arenaSettings.getJoinLocation() == null) {
            player.sendMessage(Messages.JOIN_NOT_SET);
            return;
        }

        if (arenaSettings.getExitLocation() == null) {
            player.sendMessage(Messages.EXIT_NOT_SET);
            return;
        }

        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        getCache().getFightingPlayers().add(player);
        storeInventory(player);
        updateItems(player);

        player.sendMessage(Messages.SUCCESSFUL_JOIN);
        player.teleport(arenaSettings.getJoinLocation());
    }

    public void leave(Player player) {
        if (!getCache().getFightingPlayers().contains(player)) {
            player.sendMessage(Messages.IS_NOT_ON_ARENA);
            return;
        }

        getCache().getFightingPlayers().remove(player);
        restoreInventory(player);

        player.teleport(arenaSettings.getExitLocation());
    }

    public void updateItems(Player player) {
        if (player == null) return;
        
        if (arenaSettings.getInventoryItems() != null) player.getInventory().setContents(arenaSettings.getInventoryItems());
        if (arenaSettings.getArmor() != null) player.getInventory().setArmorContents(arenaSettings.getArmor());
    }

    public void storeInventory(Player player) {
        getCache().getStoredInventories().put(player, new StoredInventory(player.getInventory().getContents(), player.getInventory().getArmorContents()));
    }

    public void restoreInventory(Player player) {
        StoredInventory storedInventory = getCache().getStoredInventories().remove(player);
        if (storedInventory == null) return;

        player.getInventory().setContents(storedInventory.getContents());
        player.getInventory().setArmorContents(storedInventory.getArmor());
    }

    public void restoreAllInventories() {
        new HashSet<>(getCache().getStoredInventories().keySet()).forEach(this::restoreInventory);
    }

<<<<<<< HEAD
    public boolean isOnArena(@NotNull Player player) {
        return DataManager.getInstance().getCache().getFightingPlayers().contains(player);
    }

=======
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
    private ArenaSettings loadSettings() {
        String serializedInventory = FileUtils.get().getString(FileUtils.Files.CONFIG, "Serialized-Inventory");
        String serializedArmor = FileUtils.get().getString(FileUtils.Files.CONFIG, "Serialized-Armor");

        ItemStack[] inventoryItems = null;
        ItemStack[] armor = null;

        try {
            inventoryItems = ItemSerialization.itemStackArrayFromBase64(serializedInventory);
            armor = ItemSerialization.itemStackArrayFromBase64(serializedArmor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Location joinLocation = LocationSerialization.deserialize(FileUtils.get().getString(FileUtils.Files.CONFIG, "Join-Location"));
        Location exitLocation = LocationSerialization.deserialize(FileUtils.get().getString(FileUtils.Files.CONFIG, "Exit-Location"));

        return new ArenaSettings(inventoryItems, armor, joinLocation, exitLocation);
    }

    public ArenaSettings getSettings() {
        return arenaSettings;
    }

    public static class StoredInventory {

<<<<<<< HEAD
        private final ItemStack[] contents;
        private final ItemStack[] armor;
=======
        private ItemStack[] contents;
        private ItemStack[] armor;
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1

        public StoredInventory(ItemStack[] contents, ItemStack[] armor) {
            this.contents = contents;
            this.armor = armor;
        }

        public ItemStack[] getContents() {
            return contents;
        }

        public ItemStack[] getArmor() {
            return armor;
        }
    }
}