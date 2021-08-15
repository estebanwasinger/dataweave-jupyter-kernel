package com.github.estebanwasinger.dataweave.magic;

import org.mule.runtime.api.metadata.TypedValue;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class NewVarMagic implements PostExectLineMagic {

    private Map<String, TypedValue> context;

    public NewVarMagic(Map<String, TypedValue> context) {
        this.context = context;
    }

    @Override
    public String getName() {
        return "var";
    }

    @Override
    public void handleLineMagic(List<String> args, TypedValue result) {
        context.put(args.get(0), result);
    }
}
