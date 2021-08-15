package com.github.estebanwasinger.dataweave.magic;

import java.util.List;

public interface PreExecLineMagic extends Magic {

    void handleLineMagic(String script, List<String> args);
}
