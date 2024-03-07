package org.example;

public class TaskService {
    private final ClanService clans;

    public TaskService(ClanService clans) {
        this.clans = clans;
    }

    public void completeTask(long clanId, long taskId) {
        int goldReward = 50; // предположим, что задание приносит 50 золота

        Clan clan = clans.getClan(clanId);
        clan.addGold(goldReward);
    }
}
