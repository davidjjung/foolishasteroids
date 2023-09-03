package com.davigj.foolish_asteroids.core.other.events;

import com.davigj.foolish_asteroids.common.util.HearsayUtil;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;
import java.util.Random;

import static com.davigj.foolish_asteroids.common.item.elixir.HearsayElixirItem.oracleMap;
import static com.davigj.foolish_asteroids.common.item.elixir.ProfoundElixirItem.chatDisableMap;
import static com.davigj.foolish_asteroids.common.util.HearsayUtil.conversations;

@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID)
public class ChatEvents {

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
}
