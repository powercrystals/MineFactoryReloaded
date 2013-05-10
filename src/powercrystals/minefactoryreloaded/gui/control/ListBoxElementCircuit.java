package powercrystals.minefactoryreloaded.gui.control;

import net.minecraft.util.StatCollector;
import powercrystals.core.gui.controls.IListBoxElement;
import powercrystals.core.gui.controls.ListBox;
import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;

public class ListBoxElementCircuit implements IListBoxElement
{
	private IRedNetLogicCircuit _circuit;
	
	public ListBoxElementCircuit(IRedNetLogicCircuit circuit)
	{
		_circuit = circuit;
	}
	
	@Override
	public Object getValue()
	{
		return _circuit;
	}
	
	@Override
	public int getHeight()
	{
		return 10;
	}
	
	@Override
	public void draw(ListBox listBox, int x, int y, int backColor, int textColor)
	{
		String text = listBox.getContainerScreen().fontRenderer.trimStringToWidth(StatCollector.translateToLocal(_circuit.getUnlocalizedName()), listBox.getContentWidth());
		listBox.getContainerScreen().fontRenderer.drawStringWithShadow(text, x, y, textColor);
	}
}
