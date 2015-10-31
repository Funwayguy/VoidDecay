package voiddecay.handlers;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import voiddecay.core.VD_Settings;
import voiddecay.core.VoidDecay;

public class ConfigHandler
{
	public static Configuration config;
	
	public static void initConfigs()
	{
		if(config == null)
		{
			VoidDecay.logger.log(Level.ERROR, "Config attempted to be loaded before it was initialised!");
			return;
		}
		
		config.load();

		VD_Settings.decaySpeed = config.getInt("Decay Speed", Configuration.CATEGORY_GENERAL, 100, 0, 100, "Decay rate percentage");
		VD_Settings.infectedChunks = config.getInt("Infected Chunks", Configuration.CATEGORY_GENERAL, 100, 0, 100, "What percentage of chunks are pre-infected");
		VD_Settings.decayCap = config.getInt("Decay Cap", Configuration.CATEGORY_GENERAL, 20, 1, Integer.MAX_VALUE, "Maximum amount of blocks that can decay per second");
		VD_Settings.fastDecay = config.getBoolean("Fast Decay", Configuration.CATEGORY_GENERAL, false, "Speeds up decay considerably at the cost of TPS latency (NOT RECOMMENDED)");
		VD_Settings.decayScaleTime = config.getBoolean("Scale With Time", Configuration.CATEGORY_GENERAL, false, "Decay scales with world time");
		VD_Settings.decayScaleDist = config.getBoolean("Scale With Distance", Configuration.CATEGORY_GENERAL, false, "Decay scales with distance from spawn");
		VD_Settings.relativeDist = config.getInt("Distance in chunks for scaling", Configuration.CATEGORY_GENERAL, 100, 1, Integer.MAX_VALUE, "");
		VD_Settings.relativeTime = config.getInt("Time in days for scaling", Configuration.CATEGORY_GENERAL, 30, 1, Integer.MAX_VALUE, "");
		VD_Settings.hideUpdates = config.getBoolean("Hide Updates", Configuration.CATEGORY_GENERAL, false, "Hides update notifications");
		VD_Settings.voidMeteor = config.getBoolean("Void Meteors", Configuration.CATEGORY_GENERAL, true, "Occasionally void causing fireballs will drop from the sky");
		
		config.save();
		
		System.out.println("Loaded configs...");
	}
}
