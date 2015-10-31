package voiddecay.blocks;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import voiddecay.core.VD_Settings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockVoidDecay extends Block
{
	public static int updates = 0;
	
	public BlockVoidDecay()
	{
		super(Material.portal);
		this.setBlockUnbreakable();
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setBlockTextureName("voiddecay:void");
		this.setTickRandomly(true);
    	this.setBlockName("void_decay.decay");
	}

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z)
    {
    	if(VD_Settings.fastDecay)
    	{
    		world.scheduleBlockUpdate(x, y, z, this, 20);
    	}
    }
	
    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
    	if(world.isRemote)
    	{
    		return;
    	}
    	
    	if(updates >= MathHelper.ceiling_float_int(VD_Settings.decayCap/2F) || rand.nextInt(100) >= VD_Settings.decaySpeed)
    	{
    		return;
    	} else
    	{
    		updates++;
    	}
    	
    	Block below = world.getBlock(x, y - 1, z);
    	
    	if(below.getMaterial() != Material.air && below != this)
    	{
    		world.setBlock(x, y - 1, z, this, 0, 2);
    		world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "portal.portal", 0.25F, 1.8F + (rand.nextFloat()*0.2F));
    		if(VD_Settings.fastDecay)
    		{
    			world.scheduleBlockUpdate(x, y, z, this, 20);
    		}
    		return;
    	} else
    	{
	    	for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
	    	{
		    	int i = x + dir.offsetX;
		    	int j = y + dir.offsetY;
		    	int k = z + dir.offsetZ;
		    	
		    	Block block = world.getBlock(i, j, k);
		    	
		    	if(block.getMaterial() != Material.air && block != this)
		    	{
		    		world.setBlock(i, j, k, this, 0, 2);
		    	}
	    	}
	    	
    		world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "portal.portal", 0.25F, 1.8F + (rand.nextFloat()*0.2F));
	    	world.setBlock(x, y, z, Blocks.air, 0, 2);
    	}
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
    	if(VD_Settings.fastDecay)
    	{
    		world.scheduleBlockUpdate(x, y, z, this, 20);
    	}
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
    	return null;
    }

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return true;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random p_149745_1_)
    {
        return 0;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
    	entity.attackEntityFrom(DamageSource.magic, 2F);
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 2;
    }

    /**
     * Determines if a new block can be replace the space occupied by this one,
     * Used in the player's placement code to make the block act like water, and lava.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @return True if the block is replaceable by another block
     */
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_)
    {
    	return null;
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int p_149646_5_)
    {
    	Block b = access.getBlock(x, y, z);
        if (b == this || b.isOpaqueCube())
        {
        	return false;
        } else
        {
        	return true;
        }
    }
}
