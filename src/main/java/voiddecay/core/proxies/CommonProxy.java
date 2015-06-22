package voiddecay.core.proxies;

import net.minecraftforge.common.MinecraftForge;
import voiddecay.client.UpdateNotification;
import voiddecay.handlers.EventHandler;
import cpw.mods.fml.common.FMLCommonHandler;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void registerHandlers()
	{
		EventHandler handler = new EventHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);
		FMLCommonHandler.instance().bus().register(new UpdateNotification());
	}
}
