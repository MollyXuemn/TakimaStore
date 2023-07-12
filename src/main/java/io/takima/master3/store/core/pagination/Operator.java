package io.takima.master3.store.core.pagination;

import java.util.regex.Pattern;

// associate one regex per operator, to be able to recognise operator from expression.
public enum Operator {
    NOT_LTE("^(.*?)!<=(.*?)$"),
    NOT_LT("^(.*?)!<(.*?)$"),
    NOT_GTE("^(.*?)!>=(.*?)$"),
    NOT_GT("^(.*?)!>(.*?)$"),
    NOT_IN("^(.*?)!~(\\{.*?\\})$"),
    NOT_LIKE("^(.*?)!=%(.*?)%$"),
    NOT_SW("^(.*?)!=(.*?)%$"),
    NOT_EW("^(.*?)!=%(.*?)$"),
    NOT_NULL("^(.*?)!=null$"),
    NOT_EQ("^(.*?)!=(.*?)$"),

    LTE("^(.*?)<=(.*?)$"),
    LT("^(.*?)<(.*?)$"),
    GTE("^(.*?)>=(.*?)$"),
    GT("^(.*?)>(.*?)$"),
    IN("^(.*?)~(\\{.*?\\})$"),
    LIKE("^(.*?)=~(.*?)~$"),
    SW("^(.*?)=(.*?)%$"),
    EW("^(.*?)=%(.*?)$"),
    NULL("^(.*?)=null$"),
    EQ("^(.*?)=(.*?)$");


    private final Pattern pattern;

    Operator(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return this.pattern;
    }
}

