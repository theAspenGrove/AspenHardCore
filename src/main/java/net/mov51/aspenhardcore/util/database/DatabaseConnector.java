package net.mov51.aspenhardcore.util.database;

import net.mov51.aspenhardcore.util.PlayPeriod;

import java.sql.*;
import java.util.UUID;

import static net.mov51.aspenhardcore.AspenHardCore.plugin;

public class DatabaseConnector {
    static String url = "jdbc:sqlite:" + plugin.getDataFolder() + "/database.db";

    public static void connect(){
        try(Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void makeTables(){
        Statement stmt;
        try(Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                stmt = conn.createStatement();
                String playPeriodsTable = "CREATE TABLE IF NOT EXISTS 'play-periods' (`UUID` TEXT NOT NULL, `time-in` INTEGER NOT NULL, `day` INTEGER NOT NULL, `time-out` INTEGER, PRIMARY KEY(`UUID`,`time-in`))";
                String daySumsTable ="CREATE TABLE IF NOT EXISTS `day-sums` (`UUID`TEXT NOT NULL,`day` INTEGER NOT NULL, `play-period-sum` INTEGER NOT NULL, PRIMARY KEY(`UUID`,`day`))";
                stmt.executeUpdate(playPeriodsTable);
                stmt.executeUpdate(daySumsTable);
                stmt.close();
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addNewPlayPeriod(UUID uuid, long timeIn, long day){
        String sql = "INSERT INTO 'play-periods'('UUID','time-in','day') VALUES(?,?,?)";
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,uuid.toString());
            pstmt.setLong(2,timeIn);
            pstmt.setLong(3,day);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Adding new play period for " + uuid.toString() + " at " + timeIn + " on day " + day);
            System.out.println(e.getMessage());
        }
    }
    public static void getActivePlayPeriod(UUID uuid){
        String sql = "SELECT * FROM `play-periods` WHERE `UUID` = ? AND `time-out` IS null";
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,uuid.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getLong("time-in") + "\t" +
                        rs.getLong("day"));
            }
        } catch (SQLException e) {
            System.out.println("Finishing play period for " + uuid);
            System.out.println(e.getMessage());
        }
    }
    public static void finishPlayPeriod(UUID uuid, long timeOut){
        String sql = "UPDATE `play-periods` SET `time-out` = ? WHERE `UUID` = ? AND `time-out` IS null";
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setLong(1,timeOut);
            pstmt.setString(2,uuid.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Finishing play period for " + uuid);
            System.out.println(e.getMessage());
        }
    }
    public static PlayPeriod getLastPlayPeriod(UUID uuid){
        String sql = "SELECT * FROM `play-periods` WHERE `UUID` = ? ORDER BY `time-in` DESC LIMIT 1";
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,uuid.toString());
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
            return new PlayPeriod(timeIn,timeOut);
        } catch (SQLException e) {
            System.out.println("Getting last play period for " + uuid.toString());
            System.out.println(e.getMessage());
        }
        return new PlayPeriod(-1,-1);
    }

    public static long getSumOfTimeDay(UUID uuid, long day){
        //get each play period for the day
        //add the time in and time out
        //return the sum
        String sql = "SELECT * FROM `play-periods` WHERE `UUID` = ? AND `day` = ?";
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,uuid.toString());
            pstmt.setLong(2,day);
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
        return -1;
    }

}
