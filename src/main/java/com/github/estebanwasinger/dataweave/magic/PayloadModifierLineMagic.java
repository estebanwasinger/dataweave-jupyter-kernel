package com.github.estebanwasinger.dataweave.magic;

import org.mule.runtime.api.metadata.TypedValue;

import java.util.List;

public interface PayloadModifierLineMagic  {
    TypedValue handleLineMagic(List<String> args, TypedValue result);
}
