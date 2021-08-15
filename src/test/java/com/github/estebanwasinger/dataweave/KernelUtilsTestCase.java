package com.github.estebanwasinger.dataweave;

import io.github.spencerpark.jupyter.kernel.display.DisplayData;
import io.github.spencerpark.jupyter.kernel.display.mime.MIMEType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class KernelUtilsTestCase {

    @Test
    public void testCell() {
        ExecutionDescriptor executionDescriptor = KernelUtils.parseExecution("%%input var\n[1,2,3,4]");
        MagicDescriptor magicDescriptor = executionDescriptor.getMagic().get();
        Assert.assertEquals(magicDescriptor.getType(), MagicType.CELL);
        Assert.assertEquals(magicDescriptor.getName(), "input");
        Assert.assertEquals(executionDescriptor.getBody(), "[1,2,3,4]");
    }

    @Test
    public void testLine() {
        ExecutionDescriptor executionDescriptor = KernelUtils.parseExecution("%output var\n[1,2,3,4]");
        MagicDescriptor magicDescriptor = executionDescriptor.getMagic().get();
        Assert.assertEquals(magicDescriptor.getType(), MagicType.LINE);
        Assert.assertEquals(magicDescriptor.getName(), "output");
        Assert.assertEquals(executionDescriptor.getBody(), "[1,2,3,4]");
    }

    @Test
    public void testBodyWithoutMagic() {
        ExecutionDescriptor executionDescriptor = KernelUtils.parseExecution("[1,2,3,4]");
        Optional<MagicDescriptor> magicDescriptor = executionDescriptor.getMagic();
        Assert.assertFalse(magicDescriptor.isPresent());
        Assert.assertEquals(executionDescriptor.getBody(), "[1,2,3,4]");
    }

    @Test
    public void onlyMagicsNoBody(){
        ExecutionDescriptor executionDescriptor = KernelUtils.parseExecution("%help");
        System.out.println(executionDescriptor);

    }

//    @Test
//    public void test() throws Exception {
//        DataWeaveKernel dataWeaveKernel = new DataWeaveKernel();
//        DisplayData eval = dataWeaveKernel.eval("1 to 5");
//        System.out.println(eval.getData(MIMEType.ANY));
//
//
//    }
}
