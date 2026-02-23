package me.lightsmp.manager;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    public void setCooldown(UUID playerUUID, int seconds) {
        cooldowns.put(playerUUID, System.currentTimeMillis() + (seconds * 1000L));
    }

    public boolean isCooldownOver(UUID playerUUID) {
        return !cooldowns.containsKey(playerUUID) || System.currentTimeMillis() > cooldowns.get(playerUUID);
    }

    public long getRemaining(UUID playerUUID) {
        return (cooldowns.get(playerUUID) - System.currentTimeMillis()) / 1000;
    }
}
