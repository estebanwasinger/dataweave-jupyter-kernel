package com.github.estebanwasinger.dataweave.magic;

import java.util.List;

public interface PreExecLineMagic<T> extends Magic{

    T handleLineMagic(List<String> args);
}
