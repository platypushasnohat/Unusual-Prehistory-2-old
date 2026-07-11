package com.barl_inc.unusual_prehistory.utils;

import java.util.Locale;
import java.util.function.IntFunction;

public class VariantHelper {
    public static IntFunction<String> nameOf(IntFunction<? extends Enum<?>> enumGetter) {
        return id -> enumGetter.apply(id).name().toLowerCase(Locale.ROOT);
    }
}
