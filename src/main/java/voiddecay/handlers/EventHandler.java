package voiddecay.handlers;

import com.jcraft.jorbis.Block;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import voiddecay.WorldGenDecay;
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
			
			int minDecay = 8; // sets min number of blocks per generation
			int maxDecay = 110; // Sets max number of blocks per generation
			int dayDivBy = 10; // +1 for each 'dayDivBy'
			int chunkDivBy = 10; // +1 for each 'chunkDivBy'

			// Distance from center of world +1 for every 'chunkDivBy' chunks away.
			int distModifier = (Math.max(Math.abs(event.chunkX), Math.abs(event.chunkZ))/chunkDivBy);
			// For every 'dayDivBy' days add +1 (grabs world time) 
			int timeModifier = Math.max((MathHelper.floor_double(event.world.getWorldTime()/24000L)/dayDivBy)*1, 0);
			// get number of blocks get min and max or just min if void progression false
			int numberofblocks = VD_Settings.decayProgression ? Math.max(Math.min((distModifier + timeModifier), maxDecay), minDecay) : minDecay;

			WorldGenDecay voidGen = new WorldGenDecay(numberofblocks);

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
