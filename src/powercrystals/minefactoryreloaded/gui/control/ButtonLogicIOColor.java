package powercrystals.minefactoryreloaded.gui.control;

import powercrystals.core.gui.GuiColor;
import powercrystals.core.gui.GuiRender;
import powercrystals.core.gui.controls.Button;
import powercrystals.minefactoryreloaded.gui.client.GuiRedNetLogic;

public class ButtonLogicIOColor extends Button
{
	public enum ButtonType
	{
		Input,
		Output
	}
	
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
	
	private int _pinIndex;
	private ButtonType _buttonType;
	private GuiRedNetLogic _containerScreen;
	
	private int _pin;
	
	public ButtonLogicIOColor(GuiRedNetLogic containerScreen, int x, int y, int pinIndex, ButtonType buttonType)
	{
		super(containerScreen, x, y, 30, 10, "");
		_pinIndex = pinIndex;
		_buttonType = buttonType;
		_containerScreen = containerScreen;
	}
	
	public int getPin()
	{
		return _pin;
	}

	public void setPin(int pin)
	{
		_pin = pin;
	}
	
	@Override
	public void onClick()
	{
		_pin++;
		if(_pin > 15)
		{
			_pin = 0;
		}
		
		if(_buttonType == ButtonType.Input)
		{
			_containerScreen.setInputPinMapping(_pinIndex, 0, _pin);
		}
		else
		{
			_containerScreen.setOutputPinMapping(_pinIndex, 2, _pin);
		}
	}

	@Override
	public void drawForeground(int mouseX, int mouseY)
	{
		GuiRender.drawRect(x + 3, y + 3, x + width - 3, y + height - 3, _pinColors[_pin].getColor());
	}
}
