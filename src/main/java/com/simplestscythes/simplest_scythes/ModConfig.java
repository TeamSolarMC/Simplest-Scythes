package com.simplestscythes.simplest_scythes;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.IntValue TILL_RADIUS;
    public static final ModConfigSpec.BooleanValue AREA_MINING;

    static {
        BUILDER.comment("Simplest Scythes Configuration");
        BUILDER.push("general");

        TILL_RADIUS = BUILDER
                .comment("Tilling radius around clicked block (1 = 3x3, 2 = 5x5, 3 = 7x7)")
                .defineInRange("tillRadius", 1, 1, 5);

        AREA_MINING = BUILDER
                .comment("Enable area mining for blocks tagged #mineable_with_hoe (uses same radius)")
                .define("areaMining", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}