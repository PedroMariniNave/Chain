package com.zpedroo.voltzchain.data;

import java.util.UUID;

public class PlayerData {

    private UUID uuid;
    private Integer kills;
    private Integer deaths;
    private Double kdr;
    private Boolean update;

    public PlayerData(UUID uuid, Integer kills, Integer deaths) {
        this.uuid = uuid;
        this.kills = kills;
        this.deaths = deaths;
        this.update = false;
        this.calculateKDR();
    }

    public UUID getUUID() {
        return uuid;
    }

    public Integer getKills() {
        return kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public Double getKDR() {
        return kdr;
    }

    public String getFormattedKDR() {
        return String.format("%.2f", kdr);
    }

    private void calculateKDR() {
        this.kdr = deaths > 0 ? (double) kills / deaths : kills;
    }

    public Boolean isQueueUpdate() {
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

    public void setUpdate(Boolean update) {
        this.update = update;
    }
}