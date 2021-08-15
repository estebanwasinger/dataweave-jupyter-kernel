package com.github.estebanwasinger.dataweave.magic;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ImportLineMagicTest extends TestCase {

    public void testHandleLineMagic() {
        List<String> imports = new ArrayList<>();
        ImportLineMagic importLineMagic = new ImportLineMagic(imports);

        ArrayList<String> args = new ArrayList<>();
        args.add("*");
        args.add("from");
        args.add("dw::Crypto");
        importLineMagic.handleLineMagic("blabla",args );
        System.out.println(imports);
    }
}