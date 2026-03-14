package com.simplestscythes.simplest_scythes.item;

import com.simplestscythes.simplest_scythes.SimplestScythes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

@EventBusSubscriber(modid = SimplestScythes.MOD_ID)
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SimplestScythes.MOD_ID);

    // Tool Materials: (incorrectBlocksForDrops, durability, speed, attackDamageBonus, enchantability, repairItems)
    public static final ToolMaterial WOODEN_SCYTHE_MAT = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, 177, 2.0F, 0.0F, 15, ItemTags.PLANKS);
    public static final ToolMaterial STONE_SCYTHE_MAT = new ToolMaterial(
            BlockTags.INCORRECT_FOR_STONE_TOOL, 393, 4.0F, 1.0F, 5, ItemTags.STONE_TOOL_MATERIALS);
    public static final ToolMaterial IRON_SCYTHE_MAT = new ToolMaterial(
            BlockTags.INCORRECT_FOR_IRON_TOOL, 750, 6.0F, 2.0F, 14, ItemTags.IRON_TOOL_MATERIALS);
    public static final ToolMaterial GOLDEN_SCYTHE_MAT = new ToolMaterial(
            BlockTags.INCORRECT_FOR_GOLD_TOOL, 96, 12.0F, 0.0F, 22, ItemTags.GOLD_TOOL_MATERIALS);
    public static final ToolMaterial DIAMOND_SCYTHE_MAT = new ToolMaterial(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 4683, 8.0F, 3.0F, 10, ItemTags.DIAMOND_TOOL_MATERIALS);
    public static final ToolMaterial COPPER_SCYTHE_MAT = new ToolMaterial(
            BlockTags.INCORRECT_FOR_COPPER_TOOL, 570, 5.0F, 1.0F, 10, ItemTags.COPPER_TOOL_MATERIALS);
    public static final ToolMaterial NETHERITE_SCYTHE_MAT = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 6093, 9.0F, 4.0F, 15, ItemTags.NETHERITE_TOOL_MATERIALS);

    public static final DeferredItem<ScytheItem> WOODEN_SCYTHE = ITEMS.registerItem("wooden_scythe",
            props -> new ScytheItem(WOODEN_SCYTHE_MAT, 3, -3.0F, props));

    public static final DeferredItem<ScytheItem> STONE_SCYTHE = ITEMS.registerItem("stone_scythe",
            props -> new ScytheItem(STONE_SCYTHE_MAT, 3, -3.0F, props));

    public static final DeferredItem<ScytheItem> COPPER_SCYTHE = ITEMS.registerItem("copper_scythe",
            props -> new ScytheItem(COPPER_SCYTHE_MAT, 3, -3.0F, props));

    public static final DeferredItem<ScytheItem> IRON_SCYTHE = ITEMS.registerItem("iron_scythe",
            props -> new ScytheItem(IRON_SCYTHE_MAT, 3, -3.0F, props));

    public static final DeferredItem<ScytheItem> GOLDEN_SCYTHE = ITEMS.registerItem("golden_scythe",
            props -> new ScytheItem(GOLDEN_SCYTHE_MAT, 3, -3.0F, props));

    public static final DeferredItem<ScytheItem> DIAMOND_SCYTHE = ITEMS.registerItem("diamond_scythe",
            props -> new ScytheItem(DIAMOND_SCYTHE_MAT, 3, -3.0F, props));

    public static final DeferredItem<ScytheItem> NETHERITE_SCYTHE = ITEMS.registerItem("netherite_scythe",
            props -> new ScytheItem(NETHERITE_SCYTHE_MAT, 3, -3.0F, props.fireResistant()));

    public static final DeferredItem<SmithingTemplateItem> SCYTHE_UPGRADE_SMITHING_TEMPLATE =
            ITEMS.registerItem("scythe_upgrade_smithing_template",
                    props -> new SmithingTemplateItem(
                            Component.translatable("item.simplest_scythes.smithing_template.scythe_upgrade.applies_to")
                                    .withStyle(ChatFormatting.BLUE),
                            Component.translatable("item.simplest_scythes.smithing_template.scythe_upgrade.ingredients")
                                    .withStyle(ChatFormatting.BLUE),
                            Component.translatable("item.simplest_scythes.smithing_template.scythe_upgrade.base_slot_description"),
                            Component.translatable("item.simplest_scythes.smithing_template.scythe_upgrade.additions_slot_description"),
                            List.of(
                                    Identifier.withDefaultNamespace("container/slot/hoe"),
                                    Identifier.fromNamespaceAndPath("simplest_scythes", "container/slot/empty_slot_scythe")
                            ),
                            List.of(
                                    Identifier.fromNamespaceAndPath("simplest_scythes", "container/slot/empty_slot_block")
                            ),
                            props.rarity(Rarity.UNCOMMON)
                    )
            );


    @SubscribeEvent
    public static void addToCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(WOODEN_SCYTHE);
            event.accept(STONE_SCYTHE);
            event.accept(COPPER_SCYTHE);
            event.accept(IRON_SCYTHE);
            event.accept(GOLDEN_SCYTHE);
            event.accept(DIAMOND_SCYTHE);
            event.accept(NETHERITE_SCYTHE);
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(WOODEN_SCYTHE);
            event.accept(STONE_SCYTHE);
            event.accept(COPPER_SCYTHE);
            event.accept(IRON_SCYTHE);
            event.accept(GOLDEN_SCYTHE);
            event.accept(DIAMOND_SCYTHE);
            event.accept(NETHERITE_SCYTHE);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(SCYTHE_UPGRADE_SMITHING_TEMPLATE);
        }
    }
}
