package com.zpedroo.voltzchain.data;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private int kills;
    private int deaths;
    private double kdr;
    private boolean update;

    public PlayerData(UUID uuid, int kills, int deaths) {
        this.uuid = uuid;
        this.kills = kills;
        this.deaths = deaths;
        this.update = false;
        this.calculateKDR();
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public double getKDR() {
        return kdr;
    }

    public String getFormattedKDR() {
        return String.format("%.2f", kdr);
    }

    private void calculateKDR() {
        this.kdr = deaths > 0 ? (double) kills / deaths : kills;
    }

    public boolean isQueueUpdate() {
        return update;
    }

    public void addKills(Integer amount) {
        this.kills += amount;
        this.update = true;
        this.calculateKDR();
    }

    public void addDeaths(Integer amount) {
        this.deaths += amount;
        this.update = true;
        this.calculateKDR();
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}