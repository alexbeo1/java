package ru.javawebinar.webapp;

import ru.javawebinar.webapp.storage.IStorage;
import ru.javawebinar.webapp.storage.XmlFileStorage;

import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * GKislin
 * 01.02.2015.
 */
public class WebAppConfig {
    private static final WebAppConfig INSTANCE = new WebAppConfig();

    private IStorage storage;

    public static WebAppConfig get() {
        return INSTANCE;
    }

    public IStorage getStorage() {
        return storage;
    }

    private WebAppConfig() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
            storage = new XmlFileStorage("C:\\Java\\webapp5\\file_storage");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
