package org.example;

public interface ClanService {
    Clan getClan(long clanId);
    void saveClan(Clan clan);
}