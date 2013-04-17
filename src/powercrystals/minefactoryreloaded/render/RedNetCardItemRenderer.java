package powercrystals.minefactoryreloaded.render;

import org.lwjgl.opengl.GL11;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RedNetCardItemRenderer implements IItemRenderer
{
	private RedNetCardsModel _cardsModel = new RedNetCardsModel();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{

		RenderEngine renderengine = Minecraft.getMinecraft().renderEngine;

		if(renderengine != null)
		{
			renderengine.bindTexture(MineFactoryReloadedCore.tileEntityFolder + "cards.png");
		}

		GL11.glPushMatrix();

		GL11.glTranslatef(-0.5f, 0, 0);
		switch(type)
		{
			case EQUIPPED:
				GL11.glRotatef(60, 0, 1, 0);
				GL11.glRotatef(200, 1, 0, 0);
				GL11.glRotatef(120, 0, 0, 1);
				GL11.glTranslatef(-1.8f, 0.5f, -1.0f);
				break;
			case INVENTORY:
				GL11.glTranslatef(0, -0.2f, 0);
				break;
			default:
				break;
		}

		GL11.glScalef(1.7f, 1.7f, 1.7f);

		switch(item.getItemDamage())
		{
		case 0:
			_cardsModel.renderLevel1(0.0625f);
			break;
		case 1:
			_cardsModel.renderLevel2(0.0625f);
			break;
		case 2:
			_cardsModel.renderLevel3(0.0625f);
			break;
		default:
			_cardsModel.renderEmptySlot(0.0625f);
		}

		GL11.glPopMatrix();
	}
}