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
		VD_Settings.decayCap = config.getInt("Decay Cap", Configuration.CATEGORY_GENERAL, 20, 1, 100, "Maximum amount of blocks that can decay per second");
		
		config.save();
		
		System.out.println("Loaded configs...");
	}
}
