package com.github.estebanwasinger.dataweave.executor;

import junit.framework.TestCase;
import org.junit.Test;
import org.mule.runtime.api.metadata.TypedValue;

import java.util.Collections;

public class RemoteServiceExecutorTest
{

    private RemoteServiceExecutor executor = new RemoteServiceExecutor();

    @Test
    public void executeTransformation() {
        TypedValue execute = executor.execute("1 to 5", Collections.emptyMap());
        System.out.println(execute.getValue());
        System.out.println(execute.getDataType());
    }

}