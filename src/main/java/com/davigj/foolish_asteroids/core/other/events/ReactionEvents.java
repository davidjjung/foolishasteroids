package com.davigj.foolish_asteroids.core.other.events;

import com.brewinandchewin.common.effect.TipsyEffect;
import com.brewinandchewin.core.registry.BCEffects;
import com.davigj.foolish_asteroids.common.item.gear.BoundlessBootsItem;
import com.davigj.foolish_asteroids.common.item.gear.MoonWalkersItem;
import com.davigj.foolish_asteroids.common.util.FeatUtil;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.other.FADataProcessors;
import com.davigj.foolish_asteroids.core.registry.FAItems;
import com.davigj.foolish_asteroids.core.util.FADamageSources;
import com.starfish_studios.naturalist.registry.NaturalistItems;
import com.teamabnormals.atmospheric.common.entity.projectile.PassionfruitSeed;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.environmental.common.entity.animal.deer.Deer;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.neapolitan.common.entity.projectile.BananaPeel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.registry.ModItems;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;
import java.util.Random;

import static com.davigj.foolish_asteroids.common.item.elixir.HearsayElixirItem.oracleMap;
import static com.davigj.foolish_asteroids.common.item.elixir.IncendiaryElixirItem.smokingPlayers;
import static com.davigj.foolish_asteroids.common.item.elixir.IndomitableElixirItem.rainbowTimers;


@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID)
public class ReactionEvents {

    static TrackedDataManager manager = TrackedDataManager.INSTANCE;

    @SubscribeEvent
    public static void llamaDrama(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (event.getEntity() instanceof Llama llama) {
            System.out.println(source.getDirectEntity());
            if (source.getEntity() != null && source.getDirectEntity() instanceof PassionfruitSeed seed && seed.getOwner() instanceof Player player) {
                List<Integer> feats = manager.getValue(player, FADataProcessors.FEATS);
                if (feats.get(1) == 0) {
                    feats.set(1, 1);
                    ItemEntity itemEntity = new ItemEntity(player.level, llama.getX(), llama.getEyeY(), llama.getZ(),
                            new ItemStack(FAItems.COMET_MEDAL.get()));
                    itemEntity.setDefaultPickUpDelay();
                    itemEntity.setNoGravity(true);
                    double initialVelocityY = 0.11;
                    itemEntity.setDeltaMovement(0, initialVelocityY, 0);
                    player.level.addFreshEntity(itemEntity);
                    manager.setValue(player, FADataProcessors.FEATS, feats);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        boolean playerVictim = false;
        Player player = null;
        if (event.getEntity() instanceof Player) {
            playerVictim = true;
            player = (Player) event.getEntity();
        }
        if (source == DamageSource.ANVIL) {
            float modelHeight = ScaleTypes.HEIGHT.getScaleData(event.getEntity()).getBaseScale();
            ScaleTypes.HEIGHT.getScaleData(event.getEntity()).setScaleTickDelay(4);
            ScaleTypes.HEIGHT.getScaleData(event.getEntity()).setTargetScale(modelHeight * 0.5f);
            ScaleTypes.HEIGHT.getScaleData(event.getEntity()).setScaleTickDelay(20);
        }
        if (playerVictim && manager.getValue(player, FADataProcessors.RAD_POISONING) && !source.isMagic()) {
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1));
        }
        if (playerVictim && manager.getValue(player, FADataProcessors.STORED_ELECTRONS) > 0 && !source.isMagic()) {
            double explosionX = player.getX();
            double explosionY = player.getY() + player.getEyeHeight();
            double explosionZ = player.getZ();
            float explosionPower = 1.3f; // Adjust explosion power as needed
            int electrons = manager.getValue(player, FADataProcessors.STORED_ELECTRONS);
            double explosionRadius = (0.25 * electrons) + 1; // Adjust the radius as needed

            for (LivingEntity entity : player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(explosionRadius, 2.0D, explosionRadius))) {
                // Apply explosion forces to entities within the radius
                if (entity != player) {
                    Vec3 pushVector = entity.position().subtract(explosionX, explosionY, explosionZ).normalize();
                    double pushStrength = 0.6 + (0.05 * electrons); // Adjust the strength as needed
                    entity.setOnGround(false);
                    entity.push(pushVector.x * pushStrength, 0.3 + (electrons * 0.075) * (1.0D - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)), pushVector.z * pushStrength);
                }
            }
            player.level.explode(player, explosionX, explosionY, explosionZ, explosionPower, false, Explosion.BlockInteraction.NONE);
            manager.setValue(player, FADataProcessors.STORED_ELECTRONS, manager.getValue(player, FADataProcessors.STORED_ELECTRONS) - 1);
            Random random = new Random();
            if (random.nextInt(5) <= 2) {
                manager.setValue(player, FADataProcessors.RAD_POISONING, false);
            }
        }
        if (playerVictim && manager.getValue(player, FADataProcessors.AUTUMNAL)) {
            // TODO: Play sound of magical wind dispelling
            manager.setValue(player, FADataProcessors.AUTUMNAL, false);
        }
        if (playerVictim && event.getSource().equals(DamageSource.LIGHTNING_BOLT)) {
            // Check if the player is holding a glass bottle in either hand
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();

            if (mainHandItem.is(Items.GLASS_BOTTLE) || offHandItem.is(Items.GLASS_BOTTLE)) {
                // Turn the glass bottle into a lightning bottle
                ItemStack lightningBottle = new ItemStack(FAItems.LIGHTNING_BOTTLE.get()); // Replace with your lightning bottle item
                if (mainHandItem.is(Items.GLASS_BOTTLE)) {
                    player.setItemInHand(InteractionHand.MAIN_HAND, lightningBottle);
                } else {
                    player.setItemInHand(InteractionHand.OFF_HAND, lightningBottle);
                }
                // Consume the lightning bolt damage
                event.setCanceled(true);
            }
        }
        if (playerVictim && source != DamageSource.LAVA && source != DamageSource.OUT_OF_WORLD && rainbowTimers.containsKey(player.getUUID())) {
            event.setCanceled(true);
        }
        if (playerVictim && source.isFall()) {
            ItemStack gauntlets = player.getItemBySlot(EquipmentSlot.FEET);
            if (gauntlets.getItem() instanceof MoonWalkersItem || gauntlets.getItem() instanceof BoundlessBootsItem) {
                // TODO: the player like, plummets to the ground when they take damage in midair lol
                event.setAmount(event.getAmount() * 0.25F);
                if (event.getAmount() < 0.5F) {
                    event.setCanceled(true);
                }
            }
        }
        Random random = new Random();
        if (event.getEntity().getType().toString().equals("entity.creeperoverhaul.dark_oak_creeper") && random.nextBoolean()) {
            Spider spider = EntityType.SPIDER.create(event.getEntity().level);
            float creeperSize = ScaleTypes.HEIGHT.getScaleData(event.getEntity()).getBaseScale();
            Level world = event.getEntity().level;
            if (!world.isClientSide) {
                assert spider != null;
                ((PathfinderMob) spider).targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(spider, Player.class, true));
            }
            ScaleTypes.BASE.getScaleData(spider).setScaleTickDelay(0);
            ScaleTypes.BASE.getScaleData(spider).setScale(0.3f * creeperSize);
            ScaleTypes.BASE.getScaleData(spider).setScaleTickDelay(20);
            assert spider != null;
            spider.setPos(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
            event.getEntity().level.addFreshEntity(spider);
        }
    }

    @SubscribeEvent
    public static void onPlayerKillFeat(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player victim) || !(event.getSource().getEntity() instanceof Player killer)) {
            return;
        }
        List<Integer> feats = manager.getValue(killer, FADataProcessors.FEATS);
        for (MobEffectInstance effectInstance : killer.getActiveEffects()) {
            if (effectInstance.getEffect() instanceof TipsyEffect && effectInstance.getAmplifier() >= 3) {
                if (feats.get(6) == 0 && feats.get(5) == 0) {
                    feats.set(5, FeatUtil.uuidToInt(victim));
                } else if (feats.get(6) == 0 && feats.get(5) != FeatUtil.uuidToInt(victim)) {
                    FeatUtil.medalMaterialize(killer);
                    feats.set(6, 1);
                }
                break;
            }
        }
        if (event.getSource().getDirectEntity() instanceof Arrow arrow) {
            for (MobEffectInstance effect : arrow.potion.getEffects()) {
                if (effect.getEffect() == MobEffects.REGENERATION) {
                    if (feats.get(8) == 0 && feats.get(7) == 0) {
                        feats.set(7, FeatUtil.uuidToInt(victim));
                    } else if (feats.get(8) == 0 && feats.get(7) != FeatUtil.uuidToInt(victim)) {
                        FeatUtil.medalMaterialize(killer);
                        feats.set(8, 1);
                    }
                }
                break;
            }
            System.out.println(arrow.potion.getEffects());
        }
        if (!event.getSource().isProjectile() && !event.getSource().isMagic() &&
                killer.getMainHandItem().getItem() == ModItems.SKILLET.get() &&
                killer.getInventory().armor.get(3).getItem() == EnvironmentalItems.THIEF_HOOD.get()) {
            if (feats.get(10) == 0 && feats.get(9) == 0) {
                feats.set(9, FeatUtil.uuidToInt(victim));
            } else if (feats.get(10) == 0 && feats.get(9) != FeatUtil.uuidToInt(victim)) {
                FeatUtil.medalMaterialize(killer);
                feats.set(10, 1);
            }
        }
        if (event.getSource().getDirectEntity() instanceof PassionfruitSeed) {
            if (feats.get(12) == 0 && feats.get(11) == 0) {
                feats.set(11, FeatUtil.uuidToInt(victim));
            } else if (feats.get(12) == 0 && feats.get(11) != FeatUtil.uuidToInt(victim)) {
                FeatUtil.medalMaterialize(killer);
                feats.set(12, 1);
            }
        }

        manager.setValue(killer, FADataProcessors.FEATS, feats);
    }

    private static void cleanKillData(List<Integer> feats, Player killer, Player victim, int featIndex1, int featIndex2) {
        if (feats.get(featIndex2) == 0 && feats.get(featIndex1) == 0) {
            feats.set(featIndex1, FeatUtil.uuidToInt(victim));
        } else if (feats.get(featIndex2) == 0 && feats.get(featIndex1) != FeatUtil.uuidToInt(victim)) {
            FeatUtil.medalMaterialize(killer);
            feats.set(featIndex2, 1);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            smokingPlayers.remove(player.getUUID());
            rainbowTimers.remove(player.getUUID());
            oracleMap.remove(player.getUUID());
        }
        if (event.getSource().equals(FADamageSources.SERAPHIC) && event.getEntity() instanceof Player player) {
            BlockPos basaltPos = player.blockPosition();
            Level world = player.level;

            int towerHeight = Math.max(1, (int) (ScaleTypes.HEIGHT.getScaleData(player).getBaseScale() / .5f));
            int i = 0;
            while (i < towerHeight) {
                if (world.getBlockState(basaltPos).isAir()) {
                    world.setBlockAndUpdate(basaltPos, Blocks.BASALT.defaultBlockState());
                }
                basaltPos = basaltPos.above();
                i++;
            }
        }
        if (event.getEntity() instanceof Deer deer && !deer.getLevel().isClientSide) {
            if (deer.hasAntlers()) {
                ItemStack antlerItem = new ItemStack(NaturalistItems.ANTLER.get());
                deer.spawnAtLocation(antlerItem);
                // TODO: make an extra antler spawn with looting. though perhaps i should be adding this to the mob's loot table whoops
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (manager.getValue(player, FADataProcessors.HIGHWAY_TO_HELL) != 0) {
            manager.setValue(player, FADataProcessors.HIGHWAY_TO_HELL, 0);
            TranslatableComponent message = new TranslatableComponent("message.hearsay.forgiveness");
            event.getPlayer().displayClientMessage(message, true);
        } else if (manager.getValue(player, FADataProcessors.SERAPHIC_ACTIVE)) {
            manager.setValue(player, FADataProcessors.SERAPHIC_ACTIVE, false);
        }
        manager.setValue(player, FADataProcessors.STORED_ELECTRONS, 0);
        manager.setValue(player, FADataProcessors.RAD_POISONING, false);
        manager.setValue(player, FADataProcessors.AUTUMNAL, false);
        manager.setValue(player, FADataProcessors.ANTI_DRUNK, 0);
    }

    @SubscribeEvent
    public static void onPotionAdded(PotionEvent.PotionApplicableEvent event) {
        MobEffect effect = event.getPotionEffect().getEffect();
        if (!event.getEntity().level.isClientSide()) {
            if (effect == BCEffects.TIPSY.get() && event.getEntity() instanceof Player player) {
                MobEffectInstance tipsy = event.getPotionEffect();
                int antiDrunk = TrackedDataManager.INSTANCE.getValue(player, FADataProcessors.ANTI_DRUNK);
                if (antiDrunk > 0) {
                    int lvl = tipsy.getAmplifier() + 1; // gets actual level
                    int duration = tipsy.getDuration();
                    int existingTipsyLvl = 0;
                    int existingTipsyDur = 0;
                    for (MobEffectInstance existingEffect : player.getActiveEffects()) {
                        if (existingEffect.getEffect().equals(BCEffects.TIPSY.get())) {
                            existingTipsyLvl = existingEffect.getAmplifier() + 1; // current level of tipsy
                            existingTipsyDur = existingEffect.getDuration();
                        }
                    }
                    if (antiDrunk - lvl >= 0) {
                        TrackedDataManager.INSTANCE.setValue(player, FADataProcessors.ANTI_DRUNK, antiDrunk - lvl);
                    } else {
                        // the incoming tipsy exceeds antidrunk
                        TrackedDataManager.INSTANCE.setValue(player, FADataProcessors.ANTI_DRUNK, 0);
                        if (existingTipsyLvl == 0) {
                            player.addEffect(new MobEffectInstance(BCEffects.TIPSY.get(), (int) (duration * 0.5), lvl - antiDrunk - 1));
                        } else {
                            player.addEffect(new MobEffectInstance(BCEffects.TIPSY.get(), (int) (existingTipsyDur + (duration * 0.5)), existingTipsyLvl + lvl - antiDrunk - 2));
                        }
                    }
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityDamaged(AttackEntityEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getTarget();

        if (target instanceof BananaPeel bananaPeel) {
            // Spawn the ItemEntity with the Banana Peel item
            ItemEntity itemEntity = new ItemEntity(player.level, bananaPeel.getX(), bananaPeel.getY(), bananaPeel.getZ(), new ItemStack(FAItems.BANANA_PEEL.get()));
            player.level.addFreshEntity(itemEntity);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // TODO: Find a better solution. This requires suppressing command feedback which has its own consequences
        Player player = event.getPlayer();
        String commandToExecute = "/gamerule doMorphDrops false";
        CommandSourceStack commandSource = player.createCommandSourceStack();
        commandSource.getServer().getCommands().performCommand(commandSource, commandToExecute);
    }
}
