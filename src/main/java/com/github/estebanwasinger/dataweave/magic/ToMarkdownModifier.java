package com.github.estebanwasinger.dataweave.magic;

import com.github.estebanwasinger.dataweave.executor.DWExecutor;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ToMarkdownModifier implements PayloadModifierLineMagic {

    private DWExecutor executor;

    public ToMarkdownModifier(DWExecutor executor) {
        this.executor = executor;
    }

    String script = "%dw 2.0\n" +
            "output text/plain\n" +
            "fun getHeader(object: Object) : String = \"|\" ++ (keysOf(object) joinBy \"|\") ++ \"|\"\n" +
            "\n" +
            "fun createEntry(entry: Object) : String = \"|\" ++ (valuesOf(entry) map $ as String joinBy \"|\") ++ \"|\"\n" +
            "\n" +
            "fun getContent(array: Array) : Array<String> = array map createEntry($)\n" +
            "\n" +
            "fun toMarkdownTable(array: Array) : String = do {\n" +
            "    var numberOfColumns = sizeOf(keysOf(array[0]))\n" +
            "    var header = getHeader(array[0])\n" +
            "    var separator = \"|\" ++ (1 to numberOfColumns map \"--\" joinBy \"|\") ++ \"|\" \n" +
            "    var content = getContent(array[0 to -1])\n" +
            "    ---\n" +
            "    ([header, separator] ++ content) joinBy \"\\n\"\n" +
            "}\n" +
            "---\n" +
            "toMarkdownTable(payload)";

    @Override
    public TypedValue handleLineMagic(List<String> args, TypedValue result) {
        HashMap<String, TypedValue> context = new HashMap<>();
        context.put("payload", result);
        TypedValue execute = executor.execute(script, context, Collections.emptyList());
        return new TypedValue(execute.getValue(), DataType.builder().type(String.class).mediaType("text/markdown").build());
    }

    @Override
    public String getName() {
        return "mrkdwn";
    }

    @Override
    public String getUsage() {
        return "%mrkdwn";
    }

    @Override
    public String getDescription() {
        return "Renders output to Markdown Table";
    }
}
