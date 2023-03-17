package io.takima.master3.store.core.pagination;

import java.util.regex.Pattern;

public enum Operator {
    LTE("^(.*?)<=(.*?)$"),
    LT("^(.*?)<(.*?)$"),
    GTE("^(.*?)>=(.*?)$"),
    GT("^(.*?)>(.*?)$"),
    NULL("^(.*?)=null$"),
    NOT_GTE("^(.*?)<(.*?)$"),
    NOT_GT("^(.*?)<=(.*?)$"),
    NOT_LTE("^(.*?)>(.*?)$"),
    NOT_LT("^(.*?)>=(.*?)$"),
    EQ("^(.*?)=(.*?)$");

    private final Pattern pattern;

    Operator(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return this.pattern;
    }
}
