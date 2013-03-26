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
import powercrystals.minefactoryreloaded.api.FarmingRegistry;
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
		FarmingRegistry.registerPlantable(new PlantableStandard(Block.sapling.blockID, Block.sapling.blockID));
		FarmingRegistry.registerPlantable(new PlantableStandard(Item.pumpkinSeeds.itemID, Block.pumpkinStem.blockID));
		FarmingRegistry.registerPlantable(new PlantableStandard(Item.melonSeeds.itemID, Block.melonStem.blockID));
		FarmingRegistry.registerPlantable(new PlantableStandard(Block.mushroomBrown.blockID, Block.mushroomBrown.blockID));
		FarmingRegistry.registerPlantable(new PlantableStandard(Block.mushroomRed.blockID, Block.mushroomRed.blockID));
		FarmingRegistry.registerPlantable(new PlantableCropPlant(Item.seeds.itemID, Block.crops.blockID));
		FarmingRegistry.registerPlantable(new PlantableCropPlant(Item.carrot.itemID, Block.carrot.blockID));
		FarmingRegistry.registerPlantable(new PlantableCropPlant(Item.potato.itemID, Block.potato.blockID));
		FarmingRegistry.registerPlantable(new PlantableNetherWart());
		FarmingRegistry.registerPlantable(new PlantableCocoa());
		FarmingRegistry.registerPlantable(new PlantableStandard(MineFactoryReloadedCore.rubberSaplingBlock.blockID, MineFactoryReloadedCore.rubberSaplingBlock.blockID));

		FarmingRegistry.registerHarvestable(new HarvestableWood());
		FarmingRegistry.registerHarvestable(new HarvestableTreeLeaves(Block.leaves.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.reed.blockID, HarvestType.LeaveBottom));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.cactus.blockID, HarvestType.LeaveBottom));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.plantRed.blockID, HarvestType.Normal));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.plantYellow.blockID, HarvestType.Normal));
		FarmingRegistry.registerHarvestable(new HarvestableShrub(Block.tallGrass.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableShrub(Block.deadBush.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.mushroomCapBrown.blockID, HarvestType.Tree));
		FarmingRegistry.registerHarvestable(new HarvestableStandard(Block.mushroomCapRed.blockID, HarvestType.Tree));
		FarmingRegistry.registerHarvestable(new HarvestableMushroom(Block.mushroomBrown.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableMushroom(Block.mushroomRed.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableStemPlant(Block.pumpkin.blockID, HarvestType.Normal));
		FarmingRegistry.registerHarvestable(new HarvestableStemPlant(Block.melon.blockID, HarvestType.Normal));
		FarmingRegistry.registerHarvestable(new HarvestableCropPlant(Block.crops.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableCropPlant(Block.carrot.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableCropPlant(Block.potato.blockID));
		FarmingRegistry.registerHarvestable(new HarvestableVine());
		FarmingRegistry.registerHarvestable(new HarvestableNetherWart());
		FarmingRegistry.registerHarvestable(new HarvestableCocoa());
		FarmingRegistry.registerHarvestable(new HarvestableStandard(MineFactoryReloadedCore.rubberWoodBlock.blockID, HarvestType.Tree));
		FarmingRegistry.registerHarvestable(new HarvestableTreeLeaves(MineFactoryReloadedCore.rubberLeavesBlock.blockID));

		FarmingRegistry.registerFertilizable(new FertilizableSapling(Block.sapling.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableCropPlant(Block.crops.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableCropPlant(Block.carrot.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableCropPlant(Block.potato.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableGiantMushroom(Block.mushroomBrown.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableGiantMushroom(Block.mushroomRed.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableStemPlants(Block.pumpkinStem.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableStemPlants(Block.melonStem.blockID));
		FarmingRegistry.registerFertilizable(new FertilizableNetherWart());
		FarmingRegistry.registerFertilizable(new FertilizableCocoa());
		FarmingRegistry.registerFertilizable(new FertilizableGrass());
		FarmingRegistry.registerFertilizable(new FertilizableRubberSapling());

		FarmingRegistry.registerFertilizer(new FertilizerStandard(MineFactoryReloadedCore.fertilizerItem.itemID, 0));
		if(MineFactoryReloadedCore.enableBonemealFertilizing.getBoolean(false))
		{
			FarmingRegistry.registerFertilizer(new FertilizerStandard(Item.dyePowder.itemID, 15));
		}
		else
		{
			FarmingRegistry.registerFertilizer(new FertilizerStandard(Item.dyePowder.itemID, 15, FertilizerType.Grass));
		}

		FarmingRegistry.registerRanchable(new RanchableCow());
		FarmingRegistry.registerRanchable(new RanchableMooshroom());
		FarmingRegistry.registerRanchable(new RanchableSheep());
		FarmingRegistry.registerRanchable(new RanchableSquid());
		
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityChicken.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.feather)),
			new MobDrop(10, new ItemStack(Item.chickenRaw)),
			new MobDrop(10, new ItemStack(Item.egg))
		}));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityCow.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.leather)),
			new MobDrop(10, new ItemStack(Item.beefRaw))
		}));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityMooshroom.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.leather)),
			new MobDrop(10, new ItemStack(Item.beefRaw))
		}));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityOcelot.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.fishRaw)),
			new MobDrop(10, new ItemStack(Item.silk))
		}));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityPig.class, new ItemStack(Item.porkRaw)));
		FarmingRegistry.registerGrindable(new GrindableSheep());
		FarmingRegistry.registerGrindable(new GrindableStandard(EntitySquid.class, new ItemStack(Item.dyePowder)));

		FarmingRegistry.registerGrindable(new GrindableStandard(EntityEnderman.class, new ItemStack(Item.enderPearl)));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityWolf.class, new ItemStack(Item.bone)));
		FarmingRegistry.registerGrindable(new GrindableZombiePigman());

		FarmingRegistry.registerGrindable(new GrindableStandard(EntityBlaze.class, new ItemStack(Item.blazeRod)));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityCaveSpider.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.silk)),
			new MobDrop(10, new ItemStack(Item.spiderEye))
		}));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityCreeper.class, new ItemStack(Item.gunpowder)));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityGhast.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.gunpowder)),
			new MobDrop(2, new ItemStack(Item.ghastTear))
		}));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityMagmaCube.class, new ItemStack(Item.magmaCream)));
		FarmingRegistry.registerGrindable(new GrindableSkeleton());
		FarmingRegistry.registerGrindable(new GrindableStandard(EntitySlime.class, new ItemStack(Item.slimeBall)));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntitySpider.class, new MobDrop[]
		{
			new MobDrop(10, new ItemStack(Item.silk)),
			new MobDrop(10, new ItemStack(Item.spiderEye))
		}));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityWitch.class, new MobDrop[]
		{ 
			new MobDrop(10, new ItemStack(Item.glassBottle, 2)),
			new MobDrop(10, new ItemStack(Item.lightStoneDust, 2)),
			new MobDrop(10, new ItemStack(Item.gunpowder, 2)),
			new MobDrop(10, new ItemStack(Item.redstone, 2)),
			new MobDrop(10, new ItemStack(Item.spiderEye, 2)),
			new MobDrop(10, new ItemStack(Item.stick, 2)),
			new MobDrop(10, new ItemStack(Item.sugar, 2))
		}));
		FarmingRegistry.registerGrindable(new GrindableStandard(EntityZombie.class, new ItemStack(Item.rottenFlesh)));
		
		FarmingRegistry.registerSludgeDrop(25, new ItemStack(Block.sand));
		FarmingRegistry.registerSludgeDrop(20, new ItemStack(Block.dirt));
		FarmingRegistry.registerSludgeDrop(15, new ItemStack(Item.clay, 4));
		FarmingRegistry.registerSludgeDrop(1, new ItemStack(Block.slowSand));
		
		FarmingRegistry.registerBreederFood(EntityChicken.class, new ItemStack(Item.seeds));
		FarmingRegistry.registerBreederFood(EntityWolf.class, new ItemStack(Item.porkCooked));
		FarmingRegistry.registerBreederFood(EntityOcelot.class, new ItemStack(Item.fishRaw));
		FarmingRegistry.registerBreederFood(EntityPig.class, new ItemStack(Item.carrot));
		
		FarmingRegistry.registerSafariNetHandler(new EntityLivingHandler());
		FarmingRegistry.registerSafariNetHandler(new EntityAgeableHandler());
		FarmingRegistry.registerSafariNetHandler(new SheepHandler());
		
		FarmingRegistry.registerMobEggHandler(new VanillaEggHandler());
		
		MFRRegistry.registerRubberTreeBiome("Swampland");
		MFRRegistry.registerRubberTreeBiome("Forest");
		MFRRegistry.registerRubberTreeBiome("Taiga");
		MFRRegistry.registerRubberTreeBiome("TaigaHills");
		MFRRegistry.registerRubberTreeBiome("Jungle");
		MFRRegistry.registerRubberTreeBiome("JungleHills");

		MFRRegistry.registerSafariNetBlacklist(EntityDragon.class);
		MFRRegistry.registerSafariNetBlacklist(EntityWither.class);
	}
}
