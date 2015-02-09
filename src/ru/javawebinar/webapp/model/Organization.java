package ru.javawebinar.webapp.model;

import ru.javawebinar.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * GKislin
 * 19.12.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    static final long serialVersionUID = 1L;

    private Link link = Link.EMPTY;
    private List<Period> periods = new LinkedList<>();

    public Organization() {
    }

    public Organization(String name, String url, Period... periods) {
        this(new Link(name, url), new LinkedList<>(Arrays.asList(periods)));
    }

    public Organization(Link link, List<Period> periods) {
        this.link = link;
        this.periods = periods;
    }

    public Link getLink() {
        return link;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        static final long serialVersionUID = 1L;

        public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate = NOW;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;

        private String position;
        private String content = "";

        public Period() {
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String position, String content) {
            this(LocalDate.of(startYear, startMonth, 1), LocalDate.of(endYear, endMonth, 1), position, content);
        }

        public Period(LocalDate startDate, LocalDate endDate, String position, String content) {
            Objects.requireNonNull(startDate, "startDate is null");
            Objects.requireNonNull(startDate, "position is null");
            this.startDate = startDate;
            this.endDate = endDate == null ? NOW : endDate;
            this.position = position;
            this.content = content == null ? "" : content;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getPosition() {
            return position;
        }

        public String getContent() {
            return content;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, position, content);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final Period other = (Period) obj;
            return Objects.equals(this.startDate, other.startDate) && Objects.equals(this.endDate, other.endDate) && Objects.equals(this.position, other.position) && Objects.equals(this.content, other.content);
        }

        @Override
        public String toString() {
            return "Period{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", position='" + position + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, periods);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Organization other = (Organization) obj;
        return Objects.equals(this.link, other.link) && Objects.equals(this.periods, other.periods);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link +
                ", periods=" + periods +
                '}';
    }
}
