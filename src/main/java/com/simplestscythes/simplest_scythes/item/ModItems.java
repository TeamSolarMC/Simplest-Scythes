package com.simplestscythes.simplest_scythes.item;

import com.simplestscythes.simplest_scythes.SimplestScythes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = SimplestScythes.MOD_ID)
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SimplestScythes.MOD_ID);

    public static final DeferredItem<ScytheItem> WOODEN_SCYTHE = ITEMS.register("wooden_scythe",
            () -> new ScytheItem(ModTiers.WOODEN_SCYTHE, new Item.Properties()
                    .attributes(ScytheItem.createAttributes(ModTiers.WOODEN_SCYTHE, 3, -3.0F))));

    public static final DeferredItem<ScytheItem> STONE_SCYTHE = ITEMS.register("stone_scythe",
            () -> new ScytheItem(ModTiers.STONE_SCYTHE, new Item.Properties()
                    .attributes(ScytheItem.createAttributes(ModTiers.STONE_SCYTHE, 3, -3.0F))));

    public static final DeferredItem<ScytheItem> IRON_SCYTHE = ITEMS.register("iron_scythe",
            () -> new ScytheItem(ModTiers.IRON_SCYTHE, new Item.Properties()
                    .attributes(ScytheItem.createAttributes(ModTiers.IRON_SCYTHE, 3, -3.0F))));

    public static final DeferredItem<ScytheItem> GOLDEN_SCYTHE = ITEMS.register("golden_scythe",
            () -> new ScytheItem(ModTiers.GOLDEN_SCYTHE, new Item.Properties()
                    .attributes(ScytheItem.createAttributes(ModTiers.GOLDEN_SCYTHE, 3, -3.0F))));

    public static final DeferredItem<ScytheItem> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe",
            () -> new ScytheItem(ModTiers.DIAMOND_SCYTHE, new Item.Properties()
                    .attributes(ScytheItem.createAttributes(ModTiers.DIAMOND_SCYTHE, 3, -3.0F))));

    public static final DeferredItem<ScytheItem> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
            () -> new ScytheItem(ModTiers.NETHERITE_SCYTHE, new Item.Properties()
                    .attributes(ScytheItem.createAttributes(ModTiers.NETHERITE_SCYTHE, 3, -3.0F))
                    .fireResistant()));

    public static final DeferredItem<SmithingTemplateItem> SCYTHE_UPGRADE_SMITHING_TEMPLATE =
            ITEMS.register("scythe_upgrade_smithing_template", () -> createScytheUpgradeTemplate());

    private static SmithingTemplateItem createScytheUpgradeTemplate() {
        // Тексты тултипов
        Component appliesTo = Component.translatable("item.simplest_scythes.smithing_template.scythe_upgrade.applies_to").withStyle(ChatFormatting.BLUE);
        Component ingredients = Component.translatable("item.simplest_scythes.smithing_template.scythe_upgrade.ingredients").withStyle(ChatFormatting.BLUE);
        Component upgradeDesc = Component.translatable("item.simplest_scythes.smithing_template.scythe_upgrade.upgradeDesc").withStyle(ChatFormatting.GRAY);
        Component baseSlotDesc = Component.translatable("item.simplest_scythes.smithing_template.scythe_upgrade.base_slot_description");
        Component additionsSlotDesc = Component.translatable("item.simplest_scythes.smithing_template.scythe_upgrade.additions_slot_description");

        // Иконки пустых слотов для UI
        ResourceLocation emptySlotScythe = ResourceLocation.fromNamespaceAndPath("simplest_scythes", "item/empty_slot_scythe");
        ResourceLocation emptySlotHoe = ResourceLocation.fromNamespaceAndPath("minecraft", "item/empty_slot_hoe");
        ResourceLocation emptySlotBlock = ResourceLocation.fromNamespaceAndPath("simplest_scythes", "item/empty_slot_block");

        return new SmithingTemplateItem(
                appliesTo,
                ingredients,
                upgradeDesc,
                baseSlotDesc,
                additionsSlotDesc,
                List.of(emptySlotHoe, emptySlotScythe),
                List.of(emptySlotBlock)
        );
    }

    @SubscribeEvent
    public static void addToCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(WOODEN_SCYTHE);
            event.accept(STONE_SCYTHE);
            event.accept(IRON_SCYTHE);
            event.accept(GOLDEN_SCYTHE);
            event.accept(DIAMOND_SCYTHE);
            event.accept(NETHERITE_SCYTHE);
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(SCYTHE_UPGRADE_SMITHING_TEMPLATE);
        }
    }
}
