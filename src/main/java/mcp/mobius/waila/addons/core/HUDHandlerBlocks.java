package mcp.mobius.waila.addons.core;

import com.google.common.base.Strings;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.*;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.block.BlockState;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormat;

import java.util.List;

@SuppressWarnings("unchecked")
public class HUDHandlerBlocks implements IComponentProvider
{
	static final IComponentProvider INSTANCE = new HUDHandlerBlocks();
	static final Identifier OBJECT_NAME_TAG = new Identifier(Waila.MODID, "object_name");
	static final Identifier REGISTRY_NAME_TAG = new Identifier(Waila.MODID, "registry_name");
	static final Identifier MOD_NAME_TAG = new Identifier(Waila.MODID, "mod_name");

	@Override
	public void appendHead(List<TextComponent> tooltip, IDataAccessor accessor, IPluginConfig config)
	{
		if (accessor.getBlockState().getMaterial().isLiquid())
			return;

		((ITaggableList<Identifier, TextComponent>) tooltip).setTag(OBJECT_NAME_TAG, new TextComponent(String.format(Waila.CONFIG.get().getFormatting().getBlockName(), I18n.translate(accessor.getStack().getTranslationKey()))));

		if (config.get(PluginCore.CONFIG_SHOW_REGISTRY))
			((ITaggableList<Identifier, TextComponent>) tooltip).setTag(REGISTRY_NAME_TAG, (TextComponent) new TextComponent(Registry.BLOCK.getId(accessor.getBlock()).toString()).setStyle(new Style().setColor(ChatFormat.GRAY)));
	}

	@Override
	public void appendBody(List<TextComponent> tooltip, IDataAccessor accessor, IPluginConfig config)
	{
		if (config.get(PluginCore.CONFIG_SHOW_STATES))
		{
			BlockState state = accessor.getBlockState();
			state.getProperties().forEach(p ->
			{
				Comparable<?> value = state.get(p);

				TextComponent valueText = (TextComponent) new TextComponent(value.toString()).setStyle(new Style().setColor(p instanceof BooleanProperty ? value == Boolean.TRUE ? ChatFormat.GREEN : ChatFormat.RED : ChatFormat.RESET));

				tooltip.add((TextComponent) new TextComponent(p.getName() + ":").append(valueText));
			});
		}
	}

	@Override
	public void appendTail(List<TextComponent> tooltip, IDataAccessor accessor, IPluginConfig config)
	{
		String modName = ModIdentification.getModInfo(accessor.getStack().getItem()).getName();
		if (!Strings.isNullOrEmpty(modName))
		{
			modName = String.format(Waila.CONFIG.get().getFormatting().getModName(), modName);

			((ITaggableList<Identifier, TextComponent>) tooltip).setTag(MOD_NAME_TAG, new TextComponent(modName));
		}
	}
}