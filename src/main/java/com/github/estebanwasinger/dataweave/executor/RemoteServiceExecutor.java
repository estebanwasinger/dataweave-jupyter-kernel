package com.github.estebanwasinger.dataweave.executor;

import com.github.estebanwasinger.dataweave.executor.remote.Input;
import com.github.estebanwasinger.dataweave.executor.remote.TransformationRequest;
import com.github.estebanwasinger.dataweave.executor.remote.TransformationResponse;
import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteServiceExecutor implements DWExecutor {

    private final OkHttpClient client;
    private final Gson gson;
    private MediaType jsonMediaType;

    public RemoteServiceExecutor() {
        client = new OkHttpClient();
        gson = new Gson();
    }

    @Override
    public TypedValue execute(String script, Map<String, TypedValue> context, List<String> imports) {
        String payload = createPayload(script, context, imports);
        jsonMediaType = MediaType.get("application/json");
        RequestBody body = RequestBody.create(payload, jsonMediaType);

        Request request = new Request.Builder()
                .url("https://developer.mulesoft.com/transform")
                .post(body)
                .build();

//        System.out.println("---");

        try (Response response = client.newCall(request).execute()) {
            return getTypedValue(response);
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }

    @NotNull
    private TypedValue getTypedValue(Response response) throws IOException {
        String string = response.body().string();
//        System.out.println(string);
        TransformationResponse transformationResponse = gson.fromJson(string, TransformationResponse.class);
        if(transformationResponse.isSuccess()) {
            return new TypedValue(transformationResponse.getResult().get("content"), getContentType(transformationResponse));
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("Error:\n").append("Kind: ").append(transformationResponse.getError().getKind()).append("\n")
                    .append("Message: ").append(transformationResponse.getError().getMessage());
            throw new RuntimeException(builder.toString());
        }
    }

    private DataType getContentType(TransformationResponse transformationResponse) {
        return DataType.builder().type(String.class).mediaType(transformationResponse.getResult().get("contentType")).build();
    }

    private String createPayload(String script, Map<String, TypedValue> context, List<String> imports) {
        Map<String, Input> inputs = new HashMap<>();
        String newScript = script;

        if(!imports.isEmpty()) {
            StringBuilder newScriptBuilder = new StringBuilder();
            for (String anImport : imports) {
                newScriptBuilder.append(anImport).append("\n");
            }
            if(!script.contains("---")) {
                newScriptBuilder.append("---\n");
            }
            newScriptBuilder.append(script);


            newScript = newScriptBuilder.toString();
        }


        for (Map.Entry<String, TypedValue> entry : context.entrySet()) {
            String value = entry.getValue().getValue().toString();
            org.mule.runtime.api.metadata.MediaType mediaType = entry.getValue().getDataType().getMediaType();
            inputs.put(entry.getKey(), new Input(value, "text", "UTF-8", mediaType.toString(), Collections.emptyMap()));
        }
        Map<String, String> fs = new HashMap<>();
        fs.put("main.dwl", newScript);
        return gson.toJson(new TransformationRequest("main.dwl", inputs, fs));
    }
}
