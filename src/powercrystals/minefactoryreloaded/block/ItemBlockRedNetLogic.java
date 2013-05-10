package powercrystals.minefactoryreloaded.block;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		pages.appendTag(new NBTTagString("15", "GUI: Pin Mapping\n\nLeft click advances by one, and right click reverses by one. Middle click " +
				"advances by 16 when using variables or constants (one for I/O)."));
		pages.appendTag(new NBTTagString("16", "RedNet Meter\n\nMuch like it can be used to read out values of RedNet cables, the RedNet Meter can " +
				"be used to read out the state of the PRC's variables."));
		pages.appendTag(new NBTTagString("17", "Memory Cards\n\nIf you need to back up your PRC or copy it to another PRC, use a memory card. Using " +
				"a blank card on a PRC will copy its program, and using a programmed card will write its program."));
		pages.appendTag(new NBTTagString("18", "Examples\n\nThe next sections cover some basic PRC scenarios. Face your PRC West with your sledgehammer " +
				"before beginning. These examples assume access to some supplies (next page)."));
		pages.appendTag(new NBTTagString("19", "Examples\n\nYou will need:\n* A few dozen cables\n* Several levers\n* Several buttons\n* Several lamps\n" +
				"* A daylight sensor\n* A piston\n* A note block\n* A sledgehammer"));
		pages.appendTag(new NBTTagString("20", "Example 1: AND gate\n\nConnect RedNet cabling to the North face of your PRC, and place two levers and a lamp on " +
				"the cable. Set the levers to Orange and Magenta with your sledgehammer."));
		pages.appendTag(new NBTTagString("21", "Ex 1: AND gate\n\nOpen your PRC. Set circuit one to And (2 Input). Set the input I/Os to I/O N, Orange and " +
				"Magenta, and the output to I/O N, White."));
		pages.appendTag(new NBTTagString("22", "Ex 1: AND gate\n\nManipulate the levers. Watch the lamp. It will light when both levers are on " +
				"and deactivate when they are turned off."));
		pages.appendTag(new NBTTagString("23", "Ex 2: Timer\n\nConnect RedNet cabling to the South face of your PRC, and attach a lamp to it. " +
				"Open your PRC and set circuit 2 to Wave (Square). Set the input to CNST 40. Set the output to I/O S, White."));
		pages.appendTag(new NBTTagString("24", "Ex 2: Timer\n\nThe lamp will blink about once per second. You can use the square wave generator for any device " +
				"that requires an alternating redstone signal."));
		pages.appendTag(new NBTTagString("25", "Ex 2: Timer\n\nThe PRC runs at a clock rate of 20 cycles per second. Adjust the period (Pd) of the " +
				"timer to make it faster or slower. Values too fast (<5 t) may not work correctly with all devices."));
		pages.appendTag(new NBTTagString("26", "Ex 3: Door Toggle\n\nConnect cabling to the East face of your PRC. Attach a button to White and a piston to Orange. " +
				"If you have extra parts, you may want to build a real door."));
		pages.appendTag(new NBTTagString("27", "Ex 3: Door Toggle\n\nOpen your PRC and set circuit 3 to a T-FlipFlop. Connect I/O E, White to both inputs, " +
				"and I/O E Orange to Q. Press the button."));
		pages.appendTag(new NBTTagString("28", "Ex 3: Door Toggle\n\nNote the piston extends and retracts on each button press. The T-FlipFlop samples the input " +
				"value on a rising edge of CLK - if you don't care, connect both to the same input as here."));
		pages.appendTag(new NBTTagString("29", "Ex 4: Daylight Sensor/Variables\n\nConnect RedNet cabling to the top of your PRC. Attach a daylight sensor to White, " +
				"and a note block to Orange. Set a pleasing tone on the note block."));
		pages.appendTag(new NBTTagString("30", "Ex 4: Daylight Sensor/Variables\n\nOpen the PRC. Set circuit 4 to Equals, with I/O U White and CNST 3 as inputs, and the " +
				"output to VARS 0. Set circuit 5 to a One-Shot Pulse, with input VARS 0 and output I/O U Orange."));
		pages.appendTag(new NBTTagString("31", "Ex 4: Daylight Sensor/Variables\n\nWhen the sun rises and sets, the daylight sensor's output level will reach 3. As it " +
				"arrives at that value, the note block will be pulsed. Use it as an alarm clock!"));
		pages.appendTag(new NBTTagString("32", "Ex 5: Daylight Clock\n\nConstruct an \"8\" out of redstone lamps. It should look like this:\n" +
				" XX \nX  X\nX  X\n XX \nX  X\nX  X\n XX \nEnsure no blocks connect the lamps."));
		pages.appendTag(new NBTTagString("33", "Ex 5: Daylight Clock\n\nStarting with the top section and proceeding clockwise, connect them as follows: " +
				" cyan, purple, blue, brown, green, red, black. Red should be the top-left pair and black the center pair."));
		pages.appendTag(new NBTTagString("34", "Ex 5: Daylight Clock\n\nConnect those wires to the top face of the PRC (along with the daylight sensor). " +
				"Open the PRC, and set circuit 6 to Seven-Segment Encoder."));
		pages.appendTag(new NBTTagString("35", "Ex 5: Daylight Clock\n\nConnect I to I/O U White, and A through G to cyan through black in order. If you " +
				"have wired everything correctly, the clock will display the current daylight value."));
		pages.appendTag(new NBTTagString("36", "Ex 5: Daylight Clock\n\nNote that as the daylight sensor has outputs 0-15, these are reprsented as hex characters " +
				"as 0-9 and A-F."));
		pages.appendTag(new NBTTagString("37", "Additional Suggestions\n\n* Try the various wave generators connected to redstone dust\n* Try using the counter - " +
				"PRE is the rollover target.\n* Try using weighted pressure plates combined with greater/less than."));
		pages.appendTag(new NBTTagString("38", "Summary\n\nNote that the examples all function independently, as the PRC has multiple circuits and " +
				"can process many tasks at once. Amazing things can be accomplished using the PRC."));
		pages.appendTag(new NBTTagString("39", "We hope you enjoy your new Programmable RedNet Controller!"));
		
		nbt.setTag("pages", pages);
		manual = new ItemStack(Item.writtenBook);
		manual.setTagCompound(nbt);
	}
	
	public ItemBlockRedNetLogic(int id)
	{
		super(id);
		setContainerItem(this);
		setCreativeTab(MFRCreativeTab.tab);
		setMaxStackSize(1);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		infoList.add("If you're lost, read the (in-game) manual");
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
