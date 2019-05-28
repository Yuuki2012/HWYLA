package mcp.mobius.waila.api;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * The Accessor is used to get some basic data out of the game without having to
 * request direct access to the game engine.<br>
 * It will also return things that are unmodified by the overriding systems
 * (like getStack).<br>
 * Common accessor for both Entity and Block/TileEntity.<br>
 * Available data depends on what it is called upon (ie : getEntity() will
 * return null if looking at a block, etc).<br>
 */
public interface ICommonAccessor
{
	World getWorld();

	PlayerEntity getPlayer();

	Block getBlock();

	Identifier getBlockId();

	BlockEntity getBlockEntity();

	Entity getEntity();

	BlockPos getPosition();

	Vec3d getRenderingPosition();

	CompoundTag getServerData();

	double getPartialFrame();

	Direction getSide();

	ItemStack getStack();
}