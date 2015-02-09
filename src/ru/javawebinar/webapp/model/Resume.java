package ru.javawebinar.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * gkislin
 * 12.12.2014.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    static final long serialVersionUID = 1L;

    private String uuid;
    private String fullName;
    private String location = "";
    private String homePage = "";
    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public static final Resume EMPTY;

    static {
        EMPTY = new Resume();
    }

    public Resume(String fullName, String location) {
        this(UUID.randomUUID().toString(), fullName, location);
    }

    public Resume(String uuid, String fullName, String location) {
        Objects.requireNonNull(uuid, "uuid is null");
        Objects.requireNonNull(fullName, "fullName is null");
        Objects.requireNonNull(location, "location is null");
        this.uuid = uuid;
        this.fullName = fullName;
        this.location = location;
    }

    public Resume() {
    }

    public Resume(String uuid) {
        this.uuid = uuid;

    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public void addSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void addContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLocation() {
        return location;
    }

    public String getHomePage() {
        return homePage;
    }

    public Section getSections(SectionType type) {
        return sections.get(type);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public void addObjective(String value) {
        addSection(SectionType.OBJECTIVE, new TextSection(value));
    }

    public void addMultiTextSection(SectionType type, String... values) {
        addSection(type, new MultiTextSection(values));
    }

    public void addOrganizationSection(SectionType type, Organization... organizations) {
        addSection(type, new OrganizationSection(organizations));
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Resume other = (Resume) obj;
        return Objects.equals(this.uuid, other.uuid) && Objects.equals(this.fullName, other.fullName) && Objects.equals(this.location, other.location) && Objects.equals(this.homePage, other.homePage) && Objects.equals(this.contacts, other.contacts) && Objects.equals(this.sections, other.sections);
    }

    //    @Override
    public int compareTo(Resume o) {
        return fullName.compareTo(o.fullName);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", location='" + location + '\'' +
                ", homePage='" + homePage + '\'' +
                ", contacts=" + contacts +
                ", sections=" + sections +
                '}';
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

/*
    private String getEmail(List<Contact> list) {
        for (Contact c : list) {
            if (c.getType() == ContactType.MAIL) {
                return c.getValue();
            }
        }
        return null;
    }
*/

}
