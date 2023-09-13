package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import com.davigj.foolish_asteroids.core.util.FoolishAsteroidsDamageSources;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Objects;
import java.util.Random;

public class FlaskItem extends Item {
    public FlaskItem(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult hitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (hitresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = hitresult.getBlockPos();
            if (!level.mayInteract(player, blockpos)) {
                return InteractionResultHolder.pass(itemstack);
            }

            if (Objects.requireNonNull(level.getFluidState(blockpos).getType().getRegistryName()).toString().equals("undergarden:virulent_mix_source")) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                level.gameEvent(player, GameEvent.FLUID_PICKUP, blockpos);
                boolean virulentResistant = false;
                for(MobEffectInstance effectInstance : player.getActiveEffects()) {
                    if(Objects.requireNonNull(effectInstance.getEffect().getRegistryName()).toString().equals("undergarden:virulent_resistance")) {
                        virulentResistant = true;
                    }
                }
                if (!virulentResistant) {
                    switch (new Random().nextInt(5)) {
                        case 0 -> player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0, false, false));
                        case 1 -> player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200, 0, false, false));
                        case 2 -> player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 1, false, false));
                        case 3 -> player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false, false));
                        case 4 -> player.addEffect(new MobEffectInstance(AMEffectRegistry.FEAR, 200, 0, false, false));
                    }
                    player.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0, false, false));
                }
                player.hurt(FoolishAsteroidsDamageSources.VIRULENT, 1.0F);
                return InteractionResultHolder.sidedSuccess(this.turnFlaskIntoItem(itemstack, player, new ItemStack(FoolishAsteroidsItems.VIRULENT_FLASK.get())), level.isClientSide());
            }
        }
        return InteractionResultHolder.pass(itemstack);
    }

    protected ItemStack turnFlaskIntoItem(ItemStack p_40652_, Player p_40653_, ItemStack p_40654_) {
        p_40653_.awardStat(Stats.ITEM_USED.get(this));
        return ItemUtils.createFilledResult(p_40652_, p_40653_, p_40654_);
    }
}
