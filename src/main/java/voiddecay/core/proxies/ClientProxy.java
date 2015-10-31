package voiddecay.core.proxies;

import net.minecraft.client.renderer.entity.RenderFireball;
import voiddecay.EntityVoidFireball;
import voiddecay.blocks.EntityCleanseTNT;
import voiddecay.blocks.EntityVoidTNT;
import voiddecay.blocks.RenderCleanseTNT;
import voiddecay.blocks.RenderVoidTNT;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public boolean isClient()
	{
		return true;
	}
	
	@Override
	public void registerHandlers()
	{
		super.registerHandlers();
		RenderingRegistry.registerEntityRenderingHandler(EntityVoidTNT.class, new RenderVoidTNT());
		RenderingRegistry.registerEntityRenderingHandler(EntityCleanseTNT.class, new RenderCleanseTNT());
    	RenderingRegistry.registerEntityRenderingHandler(EntityVoidFireball.class, new RenderFireball(4.0F));
	}
}
