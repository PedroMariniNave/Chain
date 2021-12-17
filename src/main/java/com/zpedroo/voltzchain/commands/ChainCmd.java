package com.zpedroo.voltzchain.commands;

import com.zpedroo.voltzchain.managers.ArenaManager;
import com.zpedroo.voltzchain.utils.FileUtils;
import com.zpedroo.voltzchain.utils.config.Messages;
import com.zpedroo.voltzchain.utils.menu.Menus;
import com.zpedroo.voltzchain.utils.serialization.ItemSerialization;
import com.zpedroo.voltzchain.utils.serialization.LocationSerialization;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.zpedroo.voltzchain.utils.config.Settings.*;

public class ChainCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (args.length >= 1) {
            Location location = null;
            String serializedLocation = null;
            FileUtils.FileManager file = null;

            switch (args[0].toUpperCase()) {
                case "JOIN":
                case "ENTRAR":
                    if (player == null) break;

                    ArenaManager.getInstance().join(player);
                    return true;
                case "LEAVE":
                case "SAIR":
                    if (player == null) break;

                    ArenaManager.getInstance().leave(player);
                    return true;
                case "TOP":
                    if (player == null) break;

                    Menus.getInstance().openTopMenu(player);
                    return true;
                case "SETJOIN":
                    if (player == null) break;
                    if (!player.hasPermission(ADMIN_PERMISSION)) break;

                    location = player.getLocation();
                    serializedLocation = LocationSerialization.serialize(location);

                    ArenaManager.getInstance().getSettings().setJoinLocation(location);

                    file = FileUtils.get().getFile(FileUtils.Files.CONFIG);
                    file.get().set("Join-Location", serializedLocation);
                    file.save();

                    player.sendMessage(Messages.LOCATION_SET);
                    return true;
                case "SETEXIT":
                    if (player == null) break;
                    if (!player.hasPermission(ADMIN_PERMISSION)) break;

                    location = player.getLocation();
                    serializedLocation = LocationSerialization.serialize(location);

                    ArenaManager.getInstance().getSettings().setExitLocation(location);

                    file = FileUtils.get().getFile(FileUtils.Files.CONFIG);
                    file.get().set("Exit-Location", serializedLocation);
                    file.save();

                    player.sendMessage(Messages.LOCATION_SET);
                    return true;
                case "SETINV":
                    if (player == null) break;
                    if (!player.hasPermission(ADMIN_PERMISSION)) break;

                    String serializedInventory = ItemSerialization.itemStackArrayToBase64(player.getInventory().getContents());
                    String serializedArmor = ItemSerialization.itemStackArrayToBase64(player.getInventory().getArmorContents());

                    ArenaManager.getInstance().getSettings().setInventoryItems(player.getInventory().getContents());
                    ArenaManager.getInstance().getSettings().setArmor(player.getInventory().getArmorContents());

                    file = FileUtils.get().getFile(FileUtils.Files.CONFIG);
                    file.get().set("Serialized-Inventory", serializedInventory);
                    file.get().set("Serialized-Armor", serializedArmor);
                    file.save();

                    player.getInventory().clear();
                    return true;
            }
        }

        if (player == null) return true;

        Menus.getInstance().openMainMenu(player);
        return false;
    }
}