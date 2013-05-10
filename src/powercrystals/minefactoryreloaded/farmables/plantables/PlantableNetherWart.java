package powercrystals.minefactoryreloaded.farmables.plantables;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherStalk;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;

public class PlantableNetherWart implements IFactoryPlantable
{
	@Override
	public int getSeedId()
	{
		return Item.netherStalkSeeds.itemID;
	}
	
	@Override
	public int getPlantedBlockId(World world, int x, int y, int z, ItemStack stack)
	{
		return Block.netherStalk.blockID;
	}
	
	@Override
	public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack)
	{
		return 0;
	}
	
	@Override
	public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
	{
		int groundId = world.getBlockId(x, y - 1, z);
		return (world.getBlockId(x, y - 1, z) == Block.slowSand.blockID ||
				(Block.blocksList[groundId] != null &&
				Block.blocksList[groundId].canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, ((BlockNetherStalk)Block.netherStalk))))
				&& world.isAirBlock(x, y, z);
	}
	
	@Override
	public void prePlant(World world, int x, int y, int z, ItemStack stack)
	{
	}
	
	@Override
	public void postPlant(World world, int x, int y, int z, ItemStack stack)
	{
	}
	
}
