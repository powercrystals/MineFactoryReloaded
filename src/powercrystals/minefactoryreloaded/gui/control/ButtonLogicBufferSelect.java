package powercrystals.minefactoryreloaded.gui.control;

import net.minecraft.client.gui.inventory.GuiContainer;
import powercrystals.core.gui.controls.ButtonOption;
import powercrystals.minefactoryreloaded.gui.client.GuiRedNetLogic;

public class ButtonLogicBufferSelect extends ButtonOption
{
	private LogicButtonType _buttonType;
	private GuiRedNetLogic _logicScreen;
	
	public ButtonLogicBufferSelect(GuiRedNetLogic containerScreen, int x, int y, int pinIndex, LogicButtonType buttonType)
	{
		super(containerScreen, x, y, 30, 14);
		_buttonType = buttonType;
		
		if(_buttonType == LogicButtonType.Input)
		{
			setValue(0, "I/O D");
			setValue(1, "I/O U");
			setValue(2, "I/O N");
			setValue(3, "I/O S");
			setValue(4, "I/O W");
			setValue(5, "I/O E");
			setValue(12, "CNST");
			setValue(13, "VARS");
			setSelectedIndex(0);
		}
		else
		{
			setValue(6, "I/O D");
			setValue(7, "I/O U");
			setValue(8, "I/O N");
			setValue(9, "I/O S");
			setValue(10, "I/O W");
			setValue(11, "I/O E");
			setValue(13, "VARS");
			setValue(14, "NULL");
			setSelectedIndex(6);
		}
		
	}

	@Override
	public void onValueChanged(int value, String label)
	{
		// TODO Auto-generated method stub
		
	}
}
