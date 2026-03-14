package com.simplestscythes.simplest_scythes;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue TILL_RADIUS;

    static {
        BUILDER.comment("Simplest Scythes Configuration");
        BUILDER.push("tilling");

        TILL_RADIUS = BUILDER
                .comment("Tilling radius around clicked block.",
                         "1 = 3x3, 2 = 5x5, 3 = 7x7, etc.")
                .defineInRange("tillRadius", 1, 1, 5);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
