package com.zpedroo.voltzchain.utils.menu;

import com.zpedroo.voltzchain.data.PlayerData;
import com.zpedroo.voltzchain.managers.ArenaManager;
import com.zpedroo.voltzchain.managers.DataManager;
import com.zpedroo.voltzchain.utils.FileUtils;
import com.zpedroo.voltzchain.utils.builder.InventoryUtils;
import com.zpedroo.voltzchain.utils.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Menus {

    private static Menus instance;
    public static Menus getInstance() { return instance; }

    private InventoryUtils inventoryUtils;

    public Menus() {
        instance = this;
        this.inventoryUtils = new InventoryUtils();
    }

    public void openMainMenu(Player player) {
        FileUtils.Files file = FileUtils.Files.MAIN;

        String title = ChatColor.translateAlternateColorCodes('&', FileUtils.get().getString(file, "Inventory.title"));
        int size = FileUtils.get().getInt(file, "Inventory.size");

        Inventory inventory = Bukkit.createInventory(null, size, title);

        PlayerData data = DataManager.getInstance().load(player);

        String[] placeholders = new String[]{
                "{player}",
                "{kills}",
                "{deaths}",
                "{kdr}"
        };
        String[] replaces = new String[]{
                player.getName(),
                data.getKills().toString(),
                data.getDeaths().toString(),
                data.getFormattedKDR()
        };

        for (String str : FileUtils.get().getSection(file, "Inventory.items")) {
            ItemStack item = null;
            String action = null;
            if (FileUtils.get().getFile(file).get().contains("Inventory.items." + str + ".join") && FileUtils.get().getFile(file).get().contains("Inventory.items." + str + ".left")) {
                boolean fighting = DataManager.getInstance().getCache().getFightingPlayers().contains(player);

                String typeToGet = fighting ? "left" : "join";

                item = ItemBuilder.build(FileUtils.get().getFile(file).get(), "Inventory.items." + str + "." + typeToGet, placeholders, replaces).build();
                action = FileUtils.get().getString(file, "Inventory.items." + str + "." + typeToGet + ".action");
            } else {
                item = ItemBuilder.build(FileUtils.get().getFile(file).get(), "Inventory.items." + str, placeholders, replaces).build();
                action = FileUtils.get().getString(file, "Inventory.items." + str + ".action");
            }
            int slot = FileUtils.get().getInt(file, "Inventory.items." + str + ".slot");

            switch (action.toUpperCase()) {
                case "JOIN":
                    inventoryUtils.addAction(inventory, slot, () -> {
                        ArenaManager.getInstance().join(player);
                    }, InventoryUtils.ActionType.ALL_CLICKS);
                    break;
                case "LEFT":
                    inventoryUtils.addAction(inventory, slot, () -> {
                        ArenaManager.getInstance().left(player);
                    }, InventoryUtils.ActionType.ALL_CLICKS);
                    break;
                case "TOP":
                    inventoryUtils.addAction(inventory, slot, () -> {
                        openTopMenu(player);
                    }, InventoryUtils.ActionType.ALL_CLICKS);
                    break;
            }

            inventory.setItem(slot, item);
        }

        player.openInventory(inventory);
    }

    public void openTopMenu(Player player) {
        FileUtils.Files file = FileUtils.Files.TOP;

        String title = ChatColor.translateAlternateColorCodes('&', FileUtils.get().getString(file, "Inventory.title"));
        int size = FileUtils.get().getInt(file, "Inventory.size");

        Inventory inventory = Bukkit.createInventory(null, size, title);

        int pos = 0;
        String[] slots = FileUtils.get().getString(file, "Inventory.slots").replace(" ", "").split(",");

        for (PlayerData data : DataManager.getInstance().getCache().getTopKills()) {
            ItemStack item = ItemBuilder.build(FileUtils.get().getFile(FileUtils.Files.TOP).get(), "Item", new String[]{
                    "{player}",
                    "{pos}",
                    "{kills}",
                    "{deaths}",
                    "{kdr}"
            }, new String[]{
                    Bukkit.getOfflinePlayer(data.getUUID()).getName(),
                    String.valueOf(++pos),
                    data.getKills().toString(),
                    data.getDeaths().toString(),
                    data.getFormattedKDR()
            }).build();

            int slot = Integer.parseInt(slots[pos - 1]);

            inventoryUtils.addAction(inventory, slot, null, InventoryUtils.ActionType.ALL_CLICKS); // cancel click

            inventory.setItem(slot, item);
        }

        player.openInventory(inventory);
    }
}