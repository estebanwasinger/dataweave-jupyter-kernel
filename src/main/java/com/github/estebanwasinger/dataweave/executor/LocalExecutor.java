package com.github.estebanwasinger.dataweave.executor;

import org.mule.runtime.api.el.BindingContext;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.scheduler.Scheduler;
import org.mule.weave.v2.el.MuleServiceLevelModuleManager;
import org.mule.weave.v2.el.WeaveExpressionLanguage;
import org.mule.weave.v2.model.service.CharsetProviderService;
import org.mule.weave.v2.model.service.DefaultCharsetProviderService$;

import java.util.Map;

import static org.mule.runtime.core.internal.util.rx.ImmediateScheduler.IMMEDIATE_SCHEDULER;

public class LocalExecutor implements DWExecutor {

    private final WeaveExpressionLanguage weaveEngine;

    public LocalExecutor() {
        MuleServiceLevelModuleManager muleServiceLevelModuleManager = new MuleServiceLevelModuleManager();
        Scheduler immediateScheduler = IMMEDIATE_SCHEDULER;
        CharsetProviderService charsetProviderService = DefaultCharsetProviderService$.MODULE$;
        weaveEngine = new WeaveExpressionLanguage(IMMEDIATE_SCHEDULER, charsetProviderService, muleServiceLevelModuleManager);
    }

    @Override
    public TypedValue execute(String descriptor, Map<String, TypedValue> context) {
        return weaveEngine.evaluate(descriptor, createBindingContext(context));
    }

    private BindingContext createBindingContext(Map<String, TypedValue> context) {
        BindingContext.Builder builder = BindingContext.builder();
        for (Map.Entry<String, TypedValue> entry : context.entrySet()) {
            builder.addBinding(entry.getKey(), entry.getValue());
        }

        return builder.build();
    }
}
