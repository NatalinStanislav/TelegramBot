import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String DB_PROPS = "/db/postgres.properties";
    private static final String BOT_PROPS = "/bot/bot.properties";
    private static final Config INSTANCE = new Config();

    private final String databaseUrl;
    private final String databaseUsername;
    private final String databasePassword;

    private final String botToken;
    private final String botUsername;
    private final String botProxyHost;
    private final int botProxyPort;
    private final String botProxyUser;
    private final String botProxyPassword;
    private final long botChatId;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream dbStream = Config.class.getResourceAsStream(DB_PROPS);
             InputStream botStream = Config.class.getResourceAsStream(BOT_PROPS))
        {
            Properties dbProps = new Properties();
            dbProps.load(dbStream);
            databaseUrl = dbProps.getProperty("database.url");
            databaseUsername = dbProps.getProperty("database.username");
            databasePassword = dbProps.getProperty("database.password");

            Properties botProps = new Properties();
            botProps.load(botStream);
            botToken = botProps.getProperty("bot.token");
            botUsername = botProps.getProperty("bot.username");
            botProxyHost = botProps.getProperty("bot.proxy.host");
            botProxyPort = Integer.parseInt(botProps.getProperty("bot.proxy.port"));
            botProxyUser = botProps.getProperty("bot.proxy.user");
            botProxyPassword = botProps.getProperty("bot.proxy.password");
            botChatId = Long.parseLong(botProps.getProperty("bot.chatid"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + DB_PROPS);
        }
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotProxyHost() {
        return botProxyHost;
    }

    public int getBotProxyPort() {
        return botProxyPort;
    }

    public String getBotProxyUser() {
        return botProxyUser;
    }

    public String getBotProxyPassword() {
        return botProxyPassword;
    }

    public long getBotChatId() {
        return botChatId;
    }
}