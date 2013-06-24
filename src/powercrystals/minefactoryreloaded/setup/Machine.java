package powercrystals.minefactoryreloaded.setup;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.block.BlockFactoryMachine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoAnvil;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoBrewer;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoDisenchanter;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoEnchanter;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoJukebox;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoSpawner;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityBioFuelGenerator;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityBioReactor;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityBlockBreaker;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityBlockSmasher;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityBreeder;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityChronotyper;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityCollector;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityComposter;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityDeepStorageUnit;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityEjector;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityEnchantmentRouter;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityFertilizer;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityFisher;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityFruitPicker;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityGrinder;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityHarvester;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityItemRouter;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLaserDrill;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLaserDrillPrecharger;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLavaFabricator;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLiquiCrafter;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityLiquidRouter;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityMeatPacker;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityOilFabricator;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityPlanter;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityRancher;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityRedNote;
import powercrystals.minefactoryreloaded.tile.machine.TileEntitySewer;
import powercrystals.minefactoryreloaded.tile.machine.TileEntitySlaughterhouse;
import powercrystals.minefactoryreloaded.tile.machine.TileEntitySludgeBoiler;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityUnifier;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityVet;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityWeather;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;

public class Machine
{
	public static Machine Planter = new Machine(0, 0, "Planter", TileEntityPlanter.class, "factoryPlanter", 16);
	public static Machine Fisher = new Machine(0, 1, "Fisher", TileEntityFisher.class, "factoryFisher", 2);
	public static Machine Harvester = new Machine(0, 2, "Harvester", TileEntityHarvester.class, "factoryHarvester", 24);
	public static Machine Rancher = new Machine(0, 3, "Rancher", TileEntityRancher.class, "factoryRancher", 32);
	public static Machine Fertilizer = new Machine(0, 4, "Fertilizer", TileEntityFertilizer.class, "factoryFertilizer", 96);
	public static Machine Vet = new Machine(0, 5, "Vet", TileEntityVet.class, "factoryVet", 32);
	public static Machine ItemCollector = new Machine(0, 6, "ItemCollector", TileEntityCollector.class, "factoryItemCollector", 0);
	public static Machine BlockBreaker = new Machine(0, 7, "BlockBreaker", TileEntityBlockBreaker.class, "factoryBlockBreaker", 96);
	public static Machine WeatherCollector = new Machine(0, 8, "WeatherCollector", TileEntityWeather.class, "factoryWeather", 4);
	public static Machine SludgeBoiler = new Machine(0, 9, "SludgeBoiler", TileEntitySludgeBoiler.class, "factorySludgeBoiler", 2);
	public static Machine Sewer = new Machine(0, 10, "Sewer", TileEntitySewer.class, "factorySewer", 0);
	public static Machine Composter = new Machine(0, 11, "Composter", TileEntityComposter.class, "factoryComposter", 2);
	public static Machine Breeder = new Machine(0, 12, "Breeder", TileEntityBreeder.class, "factoryBreeder", 64);
	public static Machine Grinder = new Machine(0, 13, "Grinder", TileEntityGrinder.class, "factoryGrinder", 320);
	public static Machine AutoEnchanter = new Machine(0, 14, "AutoEnchanter", TileEntityAutoEnchanter.class, "factoryEnchanter", 16);
	public static Machine Chronotyper = new Machine(0, 15, "Chronotyper", TileEntityChronotyper.class, "factoryChronotyper", 128);
	
	public static Machine Ejector = new Machine(1, 0, "Ejector", TileEntityEjector.class, "factoryEjector", 0);
	public static Machine ItemRouter = new Machine(1, 1, "ItemRouter", TileEntityItemRouter.class, "factoryItemRouter", 0);
	public static Machine LiquidRouter = new Machine(1, 2, "LiquidRouter", TileEntityLiquidRouter.class, "factoryLiquidRouter", 0);
	public static Machine DeepStorageUnit = new Machine(1, 3, "DeepStorageUnit", TileEntityDeepStorageUnit.class, "factoryDeepStorageUnit", 0);
	public static Machine LiquiCrafter = new Machine(1, 4, "LiquiCrafter", TileEntityLiquiCrafter.class, "factoryLiquiCrafter", 0);
	public static Machine LavaFabricator = new Machine(1, 5, "LavaFabricator", TileEntityLavaFabricator.class, "factoryLavaFabricator", 20);
	public static Machine OilFabricator = new Machine(1, 6, "OilFabricator", TileEntityOilFabricator.class, "factoryOilFabricator", 588);
	public static Machine AutoJukebox = new Machine(1, 7, "AutoJukebox", TileEntityAutoJukebox.class, "factoryAutoJukebox", 0);
	public static Machine Unifier = new Machine(1, 8, "Unifier", TileEntityUnifier.class, "factoryUnifier", 0);
	public static Machine AutoSpawner = new Machine(1, 9, "AutoSpawner", TileEntityAutoSpawner.class, "factoryAutoSpawner", 60);
	public static Machine BioReactor = new Machine(1, 10, "BioReactor", TileEntityBioReactor.class, "factoryBioReactor", 0);
	public static Machine BioFuelGenerator = new Machine(1, 11, "BioFuelGenerator", TileEntityBioFuelGenerator.class, "factoryBioFuelGenerator", 16);
	public static Machine AutoDisenchanter = new Machine(1, 12, "AutoDisenchanter", TileEntityAutoDisenchanter.class, "factoryDisenchanter", 32);
	public static Machine Slaughterhouse = new Machine(1, 13, "Slaughterhouse", TileEntitySlaughterhouse.class, "factorySlaughterhouse", 100);
	public static Machine MeatPacker = new Machine(1, 14, "MeatPacker", TileEntityMeatPacker.class, "factoryMeatPacker", 2);
	public static Machine EnchantmentRouter = new Machine(1, 15, "EnchantmentRouter", TileEntityEnchantmentRouter.class, "factoryEnchantmentRouter", 0);
	
	public static Machine LaserDrill = new Machine(2, 0, "LaserDrill", TileEntityLaserDrill.class, "factoryLaserDrill", 0);
	public static Machine LaserDrillPrecharger = new Machine(2, 1, "LaserDrillPrecharger", TileEntityLaserDrillPrecharger.class, "factoryLaserDrillPrecharger", 500);
	public static Machine AutoAnvil = new Machine(2, 2, "AutoAnvil", TileEntityAutoAnvil.class, "factoryAnvil", 16);
	public static Machine BlockSmasher = new Machine(2, 3, "BlockSmasher", TileEntityBlockSmasher.class, "factoryBlockSmasher", 1);
	public static Machine RedNote = new Machine(2, 4, "RedNote", TileEntityRedNote.class, "factoryRedNote", 0);
	public static Machine AutoBrewer = new Machine(2, 5, "AutoBrewer", TileEntityAutoBrewer.class, "factoryAutoBrewer", 4);
	public static Machine FruitPicker = new Machine(2, 6, "FruitPicker", TileEntityFruitPicker.class, "factoryFruitPicker", 32);
	
	private static List<Machine> _machines;
	private static Map<Integer, Machine> _machineMappingss;
	private static Map<Integer, Integer> _highestMetas;
	
	private int _blockIndex;
	private int _meta;
	private String _name;
	private String _internalName;
	private Class<? extends TileEntityFactory> _tileEntityClass;
	private String _tileEntityName;
	private Icon[] _iconsActive = new Icon[6];
	private Icon[] _iconsIdle = new Icon[6];
	private int _activationEnergyMJ;
	
	private Property _isRecipeEnabled;
	
	private Machine(int blockIndex, int meta, String name, Class<? extends TileEntityFactory> tileEntityClass, String tileEntityName, int activationEnergyMJ)
	{
		_blockIndex = blockIndex;
		_meta = meta;
		_name = name;
		_internalName = "tile.mfr.machine." + name.toLowerCase();
		_tileEntityClass = tileEntityClass;
		_tileEntityName = tileEntityName;
		_activationEnergyMJ = activationEnergyMJ;
		
		if(_machines == null) _machines = new LinkedList<Machine>();
		if(_machineMappingss == null) _machineMappingss = new HashMap<Integer, Machine>();
		if(_highestMetas == null) _highestMetas = new HashMap<Integer, Integer>();
		
		_machineMappingss.put(_meta | (_blockIndex << 4), this);
		_machines.add(this);
		
		if(_highestMetas.get(_blockIndex) == null || _highestMetas.get(_blockIndex) < _meta)
		{
			_highestMetas.put(_blockIndex, _meta);
		}
	}
	
	public static Machine getMachineFromIndex(int blockIndex, int meta)
	{
		return _machineMappingss.get(meta | (blockIndex << 4));
	}
	
	public static Machine getMachineFromId(int blockId, int meta)
	{
		return  _machineMappingss.get(meta | (((BlockFactoryMachine)Block.blocksList[blockId]).getBlockIndex() << 4));
	}
	
	public static int getHighestMetadata(int blockIndex)
	{
		return _highestMetas.get(blockIndex);
	}
	
	public static List<Machine> values()
	{
		return _machines;
	}
	
	public static void LoadTextures(int blockIndex, IconRegister ir)
	{
		for(Machine m : _machines)
		{
			if(m.getBlockIndex() == blockIndex)
			{
				m.loadIcons(ir);
			}
		}
	}
	
	
	public String getName()
	{
		return _name;
	}
	
	public String getInternalName()
	{
		return _internalName;
	}
	
	public int getBlockId()
	{
		return MineFactoryReloadedCore.machineBlocks.get(_blockIndex).blockID;
	}
	
	public int getMeta()
	{
		return _meta;
	}
	
	public int getBlockIndex()
	{
		return _blockIndex;
	}
	
	public boolean getIsRecipeEnabled()
	{
		return _isRecipeEnabled.getBoolean(true);
	}
	
	public TileEntityFactory getNewTileEntity()
	{
		try
		{
			TileEntityFactory tileEntity = _tileEntityClass.newInstance();
			return tileEntity;
		}
		catch(IllegalAccessException x)
		{
			FMLLog.severe("Unable to create instance of TileEntity from %s", _tileEntityClass.getName());
			return null;
		}
		catch(InstantiationException x)
		{
			FMLLog.severe("Unable to create instance of TileEntity from %s", _tileEntityClass.getName());
			return null;
		}
	}
	
	public int getActivationEnergyMJ()
	{
		return _activationEnergyMJ;
	}
	
	public void load(Configuration c)
	{
		_isRecipeEnabled = c.get("Machine", _name + ".Recipe.Enabled", true);
		if(_activationEnergyMJ > 0)
		{
			_activationEnergyMJ = c.get("Machine", _name + ".ActivationCostMJ", _activationEnergyMJ).getInt();
		}
		
		MinecraftForge.setBlockHarvestLevel(MineFactoryReloadedCore.machineBlocks.get(_blockIndex), _meta, "pickaxe", 0);
		GameRegistry.registerTileEntity(_tileEntityClass, _tileEntityName);
	}
	
	public void loadIcons(IconRegister ir)
	{
		_iconsActive[0] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getInternalName() + ".active.bottom");
		_iconsActive[1] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getInternalName() + ".active.top");
		_iconsActive[2] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getInternalName() + ".active.front");
		_iconsActive[3] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getInternalName() + ".active.back");
		_iconsActive[4] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getInternalName() + ".active.left");
		_iconsActive[5] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getInternalName() + ".active.right");
		_iconsIdle[0] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getInternalName() + ".idle.bottom");
		_iconsIdle[1] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getInternalName() + ".idle.top");
		_iconsIdle[2] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getInternalName() + ".idle.front");
		_iconsIdle[3] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getInternalName() + ".idle.back");
		_iconsIdle[4] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getInternalName() + ".idle.left");
		_iconsIdle[5] = ir.registerIcon("powercrystals/minefactoryreloaded/" + getInternalName() + ".idle.right");
	}
	
	public Icon getIcon(int side, boolean isActive)
	{
		if(isActive) return _iconsActive[side];
		return _iconsIdle[side];
	}
}
