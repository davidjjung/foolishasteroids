package com.davigj.foolish_asteroids.core.registry;

import com.davigj.foolish_asteroids.common.item.CommandElixirItem;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FoolishAsteroidsItems {
	public static final ItemSubRegistryHelper HELPER = FoolishAsteroidsMod.REGISTRY_HELPER.getItemSubHelper();

	public static final RegistryObject<Item> FLASK = HELPER.createItem("flask", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));

	// Mundane Elixirs
	public static final RegistryObject<Item> ARCADIAN_ELIXIR = HELPER.createItem("arcadian_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()).food(Elixirs.ELIXIR), CommandLists.ARCADIAN));

	public static final RegistryObject<Item> BELLICOSE_ELIXIR = HELPER.createItem("bellicose_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()).food(Elixirs.ELIXIR), CommandLists.BELLICOSE));

	public static final RegistryObject<Item> DILATORY_ELIXIR = HELPER.createItem("dilatory_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()).food(Elixirs.ELIXIR), CommandLists.DILATORY));

	public static final RegistryObject<Item> ESTRANGED_ELIXIR = HELPER.createItem("estranged_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()).food(Elixirs.ESTRANGED_ELIXIR), CommandLists.ESTRANGED));

	public static final RegistryObject<Item> JEJUNE_ELIXIR = HELPER.createItem("jejune_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()).food(Elixirs.ELIXIR), CommandLists.JEJUNE));

	public static final RegistryObject<Item> MYCOLOGICAL_ELIXIR = HELPER.createItem("mycological_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()).food(Elixirs.MYCOLOGICAL_ELIXIR), CommandLists.MYCOLOGICAL));

	public static final RegistryObject<Item> PARADOXICAL_ELIXIR = HELPER.createItem("paradoxical_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()).food(Elixirs.ELIXIR), CommandLists.PARADOXICAL));

	public static final RegistryObject<Item> PIQUANT_ELIXIR = HELPER.createItem("piquant_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()).food(Elixirs.ELIXIR), CommandLists.PIQUANT));

	static class Elixirs {
		public static final FoodProperties ELIXIR = (new FoodProperties.Builder()).nutrition(0).saturationMod(0).alwaysEat().build();
		public static final FoodProperties ESTRANGED_ELIXIR = (new FoodProperties.Builder()).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 60), 1.0F).nutrition(0).saturationMod(0).alwaysEat().build();
		public static final FoodProperties MYCOLOGICAL_ELIXIR = (new FoodProperties.Builder()).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 80), 1.0F).nutrition(0).saturationMod(0).alwaysEat().build();
	}

	static class CommandLists {
		public static final List<String> ARCADIAN = new ArrayList<>(List.of(
				"/scale set pehkui:motion 1.2 ",
				"/scale set pehkui:block_reach 1.4 ",
				"/scale set pehkui:base 1.1 "
				));
		public static final List<String> BELLICOSE = new ArrayList<>(List.of(
				"/scale set pehkui:jump_height 2.2 ",
				"/scale set pehkui:height 0.9 "
				));
		public static final List<String> DILATORY = new ArrayList<>(List.of(
				"/scale set pehkui:eye_height 3 ",
				"/scale set pehkui:third_person 2.5 ",
				"/scale set pehkui:visibility 3.0 "
				));
		public static final List<String> ESTRANGED = new ArrayList<>(List.of(
				"/scale set pehkui:model_height 1.4 ",
				"/scale set pehkui:model_width 0.8 ",
				"/scale set pehkui:eye_height 1.4 "
				));
		public static final List<String> JEJUNE = new ArrayList<>(List.of(
				"/scale set pehkui:step_height 2.0 ",
				"/scale set pehkui:hitbox_height 2.0 ",
				"/scale set pehkui:motion 0.75 "
				));
		public static final List<String> MYCOLOGICAL = new ArrayList<>(List.of(
				"/scale set pehkui:width 1.2 ",
				"/scale set pehkui:height 1.2 ",
				"/scale set pehkui:model_height 1.2 ",
				"/scale set pehkui:model_width 1.2 "
				));
		public static final List<String> PARADOXICAL = new ArrayList<>(List.of(
				"/scale set pehkui:projectiles 1.4 ",
				"/scale set pehkui:held_item 1.4 ",
				"/scale set pehkui:drops 1.4 "
				));
		public static final List<String> PIQUANT = new ArrayList<>(List.of(
				"/scale set pehkui:height 0.7 ",
				"/scale set pehkui:width 0.7 ",
				"/scale set pehkui:held_item 0.4 ",
				"/scale set pehkui:drops 0.4 "
				));
	}
}