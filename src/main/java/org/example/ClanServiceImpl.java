package org.example;

import java.util.HashMap;
import java.util.Map;

public class ClanServiceImpl implements ClanService {
    private Map<Long, Clan> clanMap;

    public ClanServiceImpl() {
        this.clanMap = new HashMap<>();
    }

    @Override
    public Clan getClan(long clanId) {
        return clanMap.get(clanId);
    }

    @Override
    public void saveClan(Clan clan) {
        clanMap.put(clan.getId(), clan);
    }
}