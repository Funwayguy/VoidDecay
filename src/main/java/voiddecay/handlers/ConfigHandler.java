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

		VD_Settings.decaySpeed = config.getInt("Decay Speed", Configuration.CATEGORY_GENERAL, 25, 0, 100, "How fast does the decay spread");
		VD_Settings.infectedChunks = config.getInt("Infected Chunks", Configuration.CATEGORY_GENERAL, 1, 0, 100, "What percentage of chunks are pre-infected");
		
		config.save();
		
		System.out.println("Loaded configs...");
	}
}
