package powercrystals.minefactoryreloaded.modhelpers.nei;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import codechicken.nei.MultiItemRange;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIMFRConfig implements IConfigureNEI
{

	@Override
	public void loadConfig()
	{
		try
		{
			addSubSet();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void addSubSet()
	{
		MultiItemRange subTypes = new MultiItemRange();
		subTypes.add(MineFactoryReloadedCore.machineBlock0.blockID, 0, 15);
		subTypes.add(MineFactoryReloadedCore.machineBlock1.blockID, 0, 10);
		subTypes.add(MineFactoryReloadedCore.ceramicDyeItem.itemID, 0, 15);
		subTypes.add(MineFactoryReloadedCore.factoryGlassBlock.blockID, 0, 15);
		subTypes.add(MineFactoryReloadedCore.factoryGlassPaneBlock.blockID, 0, 15);

		subTypes.add(MineFactoryReloadedCore.conveyorBlock.blockID, 0, 16);

		
		subTypes.add(MineFactoryReloadedCore.rubberWoodBlock.blockID, 0, 0);
		subTypes.add(MineFactoryReloadedCore.rubberLeavesBlock.blockID, 0, 0);
		subTypes.add(MineFactoryReloadedCore.rubberSaplingBlock.blockID, 0, 0);
		subTypes.add(MineFactoryReloadedCore.railDropoffCargoBlock.blockID, 0, 0);
		subTypes.add(MineFactoryReloadedCore.railPickupCargoBlock.blockID, 0, 0);
		subTypes.add(MineFactoryReloadedCore.railDropoffPassengerBlock.blockID, 0, 0);
		subTypes.add(MineFactoryReloadedCore.railPickupPassengerBlock.blockID, 0, 0);
		
		subTypes.add(MineFactoryReloadedCore.factoryRoadBlock.blockID, 0, 0);
		subTypes.add(MineFactoryReloadedCore.factoryRoadBlock.blockID, 1, 1);
		subTypes.add(MineFactoryReloadedCore.factoryRoadBlock.blockID, 4, 4);
		
		subTypes.add(MineFactoryReloadedCore.factoryHammerItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.milkItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.sludgeItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.sewageItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.mobEssenceItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.fertilizerItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.plasticSheetItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.rawPlasticItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.rubberBarItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.sewageBucketItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.sludgeBucketItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.mobEssenceBucketItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.syringeEmptyItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.syringeHealthItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.syringeGrowthItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.rawRubberItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.machineBaseItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.safariNetItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.blankRecordItem, 0, 0);
		subTypes.add(MineFactoryReloadedCore.syringeZombieItem, 0, 0);
		
		API.addSetRange("MineFactory", subTypes);
	}

	@Override
	public String getName()
	{
		return "Minefactory Reloaded";
	}

	@Override
	public String getVersion()
	{
		return "2.0.0";
	}

}