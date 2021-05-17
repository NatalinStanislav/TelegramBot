import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DataBase {
    public static Connection getConnection() throws SQLException {

        String username = Config.get().getDatabaseUsername();
        String password = Config.get().getDatabasePassword();
        String dbUrl = Config.get().getDatabaseUrl();

        return DriverManager.getConnection(dbUrl, username, password);
    }

    public static Player savePlayer(String playerId) throws IOException {
        Player player = Parser.getPlayer(playerId);
        try (PreparedStatement ps = getConnection().prepareStatement(
                "INSERT INTO player (id, name, total_score, goal, pass, penalty) VALUES (?,?,?,?,?,?)")) {
            ps.setString(1, player.getId());
            ps.setString(2, player.getName());
            ps.setString(3, player.getTotalScore());
            ps.setString(4, player.getGoal());
            ps.setString(5, player.getPass());
            ps.setString(6, player.getPenalty());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return player;
    }

    public static void updatePlayer(Player player) throws IOException {
        try (PreparedStatement ps = getConnection().prepareStatement(
                "UPDATE player SET total_score = ?, goal = ?, pass = ?, penalty = ? WHERE id = ?")) {
            ps.setString(1, player.getTotalScore());
            ps.setString(2, player.getGoal());
            ps.setString(3, player.getPass());
            ps.setString(4, player.getPenalty());
            ps.setString(5, player.getId());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static boolean deletePlayer(String playerName) {
        try (PreparedStatement ps = getConnection().prepareStatement(
                "DELETE FROM player WHERE name=?")) {
            ps.setString(1, playerName);
            ps.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static Set<Player> getAllPlayers() {
        Set<Player> players = new HashSet<>();
        try (PreparedStatement ps = getConnection().prepareStatement(
                "SELECT * FROM player")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                players.add(new Player(rs.getString("id"), rs.getString("name"), rs.getString("total_score"),
                        rs.getString("goal"), rs.getString("pass"), rs.getString("penalty")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return players;
    }

}
