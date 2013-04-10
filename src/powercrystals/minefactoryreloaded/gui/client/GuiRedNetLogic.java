package powercrystals.minefactoryreloaded.gui.client;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;
import powercrystals.core.gui.GuiScreenBase;
import powercrystals.core.gui.controls.Button;
import powercrystals.core.gui.controls.IListBoxElement;
import powercrystals.core.gui.controls.ListBox;
import powercrystals.core.net.PacketWrapper;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;
import powercrystals.minefactoryreloaded.gui.control.ButtonLogicIOColor;
import powercrystals.minefactoryreloaded.gui.control.ButtonLogicIOColor.ButtonType;
import powercrystals.minefactoryreloaded.gui.control.ListBoxElementCircuit;
import powercrystals.minefactoryreloaded.net.Packets;
import powercrystals.minefactoryreloaded.tile.TileEntityRedNetLogic;

public class GuiRedNetLogic extends GuiScreenBase
{
	private class CircuitComparator implements Comparator<IRedNetLogicCircuit>
	{
		@Override
		public int compare(IRedNetLogicCircuit arg0, IRedNetLogicCircuit arg1)
		{
			return StatCollector.translateToLocal(arg0.getUnlocalizedName()).compareTo(StatCollector.translateToLocal(arg1.getUnlocalizedName()));
		}
	}
	
	private TileEntityRedNetLogic _logic;
	
	private int _selectedCircuit;
	
	private ListBox _circuitList;
	
	private ButtonLogicIOColor[] _inputIOButtons = new ButtonLogicIOColor[16];
	private ButtonLogicIOColor[] _outputIOButtons = new ButtonLogicIOColor[16];
	
	private Button _nextCircuit;
	private Button _prevCircuit;
	
	public GuiRedNetLogic(Container container, TileEntityRedNetLogic logic)
	{	
		super(container, MineFactoryReloadedCore.guiFolder + "rednetlogic.png");
		xSize = 256;
		ySize = 256;
		
		_logic = logic;
		
		_circuitList = new ListBox(this, 56, 16, 120, 200)
		{
			@Override
			protected void onSelectionChanged(int newIndex, IListBoxElement newElement)
			{
			}
			
			@Override
			protected void onElementClicked(IListBoxElement element)
			{
				PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.LogicSetCircuit, new Object[]
						{ _logic.xCoord, _logic.yCoord, _logic.zCoord, _selectedCircuit, element.getValue().getClass().getName() }));
			}
		};
		
		List<IRedNetLogicCircuit> circuits = new LinkedList<IRedNetLogicCircuit>(MFRRegistry.getRedNetLogicCircuits());
		Collections.sort(circuits, new CircuitComparator());
		
		for(IRedNetLogicCircuit circuit : circuits)
		{
			_circuitList.add(new ListBoxElementCircuit(circuit));
		}
		
		addControl(_circuitList);
		
		_prevCircuit = new Button(this, 220, 16, 30, 30, "Prev")
		{
			
			@Override
			public void onClick()
			{
				_selectedCircuit--;
				if(_selectedCircuit < 0)
				{
					_selectedCircuit = 3;
				}
				requestCircuit();
			}
		};

		_nextCircuit = new Button(this, 220, 76, 30, 30, "Next")
		{
			
			@Override
			public void onClick()
			{
				_selectedCircuit++;
				if(_selectedCircuit > 3)
				{
					_selectedCircuit = 0;
				}
				requestCircuit();
			}
		};
		
		addControl(_prevCircuit);
		addControl(_nextCircuit);
		
		for(int i = 0; i < _inputIOButtons.length; i++)
		{
			_inputIOButtons[i] = new ButtonLogicIOColor(this, 22, 16 + i * 12, i, ButtonType.Input);
			_outputIOButtons[i] = new ButtonLogicIOColor(this, 190, 16 + i * 12, i, ButtonType.Output);
			
			_inputIOButtons[i].setPin(i);
			
			addControl(_inputIOButtons[i]);
			addControl(_outputIOButtons[i]);
		}
		requestCircuit();
	}
	
	private void requestCircuit()
	{
		PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.LogicRequestCircuitDefinition, new Object[]
				{ _logic.xCoord, _logic.yCoord, _logic.zCoord, _selectedCircuit }));
	}
	
	@Override
	public void updateScreen()
	{
		if(((IRedNetLogicCircuit)_circuitList.getSelectedElement().getValue()).getClass() != _logic.getCircuit(_selectedCircuit).getClass())
		{
			for(int i = 0; i < _circuitList.getElementCount(); i++)
			{
				if(((IRedNetLogicCircuit)_circuitList.getElement(i).getValue()).getClass() == _logic.getCircuit(_selectedCircuit).getClass())
				{
					_circuitList.setSelectedIndex(i);
					break;
				}
			}
		}
		
		for(int i = 0; i < _inputIOButtons.length; i++)
		{
			if(i < _logic.getCircuit(_selectedCircuit).getInputCount())
			{
				_inputIOButtons[i].setVisible(true);
				_inputIOButtons[i].setPin(_logic.getInputPinMapping(_selectedCircuit, i).pin);
			}
			else
			{
				_inputIOButtons[i].setVisible(false);
			}
		}
		
		for(int i = 0; i < _outputIOButtons.length; i++)
		{
			if(i < _logic.getCircuit(_selectedCircuit).getOutputCount())
			{
				_outputIOButtons[i].setVisible(true);
				_outputIOButtons[i].setPin(_logic.getOutputPinMapping(_selectedCircuit, i).pin);
			}
			else
			{
				_outputIOButtons[i].setVisible(false);
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		fontRenderer.drawString("Programmable RedNet Controller", 8, 6, 4210752);
		fontRenderer.drawString((_selectedCircuit + 1) + " of 4", 220, 60, 4210752);
		
		for(int i = 0; i < _inputIOButtons.length; i++)
		{
			if(i < _logic.getCircuit(_selectedCircuit).getInputCount())
			{
				fontRenderer.drawString(_logic.getCircuit(_selectedCircuit).getInputPinLabel(i), 4, 18 + i * 12, 4210752);
			}
		}
		
		for(int i = 0; i < _outputIOButtons.length; i++)
		{
			if(i < _logic.getCircuit(_selectedCircuit).getOutputCount())
			{
				fontRenderer.drawString(_logic.getCircuit(_selectedCircuit).getOutputPinLabel(i), 178, 18 + i * 12, 4210752);
			}
		}
	}
	
	public void setInputPinMapping(int index, int buffer, int pin)
	{
		PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.LogicSetPin, new Object[]
				{ _logic.xCoord, _logic.yCoord, _logic.zCoord, 0, _selectedCircuit, index, buffer, pin }));
	}
	
	public void setOutputPinMapping(int index, int buffer, int pin)
	{
		PacketDispatcher.sendPacketToServer(PacketWrapper.createPacket(MineFactoryReloadedCore.modNetworkChannel, Packets.LogicSetPin, new Object[]
				{ _logic.xCoord, _logic.yCoord, _logic.zCoord, 1, _selectedCircuit, index, buffer, pin }));
	}
}
