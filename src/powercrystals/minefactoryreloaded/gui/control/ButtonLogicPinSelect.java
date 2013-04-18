package powercrystals.minefactoryreloaded.gui.control;

import powercrystals.core.gui.GuiColor;
import powercrystals.core.gui.GuiRender;
import powercrystals.core.gui.controls.Button;
import powercrystals.minefactoryreloaded.gui.client.GuiRedNetLogic;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class ButtonLogicPinSelect extends Button
{	
	private static GuiColor[] _pinColors = new GuiColor[]
			{
				new GuiColor(223, 223, 223), // white
				new GuiColor(219, 125,  63), // orange
				new GuiColor(180,  81, 188), // magenta
				new GuiColor(107, 138, 207), // light blue
				new GuiColor(177, 166,  39), // yellow
				new GuiColor( 66, 174,  57), // lime
				new GuiColor(208, 132, 153), // pink
				new GuiColor( 65,  65,  65), // dark gray
				new GuiColor(155, 155, 155), // light gray
				new GuiColor( 47, 111, 137), // cyan
				new GuiColor(127,  62, 182), // purple
				new GuiColor( 46,  57, 141), // blue
				new GuiColor( 79,  50,  31), // brown
				new GuiColor( 53,  71,  28), // green
				new GuiColor(151,  52,  49), // red
				new GuiColor( 22,  22,  26), // black
			};
	
	private static String[] _pinColorNames = new String[]
			{
				"WT",
				"ORNG",
				"MGTA",
				"L_BL",
				"YLLW",
				"LIME",
				"PINK",
				"D_GR",
				"L_GR",
				"CYAN",
				"PURP",
				"BLUE",
				"BRWN",
				"GRN",
				"RED",
				"BLK",
			};
	
	private int _pinIndex;
	private LogicButtonType _buttonType;
	private GuiRedNetLogic _containerScreen;
	
	private int _pin;
	private int _buffer;
	
	public ButtonLogicPinSelect(GuiRedNetLogic containerScreen, int x, int y, int pinIndex, LogicButtonType buttonType)
	{
		super(containerScreen, x, y, 30, 14, "");
		_pinIndex = pinIndex;
		_buttonType = buttonType;
		_containerScreen = containerScreen;
	}
	
	public int getBuffer()
	{
		return _buffer;
	}
	
	public void setBuffer(int buffer)
	{
		_buffer = buffer;
	}
	
	public int getPin()
	{
		return _pin;
	}

	public void setPin(int pin)
	{
		_pin = pin;
		setText(((Integer)_pin).toString());
	}
	
	@Override
	public void onClick()
	{
		_pin++;
		if((_buffer == 14 && _pin > 0) || (_buffer == 13 && _pin >= _containerScreen.getVariableCount()) || (_buffer < 13 && _pin > 15))
		{
			_pin = 0;
		}
		setText(((Integer)_pin).toString());
		
		if(_buttonType == LogicButtonType.Input)
		{
			_containerScreen.setInputPinMapping(_pinIndex, _buffer, _pin);
		}
		else
		{
			_containerScreen.setOutputPinMapping(_pinIndex, _buffer, _pin);
		}
	}

	@Override
	public void drawForeground(int mouseX, int mouseY)
	{
		if(_buffer < 12)
		{
			if(!MineFactoryReloadedCore.colorblindMode.getBoolean(false))
			{
				GuiRender.drawRect(x + 3, y + 3, x + width - 3, y + height - 3, _pinColors[_pin].getColor());
			}
			else
			{

				GuiRender.drawCenteredString(containerScreen.fontRenderer, _pinColorNames[_pin], x + width / 2, y + height / 2 - 4, getTextColor(mouseX, mouseY));
			}
		}
		else if(_buffer < 14)
		{
			super.drawForeground(mouseX, mouseY);
		}
	}
}
