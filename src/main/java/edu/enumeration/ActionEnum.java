package edu.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum ActionEnum {

    UNDEFINED(""),
    RUN("run"),
    JUMP("jump"),
    WALK("walk");

    private String value;
    private static final Map<String,ActionEnum> LOOKUP = new HashMap<>();

    ActionEnum(String value) {
        this.value = value;
    }

    static {
        for(ActionEnum actionEnum : EnumSet.allOf(ActionEnum.class)) {
            LOOKUP.put(actionEnum.getValue(), actionEnum);
        }
    }

    public static String[] getArray() {
        return Stream.of(ActionEnum.values()).map(ActionEnum::getValue).toArray(String[]::new);
    }

    public static ActionEnum find(String value) {
        if (value != null && LOOKUP.get(value) != null) {
            return LOOKUP.get(value);
        } else {
            return ActionEnum.UNDEFINED;
        }
    }

    public String getValue() {
        return value;
    }

}

