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
		VD_Settings.decayProgression = config.getBoolean("Decay World Progression", Configuration.CATEGORY_GENERAL, true, "Void decay increases with world time and distance traveled from center of the world.");
		
		config.save();
		
		System.out.println("Loaded configs...");
	}
}
