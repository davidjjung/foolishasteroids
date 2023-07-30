package com.davigj.foolish_asteroids.core.registry;

import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FoolishAsteroidsBlocks {
	public static final BlockSubRegistryHelper HELPER = FoolishAsteroidsMod.REGISTRY_HELPER.getBlockSubHelper();

	public static final RegistryObject<Block> TEMPLATE_BLOCK = HELPER.createBlock("template_block", () -> new Block(Block.Properties.copy(Blocks.STONE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
}
