package com.simplestscythes.simplest_scythes;

import com.simplestscythes.simplest_scythes.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

public class ModEvents {

    @SubscribeEvent
    public static void addWanderingTraderTrades(WandererTradesEvent event) {
        event.getRareTrades().add(new BasicItemListing(
                12,  // цена в изумрудах
                new ItemStack(ModItems.SCYTHE_UPGRADE_SMITHING_TEMPLATE.get(), 1),
                3,   // maxUses
                12,  // xpReward
                0.05F // priceMultiplier
        ));
    }

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        String path = event.getName().getPath();

        if (path.equals("chests/village/village_toolsmith") ||
            path.equals("chests/village/village_armorer") ||
            path.equals("chests/village/village_weaponsmith")) {

            LootPool pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.SCYTHE_UPGRADE_SMITHING_TEMPLATE.get()))
                    .when(LootItemRandomChanceCondition.randomChance(0.25f))
                    .build();
            event.getTable().addPool(pool);
        }
    }
}
