package voiddecay.handlers;

import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import voiddecay.blocks.BlockVoidDecay;
import voiddecay.core.VD_Settings;
import voiddecay.core.VoidDecay;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class EventHandler
{
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equals(VoidDecay.MODID))
		{
			ConfigHandler.config.save();
			ConfigHandler.initConfigs();
		}
	}
	
	@SubscribeEvent
	public void onGenerate(PopulateChunkEvent.Pre event)
	{
		if(event.rand.nextInt(100) < VD_Settings.infectedChunks)
		{
			int i = event.chunkX * 16 + event.rand.nextInt(16);
			int k = event.chunkZ * 16 + event.rand.nextInt(16);
			int height = event.world.getHeightValue(i, k);
			
			WorldGenMinable voidGen = new WorldGenMinable(VoidDecay.decay, 8);
			voidGen.generate(event.world, event.rand, i, event.rand.nextInt(height > 1? height : 64), k);
		}
	}
	
	int ticks = 0;
	
	@SubscribeEvent
	public void onTick(TickEvent.ServerTickEvent event)
	{
		if(event.phase != TickEvent.Phase.END)
		{
			return;
		}
		
		ticks++;
		
		if(ticks >= 10)
		{
			ticks=0;
			BlockVoidDecay.updates = 0;
		}
	}
}
