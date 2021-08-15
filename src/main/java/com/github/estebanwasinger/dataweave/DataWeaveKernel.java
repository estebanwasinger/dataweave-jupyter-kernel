package com.github.estebanwasinger.dataweave;

import com.github.estebanwasinger.dataweave.executor.DWExecutor;
import com.github.estebanwasinger.dataweave.executor.RemoteServiceExecutor;
import com.github.estebanwasinger.dataweave.magic.*;
import io.github.spencerpark.jupyter.kernel.BaseKernel;
import io.github.spencerpark.jupyter.kernel.LanguageInfo;
import io.github.spencerpark.jupyter.kernel.display.DisplayData;
import org.jetbrains.annotations.NotNull;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.github.estebanwasinger.dataweave.MagicType.CELL;
import static com.github.estebanwasinger.dataweave.MagicType.LINE;

public class DataWeaveKernel extends BaseKernel {

    Map<String, PayloadModifierLineMagic> payloadModifierHandlers = new HashMap<>();
    Map<String, PreExecLineMagic> preExecLineHandlers = new HashMap<>();
    Map<String, PostExectLineMagic> postExecLineMagicHandlers = new HashMap<>();
    Map<String, CellMagic> cellMagicHandlers = new HashMap<>();
    Map<String, TypedValue> context;
    List<String> imports;
    DWExecutor executor;

    public DataWeaveKernel() {
        context = new HashMap<>();
        imports = new ArrayList<>();

        cellMagicHandlers.put("input", new InputMagic(context));
        postExecLineMagicHandlers.put("var", new NewVarMagic(context));
        executor = new RemoteServiceExecutor();
        payloadModifierHandlers.put("mrkdwn", new ToMarkdownModifier(executor));
        preExecLineHandlers.put("import", new ImportLineMagic(imports));
        payloadModifierHandlers.put("help", new HelpLineMagic(listMagics()));
//        executor = new LocalExecutor();
    }

    private List<Magic> listMagics() {
        List<Magic> magicList = new ArrayList<>();

        magicList.addAll(postExecLineMagicHandlers.values());
        magicList.addAll(preExecLineHandlers.values());
        magicList.addAll(payloadModifierHandlers.values());
        magicList.addAll(cellMagicHandlers.values());

        return magicList;
    }

    @Override
    public DisplayData eval(String expr) throws Exception {
        try {
            Supplier<DisplayData> displayDataSupplier = generateExecutor(expr);
            DisplayData displayData = displayDataSupplier.get();
            return displayData;

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
    }

    private Supplier<DisplayData> generateExecutor(String expr) {
        ExecutionDescriptor executionDescriptor = KernelUtils.parseExecution(expr);


        if(executionDescriptor.getMagic().isPresent()){
            MagicDescriptor magicDescriptor = executionDescriptor.getMagic().get();
            if(magicDescriptor.getType().equals(LINE)) {
                String magicName = magicDescriptor.getName();
                if(postExecLineMagicHandlers.containsKey(magicName)){
                    return () -> {
                        TypedValue result = evaluate(executionDescriptor.getBody());
                        PostExectLineMagic magic = postExecLineMagicHandlers.get(magicName);
                        magic.handleLineMagic(magicDescriptor.getArgs(), result);
                        return createDisplayData(result);
                    };
                }
                if(payloadModifierHandlers.containsKey(magicName)){
                    return () -> {
                        TypedValue result;
                        if(!executionDescriptor.getBody().isEmpty()) {
                            result = evaluate(executionDescriptor.getBody());
                        } else {
                            result = new TypedValue("", DataType.STRING);
                        }
                        PayloadModifierLineMagic magic = payloadModifierHandlers.get(magicName);
                        TypedValue typedValue = magic.handleLineMagic(magicDescriptor.getArgs(), result);
                        return createDisplayData(typedValue);
                    };
                }
                if(preExecLineHandlers.containsKey(magicName)){
                    PreExecLineMagic preExecLineMagic = preExecLineHandlers.get(magicName);
                    preExecLineMagic.handleLineMagic(executionDescriptor.getBody(), executionDescriptor.getMagic().get().getArgs());
                }

            }

            if(magicDescriptor.getType().equals(CELL)) {
                String magicName = magicDescriptor.getName();
                if(cellMagicHandlers.containsKey(magicName)){
                    CellMagic cellMagic = cellMagicHandlers.get(magicName);
                    cellMagic.handleCellMagic(magicDescriptor.getArgs(), executionDescriptor.getBody());
                } else {
                    throw new RuntimeException("No Magic");
                }
                return DisplayData::new;
            }
        }



        return () -> {
            TypedValue result = evaluate(executionDescriptor.getBody());
            return createDisplayData(result);
//            return new DisplayData(value.toString());
        };
    }

    @NotNull
    private DisplayData createDisplayData(TypedValue result) {
        Object value = result.getValue();
        DisplayData displayData = new DisplayData();
//        displayData.setDisplayId("someId");
        displayData.putText(value.toString());
        String mediatype = result.getDataType().getMediaType().toString();
        displayData.putData(mediatype, value.toString());
        return displayData;
    }

    private TypedValue evaluate(String expr) {
        if(!expr.isEmpty()){
            return executor.execute(expr, context, imports);
        } else {
            return new TypedValue("", DataType.STRING);
        }
    }

    @Override
    public LanguageInfo getLanguageInfo() {
        return new LanguageInfo("DataWeave", "2.4", "application/dw","dw","dataweave", "dataweave", "dataweave" );
    }
}
