package powercrystals.minefactoryreloaded.modhelpers.vanilla;

import net.minecraft.block.Block;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.MobDrop;
import powercrystals.minefactoryreloaded.farmables.egghandlers.VanillaEggHandler;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableCocoa;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableCropPlant;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableGiantMushroom;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableGrass;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableNetherWart;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableRubberSapling;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableSapling;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableStemPlants;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizerStandard;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableSheep;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableSkeleton;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableStandard;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableZombiePigman;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableCocoa;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableCropPlant;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableMushroom;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableNetherWart;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableShrub;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStemPlant;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableVine;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableWood;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableCocoa;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableCropPlant;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableNetherWart;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableStandard;
import powercrystals.minefactoryreloaded.farmables.ranchables.RanchableCow;
import powercrystals.minefactoryreloaded.farmables.ranchables.RanchableMooshroom;
import powercrystals.minefactoryreloaded.farmables.ranchables.RanchableSheep;
import powercrystals.minefactoryreloaded.farmables.ranchables.RanchableSquid;
import powercrystals.minefactoryreloaded.farmables.safarinethandlers.EntityAgeableHandler;
import powercrystals.minefactoryreloaded.farmables.safarinethandlers.EntityLivingHandler;
import powercrystals.minefactoryreloaded.farmables.safarinethandlers.SheepHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "MFReloaded|CompatVanilla", name = "MFR Compat: Vanilla", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class Vanilla
{
	@Init
	public void load(FMLInitializationEvent event)
	{
		MFRRegistry.registerPlantable(new PlantableStandard(Block.sapling.blockID, Block.sapling.blockID));
		MFRRegistry.registerPlantable(new PlantableStandard(Item.pumpkinSeeds.itemID, Block.pumpkinStem.blockID));
		MFRRegistry.registerPlantable(new PlantableStandard(Item.melonSeeds.itemID, Block.melonStem.blockID));
		MFRRegistry.registerPlantable(new PlantableStandard(Block.mushroomBrown.blockID, Block.mushroomBrown.blockID));
		MFRRegistry.registerPlantable(new PlantableStandard(Block.mushroomRed.blockID, Block.mushroomRed.blockID));
		MFRRegistry.registerPlantable(new PlantableCropPlant(Item.seeds.itemID, Block.crops.blockID));
		MFRRegistry.registerPlantable(new PlantableCropPlant(Item.carrot.itemID, Block.carrot.blockID));
		MFRRegistry.registerPlantable(new PlantableCropPlant(Item.potato.itemID, Block.potato.blockID));
		MFRRegistry.registerPlantable(new PlantableNetherWart());
		MFRRegistry.registerPlantable(new PlantableCocoa());
		MFRRegistry.registerPlantable(new PlantableStandard(MineFactoryReloadedCore.rubberSaplingBlock.blockID, MineFactoryReloadedCore.rubberSaplingBlock.blockID));

		MFRRegistry.registerHarvestable(new HarvestableWood());
		MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(Block.leaves.blockID));
		MFRRegistry.registerHarvestable(new HarvestableStandard(Block.reed.blockID, HarvestType.LeaveBottom));
		MFRRegistry.registerHarvestable(new HarvestableStandard(Block.cactus.blockID, HarvestType.LeaveBottom));
		MFRRegistry.registerHarvestable(new HarvestableStandard(Block.plantRed.blockID, HarvestType.Normal));
		MFRRegistry.registerHarvestable(new HarvestableStandard(Block.plantYellow.blockID, HarvestType.Normal));
		MFRRegistry.registerHarvestable(new HarvestableShrub(Block.tallGrass.blockID));
		MFRRegistry.registerHarvestable(new HarvestableShrub(Block.deadBush.blockID));
		MFRRegistry.registerHarvestable(new HarvestableStandard(Block.mushroomCapBrown.blockID, HarvestType.Tree));
		MFRRegistry.registerHarvestable(new HarvestableStandard(Block.mushroomCapRed.blockID, HarvestType.Tree));
		MFRRegistry.registerHarvestable(new HarvestableMushroom(Block.mushroomBrown.blockID));
		MFRRegistry.registerHarvestable(new HarvestableMushroom(Block.mushroomRed.blockID));
		MFRRegistry.registerHarvestable(new HarvestableStemPlant(Block.pumpkin.blockID, HarvestType.Normal));
		MFRRegistry.registerHarvestable(new HarvestableStemPlant(Block.melon.blockID, HarvestType.Normal));
		MFRRegistry.registerHarvestable(new HarvestableCropPlant(Block.crops.blockID));
		MFRRegistry.registerHarvestable(new HarvestableCropPlant(Block.carrot.blockID));
		MFRRegistry.registerHarvestable(new HarvestableCropPlant(Block.potato.blockID));
		MFRRegistry.registerHarvestable(new HarvestableVine());
		MFRRegistry.registerHarvestable(new HarvestableNetherWart());
		MFRRegistry.registerHarvestable(new HarvestableCocoa());
		MFRRegistry.registerHarvestable(new HarvestableStandard(MineFactoryReloadedCore.rubberWoodBlock.blockID, HarvestType.Tree));
		MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(MineFactoryReloadedCore.rubberLeavesBlock.blockID));

		MFRRegistry.registerFertilizable(new FertilizableSapling(Block.sapling.blockID));
		MFRRegistry.registerFertilizable(new FertilizableCropPlant(Block.crops.blockID));
		MFRRegistry.registerFertilizable(new FertilizableCropPlant(Block.carrot.blockID));
		MFRRegistry.registerFertilizable(new FertilizableCropPlant(Block.potato.blockID));
		MFRRegistry.registerFertilizable(new FertilizableGiantMushroom(Block.mushroomBrown.blockID));
		MFRRegistry.registerFertilizable(new FertilizableGiantMushroom(Block.mushroomRed.blockID));
		MFRRegistry.registerFertilizable(new FertilizableStemPlants(Block.pumpkinStem.blockID));
		MFRRegistry.registerFertilizable(new FertilizableStemPlants(Block.melonStem.blockID));
		MFRRegistry.registerFertilizable(new FertilizableNetherWart());
		MFRRegistry.registerFertilizable(new FertilizableCocoa());
		MFRRegistry.registerFertilizable(new FertilizableGrass());
		MFRRegistry.registerFertilizable(new FertilizableRubberSapling());

		MFRRegistry.registerFertilizer(new FertilizerStandard(MineFactoryReloadedCore.fertilizerItem.itemID, 0));
		if(MineFactoryReloadedCore.enableBonemealFertilizing.getBoolean(false))
		{
			MFRRegistry.registerFertilizer(new FertilizerStandard(Item.dyePowder.itemID, 15));
		}
		else
		{
			MFRRegistry.registerFertilizer(new FertilizerStandard(Item.dyePowder.itemID, 15, FertilizerType.Grass));
		}

		MFRRegistry.registerRanchable(new RanchableCow());
		MFRRegistry.registerRanchable(new RanchableMooshroom());
		MFRRegistry.registerRanchable(new RanchableSheep());
		MFRRegistry.registerRanchable(new RanchableSquid());
		
		MFRRegistry.registerGrindable(new GrindableStandard(EntityChicken.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.feather)),
			new MobDrop(10, new ItemStack(Item.chickenRaw)),
			new MobDrop(10, new ItemStack(Item.egg))
		}));
		MFRRegistry.registerGrindable(new GrindableStandard(EntityCow.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.leather)),
			new MobDrop(10, new ItemStack(Item.beefRaw))
		}));
		MFRRegistry.registerGrindable(new GrindableStandard(EntityMooshroom.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.leather)),
			new MobDrop(10, new ItemStack(Item.beefRaw))
		}));
		MFRRegistry.registerGrindable(new GrindableStandard(EntityOcelot.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.fishRaw)),
			new MobDrop(10, new ItemStack(Item.silk))
		}));
		MFRRegistry.registerGrindable(new GrindableStandard(EntityPig.class, new ItemStack(Item.porkRaw)));
		MFRRegistry.registerGrindable(new GrindableSheep());
		MFRRegistry.registerGrindable(new GrindableStandard(EntitySquid.class, new ItemStack(Item.dyePowder)));

		MFRRegistry.registerGrindable(new GrindableStandard(EntityEnderman.class, new ItemStack(Item.enderPearl)));
		MFRRegistry.registerGrindable(new GrindableStandard(EntityWolf.class, new ItemStack(Item.bone)));
		MFRRegistry.registerGrindable(new GrindableZombiePigman());

		MFRRegistry.registerGrindable(new GrindableStandard(EntityBlaze.class, new ItemStack(Item.blazeRod)));
		MFRRegistry.registerGrindable(new GrindableStandard(EntityCaveSpider.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.silk)),
			new MobDrop(10, new ItemStack(Item.spiderEye))
		}));
		MFRRegistry.registerGrindable(new GrindableStandard(EntityCreeper.class, new ItemStack(Item.gunpowder)));
		MFRRegistry.registerGrindable(new GrindableStandard(EntityGhast.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.gunpowder)),
			new MobDrop(2, new ItemStack(Item.ghastTear))
		}));
		MFRRegistry.registerGrindable(new GrindableStandard(EntityMagmaCube.class, new ItemStack(Item.magmaCream)));
		MFRRegistry.registerGrindable(new GrindableSkeleton());
		MFRRegistry.registerGrindable(new GrindableStandard(EntitySlime.class, new ItemStack(Item.slimeBall)));
		MFRRegistry.registerGrindable(new GrindableStandard(EntitySpider.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.silk)),
			new MobDrop(10, new ItemStack(Item.spiderEye))
		}));
		MFRRegistry.registerGrindable(new GrindableStandard(EntityWitch.class, new MobDrop[]
		{ 
			new MobDrop(10, new ItemStack(Item.glassBottle, 2)),
			new MobDrop(10, new ItemStack(Item.lightStoneDust, 2)),
			new MobDrop(10, new ItemStack(Item.gunpowder, 2)),
			new MobDrop(10, new ItemStack(Item.redstone, 2)),
			new MobDrop(10, new ItemStack(Item.spiderEye, 2)),
			new MobDrop(10, new ItemStack(Item.stick, 2)),
			new MobDrop(10, new ItemStack(Item.sugar, 2))
		}));
		MFRRegistry.registerGrindable(new GrindableStandard(EntityZombie.class, new ItemStack(Item.rottenFlesh)));
		
		MFRRegistry.registerSludgeDrop(50, new ItemStack(Block.sand));
		MFRRegistry.registerSludgeDrop(40, new ItemStack(Block.dirt));
		MFRRegistry.registerSludgeDrop(30, new ItemStack(Item.clay, 4));
		MFRRegistry.registerSludgeDrop(3, new ItemStack(Block.mycelium));
		MFRRegistry.registerSludgeDrop(5, new ItemStack(Block.slowSand));
		
		MFRRegistry.registerBreederFood(EntityChicken.class, new ItemStack(Item.seeds));
		MFRRegistry.registerBreederFood(EntityWolf.class, new ItemStack(Item.porkCooked));
		MFRRegistry.registerBreederFood(EntityOcelot.class, new ItemStack(Item.fishRaw));
		MFRRegistry.registerBreederFood(EntityPig.class, new ItemStack(Item.carrot));
		
		MFRRegistry.registerSafariNetHandler(new EntityLivingHandler());
		MFRRegistry.registerSafariNetHandler(new EntityAgeableHandler());
		MFRRegistry.registerSafariNetHandler(new SheepHandler());
		
		MFRRegistry.registerMobEggHandler(new VanillaEggHandler());
		
		MFRRegistry.registerRubberTreeBiome("Swampland");
		MFRRegistry.registerRubberTreeBiome("Forest");
		MFRRegistry.registerRubberTreeBiome("Taiga");
		MFRRegistry.registerRubberTreeBiome("TaigaHills");
		MFRRegistry.registerRubberTreeBiome("Jungle");
		MFRRegistry.registerRubberTreeBiome("JungleHills");

		MFRRegistry.registerSafariNetBlacklist(EntityDragon.class);
		MFRRegistry.registerSafariNetBlacklist(EntityWither.class);
		
		MFRRegistry.registerRandomMobProvider(new VanillaMobProvider());
	}
}
