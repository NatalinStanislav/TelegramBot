import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Set;

public class ScoreGuysBot extends TelegramLongPollingBot {
    private static final String TOKEN = Config.get().getBotToken();
    private static final String USER_NAME = Config.get().getBotUsername();
    private static final String PROXY_HOST = Config.get().getBotProxyHost();
    private static final Integer PROXY_PORT = Config.get().getBotProxyPort();
    private static final String PROXY_USER = Config.get().getBotProxyUser();
    private static final String PROXY_PASSWORD = Config.get().getBotProxyPassword();
    private static ScoreGuysBot instance;
    private static final DefaultBotOptions botOptions = getBotOptions();
    private static final long CHAT_ID = Config.get().getBotChatId();

    private ScoreGuysBot() {
        super(botOptions);
    }

    public static ScoreGuysBot getInstance() {
        if (instance == null) {
            instance = new ScoreGuysBot();
        }
        return instance;
    }

    private static DefaultBotOptions getBotOptions() {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(PROXY_USER, PROXY_PASSWORD.toCharArray());
            }
        });

        ApiContextInitializer.init();

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        botOptions.setProxyHost(PROXY_HOST);
        botOptions.setProxyPort(PROXY_PORT);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

        return botOptions;
    }

    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long id = update.getMessage().getChatId();

            if (id != CHAT_ID) {
                try {
                    execute(new SendMessage(id, "К сожалению, этот бот не для вас)"));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return;
            }

            if (text.startsWith("/add_")) {
                try {
                    Player addPlayer = DataBase.savePlayer(text.substring(5).trim());
                    execute(new SendMessage(CHAT_ID, "Вы добавили игрока: " + addPlayer.getName() + "\nЕго текущая статистика:\n" + addPlayer.prettyPrint()));
                } catch (IOException | TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (text.startsWith("/delete_")) {
                try {
                    if (DataBase.deletePlayer(text.substring(8).trim())) {
                        execute(new SendMessage(CHAT_ID, "Вы удалили игрока: " + text.substring(8) + "\nБольше его статистика не отслеживается"));
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (text.equals("/getAll")) {
                Set<Player> players = DataBase.getAllPlayers();
                try {
                    execute(new SendMessage(CHAT_ID, "Вы отслеживаете статистику следующих игроков:\n"));
                    if (players.isEmpty()) {
                        execute(new SendMessage(CHAT_ID, "Пусто."));
                    }
                    for (Player player : players) {
                        execute(new SendMessage(CHAT_ID, player.getName()));
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static long getChatId() {
        return CHAT_ID;
    }

    public String getBotUsername() {
        return USER_NAME;
    }

    public String getBotToken() {
        return TOKEN;
    }

    public void startListen() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
