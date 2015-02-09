package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.WebAppException;
import ru.javawebinar.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GKislin
 * 23.01.2015.
 */
public abstract class FileStorage extends AbstractStorage<File> {
    private File dir;

    public FileStorage(String path) {
        this.dir = new File(path);
        if (!dir.isDirectory() || !dir.canWrite())
            throw new IllegalArgumentException("'" + path + "' is not directory or is not writable");
    }

    @Override
    protected void doClear() {
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    protected File getContext(String fileName) {
        return new File(dir, fileName);
    }

    @Override
    protected boolean exist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume r) {
        try {
            if(!file.createNewFile()){
                throw new WebAppException("Couldn't create file " + file.getAbsolutePath(), r);
            }
        } catch (IOException e) {
            throw new WebAppException("Couldn't create file " + file.getAbsolutePath(), r, e);
        }
        write(file, r);
    }

    protected void write(File file, Resume r) {
        try {
            write(new FileOutputStream(file), r);
        } catch (IOException e) {
            throw new WebAppException("Couldn't write file " + file.getAbsolutePath(), r, e);
        }
    }

    protected Resume read(File file) {
        try {
            return read(new FileInputStream(file));
        } catch (IOException e) {
            throw new WebAppException("Couldn't read file " + file.getAbsolutePath(), e);
        }
    }

    abstract protected void write(OutputStream os, Resume r) throws IOException;

    abstract protected Resume read(InputStream is) throws IOException;

    @Override
    protected void doUpdate(File file, Resume r) {
        write(file, r);
    }

    @Override
    protected Resume doLoad(File file) {
        return read(file);
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) throw new WebAppException("File " + file.getAbsolutePath() + " can not be deleted");
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] files = dir.listFiles();
        if (files == null) return Collections.emptyList();
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) list.add(read(file));
        return list;
    }

    @Override
    public int size() {
        String[] list = dir.list();
        if (list == null) throw new WebAppException("Couldn't read directory " + dir.getAbsolutePath());
        return list.length;
    }
}
