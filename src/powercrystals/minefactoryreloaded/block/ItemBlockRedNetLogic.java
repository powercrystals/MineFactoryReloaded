package powercrystals.minefactoryreloaded.block;

import java.util.List;

import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
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
		nbt.setString("title", "PRC使用手册");
		
		NBTTagList pages = new NBTTagList();
		pages.appendTag(new NBTTagString("1", "     可编程\n        红石网络\n      控制器\n\n用户手册v1.0\n\n\n" + 
				"警告: 为正常阅读该说明书也许你需要将GUI大小适当调小。"));
		pages.appendTag(new NBTTagString("2", "恭喜你购买安装（或者从别处偷窃）这台全新的可编程红石网络控制器(PRC)。 " +
				"这本手册将指导你学习其特性以及操作。"));
		pages.appendTag(new NBTTagString("3", "特性\n\n* 每个方向均有16个I/O端口\n\n* 16个内部变量\n\n* 6种用户定义元件\n\n" +
				"* 可使用LX-100,300,500等型号的逻辑扩展模块扩展(扩展部分独立出售)\n\n"));
		pages.appendTag(new NBTTagString("4", "电路元件\n\nPRC可以看作多个元器件的组合。\n每个元件都安装每个PRC的时钟周期依次运行。\n" +
				"每个元件都有不等数目的输入和输出"));
		pages.appendTag(new NBTTagString("5", "电路元件\n\n输入可以是I/O端口，变量或者常量。输出连接到对应的I/O端口, " +
				"变量,或者为空(无相连部分). 这些选项均可独立设定。"));
		pages.appendTag(new NBTTagString("6", "电路元件\n\nPRC初始化以后，所有的元件默认为No-Op。"));
		pages.appendTag(new NBTTagString("7", "常量\n\nPRC可以使用常量作为输入。" +
				"可用常量为0-15的16个常数。"));
		pages.appendTag(new NBTTagString("8", "I/O端口\n\nPRC的前方不能连接电路，其余各面可以各链接到16个I/O口。" +
				"可以使用精密工业锤改变其朝向。"));
		pages.appendTag(new NBTTagString("9", "I/O端口\n\n每个I/O口对应一种颜色(从白到黑包括16种MC自带颜色) 。 " +
				"在每次PRC新的时钟周期开始时会更新一次会检查一次输入的I/O口更新。"));
		pages.appendTag(new NBTTagString("10", "变量\n\nPRC包含若干被其元件使用的变量。变量在时钟周期内可变化。 " +
				"不像I/O口数据那样有延迟。"));
		pages.appendTag(new NBTTagString("11", "空值\n\n如果你不想使用某数值，输出则为空值。"));
		pages.appendTag(new NBTTagString("12", "界面\n\nPRC的界面包括4部分-从左到右依次是输入引脚，元件类型， " +
				"输出引脚以及元件设置。"));
		pages.appendTag(new NBTTagString("13", "界面: 元件类型\n\n从列表中选择元件类型来使得PRC变成该种功能的元件。 " +
				"使用下一个以及上一个的按钮来选择编辑哪一个元件。"));
		pages.appendTag(new NBTTagString("14", "界面: 引脚映射\n\nPRC的引脚设定有两个部分：缓冲区和引脚数值。 缓冲区选择该引脚连接哪个方向的I/O口，或者属于变量，常量或者空值类型。 " +
				"引脚数值选择I/O口颜色，或者具体某个变量/常量。"));
		pages.appendTag(new NBTTagString("15", "界面：引脚映射\n\n用鼠标改变引脚数值：左击增加一个，右击减少一个 " +
				"，中键增加16(使用变量/常量类型时)或1（I/O颜色类型）。"));
		pages.appendTag(new NBTTagString("16", "红石网络读数表\n\n正如其名，它可以用来读取红石线缆上的数据,亦可以" +
				"用于读取PRC某状态的变量。"));
		pages.appendTag(new NBTTagString("17", "内存卡\n\n如果你需要备份PRC设置或者将其复制到其它PRC上，可以考虑使用内存卡。 " +
				"使用空白的内存卡会复制PRC上的程序，而使用已经编程过的内存卡则会向PRC写入程序。"));
		pages.appendTag(new NBTTagString("18", "范例\n\n本手册下面的部分将提供部分实例。如需要照样操作，请先用精密工业锤将PRC设置为面朝西方。 " +
				"下面将提供一些实际的应用范例。"));
		pages.appendTag(new NBTTagString("19", "范例\n\n为实践这些例子，你需要:\n* 若干红石线缆\n* 一些拉杆\n* 一些按钮\n* 一些红石灯\n" +
				"* 一个日光感应器\n* 一个活塞\n* 一个音符盒\n* 一个精密工业锤"));
		pages.appendTag(new NBTTagString("20", "例1: 与门\n\n在PRC北边连接红石线缆，并用线缆连接两个拉杆和一个红石灯。用精密工业锤设置拉杆输入分别为橙色和品红色。"));
		pages.appendTag(new NBTTagString("21", "例1: 与门\n\n打开PRC。设置元件1类型为与门(2输入)，设置输入为I/O N,橙色和品红色" +
				",设置输出为I/O N,白色。"));
		pages.appendTag(new NBTTagString("22", "例1: 与门\n\n多次使用拉杆，观察红石灯变化。 当拉杆同时打开的时候灯会亮，否则则会熄灭。"));
		pages.appendTag(new NBTTagString("23", "例2: 定时器\n\n在PRC南边连接红石线缆,并连接上红石灯" +
				"打开PRC设置元件2类型为矩形波发射器。设置输入为CNST 40。设置输出为I/O S,白色。"));
		pages.appendTag(new NBTTagString("24", "例2: 定时器\n\n红石灯大约会每秒闪一次。你可以使用矩形波发生器来控制" +
				"任何需要红石脉冲的机器。"));
		pages.appendTag(new NBTTagString("25", "例2: 定时器\n\nPRC每秒大概运行20个时钟周期。调整定时器的周期，让它变快或者变慢，" +
				"当数值小于5的时候，定时器闪烁过快，将不能正确控制设备。"));
		pages.appendTag(new NBTTagString("26", "例3: 门的触发控制\n\n在PRC东边连接线缆，并连接上设置为白色位的按钮和橙色位的活塞。" +
				"如果还有其余部分，你可以扩建成一个真实的门。"));
		pages.appendTag(new NBTTagString("27", "例3: 门的触发控制\n\n打开PRC，并设置元件3为T-触发器。将输入都设置为I/O E,白色" +
				"Q端口则设置为I/O E,橙色。 按下按钮。"));
		pages.appendTag(new NBTTagString("28", "例3: 门的触发控制\n\n注意观察按钮按下时活塞的反应。本例子中T触发器的输入采样在个时钟周期的上升沿，" +
				"因此不介意的话，可以都连接到输入端"));
		pages.appendTag(new NBTTagString("29", "例4: 日光感应器/变量\n\n在PRC顶部连接红石线缆，并连上设置为白色位的日光感应器，" +
				"以及设置为橙色的音符盒。右击三下为音符盒设置一个音符。"));
		pages.appendTag(new NBTTagString("30", "例4: 日光感应器/变量\n\n打开PRC。选择元件4类型为判断器：等于,输入为I/O U,白色；CNST 3而输出" +
				"为VARS 0。设置元件5类型为单次脉冲发生器，输入为VARS 0，输出为I/O U,橙色。"));
		pages.appendTag(new NBTTagString("31", "例4: 日光感应器/变量\n\n日出或者日落时,日光感应器输出为3。 当" +
				"输出为3的时候，则会发出一次脉冲激活音符盒。用这个可以做闹钟呢：)!!"));
		pages.appendTag(new NBTTagString("32", "例5: 日光钟\n\n建造一个 \"8\"形状的红石灯建筑。看上去像这样:\n" +
				" XX \nX  X\nX  X\n XX \nX  X\nX  X\n XX \n确保没有其他方块与之相连。"));
		pages.appendTag(new NBTTagString("33", "例5: 日光钟\n\n从左上开始,顺时针用红色线缆与之相连，颜色设置各部分则如下:" +
				"青色,紫色,蓝色,棕色,绿色,红色,黑色。红色对应左上部分，而黑色为中间的一横。 "));
		pages.appendTag(new NBTTagString("34", "例5: 日光钟\n\n将这些线缆连接到顶部(依然包括一个日光感应器)。" +
				"打开PRC,选择元件6类型为七段译码器。"));
		pages.appendTag(new NBTTagString("35", "例5: 日光钟\n\n设置I为I/O U 白色,而A到G则分别按顺序设置为从青到黑。" +
				"若你连线都正确，那么红石灯会显示出当前的光照值。"));
		pages.appendTag(new NBTTagString("36", "例5: 日光钟\n\n注意到日光光感应器的输出为0-15,而红石灯会显示十六进制的数字，即" +
				"0-9以及A-F。"));
		pages.appendTag(new NBTTagString("37", "其他建议\n\n* 试试用各种波形发生器连接红石粉\n* 试试使用计数器。" +
				"\n* 试试用测重压力盘连接比较器。"));
		pages.appendTag(new NBTTagString("38", "结束语\n\n注意这些例子的功能都是独立的。而PRC可以同时包含多个元件并同时运作，因此用PRC可以造出非常神奇的东西。"));
		pages.appendTag(new NBTTagString("39", "衷心希望你使用PRC愉快!"));
		
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
