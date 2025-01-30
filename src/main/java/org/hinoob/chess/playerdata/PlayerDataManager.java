package org.hinoob.chess.playerdata;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.get(uuid);
    }

    public PlayerData createPlayerData(UUID uuid) {
        PlayerData playerData = new PlayerData(uuid);
        playerDataMap.put(uuid, playerData);
        return playerData;
    }
}
