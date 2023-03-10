package net.mov51.aspenhardcore.util.database;

import net.mov51.aspenhardcore.util.PlayPeriod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.mov51.aspenhardcore.AspenHardCore.plugin;

public class DatabaseConnector {
    static String url = "jdbc:sqlite:" + plugin.getDataFolder() + "/database.db";
    static Connection databaseConn = null;

    public static void connect(){
        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                databaseConn = conn;
                System.out.println("A new database has been created.");
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void close(){
        try {
            if (databaseConn != null) {
                databaseConn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void makeTables(){
        if (databaseConn != null) {
            try (Statement stmt = databaseConn.createStatement()) {
                String playPeriodsTable = "CREATE TABLE IF NOT EXISTS `play-periods` (`UUID` TEXT NOT NULL, `time-in` INTEGER NOT NULL, `day` INTEGER NOT NULL, `time-out` INTEGER, PRIMARY KEY(`UUID`,`time-in`))";
                String daySumsTable = "CREATE TABLE IF NOT EXISTS `day-sums` (`UUID`TEXT NOT NULL,`day` INTEGER NOT NULL, `play-period-sum` INTEGER NOT NULL, PRIMARY KEY(`UUID`,`day`))";
                stmt.executeUpdate(playPeriodsTable);
                stmt.executeUpdate(daySumsTable);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void addNewPlayPeriod(UUID uuid, long timeIn, long day){
        String sql = "INSERT INTO `play-periods`(`UUID`,`time-in`,`day`) VALUES(?,?,?)";
        if (databaseConn != null) {
            try (PreparedStatement pstmt = databaseConn.prepareStatement(sql)) {
                pstmt.setString(1, uuid.toString());
                pstmt.setLong(2, timeIn);
                pstmt.setLong(3, day);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Adding new play period for " + uuid.toString() + " at " + timeIn + " on day " + day);
                System.out.println(e.getMessage());
            }
        }
    }
    public static List<PlayPeriod> getAllActivePlayPeriods(){
        String sql = "SELECT * FROM `play-periods` WHERE `time-out` IS NULL";
        if (databaseConn != null) {
            try (PreparedStatement pstmt = databaseConn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                List<PlayPeriod> playPeriods = new ArrayList<>();
                while(rs.next()){
                    playPeriods.add(new PlayPeriod(rs.getLong("time-in"),rs.getLong("time-out"),UUID.fromString(rs.getString("UUID"))));
                }
                return playPeriods;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    public static void finishPlayPeriod(UUID uuid, long timeOut){
        String sql = "UPDATE `play-periods` SET `time-out` = ? WHERE `UUID` = ? AND `time-out` IS null";
        if (databaseConn != null) {
            try (PreparedStatement pstmt = databaseConn.prepareStatement(sql)) {
                pstmt.setLong(1, timeOut);
                pstmt.setString(2, uuid.toString());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Finishing play period for " + uuid);
                System.out.println(e.getMessage());
            }
        }
    }
    public static PlayPeriod getLastPlayPeriod(UUID uuid){
        String sql = "SELECT * FROM `play-periods` WHERE `UUID` = ? ORDER BY `time-in` DESC LIMIT 1";
        if (databaseConn != null) {
            try (PreparedStatement pstmt = databaseConn.prepareStatement(sql)) {
                pstmt.setString(1, uuid.toString());
                long timeIn;
                long timeOut;
                try (ResultSet rs = pstmt.executeQuery()) {
                    timeIn = -2;
                    timeOut = -2;
                    while (rs.next()) {
                        timeIn = rs.getLong("time-in");
                        timeOut = rs.getLong("time-out");
                    }
                }
                return new PlayPeriod(timeIn, timeOut);
            } catch (SQLException e) {
                System.out.println("Getting last play period for " + uuid.toString());
                System.out.println(e.getMessage());
            }
        }
        return new PlayPeriod(-1,-1);
    }
    public static void addNewDaySum(UUID uuid, long day, long playPeriodSum){
        String sql = "INSERT INTO `day-sums`(`UUID`,`day`,`play-period-sum`) VALUES(?,?,?)";
        if (databaseConn != null) {
            try (PreparedStatement pstmt = databaseConn.prepareStatement(sql)) {
                pstmt.setString(1, uuid.toString());
                pstmt.setLong(2, day);
                pstmt.setLong(3, playPeriodSum);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Adding new day sum for " + uuid.toString() + " on day " + day + " with sum " + playPeriodSum);
                System.out.println(e.getMessage());
            }
        }
    }

    public static long getSumOfTimeDay(UUID uuid, long day) {
        String sql = "SELECT * FROM `play-periods` WHERE `UUID` = ? AND `day` = ?";
        if (databaseConn != null) {
            try (PreparedStatement pstmt = databaseConn.prepareStatement(sql)) {
                pstmt.setString(1, uuid.toString());
                pstmt.setLong(2, day);
                long sum = 0;
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        sum += rs.getLong("time-out") - rs.getLong("time-in");
                    }
                }
                return sum;
            } catch (SQLException e) {
                System.out.println("Getting sum of time for " + uuid.toString() + " on day " + day);
                System.out.println(e.getMessage());
            }
        }
        return -1;
    }
    public static List<UUID> getUUIDsOnDay(long day){
        String sql = "SELECT `UUID` FROM `play-periods` WHERE `day` = ? GROUP BY `UUID`";
        if (databaseConn != null) {
            try (PreparedStatement pstmt = databaseConn.prepareStatement(sql)) {
                pstmt.setLong(1, day);
                ResultSet rs = pstmt.executeQuery();
                List<UUID> uuids = new ArrayList<>();
                while(rs.next()){
                    uuids.add(UUID.fromString(rs.getString("UUID")));
                }
                return uuids;
            } catch (SQLException e) {
                System.out.println("Getting UUIDs on day " + day);
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
}
