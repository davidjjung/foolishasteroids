package com.davigj.foolish_asteroids.core.registry;

import com.davigj.foolish_asteroids.common.item.DummyElixirItem;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FoolishAsteroidsItems {
	public static final ItemSubRegistryHelper HELPER = FoolishAsteroidsMod.REGISTRY_HELPER.getItemSubHelper();

	public static final RegistryObject<Item> FLASK = HELPER.createItem("flask", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));
	public static final RegistryObject<Item> DUMMY_ELIXIR = HELPER.createItem("dummy_elixir", () -> new DummyElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()).food(Elixirs.DUMMY_ELIXIR)));

	static class Elixirs {
		public static final FoodProperties DUMMY_ELIXIR = (new FoodProperties.Builder()).nutrition(0).saturationMod(0).build();
	}
}