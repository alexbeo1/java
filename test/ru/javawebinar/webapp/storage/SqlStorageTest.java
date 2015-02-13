package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.WebAppConfig;

public class SqlStorageTest extends AbstractStorageTest{
    {
        storage = WebAppConfig.get().getStorage();
    }

}