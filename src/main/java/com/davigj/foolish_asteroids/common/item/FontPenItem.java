package com.davigj.foolish_asteroids.common.item;

import net.mehvahdjukaar.supplementaries.common.block.tiles.HangingSignBlockTile;
import net.mehvahdjukaar.supplementaries.common.block.util.TextHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;

public class FontPenItem extends Item {
    private static final int MAX_INK = 250;
    private final ResourceLocation font;
    public FontPenItem(Properties p_41383_, ResourceLocation location) {
        super(p_41383_);
        this.font = location;
    }
    public int getBarColor(ItemStack stack) {
        return 3093034;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack pen = player.getItemInHand(hand);
        ItemStack stackToRename = (hand == InteractionHand.MAIN_HAND ? player.getOffhandItem() : player.getMainHandItem());

        if (!stackToRename.isEmpty() && stackToRename.getHoverName() instanceof MutableComponent displayName) {
            if (stackToRename.getItem() != Items.WRITTEN_BOOK) {
                if (!doesFontMatch(displayName)) {
                    displayName.withStyle(Style.EMPTY.withFont(font));
                    // TODO: Scribble sound
//            player.playSound(insert_sound_here, 1.0F, 1.0F);
                    stackToRename.setHoverName(displayName);
                    return InteractionResultHolder.success(pen);
                }
            } else if (stackToRename.getItem() instanceof WrittenBookItem) {
                // Set the title of the book and all the words in the pages to be the relevant font
                if (stackToRename.hasTag()) {
                    stackToRename.setHoverName(applyFont(stackToRename.getHoverName(), font));
                    CompoundTag stackTag = stackToRename.getTag();
                    if (stackTag != null) {
                        ListTag pageData = stackTag.getList("pages", 8);

                        for(int pageNum = 0; pageNum < pageData.size(); ++pageNum) {
                            Component pageText = Component.Serializer.fromJsonLenient(pageData.getString(pageNum));
                            applyFont(pageText, font);
                            pageData.set(pageNum, StringTag.valueOf(Component.Serializer.toJson(pageText)));
                        }

                        stackTag.put("pages", pageData);
                    }
                }
            }
        }
        return InteractionResultHolder.pass(pen);
    }

    private boolean doesFontMatch(MutableComponent textComponent) {
        return textComponent.getStyle().getFont() != null
                && textComponent.getStyle().getFont().equals(font);
    }

    private static Component applyFont(Component text, ResourceLocation font) {
        if (text instanceof MutableComponent mutable) {
            mutable.setStyle(text.getStyle().withFont(font));
        }
        text.getSiblings().forEach((sib) -> {
            applyFont(sib, font);
        });
        return text;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Entity entity = context.getPlayer();

        if (entity != null && entity instanceof Player player) {
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if (blockEntity instanceof SignBlockEntity) {
                SignBlockEntity sign = (SignBlockEntity) blockEntity;
                if (applyFontToSign(sign)) {
                    if (!world.isClientSide) {
                        player.swing(context.getHand());
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    private boolean applyFontToSign(SignBlockEntity sign) {
        Component[] lines = sign.getMessages(true);
        for (int i = 0; i < lines.length; i++) {
            Component line = lines[i];
            if (line instanceof MutableComponent mutableLine) {
                if (!doesFontMatch(mutableLine)) {
                    Style newStyle = mutableLine.getStyle().withFont(font);
                    MutableComponent newLine = mutableLine.copy().setStyle(newStyle);
                    sign.setMessage(i, newLine);
                }
            }
        }
        return true;
    }
}
