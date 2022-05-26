package com.zpedroo.voltzchain.utils.menu;

import com.zpedroo.voltzchain.data.PlayerData;
import com.zpedroo.voltzchain.managers.ArenaManager;
import com.zpedroo.voltzchain.managers.DataManager;
import com.zpedroo.voltzchain.utils.FileUtils;
import com.zpedroo.voltzchain.utils.builder.InventoryBuilder;
import com.zpedroo.voltzchain.utils.builder.InventoryUtils;
import com.zpedroo.voltzchain.utils.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Menus extends InventoryUtils {

    private static Menus instance;
    public static Menus getInstance() { return instance; }

    public Menus() {
        instance = this;
    }

    public void openMainMenu(Player player) {
        FileUtils.Files file = FileUtils.Files.MAIN;

        String title = ChatColor.translateAlternateColorCodes('&', FileUtils.get().getString(file, "Inventory.title"));
        int size = FileUtils.get().getInt(file, "Inventory.size");

        InventoryBuilder inventory = new InventoryBuilder(title, size);

<<<<<<< HEAD
        PlayerData data = DataManager.getInstance().getPlayerData(player);
=======
        PlayerData data = DataManager.getInstance().load(player);
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1

        String[] placeholders = new String[]{
                "{player}",
                "{kills}",
                "{deaths}",
                "{kdr}"
        };
        String[] replaces = new String[]{
                player.getName(),
                String.valueOf(data.getKills()),
                String.valueOf(data.getDeaths()),
                data.getFormattedKDR()
        };

        for (String str : FileUtils.get().getSection(file, "Inventory.items")) {
            ItemStack item = null;
            String action = null;
<<<<<<< HEAD
            if (FileUtils.get().getFile(file).getFileConfiguration().contains("Inventory.items." + str + ".join") && FileUtils.get().getFile(file).getFileConfiguration().contains("Inventory.items." + str + ".leave")) {
=======
            if (FileUtils.get().getFile(file).get().contains("Inventory.items." + str + ".join") && FileUtils.get().getFile(file).get().contains("Inventory.items." + str + ".leave")) {
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
                boolean fighting = DataManager.getInstance().getCache().getFightingPlayers().contains(player);

                String typeToGet = fighting ? "leave" : "join";

<<<<<<< HEAD
                item = ItemBuilder.build(FileUtils.get().getFile(file).getFileConfiguration(), "Inventory.items." + str + "." + typeToGet, placeholders, replaces).build();
                action = FileUtils.get().getString(file, "Inventory.items." + str + "." + typeToGet + ".action");
            } else {
                item = ItemBuilder.build(FileUtils.get().getFile(file).getFileConfiguration(), "Inventory.items." + str, placeholders, replaces).build();
=======
                item = ItemBuilder.build(FileUtils.get().getFile(file).get(), "Inventory.items." + str + "." + typeToGet, placeholders, replaces).build();
                action = FileUtils.get().getString(file, "Inventory.items." + str + "." + typeToGet + ".action");
            } else {
                item = ItemBuilder.build(FileUtils.get().getFile(file).get(), "Inventory.items." + str, placeholders, replaces).build();
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
                action = FileUtils.get().getString(file, "Inventory.items." + str + ".action");
            }
            int slot = FileUtils.get().getInt(file, "Inventory.items." + str + ".slot");

            final String finalAction = action;
            inventory.addItem(item, slot, () -> {
                switch (finalAction.toUpperCase()) {
                    case "JOIN":
                        ArenaManager.getInstance().join(player);
                        break;
                    case "LEAVE":
                        ArenaManager.getInstance().leave(player);
                        break;
                    case "TOP":
                        openTopMenu(player);
                        break;
                }
            }, ActionType.ALL_CLICKS);
        }

        inventory.open(player);
    }

    public void openTopMenu(Player player) {
        FileUtils.Files file = FileUtils.Files.TOP;

        String title = ChatColor.translateAlternateColorCodes('&', FileUtils.get().getString(file, "Inventory.title"));
        int size = FileUtils.get().getInt(file, "Inventory.size");

        InventoryBuilder inventory = new InventoryBuilder(title, size);

        int pos = 0;
        String[] slots = FileUtils.get().getString(file, "Inventory.slots").replace(" ", "").split(",");

        for (PlayerData data : DataManager.getInstance().getCache().getTopKills()) {
<<<<<<< HEAD
            ItemStack item = ItemBuilder.build(FileUtils.get().getFile(FileUtils.Files.TOP).getFileConfiguration(), "Item", new String[]{
=======
            ItemStack item = ItemBuilder.build(FileUtils.get().getFile(FileUtils.Files.TOP).get(), "Item", new String[]{
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
                    "{player}",
                    "{pos}",
                    "{kills}",
                    "{deaths}",
                    "{kdr}"
            }, new String[]{
                    Bukkit.getOfflinePlayer(data.getUUID()).getName(),
                    String.valueOf(++pos),
                    String.valueOf(data.getKills()),
                    String.valueOf(data.getDeaths()),
                    data.getFormattedKDR()
            }).build();

            int slot = Integer.parseInt(slots[pos - 1]);

            inventory.addItem(item, slot);
        }

        inventory.open(player);
    }
}