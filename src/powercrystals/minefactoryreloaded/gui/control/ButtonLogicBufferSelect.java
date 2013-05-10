package powercrystals.minefactoryreloaded.gui.control;

import powercrystals.core.gui.controls.ButtonOption;
import powercrystals.minefactoryreloaded.gui.client.GuiRedNetLogic;

public class ButtonLogicBufferSelect extends ButtonOption
{
	private LogicButtonType _buttonType;
	private GuiRedNetLogic _logicScreen;
	private int _pinIndex;
	private boolean _ignoreChanges;
	
	public ButtonLogicBufferSelect(GuiRedNetLogic containerScreen, int x, int y, int pinIndex, LogicButtonType buttonType)
	{
		super(containerScreen, x, y, 30, 14);
		_logicScreen = containerScreen;
		_buttonType = buttonType;
		_pinIndex = pinIndex;
		
		_ignoreChanges = true;
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
		_ignoreChanges = false;
		setVisible(false);
	}
	
	public int getBuffer()
	{
		return getSelectedIndex();
	}
	
	public void setBuffer(int buffer)
	{
		_ignoreChanges = true;
		setSelectedIndex(buffer);
		_ignoreChanges = false;
	}
	
	@Override
	public void onValueChanged(int value, String label)
	{
		if(_ignoreChanges)
		{
			return;
		}
		if(_buttonType == LogicButtonType.Input)
		{
			_logicScreen.setInputPinMapping(_pinIndex, value, 0);
		}
		else
		{
			_logicScreen.setOutputPinMapping(_pinIndex, value, 0);
		}
	}
	
	@Override
	public void drawForeground(int mouseX, int mouseY)
	{
		if(getValue() == null)
		{
			System.out.println("Buffer selection of " + getSelectedIndex() + " on " + _buttonType + " has null value!");
		}
		super.drawForeground(mouseX, mouseY);
	}
}
