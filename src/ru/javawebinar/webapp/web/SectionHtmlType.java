package ru.javawebinar.webapp.web;

import ru.javawebinar.webapp.model.MultiTextSection;
import ru.javawebinar.webapp.model.Section;
import ru.javawebinar.webapp.model.SectionType;
import ru.javawebinar.webapp.model.TextSection;

import java.util.Collections;

import static ru.javawebinar.webapp.web.HtmlUtil.input;
import static ru.javawebinar.webapp.web.HtmlUtil.textArea;

/**
 * GKislin
 * 26.12.2014.
 */
public enum SectionHtmlType {
    TEXT {
        @Override
        public String toHtml(Section section, SectionType type) {
            return input(type.name(), section == null ? "" : ((TextSection) section).getValue());
        }

        @Override
        public TextSection createSection(String value) {
            return new TextSection(value);
        }
    },
    MULTI_TEXT {
        @Override
        public String toHtml(Section section, SectionType type) {
            return textArea(type.name(), section == null ? Collections.singletonList("") :((MultiTextSection) section).getValues());
        }

        @Override
        public MultiTextSection createSection(String values) {
            return new MultiTextSection(values.split("\\n"));
        }
    },
    ORGANIZATION {
        @Override
        public String toHtml(Section section, SectionType type) {
            return section.toString();
        }

        @Override
        public Section createSection(String value) {
            throw new UnsupportedOperationException();
        }
    };

    public abstract String toHtml(Section section, SectionType type);

    public abstract Section createSection(String value);
}
