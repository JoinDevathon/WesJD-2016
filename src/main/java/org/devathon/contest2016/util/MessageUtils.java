package org.devathon.contest2016.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MessageUtils {

    public static void broadcastMessage(String... messages) {
        Arrays.stream(messages).forEach(Bukkit::broadcastMessage);
    }

    public static void sendMessage(Player player, String... messages) {
        Arrays.stream(messages).forEach(player::sendMessage);
    }

}
