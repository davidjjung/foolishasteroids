package com.davigj.foolish_asteroids.core.registry;

import com.davigj.foolish_asteroids.common.item.*;
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
	public static final RegistryObject<Item> BELLICOSE_ELIXIR = HELPER.createItem("bellicose_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.BELLICOSE));

	public static final RegistryObject<Item> DILATORY_ELIXIR = HELPER.createItem("dilatory_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.DILATORY));

	public static final RegistryObject<Item> ESTRANGED_ELIXIR = HELPER.createItem("estranged_elixir", () -> new EstrangedElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.ESTRANGED));

	public static final RegistryObject<Item> JEJUNE_ELIXIR = HELPER.createItem("jejune_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.JEJUNE));

	public static final RegistryObject<Item> MYCOLOGICAL_ELIXIR = HELPER.createItem("mycological_elixir", () -> new MycologicalElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.MYCOLOGICAL));

	public static final RegistryObject<Item> QUADRATIC_ELIXIR = HELPER.createItem("quadratic_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.QUADRATIC));

	public static final RegistryObject<Item> PIQUANT_ELIXIR = HELPER.createItem("piquant_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.PIQUANT));

	public static final RegistryObject<Item> BUCOLIC_ELIXIR = HELPER.createItem("bucolic_elixir", () -> new BucolicElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> SAGACIOUS_ELIXIR = HELPER.createItem("sagacious_elixir", () -> new SagaciousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));


	// Awkward Elixirs
	public static final RegistryObject<Item> CLAUSTROPHILIC_ELIXIR = HELPER.createItem("claustrophilic_elixir", () -> new ClaustrophilicElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> ENDEMIC_ELIXIR = HELPER.createItem("endemic_elixir", () -> new EndemicElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> EVANESCENT_ELIXIR = HELPER.createItem("evanescent_elixir", () -> new EvanescentElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.EVANESCENT));

	public static final RegistryObject<Item> FRIABLE_ELIXIR = HELPER.createItem("friable_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.FRIABLE));

	public static final RegistryObject<Item> ICARIAN_ELIXIR = HELPER.createItem("icarian_elixir", () -> new IcarianElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> IMMERSIVE_ELIXIR = HELPER.createItem("immersive_elixir", () -> new ImmersiveElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> IRKSOME_ELIXIR = HELPER.createItem("irksome_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.IRKSOME));

	public static final RegistryObject<Item> JITTERY_ELIXIR = HELPER.createItem("jittery_elixir", () -> new JitteryElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> STENTORIAN_ELIXIR = HELPER.createItem("stentorian_elixir", () -> new StentorianElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> GRACIOUS_ELIXIR = HELPER.createItem("gracious_elixir", () -> new GraciousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));


	// Turbulent Elixirs
	public static final RegistryObject<Item> EMPYREAN_ELIXIR = HELPER.createItem("empyrean_elixir", () -> new EmpyreanElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> BENEVOLENT_ELIXIR = HELPER.createItem("benevolent_elixir", () -> new BenevolentElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> GARRULOUS_ELIXIR = HELPER.createItem("garrulous_elixir", () -> new GarrulousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> INDOMITABLE_ELIXIR = HELPER.createItem("indomitable_elixir", () -> new IndomitableElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> PROFOUND_ELIXIR = HELPER.createItem("profound_elixir", () -> new ProfoundElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> TENEBROUS_ELIXIR = HELPER.createItem("tenebrous_elixir", () -> new TenebrousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> VAINGLORIOUS_ELIXIR = HELPER.createItem("vainglorious_elixir", () -> new VaingloriousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> WRETCHED_ELIXIR = HELPER.createItem("wretched_elixir", () -> new WretchedElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> PARSIMONIOUS_ELIXIR = HELPER.createItem("parsimonious_elixir", () -> new ParsimoniousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));


	static class CommandLists {
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
				"/scale set pehkui:model_width 0.8 "
				));
		public static final List<String> JEJUNE = new ArrayList<>(List.of(
				"/scale set pehkui:step_height 2.0 ",
				"/scale set pehkui:motion 0.75 "
				));
		public static final List<String> MYCOLOGICAL = new ArrayList<>(List.of(
				"/scale set pehkui:width 1.2 ",
				"/scale set pehkui:height 1.2 ",
				"/scale set pehkui:model_height 1.2 ",
				"/scale set pehkui:model_width 1.2 "
				));
		public static final List<String> QUADRATIC = new ArrayList<>(List.of(
				"/scale set pehkui:projectiles 1.4 ",
				"/scale set pehkui:held_item 1.4 ",
				"/scale set pehkui:reach 0.6 ",
				"/scale set pehkui:drops 1.4 "
				));
		public static final List<String> PIQUANT = new ArrayList<>(List.of(
				"/scale set pehkui:height 0.7 ",
				"/scale set pehkui:width 0.7 ",
				"/scale set pehkui:held_item 0.4 ",
				"/scale set pehkui:drops 0.4 "
				));
		public static final List<String> FRIABLE = new ArrayList<>(List.of(
				"/scale set pehkui:height 0.4 ",
				"/scale set pehkui:width 0.4 ",
				"/scale set pehkui:motion 0.6 ",
				"/scale set pehkui:defense 0.5 ",
				"/scale set pehkui:attack 0.4 ",
				"/scale set pehkui:block_reach 3.0 ",
				"/scale set pehkui:jump_height 2.0 "
				));
		public static final List<String> EVANESCENT = new ArrayList<>(List.of(
				"/scale set pehkui:height 0.2 ",
				"/scale set pehkui:width 0.2 ",
				"/scale set pehkui:defense 0.5 "
				));
		public static final List<String> IRKSOME = new ArrayList<>(List.of(
				"/scale set pehkui:visibility 3.0 ",
				"/scale set pehkui:width 0.9 ",
				"/scale set pehkui:knockback 2.0 "
				));
	}
}