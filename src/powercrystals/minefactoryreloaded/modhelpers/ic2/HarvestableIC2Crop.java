package powercrystals.minefactoryreloaded.modhelpers.ic2;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableIC2Crop implements IFactoryHarvestable
{
	private int _blockId;
	private Method _getCropMethod;
	private Field _dirtyField;
	
	public HarvestableIC2Crop(int blockId) throws NoSuchMethodException, SecurityException, ClassNotFoundException, NoSuchFieldException
	{
		_blockId = blockId;
		_getCropMethod = Class.forName("ic2.core.block.TileEntityCrop").getMethod("crop");
		_dirtyField = Class.forName("ic2.core.block.TileEntityCrop").getField("dirty");
	}
	
	@Override
	public int getPlantId()
	{
		return _blockId;
	}
	
	@Override
	public HarvestType getHarvestType()
	{
		return HarvestType.Normal;
	}
	
	@Override
	public boolean breakBlock()
	{
		return false;
	}
	
	@Override
	public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te == null || !(te instanceof ICropTile))
		{
			return false;
		}
		ICropTile tec = (ICropTile)te;
		CropCard crop;
		try
		{
			crop = (CropCard)_getCropMethod.invoke(tec);
			if(tec.getID() < 0 || !crop.canBeHarvested(tec) || crop.canGrow(tec))
			{
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, int x, int y, int z)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		
		ICropTile tec = (ICropTile)world.getBlockTileEntity(x, y, z);
		CropCard crop;
		try
		{
			crop = (CropCard)_getCropMethod.invoke(tec);
			
			float chance = crop.dropGainChance();
			for (int i = 0; i < tec.getGain(); i++)
			{
				chance *= 1.03F;
			}
			
			chance -= rand.nextFloat();
			int numDrops = 0;
			while (chance > 0.0F)
			{
				numDrops++;
				chance -= rand.nextFloat();
			}
			ItemStack[] cropDrops = new ItemStack[numDrops];
			for (int i = 0; i < numDrops; i++)
			{
				cropDrops[i] = crop.getGain(tec);
				if((cropDrops[i] != null) && (rand.nextInt(100) <= tec.getGain()))
				{
					cropDrops[i].stackSize += 1;
				}
			}
			
			tec.setSize(crop.getSizeAfterHarvest(tec));
			_dirtyField.setBoolean(tec, true);
			for(ItemStack s : cropDrops)
			{
				drops.add(s);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return drops;
	}
	
	@Override
	public void preHarvest(World world, int x, int y, int z)
	{
	}
	
	@Override
	public void postHarvest(World world, int x, int y, int z)
	{
	}
}
