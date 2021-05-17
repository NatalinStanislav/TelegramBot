import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, TelegramApiException {
        ScoreGuysBot bot = ScoreGuysBot.getInstance();
        bot.startListen();

        Set<Player> playersFromDataBase = DataBase.getAllPlayers();
        Set<String> playersIds = new HashSet<>();
        for (Player player: playersFromDataBase) {
            playersIds.add(player.getId());
        }
        Set<Player> playersFromSite = Parser.getPlayers(playersIds);
//        Set<Player> playersFromSite = getTestPlayers();

        notifyDifferences(playersFromDataBase, playersFromSite, bot);
    }

    private static void notifyDifferences(Set<Player> playersFromDataBase, Set<Player> playersFromSite, ScoreGuysBot bot) throws TelegramApiException, IOException {
        if(!playersFromDataBase.equals(playersFromSite)) {
            for (Player playerDB: playersFromDataBase) {
                for (Player playerFromSite: playersFromSite) {
                    if (playerDB.getId().equals(playerFromSite.getId()) && !playerDB.equals(playerFromSite)) {
                        if (!playerDB.getGoal().equals(playerFromSite.getGoal())) {
                            bot.execute(new SendMessage(ScoreGuysBot.getChatId(), playerFromSite.getName() + " забил гол!"));
                        }
                        if (!playerDB.getPass().equals(playerFromSite.getPass())) {
                            bot.execute(new SendMessage(ScoreGuysBot.getChatId(), playerFromSite.getName() + " отдал передачу!"));
                        }
                        if (!playerDB.getPenalty().equals(playerFromSite.getPenalty())) {
                            bot.execute(new SendMessage(ScoreGuysBot.getChatId(), playerFromSite.getName() + " заработал штраф!"));
                        }
                        bot.execute(new SendMessage(ScoreGuysBot.getChatId(), "Новая статистика: \n" + playerFromSite.prettyPrint()));
                        DataBase.updatePlayer(playerFromSite);
                    }
                }
            }
        }
    }

    private static Set<Player> getTestPlayers() {
        Set<Player> players = new HashSet<>();
        players.add(new Player("5ac4ee13-8ec9-4937-8651-f9d6a670ef79", "Тюшков Максим", "24", "12", "12", "12"));
        players.add(new Player("91a8a1d6-9bbe-4b88-a657-64e7986c9ef8", "Тюшков Александр", "4", "2", "2", "2"));
        return players;
    }
}
