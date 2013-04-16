package powercrystals.minefactoryreloaded.block;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.rednet.IConnectableRedNet;
import powercrystals.minefactoryreloaded.api.rednet.RedNetConnectionType;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import powercrystals.minefactoryreloaded.tile.TileEntityRedNetLogic;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockRedNetLogic extends BlockContainer implements IConnectableRedNet
{
	public BlockRedNetLogic(int id)
	{
		super(id, Material.clay);
		setUnlocalizedName("mfr.rednet.logic");
		setHardness(0.8F);
		setCreativeTab(MFRCreativeTab.tab);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityRedNetLogic();
	}

	@Override
	public RedNetConnectionType getConnectionType(World world, int x, int y, int z, ForgeDirection side)
	{
		return RedNetConnectionType.CableAll;
	}

	@Override
	public int getOutputValue(World world, int x, int y, int z, ForgeDirection side, int subnet)
	{
		TileEntityRedNetLogic logic = (TileEntityRedNetLogic)world.getBlockTileEntity(x, y, z);
		if(logic != null)
		{
			return logic.getOutputValue(side, subnet);
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
			return logic.getOutputValues(side);
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
			logic.onInputsChanged(side, inputValues);
		}
	}

	@Override
	public void onInputChanged(World world, int x, int y, int z, ForgeDirection side, int inputValue)
	{
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
	{
		player.openGui(MineFactoryReloadedCore.instance(), 0, world, x, y, z);
		return true;
	}
	
	@Override
	public int getRenderType()
	{
		return MineFactoryReloadedCore.renderIdRedNetLogic;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z)
	{
		return false;
	}
	
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		return true;
	}
}
