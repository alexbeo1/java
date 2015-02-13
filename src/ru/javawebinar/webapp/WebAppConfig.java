package ru.javawebinar.webapp;

import ru.javawebinar.webapp.storage.IStorage;
import ru.javawebinar.webapp.storage.XmlFileStorage;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.LogManager;

/**
 * GKislin
 * 01.02.2015.
 */
public class WebAppConfig {
    private static final WebAppConfig INSTANCE = new WebAppConfig();

    private IStorage storage;
    private Properties appProps;

    public static WebAppConfig get() {
        return INSTANCE;
    }

    public IStorage getStorage() {
        return storage;
    }

    private WebAppConfig() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("logging.properties");
             InputStream webAppIs = getClass().getClassLoader().getResourceAsStream("webapp.properties")
        ) {
            LogManager.getLogManager().readConfiguration(is);

            appProps = new Properties();
            appProps.load(webAppIs);
            storage = new XmlFileStorage(appProps.getProperty("storage.dir"));
            appProps.getProperty("db.url");
            appProps.getProperty("db.user");
            appProps.getProperty("db.password");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
