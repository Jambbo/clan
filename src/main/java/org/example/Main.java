package org.example;

public class Main {
    public static void main(String[] args) {
        Clan clan = new Clan(1, "Example Clan", 100); // Создаем экземпляр клана с идентификатором 1, именем "Example Clan" и 100 золота
        System.out.println("Initial gold: " + clan.getGold());

        clan.addGold(50);
        System.out.println("Gold after adding: " + clan.getGold());

        clan.reduceGold(30);
        System.out.println("Gold after reducing: " + clan.getGold());

        clan.closeConnection();
    }
}
