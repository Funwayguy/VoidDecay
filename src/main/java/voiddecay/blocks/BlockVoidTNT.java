package voiddecay.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockVoidTNT extends Block
{
	public BlockVoidTNT()
	{
		super(Material.tnt);
		this.setBlockTextureName("voiddecay:tnt_void");
		this.setBlockName("void_decay.void_tnt");
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);

        if (world.isBlockIndirectlyGettingPowered(x, y, z))
        {
            this.blowUp(world, x, y, z, null);
            world.setBlockToAir(x, y, z);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (world.isBlockIndirectlyGettingPowered(x, y, z))
        {
            this.blowUp(world, x, y, z, null);
            world.setBlockToAir(x, y, z);
        }
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.flint_and_steel)
        {
            this.blowUp(world, x, y, z, player);
            world.setBlockToAir(x, y, z);
            player.getCurrentEquippedItem().damageItem(1, player);
            return true;
        }
        else
        {
            return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
        }
    }

    /**
     * Called upon the block being destroyed by an explosion
     */
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion)
    {
        if (!world.isRemote)
        {
        	EntityVoidTNT entitytntprimed = new EntityVoidTNT(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), explosion.getExplosivePlacedBy());
            entitytntprimed.fuse = world.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
            world.spawnEntityInWorld(entitytntprimed);
        }
    }

    public void blowUp(World world, int x, int y, int z, EntityLivingBase entityLiving)
    {
        if (!world.isRemote)
        {
        	EntityVoidTNT entitytntprimed = new EntityVoidTNT(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), entityLiving);
            world.spawnEntityInWorld(entitytntprimed);
            world.playSoundAtEntity(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
        }
    }
}
