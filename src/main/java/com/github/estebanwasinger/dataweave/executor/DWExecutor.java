package com.github.estebanwasinger.dataweave.executor;

import org.mule.runtime.api.metadata.TypedValue;

import java.lang.reflect.Type;
import java.util.Map;

public interface DWExecutor {

    TypedValue execute(String script, Map<String, TypedValue> context);
}
