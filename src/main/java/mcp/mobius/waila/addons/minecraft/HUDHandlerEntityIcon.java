package mcp.mobius.waila.addons.minecraft;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.LeadKnotEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class HUDHandlerEntityIcon implements IEntityComponentProvider
{
	public static final IEntityComponentProvider INSTANCE = new HUDHandlerEntityIcon();

	@Override
	public ItemStack getDisplayItem(IEntityAccessor accessor, IPluginConfig config)
	{
		if (accessor.getEntity() instanceof AbstractMinecartEntity)
		{
			AbstractMinecartEntity minecartEntity = (AbstractMinecartEntity) accessor.getEntity();
			AbstractMinecartEntity.Type type = minecartEntity.getMinecartType();
			switch (type)
			{
			case RIDEABLE:
				return new ItemStack(Items.MINECART);
			case CHEST:
				return new ItemStack(Items.CHEST_MINECART);
			case FURNACE:
				return new ItemStack(Items.FURNACE_MINECART);
			case HOPPER:
				return new ItemStack(Items.HOPPER_MINECART);
			case TNT:
				return new ItemStack(Items.TNT_MINECART);
			case COMMAND_BLOCK:
				return new ItemStack(Items.COMMAND_BLOCK_MINECART);
			default:
				break;
			}
		}
		else if (accessor.getEntity() instanceof ItemFrameEntity)
		{
			ItemStack held = ((ItemFrameEntity) accessor.getEntity()).getHeldItemStack();
			return held.isEmpty() ? new ItemStack(Items.ITEM_FRAME) : held;
		}
		else if (accessor.getEntity() instanceof PaintingEntity)
		{
			return new ItemStack(Items.PAINTING);
		}
		else if (accessor.getEntity() instanceof LeadKnotEntity)
		{
			return new ItemStack(Items.LEAD);
		}
		return ItemStack.EMPTY;
	}
}