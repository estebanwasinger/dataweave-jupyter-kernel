package com.github.estebanwasinger.dataweave.magic;

import java.util.List;

public class ImportLineMagic implements PreExecLineMagic {
    private List<String> imports;

    public ImportLineMagic(List<String> imports) {
        this.imports = imports;
    }

    @Override
    public String getName() {
        return "import";
    }

    @Override
    public String getUsage() {
        return "%import <package to import>";
    }

    @Override
    public String getDescription() {
        return "Adds a new import declaration";
    }

    @Override
    public void handleLineMagic(String script, List<String> args) {
        StringBuilder builder = new StringBuilder();
        builder.append("import ");
        for (String arg : args) {
            builder.append(arg).append(" ");
        }
        imports.add(builder.toString());
    }
}
