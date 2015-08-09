package voiddecay.handlers;

import net.minecraft.util.ChunkCoordinates;
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
			int decayDiff = maxDecay - minDecay;
			
			double daysTillMax = VD_Settings.relativeTime; // days till maximum decay
			double distTillMax = VD_Settings.relativeDist; // distance in chunks till maximum decay
			
			double scaleFactor = 0D;
			
			if(VD_Settings.decayScaleDist)
			{
				ChunkCoordinates spawn = event.world.getSpawnPoint();
				double spawnDist = Math.sqrt((i*i - spawn.posX*spawn.posX) + (k*k - spawn.posZ*spawn.posZ));
				double distFactor = spawnDist/(distTillMax * 16D);
				scaleFactor += distFactor;
			}
			
			if(VD_Settings.decayScaleTime)
			{
				double timeFactor = (event.world.getWorldTime()/24000D)/daysTillMax;
				scaleFactor += timeFactor;
			}
			
			scaleFactor = MathHelper.clamp_double(scaleFactor, 0D, 1D);

			WorldGenDecay voidGen = new WorldGenDecay(minDecay + MathHelper.floor_double(decayDiff * scaleFactor));

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
