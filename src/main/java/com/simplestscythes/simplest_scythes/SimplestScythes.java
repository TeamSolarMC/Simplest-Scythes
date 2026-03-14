package com.simplestscythes.simplest_scythes;

import com.simplestscythes.simplest_scythes.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(SimplestScythes.MOD_ID)
public class SimplestScythes {
    public static final String MOD_ID = "simplest_scythes";

    public SimplestScythes(IEventBus modEventBus) {
        ModItems.ITEMS.register(modEventBus);

        ModLoadingContext.get().getActiveContainer()
                .registerConfig(ModConfig.Type.COMMON, com.simplestscythes.simplest_scythes.ModConfig.SPEC);

        NeoForge.EVENT_BUS.register(ModEvents.class);
    }
}
