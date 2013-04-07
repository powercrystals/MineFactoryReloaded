package powercrystals.minefactoryreloaded.block;

import powercrystals.minefactoryreloaded.api.rednet.IConnectableRedNet;
import powercrystals.minefactoryreloaded.api.rednet.RedNetConnectionType;
import powercrystals.minefactoryreloaded.tile.TileEntityRedNetLogic;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockRedNetLogic extends BlockContainer implements IConnectableRedNet
{
	public BlockRedNetLogic(int id)
	{
		super(id, Material.clay);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityRedNetLogic();
	}

	@Override
	public RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side)
	{
		return RedNetConnectionType.PlateAll;
	}

	@Override
	public int getOutputValue(World world, int x, int y, int z, ForgeDirection side, int subnet)
	{
		TileEntityRedNetLogic logic = (TileEntityRedNetLogic)world.getBlockTileEntity(x, y, z);
		if(logic != null)
		{
			return logic.getOutputValue(subnet);
		}
		else
		{
			return 0;
		}
	}

	@Override
	public int[] getOutputValues(World world, int x, int y, int z, ForgeDirection side)
	{
		TileEntityRedNetLogic logic = (TileEntityRedNetLogic)world.getBlockTileEntity(x, y, z);
		if(logic != null)
		{
			return logic.getOutputValues();
		}
		else
		{
			return new int[16];
		}
	}

	@Override
	public void onInputsChanged(World world, int x, int y, int z, ForgeDirection side, int[] inputValues)
	{
		TileEntityRedNetLogic logic = (TileEntityRedNetLogic)world.getBlockTileEntity(x, y, z);
		if(logic != null)
		{
			logic.onInputsChanged(inputValues);
		}
	}

	@Override
	public void onInputChanged(World world, int x, int y, int z, ForgeDirection side, int inputValue)
	{
	}
}
