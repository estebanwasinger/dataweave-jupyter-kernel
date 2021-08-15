package com.github.estebanwasinger.dataweave.magic;

import com.github.estebanwasinger.dataweave.magic.CellMagic;
import com.github.estebanwasinger.dataweave.magic.PostExectLineMagic;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.DataTypeBuilder;
import org.mule.runtime.api.metadata.TypedValue;

import java.util.List;
import java.util.Map;

public class InputMagic implements CellMagic {

    private final Map<String, TypedValue> context;

    public InputMagic(Map<String, TypedValue> context) {
        this.context = context;
    }

    @Override
    public String getName() {
        return "input";
    }

    @Override
    public void handleCellMagic(List<String> args, String body) {
        String name = args.get(0);
        String contentType;

        if(args.size() > 1) {
            contentType = args.get(1);
        } else {
            contentType = "application/json";
        }
        DataType dataType = DataType.builder().type(String.class).mediaType(contentType).build();

        context.put(name, new TypedValue<>(body, dataType));
    }
}
