package com.zpedroo.voltzchain.objects;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class ArenaSettings {

    private ItemStack[] inventoryItems;
    private ItemStack[] armor;
    private Location joinLocation;
    private Location exitLocation;

    public ArenaSettings(ItemStack[] inventoryItems, ItemStack[] armor, Location joinLocation, Location exitLocation) {
        this.inventoryItems = inventoryItems;
        this.armor = armor;
        this.joinLocation = joinLocation;
        this.exitLocation = exitLocation;
    }

    public ItemStack[] getInventoryItems() {
        return inventoryItems;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public Location getJoinLocation() {
        return joinLocation;
    }

    public Location getExitLocation() {
        return exitLocation;
    }

    public void setInventoryItems(ItemStack[] inventory) {
        this.inventoryItems = inventory;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public void setJoinLocation(Location joinLocation) {
        this.joinLocation = joinLocation;
    }

    public void setExitLocation(Location exitLocation) {
        this.exitLocation = exitLocation;
    }
}