package voiddecay;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityVoidFireball extends EntityFireball
{
	
	public EntityVoidFireball(World world)
	{
		super(world);
        this.setSize(1F, 1F);
	}
	
	public EntityVoidFireball(World world, EntityLivingBase shooter, double x, double y, double z)
	{
		super(world, shooter, x, y, z);
        this.setSize(1F, 1F);
	}
	
	public EntityVoidFireball(World world, double x, double y, double z, double motX, double motY, double motZ)
	{
		super(world, x, y, z, motX, motY, motZ);
        this.setSize(1F, 1F);
	}
	
	@Override
	protected void onImpact(MovingObjectPosition mop)
	{
		if(this.worldObj.isRemote)
		{
			return;
		}
		
		VoidExplosion.newExplosion(this.worldObj, this, this.posX, this.posY, this.posZ, 2.0F);

        this.worldObj.spawnParticle("largeexplode", this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
        
        this.setDead();
	}
}
