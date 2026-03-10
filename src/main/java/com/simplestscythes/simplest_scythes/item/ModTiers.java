package com.simplestscythes.simplest_scythes.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public enum ModTiers implements Tier {
    WOODEN_SCYTHE(  177, 2.0F, 0.0F, 15, () -> Ingredient.of(net.minecraft.world.item.Items.OAK_PLANKS)),
    STONE_SCYTHE(   393, 4.0F, 1.0F, 5,  () -> Ingredient.of(net.minecraft.world.item.Items.COBBLESTONE)),
    IRON_SCYTHE(    750, 6.0F, 2.0F, 14, () -> Ingredient.of(net.minecraft.world.item.Items.IRON_INGOT)),
    GOLDEN_SCYTHE(  96,  12.0F, 0.0F, 22, () -> Ingredient.of(net.minecraft.world.item.Items.GOLD_INGOT)),
    DIAMOND_SCYTHE( 4683, 8.0F, 3.0F, 10, () -> Ingredient.of(net.minecraft.world.item.Items.DIAMOND)),
    NETHERITE_SCYTHE(6093, 9.0F, 4.0F, 15, () -> Ingredient.of(net.minecraft.world.item.Items.NETHERITE_INGOT));

    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final java.util.function.Supplier<Ingredient> repairIngredient;

    ModTiers(int uses, float speed, float attackDamageBonus, int enchantmentValue,
             java.util.function.Supplier<Ingredient> repairIngredient) {
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    @Override public int getUses() { return this.uses; }
    @Override public float getSpeed() { return this.speed; }
    @Override public float getAttackDamageBonus() { return this.attackDamageBonus; }
    @Override public TagKey<Block> getIncorrectBlocksForDrops() { return BlockTags.INCORRECT_FOR_WOODEN_TOOL; }
    @Override public int getEnchantmentValue() { return this.enchantmentValue; }
    @Override public Ingredient getRepairIngredient() { return this.repairIngredient.get(); }
}