package voiddecay.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import voiddecay.EntityVoidFireball;
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
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if(!event.entityLiving.worldObj.isRemote && event.entityLiving.worldObj.isRaining() && event.entityLiving.worldObj.isThundering())
		{
			if(VD_Settings.voidMeteor && event.entityLiving.worldObj.rand.nextInt(100) == 0 && !event.entityLiving.worldObj.provider.hasNoSky && event.entityLiving.ticksExisted%200 == 0 && event.entityLiving instanceof EntityPlayer)
			{
				double spawnX = event.entityLiving.posX + (event.entityLiving.worldObj.rand.nextDouble() * 128D) - 64D;
				double spawnZ = event.entityLiving.posZ + (event.entityLiving.worldObj.rand.nextDouble() * 128D) - 64D;
				
				EntityFireball fireball = new EntityVoidFireball(event.entityLiving.worldObj, spawnX, 255, spawnZ, 0.1D, -2D, 0.1D);
				event.entityLiving.worldObj.spawnEntityInWorld(fireball);
			}
		}
	}
}
