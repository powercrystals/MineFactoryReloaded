package powercrystals.minefactoryreloaded.block;

import java.util.List;

import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class ItemBlockRedNetLogic extends ItemBlock
{
	public static ItemStack manual;
	
	static
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("author", "powercrystals");
		nbt.setString("title", "PRC Owner's Manual");
		
		NBTTagList pages = new NBTTagList();
		pages.appendTag(new NBTTagString("1", "     Programmable\n        RedNet\n      Controller\n\nOwner's Manual v1.0\n\n\n" + 
				"WARNING: YOU MAY NEED TO ADJUST YOUR GUI SCALE SMALLER TO APPROPRIATELY USE THIS PRODUCT"));
		pages.appendTag(new NBTTagString("2", "Congratulations on your purchase, assembly, or theft of your new Programmable RedNet Controller (PRC). " +
				"This manual will attempt to guide you through its features and operations."));
		pages.appendTag(new NBTTagString("3", "Features\n\n* 16 I/O points on\n  each side\n* 16 internal\n  variables\n* 6 user-defined\n  circuits\n" +
				"* Expandable with\n  LX-100, 300, 500\n  expansion modules\n  (sold separately)"));
		pages.appendTag(new NBTTagString("4", "Circuits\n\nThe PRC is composed of one or more circuits. Each circuit is executed sequentially on every " +
				"PRC clock cycle. Circuits are composed of inputs and outputs."));
		pages.appendTag(new NBTTagString("5", "Circuits\n\nInputs can come from I/O, variables, or constant values. Outputs can be routed to I/O, " +
				"variables, or null (disconnected). These options are discussed separately."));
		pages.appendTag(new NBTTagString("6", "Circuits\n\nAll circuits default to No-Op when the PRC is initialized."));
		pages.appendTag(new NBTTagString("7", "Constants\n\nThe PRC can use constant values for its inputs. It contains 16 constants, with values " +
				"0-15 in them."));
		pages.appendTag(new NBTTagString("8", "I/O\n\nEach face of the PRC can be connected to up to 16 I/O points. The front face cannot be connected, " +
				"but the PRC can be rotated by using your standard sledgehammer."));
		pages.appendTag(new NBTTagString("9", "I/O\n\nEach I/O point is indicated by a color, ranging from white to black as per the standard elements. " +
				"I/O inputs will update on the next PRC clock cycle."));
		pages.appendTag(new NBTTagString("10", "Variables\n\nThe PRC contains variables that can be used by its circuits. Variables propagate between " +
				"circuits on the same clock cycle; unlike the I/O delay."));
		pages.appendTag(new NBTTagString("11", "Null\n\nRoute outputs to Null if you do not want to use the value."));
		pages.appendTag(new NBTTagString("12", "GUI\n\nThe PRC GUI has four sections - from left to right they are input pins, circuit type, output pins, " +
				"and circuit selection."));
		pages.appendTag(new NBTTagString("13", "GUI: Circuit Type\n\nSelect a circuit type from the list to change the selected circuit in the PRC to that " +
				"circuit type. Use the Next and Prev buttons to change which circuit is being edited."));
		pages.appendTag(new NBTTagString("14", "GUI: Pin Mapping\n\nPRC pins are connected in two parts, buffer and pin number. Buffer selects I/O and face, " +
				"variables, constants, or null, as applicable. Pin chooses which I/O color, or which variable/constant."));
		pages.appendTag(new NBTTagString("15", "Examples\n\nThe next sections cover some basic PRC scenarios. Face your PRC West with your sledgehammer " +
				"before beginning. Ensure you have levers, lamps, redstone dust, and RedNet cabling."));
		pages.appendTag(new NBTTagString("16", "Example 1: And\n\nConnect RedNet cabling to the North face of your PRC, and place two levers and a lamp on " +
				"the cable. Set the levers to Orange and Magenta with your sledgehammer."));
		pages.appendTag(new NBTTagString("17", "Example 1: And\n\nOpen your PRC. Set circuit one to And (2 Input). Set the input I/Os to I/O N, Orange and " +
				"Magenta, and the output to I/O N, White."));
		pages.appendTag(new NBTTagString("18", "Example 1: And\n\nManipulate the levers. Watch the lamp. It will light when both levers are on " +
				"and deactivate when they are turned off."));
		pages.appendTag(new NBTTagString("19", "Example 2: Timer\n\nConnect RedNet cabling to the South face of your PRC, and attach a lamp to it. " +
				"Open your PRC and set circuit 2 to Wave (Square). Set the output to I/O S, White."));
		pages.appendTag(new NBTTagString("20", "Example 2: Timer\n\nThe lamp will blink. You can use the square wave generator for any device " +
				"that requires an alternating redstone signal."));
		pages.appendTag(new NBTTagString("21", "Example 3: Variables\n\nConnect RedNet cabling to the East face of your PRC, and run a line of " +
				"redstone dust away from the cable."));
		pages.appendTag(new NBTTagString("22", "Example 3: Variables\n\nOpen your PRC, and set circuit 3 to Wave (Triangle) and its output to " +
				"variable 0 (under the VARS buffer)."));
		pages.appendTag(new NBTTagString("23", "Example 3: Variables\n\nSet circuit 4 to Passthrough and its input to variable 0 and output to the " + 
				" I/O E White. Watch the dust rise and fall in power."));
		pages.appendTag(new NBTTagString("24", "Summary\n\nNote that the examples all function independently, as the PRC has multiple circuits and " +
				"can process many tasks at once. Amazing things can be accomplished using the PRC."));
		pages.appendTag(new NBTTagString("25", "We hope you enjoy your new Programmable RedNet Controller!"));
		
		nbt.setTag("pages", pages);
		manual = new ItemStack(Item.writtenBook);
		manual.setTagCompound(nbt);
	}
	
	public ItemBlockRedNetLogic(int id)
	{
		super(id);
		setContainerItem(this);
		setCreativeTab(MFRCreativeTab.tab);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes)
	{
		subTypes.add(new ItemStack(itemId, 1, 0));
		subTypes.add(manual);
	}
}
