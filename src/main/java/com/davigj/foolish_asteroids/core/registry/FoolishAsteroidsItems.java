package com.davigj.foolish_asteroids.core.registry;

import com.davigj.foolish_asteroids.common.item.BananaPeelItem;
import com.davigj.foolish_asteroids.common.item.SunbirdFeatherItem;
import com.davigj.foolish_asteroids.common.item.medal.*;
import com.davigj.foolish_asteroids.common.item.elixir.*;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FoolishAsteroidsItems {
	public static final ItemSubRegistryHelper HELPER = FoolishAsteroidsMod.REGISTRY_HELPER.getItemSubHelper();

	public static final RegistryObject<Item> FLASK = HELPER.createItem("flask", () -> new FlaskItem(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));
	public static final RegistryObject<Item> VIRULENT_FLASK = HELPER.createItem("virulent_flask", () -> new VirulentFlaskItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	public static final RegistryObject<Item> MUNDANE_ELIXIR = HELPER.createItem("mundane_elixir", () -> new ElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> AWKWARD_ELIXIR = HELPER.createItem("awkward_elixir", () -> new ElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> TURBULENT_ELIXIR = HELPER.createItem("turbulent_elixir", () -> new ElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));

	// Mundane Elixirs
	public static final RegistryObject<Item> BELLICOSE_ELIXIR = HELPER.createItem("bellicose_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.BELLICOSE));
	public static final RegistryObject<Item> BUCOLIC_ELIXIR = HELPER.createItem("bucolic_elixir", () -> new BucolicElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> EARTHBOUND_ELIXIR = HELPER.createItem("earthbound_elixir", () -> new EarthboundElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> ESTRANGED_ELIXIR = HELPER.createItem("estranged_elixir", () -> new EstrangedElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.ESTRANGED));
	public static final RegistryObject<Item> EVANESCENT_ELIXIR = HELPER.createItem("evanescent_elixir", () -> new EvanescentElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.EVANESCENT));
	public static final RegistryObject<Item> IRKSOME_ELIXIR = HELPER.createItem("irksome_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.IRKSOME));
	public static final RegistryObject<Item> MYCOLOGICAL_ELIXIR = HELPER.createItem("mycological_elixir", () -> new MycologicalElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.MYCOLOGICAL));
	public static final RegistryObject<Item> PERSPICACIOUS_ELIXIR = HELPER.createItem("perspicacious_elixir", () -> new PerspicaciousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> QUADRATIC_ELIXIR = HELPER.createItem("quadratic_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.QUADRATIC));
	public static final RegistryObject<Item> SAGACIOUS_ELIXIR = HELPER.createItem("sagacious_elixir", () -> new SagaciousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> SUSURROUS_ELIXIR = HELPER.createItem("susurrous_elixir", () -> new SusurrousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> TRITURATED_ELIXIR = HELPER.createItem("triturated_elixir", () -> new CommandElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get()), CommandLists.TRITURATED));


	// Awkward Elixirs
	public static final RegistryObject<Item> ATOMIZED_ELIXIR = HELPER.createItem("atomized_elixir", () -> new AtomizedElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> BOUNDLESS_ELIXIR = HELPER.createItem("boundless_elixir", () -> new BoundlessElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> CLAUSTROPHILIC_ELIXIR = HELPER.createItem("claustrophilic_elixir", () -> new ClaustrophilicElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> DILATORY_ELIXIR = HELPER.createItem("dilatory_elixir", () -> new DilatoryElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> ENDEMIC_ELIXIR = HELPER.createItem("endemic_elixir", () -> new VirulentFlaskItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> GRACIOUS_ELIXIR = HELPER.createItem("gracious_elixir", () -> new GraciousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> HEARSAY_ELIXIR = HELPER.createItem("hearsay_elixir", () -> new HearsayElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> HERESY_ELIXIR = HELPER.createItem("heresy_elixir", () -> new HeresyElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> INCENDIARY_ELIXIR = HELPER.createItem("incendiary_elixir", () -> new IncendiaryElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> JITTERY_ELIXIR = HELPER.createItem("jittery_elixir", () -> new JitteryElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> STENTORIAN_ELIXIR = HELPER.createItem("stentorian_elixir", () -> new StentorianElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> VAINGLORIOUS_ELIXIR = HELPER.createItem("vainglorious_elixir", () -> new VaingloriousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));


	// Turbulent Elixirs
	public static final RegistryObject<Item> BENEVOLENT_ELIXIR = HELPER.createItem("benevolent_elixir", () -> new BenevolentElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> EMPYREAN_ELIXIR = HELPER.createItem("empyrean_elixir", () -> new EmpyreanElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> FREAKY_ELIXIR = HELPER.createItem("freaky_elixir", () -> new FreakyElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> GARRULOUS_ELIXIR = HELPER.createItem("garrulous_elixir", () -> new GarrulousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> INDOMITABLE_ELIXIR = HELPER.createItem("indomitable_elixir", () -> new IndomitableElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> LOQUACIOUS_ELIXIR = HELPER.createItem("loquacious_elixir", () -> new LoquaciousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> PARSIMONIOUS_ELIXIR = HELPER.createItem("parsimonious_elixir", () -> new ParsimoniousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> PROFOUND_ELIXIR = HELPER.createItem("profound_elixir", () -> new ProfoundElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> SERAPHIC_ELIXIR = HELPER.createItem("seraphic_elixir", () -> new SeraphicElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> TENEBROUS_ELIXIR = HELPER.createItem("tenebrous_elixir", () -> new TenebrousElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> TOUCHY_ELIXIR = HELPER.createItem("touchy_elixir", () -> new TouchyElixirItem(
			new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(FoolishAsteroidsItems.FLASK.get())));
	public static final RegistryObject<Item> WRETCHED_ELIXIR = HELPER.createItem("wretched_elixir", () -> new WretchedElixirItem(
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

	public static final RegistryObject<Item> BLANK_MEDALLION = HELPER.createItem("blank_medallion", () -> new BlankMedallionItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static final RegistryObject<Item> COW_MEDAL = HELPER.createItem("cow_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:cow", 30));
	public static final RegistryObject<Item> PIG_MEDAL = HELPER.createItem("pig_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:pig", 30));
	public static final RegistryObject<Item> SHEEP_MEDAL = HELPER.createItem("sheep_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:sheep", 30));
	public static final RegistryObject<Item> CHICKEN_MEDAL = HELPER.createItem("chicken_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:chicken", 30));
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
	public static final RegistryObject<Item> BLAZE_MEDAL = HELPER.createItem("blaze_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:blaze", 120));
	public static final RegistryObject<Item> ENDERMAN_MEDAL = HELPER.createItem("enderman_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:enderman", 120));
	public static final RegistryObject<Item> SKELETON_MEDAL = HELPER.createItem("skeleton_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:skeleton", 30));
	public static final RegistryObject<Item> ZOMBIE_MEDAL = HELPER.createItem("zombie_medal", () -> new MorphMedalItem(
			new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:zombie", 30));
	public static final RegistryObject<Item> GHAST_MEDAL = HELPER.createItem("ghast_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:ghast", 120));
	public static final RegistryObject<Item> SUNBIRD_MEDAL = HELPER.createItem("sunbird_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "alexsmobs:sunbird", 180));
	public static final RegistryObject<Item> HIPPO_MEDAL = HELPER.createItem("hippo_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "alexsmobs:hippo", 30));
	public static final RegistryObject<Item> VILLAGER_MEDAL = HELPER.createItem("villager_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:villager", 30));
	public static final RegistryObject<Item> WANDERING_TRADER_MEDAL = HELPER.createItem("wandering_trader_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:wandering_trader", 30));
	public static final RegistryObject<Item> STRIDER_MEDAL = HELPER.createItem("strider_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:strider", 60));
	public static final RegistryObject<Item> GAZELLE_MEDAL = HELPER.createItem("gazelle_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "alexsmobs:gazelle", 30));
	public static final RegistryObject<Item> DEER_MEDAL = HELPER.createItem("deer_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "environmental:deer", 30));
	public static final RegistryObject<Item> SEAL_MEDAL = HELPER.createItem("seal_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "alexsmobs:seal", 30));
	public static final RegistryObject<Item> WITHER_MEDAL = HELPER.createItem("wither_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:wither", 180));
	public static final RegistryObject<Item> SAND_BLOCK_MEDAL = HELPER.createItem("sand_block_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:falling_block", 30));
	public static final RegistryObject<Item> TROPICAL_FISH_MEDAL = HELPER.createItem("tropical_fish_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:tropical_fish", 60));
	public static final RegistryObject<Item> GUARDIAN_MEDAL = HELPER.createItem("guardian_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:guardian", 60));
	public static final RegistryObject<Item> ELDER_GUARDIAN_MEDAL = HELPER.createItem("elder_guardian_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:elder_guardian", 120));
	public static final RegistryObject<Item> SLIME_MEDAL = HELPER.createItem("slime_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:slime", 30));
	public static final RegistryObject<Item> MAGMA_CUBE_MEDAL = HELPER.createItem("magma_cube_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:magma_cube", 30));
	public static final RegistryObject<Item> FOX_MEDAL = HELPER.createItem("fox_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:fox", 30));
	public static final RegistryObject<Item> CAT_MEDAL = HELPER.createItem("cat_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:cat", 30));
	public static final RegistryObject<Item> WOLF_MEDAL = HELPER.createItem("wolf_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:wolf", 30));
	public static final RegistryObject<Item> PIGLIN_MEDAL = HELPER.createItem("piglin_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:piglin", 30));
	public static final RegistryObject<Item> STONELING_MEDAL = HELPER.createItem("stoneling_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "quark:stoneling", 30));
	public static final RegistryObject<Item> CREEPER_MEDAL = HELPER.createItem("creeper_medal", () ->
			new MorphMedalItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), "minecraft:creeper", 60));

	public static final RegistryObject<Item> BARNYARD_EMBLEM = HELPER.createItem("barnyard_emblem", () ->
			new BarnyardEmblemItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> FISHY_EMBLEM = HELPER.createItem("fishy_emblem", () ->
			new FishyEmblemItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> WALLFLOWER_EMBLEM = HELPER.createItem("wallflower_emblem", () ->
			new WallflowerEmblemItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> FREIGHT_EMBLEM = HELPER.createItem("freight_emblem", () ->
			new FreightEmblemItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> PROPRIETARY_EMBLEM = HELPER.createItem("proprietary_emblem", () ->
			new ProprietaryEmblemItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> KERATINOUS_EMBLEM = HELPER.createItem("keratinous_emblem", () ->
			new KeratinousEmblemItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> INFECTIOUS_EMBLEM = HELPER.createItem("infectious_emblem", () ->
			new InfectiousEmblemItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> HUNTED_EMBLEM = HELPER.createItem("hunted_emblem", () ->
			new HuntedEmblemItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static final RegistryObject<Item> SUNBIRD_FEATHER = HELPER.createItem("sunbird_feather", () ->
			new SunbirdFeatherItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> BANANA_PEEL = HELPER.createItem("banana_peel", () ->
			new BananaPeelItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

}