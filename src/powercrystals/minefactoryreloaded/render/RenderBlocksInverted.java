package powercrystals.minefactoryreloaded.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

public class RenderBlocksInverted
{
	/**
	 * Renders a standard cube block at the given coordinates
	 */
	public boolean renderStandardBlock(RenderBlocks renderer, Block par1Block, int par2, int par3, int par4)
	{
		int l = par1Block.colorMultiplier(renderer.blockAccess, par2, par3, par4);
		float f = (l >> 16 & 255) / 255.0F;
		float f1 = (l >> 8 & 255) / 255.0F;
		float f2 = (l & 255) / 255.0F;
		
		if(EntityRenderer.anaglyphEnable)
		{
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}
		
		return Minecraft.isAmbientOcclusionEnabled() && Block.lightValue[par1Block.blockID] == 0 ? (renderer.partialRenderBounds ? this.func_102027_b(renderer,
				par1Block, par2, par3, par4, f, f1, f2) : this.renderStandardBlockWithAmbientOcclusion(renderer, par1Block, par2, par3, par4, f, f1, f2))
				: this.renderStandardBlockWithColorMultiplier(renderer, par1Block, par2, par3, par4, f, f1, f2);
	}
	
	public boolean func_102027_b(RenderBlocks renderer, Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
	{
		renderer.enableAO = true;
		boolean flag = false;
		float f3 = 0.0F;
		float f4 = 0.0F;
		float f5 = 0.0F;
		float f6 = 0.0F;
		boolean flag1 = true;
		int l = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(983055);
		
		if(renderer.getBlockIcon(par1Block).getIconName().equals("grass_top"))
		{
			flag1 = false;
		}
		else if(renderer.hasOverrideBlockTexture())
		{
			flag1 = false;
		}
		
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f7;
		int i1;
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3 - 1, par4, 0))
		{
			if(renderer.renderMinY <= 0.0D)
			{
				--par3;
			}
			
			renderer.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoLightValueScratchXYNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoLightValueScratchYZNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoLightValueScratchYZNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoLightValueScratchXYPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4);
			flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
			flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
			
			if(!flag4 && !flag2)
			{
				renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXYNN;
				renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXYNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4 - 1);
				renderer.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4 - 1);
			}
			
			if(!flag5 && !flag2)
			{
				renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXYNN;
				renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXYNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4 + 1);
				renderer.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4 + 1);
			}
			
			if(!flag4 && !flag3)
			{
				renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXYPN;
				renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXYPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4 - 1);
				renderer.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4 - 1);
			}
			
			if(!flag5 && !flag3)
			{
				renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXYPN;
				renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXYPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4 + 1);
				renderer.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4 + 1);
			}
			
			if(renderer.renderMinY <= 0.0D)
			{
				++par3;
			}
			
			i1 = l;
			
			if(renderer.renderMinY <= 0.0D || !renderer.blockAccess.isBlockOpaqueCube(par2, par3 - 1, par4))
			{
				i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			}
			
			f7 = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4);
			f3 = (renderer.aoLightValueScratchXYZNNP + renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchYZNP + f7) / 4.0F;
			f6 = (renderer.aoLightValueScratchYZNP + f7 + renderer.aoLightValueScratchXYZPNP + renderer.aoLightValueScratchXYPN) / 4.0F;
			f5 = (f7 + renderer.aoLightValueScratchYZNN + renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXYZPNN) / 4.0F;
			f4 = (renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXYZNNN + f7 + renderer.aoLightValueScratchYZNN) / 4.0F;
			renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXYNN, renderer.aoBrightnessYZNP, i1);
			renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessYZNP, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXYPN, i1);
			renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessYZNN, renderer.aoBrightnessXYPN, renderer.aoBrightnessXYZPNN, i1);
			renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessXYNN, renderer.aoBrightnessXYZNNN, renderer.aoBrightnessYZNN, i1);
			
			if(flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.5F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.5F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.5F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.5F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.5F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.5F;
			}
			
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			this.renderBottomFace(renderer, par1Block, par2, par3, par4,
					renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 0));
			flag = true;
		}
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3 + 1, par4, 1))
		{
			if(renderer.renderMaxY >= 1.0D)
			{
				++par3;
			}
			
			renderer.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoLightValueScratchXYNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoLightValueScratchXYPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoLightValueScratchYZPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoLightValueScratchYZPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 + 1);
			flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
			
			if(!flag4 && !flag2)
			{
				renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXYNP;
				renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXYNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4 - 1);
				renderer.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4 - 1);
			}
			
			if(!flag4 && !flag3)
			{
				renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXYPP;
				renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXYPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4 - 1);
				renderer.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4 - 1);
			}
			
			if(!flag5 && !flag2)
			{
				renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXYNP;
				renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXYNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4 + 1);
				renderer.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4 + 1);
			}
			
			if(!flag5 && !flag3)
			{
				renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXYPP;
				renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXYPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4 + 1);
				renderer.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4 + 1);
			}
			
			if(renderer.renderMaxY >= 1.0D)
			{
				--par3;
			}
			
			i1 = l;
			
			if(renderer.renderMaxY >= 1.0D || !renderer.blockAccess.isBlockOpaqueCube(par2, par3 + 1, par4))
			{
				i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			}
			
			f7 = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4);
			f6 = (renderer.aoLightValueScratchXYZNPP + renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchYZPP + f7) / 4.0F;
			f3 = (renderer.aoLightValueScratchYZPP + f7 + renderer.aoLightValueScratchXYZPPP + renderer.aoLightValueScratchXYPP) / 4.0F;
			f4 = (f7 + renderer.aoLightValueScratchYZPN + renderer.aoLightValueScratchXYPP + renderer.aoLightValueScratchXYZPPN) / 4.0F;
			f5 = (renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchXYZNPN + f7 + renderer.aoLightValueScratchYZPN) / 4.0F;
			renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessXYZNPP, renderer.aoBrightnessXYNP, renderer.aoBrightnessYZPP, i1);
			renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessYZPP, renderer.aoBrightnessXYZPPP, renderer.aoBrightnessXYPP, i1);
			renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessYZPN, renderer.aoBrightnessXYPP, renderer.aoBrightnessXYZPPN, i1);
			renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessXYNP, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessYZPN, i1);
			renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5;
			renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6;
			renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7;
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			this.renderTopFace(renderer, par1Block, par2, par3, par4,
					renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 1));
			flag = true;
		}
		
		float f8;
		float f9;
		float f10;
		float f11;
		int j1;
		int k1;
		int l1;
		int i2;
		Icon icon;
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3, par4 - 1, 2))
		{
			if(renderer.renderMinZ <= 0.0D)
			{
				--par4;
			}
			
			renderer.aoLightValueScratchXZNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoLightValueScratchYZNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoLightValueScratchYZPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4);
			renderer.aoLightValueScratchXZPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			renderer.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
			flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
			flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
			
			if(!flag2 && !flag4)
			{
				renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXZNN;
				renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXZNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3 - 1, par4);
				renderer.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3 - 1, par4);
			}
			
			if(!flag2 && !flag5)
			{
				renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXZNN;
				renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXZNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3 + 1, par4);
				renderer.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3 + 1, par4);
			}
			
			if(!flag3 && !flag4)
			{
				renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXZPN;
				renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXZPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3 - 1, par4);
				renderer.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3 - 1, par4);
			}
			
			if(!flag3 && !flag5)
			{
				renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXZPN;
				renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXZPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3 + 1, par4);
				renderer.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3 + 1, par4);
			}
			
			if(renderer.renderMinZ <= 0.0D)
			{
				++par4;
			}
			
			i1 = l;
			
			if(renderer.renderMinZ <= 0.0D || !renderer.blockAccess.isBlockOpaqueCube(par2, par3, par4 - 1))
			{
				i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			}
			
			f7 = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 - 1);
			f9 = (renderer.aoLightValueScratchXZNN + renderer.aoLightValueScratchXYZNPN + f7 + renderer.aoLightValueScratchYZPN) / 4.0F;
			f8 = (f7 + renderer.aoLightValueScratchYZPN + renderer.aoLightValueScratchXZPN + renderer.aoLightValueScratchXYZPPN) / 4.0F;
			f11 = (renderer.aoLightValueScratchYZNN + f7 + renderer.aoLightValueScratchXYZPNN + renderer.aoLightValueScratchXZPN) / 4.0F;
			f10 = (renderer.aoLightValueScratchXYZNNN + renderer.aoLightValueScratchXZNN + renderer.aoLightValueScratchYZNN + f7) / 4.0F;
			f3 = (float) (f9 * renderer.renderMaxY * (1.0D - renderer.renderMinX) + f8 * renderer.renderMinY * renderer.renderMinX
					+ f11 * (1.0D - renderer.renderMaxY) * renderer.renderMinX + f10 * (1.0D - renderer.renderMaxY)
					* (1.0D - renderer.renderMinX));
			f4 = (float) (f9 * renderer.renderMaxY * (1.0D - renderer.renderMaxX) + f8 * renderer.renderMaxY * renderer.renderMaxX
					+ f11 * (1.0D - renderer.renderMaxY) * renderer.renderMaxX + f10 * (1.0D - renderer.renderMaxY)
					* (1.0D - renderer.renderMaxX));
			f5 = (float) (f9 * renderer.renderMinY * (1.0D - renderer.renderMaxX) + f8 * renderer.renderMinY * renderer.renderMaxX
					+ f11 * (1.0D - renderer.renderMinY) * renderer.renderMaxX + f10 * (1.0D - renderer.renderMinY)
					* (1.0D - renderer.renderMaxX));
			f6 = (float) (f9 * renderer.renderMinY * (1.0D - renderer.renderMinX) + f8 * renderer.renderMinY * renderer.renderMinX
					+ f11 * (1.0D - renderer.renderMinY) * renderer.renderMinX + f10 * (1.0D - renderer.renderMinY)
					* (1.0D - renderer.renderMinX));
			k1 = renderer.getAoBrightness(renderer.aoBrightnessXZNN, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessYZPN, i1);
			j1 = renderer.getAoBrightness(renderer.aoBrightnessYZPN, renderer.aoBrightnessXZPN, renderer.aoBrightnessXYZPPN, i1);
			i2 = renderer.getAoBrightness(renderer.aoBrightnessYZNN, renderer.aoBrightnessXYZPNN, renderer.aoBrightnessXZPN, i1);
			l1 = renderer.getAoBrightness(renderer.aoBrightnessXYZNNN, renderer.aoBrightnessXZNN, renderer.aoBrightnessYZNN, i1);
			renderer.brightnessTopLeft = renderer.mixAoBrightness(k1, j1, i2, l1, renderer.renderMaxY * (1.0D - renderer.renderMinX), renderer.renderMaxY
					* renderer.renderMinX, (1.0D - renderer.renderMaxY) * renderer.renderMinX, (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinX));
			renderer.brightnessBottomLeft = renderer.mixAoBrightness(k1, j1, i2, l1, renderer.renderMaxY * (1.0D - renderer.renderMaxX), renderer.renderMaxY
					* renderer.renderMaxX, (1.0D - renderer.renderMaxY) * renderer.renderMaxX, (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxX));
			renderer.brightnessBottomRight = renderer.mixAoBrightness(k1, j1, i2, l1, renderer.renderMinY * (1.0D - renderer.renderMaxX), renderer.renderMinY
					* renderer.renderMaxX, (1.0D - renderer.renderMinY) * renderer.renderMaxX, (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxX));
			renderer.brightnessTopRight = renderer.mixAoBrightness(k1, j1, i2, l1, renderer.renderMinY * (1.0D - renderer.renderMinX), renderer.renderMinY
					* renderer.renderMinX, (1.0D - renderer.renderMinY) * renderer.renderMinX, (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinX));
			
			if(flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.8F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.8F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.8F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.8F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.8F;
			}
			
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 2);
			this.renderEastFace(renderer, par1Block, par2, par3, par4, icon);
			
			if(RenderBlocks.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				renderer.colorRedTopLeft *= par5;
				renderer.colorRedBottomLeft *= par5;
				renderer.colorRedBottomRight *= par5;
				renderer.colorRedTopRight *= par5;
				renderer.colorGreenTopLeft *= par6;
				renderer.colorGreenBottomLeft *= par6;
				renderer.colorGreenBottomRight *= par6;
				renderer.colorGreenTopRight *= par6;
				renderer.colorBlueTopLeft *= par7;
				renderer.colorBlueBottomLeft *= par7;
				renderer.colorBlueBottomRight *= par7;
				renderer.colorBlueTopRight *= par7;
				this.renderEastFace(renderer, par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			
			flag = true;
		}
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3, par4 + 1, 3))
		{
			if(renderer.renderMaxZ >= 1.0D)
			{
				++par4;
			}
			
			renderer.aoLightValueScratchXZNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoLightValueScratchXZPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoLightValueScratchYZNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoLightValueScratchYZPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4);
			renderer.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
			flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
			
			if(!flag2 && !flag4)
			{
				renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXZNP;
				renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXZNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3 - 1, par4);
				renderer.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3 - 1, par4);
			}
			
			if(!flag2 && !flag5)
			{
				renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXZNP;
				renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXZNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3 + 1, par4);
				renderer.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3 + 1, par4);
			}
			
			if(!flag3 && !flag4)
			{
				renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXZPP;
				renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXZPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3 - 1, par4);
				renderer.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3 - 1, par4);
			}
			
			if(!flag3 && !flag5)
			{
				renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXZPP;
				renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXZPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3 + 1, par4);
				renderer.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3 + 1, par4);
			}
			
			if(renderer.renderMaxZ >= 1.0D)
			{
				--par4;
			}
			
			i1 = l;
			
			if(renderer.renderMaxZ >= 1.0D || !renderer.blockAccess.isBlockOpaqueCube(par2, par3, par4 + 1))
			{
				i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			}
			
			f7 = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 + 1);
			f9 = (renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchXYZNPP + f7 + renderer.aoLightValueScratchYZPP) / 4.0F;
			f8 = (f7 + renderer.aoLightValueScratchYZPP + renderer.aoLightValueScratchXZPP + renderer.aoLightValueScratchXYZPPP) / 4.0F;
			f11 = (renderer.aoLightValueScratchYZNP + f7 + renderer.aoLightValueScratchXYZPNP + renderer.aoLightValueScratchXZPP) / 4.0F;
			f10 = (renderer.aoLightValueScratchXYZNNP + renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchYZNP + f7) / 4.0F;
			f3 = (float) (f9 * renderer.renderMaxY * (1.0D - renderer.renderMinX) + f8 * renderer.renderMaxY * renderer.renderMinX
					+ f11 * (1.0D - renderer.renderMaxY) * renderer.renderMinX + f10 * (1.0D - renderer.renderMaxY)
					* (1.0D - renderer.renderMinX));
			f4 = (float) (f9 * renderer.renderMinY * (1.0D - renderer.renderMinX) + f8 * renderer.renderMinY * renderer.renderMinX
					+ f11 * (1.0D - renderer.renderMinY) * renderer.renderMinX + f10 * (1.0D - renderer.renderMinY)
					* (1.0D - renderer.renderMinX));
			f5 = (float) (f9 * renderer.renderMinY * (1.0D - renderer.renderMaxX) + f8 * renderer.renderMinY * renderer.renderMaxX
					+ f11 * (1.0D - renderer.renderMinY) * renderer.renderMaxX + f10 * (1.0D - renderer.renderMinY)
					* (1.0D - renderer.renderMaxX));
			f6 = (float) (f9 * renderer.renderMaxY * (1.0D - renderer.renderMaxX) + f8 * renderer.renderMaxY * renderer.renderMaxX
					+ f11 * (1.0D - renderer.renderMaxY) * renderer.renderMaxX + f10 * (1.0D - renderer.renderMaxY)
					* (1.0D - renderer.renderMaxX));
			k1 = renderer.getAoBrightness(renderer.aoBrightnessXZNP, renderer.aoBrightnessXYZNPP, renderer.aoBrightnessYZPP, i1);
			j1 = renderer.getAoBrightness(renderer.aoBrightnessYZPP, renderer.aoBrightnessXZPP, renderer.aoBrightnessXYZPPP, i1);
			i2 = renderer.getAoBrightness(renderer.aoBrightnessYZNP, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXZPP, i1);
			l1 = renderer.getAoBrightness(renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXZNP, renderer.aoBrightnessYZNP, i1);
			renderer.brightnessTopLeft = renderer.mixAoBrightness(k1, l1, i2, j1, renderer.renderMaxY * (1.0D - renderer.renderMinX),
					(1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinX), (1.0D - renderer.renderMaxY) * renderer.renderMinX, renderer.renderMaxY
					* renderer.renderMinX);
			renderer.brightnessBottomLeft = renderer.mixAoBrightness(k1, l1, i2, j1, renderer.renderMinY * (1.0D - renderer.renderMinX),
					(1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinX), (1.0D - renderer.renderMinY) * renderer.renderMinX, renderer.renderMinY
					* renderer.renderMinX);
			renderer.brightnessBottomRight = renderer.mixAoBrightness(k1, l1, i2, j1, renderer.renderMinY * (1.0D - renderer.renderMaxX),
					(1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxX), (1.0D - renderer.renderMinY) * renderer.renderMaxX, renderer.renderMinY
					* renderer.renderMaxX);
			renderer.brightnessTopRight = renderer.mixAoBrightness(k1, l1, i2, j1, renderer.renderMaxY * (1.0D - renderer.renderMaxX),
					(1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxX), (1.0D - renderer.renderMaxY) * renderer.renderMaxX, renderer.renderMaxY
					* renderer.renderMaxX);
			
			if(flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.8F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.8F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.8F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.8F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.8F;
			}
			
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 3);
			this.renderWestFace(renderer, par1Block, par2, par3, par4,
					renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 3));
			
			if(RenderBlocks.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				renderer.colorRedTopLeft *= par5;
				renderer.colorRedBottomLeft *= par5;
				renderer.colorRedBottomRight *= par5;
				renderer.colorRedTopRight *= par5;
				renderer.colorGreenTopLeft *= par6;
				renderer.colorGreenBottomLeft *= par6;
				renderer.colorGreenBottomRight *= par6;
				renderer.colorGreenTopRight *= par6;
				renderer.colorBlueTopLeft *= par7;
				renderer.colorBlueBottomLeft *= par7;
				renderer.colorBlueBottomRight *= par7;
				renderer.colorBlueTopRight *= par7;
				this.renderWestFace(renderer, par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			
			flag = true;
		}
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2 - 1, par3, par4, 4))
		{
			if(renderer.renderMinX <= 0.0D)
			{
				--par2;
			}
			
			renderer.aoLightValueScratchXYNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoLightValueScratchXZNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoLightValueScratchXZNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoLightValueScratchXYNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4);
			renderer.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
			
			if(!flag5 && !flag2)
			{
				renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXZNN;
				renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXZNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4 - 1);
				renderer.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4 - 1);
			}
			
			if(!flag4 && !flag2)
			{
				renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXZNP;
				renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXZNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4 + 1);
				renderer.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4 + 1);
			}
			
			if(!flag5 && !flag3)
			{
				renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXZNN;
				renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXZNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4 - 1);
				renderer.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4 - 1);
			}
			
			if(!flag4 && !flag3)
			{
				renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXZNP;
				renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXZNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4 + 1);
				renderer.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4 + 1);
			}
			
			if(renderer.renderMinX <= 0.0D)
			{
				++par2;
			}
			
			i1 = l;
			
			if(renderer.renderMinX <= 0.0D || !renderer.blockAccess.isBlockOpaqueCube(par2 - 1, par3, par4))
			{
				i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			}
			
			f7 = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4);
			f9 = (renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXYZNNP + f7 + renderer.aoLightValueScratchXZNP) / 4.0F;
			f8 = (f7 + renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchXYZNPP) / 4.0F;
			f11 = (renderer.aoLightValueScratchXZNN + f7 + renderer.aoLightValueScratchXYZNPN + renderer.aoLightValueScratchXYNP) / 4.0F;
			f10 = (renderer.aoLightValueScratchXYZNNN + renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXZNN + f7) / 4.0F;
			f3 = (float) (f8 * renderer.renderMaxY * renderer.renderMaxZ + f11 * renderer.renderMaxY * (1.0D - renderer.renderMaxZ)
					+ f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxZ) + f9 * (1.0D - renderer.renderMaxY)
					* renderer.renderMaxZ);
			f4 = (float) (f8 * renderer.renderMaxY * renderer.renderMinZ + f11 * renderer.renderMaxY * (1.0D - renderer.renderMinZ)
					+ f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinZ) + f9 * (1.0D - renderer.renderMaxY)
					* renderer.renderMinZ);
			f5 = (float) (f8 * renderer.renderMinY * renderer.renderMinZ + f11 * renderer.renderMinY * (1.0D - renderer.renderMinZ)
					+ f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinZ) + f9 * (1.0D - renderer.renderMinY)
					* renderer.renderMinZ);
			f6 = (float) (f8 * renderer.renderMinY * renderer.renderMaxZ + f11 * renderer.renderMinY * (1.0D - renderer.renderMaxZ)
					+ f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxZ) + f9 * (1.0D - renderer.renderMinY)
					* renderer.renderMaxZ);
			k1 = renderer.getAoBrightness(renderer.aoBrightnessXYNN, renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXZNP, i1);
			j1 = renderer.getAoBrightness(renderer.aoBrightnessXZNP, renderer.aoBrightnessXYNP, renderer.aoBrightnessXYZNPP, i1);
			i2 = renderer.getAoBrightness(renderer.aoBrightnessXZNN, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessXYNP, i1);
			l1 = renderer.getAoBrightness(renderer.aoBrightnessXYZNNN, renderer.aoBrightnessXYNN, renderer.aoBrightnessXZNN, i1);
			renderer.brightnessTopLeft = renderer.mixAoBrightness(j1, i2, l1, k1, renderer.renderMaxY * renderer.renderMaxZ, renderer.renderMaxY
					* (1.0D - renderer.renderMaxZ), (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxZ), (1.0D - renderer.renderMaxY)
					* renderer.renderMaxZ);
			renderer.brightnessBottomLeft = renderer.mixAoBrightness(j1, i2, l1, k1, renderer.renderMaxY * renderer.renderMinZ, renderer.renderMaxY
					* (1.0D - renderer.renderMinZ), (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinZ), (1.0D - renderer.renderMaxY)
					* renderer.renderMinZ);
			renderer.brightnessBottomRight = renderer.mixAoBrightness(j1, i2, l1, k1, renderer.renderMinY * renderer.renderMinZ, renderer.renderMinY
					* (1.0D - renderer.renderMinZ), (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinZ), (1.0D - renderer.renderMinY)
					* renderer.renderMinZ);
			renderer.brightnessTopRight = renderer.mixAoBrightness(j1, i2, l1, k1, renderer.renderMinY * renderer.renderMaxZ, renderer.renderMinY
					* (1.0D - renderer.renderMaxZ), (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxZ), (1.0D - renderer.renderMinY)
					* renderer.renderMaxZ);
			
			if(flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.6F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.6F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.6F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.6F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.6F;
			}
			
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 4);
			this.renderNorthFace(renderer, par1Block, par2, par3, par4, icon);
			
			if(RenderBlocks.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				renderer.colorRedTopLeft *= par5;
				renderer.colorRedBottomLeft *= par5;
				renderer.colorRedBottomRight *= par5;
				renderer.colorRedTopRight *= par5;
				renderer.colorGreenTopLeft *= par6;
				renderer.colorGreenBottomLeft *= par6;
				renderer.colorGreenBottomRight *= par6;
				renderer.colorGreenTopRight *= par6;
				renderer.colorBlueTopLeft *= par7;
				renderer.colorBlueBottomLeft *= par7;
				renderer.colorBlueBottomRight *= par7;
				renderer.colorBlueTopRight *= par7;
				this.renderNorthFace(renderer, par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			
			flag = true;
		}
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2 + 1, par3, par4, 5))
		{
			if(renderer.renderMaxX >= 1.0D)
			{
				++par2;
			}
			
			renderer.aoLightValueScratchXYPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoLightValueScratchXZPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoLightValueScratchXZPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoLightValueScratchXYPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4);
			renderer.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
			
			if(!flag2 && !flag4)
			{
				renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXZPN;
				renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXZPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4 - 1);
				renderer.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4 - 1);
			}
			
			if(!flag2 && !flag5)
			{
				renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXZPP;
				renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXZPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4 + 1);
				renderer.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4 + 1);
			}
			
			if(!flag3 && !flag4)
			{
				renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXZPN;
				renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXZPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4 - 1);
				renderer.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4 - 1);
			}
			
			if(!flag3 && !flag5)
			{
				renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXZPP;
				renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXZPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4 + 1);
				renderer.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4 + 1);
			}
			
			if(renderer.renderMaxX >= 1.0D)
			{
				--par2;
			}
			
			i1 = l;
			
			if(renderer.renderMaxX >= 1.0D || !renderer.blockAccess.isBlockOpaqueCube(par2 + 1, par3, par4))
			{
				i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			}
			
			f7 = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4);
			f9 = (renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXYZPNP + f7 + renderer.aoLightValueScratchXZPP) / 4.0F;
			f8 = (renderer.aoLightValueScratchXYZPNN + renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXZPN + f7) / 4.0F;
			f11 = (renderer.aoLightValueScratchXZPN + f7 + renderer.aoLightValueScratchXYZPPN + renderer.aoLightValueScratchXYPP) / 4.0F;
			f10 = (f7 + renderer.aoLightValueScratchXZPP + renderer.aoLightValueScratchXYPP + renderer.aoLightValueScratchXYZPPP) / 4.0F;
			f3 = (float) (f9 * (1.0D - renderer.renderMinY) * renderer.renderMaxZ + f8 * (1.0D - renderer.renderMinY)
					* (1.0D - renderer.renderMaxZ) + f11 * renderer.renderMinY * (1.0D - renderer.renderMaxZ) + f10 * renderer.renderMinY
					* renderer.renderMaxZ);
			f4 = (float) (f9 * (1.0D - renderer.renderMinY) * renderer.renderMinZ + f8 * (1.0D - renderer.renderMinY)
					* (1.0D - renderer.renderMinZ) + f11 * renderer.renderMinY * (1.0D - renderer.renderMinZ) + f10 * renderer.renderMinY
					* renderer.renderMinZ);
			f5 = (float) (f9 * (1.0D - renderer.renderMaxY) * renderer.renderMinZ + f8 * (1.0D - renderer.renderMaxY)
					* (1.0D - renderer.renderMinZ) + f11 * renderer.renderMaxY * (1.0D - renderer.renderMinZ) + f10 * renderer.renderMaxY
					* renderer.renderMinZ);
			f6 = (float) (f9 * (1.0D - renderer.renderMaxY) * renderer.renderMaxZ + f8 * (1.0D - renderer.renderMaxY)
					* (1.0D - renderer.renderMaxZ) + f11 * renderer.renderMaxY * (1.0D - renderer.renderMaxZ) + f10 * renderer.renderMaxY
					* renderer.renderMaxZ);
			k1 = renderer.getAoBrightness(renderer.aoBrightnessXYPN, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXZPP, i1);
			j1 = renderer.getAoBrightness(renderer.aoBrightnessXZPP, renderer.aoBrightnessXYPP, renderer.aoBrightnessXYZPPP, i1);
			i2 = renderer.getAoBrightness(renderer.aoBrightnessXZPN, renderer.aoBrightnessXYZPPN, renderer.aoBrightnessXYPP, i1);
			l1 = renderer.getAoBrightness(renderer.aoBrightnessXYZPNN, renderer.aoBrightnessXYPN, renderer.aoBrightnessXZPN, i1);
			renderer.brightnessTopLeft = renderer.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer.renderMinY) * renderer.renderMaxZ,
					(1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxZ), renderer.renderMinY * (1.0D - renderer.renderMaxZ), renderer.renderMinY
					* renderer.renderMaxZ);
			renderer.brightnessBottomLeft = renderer.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer.renderMinY) * renderer.renderMinZ,
					(1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinZ), renderer.renderMinY * (1.0D - renderer.renderMinZ), renderer.renderMinY
					* renderer.renderMinZ);
			renderer.brightnessBottomRight = renderer.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer.renderMaxY) * renderer.renderMinZ,
					(1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinZ), renderer.renderMaxY * (1.0D - renderer.renderMinZ), renderer.renderMaxY
					* renderer.renderMinZ);
			renderer.brightnessTopRight = renderer.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer.renderMaxY) * renderer.renderMaxZ,
					(1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxZ), renderer.renderMaxY * (1.0D - renderer.renderMaxZ), renderer.renderMaxY
					* renderer.renderMaxZ);
			
			if(flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.6F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.6F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.6F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.6F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.6F;
			}
			
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 5);
			this.renderSouthFace(renderer, par1Block, par2, par3, par4, icon);
			
			if(RenderBlocks.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				renderer.colorRedTopLeft *= par5;
				renderer.colorRedBottomLeft *= par5;
				renderer.colorRedBottomRight *= par5;
				renderer.colorRedTopRight *= par5;
				renderer.colorGreenTopLeft *= par6;
				renderer.colorGreenBottomLeft *= par6;
				renderer.colorGreenBottomRight *= par6;
				renderer.colorGreenTopRight *= par6;
				renderer.colorBlueTopLeft *= par7;
				renderer.colorBlueBottomLeft *= par7;
				renderer.colorBlueBottomRight *= par7;
				renderer.colorBlueTopRight *= par7;
				this.renderSouthFace(renderer, par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			
			flag = true;
		}
		
		renderer.enableAO = false;
		return flag;
	}
	
	public boolean renderStandardBlockWithAmbientOcclusion(RenderBlocks renderer, Block par1Block, int par2, int par3, int par4, float par5, float par6,
			float par7)
	{
		renderer.enableAO = true;
		boolean flag = false;
		float f3 = 0.0F;
		float f4 = 0.0F;
		float f5 = 0.0F;
		float f6 = 0.0F;
		boolean flag1 = true;
		int l = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(983055);
		
		if(renderer.getBlockIcon(par1Block).getIconName().equals("grass_top"))
		{
			flag1 = false;
		}
		else if(renderer.hasOverrideBlockTexture())
		{
			flag1 = false;
		}
		
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f7;
		int i1;
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3 - 1, par4, 0))
		{
			if(renderer.renderMinY <= 0.0D)
			{
				--par3;
			}
			
			renderer.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoLightValueScratchXYNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoLightValueScratchYZNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoLightValueScratchYZNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoLightValueScratchXYPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4);
			flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
			flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
			
			if(!flag4 && !flag2)
			{
				renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXYNN;
				renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXYNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4 - 1);
				renderer.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4 - 1);
			}
			
			if(!flag5 && !flag2)
			{
				renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXYNN;
				renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXYNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4 + 1);
				renderer.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4 + 1);
			}
			
			if(!flag4 && !flag3)
			{
				renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXYPN;
				renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXYPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4 - 1);
				renderer.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4 - 1);
			}
			
			if(!flag5 && !flag3)
			{
				renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXYPN;
				renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXYPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4 + 1);
				renderer.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4 + 1);
			}
			
			if(renderer.renderMinY <= 0.0D)
			{
				++par3;
			}
			
			i1 = l;
			
			if(renderer.renderMinY <= 0.0D || !renderer.blockAccess.isBlockOpaqueCube(par2, par3 - 1, par4))
			{
				i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			}
			
			f7 = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4);
			f3 = (renderer.aoLightValueScratchXYZNNP + renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchYZNP + f7) / 4.0F;
			f6 = (renderer.aoLightValueScratchYZNP + f7 + renderer.aoLightValueScratchXYZPNP + renderer.aoLightValueScratchXYPN) / 4.0F;
			f5 = (f7 + renderer.aoLightValueScratchYZNN + renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXYZPNN) / 4.0F;
			f4 = (renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXYZNNN + f7 + renderer.aoLightValueScratchYZNN) / 4.0F;
			renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXYNN, renderer.aoBrightnessYZNP, i1);
			renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessYZNP, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXYPN, i1);
			renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessYZNN, renderer.aoBrightnessXYPN, renderer.aoBrightnessXYZPNN, i1);
			renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessXYNN, renderer.aoBrightnessXYZNNN, renderer.aoBrightnessYZNN, i1);
			
			if(flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.5F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.5F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.5F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.5F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.5F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.5F;
			}
			
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			this.renderBottomFace(renderer, par1Block, par2, par3, par4,
					renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 0));
			flag = true;
		}
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3 + 1, par4, 1))
		{
			if(renderer.renderMaxY >= 1.0D)
			{
				++par3;
			}
			
			renderer.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoLightValueScratchXYNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoLightValueScratchXYPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoLightValueScratchYZPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoLightValueScratchYZPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 + 1);
			flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
			
			if(!flag4 && !flag2)
			{
				renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXYNP;
				renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXYNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4 - 1);
				renderer.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4 - 1);
			}
			
			if(!flag4 && !flag3)
			{
				renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXYPP;
				renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXYPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4 - 1);
				renderer.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4 - 1);
			}
			
			if(!flag5 && !flag2)
			{
				renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXYNP;
				renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXYNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4 + 1);
				renderer.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4 + 1);
			}
			
			if(!flag5 && !flag3)
			{
				renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXYPP;
				renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXYPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4 + 1);
				renderer.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4 + 1);
			}
			
			if(renderer.renderMaxY >= 1.0D)
			{
				--par3;
			}
			
			i1 = l;
			
			if(renderer.renderMaxY >= 1.0D || !renderer.blockAccess.isBlockOpaqueCube(par2, par3 + 1, par4))
			{
				i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			}
			
			f7 = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4);
			f6 = (renderer.aoLightValueScratchXYZNPP + renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchYZPP + f7) / 4.0F;
			f3 = (renderer.aoLightValueScratchYZPP + f7 + renderer.aoLightValueScratchXYZPPP + renderer.aoLightValueScratchXYPP) / 4.0F;
			f4 = (f7 + renderer.aoLightValueScratchYZPN + renderer.aoLightValueScratchXYPP + renderer.aoLightValueScratchXYZPPN) / 4.0F;
			f5 = (renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchXYZNPN + f7 + renderer.aoLightValueScratchYZPN) / 4.0F;
			renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessXYZNPP, renderer.aoBrightnessXYNP, renderer.aoBrightnessYZPP, i1);
			renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessYZPP, renderer.aoBrightnessXYZPPP, renderer.aoBrightnessXYPP, i1);
			renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessYZPN, renderer.aoBrightnessXYPP, renderer.aoBrightnessXYZPPN, i1);
			renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessXYNP, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessYZPN, i1);
			renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5;
			renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6;
			renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7;
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			this.renderTopFace(renderer, par1Block, par2, par3, par4,
					renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 1));
			flag = true;
		}
		
		Icon icon;
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3, par4 - 1, 2))
		{
			if(renderer.renderMinZ <= 0.0D)
			{
				--par4;
			}
			
			renderer.aoLightValueScratchXZNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoLightValueScratchYZNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoLightValueScratchYZPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4);
			renderer.aoLightValueScratchXZPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			renderer.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
			flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
			flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
			
			if(!flag2 && !flag4)
			{
				renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXZNN;
				renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXZNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3 - 1, par4);
				renderer.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3 - 1, par4);
			}
			
			if(!flag2 && !flag5)
			{
				renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXZNN;
				renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXZNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3 + 1, par4);
				renderer.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3 + 1, par4);
			}
			
			if(!flag3 && !flag4)
			{
				renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXZPN;
				renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXZPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3 - 1, par4);
				renderer.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3 - 1, par4);
			}
			
			if(!flag3 && !flag5)
			{
				renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXZPN;
				renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXZPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3 + 1, par4);
				renderer.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3 + 1, par4);
			}
			
			if(renderer.renderMinZ <= 0.0D)
			{
				++par4;
			}
			
			i1 = l;
			
			if(renderer.renderMinZ <= 0.0D || !renderer.blockAccess.isBlockOpaqueCube(par2, par3, par4 - 1))
			{
				i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			}
			
			f7 = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 - 1);
			f3 = (renderer.aoLightValueScratchXZNN + renderer.aoLightValueScratchXYZNPN + f7 + renderer.aoLightValueScratchYZPN) / 4.0F;
			f4 = (f7 + renderer.aoLightValueScratchYZPN + renderer.aoLightValueScratchXZPN + renderer.aoLightValueScratchXYZPPN) / 4.0F;
			f5 = (renderer.aoLightValueScratchYZNN + f7 + renderer.aoLightValueScratchXYZPNN + renderer.aoLightValueScratchXZPN) / 4.0F;
			f6 = (renderer.aoLightValueScratchXYZNNN + renderer.aoLightValueScratchXZNN + renderer.aoLightValueScratchYZNN + f7) / 4.0F;
			renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXZNN, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessYZPN, i1);
			renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessYZPN, renderer.aoBrightnessXZPN, renderer.aoBrightnessXYZPPN, i1);
			renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessYZNN, renderer.aoBrightnessXYZPNN, renderer.aoBrightnessXZPN, i1);
			renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessXYZNNN, renderer.aoBrightnessXZNN, renderer.aoBrightnessYZNN, i1);
			
			if(flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.8F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.8F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.8F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.8F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.8F;
			}
			
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 2);
			this.renderEastFace(renderer, par1Block, par2, par3, par4, icon);
			
			if(RenderBlocks.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				renderer.colorRedTopLeft *= par5;
				renderer.colorRedBottomLeft *= par5;
				renderer.colorRedBottomRight *= par5;
				renderer.colorRedTopRight *= par5;
				renderer.colorGreenTopLeft *= par6;
				renderer.colorGreenBottomLeft *= par6;
				renderer.colorGreenBottomRight *= par6;
				renderer.colorGreenTopRight *= par6;
				renderer.colorBlueTopLeft *= par7;
				renderer.colorBlueBottomLeft *= par7;
				renderer.colorBlueBottomRight *= par7;
				renderer.colorBlueTopRight *= par7;
				this.renderEastFace(renderer, par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			
			flag = true;
		}
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3, par4 + 1, 3))
		{
			if(renderer.renderMaxZ >= 1.0D)
			{
				++par4;
			}
			
			renderer.aoLightValueScratchXZNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoLightValueScratchXZPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoLightValueScratchYZNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoLightValueScratchYZPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4);
			renderer.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
			flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
			
			if(!flag2 && !flag4)
			{
				renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXZNP;
				renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXZNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3 - 1, par4);
				renderer.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3 - 1, par4);
			}
			
			if(!flag2 && !flag5)
			{
				renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXZNP;
				renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXZNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3 + 1, par4);
				renderer.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3 + 1, par4);
			}
			
			if(!flag3 && !flag4)
			{
				renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXZPP;
				renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXZPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3 - 1, par4);
				renderer.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3 - 1, par4);
			}
			
			if(!flag3 && !flag5)
			{
				renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXZPP;
				renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXZPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3 + 1, par4);
				renderer.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3 + 1, par4);
			}
			
			if(renderer.renderMaxZ >= 1.0D)
			{
				--par4;
			}
			
			i1 = l;
			
			if(renderer.renderMaxZ >= 1.0D || !renderer.blockAccess.isBlockOpaqueCube(par2, par3, par4 + 1))
			{
				i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			}
			
			f7 = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 + 1);
			f3 = (renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchXYZNPP + f7 + renderer.aoLightValueScratchYZPP) / 4.0F;
			f6 = (f7 + renderer.aoLightValueScratchYZPP + renderer.aoLightValueScratchXZPP + renderer.aoLightValueScratchXYZPPP) / 4.0F;
			f5 = (renderer.aoLightValueScratchYZNP + f7 + renderer.aoLightValueScratchXYZPNP + renderer.aoLightValueScratchXZPP) / 4.0F;
			f4 = (renderer.aoLightValueScratchXYZNNP + renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchYZNP + f7) / 4.0F;
			renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXZNP, renderer.aoBrightnessXYZNPP, renderer.aoBrightnessYZPP, i1);
			renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessYZPP, renderer.aoBrightnessXZPP, renderer.aoBrightnessXYZPPP, i1);
			renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessYZNP, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXZPP, i1);
			renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXZNP, renderer.aoBrightnessYZNP, i1);
			
			if(flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.8F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.8F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.8F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.8F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.8F;
			}
			
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 3);
			this.renderWestFace(renderer, par1Block, par2, par3, par4,
					renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 3));
			
			if(RenderBlocks.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				renderer.colorRedTopLeft *= par5;
				renderer.colorRedBottomLeft *= par5;
				renderer.colorRedBottomRight *= par5;
				renderer.colorRedTopRight *= par5;
				renderer.colorGreenTopLeft *= par6;
				renderer.colorGreenBottomLeft *= par6;
				renderer.colorGreenBottomRight *= par6;
				renderer.colorGreenTopRight *= par6;
				renderer.colorBlueTopLeft *= par7;
				renderer.colorBlueBottomLeft *= par7;
				renderer.colorBlueBottomRight *= par7;
				renderer.colorBlueTopRight *= par7;
				this.renderWestFace(renderer, par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			
			flag = true;
		}
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2 - 1, par3, par4, 4))
		{
			if(renderer.renderMinX <= 0.0D)
			{
				--par2;
			}
			
			renderer.aoLightValueScratchXYNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoLightValueScratchXZNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoLightValueScratchXZNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoLightValueScratchXYNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4);
			renderer.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
			
			if(!flag5 && !flag2)
			{
				renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXZNN;
				renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXZNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4 - 1);
				renderer.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4 - 1);
			}
			
			if(!flag4 && !flag2)
			{
				renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXZNP;
				renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXZNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4 + 1);
				renderer.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4 + 1);
			}
			
			if(!flag5 && !flag3)
			{
				renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXZNN;
				renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXZNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4 - 1);
				renderer.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4 - 1);
			}
			
			if(!flag4 && !flag3)
			{
				renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXZNP;
				renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXZNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4 + 1);
				renderer.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4 + 1);
			}
			
			if(renderer.renderMinX <= 0.0D)
			{
				++par2;
			}
			
			i1 = l;
			
			if(renderer.renderMinX <= 0.0D || !renderer.blockAccess.isBlockOpaqueCube(par2 - 1, par3, par4))
			{
				i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			}
			
			f7 = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 - 1, par3, par4);
			f6 = (renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXYZNNP + f7 + renderer.aoLightValueScratchXZNP) / 4.0F;
			f3 = (f7 + renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchXYZNPP) / 4.0F;
			f4 = (renderer.aoLightValueScratchXZNN + f7 + renderer.aoLightValueScratchXYZNPN + renderer.aoLightValueScratchXYNP) / 4.0F;
			f5 = (renderer.aoLightValueScratchXYZNNN + renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXZNN + f7) / 4.0F;
			renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessXYNN, renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXZNP, i1);
			renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXZNP, renderer.aoBrightnessXYNP, renderer.aoBrightnessXYZNPP, i1);
			renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessXZNN, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessXYNP, i1);
			renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessXYZNNN, renderer.aoBrightnessXYNN, renderer.aoBrightnessXZNN, i1);
			
			if(flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.6F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.6F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.6F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.6F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.6F;
			}
			
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 4);
			this.renderNorthFace(renderer, par1Block, par2, par3, par4, icon);
			
			if(RenderBlocks.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				renderer.colorRedTopLeft *= par5;
				renderer.colorRedBottomLeft *= par5;
				renderer.colorRedBottomRight *= par5;
				renderer.colorRedTopRight *= par5;
				renderer.colorGreenTopLeft *= par6;
				renderer.colorGreenBottomLeft *= par6;
				renderer.colorGreenBottomRight *= par6;
				renderer.colorGreenTopRight *= par6;
				renderer.colorBlueTopLeft *= par7;
				renderer.colorBlueBottomLeft *= par7;
				renderer.colorBlueBottomRight *= par7;
				renderer.colorBlueTopRight *= par7;
				this.renderNorthFace(renderer, par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			
			flag = true;
		}
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2 + 1, par3, par4, 5))
		{
			if(renderer.renderMaxX >= 1.0D)
			{
				++par2;
			}
			
			renderer.aoLightValueScratchXYPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoLightValueScratchXZPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoLightValueScratchXZPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoLightValueScratchXYPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4);
			renderer.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			flag3 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			flag2 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			flag5 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			flag4 = Block.canBlockGrass[renderer.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
			
			if(!flag2 && !flag4)
			{
				renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXZPN;
				renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXZPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4 - 1);
				renderer.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4 - 1);
			}
			
			if(!flag2 && !flag5)
			{
				renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXZPP;
				renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXZPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 - 1, par4 + 1);
				renderer.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4 + 1);
			}
			
			if(!flag3 && !flag4)
			{
				renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXZPN;
				renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXZPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4 - 1);
				renderer.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4 - 1);
			}
			
			if(!flag3 && !flag5)
			{
				renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXZPP;
				renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXZPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2, par3 + 1, par4 + 1);
				renderer.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4 + 1);
			}
			
			if(renderer.renderMaxX >= 1.0D)
			{
				--par2;
			}
			
			i1 = l;
			
			if(renderer.renderMaxX >= 1.0D || !renderer.blockAccess.isBlockOpaqueCube(par2 + 1, par3, par4))
			{
				i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			}
			
			f7 = par1Block.getAmbientOcclusionLightValue(renderer.blockAccess, par2 + 1, par3, par4);
			f3 = (renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXYZPNP + f7 + renderer.aoLightValueScratchXZPP) / 4.0F;
			f4 = (renderer.aoLightValueScratchXYZPNN + renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXZPN + f7) / 4.0F;
			f5 = (renderer.aoLightValueScratchXZPN + f7 + renderer.aoLightValueScratchXYZPPN + renderer.aoLightValueScratchXYPP) / 4.0F;
			f6 = (f7 + renderer.aoLightValueScratchXZPP + renderer.aoLightValueScratchXYPP + renderer.aoLightValueScratchXYZPPP) / 4.0F;
			renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXYPN, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXZPP, i1);
			renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessXZPP, renderer.aoBrightnessXYPP, renderer.aoBrightnessXYZPPP, i1);
			renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessXZPN, renderer.aoBrightnessXYZPPN, renderer.aoBrightnessXYPP, i1);
			renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessXYZPNN, renderer.aoBrightnessXYPN, renderer.aoBrightnessXZPN, i1);
			
			if(flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.6F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.6F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.6F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.6F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.6F;
			}
			
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 5);
			this.renderSouthFace(renderer, par1Block, par2, par3, par4, icon);
			
			if(RenderBlocks.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				renderer.colorRedTopLeft *= par5;
				renderer.colorRedBottomLeft *= par5;
				renderer.colorRedBottomRight *= par5;
				renderer.colorRedTopRight *= par5;
				renderer.colorGreenTopLeft *= par6;
				renderer.colorGreenBottomLeft *= par6;
				renderer.colorGreenBottomRight *= par6;
				renderer.colorGreenTopRight *= par6;
				renderer.colorBlueTopLeft *= par7;
				renderer.colorBlueBottomLeft *= par7;
				renderer.colorBlueBottomRight *= par7;
				renderer.colorBlueTopRight *= par7;
				this.renderSouthFace(renderer, par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			
			flag = true;
		}
		
		renderer.enableAO = false;
		return flag;
	}
	
	/**
	 * Renders a standard cube block at the given coordinates, with a given
	 * color ratio. Args: block, x, y, z, r, g, b
	 */
	public boolean renderStandardBlockWithColorMultiplier(RenderBlocks renderer, Block par1Block, int par2, int par3, int par4, float par5, float par6,
			float par7)
	{
		renderer.enableAO = false;
		Tessellator tessellator = Tessellator.instance;
		boolean flag = false;
		float f3 = 0.5F;
		float f4 = 1.0F;
		float f5 = 0.8F;
		float f6 = 0.6F;
		float f7 = f4 * par5;
		float f8 = f4 * par6;
		float f9 = f4 * par7;
		float f10 = f3;
		float f11 = f5;
		float f12 = f6;
		float f13 = f3;
		float f14 = f5;
		float f15 = f6;
		float f16 = f3;
		float f17 = f5;
		float f18 = f6;
		
		if(par1Block != Block.grass)
		{
			f10 = f3 * par5;
			f11 = f5 * par5;
			f12 = f6 * par5;
			f13 = f3 * par6;
			f14 = f5 * par6;
			f15 = f6 * par6;
			f16 = f3 * par7;
			f17 = f5 * par7;
			f18 = f6 * par7;
		}
		
		int l = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4);
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3 - 1, par4, 0))
		{
			tessellator.setBrightness(renderer.renderMinY > 0.0D ? l : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4));
			tessellator.setColorOpaque_F(f10, f13, f16);
			this.renderBottomFace(renderer, par1Block, par2, par3, par4,
					renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 0));
			flag = true;
		}
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3 + 1, par4, 1))
		{
			tessellator.setBrightness(renderer.renderMaxY < 1.0D ? l : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4));
			tessellator.setColorOpaque_F(f7, f8, f9);
			this.renderTopFace(renderer, par1Block, par2, par3, par4,
					renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 1));
			flag = true;
		}
		
		Icon icon;
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3, par4 - 1, 2))
		{
			tessellator.setBrightness(renderer.renderMinZ > 0.0D ? l : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1));
			tessellator.setColorOpaque_F(f11, f14, f17);
			icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 2);
			this.renderEastFace(renderer, par1Block, par2, par3, par4, icon);
			
			if(RenderBlocks.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				tessellator.setColorOpaque_F(f11 * par5, f14 * par6, f17 * par7);
				this.renderEastFace(renderer, par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			
			flag = true;
		}
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3, par4 + 1, 3))
		{
			tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? l : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1));
			tessellator.setColorOpaque_F(f11, f14, f17);
			icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 3);
			this.renderWestFace(renderer, par1Block, par2, par3, par4, icon);
			
			if(RenderBlocks.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				tessellator.setColorOpaque_F(f11 * par5, f14 * par6, f17 * par7);
				this.renderWestFace(renderer, par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			
			flag = true;
		}
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2 - 1, par3, par4, 4))
		{
			tessellator.setBrightness(renderer.renderMinX > 0.0D ? l : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4));
			tessellator.setColorOpaque_F(f12, f15, f18);
			icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 4);
			this.renderNorthFace(renderer, par1Block, par2, par3, par4, icon);
			
			if(RenderBlocks.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				tessellator.setColorOpaque_F(f12 * par5, f15 * par6, f18 * par7);
				this.renderNorthFace(renderer, par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			
			flag = true;
		}
		
		if(renderer.renderAllFaces || par1Block.shouldSideBeRendered(renderer.blockAccess, par2 + 1, par3, par4, 5))
		{
			tessellator.setBrightness(renderer.renderMaxX < 1.0D ? l : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4));
			tessellator.setColorOpaque_F(f12, f15, f18);
			icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 5);
			this.renderSouthFace(renderer, par1Block, par2, par3, par4, icon);
			
			if(RenderBlocks.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				tessellator.setColorOpaque_F(f12 * par5, f15 * par6, f18 * par7);
				this.renderSouthFace(renderer, par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * Renders the given texture to the bottom face of the block. Args: block,
	 * x, y, z, texture
	 */
	public void renderBottomFace(RenderBlocks renderer, Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator tessellator = Tessellator.instance;
		
		if(renderer.hasOverrideBlockTexture())
		{
			par8Icon = renderer.overrideBlockTexture;
		}
		
		double d3 = par8Icon.getInterpolatedU(renderer.renderMinX * 16.0D);
		double d4 = par8Icon.getInterpolatedU(renderer.renderMaxX * 16.0D);
		double d5 = par8Icon.getInterpolatedV(renderer.renderMinZ * 16.0D);
		double d6 = par8Icon.getInterpolatedV(renderer.renderMaxZ * 16.0D);
		
		if(renderer.renderMinX < 0.0D || renderer.renderMaxX > 1.0D)
		{
			d3 = par8Icon.getMinU();
			d4 = par8Icon.getMaxU();
		}
		
		if(renderer.renderMinZ < 0.0D || renderer.renderMaxZ > 1.0D)
		{
			d5 = par8Icon.getMinV();
			d6 = par8Icon.getMaxV();
		}
		
		double d7 = d4;
		double d8 = d3;
		double d9 = d5;
		double d10 = d6;
		
		if(renderer.uvRotateBottom == 2)
		{
			d3 = par8Icon.getInterpolatedU(renderer.renderMinZ * 16.0D);
			d5 = par8Icon.getInterpolatedV(16.0D - renderer.renderMaxX * 16.0D);
			d4 = par8Icon.getInterpolatedU(renderer.renderMaxZ * 16.0D);
			d6 = par8Icon.getInterpolatedV(16.0D - renderer.renderMinX * 16.0D);
			d9 = d5;
			d10 = d6;
			d7 = d3;
			d8 = d4;
			d5 = d6;
			d6 = d9;
		}
		else if(renderer.uvRotateBottom == 1)
		{
			d3 = par8Icon.getInterpolatedU(16.0D - renderer.renderMaxZ * 16.0D);
			d5 = par8Icon.getInterpolatedV(renderer.renderMinX * 16.0D);
			d4 = par8Icon.getInterpolatedU(16.0D - renderer.renderMinZ * 16.0D);
			d6 = par8Icon.getInterpolatedV(renderer.renderMaxX * 16.0D);
			d7 = d4;
			d8 = d3;
			d3 = d4;
			d4 = d8;
			d9 = d6;
			d10 = d5;
		}
		else if(renderer.uvRotateBottom == 3)
		{
			d3 = par8Icon.getInterpolatedU(16.0D - renderer.renderMinX * 16.0D);
			d4 = par8Icon.getInterpolatedU(16.0D - renderer.renderMaxX * 16.0D);
			d5 = par8Icon.getInterpolatedV(16.0D - renderer.renderMinZ * 16.0D);
			d6 = par8Icon.getInterpolatedV(16.0D - renderer.renderMaxZ * 16.0D);
			d7 = d4;
			d8 = d3;
			d9 = d5;
			d10 = d6;
		}
		
		double d11 = par2 + renderer.renderMinX;
		double d12 = par2 + renderer.renderMaxX;
		double d13 = par4 + renderer.renderMinY;
		double d14 = par6 + renderer.renderMinZ;
		double d15 = par6 + renderer.renderMaxZ;
		
		if(renderer.enableAO)
		{
			tessellator.setColorOpaque_F(renderer.colorRedTopRight, renderer.colorGreenTopRight, renderer.colorBlueTopRight);
			tessellator.setBrightness(renderer.brightnessTopRight);
			tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
			tessellator.setColorOpaque_F(renderer.colorRedBottomRight, renderer.colorGreenBottomRight, renderer.colorBlueBottomRight);
			tessellator.setBrightness(renderer.brightnessBottomRight);
			tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
			tessellator.setColorOpaque_F(renderer.colorRedBottomLeft, renderer.colorGreenBottomLeft, renderer.colorBlueBottomLeft);
			tessellator.setBrightness(renderer.brightnessBottomLeft);
			tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
			tessellator.setColorOpaque_F(renderer.colorRedTopLeft, renderer.colorGreenTopLeft, renderer.colorBlueTopLeft);
			tessellator.setBrightness(renderer.brightnessTopLeft);
			tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
		}
		else
		{
			tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
			tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
			tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
			tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
		}
	}
	
	/**
	 * Renders the given texture to the top face of the block. Args: block, x,
	 * y, z, texture
	 */
	public void renderTopFace(RenderBlocks renderer, Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator tessellator = Tessellator.instance;
		
		if(renderer.hasOverrideBlockTexture())
		{
			par8Icon = renderer.overrideBlockTexture;
		}
		
		double d3 = par8Icon.getInterpolatedU(renderer.renderMinX * 16.0D);
		double d4 = par8Icon.getInterpolatedU(renderer.renderMaxX * 16.0D);
		double d5 = par8Icon.getInterpolatedV(renderer.renderMinZ * 16.0D);
		double d6 = par8Icon.getInterpolatedV(renderer.renderMaxZ * 16.0D);
		
		if(renderer.renderMinX < 0.0D || renderer.renderMaxX > 1.0D)
		{
			d3 = par8Icon.getMinU();
			d4 = par8Icon.getMaxU();
		}
		
		if(renderer.renderMinZ < 0.0D || renderer.renderMaxZ > 1.0D)
		{
			d5 = par8Icon.getMinV();
			d6 = par8Icon.getMaxV();
		}
		
		double d7 = d4;
		double d8 = d3;
		double d9 = d5;
		double d10 = d6;
		
		if(renderer.uvRotateTop == 1)
		{
			d3 = par8Icon.getInterpolatedU(renderer.renderMinZ * 16.0D);
			d5 = par8Icon.getInterpolatedV(16.0D - renderer.renderMaxX * 16.0D);
			d4 = par8Icon.getInterpolatedU(renderer.renderMaxZ * 16.0D);
			d6 = par8Icon.getInterpolatedV(16.0D - renderer.renderMinX * 16.0D);
			d9 = d5;
			d10 = d6;
			d7 = d3;
			d8 = d4;
			d5 = d6;
			d6 = d9;
		}
		else if(renderer.uvRotateTop == 2)
		{
			d3 = par8Icon.getInterpolatedU(16.0D - renderer.renderMaxZ * 16.0D);
			d5 = par8Icon.getInterpolatedV(renderer.renderMinX * 16.0D);
			d4 = par8Icon.getInterpolatedU(16.0D - renderer.renderMinZ * 16.0D);
			d6 = par8Icon.getInterpolatedV(renderer.renderMaxX * 16.0D);
			d7 = d4;
			d8 = d3;
			d3 = d4;
			d4 = d8;
			d9 = d6;
			d10 = d5;
		}
		else if(renderer.uvRotateTop == 3)
		{
			d3 = par8Icon.getInterpolatedU(16.0D - renderer.renderMinX * 16.0D);
			d4 = par8Icon.getInterpolatedU(16.0D - renderer.renderMaxX * 16.0D);
			d5 = par8Icon.getInterpolatedV(16.0D - renderer.renderMinZ * 16.0D);
			d6 = par8Icon.getInterpolatedV(16.0D - renderer.renderMaxZ * 16.0D);
			d7 = d4;
			d8 = d3;
			d9 = d5;
			d10 = d6;
		}
		
		double d11 = par2 + renderer.renderMinX;
		double d12 = par2 + renderer.renderMaxX;
		double d13 = par4 + renderer.renderMaxY;
		double d14 = par6 + renderer.renderMinZ;
		double d15 = par6 + renderer.renderMaxZ;
		
		if(renderer.enableAO)
		{
			tessellator.setColorOpaque_F(renderer.colorRedTopRight, renderer.colorGreenTopRight, renderer.colorBlueTopRight);
			tessellator.setBrightness(renderer.brightnessTopRight);
			tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
			tessellator.setColorOpaque_F(renderer.colorRedBottomRight, renderer.colorGreenBottomRight, renderer.colorBlueBottomRight);
			tessellator.setBrightness(renderer.brightnessBottomRight);
			tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
			tessellator.setColorOpaque_F(renderer.colorRedBottomLeft, renderer.colorGreenBottomLeft, renderer.colorBlueBottomLeft);
			tessellator.setBrightness(renderer.brightnessBottomLeft);
			tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
			tessellator.setColorOpaque_F(renderer.colorRedTopLeft, renderer.colorGreenTopLeft, renderer.colorBlueTopLeft);
			tessellator.setBrightness(renderer.brightnessTopLeft);
			tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
		}
		else
		{
			tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
			tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
			tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
			tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
		}
	}
	
	/**
	 * Renders the given texture to the east (z-negative) face of the block.
	 * Args: block, x, y, z, texture
	 */
	public void renderEastFace(RenderBlocks renderer, Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator tessellator = Tessellator.instance;
		
		if(renderer.hasOverrideBlockTexture())
		{
			par8Icon = renderer.overrideBlockTexture;
		}
		
		double d3 = par8Icon.getInterpolatedU(renderer.renderMinX * 16.0D);
		double d4 = par8Icon.getInterpolatedU(renderer.renderMaxX * 16.0D);
		double d5 = par8Icon.getInterpolatedV(16.0D - renderer.renderMaxY * 16.0D);
		double d6 = par8Icon.getInterpolatedV(16.0D - renderer.renderMinY * 16.0D);
		double d7;
		
		if(renderer.flipTexture)
		{
			d7 = d3;
			d3 = d4;
			d4 = d7;
		}
		
		if(renderer.renderMinX < 0.0D || renderer.renderMaxX > 1.0D)
		{
			d3 = par8Icon.getMinU();
			d4 = par8Icon.getMaxU();
		}
		
		if(renderer.renderMinY < 0.0D || renderer.renderMaxY > 1.0D)
		{
			d5 = par8Icon.getMinV();
			d6 = par8Icon.getMaxV();
		}
		
		d7 = d4;
		double d8 = d3;
		double d9 = d5;
		double d10 = d6;
		
		if(renderer.uvRotateEast == 2)
		{
			d3 = par8Icon.getInterpolatedU(renderer.renderMinY * 16.0D);
			d5 = par8Icon.getInterpolatedV(16.0D - renderer.renderMinX * 16.0D);
			d4 = par8Icon.getInterpolatedU(renderer.renderMaxY * 16.0D);
			d6 = par8Icon.getInterpolatedV(16.0D - renderer.renderMaxX * 16.0D);
			d9 = d5;
			d10 = d6;
			d7 = d3;
			d8 = d4;
			d5 = d6;
			d6 = d9;
		}
		else if(renderer.uvRotateEast == 1)
		{
			d3 = par8Icon.getInterpolatedU(16.0D - renderer.renderMaxY * 16.0D);
			d5 = par8Icon.getInterpolatedV(renderer.renderMaxX * 16.0D);
			d4 = par8Icon.getInterpolatedU(16.0D - renderer.renderMinY * 16.0D);
			d6 = par8Icon.getInterpolatedV(renderer.renderMinX * 16.0D);
			d7 = d4;
			d8 = d3;
			d3 = d4;
			d4 = d8;
			d9 = d6;
			d10 = d5;
		}
		else if(renderer.uvRotateEast == 3)
		{
			d3 = par8Icon.getInterpolatedU(16.0D - renderer.renderMinX * 16.0D);
			d4 = par8Icon.getInterpolatedU(16.0D - renderer.renderMaxX * 16.0D);
			d5 = par8Icon.getInterpolatedV(renderer.renderMaxY * 16.0D);
			d6 = par8Icon.getInterpolatedV(renderer.renderMinY * 16.0D);
			d7 = d4;
			d8 = d3;
			d9 = d5;
			d10 = d6;
		}
		
		double d11 = par2 + renderer.renderMinX;
		double d12 = par2 + renderer.renderMaxX;
		double d13 = par4 + renderer.renderMinY;
		double d14 = par4 + renderer.renderMaxY;
		double d15 = par6 + renderer.renderMinZ;
		
		if(renderer.enableAO)
		{
			tessellator.setColorOpaque_F(renderer.colorRedTopRight, renderer.colorGreenTopRight, renderer.colorBlueTopRight);
			tessellator.setBrightness(renderer.brightnessTopRight);
			tessellator.addVertexWithUV(d11, d13, d15, d4, d6);
			tessellator.setColorOpaque_F(renderer.colorRedBottomRight, renderer.colorGreenBottomRight, renderer.colorBlueBottomRight);
			tessellator.setBrightness(renderer.brightnessBottomRight);
			tessellator.addVertexWithUV(d12, d13, d15, d8, d10);
			tessellator.setColorOpaque_F(renderer.colorRedBottomLeft, renderer.colorGreenBottomLeft, renderer.colorBlueBottomLeft);
			tessellator.setBrightness(renderer.brightnessBottomLeft);
			tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
			tessellator.setColorOpaque_F(renderer.colorRedTopLeft, renderer.colorGreenTopLeft, renderer.colorBlueTopLeft);
			tessellator.setBrightness(renderer.brightnessTopLeft);
			tessellator.addVertexWithUV(d11, d14, d15, d7, d9);
		}
		else
		{
			tessellator.addVertexWithUV(d11, d13, d15, d4, d6);
			tessellator.addVertexWithUV(d12, d13, d15, d8, d10);
			tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
			tessellator.addVertexWithUV(d11, d14, d15, d7, d9);
		}
	}
	
	/**
	 * Renders the given texture to the west (z-positive) face of the block.
	 * Args: block, x, y, z, texture
	 */
	public void renderWestFace(RenderBlocks renderer, Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator tessellator = Tessellator.instance;
		
		if(renderer.hasOverrideBlockTexture())
		{
			par8Icon = renderer.overrideBlockTexture;
		}
		
		double d3 = par8Icon.getInterpolatedU(renderer.renderMinX * 16.0D);
		double d4 = par8Icon.getInterpolatedU(renderer.renderMaxX * 16.0D);
		double d5 = par8Icon.getInterpolatedV(16.0D - renderer.renderMaxY * 16.0D);
		double d6 = par8Icon.getInterpolatedV(16.0D - renderer.renderMinY * 16.0D);
		double d7;
		
		if(renderer.flipTexture)
		{
			d7 = d3;
			d3 = d4;
			d4 = d7;
		}
		
		if(renderer.renderMinX < 0.0D || renderer.renderMaxX > 1.0D)
		{
			d3 = par8Icon.getMinU();
			d4 = par8Icon.getMaxU();
		}
		
		if(renderer.renderMinY < 0.0D || renderer.renderMaxY > 1.0D)
		{
			d5 = par8Icon.getMinV();
			d6 = par8Icon.getMaxV();
		}
		
		d7 = d4;
		double d8 = d3;
		double d9 = d5;
		double d10 = d6;
		
		if(renderer.uvRotateWest == 1)
		{
			d3 = par8Icon.getInterpolatedU(renderer.renderMinY * 16.0D);
			d6 = par8Icon.getInterpolatedV(16.0D - renderer.renderMinX * 16.0D);
			d4 = par8Icon.getInterpolatedU(renderer.renderMaxY * 16.0D);
			d5 = par8Icon.getInterpolatedV(16.0D - renderer.renderMaxX * 16.0D);
			d9 = d5;
			d10 = d6;
			d7 = d3;
			d8 = d4;
			d5 = d6;
			d6 = d9;
		}
		else if(renderer.uvRotateWest == 2)
		{
			d3 = par8Icon.getInterpolatedU(16.0D - renderer.renderMaxY * 16.0D);
			d5 = par8Icon.getInterpolatedV(renderer.renderMinX * 16.0D);
			d4 = par8Icon.getInterpolatedU(16.0D - renderer.renderMinY * 16.0D);
			d6 = par8Icon.getInterpolatedV(renderer.renderMaxX * 16.0D);
			d7 = d4;
			d8 = d3;
			d3 = d4;
			d4 = d8;
			d9 = d6;
			d10 = d5;
		}
		else if(renderer.uvRotateWest == 3)
		{
			d3 = par8Icon.getInterpolatedU(16.0D - renderer.renderMinX * 16.0D);
			d4 = par8Icon.getInterpolatedU(16.0D - renderer.renderMaxX * 16.0D);
			d5 = par8Icon.getInterpolatedV(renderer.renderMaxY * 16.0D);
			d6 = par8Icon.getInterpolatedV(renderer.renderMinY * 16.0D);
			d7 = d4;
			d8 = d3;
			d9 = d5;
			d10 = d6;
		}
		
		double d11 = par2 + renderer.renderMinX;
		double d12 = par2 + renderer.renderMaxX;
		double d13 = par4 + renderer.renderMinY;
		double d14 = par4 + renderer.renderMaxY;
		double d15 = par6 + renderer.renderMaxZ;
		
		if(renderer.enableAO)
		{
			tessellator.setColorOpaque_F(renderer.colorRedTopRight, renderer.colorGreenTopRight, renderer.colorBlueTopRight);
			tessellator.setBrightness(renderer.brightnessTopRight);
			tessellator.addVertexWithUV(d12, d14, d15, d7, d9);
			tessellator.setColorOpaque_F(renderer.colorRedBottomRight, renderer.colorGreenBottomRight, renderer.colorBlueBottomRight);
			tessellator.setBrightness(renderer.brightnessBottomRight);
			tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
			tessellator.setColorOpaque_F(renderer.colorRedBottomLeft, renderer.colorGreenBottomLeft, renderer.colorBlueBottomLeft);
			tessellator.setBrightness(renderer.brightnessBottomLeft);
			tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
			tessellator.setColorOpaque_F(renderer.colorRedTopLeft, renderer.colorGreenTopLeft, renderer.colorBlueTopLeft);
			tessellator.setBrightness(renderer.brightnessTopLeft);
			tessellator.addVertexWithUV(d11, d14, d15, d3, d5);
		}
		else
		{
			tessellator.addVertexWithUV(d12, d14, d15, d7, d9);
			tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
			tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
			tessellator.addVertexWithUV(d11, d14, d15, d3, d5);
		}
	}
	
	/**
	 * Renders the given texture to the north (x-negative) face of the block.
	 * Args: block, x, y, z, texture
	 */
	public void renderNorthFace(RenderBlocks renderer, Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator tessellator = Tessellator.instance;
		
		if(renderer.hasOverrideBlockTexture())
		{
			par8Icon = renderer.overrideBlockTexture;
		}
		
		double d3 = par8Icon.getInterpolatedU(renderer.renderMinZ * 16.0D);
		double d4 = par8Icon.getInterpolatedU(renderer.renderMaxZ * 16.0D);
		double d5 = par8Icon.getInterpolatedV(16.0D - renderer.renderMaxY * 16.0D);
		double d6 = par8Icon.getInterpolatedV(16.0D - renderer.renderMinY * 16.0D);
		double d7;
		
		if(renderer.flipTexture)
		{
			d7 = d3;
			d3 = d4;
			d4 = d7;
		}
		
		if(renderer.renderMinZ < 0.0D || renderer.renderMaxZ > 1.0D)
		{
			d3 = par8Icon.getMinU();
			d4 = par8Icon.getMaxU();
		}
		
		if(renderer.renderMinY < 0.0D || renderer.renderMaxY > 1.0D)
		{
			d5 = par8Icon.getMinV();
			d6 = par8Icon.getMaxV();
		}
		
		d7 = d4;
		double d8 = d3;
		double d9 = d5;
		double d10 = d6;
		
		if(renderer.uvRotateNorth == 1)
		{
			d3 = par8Icon.getInterpolatedU(renderer.renderMinY * 16.0D);
			d5 = par8Icon.getInterpolatedV(16.0D - renderer.renderMaxZ * 16.0D);
			d4 = par8Icon.getInterpolatedU(renderer.renderMaxY * 16.0D);
			d6 = par8Icon.getInterpolatedV(16.0D - renderer.renderMinZ * 16.0D);
			d9 = d5;
			d10 = d6;
			d7 = d3;
			d8 = d4;
			d5 = d6;
			d6 = d9;
		}
		else if(renderer.uvRotateNorth == 2)
		{
			d3 = par8Icon.getInterpolatedU(16.0D - renderer.renderMaxY * 16.0D);
			d5 = par8Icon.getInterpolatedV(renderer.renderMinZ * 16.0D);
			d4 = par8Icon.getInterpolatedU(16.0D - renderer.renderMinY * 16.0D);
			d6 = par8Icon.getInterpolatedV(renderer.renderMaxZ * 16.0D);
			d7 = d4;
			d8 = d3;
			d3 = d4;
			d4 = d8;
			d9 = d6;
			d10 = d5;
		}
		else if(renderer.uvRotateNorth == 3)
		{
			d3 = par8Icon.getInterpolatedU(16.0D - renderer.renderMinZ * 16.0D);
			d4 = par8Icon.getInterpolatedU(16.0D - renderer.renderMaxZ * 16.0D);
			d5 = par8Icon.getInterpolatedV(renderer.renderMaxY * 16.0D);
			d6 = par8Icon.getInterpolatedV(renderer.renderMinY * 16.0D);
			d7 = d4;
			d8 = d3;
			d9 = d5;
			d10 = d6;
		}
		
		double d11 = par2 + renderer.renderMinX;
		double d12 = par4 + renderer.renderMinY;
		double d13 = par4 + renderer.renderMaxY;
		double d14 = par6 + renderer.renderMinZ;
		double d15 = par6 + renderer.renderMaxZ;
		
		if(renderer.enableAO)
		{
			tessellator.setColorOpaque_F(renderer.colorRedTopRight, renderer.colorGreenTopRight, renderer.colorBlueTopRight);
			tessellator.setBrightness(renderer.brightnessTopRight);
			tessellator.addVertexWithUV(d11, d12, d15, d4, d6);
			tessellator.setColorOpaque_F(renderer.colorRedBottomRight, renderer.colorGreenBottomRight, renderer.colorBlueBottomRight);
			tessellator.setBrightness(renderer.brightnessBottomRight);
			tessellator.addVertexWithUV(d11, d12, d14, d8, d10);
			tessellator.setColorOpaque_F(renderer.colorRedBottomLeft, renderer.colorGreenBottomLeft, renderer.colorBlueBottomLeft);
			tessellator.setBrightness(renderer.brightnessBottomLeft);
			tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
			tessellator.setColorOpaque_F(renderer.colorRedTopLeft, renderer.colorGreenTopLeft, renderer.colorBlueTopLeft);
			tessellator.setBrightness(renderer.brightnessTopLeft);
			tessellator.addVertexWithUV(d11, d13, d15, d7, d9);
		}
		else
		{
			tessellator.addVertexWithUV(d11, d12, d15, d4, d6);
			tessellator.addVertexWithUV(d11, d12, d14, d8, d10);
			tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
			tessellator.addVertexWithUV(d11, d13, d15, d7, d9);
		}
	}
	
	/**
	 * Renders the given texture to the south (x-positive) face of the block.
	 * Args: block, x, y, z, texture
	 */
	public void renderSouthFace(RenderBlocks renderer, Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator tessellator = Tessellator.instance;
		
		if(renderer.hasOverrideBlockTexture())
		{
			par8Icon = renderer.overrideBlockTexture;
		}
		
		double d3 = par8Icon.getInterpolatedU(renderer.renderMinZ * 16.0D);
		double d4 = par8Icon.getInterpolatedU(renderer.renderMaxZ * 16.0D);
		double d5 = par8Icon.getInterpolatedV(16.0D - renderer.renderMaxY * 16.0D);
		double d6 = par8Icon.getInterpolatedV(16.0D - renderer.renderMinY * 16.0D);
		double d7;
		
		if(renderer.flipTexture)
		{
			d7 = d3;
			d3 = d4;
			d4 = d7;
		}
		
		if(renderer.renderMinZ < 0.0D || renderer.renderMaxZ > 1.0D)
		{
			d3 = par8Icon.getMinU();
			d4 = par8Icon.getMaxU();
		}
		
		if(renderer.renderMinY < 0.0D || renderer.renderMaxY > 1.0D)
		{
			d5 = par8Icon.getMinV();
			d6 = par8Icon.getMaxV();
		}
		
		d7 = d4;
		double d8 = d3;
		double d9 = d5;
		double d10 = d6;
		
		if(renderer.uvRotateSouth == 2)
		{
			d3 = par8Icon.getInterpolatedU(renderer.renderMinY * 16.0D);
			d5 = par8Icon.getInterpolatedV(16.0D - renderer.renderMinZ * 16.0D);
			d4 = par8Icon.getInterpolatedU(renderer.renderMaxY * 16.0D);
			d6 = par8Icon.getInterpolatedV(16.0D - renderer.renderMaxZ * 16.0D);
			d9 = d5;
			d10 = d6;
			d7 = d3;
			d8 = d4;
			d5 = d6;
			d6 = d9;
		}
		else if(renderer.uvRotateSouth == 1)
		{
			d3 = par8Icon.getInterpolatedU(16.0D - renderer.renderMaxY * 16.0D);
			d5 = par8Icon.getInterpolatedV(renderer.renderMaxZ * 16.0D);
			d4 = par8Icon.getInterpolatedU(16.0D - renderer.renderMinY * 16.0D);
			d6 = par8Icon.getInterpolatedV(renderer.renderMinZ * 16.0D);
			d7 = d4;
			d8 = d3;
			d3 = d4;
			d4 = d8;
			d9 = d6;
			d10 = d5;
		}
		else if(renderer.uvRotateSouth == 3)
		{
			d3 = par8Icon.getInterpolatedU(16.0D - renderer.renderMinZ * 16.0D);
			d4 = par8Icon.getInterpolatedU(16.0D - renderer.renderMaxZ * 16.0D);
			d5 = par8Icon.getInterpolatedV(renderer.renderMaxY * 16.0D);
			d6 = par8Icon.getInterpolatedV(renderer.renderMinY * 16.0D);
			d7 = d4;
			d8 = d3;
			d9 = d5;
			d10 = d6;
		}
		
		double d11 = par2 + renderer.renderMaxX;
		double d12 = par4 + renderer.renderMinY;
		double d13 = par4 + renderer.renderMaxY;
		double d14 = par6 + renderer.renderMinZ;
		double d15 = par6 + renderer.renderMaxZ;
		
		if(renderer.enableAO)
		{
			tessellator.setColorOpaque_F(renderer.colorRedTopRight, renderer.colorGreenTopRight, renderer.colorBlueTopRight);
			tessellator.setBrightness(renderer.brightnessTopRight);
			tessellator.addVertexWithUV(d11, d13, d15, d3, d5);
			tessellator.setColorOpaque_F(renderer.colorRedBottomRight, renderer.colorGreenBottomRight, renderer.colorBlueBottomRight);
			tessellator.setBrightness(renderer.brightnessBottomRight);
			tessellator.addVertexWithUV(d11, d13, d14, d7, d9);
			tessellator.setColorOpaque_F(renderer.colorRedBottomLeft, renderer.colorGreenBottomLeft, renderer.colorBlueBottomLeft);
			tessellator.setBrightness(renderer.brightnessBottomLeft);
			tessellator.addVertexWithUV(d11, d12, d14, d4, d6);
			tessellator.setColorOpaque_F(renderer.colorRedTopLeft, renderer.colorGreenTopLeft, renderer.colorBlueTopLeft);
			tessellator.setBrightness(renderer.brightnessTopLeft);
			tessellator.addVertexWithUV(d11, d12, d15, d8, d10);
		}
		else
		{
			tessellator.addVertexWithUV(d11, d13, d15, d3, d5);
			tessellator.addVertexWithUV(d11, d13, d14, d7, d9);
			tessellator.addVertexWithUV(d11, d12, d14, d4, d6);
			tessellator.addVertexWithUV(d11, d12, d15, d8, d10);
		}
	}
}