package com.github.estebanwasinger.dataweave.magic;

import org.mule.runtime.api.metadata.TypedValue;

import java.util.List;

public interface PayloadModifierLineMagic extends Magic {
    TypedValue handleLineMagic(List<String> args, TypedValue result);
}
