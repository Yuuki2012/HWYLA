package mcp.mobius.waila.addons.minecraft;

import mcp.mobius.waila.api.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.world.World;

import java.util.List;

public class HUDHandlerVanilla implements IComponentProvider, IServerDataProvider<BlockEntity>
{
	static final HUDHandlerVanilla INSTANCE = new HUDHandlerVanilla();

	@Override
	public ItemStack getStack(IDataAccessor accessor, IPluginConfig config)
	{
		if (config.get(PluginMinecraft.CONFIG_HIDE_SILVERFISH) && accessor.getBlock() instanceof InfestedBlock)
			return new ItemStack(((InfestedBlock) accessor.getBlock()).getRegularBlock().asItem());

		if (accessor.getBlock() == Blocks.WHEAT)
			return new ItemStack(Items.WHEAT);

		if (accessor.getBlock() == Blocks.BEETROOTS)
			return new ItemStack(Items.BEETROOT);

		return ItemStack.EMPTY;
	}

	@Override
	public void appendHead(List<TextComponent> tooltip, IDataAccessor accessor, IPluginConfig config)
	{
		if (accessor.getBlock() == Blocks.SPAWNER && config.get(PluginMinecraft.CONFIG_SPAWNER_TYPE))
		{
			MobSpawnerBlockEntity spawner = (MobSpawnerBlockEntity) accessor.getBlockEntity();

			tooltip.set(0, (TextComponent) new TranslatableComponent(accessor.getBlock().getTranslationKey()).append(new TextComponent(" (")).append(spawner.getLogic().getRenderedEntity().getDisplayName()).append(new TextComponent(")")));
		}
	}

	@Override
	public void appendBody(List<TextComponent> tooltip, IDataAccessor accessor, IPluginConfig config)
	{
		if (config.get(PluginMinecraft.CONFIG_CROP_PROGRESS))
		{
			if (accessor.getBlock() instanceof CropBlock)
			{
				CropBlock crop = (CropBlock) accessor.getBlock();

				addMaturityTooltip(tooltip, accessor.getBlockState().get(crop.getAgeProperty()) / (float) crop.getMaxAge());
			}
			else if (accessor.getBlock() == Blocks.MELON_STEM || accessor.getBlock() == Blocks.PUMPKIN_STEM)
			{
				addMaturityTooltip(tooltip, accessor.getBlockState().get(Properties.AGE_7) / 7F);
			}
			else if (accessor.getBlock() == Blocks.COCOA)
			{
				addMaturityTooltip(tooltip, accessor.getBlockState().get(Properties.AGE_2) / 2.0F);
			}
			else if (accessor.getBlock() == Blocks.SWEET_BERRY_BUSH)
			{
				addMaturityTooltip(tooltip, accessor.getBlockState().get(Properties.AGE_3) / 3.0F);
			}
		}

		if (config.get(PluginMinecraft.CONFIG_LEVER) && accessor.getBlock() instanceof LeverBlock)
		{
			boolean active = accessor.getBlockState().get(Properties.POWERED);

			tooltip.add((TextComponent) new TranslatableComponent("tooltip.waila.state", new TranslatableComponent("tooltip.waila.state_" + (active ? "on" : "off"))).append(""));

			return;
		}

		if (config.get(PluginMinecraft.CONFIG_REPEATER) && accessor.getBlock() == Blocks.REPEATER)
		{
			int delay = accessor.getBlockState().get(Properties.DELAY);

			tooltip.add((TextComponent) new TranslatableComponent("waila.tooltip.delay", delay).append(""));

			return;
		}

		if (config.get(PluginMinecraft.CONFIG_COMPARATOR) && accessor.getBlock() == Blocks.COMPARATOR)
		{
			ComparatorMode mode = accessor.getBlockState().get(Properties.COMPARATOR_MODE);

			tooltip.add((TextComponent) new TranslatableComponent("tooltip.waila.mode", new TranslatableComponent("tooltip.waila.mode_." + (mode == ComparatorMode.COMPARE ? "comparator" : "subtractor"))).append(""));

			return;
		}

		if (config.get(PluginMinecraft.CONFIG_REDSTONE) && accessor.getBlock() == Blocks.REDSTONE_WIRE)
		{
			tooltip.add((TextComponent) new TranslatableComponent("tooltip.waila.power", accessor.getBlockState().get(Properties.POWER)).append(""));

			return;
		}

		if (config.get(PluginMinecraft.CONFIG_JUKEBOX) && accessor.getBlock() == Blocks.JUKEBOX)
		{
			if (accessor.getServerData().containsKey("record"))
				tooltip.add((TextComponent) new TranslatableComponent("record.nowPlaying", TextComponent.Serializer.fromJsonString(accessor.getServerData().getString("record"))).append(""));
			else
				tooltip.add((TextComponent) new TranslatableComponent("tooltip.waila.empty").append(""));
		}
	}

	@Override
	public void appendServerData(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity)
	{
		if (blockEntity instanceof JukeboxBlockEntity)
		{
			JukeboxBlockEntity jukebox = (JukeboxBlockEntity) blockEntity;

			data.putString("record", TextComponent.Serializer.toJsonString(jukebox.getRecord().getDisplayName()));
		}
	}

	private static void addMaturityTooltip(List<TextComponent> tooltip, float growthValue)
	{
		growthValue *= 100.0F;

		if (growthValue < 100.0F)
			tooltip.add((TextComponent) new TranslatableComponent("tooltip.waila.crop_growth", String.format("%.0f%%", growthValue)).append(""));
		else
			tooltip.add((TextComponent) new TranslatableComponent("tooltip.waila.crop_growth", new TranslatableComponent("tooltip.waila.crop_mature")).append(""));
	}
}