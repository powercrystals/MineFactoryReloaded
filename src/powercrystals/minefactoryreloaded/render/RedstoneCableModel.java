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
	private ModelRenderer _base;
	private ModelRenderer _cableConn;
	private ModelRenderer _plate;
	private ModelRenderer _interfaceConn;
	private ModelRenderer _bandWhite;
	
	private Vector3f[] _bandColors = new Vector3f[16];

	public RedstoneCableModel()
	{
		textureWidth = 64;
		textureHeight = 32;

		_base = new ModelRenderer(this, 36, 0);
		_base.addBox(-2F, -2F, -2F, 4, 4, 4);
		_base.setRotationPoint(0F, 0F, 0F);
		_base.setTextureSize(64, 32);
		_base.mirror = true;
		setRotation(_base, 0F, 0F, 0F);
		_cableConn = new ModelRenderer(this, 36, 8);
		_cableConn.addBox(2F, -2F, -2F, 6, 4, 4);
		_cableConn.setRotationPoint(0F, 0F, 0F);
		_cableConn.setTextureSize(64, 32);
		_cableConn.mirror = true;
		setRotation(_cableConn, 0F, 0F, 0F);
		_plate = new ModelRenderer(this, 0, 0);
		_plate.addBox(6, -7, -7, 2, 14, 14);
		_plate.setRotationPoint(0,0,0);
		_plate.setTextureSize(64, 32);
		_plate.mirror = true;
		setRotation(_plate, 0F, 0F, 0F);
		_interfaceConn = new ModelRenderer(this, 36, 0);
		_interfaceConn.addBox(2, -2, -2, 4, 4, 4);
		_interfaceConn.setRotationPoint(0,0,0);
		_interfaceConn.setTextureSize(64, 32);
		_interfaceConn.mirror = true;
		setRotation(_interfaceConn, 0F, 0F, 0F);
		_bandWhite = new ModelRenderer(this, 36, 16);
		_bandWhite.addBox(5, -2.5f, -2.5f, 1, 5, 5);
		_bandWhite.setRotationPoint(0,0,0);
		_bandWhite.setTextureSize(64, 32);
		_bandWhite.mirror = true;
		setRotation(_bandWhite, 0F, 0F, 0F);
		
		_bandColors[0] = new Vector3f(1.0f,1.0f,1.0f);
		_bandColors[1] = new Vector3f(0.8633f, 0.5195f, 0.293f);
		_bandColors[2] = new Vector3f(0.7578f, 0.4297f, 0.7891f);
		_bandColors[3] = new Vector3f(0.4922f, 0.5977f, 0.8125f);
		_bandColors[4] = new Vector3f(0.8086f, 0.7578f, 0.1914f);
		_bandColors[5] = new Vector3f(0.3086f, 0.7578f, 0.2734f);
		_bandColors[6] = new Vector3f(0.8398f, 0.5898f, 0.6563f);
		_bandColors[7] = new Vector3f(0.2891f, 0.2891f, 0.2891f);
		_bandColors[8] = new Vector3f(0.7031f, 0.7031f, 0.7031f);
		_bandColors[9] = new Vector3f(0.2109f, 0.5f, 0.6172f);
		_bandColors[10] = new Vector3f(0.5664f, 0.3203f, 0.7734f);
		_bandColors[11] = new Vector3f(0.2031f, 0.2539f, 0.6328f);
		_bandColors[12] = new Vector3f(0.3594f, 0.2266f, 0.1406f);
		_bandColors[13] = new Vector3f(0.2383f, 0.3203f, 0.125f);
		_bandColors[14] = new Vector3f(0.6797f, 0.2422f, 0.2188f);
		_bandColors[15] = new Vector3f(0.1055f, 0.1055f, 0.1055f);
	}
	
	public void render(float f5)
	{
		_base.render(f5);
		renderSide(ConnectionState.CableAll, 0, 0, 0, f5);
		renderSide(ConnectionState.CableAll, 0, (float)Math.PI, 0, f5);
	}

	public void render(TileRedstoneCable entity, float f5)
	{
		_base.render(f5);
		
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
		case CableAll:
			_cableConn.rotateAngleY = yRot;
			_cableConn.rotateAngleZ = zRot;
			_cableConn.render(scale);
			break;
		case CableSingle:
			_cableConn.rotateAngleY = yRot;
			_cableConn.rotateAngleZ = zRot;
			_cableConn.render(scale);
			
			_bandWhite.rotateAngleY = yRot;
			_bandWhite.rotateAngleZ = zRot;
			
			GL11.glColor3f(_bandColors[color].x, _bandColors[color].y, _bandColors[color].z);
			_bandWhite.render(scale);
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			break;
		case FlatSingle:
			_interfaceConn.rotateAngleY = yRot;
			_interfaceConn.rotateAngleZ = zRot;
			_interfaceConn.render(scale);
			
			_plate.rotateAngleY = yRot;
			_plate.rotateAngleZ = zRot;
			_plate.render(scale);
			
			_bandWhite.rotateAngleY = yRot;
			_bandWhite.rotateAngleZ = zRot;
			GL11.glColor3f(_bandColors[color].x, _bandColors[color].y, _bandColors[color].z);
			_bandWhite.render(scale);
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
