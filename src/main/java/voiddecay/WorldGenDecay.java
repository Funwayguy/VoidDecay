package voiddecay;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import voiddecay.core.VD_Settings;
import voiddecay.core.VoidDecay;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenDecay implements IWorldGenerator
{
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if(VD_Settings.dimBlacklist.contains(world.provider.dimensionId) != VD_Settings.dimWhitelist || rand.nextInt(100) >= VD_Settings.infectedChunks)
		{
			return;
		}
		
		int x = chunkX*16 + rand.nextInt(16);
		int y = rand.nextInt(world.provider.getActualHeight());
		int z = chunkZ*16 + rand.nextInt(16);
		
		int minDecay = 8; // sets min number of blocks per generation
		int maxDecay = 110; // Sets max number of blocks per generation
		int decayDiff = maxDecay - minDecay;
		
		double daysTillMax = VD_Settings.relativeTime; // days till maximum decay
		double distTillMax = VD_Settings.relativeDist; // distance in chunks till maximum decay
		
		double scaleFactor = 0D;
		
		if(VD_Settings.decayScaleDist)
		{
			ChunkCoordinates spawn = world.getSpawnPoint();
			double spawnDist = Math.sqrt((x*x - spawn.posX*spawn.posX) + (z*z - spawn.posZ*spawn.posZ));
			double distFactor = spawnDist/(distTillMax * 16D);
			scaleFactor += distFactor;
		}
		
		if(VD_Settings.decayScaleTime)
		{
			double timeFactor = (world.getWorldTime()/24000D)/daysTillMax;
			scaleFactor += timeFactor;
		}
		
		scaleFactor = MathHelper.clamp_double(scaleFactor, 0D, 1D);
		int numberOfBlocks = minDecay + MathHelper.floor_double(decayDiff * scaleFactor);
		
        float f = rand.nextFloat() * (float)Math.PI;
        double d0 = (double)((float)(x + 8) + MathHelper.sin(f) * (float)numberOfBlocks / 8.0F);
        double d1 = (double)((float)(x + 8) - MathHelper.sin(f) * (float)numberOfBlocks / 8.0F);
        double d2 = (double)((float)(z + 8) + MathHelper.cos(f) * (float)numberOfBlocks / 8.0F);
        double d3 = (double)((float)(z + 8) - MathHelper.cos(f) * (float)numberOfBlocks / 8.0F);
        double d4 = (double)(y + rand.nextInt(3) - 2);
        double d5 = (double)(y + rand.nextInt(3) - 2);

        for (int l = 0; l <= numberOfBlocks; ++l)
        {
            double d6 = d0 + (d1 - d0) * (double)l / (double)numberOfBlocks;
            double d7 = d4 + (d5 - d4) * (double)l / (double)numberOfBlocks;
            double d8 = d2 + (d3 - d2) * (double)l / (double)numberOfBlocks;
            double d9 = rand.nextDouble() * (double)numberOfBlocks / 16.0D;
            double d10 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int k2 = i1; k2 <= l1; ++k2)
            {
                double d12 = ((double)k2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D)
                {
                    for (int l2 = j1; l2 <= i2; ++l2)
                    {
                        double d13 = ((double)l2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for (int i3 = k1; i3 <= j2; ++i3)
                            {
                                double d14 = ((double)i3 + 0.5D - d8) / (d10 / 2.0D);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && world.getBlock(k2, l2, i3).getMaterial() != Material.air)
                                {
                                    world.setBlock(k2, l2, i3, VoidDecay.decay, 0, 2);
                                }
                            }
                        }
                    }
                }
            }
        }
	}
}