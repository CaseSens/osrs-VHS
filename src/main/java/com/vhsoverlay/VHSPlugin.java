package com.vhsoverlay;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "VHS Overlay"
)
public class VHSPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private VHSConfig config;

	@Inject
	private OverlayManager overlayManager;

	private CRTOverlay crtOverlay;

	@Override
	protected void startUp() throws Exception
	{
		updateOverlay();
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(crtOverlay);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("VHS")) {
			updateOverlay();
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			// Apply the VHS effect here
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "VHS effect activated", null);
		}
		else
		{
			// Maybe deactivate the VHS effect if you have persistent effects
		}
	}

	private void updateOverlay() {
		if (config.enableVHS()) {
			overlayManager.add(crtOverlay);
		} else {
			overlayManager.remove(crtOverlay);
		}
	}

	@Provides
	VHSConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VHSConfig.class);
	}
}
