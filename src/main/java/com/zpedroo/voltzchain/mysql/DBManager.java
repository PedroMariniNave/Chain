package com.zpedroo.voltzchain.mysql;

import com.zpedroo.voltzchain.data.PlayerData;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;

public class DBManager {

    public void saveData(PlayerData data) {
<<<<<<< HEAD
        executeUpdate("REPLACE INTO `" + DBConnection.TABLE + "` (`uuid`, `kills`, `deaths`) VALUES " +
                "('" + data.getUUID().toString() + "', " +
                "'" + data.getKills() + "', " +
                "'" + data.getDeaths() + "');");
=======
        if (contains(data.getUUID().toString(), "uuid")) {
            String query = "UPDATE `" + DBConnection.TABLE + "` SET" +
                    "`uuid`='" + data.getUUID().toString() + "', " +
                    "`kills`='" + data.getKills() + "', " +
                    "`deaths`='" + data.getDeaths() + "' " +
                    "WHERE `uuid`='" + data.getUUID().toString() + "';";
            executeUpdate(query);
            return;
        }

        String query = "INSERT INTO `" + DBConnection.TABLE + "` (`uuid`, `kills`, `deaths`) VALUES " +
                "('" + data.getUUID().toString() + "', " +
                "'" + data.getKills() + "', " +
                "'" + data.getDeaths() + "');";
        executeUpdate(query);
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
    }

    public PlayerData loadData(Player player) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String query = "SELECT * FROM `" + DBConnection.TABLE + "` WHERE `uuid`='" + player.getUniqueId().toString() + "';";

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();

            if (result.next()) {
                UUID uuid = UUID.fromString(result.getString(1));
<<<<<<< HEAD
                int kills = result.getInt(2);
                int deaths = result.getInt(3);
=======
                Integer kills = result.getInt(2);
                Integer deaths = result.getInt(3);
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1

                return new PlayerData(uuid, kills, deaths);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection, result, preparedStatement, null);
        }

        return new PlayerData(player.getUniqueId(), 0, 0);
    }

    public List<PlayerData> getTop() {
        List<PlayerData> top = new ArrayList<>(10);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String query = "SELECT * FROM `" + DBConnection.TABLE + "` ORDER BY `kills` DESC LIMIT 10;";

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();

            while (result.next()) {
                UUID uuid = UUID.fromString(result.getString(1));
                int kills = result.getInt(2);
                int deaths = result.getInt(3);

                top.add(new PlayerData(uuid, kills, deaths));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection, result, preparedStatement, null);
        }

        return top;
    }

<<<<<<< HEAD
=======
    private Boolean contains(String value, String column) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String query = "SELECT `" + column + "` FROM `" + DBConnection.TABLE + "` WHERE `" + column + "`='" + value + "';";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();
            return result.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection, result, preparedStatement, null);
        }

        return false;
    }

>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
    private void executeUpdate(String query) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection, null, null, statement);
        }
    }

    private void closeConnection(Connection connection, ResultSet resultSet, PreparedStatement preparedStatement, Statement statement) {
        try {
            if (connection != null) connection.close();
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (statement != null) statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS `" + DBConnection.TABLE + "` (`uuid` VARCHAR(255) NOT NULL, `kills` INTEGER NOT NULL, `deaths` INTEGER NOT NULL, PRIMARY KEY(`uuid`));";
        executeUpdate(query);
    }

    private Connection getConnection() throws SQLException {
        return DBConnection.getInstance().getConnection();
    }
}