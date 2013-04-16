package powercrystals.minefactoryreloaded.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class RedNetCardsModel extends ModelBase {
	// fields
	ModelRenderer Empty;
	ModelRenderer Lvl1Base;
	ModelRenderer Lvl1Fan;
	ModelRenderer Lvl1Bit1;
	ModelRenderer Lvl1Bit2;
	ModelRenderer Lvl2Base;
	ModelRenderer Lvl2Fan;
	ModelRenderer Lvl2Bit1;
	ModelRenderer Lvl2Cord1;
	ModelRenderer Lvl2Cord2;
	ModelRenderer Lvl2Cord3;
	ModelRenderer Lvl3Base;
	ModelRenderer Lvl3Fan;
	ModelRenderer Lvl3FrameNear;
	ModelRenderer Lvl3FrameFar;
	ModelRenderer Lvl3FrameTop;
	ModelRenderer Lvl3FrameBottom;
	ModelRenderer Lvl3Bit;
	ModelRenderer Lvl2Bit2;

	public RedNetCardsModel() {
		textureWidth = 48;
		textureHeight = 24;

		Empty = new ModelRenderer(this, 42, 16);
		Empty.addBox(0F, 0F, 0F, 1, 5, 1);
		Empty.setRotationPoint(0F, 0F, 0F);
		Empty.setTextureSize(48, 24);
		setRotation(Empty, 0F, 0F, 0F);
		Lvl1Base = new ModelRenderer(this, 0, 0);
		Lvl1Base.addBox(0F, 0F, 0F, 14, 5, 1);
		Lvl1Base.setRotationPoint(0F, 0F, 0F);
		Lvl1Base.setTextureSize(48, 24);
		setRotation(Lvl1Base, 0F, 0F, 0F);
		Lvl1Fan = new ModelRenderer(this, 30, 0);
		Lvl1Fan.addBox(3F, 1F, 1F, 3, 3, 1);
		Lvl1Fan.setRotationPoint(0F, 0F, 0F);
		Lvl1Fan.setTextureSize(48, 24);
		setRotation(Lvl1Fan, 0F, 0F, 0F);
		Lvl1Bit1 = new ModelRenderer(this, 30, 4);
		Lvl1Bit1.addBox(0F, 0F, 0F, 3, 1, 1);
		Lvl1Bit1.setRotationPoint(9F, 1F, 1F);
		Lvl1Bit1.setTextureSize(48, 24);
		setRotation(Lvl1Bit1, 0F, 0F, 0F);
		Lvl1Bit2 = new ModelRenderer(this, 30, 4);
		Lvl1Bit2.addBox(8F, 3F, 1F, 3, 1, 1);
		Lvl1Bit2.setRotationPoint(0F, 0F, 0F);
		Lvl1Bit2.setTextureSize(48, 24);
		setRotation(Lvl1Bit2, 0F, 0F, 0F);
		Lvl2Base = new ModelRenderer(this, 0, 6);
		Lvl2Base.addBox(0F, 0F, 0F, 14, 5, 1);
		Lvl2Base.setRotationPoint(0F, 0F, 0F);
		Lvl2Base.setTextureSize(48, 24);
		setRotation(Lvl2Base, 0F, 0F, 0F);
		Lvl2Fan = new ModelRenderer(this, 30, 6);
		Lvl2Fan.addBox(0F, 0F, 0F, 3, 3, 1);
		Lvl2Fan.setRotationPoint(3F, 1F, 1F);
		Lvl2Fan.setTextureSize(48, 24);
		setRotation(Lvl2Fan, 0F, 0F, 0F);
		Lvl2Bit1 = new ModelRenderer(this, 38, 5);
		Lvl2Bit1.addBox(0F, 0F, 0F, 2, 2, 2);
		Lvl2Bit1.setRotationPoint(11F, 1F, 1F);
		Lvl2Bit1.setTextureSize(48, 24);
		setRotation(Lvl2Bit1, 0F, 0F, 0F);
		Lvl2Cord1 = new ModelRenderer(this, 30, 10);
		Lvl2Cord1.addBox(9F, 3F, 1F, 3, 1, 1);
		Lvl2Cord1.setRotationPoint(0F, 0F, 0F);
		Lvl2Cord1.setTextureSize(48, 24);
		setRotation(Lvl2Cord1, 0F, 0F, 0F);
		Lvl2Cord2 = new ModelRenderer(this, 38, 2);
		Lvl2Cord2.addBox(8F, 1F, 1F, 1, 2, 1);
		Lvl2Cord2.setRotationPoint(0F, 0F, 0F);
		Lvl2Cord2.setTextureSize(48, 24);
		setRotation(Lvl2Cord2, 0F, 0F, 0F);
		Lvl2Cord3 = new ModelRenderer(this, 38, 0);
		Lvl2Cord3.addBox(6F, 1F, 1F, 2, 1, 1);
		Lvl2Cord3.setRotationPoint(0F, 0F, 0F);
		Lvl2Cord3.setTextureSize(48, 24);
		setRotation(Lvl2Cord3, 0F, 0F, 0F);
		Lvl3Base = new ModelRenderer(this, 0, 12);
		Lvl3Base.addBox(0F, 0F, 0F, 14, 5, 1);
		Lvl3Base.setRotationPoint(0F, 0F, 0F);
		Lvl3Base.setTextureSize(48, 24);
		setRotation(Lvl3Base, 0F, 0F, 0F);
		Lvl3Fan = new ModelRenderer(this, 30, 12);
		Lvl3Fan.addBox(3F, 1F, 1F, 3, 3, 1);
		Lvl3Fan.setRotationPoint(0F, 0F, 0F);
		Lvl3Fan.setTextureSize(48, 24);
		setRotation(Lvl3Fan, 0F, 0F, 0F);
		Lvl3FrameNear = new ModelRenderer(this, 38, 9);
		Lvl3FrameNear.addBox(0F, 0F, 1F, 3, 5, 2);
		Lvl3FrameNear.setRotationPoint(0F, 0F, 0F);
		Lvl3FrameNear.setTextureSize(48, 24);
		setRotation(Lvl3FrameNear, 0F, 0F, 0F);
		Lvl3FrameFar = new ModelRenderer(this, 0, 18);
		Lvl3FrameFar.addBox(6F, 0F, 1F, 8, 4, 2);
		Lvl3FrameFar.setRotationPoint(0F, 0F, 0F);
		Lvl3FrameFar.setTextureSize(48, 24);
		setRotation(Lvl3FrameFar, 0F, 0F, 0F);
		Lvl3FrameTop = new ModelRenderer(this, 20, 18);
		Lvl3FrameTop.addBox(3F, 0F, 1F, 3, 1, 2);
		Lvl3FrameTop.setRotationPoint(0F, 0F, 0F);
		Lvl3FrameTop.setTextureSize(48, 24);
		setRotation(Lvl3FrameTop, 0F, 0F, 0F);
		Lvl3FrameBottom = new ModelRenderer(this, 30, 16);
		Lvl3FrameBottom.addBox(3F, 4F, 1F, 4, 1, 2);
		Lvl3FrameBottom.setRotationPoint(0F, 0F, 0F);
		Lvl3FrameBottom.setTextureSize(48, 24);
		setRotation(Lvl3FrameBottom, 0F, 0F, 0F);
		Lvl3Bit = new ModelRenderer(this, 20, 21);
		Lvl3Bit.addBox(9F, 4F, 1F, 3, 1, 1);
		Lvl3Bit.setRotationPoint(0F, 0F, 0F);
		Lvl3Bit.setTextureSize(48, 24);
		setRotation(Lvl3Bit, 0F, 0F, 0F);
		Lvl2Bit2 = new ModelRenderer(this, 38, 5);
		Lvl2Bit2.addBox(6F, 3F, 1F, 2, 2, 2);
		Lvl2Bit2.setRotationPoint(0F, 0F, 0F);
		Lvl2Bit2.setTextureSize(48, 24);
		setRotation(Lvl2Bit2, 0F, 0F, 0F);
	}

	public void renderEmptySlot(float scale) {
		Empty.render(scale);
	}

	public void renderLevel1(float scale) {
		Lvl1Base.render(scale);
		Lvl1Fan.render(scale);
		Lvl1Bit1.render(scale);
		Lvl1Bit2.render(scale);
	}

	public void renderLevel2(float scale) {
		Lvl2Base.render(scale);
		Lvl2Fan.render(scale);
		Lvl2Bit1.render(scale);
		Lvl2Bit2.render(scale);
		Lvl2Cord1.render(scale);
		Lvl2Cord2.render(scale);
		Lvl2Cord3.render(scale);
	}

	public void renderLevel3(float scale) {
		Lvl3Base.render(scale);
		Lvl3Fan.render(scale);
		Lvl3FrameNear.render(scale);
		Lvl3FrameFar.render(scale);
		Lvl3FrameTop.render(scale);
		Lvl3FrameBottom.render(scale);
		Lvl3Bit.render(scale);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}