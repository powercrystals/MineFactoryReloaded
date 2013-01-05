package powercrystals.minefactoryreloaded.modhelpers;

import java.util.Random;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.FarmingRegistry;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.farmables.FertilizerStandard;
import powercrystals.minefactoryreloaded.farmables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.PlantableStandard;

@Mod(modid = "MFReloaded|CompatIC2", name = "MFR Compat: IC2", version = MineFactoryReloadedCore.version, dependencies = "after:MFReloaded;after:IC2")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class IC2
{
	@Init
	public static void load(FMLInitializationEvent e)
	{
		try
		{
			Class<?> ic2Items = Class.forName("ic2.core.Ic2Items");
			if(ic2Items != null)
			{
				ItemStack rubberSapling = (ItemStack)ic2Items.getField("rubberSapling").get(null);
				ItemStack rubberLeaves = (ItemStack)ic2Items.getField("rubberLeaves").get(null);
				ItemStack rubberWood = (ItemStack)ic2Items.getField("rubberWood").get(null);
				if(rubberSapling != null)
				{
					FarmingRegistry.registerPlantable(new PlantableStandard(rubberSapling.itemID, rubberSapling.itemID));
					FarmingRegistry.registerFertilizable(new IC2().new FertilizableIC2RubberTree(rubberSapling.itemID));
				}
				if(rubberLeaves != null)
				{
					FarmingRegistry.registerHarvestable(new HarvestableTreeLeaves(rubberLeaves.itemID));
				}
				if(rubberWood != null)
				{
					FarmingRegistry.registerHarvestable(new HarvestableStandard(rubberWood.itemID, HarvestType.Tree));
				}
				
				ItemStack fertilizer = (ItemStack)ic2Items.getField("fertilizer").get(null);
				if(fertilizer != null)
				{
					FarmingRegistry.registerFertilizer(new FertilizerStandard(fertilizer.itemID, fertilizer.getItemDamage()));
				}
			}
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
	
	public class FertilizableIC2RubberTree implements IFactoryFertilizable
	{
		private int _saplingId;
		
		public FertilizableIC2RubberTree(int blockId)
		{
			_saplingId = blockId;
		}
		
		@Override
		public int getFertilizableBlockId()
		{
			return _saplingId;
		}

		@Override
		public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType)
		{
			return fertilizerType == FertilizerType.GrowPlant;
		}

		@Override
		public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType)
		{
			try
			{
				((BlockSapling)Block.blocksList[_saplingId]).growTree(world, x, y, z, rand);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return world.getBlockId(x, y, z) != _saplingId;
		}
		
	}
}
