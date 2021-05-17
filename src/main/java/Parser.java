import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Parser {
    public static Player getPlayer(String playerId) throws IOException {
        ArrayList <String> playerStats = new ArrayList<>();
        playerStats.add(playerId);

        Document doc = Jsoup.connect(String.format("https://spbhl.ru/Player?PlayerID=%s",playerId)).get();
        playerStats.add(doc.getElementsByTag("title").html().split(" · ")[1]);
        Elements rows = doc.getElementById("CareerPlayerGridView").select("tr");
        Elements scores = rows.get(1).select("td");
        for (Element element: scores) {
            playerStats.add(element.html());
        }
        return new Player(playerStats.get(0), playerStats.get(1), playerStats.get(4), playerStats.get(6), playerStats.get(7), playerStats.get(9));
    }

    public static Set<Player> getPlayers(Set<String> playerIds) throws IOException {
        Set<Player> players = new HashSet<>();
        for (String id: playerIds) {
            players.add(getPlayer(id));
        }
        return players;
    }
}
