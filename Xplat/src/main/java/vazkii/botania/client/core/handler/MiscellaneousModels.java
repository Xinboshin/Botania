/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.client.core.handler;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.DyeColor;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.block.FloatingFlower;
import vazkii.botania.client.lib.ResourcesLib;
import vazkii.botania.client.model.ManaBlasterModel;
import vazkii.botania.client.model.TinyPotatoModel;
import vazkii.botania.client.render.block_entity.CorporeaCrystalCubeBlockEntityRenderer;
import vazkii.botania.client.render.block_entity.ManaPumpBlockEntityRenderer;
import vazkii.botania.common.item.equipment.bauble.FlugelTiaraItem;
import vazkii.botania.common.item.relic.KeyOfTheKingsLawItem;
import vazkii.botania.common.lib.LibMisc;
import vazkii.botania.xplat.ClientXplatAbstractions;

import java.util.*;
import java.util.function.Consumer;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class MiscellaneousModels {
	public static final MiscellaneousModels INSTANCE = new MiscellaneousModels();

	public boolean registeredModels = false;

	public final BakedModel[] tiaraWingIcons = new BakedModel[FlugelTiaraItem.WING_TYPES];
	public final BakedModel[] thirdEyeLayers = new BakedModel[3];

	public BakedModel goldfishModel,
			phiFlowerModel,
			nerfBatModel,
			bloodPendantChain,
			bloodPendantGem,
			snowflakePendantGem,
			itemFinderGem,
			pyroclastGem,
			crimsonGem,
			cirrusGem,
			nimbusGem,
			terrasteelHelmWillModel,
			elvenSpreaderCore,
			gaiaSpreaderCore,
			manaSpreaderCore,
			redstoneSpreaderCore,
			manaSpreaderScaffolding,
			elvenSpreaderScaffolding,
			gaiaSpreaderScaffolding;

	public final HashMap<DyeColor, BakedModel> spreaderPaddings = new HashMap<>();

	public final BakedModel[] kingKeyWeaponModels = new BakedModel[KeyOfTheKingsLawItem.WEAPON_TYPES];

	public void onModelRegister(ResourceManager rm, Consumer<ResourceLocation> consumer) {
		consumer.accept(prefix("icon/goldfish"));
		consumer.accept(prefix("icon/phiflower"));
		consumer.accept(prefix("icon/nerfbat"));
		consumer.accept(prefix("icon/blood_pendant_chain"));
		consumer.accept(prefix("icon/blood_pendant_gem"));
		for (int i = 0; i < KeyOfTheKingsLawItem.WEAPON_TYPES; i++) {
			consumer.accept(prefix("icon/gate_weapon_" + i));
		}
		consumer.accept(prefix("icon/will_flame"));
		for (int i = 0; i < thirdEyeLayers.length; i++) {
			consumer.accept(prefix("icon/third_eye_" + i));
		}
		consumer.accept(prefix("icon/lava_pendant_gem"));
		consumer.accept(prefix("icon/super_lava_pendant_gem"));
		consumer.accept(prefix("icon/itemfinder_gem"));
		consumer.accept(prefix("icon/cloud_pendant_gem"));
		consumer.accept(prefix("icon/super_cloud_pendant_gem"));
		consumer.accept(prefix("icon/ice_pendant_gem"));
		for (int i = 0; i < tiaraWingIcons.length; i++) {
			consumer.accept(prefix("icon/tiara_wing_" + (i + 1)));
		}

		consumer.accept(new ModelResourceLocation(prefix("mana_gun_clip"), "inventory"));
		consumer.accept(new ModelResourceLocation(prefix("desu_gun"), "inventory"));
		consumer.accept(new ModelResourceLocation(prefix("desu_gun_clip"), "inventory"));
		consumer.accept(prefix("block/corporea_crystal_cube_glass"));
		consumer.accept(prefix("block/pump_head"));
		consumer.accept(prefix("block/elven_spreader_core"));
		consumer.accept(prefix("block/gaia_spreader_core"));
		consumer.accept(prefix("block/mana_spreader_core"));
		consumer.accept(prefix("block/redstone_spreader_core"));
		consumer.accept(prefix("block/mana_spreader_scaffolding"));
		consumer.accept(prefix("block/elven_spreader_scaffolding"));
		consumer.accept(prefix("block/gaia_spreader_scaffolding"));
		for (DyeColor color : DyeColor.values()) {
			consumer.accept(prefix("block/" + color.toString() + "_spreader_padding"));
		}

		registerIslands();
		registerTaters(rm, consumer);

		if (!registeredModels) {
			registeredModels = true;
		}
	}

	private static void registerIslands() {
		BotaniaAPIClient.instance().registerIslandTypeModel(FloatingFlower.IslandType.GRASS, prefix("block/islands/island_grass"));
		BotaniaAPIClient.instance().registerIslandTypeModel(FloatingFlower.IslandType.PODZOL, prefix("block/islands/island_podzol"));
		BotaniaAPIClient.instance().registerIslandTypeModel(FloatingFlower.IslandType.MYCEL, prefix("block/islands/island_mycel"));
		BotaniaAPIClient.instance().registerIslandTypeModel(FloatingFlower.IslandType.SNOW, prefix("block/islands/island_snow"));
		BotaniaAPIClient.instance().registerIslandTypeModel(FloatingFlower.IslandType.DRY, prefix("block/islands/island_dry"));
		BotaniaAPIClient.instance().registerIslandTypeModel(FloatingFlower.IslandType.GOLDEN, prefix("block/islands/island_golden"));
		BotaniaAPIClient.instance().registerIslandTypeModel(FloatingFlower.IslandType.VIVID, prefix("block/islands/island_vivid"));
		BotaniaAPIClient.instance().registerIslandTypeModel(FloatingFlower.IslandType.SCORCHED, prefix("block/islands/island_scorched"));
		BotaniaAPIClient.instance().registerIslandTypeModel(FloatingFlower.IslandType.INFUSED, prefix("block/islands/island_infused"));
		BotaniaAPIClient.instance().registerIslandTypeModel(FloatingFlower.IslandType.MUTATED, prefix("block/islands/island_mutated"));
	}

	private static void registerTaters(ResourceManager rm, Consumer<ResourceLocation> consumer) {
		for (ResourceLocation model : rm.listResources(ResourcesLib.PREFIX_MODELS + ResourcesLib.PREFIX_TINY_POTATO, s -> s.getPath().endsWith(ResourcesLib.ENDING_JSON)).keySet()) {
			if (LibMisc.MOD_ID.equals(model.getNamespace())) {
				String path = model.getPath();
				path = path.substring(ResourcesLib.PREFIX_MODELS.length(), path.length() - ResourcesLib.ENDING_JSON.length());
				consumer.accept(new ResourceLocation(LibMisc.MOD_ID, path));
			}
		}
	}

	public void onModelBake(ModelBakery loader, Map<ResourceLocation, BakedModel> map) {
		if (!registeredModels) {
			BotaniaAPI.LOGGER.error("Additional models failed to register! Aborting baking models to avoid early crashing.");
			return;
		}
		// Platforms
		ModelResourceLocation abstruseName = new ModelResourceLocation(prefix("abstruse_platform"), "");
		BakedModel abstruse = map.get(abstruseName);
		ModelResourceLocation spectralName = new ModelResourceLocation(prefix("spectral_platform"), "");
		BakedModel spectral = map.get(spectralName);
		ModelResourceLocation infrangibleName = new ModelResourceLocation(prefix("infrangible_platform"), "");
		BakedModel infrangible = map.get(infrangibleName);

		map.put(abstruseName, ClientXplatAbstractions.INSTANCE.wrapPlatformModel(abstruse));
		map.put(spectralName, ClientXplatAbstractions.INSTANCE.wrapPlatformModel(spectral));
		map.put(infrangibleName, ClientXplatAbstractions.INSTANCE.wrapPlatformModel(infrangible));

		// Mana Blaster
		ModelResourceLocation key = new ModelResourceLocation(prefix("mana_gun"), "inventory");
		BakedModel originalModel = map.get(key);
		ModelResourceLocation clipKey = new ModelResourceLocation(prefix("mana_gun_clip"), "inventory");
		BakedModel originalModelClip = map.get(clipKey);
		map.put(key, new ManaBlasterModel(loader, originalModel, originalModelClip));

		// Tiny Potato
		ModelResourceLocation tinyPotato = new ModelResourceLocation(prefix("tiny_potato"), "inventory");
		BakedModel originalPotato = map.get(tinyPotato);
		map.put(tinyPotato, new TinyPotatoModel(originalPotato));

		CorporeaCrystalCubeBlockEntityRenderer.cubeModel = map.get(prefix("block/corporea_crystal_cube_glass"));
		ManaPumpBlockEntityRenderer.headModel = map.get(prefix("block/pump_head"));

		// Spreader cores, paddings and scaffoldings
		elvenSpreaderCore = map.get(prefix("block/elven_spreader_core"));
		gaiaSpreaderCore = map.get(prefix("block/gaia_spreader_core"));
		manaSpreaderCore = map.get(prefix("block/mana_spreader_core"));
		redstoneSpreaderCore = map.get(prefix("block/redstone_spreader_core"));
		manaSpreaderScaffolding = map.get(prefix("block/mana_spreader_scaffolding"));
		elvenSpreaderScaffolding = map.get(prefix("block/elven_spreader_scaffolding"));
		gaiaSpreaderScaffolding = map.get(prefix("block/gaia_spreader_scaffolding"));
		for (DyeColor color : DyeColor.values()) {
			spreaderPaddings.put(color, map.get(prefix("block/" + color.getName() + "_spreader_padding")));
		}

		// Icons
		goldfishModel = map.get(prefix("icon/goldfish"));
		phiFlowerModel = map.get(prefix("icon/phiflower"));
		nerfBatModel = map.get(prefix("icon/nerfbat"));
		bloodPendantChain = map.get(prefix("icon/blood_pendant_chain"));
		bloodPendantGem = map.get(prefix("icon/blood_pendant_gem"));
		for (int i = 0; i < KeyOfTheKingsLawItem.WEAPON_TYPES; i++) {
			kingKeyWeaponModels[i] = map.get(prefix("icon/gate_weapon_" + i));
		}
		terrasteelHelmWillModel = map.get(prefix("icon/will_flame"));
		for (int i = 0; i < thirdEyeLayers.length; i++) {
			thirdEyeLayers[i] = map.get(prefix("icon/third_eye_" + i));
		}
		pyroclastGem = map.get(prefix("icon/lava_pendant_gem"));
		crimsonGem = map.get(prefix("icon/super_lava_pendant_gem"));
		itemFinderGem = map.get(prefix("icon/itemfinder_gem"));

		cirrusGem = map.get(prefix("icon/cloud_pendant_gem"));
		nimbusGem = map.get(prefix("icon/super_cloud_pendant_gem"));
		snowflakePendantGem = map.get(prefix("icon/ice_pendant_gem"));
		for (int i = 0; i < tiaraWingIcons.length; i++) {
			tiaraWingIcons[i] = map.get(prefix("icon/tiara_wing_" + (i + 1)));
		}
	}

	private MiscellaneousModels() {}
}
