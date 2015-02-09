package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 23.01.2015.
 */
public class DataStreamFileStorage extends FileStorage {

    public DataStreamFileStorage(String path) {
        super(path);
    }

    @Override
    protected void write(OutputStream os, Resume resume) throws IOException {
        try (final DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            dos.writeUTF(resume.getLocation());
            dos.writeUTF(resume.getHomePage());
            Map<ContactType, String> contacts = resume.getContacts();

            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeInt(entry.getKey().ordinal());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getValue());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((MultiTextSection) section).getValues(), dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeCollection(dos, ((OrganizationSection) section).getValues(), (org) -> {
                            dos.writeUTF(org.getLink().getName());
                            dos.writeUTF(org.getLink().getUrl());
                            writeCollection(dos, org.getPeriods(), period -> {
                                DataStreamFileStorage.this.writeLocalDate(dos, period.getStartDate());
                                DataStreamFileStorage.this.writeLocalDate(dos, period.getEndDate());
                                dos.writeUTF(period.getPosition());
                                dos.writeUTF(period.getContent());
                            });
                        });
                        break;
                }
            }
        }
    }

    @Override
    protected Resume read(InputStream is) throws IOException {
        Resume r = new Resume();
        try (DataInputStream dis = new DataInputStream(is)) {
            r.setUuid(dis.readUTF());
            r.setFullName(dis.readUTF());
            r.setLocation(dis.readUTF());
            r.setHomePage(dis.readUTF());
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                r.addContact(ContactType.VALUES[dis.readInt()], dis.readUTF());
            }
            final int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                        r.addObjective(dis.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(sectionType,
                                new MultiTextSection(readList(dis, dis::readUTF)));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        r.addSection(sectionType,
                                new OrganizationSection(readList(dis, () -> new Organization(new Link(dis.readUTF(), dis.readUTF()),
                                        readList(dis, () -> new Organization.Period(readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()))))));
                        break;
                }
            }
            return r;
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        Objects.requireNonNull(ld, "LocalDate cannot be null, use Period.NOW");
        dos.writeInt(ld.getYear());
        dos.writeUTF(ld.getMonth().name());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), Month.valueOf(dis.readUTF()), 1);
    }

    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }
}
