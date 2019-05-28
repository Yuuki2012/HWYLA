package mcp.mobius.waila.gui;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.impl.config.WailaConfig;
import mcp.mobius.waila.gui.config.*;
import mcp.mobius.waila.gui.config.value.OptionsEntryValueBoolean;
import mcp.mobius.waila.gui.config.value.OptionsEntryValueCycle;
import mcp.mobius.waila.gui.config.value.OptionsEntryValueEnum;
import mcp.mobius.waila.gui.config.value.OptionsEntryValueInput;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.SystemUtil;
import org.apache.commons.lang3.StringEscapeUtils;

public class GuiConfigWaila extends GuiOptions
{

	public GuiConfigWaila(Screen parent)
	{
		super(parent, new TranslatableComponent("gui.waila.configuration", Waila.NAME), Waila.CONFIG::save, Waila.CONFIG::invalidate);
	}

	@Override
	public OptionsListWidget getOptions()
	{
		OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30, Waila.CONFIG::save);
		options.add(new OptionsEntryButton(
				SystemUtil.createTranslationKey("config", new Identifier(Waila.MODID, "general")),
				new ButtonWidget(0, 0, 100, 20, null, w ->
				{
					minecraft.openScreen(new GuiOptions(GuiConfigWaila.this, new TranslatableComponent(SystemUtil.createTranslationKey("config", new Identifier(Waila.MODID, "general"))))
					{
						@Override
						public OptionsListWidget getOptions()
						{
							OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);
							options.add(new OptionsEntryValueBoolean(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "display_tooltip")),
									Waila.CONFIG.get().getGeneral().shouldDisplayTooltip(),
									val -> Waila.CONFIG.get().getGeneral().setDisplayTooltip(val)));
							options.add(new OptionsEntryValueBoolean(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "display_fluids")),
									Waila.CONFIG.get().getGeneral().shouldDisplayFluids(),
									val -> Waila.CONFIG.get().getGeneral().setDisplayFluids(val)));
							options.add(new OptionsEntryValueBoolean(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "sneaky_details")),
									Waila.CONFIG.get().getGeneral().shouldShiftForDetails(),
									val -> Waila.CONFIG.get().getGeneral().setShiftForDetails(val)));
							options.add(new OptionsEntryValueEnum<>(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "display_mode")),
									WailaConfig.DisplayMode.values(), Waila.CONFIG.get().getGeneral().getDisplayMode(),
									val -> Waila.CONFIG.get().getGeneral().setDisplayMode(val)));
							options.add(new OptionsEntryValueBoolean(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "hide_from_players")),
									Waila.CONFIG.get().getGeneral().shouldHideFromPlayerList(),
									val -> Waila.CONFIG.get().getGeneral().setHideFromPlayerList(val)));
							options.add(new OptionsEntryValueBoolean(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "hide_from_debug")),
									Waila.CONFIG.get().getGeneral().shouldHideFromDebug(),
									val -> Waila.CONFIG.get().getGeneral().setHideFromDebug(val)));
							options.add(new OptionsEntryValueBoolean(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "display_item")),
									Waila.CONFIG.get().getGeneral().shouldShowItem(),
									val -> Waila.CONFIG.get().getGeneral().setShowItem(val)));
							options.add(new OptionsEntryValueBoolean(
									SystemUtil.createTranslationKey("config", new Identifier(Waila.MODID, "tts")),
									Waila.CONFIG.get().getGeneral().shouldEnableTextToSpeech(),
									val -> Waila.CONFIG.get().getGeneral().setEnableTextToSpeech(val)));
							return options;
						}
					});
				})));
		options.add(new OptionsEntryButton(
				SystemUtil.createTranslationKey("config", new Identifier(Waila.MODID, "overlay")),
				new ButtonWidget(0, 0, 100, 20, null, w ->
				{
					minecraft.openScreen(new GuiOptions(GuiConfigWaila.this, new TranslatableComponent(
							SystemUtil.createTranslationKey("config", new Identifier(Waila.MODID, "overlay"))))
					{
						@Override
						public OptionsListWidget getOptions()
						{
							OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32,
									height - 32, 30);
							options.add(new OptionsEntryValueInput<>(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "overlay_pos_x")),
									Waila.CONFIG.get().getOverlay().getOverlayPosX(),
									val -> Waila.CONFIG.get().getOverlay()
											.setOverlayPosX(Math.min(1.0F, Math.max(0.0F, val))),
									OptionsEntryValueInput.FLOAT));
							options.add(new OptionsEntryValueInput<>(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "overlay_pos_y")),
									Waila.CONFIG.get().getOverlay().getOverlayPosY(),
									val -> Waila.CONFIG.get().getOverlay()
											.setOverlayPosY(Math.min(1.0F, Math.max(0.0F, val))),
									OptionsEntryValueInput.FLOAT));
							options.add(new OptionsEntryValueInput<>(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "overlay_scale")),
									Waila.CONFIG.get().getOverlay().getOverlayScale(),
									val -> Waila.CONFIG.get().getOverlay()
											.setOverlayScale(Math.min(2.0F, Math.max(0.1F, val))),
									OptionsEntryValueInput.FLOAT));
							options.add(new OptionsEntryButton(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "overlay_color")),
									new ButtonWidget(0, 0, 100, 20, null, w ->
									{
										minecraft.openScreen(new GuiOptions(GuiConfigWaila.this,
												new TranslatableComponent(SystemUtil.createTranslationKey("config",
														new Identifier(Waila.MODID, "overlay_color"))))
										{
											@Override
											public OptionsListWidget getOptions()
											{
												OptionsListWidget options = new OptionsListWidget(this, minecraft,
														width + 45, height, 32, height - 32, 30);
												options.add(new OptionsEntryValueInput<>(
														SystemUtil.createTranslationKey("config",
																new Identifier(Waila.MODID, "overlay_alpha")),
														Waila.CONFIG.get().getOverlay().getColor().getRawAlpha(),
														val -> Waila.CONFIG.get().getOverlay().getColor()
																.setAlpha(Math.min(100, Math.max(0, val))),
														OptionsEntryValueInput.INTEGER));
												options.add(new OptionsEntryValueCycle(
														SystemUtil.createTranslationKey("config",
																new Identifier(Waila.MODID, "overlay_theme")),
														Waila.CONFIG.get().getOverlay().getColor().getThemes().stream()
																.map(t -> t.getId().toString())
																.sorted(String::compareToIgnoreCase)
																.toArray(String[]::new),
														Waila.CONFIG.get().getOverlay().getColor().getTheme().getId()
																.toString(),
														val -> Waila.CONFIG.get().getOverlay().getColor()
																.applyTheme(new Identifier(val))));
												return options;
											}
										});
									})));
							return options;
						}
					});
				})));
		options.add(new OptionsEntryButton(
				SystemUtil.createTranslationKey("config", new Identifier(Waila.MODID, "formatting")),
				new ButtonWidget(0, 0, 100, 20, null, w ->
				{
					minecraft.openScreen(new GuiOptions(GuiConfigWaila.this, new TranslatableComponent(
							SystemUtil.createTranslationKey("config", new Identifier(Waila.MODID, "overlay"))))
					{
						@Override
						public OptionsListWidget getOptions()
						{
							OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32,
									height - 32, 30);
							options.add(new OptionsEntryValueInput<>(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "format_mod_name")),
									StringEscapeUtils.escapeJava(Waila.CONFIG.get().getFormatting().getModName()),
									val -> Waila.CONFIG.get().getFormatting()
											.setModName(val.isEmpty() || !val.contains("%s")
													? Waila.CONFIG.get().getFormatting().getModName()
													: val)));
							options.add(new OptionsEntryValueInput<>(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "format_block_name")),
									StringEscapeUtils.escapeJava(Waila.CONFIG.get().getFormatting().getBlockName()),
									val -> Waila.CONFIG.get().getFormatting()
											.setBlockName(val.isEmpty() || !val.contains("%s")
													? Waila.CONFIG.get().getFormatting().getBlockName()
													: val)));
							options.add(new OptionsEntryValueInput<>(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "format_fluid_name")),
									StringEscapeUtils.escapeJava(Waila.CONFIG.get().getFormatting().getFluidName()),
									val -> Waila.CONFIG.get().getFormatting()
											.setFluidName(val.isEmpty() || !val.contains("%s")
													? Waila.CONFIG.get().getFormatting().getFluidName()
													: val)));
							options.add(new OptionsEntryValueInput<>(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "format_entity_name")),
									StringEscapeUtils.escapeJava(Waila.CONFIG.get().getFormatting().getEntityName()),
									val -> Waila.CONFIG.get().getFormatting()
											.setEntityName(val.isEmpty() || !val.contains("%s")
													? Waila.CONFIG.get().getFormatting().getEntityName()
													: val)));
							options.add(new OptionsEntryValueInput<>(
									SystemUtil.createTranslationKey("config",
											new Identifier(Waila.MODID, "format_registry_name")),
									StringEscapeUtils.escapeJava(Waila.CONFIG.get().getFormatting().getRegistryName()),
									val -> Waila.CONFIG.get().getFormatting()
											.setRegistryName(val.isEmpty() || !val.contains("%s")
													? Waila.CONFIG.get().getFormatting().getRegistryName()
													: val)));
							return options;
						}
					});
				})));
		return options;
	}
}
