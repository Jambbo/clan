package org.example;

import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Clan {
    private long id;
    private String name;
    private AtomicInteger gold;
    private final Lock lock;
    private Connection connection;


    public Clan(long id, String name, int gold) {
        this.id = id;
        this.name = name;
        this.gold = new AtomicInteger(gold);
        this.lock = new ReentrantLock();
        this.connection = createConnection();
        createTable();
    }

    private Connection createConnection() {
        Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    private void createTable() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS clan_actions (id INT AUTO_INCREMENT PRIMARY KEY, clan_id INT, action VARCHAR(255), amount INT, previous_gold INT, current_gold INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return gold.get();
    }

    public void addGold(int amount) {
        lock.lock();
        try {
            int previousGold = gold.get();
            gold.addAndGet(amount);
            logAction("Added",amount,previousGold,gold.get());
        } finally {
            lock.unlock();
        }
    }

    public void reduceGold(int amount) {
        lock.lock();
        try {
            int previousGold = gold.get();
            gold.addAndGet(-amount);
            logAction("Reduced",amount,previousGold,gold.get());
        } finally {
            lock.unlock();
        }
    }
    private void logAction(String action, int amount, int previousGold, int currentGold) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO clan_actions (clan_id, action, amount, previous_gold, current_gold) VALUES (?, ?, ?, ?, ?)");
            stmt.setLong(1, id);
            stmt.setString(2, action);
            stmt.setInt(3, amount);
            stmt.setInt(4, previousGold);
            stmt.setInt(5, currentGold);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void closeConnection(){
        try{
            if(connection!=null){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
