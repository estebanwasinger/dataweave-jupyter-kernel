package com.github.estebanwasinger.dataweave.magic;

import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;

import java.util.List;

public class HelpLineMagic implements PayloadModifierLineMagic {

    private List<Magic> magicList;

    public HelpLineMagic(List<Magic> magicList) {
        this.magicList = magicList;
    }

    @Override
    public TypedValue handleLineMagic(List<String> args, TypedValue result) {
        StringBuilder builder = new StringBuilder();
        builder.append("List of available magic capabilities:\n")
                .append("===================================\n");
        for (Magic magic : magicList) {
            builder.append("- ").append(magic.getName()).append(" -- ").append(magic.getUsage()).append("\n")
                    .append("   ").append(magic.getDescription()).append("\n");
        }

        return new TypedValue(builder.toString(), DataType.STRING);
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getUsage() {
        return "%help";
    }

    @Override
    public String getDescription() {
        return "Returns the list of magic available";
    }
}
