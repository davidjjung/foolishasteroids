package com.davigj.foolish_asteroids.core.registry;

import com.davigj.foolish_asteroids.common.item.MorphMedalItem;
import com.davigj.foolish_asteroids.common.item.elixir.*;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.resources.ResourceLocation;
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

	public static final RegistryObject<Item> MUNDANE_ELIXIR = HELPER.createItem("mundane_elixir", () -> new ElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> AWKWARD_ELIXIR = HELPER.createItem("awkward_elixir", () -> new ElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> TURBULENT_ELIXIR = HELPER.createItem("turbulent_elixir", () -> new ElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));


	// Mundane Elixirs
	public static final RegistryObject<Item> BELLICOSE_ELIXIR = HELPER.createItem("bellicose_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.BELLICOSE));
	public static final RegistryObject<Item> ESTRANGED_ELIXIR = HELPER.createItem("estranged_elixir", () -> new EstrangedElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.ESTRANGED));
	public static final RegistryObject<Item> MYCOLOGICAL_ELIXIR = HELPER.createItem("mycological_elixir", () -> new MycologicalElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.MYCOLOGICAL));
	public static final RegistryObject<Item> QUADRATIC_ELIXIR = HELPER.createItem("quadratic_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.QUADRATIC));
	public static final RegistryObject<Item> TRITURATED_ELIXIR = HELPER.createItem("triturated_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.TRITURATED));
	public static final RegistryObject<Item> BUCOLIC_ELIXIR = HELPER.createItem("bucolic_elixir", () -> new BucolicElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> SAGACIOUS_ELIXIR = HELPER.createItem("sagacious_elixir", () -> new SagaciousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> HEARSAY_ELIXIR = HELPER.createItem("hearsay_elixir", () -> new HearsayElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> HERESY_ELIXIR = HELPER.createItem("heresy_elixir", () -> new HeresyElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> PERSPICACIOUS_ELIXIR = HELPER.createItem("perspicacious_elixir", () -> new PerspicaciousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> SUSURROUS_ELIXIR = HELPER.createItem("susurrous_elixir", () -> new SusurrousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));


	// Awkward Elixirs
	public static final RegistryObject<Item> ATOMIZED_ELIXIR = HELPER.createItem("atomized_elixir", () -> new AtomizedElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> CLAUSTROPHILIC_ELIXIR = HELPER.createItem("claustrophilic_elixir", () -> new ClaustrophilicElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> DILATORY_ELIXIR = HELPER.createItem("dilatory_elixir", () -> new DilatoryElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> ENDEMIC_ELIXIR = HELPER.createItem("endemic_elixir", () -> new EndemicElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> EVANESCENT_ELIXIR = HELPER.createItem("evanescent_elixir", () -> new EvanescentElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.EVANESCENT));
	public static final RegistryObject<Item> FRIABLE_ELIXIR = HELPER.createItem("friable_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.FRIABLE));
	public static final RegistryObject<Item> ICARIAN_ELIXIR = HELPER.createItem("icarian_elixir", () -> new IcarianElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> EARTHBOUND_ELIXIR = HELPER.createItem("earthbound_elixir", () -> new EarthboundElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> INCENDIARY_ELIXIR = HELPER.createItem("incendiary_elixir", () -> new IncendiaryElixirItem(
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
	public static final RegistryObject<Item> SERAPHIC_ELIXIR = HELPER.createItem("seraphic_elixir", () -> new SeraphicElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> FREAKY_ELIXIR = HELPER.createItem("freaky_elixir", () -> new FreakyElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> LOQUACIOUS_ELIXIR = HELPER.createItem("loquacious_elixir", () -> new LoquaciousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> TENEBROUS_ELIXIR = HELPER.createItem("tenebrous_elixir", () -> new TenebrousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> VAINGLORIOUS_ELIXIR = HELPER.createItem("vainglorious_elixir", () -> new VaingloriousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> WRETCHED_ELIXIR = HELPER.createItem("wretched_elixir", () -> new WretchedElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> PARSIMONIOUS_ELIXIR = HELPER.createItem("parsimonious_elixir", () -> new ParsimoniousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> TOUCHY_ELIXIR = HELPER.createItem("touchy_elixir", () -> new TouchyElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	static class CommandLists {
		public static final List<String> BELLICOSE = new ArrayList<>(List.of(
				"/scale set pehkui:jump_height 2.2 ",
				"/scale set pehkui:height 0.9 "
				));
		public static final List<String> ESTRANGED = new ArrayList<>(List.of(
				"/scale set pehkui:model_height 1.4 ",
				"/scale set pehkui:model_width 0.8 "
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
				"/scale set pehkui:height 0.3 ",
				"/scale set pehkui:width 0.3 ",
				"/scale set pehkui:defense 0.5 "
				));
		public static final List<String> TRITURATED = new ArrayList<>(List.of(
				"/scale set pehkui:height 0.1 ",
				"/scale set pehkui:entity_reach 0.5 "
				));
		public static final List<String> IRKSOME = new ArrayList<>(List.of(
				"/scale set pehkui:visibility 3.0 ",
				"/scale set pehkui:width 0.9 ",
				"/scale set pehkui:knockback 2.0 "
				));
	}

	public static final RegistryObject<Item> COW_MEDAL = HELPER.createItem("cow_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:cow", 30));
	public static final RegistryObject<Item> PIG_MEDAL = HELPER.createItem("pig_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:pig", 30));
	public static final RegistryObject<Item> SHEEP_MEDAL = HELPER.createItem("sheep_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:sheep", 30));
	public static final RegistryObject<Item> TAPIR_MEDAL = HELPER.createItem("tapir_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "environmental:tapir", 30));
	public static final RegistryObject<Item> CHICKEN_MEDAL = HELPER.createItem("chicken_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:chicken", 30));
	public static final RegistryObject<Item> HORSE_MEDAL = HELPER.createItem("horse_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:horse", 30));
	public static final RegistryObject<Item> TURKEY_MEDAL = HELPER.createItem("turkey_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "autumnity:turkey", 30));
	public static final RegistryObject<Item> SQUIRREL_MEDAL = HELPER.createItem("squirrel_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "ecologics:squirrel", 30));
	public static final RegistryObject<Item> RABBIT_MEDAL = HELPER.createItem("rabbit_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:rabbit", 30));
	public static final RegistryObject<Item> BEE_MEDAL = HELPER.createItem("bee_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:bee", 120));
	public static final RegistryObject<Item> BUTTERFLY_MEDAL = HELPER.createItem("butterfly_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "naturalist:butterfly", 120));
	public static final RegistryObject<Item> CATERPILLAR_MEDAL = HELPER.createItem("caterpillar_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "naturalist:caterpillar", 30));
	public static final RegistryObject<Item> CATFISH_MEDAL = HELPER.createItem("catfish_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "alexsmobs:catfish", 60));
	public static final RegistryObject<Item> PERCH_MEDAL = HELPER.createItem("perch_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "upgrade_aquatic:perch", 60));
	public static final RegistryObject<Item> PIKE_MEDAL = HELPER.createItem("pike_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "upgrade_aquatic:perch", 60));
	public static final RegistryObject<Item> COD_MEDAL = HELPER.createItem("cod_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:cod", 60));
	public static final RegistryObject<Item> SALMON_MEDAL = HELPER.createItem("salmon_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:salmon", 60));
	public static final RegistryObject<Item> FLYING_FISH_MEDAL = HELPER.createItem("flying_fish_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "alexsmobs:flying_fish", 60));
	public static final RegistryObject<Item> PUFFERFISH_MEDAL = HELPER.createItem("pufferfish_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:pufferfish", 60));
	public static final RegistryObject<Item> GWIBLING = HELPER.createItem("gwibling_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "undergarden:gwibling", 60));
	public static final RegistryObject<Item> COSMIC_COD_MEDAL = HELPER.createItem("cosmic_cod_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "alexsmobs:cosmic_cod", 120));
	public static final RegistryObject<Item> BLAZE_MEDAL = HELPER.createItem("blaze_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:blaze", 120));
	public static final RegistryObject<Item> ENDERMAN_MEDAL = HELPER.createItem("enderman_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:enderman", 120));
	public static final RegistryObject<Item> SKELETON_MEDAL = HELPER.createItem("skeleton_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:skeleton", 120));
	public static final RegistryObject<Item> ZOMBIE_MEDAL = HELPER.createItem("zombie_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:zombie", 30));
	public static final RegistryObject<Item> HUSK_MEDAL = HELPER.createItem("husk_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:husk", 30));
	public static final RegistryObject<Item> GHAST_MEDAL = HELPER.createItem("ghast_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:ghast", 0));
	public static final RegistryObject<Item> FROSTBITTEN_MEDAL = HELPER.createItem("frostbitten_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "rottencreatures:frostbitten", 0));
	public static final RegistryObject<Item> DROWNED_MEDAL = HELPER.createItem("drowned_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:drowned", 0));
	public static final RegistryObject<Item> SUNBIRD_MEDAL = HELPER.createItem("sunbird_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "alexsmobs:sunbird", 0));
	public static final RegistryObject<Item> HIPPO_MEDAL = HELPER.createItem("hippo_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "alexsmobs:hippo", 0));
	public static final RegistryObject<Item> VILLAGER_MEDAL = HELPER.createItem("villager_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:villager", 0));
	public static final RegistryObject<Item> WANDERING_TRADER_MEDAL = HELPER.createItem("wandering_trader_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:wandering_trader", 0));
	public static final RegistryObject<Item> STRIDER_MEDAL = HELPER.createItem("strider_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:strider", 0));
	public static final RegistryObject<Item> GAZELLE_MEDAL = HELPER.createItem("gazelle_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "alexsmobs:gazelle", 0));
	public static final RegistryObject<Item> SEAL_MEDAL = HELPER.createItem("seal_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "alexsmobs:seal", 0));
	public static final RegistryObject<Item> ARMOR_STAND_MEDAL = HELPER.createItem("armor_stand_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:armor_stand", 0));
	public static final RegistryObject<Item> EASEL_MEDAL = HELPER.createItem("easel_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "zetter:easel_entity", 0));
	public static final RegistryObject<Item> BOAT_MEDAL = HELPER.createItem("boat_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:boat", 0));
	public static final RegistryObject<Item> SLED_MEDAL = HELPER.createItem("sled_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "snowyspirit:sled", 0));
	public static final RegistryObject<Item> MINECART_MEDAL = HELPER.createItem("minecart_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:minecart", 0));
	public static final RegistryObject<Item> TARGET_DUMMY_MEDAL = HELPER.createItem("target_dummy_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "dummmmmmy:target_dummy", 0));
	public static final RegistryObject<Item> MANNEQUIN_MEDAL = HELPER.createItem("mannequin_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "mannequins:mannequin", 0));
	public static final RegistryObject<Item> STATUE_MEDAL = HELPER.createItem("statue_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "mannequins:statue", 0));
	public static final RegistryObject<Item> WITHER_MEDAL = HELPER.createItem("wither_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:wither", 0));
	public static final RegistryObject<Item> SAND_BLOCK_MEDAL = HELPER.createItem("sand_block_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:falling_block", 0));
	public static final RegistryObject<Item> SNOW_GOLEM_MEDAL = HELPER.createItem("snow_golem_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:snow_golem", 0));
	public static final RegistryObject<Item> TURTLE_MEDAL = HELPER.createItem("turtle_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:turtle", 0));
	public static final RegistryObject<Item> TORTOISE_MEDAL = HELPER.createItem("tortoise_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "sullysmod:tortoise", 0));
	public static final RegistryObject<Item> TROPICAL_FISH_MEDAL = HELPER.createItem("tropical_fish_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:tropical_fish", 0));
	public static final RegistryObject<Item> TERRAPIN_MEDAL = HELPER.createItem("terrapin_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "alexsmobs:terrapin", 0));
	public static final RegistryObject<Item> GUARDIAN_MEDAL = HELPER.createItem("guardian_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:guardian", 0));
	public static final RegistryObject<Item> ELDER_GUARDIAN_MEDAL = HELPER.createItem("elder_guardian_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:elder_guardian", 0));
	public static final RegistryObject<Item> SLIME_MEDAL = HELPER.createItem("slime_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:slime", 0));
	public static final RegistryObject<Item> MAGMA_CUBE_MEDAL = HELPER.createItem("magma_cube_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:magma_cube", 0));
	public static final RegistryObject<Item> FOX_MEDAL = HELPER.createItem("fox_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:fox", 0));
	public static final RegistryObject<Item> CAT_MEDAL = HELPER.createItem("cat_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:cat", 0));
	public static final RegistryObject<Item> WOLF_MEDAL = HELPER.createItem("wolf_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:wolf", 0));
	public static final RegistryObject<Item> SHIBA_MEDAL = HELPER.createItem("shiba_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "quark:shiba", 0));
	public static final RegistryObject<Item> PIGLIN_MEDAL = HELPER.createItem("piglin_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:piglin", 0));
	public static final RegistryObject<Item> STONELING_MEDAL = HELPER.createItem("stoneling_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "quark:stoneling", 0));
	public static final RegistryObject<Item> SCINTLING_MEDAL = HELPER.createItem("scintling_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "undergarden:scintling", 0));
	public static final RegistryObject<Item> CREEPER_MEDAL = HELPER.createItem("creeper_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:creeper", 0));
//	public static final RegistryObject<Item> BAMBOO_CREEPER_MEDAL = HELPER.createItem("bamboo_creeper_medal", () ->
//			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "creeper_overhaul:bamboo_creeper", 0));
//	public static final RegistryObject<Item> DESERT_CREEPER_MEDAL = HELPER.createItem("desert_creeper_medal", () ->
//			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "creeper_overhaul:desert_creeper", 0));
//	public static final RegistryObject<Item> BADLANDS_CREEPER_MEDAL = HELPER.createItem("badlands_creeper_medal", () ->
//			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "creeper_overhaul:badlands_creeper", 0));
//	public static final RegistryObject<Item> HILLS_CREEPER_MEDAL = HELPER.createItem("hills_creeper_medal", () ->
//			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "creeper_overhaul:hills_creeper", 0));
//	public static final RegistryObject<Item> SAVANNAH_CREEPER_MEDAL = HELPER.createItem("savannah_creeper_medal", () ->
//			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "creeper_overhaul:savannah_creeper", 0));
//	public static final RegistryObject<Item> MUSHROOM_CREEPER_MEDAL = HELPER.createItem("mushroom_creeper_medal", () ->
//			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "creeper_overhaul:mushroom_creeper", 0));
//	public static final RegistryObject<Item> SWAMP_CREEPER_MEDAL = HELPER.createItem("swamp_creeper_medal", () ->
//			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "creeper_overhaul:swamp_creeper", 0));
//	public static final RegistryObject<Item> DRIPSTONE_CREEPER_MEDAL = HELPER.createItem("dripstone_creeper_medal", () ->
//			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "creeper_overhaul:dripstone_creeper", 0));
//	public static final RegistryObject<Item> DARK_OAK_CREEPER_MEDAL = HELPER.createItem("dark_oak_creeper_medal", () ->
//			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "creeper_overhaul:dark_oak_creeper", 0));
//	public static final RegistryObject<Item> SPRUCE_CREEPER_MEDAL = HELPER.createItem("spruce_creeper_medal", () ->
//			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "creeper_overhaul:spruce_creeper", 0));
//	public static final RegistryObject<Item> SNOWY_CREEPER_MEDAL = HELPER.createItem("snowy_creeper_medal", () ->
//			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "creeper_overhaul:snowy_creeper", 0));
//	public static final RegistryObject<Item> BEACH_CREEPER_MEDAL = HELPER.createItem("beach_creeper_medal", () ->
//			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "creeper_overhaul:beach_creeper", 0));






}