package com.github.estebanwasinger.dataweave.magic;

import java.util.List;

public interface CellMagic extends Magic {

    void handleCellMagic(List<String> args, String body);
}
