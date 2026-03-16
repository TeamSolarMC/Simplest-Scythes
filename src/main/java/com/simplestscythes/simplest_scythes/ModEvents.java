package com.simplestscythes.simplest_scythes;

import com.simplestscythes.simplest_scythes.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
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
        event.getGenericTrades().add(new BasicItemListing(
                12,
                new ItemStack(ModItems.SCYTHE_UPGRADE_SMITHING_TEMPLATE.get(), 1),
                3,
                12,
                0.05F
        ));
    }

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        String path = event.getName().toString();

        if (path.contains("village_toolsmith") ||
            path.contains("village_armorer") ||
            path.contains("village_weaponsmith")) {

            LootPool pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.SCYTHE_UPGRADE_SMITHING_TEMPLATE.get()))
                    .when(LootItemRandomChanceCondition.randomChance(0.25F))
                    .build();
            event.getTable().addPool(pool);
        }
    }
}
