package com.zpedroo.voltzchain.utils.config;

import com.zpedroo.voltzchain.utils.FileUtils;
import org.bukkit.ChatColor;

public class Messages {

    public static final String ALREADY_ON_ARENA = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.already-on-arena"));

    public static final String IS_NOT_ON_ARENA = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.is-not-on-arena"));

    public static final String SUCCESSFUL_JOIN = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.successful-join"));

    public static final String LOCATION_SET = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.location-set"));

    public static final String JOIN_NOT_SET = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.join-not-set"));

    public static final String EXIT_NOT_SET = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.exit-not-set"));

    public static final String BLACKLISTED_COMMAND = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.blacklisted-command"));

    private static String getColored(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}