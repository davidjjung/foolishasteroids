package com.davigj.foolish_asteroids.core.other;

import com.brewinandchewin.core.registry.BCEffects;
import com.davigj.foolish_asteroids.common.item.elixir.HeresyElixirItem;
import com.davigj.foolish_asteroids.common.util.HearsayUtil;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsParticleTypes;
import com.davigj.foolish_asteroids.core.util.FoolishAsteroidsDamageSources;
import com.github.alexthe666.alexsmobs.entity.util.RainbowUtil;
import com.teamabnormals.autumnity.core.registry.AutumnityParticleTypes;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.neapolitan.common.entity.projectile.BananaPeel;
import net.mehvahdjukaar.supplementaries.setup.ModRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.init.GBlocks;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

import static com.davigj.foolish_asteroids.common.item.elixir.HearsayElixirItem.oracleMap;
import static com.davigj.foolish_asteroids.common.item.elixir.IncendiaryElixirItem.smokingPlayers;
import static com.davigj.foolish_asteroids.common.item.elixir.IndomitableElixirItem.rainbowTimers;
import static com.davigj.foolish_asteroids.common.item.elixir.ProfoundElixirItem.chatDisableMap;
import static com.davigj.foolish_asteroids.common.util.HearsayUtil.conversations;


@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID)
public class FoolishAsteroidsEvents {
    // TODO: seraphic elixirs are acting up again

    private static final Logger LOGGER = Logger.getLogger(FoolishAsteroidsEvents.class.getName());
    static TrackedDataManager manager = TrackedDataManager.INSTANCE;

    @SubscribeEvent
    public static void playerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();
        if (player.getItemInHand(event.getHand()).getItem() == Items.GLASS_BOTTLE && event.getTarget() instanceof Ghast ghast
                && manager.getValue(ghast, FoolishAsteroidsMod.BLUSTER_RECHARGE) == 0 && event.getPlayer() != null && !player.getLevel().isClientSide()) {
            InteractionHand hand = event.getHand();
            manager.setValue(ghast, FoolishAsteroidsMod.BLUSTER_RECHARGE, 10);
            if (hand == InteractionHand.MAIN_HAND) {
                manager.setValue(player, FoolishAsteroidsMod.BLUSTER_HARVEST, 4);
            } else {
                manager.setValue(player, FoolishAsteroidsMod.BLUSTER_HARVEST, 2);
            }
            System.out.println(hand);
        }
    }

    @SubscribeEvent
    public static void playerUse(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        if (player.getItemInHand(event.getHand()).getItem() == ModRegistry.SOAP.get()
                && player.getItemBySlot(EquipmentSlot.FEET).getItem() == FoolishAsteroidsItems.BUBBLEBOOTS.get()) {
            ItemStack armorStack = player.getItemBySlot(EquipmentSlot.FEET);
            armorStack.setDamageValue(0);
            ItemStack handStack = player.getItemInHand(event.getHand());
            handStack.shrink(1);
        }
    }

    private static String updateSenderID(ItemStack itemStack, String currentSenderID) {
        CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains("display", 10)) {
            CompoundTag displayTag = tag.getCompound("display");
            if (displayTag.contains("Name", 8)) {
                String nameJson = displayTag.getString("Name");
                try {
                    Component nameComponent = Component.Serializer.fromJson(nameJson);
                    if (nameComponent instanceof TextComponent) {
                        return "<" + ((TextComponent) nameComponent).getText() + "> ";
                    }
                } catch (Exception e) {
                    // Handle deserialization error
                }
            }
        }
        return currentSenderID; // If no valid name found, return the current senderID
    }


    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        String senderID = "<" + event.getUsername() + "> ";
        String trueSenderID = "<" + event.getUsername() + "> ";

        boolean gab = false;
        ItemStack handItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();
        if (handItem.is(FoolishAsteroidsItems.GIFT_OF_GAB.get())
                || offHandItem.is(FoolishAsteroidsItems.GIFT_OF_GAB.get())) {
            gab = true;
        }
        if (gab) {
            if (handItem.is(FoolishAsteroidsItems.GIFT_OF_GAB.get())) {
                senderID = updateSenderID(handItem, senderID);
            } else {
                senderID = updateSenderID(offHandItem, senderID);
            }
            ItemStack usedItem = handItem.is(FoolishAsteroidsItems.GIFT_OF_GAB.get()) ? handItem : offHandItem;
            usedItem.hurtAndBreak(1, player, (entity) -> {
                entity.broadcastBreakEvent(handItem.isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND);
            });
            if (usedItem.isEmpty()) {
                // If the item is depleted, remove it
                player.getInventory().removeItem(usedItem);
            }
        } else {
            senderID = "<" + event.getUsername() + "> ";
        }

        TextComponent original = (TextComponent) new TextComponent(senderID).append(event.getMessage());
        TranslatableComponent modified = new TranslatableComponent(HearsayUtil.getDialogueLine(player).getKey(), senderID);
        if (chatDisableMap.containsKey(player)) {
            event.setCanceled(true);
            TranslatableComponent message = new TranslatableComponent("message.profound.chat_disabled");
            player.displayClientMessage(message, true);
        } else if (oracleMap.containsKey(player)) {
            for (ServerPlayer receiver : Objects.requireNonNull(player.level.getServer()).getPlayerList().getPlayers()) {
                if (!oracleMap.containsKey(receiver) || receiver == player) {
                    receiver.sendMessage(original, receiver.getUUID());
                } else {
                    receiver.sendMessage(modified, receiver.getUUID());
                    int dialogueIndex = TrackedDataManager.INSTANCE.getValue(receiver, FoolishAsteroidsMod.DIALOGUE_INDEX) + 1;
                    int convoIndex = TrackedDataManager.INSTANCE.getValue(receiver, FoolishAsteroidsMod.CONVO_INDEX);
                    TrackedDataManager.INSTANCE.setValue(receiver, FoolishAsteroidsMod.DIALOGUE_INDEX, dialogueIndex);
                    if (dialogueIndex == conversations[convoIndex].length) {
                        Random random = new Random();
                        TrackedDataManager.INSTANCE.setValue(receiver, FoolishAsteroidsMod.DIALOGUE_INDEX, 0);
                        TrackedDataManager.INSTANCE.setValue(receiver, FoolishAsteroidsMod.CONVO_INDEX, random.nextInt(3));
                    }
                }
            }
        } else {
            for (ServerPlayer receiver : Objects.requireNonNull(player.level.getServer()).getPlayerList().getPlayers()) {
                if (oracleMap.containsKey(receiver)) {
                    receiver.sendMessage(modified, receiver.getUUID());
                    int dialogueIndex = TrackedDataManager.INSTANCE.getValue(receiver, FoolishAsteroidsMod.DIALOGUE_INDEX) + 1;
                    int convoIndex = TrackedDataManager.INSTANCE.getValue(receiver, FoolishAsteroidsMod.CONVO_INDEX);
                    TrackedDataManager.INSTANCE.setValue(receiver, FoolishAsteroidsMod.DIALOGUE_INDEX, dialogueIndex);
                    if (dialogueIndex == conversations[convoIndex].length) {
                        Random random = new Random();
                        TrackedDataManager.INSTANCE.setValue(receiver, FoolishAsteroidsMod.DIALOGUE_INDEX, 0);
                        TrackedDataManager.INSTANCE.setValue(receiver, FoolishAsteroidsMod.CONVO_INDEX, random.nextInt(3));
                    }
                } else {
                    receiver.sendMessage(original, receiver.getUUID());
                }
            }
        }
        event.setCanceled(true);
        // TODO: Send a message that is ONLY visible in server logs, using trueSenderID with the original contents of the message.
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.START && player != null && !player.level.isClientSide) {
            UUID playerId = player.getUUID();
            if (rainbowTimers.containsKey(playerId)) {
                int timerTicks = rainbowTimers.get(playerId);
                if (timerTicks > 0) {
                    timerTicks--;
                    rainbowTimers.put(playerId, timerTicks);
                    if (timerTicks == 0) {
                        // Rainbow effect duration has reached zero, remove the rainbow effect
                        RainbowUtil.setRainbowType(player, 0);
                        rainbowTimers.remove(playerId);
                    }
                }
            }
            if (manager.getValue(player, FoolishAsteroidsMod.SERAPHIC_ACTIVE) && player.tickCount % 20 == 0) {
                Vec3 lookDirection = player.getLookAngle();
                double horizontalAngle = Math.atan2(lookDirection.z, lookDirection.x);
                double degree = Math.toDegrees(horizontalAngle);
                degree = (degree + 360) % 360;
                boolean sinfulGaze = false;
                switch (manager.getValue(player, FoolishAsteroidsMod.SERAPHIC_DIR)) {
                    case 0 -> {
                        if (degree >= 180 && degree < 360) sinfulGaze = true;
                    }
                    case 1 -> {
                        if (degree >= 270 || degree < 90) sinfulGaze = true;
                    }
                    case 2 -> {
                        if (degree >= 0 && degree < 180) sinfulGaze = true;
                    }
                    case 3 -> {
                        if (degree >= 90 && degree < 270) sinfulGaze = true;
                    }
                }
                if (sinfulGaze) {
                    player.hurt(FoolishAsteroidsDamageSources.SERAPHIC, 6.0f);
                    TranslatableComponent message = new TranslatableComponent("message.seraphic.damage");
                    player.displayClientMessage(message, true);
                }

            }
        }
        if (event.phase == TickEvent.Phase.END && !player.level.isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) event.player;
            Long chatDisableTime = chatDisableMap.get(serverPlayer);
            Long oracleTime = oracleMap.get(serverPlayer);

            // If the player's chat is disabled and the specified time has elapsed, re-enable chat
            if (chatDisableTime != null && System.currentTimeMillis() >= chatDisableTime) {
                TranslatableComponent message = new TranslatableComponent("message.profound.chat_enabled");
                serverPlayer.displayClientMessage(message, true);
                chatDisableMap.remove(serverPlayer);
            }

            Long oracleMapTime = oracleMap.get(serverPlayer);
            if (oracleTime != null && System.currentTimeMillis() >= oracleMapTime) {
                TranslatableComponent message = new TranslatableComponent("message.hearsay.use_end");
                serverPlayer.displayClientMessage(message, true);
                TrackedDataManager.INSTANCE.setValue(serverPlayer, FoolishAsteroidsMod.HEARSAY_ACTIVE, false);
                oracleMap.remove(serverPlayer);
            }
            if (player.tickCount % 20 == 0 && manager.getValue(player, FoolishAsteroidsMod.AUTUMNAL)) {
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 0, false, false));
            }
            int blusterHarvest = manager.getValue(player, FoolishAsteroidsMod.BLUSTER_HARVEST);
            ItemStack stack;
            switch (blusterHarvest) {
                case 4, 2 -> manager.setValue(player, FoolishAsteroidsMod.BLUSTER_HARVEST, blusterHarvest - 1);
                case 3 ->  {
                    stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                    Level level = player.getLevel();
                    if (stack.is(Items.GLASS_BOTTLE)) {
                        stack.shrink(1);
                        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BUCKET_FILL_POWDER_SNOW, SoundSource.NEUTRAL, 1.0F, 1.0F);
                        if (stack.isEmpty()) {
                            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(FoolishAsteroidsItems.BLUSTER_BOTTLE.get()));
                        } else if (!player.getInventory().add(new ItemStack(FoolishAsteroidsItems.BLUSTER_BOTTLE.get()))) {
                            player.drop(new ItemStack(FoolishAsteroidsItems.BLUSTER_BOTTLE.get()), false);
                        }
                    }
                    manager.setValue(player, FoolishAsteroidsMod.BLUSTER_HARVEST, 0);
                }
                case 1 -> {
                    stack = player.getItemInHand(InteractionHand.OFF_HAND);
                    Level level = player.getLevel();
                    if (stack.is(Items.GLASS_BOTTLE)) {
                        stack.shrink(1);
                        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BUCKET_FILL_POWDER_SNOW, SoundSource.NEUTRAL, 1.0F, 1.0F);
                        if (stack.isEmpty()) {
                            player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(FoolishAsteroidsItems.BLUSTER_BOTTLE.get()));
                        } else if (!player.getInventory().add(new ItemStack(FoolishAsteroidsItems.BLUSTER_BOTTLE.get()))) {
                            player.drop(new ItemStack(FoolishAsteroidsItems.BLUSTER_BOTTLE.get()), false);
                        }
                    }
                    manager.setValue(player, FoolishAsteroidsMod.BLUSTER_HARVEST, 0);
                }
                default -> { break; }
            }
        }
        if (event.phase == TickEvent.Phase.START && player != null) {
            UUID playerId = player.getUUID();
            ScaleData atk = ScaleTypes.ATTACK.getScaleData(player);
            if (smokingPlayers.containsKey(playerId)) {
                int remainingTicks = smokingPlayers.get(playerId);
                if (remainingTicks > 0) {
                    float atkVal = atk.getBaseScale();
                    if (!player.level.isClientSide()) {
                        remainingTicks--;
                        smokingPlayers.put(playerId, remainingTicks);
                        if (remainingTicks <= 400 && remainingTicks >= 201) {
                            atk.setTargetScale(atkVal + 0.006f);
                        } else if (remainingTicks < 200) {
                            atk.setTargetScale(atkVal * 1.02f);
                        } else {
                            atk.setTargetScale(atkVal + 0.01f);
                        }
                    }
                    ParticleOptions particle;
                    if (remainingTicks <= 400 && remainingTicks >= 201) {
                        particle = ParticleTypes.POOF;
                    } else if (remainingTicks < 200) {
                        particle = ParticleTypes.CAMPFIRE_COSY_SMOKE;
                    } else {
                        particle = ParticleTypes.SMOKE;
                    }
                    // Spawn campfire smoke particles
                    Level level = player.level;
                    Random random = new Random();
                    double x = player.getX();
                    double y = player.getY() + player.getEyeHeight();
                    double z = player.getZ();
                    if (random.nextInt(10) >= 4 && !player.isSpectator() && !player.isCreative() && player.level.isClientSide()) {
                        if (random.nextInt(10) == 1) {
                            player.playSound(SoundEvents.FURNACE_FIRE_CRACKLE, 1, 1);
                        }
                        level.addParticle(particle, x, y, z, 0.0D, 0.0D, 0.0D);
                    }
                    if (remainingTicks < 10) {
                        player.setSecondsOnFire(8);
                        atk.setTargetScale(0.25f);
                    }
                    if (player.isInWaterRainOrBubble() || player.isInPowderSnow) {
                        atk.setTargetScale(atkVal * 0.75f);
                        player.playSound(SoundEvents.FIRE_EXTINGUISH, 1, 1);
                        smokingPlayers.remove(playerId);
                    }
                } else {
                    smokingPlayers.remove(playerId);
                }
            }
            int rads = manager.getValue(player, FoolishAsteroidsMod.STORED_ELECTRONS);
            if (rads > 0) {
                Level level = player.level;
                Random random = new Random();
                double x = player.getX();
                double y = player.getY() + player.getEyeHeight() - (ScaleTypes.HEIGHT.getScaleData(player).getBaseScale() / 2);
                double z = player.getZ();
                if (random.nextInt(40) <= rads && !player.isSpectator() && !player.isCreative()) {
                    level.addParticle(FoolishAsteroidsParticleTypes.ELECTRON.get(), x, y, z,
                            10, 3, 10);
                }
            }
            if (player.tickCount % 5 == 0 && manager.getValue(player, FoolishAsteroidsMod.AUTUMNAL)) {
                Random rand = new Random();
                rand.nextInt(1);
                double x = player.getX() - 0.5;
                double y = player.getY();
                double z = player.getZ() - 0.5;
                Level level = player.level;
                BlockPos pos = player.blockPosition();
                int color = ((Biome) level.m_204166_(pos).m_203334_()).getFoliageColor();
                double d0 = (double) ((float) (color >> 16 & 255) / 255.0F);
                double d1 = (double) ((float) (color >> 8 & 255) / 255.0F);
                double d2 = (double) ((float) (color & 255) / 255.0F);
                double d3 = (double) ((float) x + rand.nextFloat());
                double d6 = (double) ((float) z + rand.nextFloat());
                level.addParticle((ParticleOptions) AutumnityParticleTypes.FALLING_MAPLE_LEAF.get(), d3, y + 0.025, d6, d0, d1, d2);
            }
            int bluster = manager.getValue(player, FoolishAsteroidsMod.BLUSTER_HARVEST);
            if (bluster == 3) {
                player.swing(InteractionHand.MAIN_HAND);
            } else if (bluster == 1) {
                player.swing(InteractionHand.OFF_HAND);
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
        }
        if (playerVictim && manager.getValue(player, FoolishAsteroidsMod.RAD_POISONING) && !source.isMagic()) {
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1));
        }
        if (playerVictim && manager.getValue(player, FoolishAsteroidsMod.STORED_ELECTRONS) > 0 && !source.isMagic()) {
            double explosionX = player.getX();
            double explosionY = player.getY() + player.getEyeHeight();
            double explosionZ = player.getZ();
            float explosionPower = 1.3f; // Adjust explosion power as needed
            int electrons = manager.getValue(player, FoolishAsteroidsMod.STORED_ELECTRONS);
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
            manager.setValue(player, FoolishAsteroidsMod.STORED_ELECTRONS, manager.getValue(player, FoolishAsteroidsMod.STORED_ELECTRONS) - 1);
            Random random = new Random();
            if (random.nextInt(5) <= 2) {
                manager.setValue(player, FoolishAsteroidsMod.RAD_POISONING, false);
            }
        }
        if (playerVictim && manager.getValue(player, FoolishAsteroidsMod.AUTUMNAL)) {
            // TODO: Play sound of magical wind dispelling
            manager.setValue(player, FoolishAsteroidsMod.AUTUMNAL, false);
        }
        if (playerVictim && event.getSource().equals(DamageSource.LIGHTNING_BOLT)) {
            // Check if the player is holding a glass bottle in either hand
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();

            if (mainHandItem.is(Items.GLASS_BOTTLE) || offHandItem.is(Items.GLASS_BOTTLE)) {
                // Turn the glass bottle into a lightning bottle
                ItemStack lightningBottle = new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()); // Replace with your lightning bottle item
                if (mainHandItem.is(Items.GLASS_BOTTLE)) {
                    player.setItemInHand(InteractionHand.MAIN_HAND, lightningBottle);
                } else {
                    player.setItemInHand(InteractionHand.OFF_HAND, lightningBottle);
                }
                // Consume the lightning bolt damage
                event.setCanceled(true);
            }
        }
        if (playerVictim && rainbowTimers.containsKey(player.getUUID())) {
            event.setCanceled(true);
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
            spider.setPos(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
            event.getEntity().level.addFreshEntity(spider);
        }
    }


    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            smokingPlayers.remove(player.getUUID());
            rainbowTimers.remove(player.getUUID());
            oracleMap.remove(player.getUUID());
        }
        if (event.getSource().equals(FoolishAsteroidsDamageSources.SERAPHIC) && event.getEntity() instanceof Player player) {
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
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();

        if (entity instanceof Witch && !entity.level.isClientSide()) {
            // Only apply logic on the server side for Witches

            if (entity.tickCount % 20 == 0) {
                // Check every second (20 ticks) to reduce frequency

                Entity target = ((Witch) entity).getTarget();
                if (target instanceof Player) {
                    Player player = (Player) target;
                    if (player.getMainHandItem().getItem() instanceof HeresyElixirItem ||
                            player.getOffhandItem().getItem() instanceof HeresyElixirItem) {
                        // Cancel the event to prevent the Witch from attacking the player
                        ((Witch) entity).setTarget(null);
                    }
                }
            }
        }
        if (entity instanceof Ghast ghast && entity.tickCount % 20 == 0) {
            int blusterTimer = manager.getValue(ghast, FoolishAsteroidsMod.BLUSTER_RECHARGE);
            if (blusterTimer != 0) {
                manager.setValue(ghast, FoolishAsteroidsMod.BLUSTER_RECHARGE, blusterTimer - 1);
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (manager.getValue(player, FoolishAsteroidsMod.HIGHWAY_TO_HELL) != 0) {
            manager.setValue(player, FoolishAsteroidsMod.HIGHWAY_TO_HELL, 0);
            TranslatableComponent message = new TranslatableComponent("message.hearsay.forgiveness");
            event.getPlayer().displayClientMessage(message, true);
        } else if (manager.getValue(player, FoolishAsteroidsMod.SERAPHIC_ACTIVE)) {
            manager.setValue(player, FoolishAsteroidsMod.SERAPHIC_ACTIVE, false);
        }
        manager.setValue(player, FoolishAsteroidsMod.STORED_ELECTRONS, 0);
        manager.setValue(player, FoolishAsteroidsMod.RAD_POISONING, false);
        manager.setValue(player, FoolishAsteroidsMod.AUTUMNAL, false);
        manager.setValue(player, FoolishAsteroidsMod.ANTI_DRUNK, 0);
    }

    @SubscribeEvent
    public static void onPotionAdded(PotionEvent.PotionApplicableEvent event) {
        if (!event.getEntity().level.isClientSide()) {
            if (event.getPotionEffect().getEffect() == BCEffects.TIPSY.get() && event.getEntity() instanceof Player player) {
                MobEffectInstance tipsy = event.getPotionEffect();
                int antiDrunk = TrackedDataManager.INSTANCE.getValue(player, FoolishAsteroidsMod.ANTI_DRUNK);
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
                        TrackedDataManager.INSTANCE.setValue(player, FoolishAsteroidsMod.ANTI_DRUNK, antiDrunk - lvl);
                    } else {
                        // the incoming tipsy exceeds antidrunk
                        TrackedDataManager.INSTANCE.setValue(player, FoolishAsteroidsMod.ANTI_DRUNK, 0);
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
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        // Check if the pressed key is the one you're interested in
    }

    @SubscribeEvent
    public static void onEntityDamaged(AttackEntityEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getTarget();

        if (target instanceof BananaPeel bananaPeel) {
            // Spawn the ItemEntity with the Banana Peel item
            ItemEntity itemEntity = new ItemEntity(player.level, bananaPeel.getX(), bananaPeel.getY(), bananaPeel.getZ(), new ItemStack(FoolishAsteroidsItems.BANANA_PEEL.get()));
            player.level.addFreshEntity(itemEntity);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        String commandToExecute = "/gamerule doMorphDrops false";
        CommandSourceStack commandSource = player.createCommandSourceStack();
        commandSource.getServer().getCommands().performCommand(commandSource, commandToExecute);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        ItemStack heldItem = player.getItemInHand(hand);
        BlockState clickedBlockState = player.level.getBlockState(event.getPos());
        if (!player.level.isClientSide() && heldItem.getItem() == Items.GLASS_BOTTLE && clickedBlockState.getBlock() == GBlocks.CHARGED_LUMIERE_BLOCK.get()) {
            if (hand == InteractionHand.MAIN_HAND || (hand == InteractionHand.OFF_HAND && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.GLASS_BOTTLE)) {
                if (!player.isCreative()) {
                    player.getMainHandItem().shrink(1);  // Decrease the glass bottle stack size
                    if (!player.getAbilities().instabuild) {
                        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            player.setItemInHand(player.getUsedItemHand(), new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()));
                        } else {
                            if (!player.getInventory().add(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()))) {
                                player.drop(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()), false);
                            }
                        }
                    }
                } else if (!player.getInventory().add(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()))) {
                    player.drop(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()), false);
                }
            } else if (hand == InteractionHand.OFF_HAND && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() != Items.GLASS_BOTTLE) {
                if (!player.isCreative()) {
                    player.getOffhandItem().shrink(1);  // Decrease the glass bottle stack size
                    if (!player.getAbilities().instabuild) {
                        ItemStack stack = player.getItemInHand(InteractionHand.OFF_HAND);
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            player.setItemInHand(player.getUsedItemHand(), new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()));
                        } else {
                            if (!player.getInventory().add(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()))) {
                                player.drop(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()), false);
                            }
                        }
                    }
                } else if (!player.getInventory().add(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()))) {
                    player.drop(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()), false);
                }
            }

            // Replace the grass block with a bottle of lightning
            player.level.setBlockAndUpdate(event.getPos(), GBlocks.LUMIERE_BLOCK.get().defaultBlockState());


            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);

        }
    }
}
