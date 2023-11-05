package com.vhsoverlay;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("vhs")
public interface VHSConfig extends Config
{
	@ConfigItem(
		keyName = "enableVHS",
		name = "Enable VHS Effect",
		description = "Toggle the VHS effect overlay"
	)
	default boolean enableVHS()
	{
		return true;
	}
}
