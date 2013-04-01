package powercrystals.minefactoryreloaded.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import powercrystals.minefactoryreloaded.tile.TileRedstoneCable;
import powercrystals.minefactoryreloaded.tile.TileRedstoneCable.ConnectionState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.common.ForgeDirection;

public class RedstoneCableModel extends ModelBase
{
	// fields
	ModelRenderer Base;
	ModelRenderer CableConn;
	ModelRenderer Plate;
	ModelRenderer InterfaceConn;
	ModelRenderer BandWhite;
	
	private Vector3f[] mBandColors = new Vector3f[16];

	public RedstoneCableModel()
	{
		textureWidth = 64;
		textureHeight = 32;

		Base = new ModelRenderer(this, 36, 0);
		Base.addBox(-2F, -2F, -2F, 4, 4, 4);
		Base.setRotationPoint(0F, 0F, 0F);
		Base.setTextureSize(64, 32);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);
		CableConn = new ModelRenderer(this, 36, 8);
		CableConn.addBox(2F, -2F, -2F, 6, 4, 4);
		CableConn.setRotationPoint(0F, 0F, 0F);
		CableConn.setTextureSize(64, 32);
		CableConn.mirror = true;
		setRotation(CableConn, 0F, 0F, 0F);
		Plate = new ModelRenderer(this, 0, 0);
		Plate.addBox(6, -7, -7, 2, 14, 14);
		Plate.setRotationPoint(0,0,0);
		Plate.setTextureSize(64, 32);
		Plate.mirror = true;
		setRotation(Plate, 0F, 0F, 0F);
		InterfaceConn = new ModelRenderer(this, 36, 0);
		InterfaceConn.addBox(2, -2, -2, 4, 4, 4);
		InterfaceConn.setRotationPoint(0,0,0);
		InterfaceConn.setTextureSize(64, 32);
		InterfaceConn.mirror = true;
		setRotation(InterfaceConn, 0F, 0F, 0F);
		BandWhite = new ModelRenderer(this, 36, 16);
		BandWhite.addBox(5, -2.5f, -2.5f, 1, 5, 5);
		BandWhite.setRotationPoint(0,0,0);
		BandWhite.setTextureSize(64, 32);
		BandWhite.mirror = true;
		setRotation(BandWhite, 0F, 0F, 0F);
		
		mBandColors[0] = new Vector3f(1.0f,1.0f,1.0f);
		mBandColors[1] = new Vector3f(0.8633f, 0.5195f, 0.293f);
		mBandColors[2] = new Vector3f(0.7578f, 0.4297f, 0.7891f);
		mBandColors[3] = new Vector3f(0.4922f, 0.5977f, 0.8125f);
		mBandColors[4] = new Vector3f(0.8086f, 0.7578f, 0.1914f);
		mBandColors[5] = new Vector3f(0.3086f, 0.7578f, 0.2734f);
		mBandColors[6] = new Vector3f(0.8398f, 0.5898f, 0.6563f);
		mBandColors[7] = new Vector3f(0.2891f, 0.2891f, 0.2891f);
		mBandColors[8] = new Vector3f(0.7031f, 0.7031f, 0.7031f);
		mBandColors[9] = new Vector3f(0.2109f, 0.5f, 0.6172f);
		mBandColors[10] = new Vector3f(0.5664f, 0.3203f, 0.7734f);
		mBandColors[11] = new Vector3f(0.2031f, 0.2539f, 0.6328f);
		mBandColors[12] = new Vector3f(0.3594f, 0.2266f, 0.1406f);
		mBandColors[13] = new Vector3f(0.2383f, 0.3203f, 0.125f);
		mBandColors[14] = new Vector3f(0.6797f, 0.2422f, 0.2188f);
		mBandColors[15] = new Vector3f(0.1055f, 0.1055f, 0.1055f);
	}
	
	public void render(float f5)
	{
		Base.render(f5);
		renderSide(ConnectionState.ConnectToCable, 0, 0, 0, f5);
		renderSide(ConnectionState.ConnectToCable, 0, (float)Math.PI, 0, f5);
	}

	public void render(TileRedstoneCable entity, float f5)
	{
		Base.render(f5);
		
		renderSide(entity.getConnectionState(ForgeDirection.EAST), entity.getSideColor(ForgeDirection.EAST),0,0,f5);
		renderSide(entity.getConnectionState(ForgeDirection.SOUTH), entity.getSideColor(ForgeDirection.SOUTH),(float)-Math.PI/2, 0, f5);
		renderSide(entity.getConnectionState(ForgeDirection.WEST), entity.getSideColor(ForgeDirection.WEST),(float)Math.PI, 0, f5);
		renderSide(entity.getConnectionState(ForgeDirection.NORTH), entity.getSideColor(ForgeDirection.NORTH),(float)Math.PI/2, 0, f5);
		renderSide(entity.getConnectionState(ForgeDirection.UP), entity.getSideColor(ForgeDirection.UP),0,(float)Math.PI/2, f5);
		renderSide(entity.getConnectionState(ForgeDirection.DOWN), entity.getSideColor(ForgeDirection.DOWN),0,(float)-Math.PI/2, f5);
	}
	
	private void renderSide(ConnectionState state, int color, float yRot, float zRot, float scale)
	{
		switch (state)
		{
		case ConnectToCable:
			CableConn.rotateAngleY = yRot;
			CableConn.rotateAngleZ = zRot;
			CableConn.render(scale);
			break;
		case ConnectToMachine:
			CableConn.rotateAngleY = yRot;
			CableConn.rotateAngleZ = zRot;
			CableConn.render(scale);
			
			BandWhite.rotateAngleY = yRot;
			BandWhite.rotateAngleZ = zRot;
			
			GL11.glColor3f(mBandColors[color].x, mBandColors[color].y, mBandColors[color].z);
			BandWhite.render(scale);
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			break;
		case ConnectToInterface:
			InterfaceConn.rotateAngleY = yRot;
			InterfaceConn.rotateAngleZ = zRot;
			InterfaceConn.render(scale);
			
			Plate.rotateAngleY = yRot;
			Plate.rotateAngleZ = zRot;
			Plate.render(scale);
			
			BandWhite.rotateAngleY = yRot;
			BandWhite.rotateAngleZ = zRot;
			GL11.glColor3f(mBandColors[color].x, mBandColors[color].y, mBandColors[color].z);
			BandWhite.render(scale);
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			break;
		default:
			break;
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

}
