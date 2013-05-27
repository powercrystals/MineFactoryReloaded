package skyboy.core.world;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.logging.ILogAgent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.collect.ImmutableSetMultimap;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings("rawtypes")
public class WorldServerProxy extends WorldServer {    
	protected WorldServer proxiedWorld;

	private static String getPar2String(World world) {
		// TODO: reflect to avoid triggering overridden methods?
		return world.getWorldInfo().getWorldName();
	}

	private static WorldSettings getPar4WorldSettings(World world) {
		// TODO: reflect to avoid triggering overridden methods?
		return new WorldSettings(world.getWorldInfo());
	}
	
	public WorldServerProxy(WorldServer world) {
		super(world.getMinecraftServer(), world.getSaveHandler(), getPar2String(world), world.provider, getPar4WorldSettings(world), world.theProfiler, world.getWorldLogAgent());
		this.proxiedWorld = world;
		//perWorldStorage = world.perWorldStorage; // final, set in super; requires reflection
		ReflectionHelper.setPrivateValue(World.class, this, world.perWorldStorage, new String[]{"perWorldStorage"}); // forge-added, no reobf
		scheduledUpdatesAreImmediate = world.scheduledUpdatesAreImmediate;
		loadedEntityList = world.loadedEntityList;
		loadedTileEntityList = world.loadedTileEntityList;
		playerEntities = world.playerEntities;
		weatherEffects = world.weatherEffects;
		skylightSubtracted = world.skylightSubtracted;
		prevRainingStrength = world.prevRainingStrength;
		rainingStrength = world.rainingStrength;
		prevThunderingStrength = world.prevThunderingStrength;
		thunderingStrength = world.thunderingStrength;
		lastLightningBolt = world.lastLightningBolt;
		difficultySetting = world.difficultySetting;
		rand = world.rand;
		//provider = world.provider; // handled by super
		findingSpawnPoint = world.findingSpawnPoint;
		mapStorage = world.mapStorage;
		villageCollectionObj = world.villageCollectionObj;
		//theProfiler = world.theProfiler; // handled by super
		isRemote = world.isRemote;
		theChunkProviderServer = world.theChunkProviderServer;
	    canNotSave = world.canNotSave;
	    allPlayersSleeping = world.allPlayersSleeping;
		customTeleporters = world.customTeleporters;
	}

	@Override
	/**
	 * Gets the biome for a given set of x/z coordinates
	 */
	public BiomeGenBase getBiomeGenForCoords(int par1, int par2) {
		return this.proxiedWorld.getBiomeGenForCoords(par1, par2);
	}

	@Override
	public BiomeGenBase getBiomeGenForCoordsBody(int par1, int par2) {
		return this.proxiedWorld.getBiomeGenForCoordsBody(par1, par2);
	}

	@Override
	public WorldChunkManager getWorldChunkManager() {
		return this.proxiedWorld.getWorldChunkManager();
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void finishSetup() {
		ArrayList<String> q = new ArrayList<String>();
		q.add("finishSetup"); // forge-added, no reobf
		//q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.World", new String[]{""})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]));
		try {
			callable.invoke(this.proxiedWorld);
		} catch (Throwable e) {
		}
	}

	@Override
	/**
	 * Creates the chunk provider for this world. Called in the constructor. Retrieves provider from worldProvider?
	 */
	protected IChunkProvider createChunkProvider() {
		ArrayList<String> q = new ArrayList<String>();
		q.add("createChunkProvider");
		q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.World", new String[]{"func_72970_h"})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]));
		try {
			return (IChunkProvider)callable.invoke(this.proxiedWorld);
		} catch (Throwable e) {
			return null;
		}
	}

	@Override
	protected void initialize(WorldSettings par1WorldSettings) {
		ArrayList<String> q = new ArrayList<String>();
		q.add("initialize");
		q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.World", new String[]{"func_72963_a"})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]), WorldSettings.class);
		try {
			callable.invoke(this.proxiedWorld, par1WorldSettings);
		} catch (Throwable e) {
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Sets a new spawn location by finding an uncovered block at a random (x,z) location in the chunk.
	 */
	public void setSpawnLocation() {
		this.proxiedWorld.setSpawnLocation();
	}

	@Override
	/**
	 * Returns the block ID of the first block at this (x,z) location with air above it, searching from sea level
	 * upwards.
	 */
	public int getFirstUncoveredBlock(int par1, int par2) {
		return this.proxiedWorld.getFirstUncoveredBlock(par1, par2);
	}

	@Override
	/**
	 * Returns the block ID at coords x,y,z
	 */
	public int getBlockId(int par1, int par2, int par3) {
		return this.proxiedWorld.getBlockId(par1, par2, par3);
	}

	@Override
	/**
	 * Returns true if the block at the specified coordinates is empty
	 */
	public boolean isAirBlock(int par1, int par2, int par3) {
		return this.proxiedWorld.isAirBlock(par1, par2, par3);
	}

	@Override
	/**
	 * Checks if a block at a given position should have a tile entity.
	 */
	public boolean blockHasTileEntity(int par1, int par2, int par3) {
		return this.proxiedWorld.blockHasTileEntity(par1, par2, par3);
	}

	@Override
	/**
	 * Returns the render type of the block at the given coordinate.
	 */
	public int blockGetRenderType(int par1, int par2, int par3) {
		return this.proxiedWorld.blockGetRenderType(par1, par2, par3);
	}

	@Override
	/**
	 * Returns whether a block exists at world coordinates x, y, z
	 */
	public boolean blockExists(int par1, int par2, int par3) {
		return this.proxiedWorld.blockExists(par1, par2, par3);
	}

	@Override
	/**
	 * Checks if any of the chunks within distance (argument 4) blocks of the given block exist
	 */
	public boolean doChunksNearChunkExist(int par1, int par2, int par3, int par4) {
		return this.proxiedWorld.doChunksNearChunkExist(par1, par2, par3, par4);
	}

	@Override
	/**
	 * Checks between a min and max all the chunks inbetween actually exist. Args: minX, minY, minZ, maxX, maxY, maxZ
	 */
	public boolean checkChunksExist(int par1, int par2, int par3, int par4, int par5, int par6) {
		return this.proxiedWorld.checkChunksExist(par1, par2, par3, par4, par5, par6);
	}

	@Override
	/**
	 * Returns whether a chunk exists at chunk coordinates x, y
	 */
	protected boolean chunkExists(int par1, int par2) {
		ArrayList<String> q = new ArrayList<String>();
		q.add("chunkExists");
		q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.World", new String[]{"func_72916_c"})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]), int.class, int.class);
		try {
			return (Boolean)callable.invoke(this.proxiedWorld, par1, par2);
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	/**
	 * Returns a chunk looked up by block coordinates. Args: x, z
	 */
	public Chunk getChunkFromBlockCoords(int par1, int par2) {
		return this.proxiedWorld.getChunkFromBlockCoords(par1, par2);
	}

	@Override
	/**
	 * Returns back a chunk looked up by chunk coordinates Args: x, y
	 */
	public Chunk getChunkFromChunkCoords(int par1, int par2) {
		return this.proxiedWorld.getChunkFromChunkCoords(par1, par2);
	}

	@Override
	/**
	 * Sets the block ID and metadata at a given location. Args: X, Y, Z, new block ID, new metadata, flags. Flag 1 will
	 * cause a block update. Flag 2 will send the change to clients (you almost always want this). Flag 4 prevents the
	 * block from being re-rendered, if this is a client world. Flags can be added together.
	 */
	public boolean setBlock(int par1, int par2, int par3, int par4, int par5, int par6) {
		return this.proxiedWorld.setBlock(par1, par2, par3, par4, par5, par6);
	}

	@Override
	/**
	 * Returns the block's material.
	 */
	public Material getBlockMaterial(int par1, int par2, int par3) {
		return this.proxiedWorld.getBlockMaterial(par1, par2, par3);
	}

	@Override
	/**
	 * Returns the block metadata at coords x,y,z
	 */
	public int getBlockMetadata(int par1, int par2, int par3) {
		return this.proxiedWorld.getBlockMetadata(par1, par2, par3);
	}

	@Override
	/**
	 * Sets the blocks metadata and if set will then notify blocks that this block changed, depending on the flag. Args:
	 * x, y, z, metadata, flag. See setBlock for flag description
	 */
	public boolean setBlockMetadataWithNotify(int par1, int par2, int par3, int par4, int par5) {
		return this.proxiedWorld.setBlockMetadataWithNotify(par1, par2, par3, par4, par5);
	}

	@Override
	/**
	 * Sets a block to 0 and notifies relevant systems with the block change  Args: x, y, z
	 */
	public boolean setBlockToAir(int par1, int par2, int par3) {
		return this.proxiedWorld.setBlockToAir(par1, par2, par3);
	}

	@Override
	/**
	 * Destroys a block and optionally drops items. Args: X, Y, Z, dropItems
	 */
	public boolean destroyBlock(int par1, int par2, int par3, boolean par4) {
		return this.proxiedWorld.destroyBlock(par1, par2, par3, par4);
	}

	@Override
	/**
	 * Sets a block and notifies relevant systems with the block change  Args: x, y, z, blockID
	 */
	public boolean setBlock(int par1, int par2, int par3, int par4) {
		return this.proxiedWorld.setBlock(par1, par2, par3, par4);
	}

	@Override
	/**
	 * On the client, re-renders the block. On the server, sends the block to the client (which will re-render it),
	 * including the tile entity description packet if applicable. Args: x, y, z
	 */
	public void markBlockForUpdate(int par1, int par2, int par3) {
		this.proxiedWorld.markBlockForUpdate(par1, par2, par3);
	}

	@Override
	/**
	 * The block type change and need to notify other systems  Args: x, y, z, blockID
	 */
	public void notifyBlockChange(int par1, int par2, int par3, int par4) {
		this.proxiedWorld.notifyBlockChange(par1, par2, par3, par4);
	}

	@Override
	/**
	 * marks a vertical line of blocks as dirty
	 */
	public void markBlocksDirtyVertical(int par1, int par2, int par3, int par4) {
		this.proxiedWorld.markBlocksDirtyVertical(par1, par2, par3, par4);
	}

	@Override
	/**
	 * On the client, re-renders all blocks in this range, inclusive. On the server, does nothing. Args: min x, min y,
	 * min z, max x, max y, max z
	 */
	public void markBlockRangeForRenderUpdate(int par1, int par2, int par3, int par4, int par5, int par6) {
		this.proxiedWorld.markBlockRangeForRenderUpdate(par1, par2, par3, par4, par5, par6);
	}

	@Override
	/**
	 * Notifies neighboring blocks that this specified block changed  Args: x, y, z, blockID
	 */
	public void notifyBlocksOfNeighborChange(int par1, int par2, int par3, int par4) {
		this.proxiedWorld.notifyBlocksOfNeighborChange(par1, par2, par3, par4);
	}

	@Override
	/**
	 * Calls notifyBlockOfNeighborChange on adjacent blocks, except the one on the given side. Args: X, Y, Z,
	 * changingBlockID, side
	 */
	public void notifyBlocksOfNeighborChange(int par1, int par2, int par3, int par4, int par5) {
		this.proxiedWorld.notifyBlocksOfNeighborChange(par1, par2, par3, par4, par5);
	}

	@Override
	/**
	 * Notifies a block that one of its neighbor change to the specified type Args: x, y, z, blockID
	 */
	public void notifyBlockOfNeighborChange(int par1, int par2, int par3, int par4) {
		this.proxiedWorld.notifyBlockOfNeighborChange(par1, par2, par3, par4);
	}

	@Override
	/**
	 * Returns true if the given block will receive a scheduled tick in the future. Args: X, Y, Z, blockID
	 */
	public boolean isBlockTickScheduled(int par1, int par2, int par3, int par4) {
		return this.proxiedWorld.isBlockTickScheduled(par1, par2, par3, par4);
	}

	@Override
	/**
	 * Checks if the specified block is able to see the sky
	 */
	public boolean canBlockSeeTheSky(int par1, int par2, int par3) {
		return this.proxiedWorld.canBlockSeeTheSky(par1, par2, par3);
	}

	@Override
	/**
	 * Does the same as getBlockLightValue_do but without checking if its not a normal block
	 */
	public int getFullBlockLightValue(int par1, int par2, int par3) {
		return this.proxiedWorld.getFullBlockLightValue(par1, par2, par3);
	}

	@Override
	/**
	 * Gets the light value of a block location
	 */
	public int getBlockLightValue(int par1, int par2, int par3) {
		return this.proxiedWorld.getBlockLightValue(par1, par2, par3);
	}

	@Override
	/**
	 * Gets the light value of a block location. This is the actual function that gets the value and has a bool flag
	 * that indicates if its a half step block to get the maximum light value of a direct neighboring block (left,
	 * right, forward, back, and up)
	 */
	public int getBlockLightValue_do(int par1, int par2, int par3, boolean par4) {
		return this.proxiedWorld.getBlockLightValue_do(par1, par2, par3, par4);
	}

	@Override
	/**
	 * Returns the y coordinate with a block in it at this x, z coordinate
	 */
	public int getHeightValue(int par1, int par2) {
		return this.proxiedWorld.getHeightValue(par1, par2);
	}

	@Override
	/**
	 * Gets the heightMapMinimum field of the given chunk, or 0 if the chunk is not loaded. Coords are in blocks. Args:
	 * X, Z
	 */
	public int getChunkHeightMapMinimum(int par1, int par2) {
		return this.proxiedWorld.getChunkHeightMapMinimum(par1, par2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Brightness for SkyBlock.Sky is clear white and (through color computing it is assumed) DEPENDENT ON DAYTIME.
	 * Brightness for SkyBlock.Block is yellowish and independent.
	 */
	public int getSkyBlockTypeBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4) {
		return this.proxiedWorld.getSkyBlockTypeBrightness(par1EnumSkyBlock, par2, par3, par4);
	}

	@Override
	/**
	 * Returns saved light value without taking into account the time of day.  Either looks in the sky light map or
	 * block light map based on the enumSkyBlock arg.
	 */
	public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4) {
		return this.proxiedWorld.getSavedLightValue(par1EnumSkyBlock, par2, par3, par4);
	}

	@Override
	/**
	 * Sets the light value either into the sky map or block map depending on if enumSkyBlock is set to sky or block.
	 * Args: enumSkyBlock, x, y, z, lightValue
	 */
	public void setLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5) {
		this.proxiedWorld.setLightValue(par1EnumSkyBlock, par2, par3, par4, par5);
	}

	@Override
	/**
	 * On the client, re-renders this block. On the server, does nothing. Used for lighting updates.
	 */
	public void markBlockForRenderUpdate(int par1, int par2, int par3) {
		this.proxiedWorld.markBlockForRenderUpdate(par1, par2, par3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Any Light rendered on a 1.8 Block goes through here
	 */
	public int getLightBrightnessForSkyBlocks(int par1, int par2, int par3, int par4) {
		return this.proxiedWorld.getLightBrightnessForSkyBlocks(par1, par2, par3, par4);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getBrightness(int par1, int par2, int par3, int par4) {
		return this.proxiedWorld.getBrightness(par1, par2, par3, par4);
	}

	@Override
	/**
	 * Returns how bright the block is shown as which is the block's light value looked up in a lookup table (light
	 * values aren't linear for brightness). Args: x, y, z
	 */
	public float getLightBrightness(int par1, int par2, int par3) {
		return this.proxiedWorld.getLightBrightness(par1, par2, par3);
	}

	@Override
	/**
	 * Checks whether its daytime by seeing if the light subtracted from the skylight is less than 4
	 */
	public boolean isDaytime() {
		return this.proxiedWorld.isDaytime();
	}

	@Override
	/**
	 * ray traces all blocks, including non-collideable ones
	 */
	public MovingObjectPosition rayTraceBlocks(Vec3 par1Vec3, Vec3 par2Vec3) {
		return this.proxiedWorld.rayTraceBlocks(par1Vec3, par2Vec3);
	}

	@Override
	public MovingObjectPosition rayTraceBlocks_do(Vec3 par1Vec3, Vec3 par2Vec3, boolean par3) {
		return this.proxiedWorld.rayTraceBlocks_do(par1Vec3, par2Vec3, par3);
	}

	@Override
	public MovingObjectPosition rayTraceBlocks_do_do(Vec3 par1Vec3, Vec3 par2Vec3, boolean par3, boolean par4) {
		return this.proxiedWorld.rayTraceBlocks_do_do(par1Vec3, par2Vec3, par3, par4);
	}

	@Override
	/**
	 * Plays a sound at the entity's position. Args: entity, sound, volume (relative to 1.0), and frequency (or pitch,
	 * also relative to 1.0).
	 */
	public void playSoundAtEntity(Entity par1Entity, String par2Str, float par3, float par4) {
		this.proxiedWorld.playSoundAtEntity(par1Entity, par2Str, par3, par4);
	}

	@Override
	/**
	 * Plays sound to all near players except the player reference given
	 */
	public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, float par3, float par4) {
		this.proxiedWorld.playSoundToNearExcept(par1EntityPlayer, par2Str, par3, par4);
	}

	@Override
	/**
	 * Play a sound effect. Many many parameters for this function. Not sure what they do, but a classic call is :
	 * (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 'random.door_open', 1.0F, world.rand.nextFloat() * 0.1F +
	 * 0.9F with i,j,k position of the block.
	 */
	public void playSoundEffect(double par1, double par3, double par5, String par7Str, float par8, float par9) {
		this.proxiedWorld.playSoundEffect(par1, par3, par5, par7Str, par8, par9);
	}

	@Override
	/**
	 * par8 is loudness, all pars passed to minecraftInstance.sndManager.playSound
	 */
	public void playSound(double par1, double par3, double par5, String par7Str, float par8, float par9, boolean par10) {
		this.proxiedWorld.playSound(par1, par3, par5, par7Str, par8, par9, par10);
	}

	@Override
	/**
	 * Plays a record at the specified coordinates of the specified name. Args: recordName, x, y, z
	 */
	public void playRecord(String par1Str, int par2, int par3, int par4) {
		this.proxiedWorld.playRecord(par1Str, par2, par3, par4);
	}

	@Override
	/**
	 * Spawns a particle.  Args particleName, x, y, z, velX, velY, velZ
	 */
	public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {
		this.proxiedWorld.spawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
	}

	@Override
	/**
	 * adds a lightning bolt to the list of lightning bolts in this world.
	 */
	public boolean addWeatherEffect(Entity par1Entity) {
		return this.proxiedWorld.addWeatherEffect(par1Entity);
	}

	@Override
	/**
	 * Called to place all entities as part of a world
	 */
	public boolean spawnEntityInWorld(Entity par1Entity) {
		return this.proxiedWorld.spawnEntityInWorld(par1Entity);
	}

	@Override
	/**
	 * Start the skin for this entity downloading, if necessary, and increment its reference counter
	 */
	protected void obtainEntitySkin(Entity par1Entity) {
		ArrayList<String> q = new ArrayList<String>();
		q.add("obtainEntitySkin");
		q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.World", new String[]{"func_72923_a"})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]), Entity.class);
		try {
			callable.invoke(this.proxiedWorld, par1Entity);
		} catch (Throwable e) {
		}
	}

	@Override
	/**
	 * Decrement the reference counter for this entity's skin image data
	 */
	public void releaseEntitySkin(Entity par1Entity) {
		this.proxiedWorld.releaseEntitySkin(par1Entity);
	}

	@Override
	/**
	 * Schedule the entity for removal during the next tick. Marks the entity dead in anticipation.
	 */
	public void removeEntity(Entity par1Entity) {
		this.proxiedWorld.removeEntity(par1Entity);
	}

	@Override
	/**
	 * Do NOT use this method to remove normal entities- use normal removeEntity
	 */
	public void removePlayerEntityDangerously(Entity par1Entity) {
		this.proxiedWorld.removePlayerEntityDangerously(par1Entity);
	}

	@Override
	/**
	 * Adds a IWorldAccess to the list of worldAccesses
	 */
	public void addWorldAccess(IWorldAccess par1IWorldAccess) {
		this.proxiedWorld.addWorldAccess(par1IWorldAccess);
	}

	@Override
	/**
	 * Returns a list of bounding boxes that collide with aabb excluding the passed in entity's collision. Args: entity,
	 * aabb
	 */
	public List getCollidingBoundingBoxes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB) {
		return this.proxiedWorld.getCollidingBoundingBoxes(par1Entity, par2AxisAlignedBB);
	}

	@Override
	/**
	 * calculates and returns a list of colliding bounding boxes within a given AABB
	 */
	public List getCollidingBlockBounds(AxisAlignedBB par1AxisAlignedBB) {
		return this.proxiedWorld.getCollidingBlockBounds(par1AxisAlignedBB);
	}

	@Override
	/**
	 * Returns the amount of skylight subtracted for the current time
	 */
	public int calculateSkylightSubtracted(float par1) {
		return this.proxiedWorld.calculateSkylightSubtracted(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Removes a worldAccess from the worldAccesses object
	 */
	public void removeWorldAccess(IWorldAccess par1IWorldAccess) {
		this.proxiedWorld.removeWorldAccess(par1IWorldAccess);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns the sun brightness - checks time of day, rain and thunder
	 */
	public float getSunBrightness(float par1) {
		return this.proxiedWorld.getSunBrightness(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Calculates the color for the skybox
	 */
	public Vec3 getSkyColor(Entity par1Entity, float par2) {
		return this.proxiedWorld.getSkyColor(par1Entity, par2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getSkyColorBody(Entity par1Entity, float par2) {
		return this.proxiedWorld.getSkyColorBody(par1Entity, par2);
	}

	@Override
	/**
	 * calls calculateCelestialAngle
	 */
	public float getCelestialAngle(float par1) {
		return this.proxiedWorld.getCelestialAngle(par1);
	}

	@Override
	public int getMoonPhase() {
		return this.proxiedWorld.getMoonPhase();
	}

	@Override
	/**
	 * Return getCelestialAngle()*2*PI
	 */
	public float getCelestialAngleRadians(float par1) {
		return this.proxiedWorld.getCelestialAngleRadians(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getCloudColour(float par1) {
		return this.proxiedWorld.getCloudColour(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 drawCloudsBody(float par1) {
		return this.proxiedWorld.drawCloudsBody(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns vector(ish) with R/G/B for fog
	 */
	public Vec3 getFogColor(float par1) {
		return this.proxiedWorld.getFogColor(par1);
	}

	@Override
	/**
	 * Gets the height to which rain/snow will fall. Calculates it if not already stored.
	 */
	public int getPrecipitationHeight(int par1, int par2) {
		return this.proxiedWorld.getPrecipitationHeight(par1, par2);
	}

	@Override
	/**
	 * Finds the highest block on the x, z coordinate that is solid and returns its y coord. Args x, z
	 */
	public int getTopSolidOrLiquidBlock(int par1, int par2) {
		return this.proxiedWorld.getTopSolidOrLiquidBlock(par1, par2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * How bright are stars in the sky
	 */
	public float getStarBrightness(float par1) {
		return this.proxiedWorld.getStarBrightness(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightnessBody(float par1) {
		return this.proxiedWorld.getStarBrightnessBody(par1);
	}

	@Override
	/**
	 * Schedules a tick to a block with a delay (Most commonly the tick rate)
	 */
	public void scheduleBlockUpdate(int par1, int par2, int par3, int par4, int par5) {
		this.proxiedWorld.scheduleBlockUpdate(par1, par2, par3, par4, par5);
	}

	@Override
	public void func_82740_a(int par1, int par2, int par3, int par4, int par5, int par6) {
		this.proxiedWorld.func_82740_a(par1, par2, par3, par4, par5, par6);
	}

	@Override
	/**
	 * Schedules a block update from the saved information in a chunk. Called when the chunk is loaded.
	 */
	public void scheduleBlockUpdateFromLoad(int par1, int par2, int par3, int par4, int par5, int par6) {
		this.proxiedWorld.scheduleBlockUpdateFromLoad(par1, par2, par3, par4, par5, par6);
	}

	@Override
	/**
	 * Updates (and cleans up) entities and tile entities
	 */
	public void updateEntities() {
		this.proxiedWorld.updateEntities();
	}

	@Override
	public void addTileEntity(Collection par1Collection) {
		this.proxiedWorld.addTileEntity(par1Collection);
	}

	@Override
	/**
	 * Will update the entity in the world if the chunk the entity is in is currently loaded. Args: entity
	 */
	public void updateEntity(Entity par1Entity) {
		this.proxiedWorld.updateEntity(par1Entity);
	}

	@Override
	/**
	 * Will update the entity in the world if the chunk the entity is in is currently loaded or its forced to update.
	 * Args: entity, forceUpdate
	 */
	public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2) {
		this.proxiedWorld.updateEntityWithOptionalForce(par1Entity, par2);
	}

	@Override
	/**
	 * Returns true if there are no solid, live entities in the specified AxisAlignedBB
	 */
	public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB) {
		return this.proxiedWorld.checkNoEntityCollision(par1AxisAlignedBB);
	}

	@Override
	/**
	 * Returns true if there are no solid, live entities in the specified AxisAlignedBB, excluding the given entity
	 */
	public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB, Entity par2Entity) {
		return this.proxiedWorld.checkNoEntityCollision(par1AxisAlignedBB, par2Entity);
	}

	@Override
	/**
	 * Returns true if there are any blocks in the region constrained by an AxisAlignedBB
	 */
	public boolean checkBlockCollision(AxisAlignedBB par1AxisAlignedBB) {
		return this.proxiedWorld.checkBlockCollision(par1AxisAlignedBB);
	}

	@Override
	/**
	 * Returns if any of the blocks within the aabb are liquids. Args: aabb
	 */
	public boolean isAnyLiquid(AxisAlignedBB par1AxisAlignedBB) {
		return this.proxiedWorld.isAnyLiquid(par1AxisAlignedBB);
	}

	@Override
	/**
	 * Returns whether or not the given bounding box is on fire or not
	 */
	public boolean isBoundingBoxBurning(AxisAlignedBB par1AxisAlignedBB) {
		return this.proxiedWorld.isBoundingBoxBurning(par1AxisAlignedBB);
	}

	@Override
	/**
	 * handles the acceleration of an object whilst in water. Not sure if it is used elsewhere.
	 */
	public boolean handleMaterialAcceleration(AxisAlignedBB par1AxisAlignedBB, Material par2Material, Entity par3Entity) {
		return this.proxiedWorld.handleMaterialAcceleration(par1AxisAlignedBB, par2Material, par3Entity);
	}

	@Override
	/**
	 * Returns true if the given bounding box contains the given material
	 */
	public boolean isMaterialInBB(AxisAlignedBB par1AxisAlignedBB, Material par2Material) {
		return this.proxiedWorld.isMaterialInBB(par1AxisAlignedBB, par2Material);
	}

	@Override
	/**
	 * checks if the given AABB is in the material given. Used while swimming.
	 */
	public boolean isAABBInMaterial(AxisAlignedBB par1AxisAlignedBB, Material par2Material) {
		return this.proxiedWorld.isAABBInMaterial(par1AxisAlignedBB, par2Material);
	}

	@Override
	/**
	 * Creates an explosion. Args: entity, x, y, z, strength
	 */
	public Explosion createExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9) {
		return this.proxiedWorld.createExplosion(par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	/**
	 * returns a new explosion. Does initiation (at time of writing Explosion is not finished)
	 */
	public Explosion newExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10) {
		return this.proxiedWorld.newExplosion(par1Entity, par2, par4, par6, par8, par9, par10);
	}

	@Override
	/**
	 * Gets the percentage of real blocks within within a bounding box, along a specified vector.
	 */
	public float getBlockDensity(Vec3 par1Vec3, AxisAlignedBB par2AxisAlignedBB) {
		return this.proxiedWorld.getBlockDensity(par1Vec3, par2AxisAlignedBB);
	}

	@Override
	/**
	 * If the block in the given direction of the given coordinate is fire, extinguish it. Args: Player, X,Y,Z,
	 * blockDirection
	 */
	public boolean extinguishFire(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5) {
		return this.proxiedWorld.extinguishFire(par1EntityPlayer, par2, par3, par4, par5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * This string is 'All: (number of loaded entities)' Viewable by press ing F3
	 */
	public String getDebugLoadedEntities() {
		return this.proxiedWorld.getDebugLoadedEntities();
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns the name of the current chunk provider, by calling chunkprovider.makeString()
	 */
	public String getProviderName() {
		return this.proxiedWorld.getProviderName();
	}

	@Override
	/**
	 * Returns the TileEntity associated with a given block in X,Y,Z coordinates, or null if no TileEntity exists
	 */
	public TileEntity getBlockTileEntity(int par1, int par2, int par3) {
		return this.proxiedWorld.getBlockTileEntity(par1, par2, par3);
	}

	@Override
	/**
	 * Sets the TileEntity for a given block in X, Y, Z coordinates
	 */
	public void setBlockTileEntity(int par1, int par2, int par3, TileEntity par4TileEntity) {
		this.proxiedWorld.setBlockTileEntity(par1, par2, par3, par4TileEntity);
	}

	@Override
	/**
	 * Removes the TileEntity for a given block in X,Y,Z coordinates
	 */
	public void removeBlockTileEntity(int par1, int par2, int par3) {
		this.proxiedWorld.removeBlockTileEntity(par1, par2, par3);
	}

	@Override
	/**
	 * adds tile entity to despawn list (renamed from markEntityForDespawn)
	 */
	public void markTileEntityForDespawn(TileEntity par1TileEntity) {
		this.proxiedWorld.markTileEntityForDespawn(par1TileEntity);
	}

	@Override
	/**
	 * Returns true if the block at the specified coordinates is an opaque cube. Args: x, y, z
	 */
	public boolean isBlockOpaqueCube(int par1, int par2, int par3) {
		return this.proxiedWorld.isBlockOpaqueCube(par1, par2, par3);
	}

	@Override
	/**
	 * Indicate if a material is a normal solid opaque cube.
	 */
	public boolean isBlockNormalCube(int par1, int par2, int par3) {
		return this.proxiedWorld.isBlockNormalCube(par1, par2, par3);
	}

	@Override
	public boolean func_85174_u(int par1, int par2, int par3) {
		return this.proxiedWorld.func_85174_u(par1, par2, par3);
	}

	@Override
	/**
	 * Returns true if the block at the given coordinate has a solid (buildable) top surface.
	 */
	public boolean doesBlockHaveSolidTopSurface(int par1, int par2, int par3) {
		return this.proxiedWorld.doesBlockHaveSolidTopSurface(par1, par2, par3);
	}

	@Override
	/**
	 * Performs check to see if the block is a normal, solid block, or if the metadata of the block indicates that its
	 * facing puts its solid side upwards. (inverted stairs, for example)
	 */
	@Deprecated //DO NOT USE THIS!!! USE doesBlockHaveSolidTopSurface
	public boolean isBlockTopFacingSurfaceSolid(Block par1Block, int par2) {
		return this.proxiedWorld.isBlockTopFacingSurfaceSolid(par1Block, par2);
	}

	@Override
	/**
	 * Checks if the block is a solid, normal cube. If the chunk does not exist, or is not loaded, it returns the
	 * boolean parameter.
	 */
	public boolean isBlockNormalCubeDefault(int par1, int par2, int par3, boolean par4) {
		return this.proxiedWorld.isBlockNormalCubeDefault(par1, par2, par3, par4);
	}

	@Override
	/**
	 * Called on construction of the World class to setup the initial skylight values
	 */
	public void calculateInitialSkylight() {
		this.proxiedWorld.calculateInitialSkylight();
	}

	@Override
	/**
	 * Set which types of mobs are allowed to spawn (peaceful vs hostile).
	 */
	public void setAllowedSpawnTypes(boolean par1, boolean par2) {
		this.proxiedWorld.setAllowedSpawnTypes(par1, par2);
	}

	@Override
	/**
	 * Runs a single tick for the world
	 */
	public void tick() {
		this.proxiedWorld.tick();
	}

	@Override
	public void calculateInitialWeatherBody() {
		this.proxiedWorld.calculateInitialWeatherBody();
	}

	@Override
	/**
	 * Updates all weather states.
	 */
	protected void updateWeather() {
		ArrayList<String> q = new ArrayList<String>();
		q.add("updateWeather");
		q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.World", new String[]{"func_72979_l"})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]));
		try {
			callable.invoke(this.proxiedWorld);
		} catch (Throwable e) {
		}
	}

	@Override
	public void updateWeatherBody() {
		this.proxiedWorld.updateWeatherBody();
	}

	@Override
	public void toggleRain() {
		this.proxiedWorld.toggleRain();
	}

	@Override
	protected void setActivePlayerChunksAndCheckLight() {
		ArrayList<String> q = new ArrayList<String>();
		q.add("setActivePlayerChunksAndCheckLight");
		q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.World", new String[]{"func_72903_x"})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]));
		try {
			callable.invoke(this.proxiedWorld);
		} catch (Throwable e) {
		}
	}

	@Override
	protected void moodSoundAndLightCheck(int par1, int par2, Chunk par3Chunk) {
		ArrayList<String> q = new ArrayList<String>();
		q.add("moodSoundAndLightCheck");
		q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.World", new String[]{"func_72941_a"})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]), int.class, int.class, Chunk.class);
		try {
			callable.invoke(this.proxiedWorld, par1, par2, par3Chunk);
		} catch (Throwable e) {
		}
	}

	@Override
	/**
	 * plays random cave ambient sounds and runs updateTick on random blocks within each chunk in the vacinity of a
	 * player
	 */
	protected void tickBlocksAndAmbiance() {
		ArrayList<String> q = new ArrayList<String>();
		q.add("tickBlocksAndAmbiance");
		q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.World", new String[]{"func_72893_g"})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]));
		try {
			callable.invoke(this.proxiedWorld);
		} catch (Throwable e) {
		}
	}

	@Override
	/**
	 * checks to see if a given block is both water and is cold enough to freeze
	 */
	public boolean isBlockFreezable(int par1, int par2, int par3) {
		return this.proxiedWorld.isBlockFreezable(par1, par2, par3);
	}

	@Override
	/**
	 * checks to see if a given block is both water and has at least one immediately adjacent non-water block
	 */
	public boolean isBlockFreezableNaturally(int par1, int par2, int par3) {
		return this.proxiedWorld.isBlockFreezableNaturally(par1, par2, par3);
	}

	@Override
	/**
	 * checks to see if a given block is both water, and cold enough to freeze - if the par4 boolean is set, this will
	 * only return true if there is a non-water block immediately adjacent to the specified block
	 */
	public boolean canBlockFreeze(int par1, int par2, int par3, boolean par4) {
		return this.proxiedWorld.canBlockFreeze(par1, par2, par3, par4);
	}

	@Override
	public boolean canBlockFreezeBody(int par1, int par2, int par3, boolean par4) {
		return this.proxiedWorld.canBlockFreezeBody(par1, par2, par3, par4);
	}

	@Override
	/**
	 * Tests whether or not snow can be placed at a given location
	 */
	public boolean canSnowAt(int par1, int par2, int par3) {
		return this.proxiedWorld.canSnowAt(par1, par2, par3);
	}

	@Override
	public boolean canSnowAtBody(int par1, int par2, int par3) {
		return this.proxiedWorld.canSnowAtBody(par1, par2, par3);
	}

	@Override
	public void updateAllLightTypes(int par1, int par2, int par3) {
		this.proxiedWorld.updateAllLightTypes(par1, par2, par3);
	}

	@Override
	public void updateLightByType(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4) {
		this.proxiedWorld.updateLightByType(par1EnumSkyBlock, par2, par3, par4);
	}

	@Override
	/**
	 * Runs through the list of updates to run and ticks them
	 */
	public boolean tickUpdates(boolean par1) {
		return this.proxiedWorld.tickUpdates(par1);
	}

	@Override
	public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2) {
		return this.proxiedWorld.getPendingBlockUpdates(par1Chunk, par2);
	}

	@Override
	/**
	 * Will get all entities within the specified AABB excluding the one passed into it. Args: entityToExclude, aabb
	 */
	public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB) {
		return this.proxiedWorld.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB);
	}

	@Override
	public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3IEntitySelector) {
		return this.proxiedWorld.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB, par3IEntitySelector);
	}

	@Override
	/**
	 * Returns all entities of the specified class type which intersect with the AABB. Args: entityClass, aabb
	 */
	public List getEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB) {
		return this.proxiedWorld.getEntitiesWithinAABB(par1Class, par2AxisAlignedBB);
	}

	@Override
	public List selectEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3IEntitySelector) {
		return this.proxiedWorld.selectEntitiesWithinAABB(par1Class, par2AxisAlignedBB, par3IEntitySelector);
	}

	@Override
	public Entity findNearestEntityWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, Entity par3Entity) {
		return this.proxiedWorld.findNearestEntityWithinAABB(par1Class, par2AxisAlignedBB, par3Entity);
	}

	@Override
	/**
	 * Returns the Entity with the given ID, or null if it doesn't exist in this World.
	 */
	public Entity getEntityByID(int i) {
		return this.proxiedWorld.getEntityByID(i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Accessor for world Loaded Entity List
	 */
	public List getLoadedEntityList() {
		return this.proxiedWorld.getLoadedEntityList();
	}

	@Override

	/**
	 * marks the chunk that contains this tilentity as modified and then calls worldAccesses.doNothingWithTileEntity
	 */
	public void updateTileEntityChunkAndDoNothing(int par1, int par2, int par3, TileEntity par4TileEntity) {
		this.proxiedWorld.updateTileEntityChunkAndDoNothing(par1, par2, par3, par4TileEntity);
	}

	@Override
	/**
	 * Counts how many entities of an entity class exist in the world. Args: entityClass
	 */
	public int countEntities(Class par1Class) {
		return this.proxiedWorld.countEntities(par1Class);
	}

	@Override
	/**
	 * adds entities to the loaded entities list, and loads thier skins.
	 */
	public void addLoadedEntities(List par1List) {
		this.proxiedWorld.addLoadedEntities(par1List);
	}

	@Override
	/**
	 * Adds a list of entities to be unloaded on the next pass of World.updateEntities()
	 */
	public void unloadEntities(List par1List) {
		this.proxiedWorld.unloadEntities(par1List);
	}

	@Override
	/**
	 * Returns true if the given Entity can be placed on the given side of the given block position.
	 */
	public boolean canPlaceEntityOnSide(int par1, int par2, int par3, int par4, boolean par5, int par6, Entity par7Entity, ItemStack par8ItemStack) {
		return this.proxiedWorld.canPlaceEntityOnSide(par1, par2, par3, par4, par5, par6, par7Entity, par8ItemStack);
	}

	@Override
	public PathEntity getPathEntityToEntity(Entity par1Entity, Entity par2Entity, float par3, boolean par4, boolean par5, boolean par6, boolean par7) {
		return this.proxiedWorld.getPathEntityToEntity(par1Entity, par2Entity, par3, par4, par5, par6, par7);
	}

	@Override
	public PathEntity getEntityPathToXYZ(Entity par1Entity, int par2, int par3, int par4, float par5, boolean par6, boolean par7, boolean par8, boolean par9) {
		return this.proxiedWorld.getEntityPathToXYZ(par1Entity, par2, par3, par4, par5, par6, par7, par8, par9);
	}

	@Override
	/**
	 * Is this block powering in the specified direction Args: x, y, z, direction
	 */
	public int isBlockProvidingPowerTo(int par1, int par2, int par3, int par4) {
		return this.proxiedWorld.isBlockProvidingPowerTo(par1, par2, par3, par4);
	}

	@Override
	/**
	 * Returns the highest redstone signal strength powering the given block. Args: X, Y, Z.
	 */
	public int getBlockPowerInput(int par1, int par2, int par3) {
		return this.proxiedWorld.getBlockPowerInput(par1, par2, par3);
	}

	@Override
	/**
	 * Returns the indirect signal strength being outputted by the given block in the *opposite* of the given direction.
	 * Args: X, Y, Z, direction
	 */
	public boolean getIndirectPowerOutput(int par1, int par2, int par3, int par4) {
		return this.proxiedWorld.getIndirectPowerOutput(par1, par2, par3, par4);
	}

	@Override
	/**
	 * Gets the power level from a certain block face.  Args: x, y, z, direction
	 */
	public int getIndirectPowerLevelTo(int par1, int par2, int par3, int par4) {
		return this.proxiedWorld.getIndirectPowerLevelTo(par1, par2, par3, par4);
	}

	@Override
	/**
	 * Used to see if one of the blocks next to you or your block is getting power from a neighboring block. Used by
	 * items like TNT or Doors so they don't have redstone going straight into them.  Args: x, y, z
	 */
	public boolean isBlockIndirectlyGettingPowered(int par1, int par2, int par3) {
		return this.proxiedWorld.isBlockIndirectlyGettingPowered(par1, par2, par3);
	}

	@Override
	public int getStrongestIndirectPower(int par1, int par2, int par3) {
		return this.proxiedWorld.getStrongestIndirectPower(par1, par2, par3);
	}

	@Override
	/**
	 * Gets the closest player to the entity within the specified distance (if distance is less than 0 then ignored).
	 * Args: entity, dist
	 */
	public EntityPlayer getClosestPlayerToEntity(Entity par1Entity, double par2) {
		return this.proxiedWorld.getClosestPlayerToEntity(par1Entity, par2);
	}

	@Override
	/**
	 * Gets the closest player to the point within the specified distance (distance can be set to less than 0 to not
	 * limit the distance). Args: x, y, z, dist
	 */
	public EntityPlayer getClosestPlayer(double par1, double par3, double par5, double par7) {
		return this.proxiedWorld.getClosestPlayer(par1, par3, par5, par7);
	}

	@Override
	/**
	 * Returns the closest vulnerable player to this entity within the given radius, or null if none is found
	 */
	public EntityPlayer getClosestVulnerablePlayerToEntity(Entity par1Entity, double par2) {
		return this.proxiedWorld.getClosestVulnerablePlayerToEntity(par1Entity, par2);
	}

	@Override
	/**
	 * Returns the closest vulnerable player within the given radius, or null if none is found.
	 */
	public EntityPlayer getClosestVulnerablePlayer(double par1, double par3, double par5, double par7) {
		return this.proxiedWorld.getClosestVulnerablePlayer(par1, par3, par5, par7);
	}

	@Override
	/**
	 * Find a player by name in this world.
	 */
	public EntityPlayer getPlayerEntityByName(String par1Str) {
		return this.proxiedWorld.getPlayerEntityByName(par1Str);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * If on MP, sends a quitting packet.
	 */
	public void sendQuittingDisconnectingPacket() {
		this.proxiedWorld.sendQuittingDisconnectingPacket();
	}

	@Override
	/**
	 * Checks whether the session lock file was modified by another process
	 */
	public void checkSessionLock() throws MinecraftException {
		this.proxiedWorld.checkSessionLock();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_82738_a(long par1) {
		this.proxiedWorld.func_82738_a(par1);
	}

	@Override
	/**
	 * Retrieve the world seed from level.dat
	 */
	public long getSeed() {
		return this.proxiedWorld.getSeed();
	}

	@Override
	public long getTotalWorldTime() {
		return this.proxiedWorld.getTotalWorldTime();
	}

	@Override
	public long getWorldTime() {
		return this.proxiedWorld.getWorldTime();
	}

	@Override
	/**
	 * Sets the world time.
	 */
	public void setWorldTime(long par1) {
		this.proxiedWorld.setWorldTime(par1);
	}

	@Override
	/**
	 * Returns the coordinates of the spawn point
	 */
	public ChunkCoordinates getSpawnPoint() {
		return this.proxiedWorld.getSpawnPoint();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setSpawnLocation(int par1, int par2, int par3) {
		this.proxiedWorld.setSpawnLocation(par1, par2, par3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * spwans an entity and loads surrounding chunks
	 */
	public void joinEntityInSurroundings(Entity par1Entity) {
		this.proxiedWorld.joinEntityInSurroundings(par1Entity);
	}

	@Override
	/**
	 * Called when checking if a certain block can be mined or not. The 'spawn safe zone' check is located here.
	 */
	public boolean canMineBlock(EntityPlayer par1EntityPlayer, int par2, int par3, int par4) {
		return this.proxiedWorld.canMineBlock(par1EntityPlayer, par2, par3, par4);
	}

	@Override
	public boolean canMineBlockBody(EntityPlayer par1EntityPlayer, int par2, int par3, int par4) {
		return this.proxiedWorld.canMineBlockBody(par1EntityPlayer, par2, par3, par4);
	}

	@Override
	/**
	 * sends a Packet 38 (Entity Status) to all tracked players of that entity
	 */
	public void setEntityState(Entity par1Entity, byte par2) {
		this.proxiedWorld.setEntityState(par1Entity, par2);
	}

	@Override
	/**
	 * gets the IChunkProvider this world uses.
	 */
	public IChunkProvider getChunkProvider() {
		return this.proxiedWorld.getChunkProvider();
	}

	@Override
	/**
	 * Adds a block event with the given Args to the blockEventCache. During the next tick(), the block specified will
	 * have its onBlockEvent handler called with the given parameters. Args: X,Y,Z, BlockID, EventID, EventParameter
	 */
	public void addBlockEvent(int par1, int par2, int par3, int par4, int par5, int par6) {
		this.proxiedWorld.addBlockEvent(par1, par2, par3, par4, par5, par6);
	}

	@Override
	/**
	 * Returns this world's current save handler
	 */
	public ISaveHandler getSaveHandler() {
		return this.proxiedWorld.getSaveHandler();
	}

	@Override
	/**
	 * Gets the World's WorldInfo instance
	 */
	public WorldInfo getWorldInfo() {
		return this.proxiedWorld.getWorldInfo();
	}

	@Override
	/**
	 * Gets the GameRules instance.
	 */
	public GameRules getGameRules() {
		return this.proxiedWorld.getGameRules();
	}

	@Override
	/**
	 * Updates the flag that indicates whether or not all players in the world are sleeping.
	 */
	public void updateAllPlayersSleepingFlag() {
		this.proxiedWorld.updateAllPlayersSleepingFlag();
	}

	@Override
	public float getWeightedThunderStrength(float par1) {
		return this.proxiedWorld.getWeightedThunderStrength(par1);
	}

	@Override
	/**
	 * Not sure about this actually. Reverting this one myself.
	 */
	public float getRainStrength(float par1) {
		return this.proxiedWorld.getRainStrength(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setRainStrength(float par1) {
		this.proxiedWorld.setRainStrength(par1);
	}

	@Override
	/**
	 * Returns true if the current thunder strength (weighted with the rain strength) is greater than 0.9
	 */
	public boolean isThundering() {
		return this.proxiedWorld.isThundering();
	}

	@Override
	/**
	 * Returns true if the current rain strength is greater than 0.2
	 */
	public boolean isRaining() {
		return this.proxiedWorld.isRaining();
	}

	@Override
	public boolean canLightningStrikeAt(int par1, int par2, int par3) {
		return this.proxiedWorld.canLightningStrikeAt(par1, par2, par3);
	}

	@Override
	/**
	 * Checks to see if the biome rainfall values for a given x,y,z coordinate set are extremely high
	 */
	public boolean isBlockHighHumidity(int par1, int par2, int par3) {
		return this.proxiedWorld.isBlockHighHumidity(par1, par2, par3);
	}

	@Override
	/**
	 * Assigns the given String id to the given MapDataBase using the MapStorage, removing any existing ones of the same
	 * id.
	 */
	public void setItemData(String par1Str, WorldSavedData par2WorldSavedData) {
		this.proxiedWorld.setItemData(par1Str, par2WorldSavedData);
	}

	@Override
	/**
	 * Loads an existing MapDataBase corresponding to the given String id from disk using the MapStorage, instantiating
	 * the given Class, or returns null if none such file exists. args: Class to instantiate, String dataid
	 */
	public WorldSavedData loadItemData(Class par1Class, String par2Str) {
		return this.proxiedWorld.loadItemData(par1Class, par2Str);
	}

	@Override
	/**
	 * Returns an unique new data id from the MapStorage for the given prefix and saves the idCounts map to the
	 * 'idcounts' file.
	 */
	public int getUniqueDataId(String par1Str) {
		return this.proxiedWorld.getUniqueDataId(par1Str);
	}

	@Override
	public void func_82739_e(int par1, int par2, int par3, int par4, int par5) {
		this.proxiedWorld.func_82739_e(par1, par2, par3, par4, par5);
	}

	@Override
	/**
	 * See description for playAuxSFX.
	 */
	public void playAuxSFX(int par1, int par2, int par3, int par4, int par5) {
		this.proxiedWorld.playAuxSFX(par1, par2, par3, par4, par5);
	}

	@Override
	/**
	 * See description for playAuxSFX.
	 */
	public void playAuxSFXAtEntity(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6) {
		this.proxiedWorld.playAuxSFXAtEntity(par1EntityPlayer, par2, par3, par4, par5, par6);
	}

	@Override
	/**
	 * Returns current world height.
	 */
	public int getHeight() {
		return this.proxiedWorld.getHeight();
	}

	@Override
	/**
	 * Returns current world height.
	 */
	public int getActualHeight() {
		return this.proxiedWorld.getActualHeight();
	}

	@Override
	public IUpdatePlayerListBox func_82735_a(EntityMinecart par1EntityMinecart) {
		return this.proxiedWorld.func_82735_a(par1EntityMinecart);
	}

	@Override
	/**
	 * puts the World Random seed to a specific state dependant on the inputs
	 */
	public Random setRandomSeed(int par1, int par2, int par3) {
		return this.proxiedWorld.setRandomSeed(par1, par2, par3);
	}

	@Override
	/**
	 * Returns the location of the closest structure of the specified type. If not found returns null.
	 */
	public ChunkPosition findClosestStructure(String par1Str, int par2, int par3, int par4) {
		return this.proxiedWorld.findClosestStructure(par1Str, par2, par3, par4);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * set by !chunk.getAreLevelsEmpty
	 */
	public boolean extendedLevelsInChunkCache() {
		return this.proxiedWorld.extendedLevelsInChunkCache();
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns horizon height for use in rendering the sky.
	 */
	public double getHorizon() {
		return this.proxiedWorld.getHorizon();
	}

	@Override
	/**
	 * Adds some basic stats of the world to the given crash report.
	 */
	public CrashReportCategory addWorldInfoToCrashReport(CrashReport par1CrashReport) {
		return this.proxiedWorld.addWorldInfoToCrashReport(par1CrashReport);
	}

	@Override
	/**
	 * Starts (or continues) destroying a block with given ID at the given coordinates for the given partially destroyed
	 * value
	 */
	public void destroyBlockInWorldPartially(int par1, int par2, int par3, int par4, int par5) {
		this.proxiedWorld.destroyBlockInWorldPartially(par1, par2, par3, par4, par5);
	}

	@Override
	/**
	 * Return the Vec3Pool object for this world.
	 */
	public Vec3Pool getWorldVec3Pool() {
		return this.proxiedWorld.getWorldVec3Pool();
	}

	@Override
	/**
	 * returns a calendar object containing the current date
	 */
	public Calendar getCurrentDate() {
		return this.proxiedWorld.getCurrentDate();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_92088_a(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13NBTTagCompound) {
		this.proxiedWorld.func_92088_a(par1, par3, par5, par7, par9, par11, par13NBTTagCompound);
	}

	@Override
	public Scoreboard getScoreboard() {
		return this.proxiedWorld.getScoreboard();
	}

	@Override
	public void func_96440_m(int par1, int par2, int par3, int par4) {
		this.proxiedWorld.func_96440_m(par1, par2, par3, par4);
	}

	@Override
	public ILogAgent getWorldLogAgent() {
		return this.proxiedWorld.getWorldLogAgent();
	}

	@Override
	/**
	 * Adds a single TileEntity to the world.
	 * @param entity The TileEntity to be added.
	 */
	public void addTileEntity(TileEntity entity) {
		this.proxiedWorld.addTileEntity(entity);
	}

	@Override
	/**
	 * Determine if the given block is considered solid on the
	 * specified side.  Used by placement logic.
	 *
	 * @param x Block X Position
	 * @param y Block Y Position
	 * @param z Block Z Position
	 * @param side The Side in question
	 * @return True if the side is solid
	 */
	public boolean isBlockSolidOnSide(int x, int y, int z, ForgeDirection side) {
		return this.proxiedWorld.isBlockSolidOnSide(x, y, z, side);
	}

	@Override
	/**
	 * Determine if the given block is considered solid on the
	 * specified side.  Used by placement logic.
	 *
	 * @param x Block X Position
	 * @param y Block Y Position
	 * @param z Block Z Position
	 * @param side The Side in question
	 * @param _default The defult to return if the block doesn't exist.
	 * @return True if the side is solid
	 */
	public boolean isBlockSolidOnSide(int x, int y, int z, ForgeDirection side, boolean _default) {
		return this.proxiedWorld.isBlockSolidOnSide(x, y, z, side, _default);
	}

	@Override
	/**
	 * Get the persistent chunks for this world
	 *
	 * @return
	 */
	public ImmutableSetMultimap<ChunkCoordIntPair, Ticket> getPersistentChunks()
	{
		return this.proxiedWorld.getPersistentChunks();
	}

	@Override
	/**
	 * Readded as it was removed, very useful helper function
	 *
	 * @param x X position
	 * @param y Y Position
	 * @param z Z Position
	 * @return The blocks light opacity
	 */
	public int getBlockLightOpacity(int x, int y, int z) {
		return this.proxiedWorld.getBlockLightOpacity(x, y, z);
	}

	@Override
	/**
	 * Returns a count of entities that classify themselves as the specified creature type.
	 */
	public int countEntities(EnumCreatureType type, boolean forSpawnCount) {
		return this.proxiedWorld.countEntities(type, forSpawnCount);
	}

    @Override
    /**
     * only spawns creatures allowed by the chunkProvider
     */
    public SpawnListEntry spawnRandomCreature(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        return this.proxiedWorld.spawnRandomCreature(par1EnumCreatureType, par2, par3, par4);
    }

    @Override
    protected void wakeAllPlayers()
    {
		ArrayList<String> q = new ArrayList<String>();
		q.add("wakeAllPlayers");
		q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.WorldServer", new String[]{"func_73053_d"})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]));
		try {
			callable.invoke(this.proxiedWorld);
		} catch (Throwable e) {
		}
    }

    @Override
    public boolean areAllPlayersAsleep()
    {
        return this.proxiedWorld.areAllPlayersAsleep();
    }

    @Override
    /**
     * Resets the updateEntityTick field to 0
     */
    public void resetUpdateEntityTick()
    {
        this.proxiedWorld.resetUpdateEntityTick();
    }

    @Override
    /**
     * direct call to super.updateEntityWithOptionalForce
     */
    public void uncheckedUpdateEntity(Entity par1Entity, boolean par2)
    {
        this.proxiedWorld.uncheckedUpdateEntity(par1Entity, par2);
    }

    @Override
    /**
     * pars: min x,y,z , max x,y,z
     */
    public List getAllTileEntityInBox(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        return this.proxiedWorld.getAllTileEntityInBox(par1, par2, par3, par4, par5, par6);
    }

    @Override
    /**
     * creates a spawn position at random within 256 blocks of 0,0
     */
    protected void createSpawnPosition(WorldSettings par1WorldSettings)
    {
		ArrayList<String> q = new ArrayList<String>();
		q.add("createSpawnPosition");
		q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.WorldServer", new String[]{"func_73052_b"})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]), WorldSettings.class);
		try {
			callable.invoke(this.proxiedWorld);
		} catch (Throwable e) {
		}
    }

    @Override
    /**
     * Creates the bonus chest in the world.
     */
    protected void createBonusChest()
    {
		ArrayList<String> q = new ArrayList<String>();
		q.add("createBonusChest");
		q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.WorldServer", new String[]{"func_73047_i"})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]));
		try {
			callable.invoke(this.proxiedWorld);
		} catch (Throwable e) {
		}
    }

    @Override
    /**
     * Gets the hard-coded portal location to use when entering this dimension.
     */
    public ChunkCoordinates getEntrancePortalLocation()
    {
        return this.proxiedWorld.getEntrancePortalLocation();
    }

    @Override
    /**
     * Saves all chunks to disk while updating progress bar.
     */
    public void saveAllChunks(boolean par1, IProgressUpdate par2IProgressUpdate) throws MinecraftException
    {
    	this.proxiedWorld.saveAllChunks(par1, par2IProgressUpdate);
    }

    @Override
    public void func_104140_m()
    {
        this.proxiedWorld.func_104140_m();
    }

    @Override
    /**
     * Saves the chunks to disk.
     */
    protected void saveLevel() throws MinecraftException
    {
		ArrayList<String> q = new ArrayList<String>();
		q.add("saveLevel");
		q.addAll(Arrays.asList(ObfuscationReflectionHelper.remapFieldNames("net.minecraft.world.WorldServer", new String[]{"func_73042_a"})));
		Method callable = ReflectionHelper.findMethod(World.class, this.proxiedWorld, q.toArray(new String[q.size()]));
		try {
			callable.invoke(this.proxiedWorld);
		} catch (Throwable e) {
			if (e instanceof MinecraftException)
				throw (MinecraftException)e;
		}
    }

    @Override
    /**
     * Syncs all changes to disk and wait for completion.
     */
    public void flush()
    {
        this.proxiedWorld.flush();
    }

    @Override
    /**
     * Gets the MinecraftServer.
     */
    public MinecraftServer getMinecraftServer()
    {
        return this.proxiedWorld.getMinecraftServer();
    }

    @Override
    /**
     * Gets the EntityTracker
     */
    public EntityTracker getEntityTracker()
    {
        return this.proxiedWorld.getEntityTracker();
    }

    @Override
    public PlayerManager getPlayerManager()
    {
        return this.proxiedWorld.getPlayerManager();
    }

    @Override
    public Teleporter getDefaultTeleporter()
    {
        return this.proxiedWorld.getDefaultTeleporter();
    }

    @Override
    public File getChunkSaveLocation()
    {
        return this.proxiedWorld.getChunkSaveLocation();
    }
}
