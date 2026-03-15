package com.simplestscythes.simplest_scythes.item;

import com.simplestscythes.simplest_scythes.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.ItemAbilities;

import java.util.HashMap;
import java.util.Map;

public class ScytheItem extends HoeItem {

    private final Tier tier;

    private static final Map<Block, BlockState> TILLABLES = new HashMap<>();

    static {
        TILLABLES.put(Blocks.GRASS_BLOCK, Blocks.FARMLAND.defaultBlockState());
        TILLABLES.put(Blocks.DIRT, Blocks.FARMLAND.defaultBlockState());
        TILLABLES.put(Blocks.DIRT_PATH, Blocks.FARMLAND.defaultBlockState());
        TILLABLES.put(Blocks.COARSE_DIRT, Blocks.DIRT.defaultBlockState());
        TILLABLES.put(Blocks.ROOTED_DIRT, Blocks.DIRT.defaultBlockState());
    }

    public ScytheItem(Tier tier, Properties properties) {
        super(tier, properties);
        this.tier = tier;
    }

    @Override
    public int getEnchantmentValue() {
        return this.getTier().getEnchantmentValue();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Player player = context.getPlayer();

        if (player == null) {
            return InteractionResult.PASS;
        }

        if (player.isShiftKeyDown()) {
            return super.useOn(context);
        }

        if (context.getClickedFace() != Direction.UP) {
            return InteractionResult.PASS;
        }

        int tilledCount = 0;
        int radius = ModConfig.TILL_RADIUS.get();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos targetPos = clickedPos.offset(dx, 0, dz);
                BlockState targetState = level.getBlockState(targetPos);
                BlockPos abovePos = targetPos.above();
                BlockState aboveState = level.getBlockState(abovePos);

                if (!aboveState.isAir()) {
                    continue;
                }

                BlockState tilledState = TILLABLES.get(targetState.getBlock());
                if (tilledState != null) {
                    if (!level.isClientSide()) {
                        level.setBlock(targetPos, tilledState, Block.UPDATE_ALL_IMMEDIATE);
                    }
                    tilledCount++;
                }
            }
        }

        if (tilledCount > 0) {
            level.playSound(player, clickedPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            ItemStack stack = context.getItemInHand();
            stack.hurtAndBreak(tilledCount, player,
                    context.getHand() == InteractionHand.MAIN_HAND
                            ? EquipmentSlot.MAINHAND
                            : EquipmentSlot.OFFHAND);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_HOE_ACTIONS.contains(itemAbility)
                || itemAbility == ItemAbilities.SWORD_SWEEP;
    }
}