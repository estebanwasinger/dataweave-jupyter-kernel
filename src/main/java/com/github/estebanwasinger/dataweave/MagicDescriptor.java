package com.github.estebanwasinger.dataweave;

import java.util.List;

public class MagicDescriptor {
    private MagicType type;
    private String name;
    private List<String> args;

    public MagicDescriptor(MagicType type, String name, List<String> args) {
        this.type = type;
        this.name = name;
        this.args = args;
    }

    public MagicType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<String> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "MagicDescriptor{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", args=" + args +
                '}';
    }
}
