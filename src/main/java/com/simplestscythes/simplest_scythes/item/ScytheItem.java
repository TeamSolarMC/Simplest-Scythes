package com.simplestscythes.simplest_scythes.item;

import com.simplestscythes.simplest_scythes.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.ItemAbilities;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.List;
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

        // Single block mode when sneaking
        if (player.isShiftKeyDown()) {
            return super.useOn(context);
        }

        int radius = ModConfig.TILL_RADIUS.get();

        // === HARVEST CROPS ===
        int harvestedCount = 0;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos targetPos = clickedPos.offset(dx, 0, dz);
                BlockState targetState = level.getBlockState(targetPos);
                Block block = targetState.getBlock();

                if (block instanceof CropBlock crop && crop.isMaxAge(targetState)) {
                    if (!level.isClientSide()) {
                        List<ItemStack> drops = Block.getDrops(targetState, (ServerLevel) level, targetPos, null);
                        ItemStack seedItem = crop.getCloneItemStack(level, targetPos, targetState);
                        boolean seedRemoved = false;
                        for (ItemStack drop : drops) {
                            if (!seedRemoved && drop.getItem() == seedItem.getItem()) {
                                drop.shrink(1);
                                seedRemoved = true;
                            }
                            if (!drop.isEmpty()) {
                                Block.popResource(level, targetPos, drop);
                            }
                        }
                        level.setBlock(targetPos, crop.getStateForAge(0), Block.UPDATE_ALL_IMMEDIATE);
                    }
                    harvestedCount++;
                }
            }
        }

        if (harvestedCount > 0) {
            level.playSound(player, clickedPos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            context.getItemInHand().hurtAndBreak(harvestedCount, player,
                    context.getHand() == InteractionHand.MAIN_HAND
                            ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        // === TILL SOIL ===
        if (context.getClickedFace() != Direction.UP) {
            return InteractionResult.PASS;
        }

        int tilledCount = 0;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos targetPos = clickedPos.offset(dx, 0, dz);
                BlockState targetState = level.getBlockState(targetPos);
                BlockPos abovePos = targetPos.above();
                BlockState aboveState = level.getBlockState(abovePos);

                if (!aboveState.isAir()) continue;

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
            context.getItemInHand().hurtAndBreak(tilledCount, player,
                    context.getHand() == InteractionHand.MAIN_HAND
                            ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_HOE_ACTIONS.contains(itemAbility)
                || itemAbility == ItemAbilities.SWORD_SWEEP;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!level.isClientSide() && ModConfig.AREA_MINING.get()) {
            if (state.is(BlockTags.MINEABLE_WITH_HOE) && miner instanceof Player player) {
                int radius = ModConfig.TILL_RADIUS.get();

                // Determine which face the player is looking at
                Direction facing = Direction.getNearest(
                        player.getLookAngle().x,
                        player.getLookAngle().y,
                        player.getLookAngle().z
                );

                for (int d1 = -radius; d1 <= radius; d1++) {
                    for (int d2 = -radius; d2 <= radius; d2++) {
                        if (d1 == 0 && d2 == 0) continue;

                        BlockPos targetPos;
                        if (facing == Direction.UP || facing == Direction.DOWN) {
                            // Looking up/down — break in X/Z plane (horizontal)
                            targetPos = pos.offset(d1, 0, d2);
                        } else if (facing == Direction.NORTH || facing == Direction.SOUTH) {
                            // Looking north/south — break in X/Y plane (vertical wall)
                            targetPos = pos.offset(d1, d2, 0);
                        } else {
                            // Looking east/west — break in Z/Y plane (vertical wall)
                            targetPos = pos.offset(0, d2, d1);
                        }

                        BlockState targetState = level.getBlockState(targetPos);

                        if (targetState.is(BlockTags.MINEABLE_WITH_HOE)) {
                            level.destroyBlock(targetPos, true, player);
                            stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                        }
                    }
                }
            }
        }
        return super.mineBlock(stack, level, state, pos, miner);
    }
}