package huter.rustyores.main.renderers;

import huter.rustyores.main.RustyOres;
import huter.rustyores.main.blocks.ModBlocks;
import huter.rustyores.main.blocks.ThermiteWire;
import huter.rustyores.main.proxies.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class ThermiteWireBlocksRenderer implements ISimpleBlockRenderingHandler {

	public int ModelID;
	
	public ThermiteWireBlocksRenderer(int RID) {
        ModelID = RID;
    }
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int renderId, RenderBlocks renderer) {
		return renderId == ClientProxy.thermiteWireID ? renderThermiteWire(block, x, y, z, renderer) :
            false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean renderThermiteWire(Block par1Block, int par2, int par3, int par4, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        int l = renderer.blockAccess.getBlockMetadata(par2, par3, par4);
        IIcon icon = BlockRedstoneWire.getRedstoneWireIcon("cross");
        IIcon icon1 = BlockRedstoneWire.getRedstoneWireIcon("line");
        IIcon icon2 = BlockRedstoneWire.getRedstoneWireIcon("cross_overlay");
        IIcon icon3 = BlockRedstoneWire.getRedstoneWireIcon("line_overlay");
        tessellator.setBrightness(par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4));
        float f = 1.0F;
        float f1 = (float)l / 15.0F;
        float f2 = f1 * 0.6F + 0.4F;

        if (l == 0)
        {
            f2 = (float)RustyOres.thermiteWireColorR/255;
        }

        float f3 = f1 * f1 * 0.7F - 0.5F;
        float f4 = f1 * f1 * 0.6F - 0.7F;

        if (f3 < 0.0F)
        {
            f3 = (float)RustyOres.thermiteWireColorG/255;
        }

        if (f4 < 0.0F)
        {
            f4 = (float)RustyOres.thermiteWireColorB/255;
        }

        tessellator.setColorOpaque_F(f2, f3, f4);
        double d0 = 0.015625D;
        double d1 = 0.015625D;
        boolean flag = ThermiteWire.isPowerProviderOrWire(renderer.blockAccess, par2 - 1, par3, par4, 1) || !renderer.blockAccess.getBlock(par2 - 1, par3, par4).isBlockNormalCube() && ThermiteWire.isPowerProviderOrWire(renderer.blockAccess, par2 - 1, par3 - 1, par4, -1);
        boolean flag1 = ThermiteWire.isPowerProviderOrWire(renderer.blockAccess, par2 + 1, par3, par4, 3) || !renderer.blockAccess.getBlock(par2 + 1, par3, par4).isBlockNormalCube() && ThermiteWire.isPowerProviderOrWire(renderer.blockAccess, par2 + 1, par3 - 1, par4, -1);
        boolean flag2 = ThermiteWire.isPowerProviderOrWire(renderer.blockAccess, par2, par3, par4 - 1, 2) || !renderer.blockAccess.getBlock(par2, par3, par4 - 1).isBlockNormalCube() && ThermiteWire.isPowerProviderOrWire(renderer.blockAccess, par2, par3 - 1, par4 - 1, -1);
        boolean flag3 = ThermiteWire.isPowerProviderOrWire(renderer.blockAccess, par2, par3, par4 + 1, 0) || !renderer.blockAccess.getBlock(par2, par3, par4 + 1).isBlockNormalCube() && ThermiteWire.isPowerProviderOrWire(renderer.blockAccess, par2, par3 - 1, par4 + 1, -1);

        if (!renderer.blockAccess.getBlock(par2, par3 + 1, par4).isBlockNormalCube())
        {
            if (renderer.blockAccess.getBlock(par2 - 1, par3, par4).isBlockNormalCube() && ThermiteWire.isPowerProviderOrWire(renderer.blockAccess, par2 - 1, par3 + 1, par4, -1))
            {
                flag = true;
            }

            if (renderer.blockAccess.getBlock(par2 + 1, par3, par4).isBlockNormalCube() && ThermiteWire.isPowerProviderOrWire(renderer.blockAccess, par2 + 1, par3 + 1, par4, -1))
            {
                flag1 = true;
            }

            if (renderer.blockAccess.getBlock(par2, par3, par4 - 1).isBlockNormalCube() && ThermiteWire.isPowerProviderOrWire(renderer.blockAccess, par2, par3 + 1, par4 - 1, -1))
            {
                flag2 = true;
            }

            if (renderer.blockAccess.getBlock(par2, par3, par4 + 1).isBlockNormalCube() && ThermiteWire.isPowerProviderOrWire(renderer.blockAccess, par2, par3 + 1, par4 + 1, -1))
            {
                flag3 = true;
            }
        }

        float f5 = (float)(par2 + 0);
        float f6 = (float)(par2 + 1);
        float f7 = (float)(par4 + 0);
        float f8 = (float)(par4 + 1);
        int i1 = 0;

        if ((flag || flag1) && !flag2 && !flag3)
        {
            i1 = 1;
        }

        if ((flag2 || flag3) && !flag1 && !flag)
        {
            i1 = 2;
        }

        if (i1 == 0)
        {
            int j1 = 0;
            int k1 = 0;
            int l1 = 16;
            int i2 = 16;
            boolean flag4 = true;

            if (!flag)
            {
                f5 += 0.3125F;
            }

            if (!flag)
            {
                j1 += 5;
            }

            if (!flag1)
            {
                f6 -= 0.3125F;
            }

            if (!flag1)
            {
                l1 -= 5;
            }

            if (!flag2)
            {
                f7 += 0.3125F;
            }

            if (!flag2)
            {
                k1 += 5;
            }

            if (!flag3)
            {
                f8 -= 0.3125F;
            }

            if (!flag3)
            {
                i2 -= 5;
            }
            
            tessellator.addVertexWithUV((double)f6, (double)par3 + 0.015625D, (double)f8, (double)icon.getInterpolatedU((double)l1), (double)icon.getInterpolatedV((double)i2));
            tessellator.addVertexWithUV((double)f6, (double)par3 + 0.015625D, (double)f7, (double)icon.getInterpolatedU((double)l1), (double)icon.getInterpolatedV((double)k1));
            tessellator.addVertexWithUV((double)f5, (double)par3 + 0.015625D, (double)f7, (double)icon.getInterpolatedU((double)j1), (double)icon.getInterpolatedV((double)k1));
            tessellator.addVertexWithUV((double)f5, (double)par3 + 0.015625D, (double)f8, (double)icon.getInterpolatedU((double)j1), (double)icon.getInterpolatedV((double)i2));
            tessellator.setColorOpaque_F(f, f, f);
            tessellator.addVertexWithUV((double)f6, (double)par3 + 0.015625D, (double)f8, (double)icon2.getInterpolatedU((double)l1), (double)icon2.getInterpolatedV((double)i2));
            tessellator.addVertexWithUV((double)f6, (double)par3 + 0.015625D, (double)f7, (double)icon2.getInterpolatedU((double)l1), (double)icon2.getInterpolatedV((double)k1));
            tessellator.addVertexWithUV((double)f5, (double)par3 + 0.015625D, (double)f7, (double)icon2.getInterpolatedU((double)j1), (double)icon2.getInterpolatedV((double)k1));
            tessellator.addVertexWithUV((double)f5, (double)par3 + 0.015625D, (double)f8, (double)icon2.getInterpolatedU((double)j1), (double)icon2.getInterpolatedV((double)i2));
        }
        else if (i1 == 1)
        {
            tessellator.addVertexWithUV((double)f6, (double)par3 + 0.015625D, (double)f8, (double)icon1.getMaxU(), (double)icon1.getMaxV());
            tessellator.addVertexWithUV((double)f6, (double)par3 + 0.015625D, (double)f7, (double)icon1.getMaxU(), (double)icon1.getMinV());
            tessellator.addVertexWithUV((double)f5, (double)par3 + 0.015625D, (double)f7, (double)icon1.getMinU(), (double)icon1.getMinV());
            tessellator.addVertexWithUV((double)f5, (double)par3 + 0.015625D, (double)f8, (double)icon1.getMinU(), (double)icon1.getMaxV());
            tessellator.setColorOpaque_F(f, f, f);
            tessellator.addVertexWithUV((double)f6, (double)par3 + 0.015625D, (double)f8, (double)icon3.getMaxU(), (double)icon3.getMaxV());
            tessellator.addVertexWithUV((double)f6, (double)par3 + 0.015625D, (double)f7, (double)icon3.getMaxU(), (double)icon3.getMinV());
            tessellator.addVertexWithUV((double)f5, (double)par3 + 0.015625D, (double)f7, (double)icon3.getMinU(), (double)icon3.getMinV());
            tessellator.addVertexWithUV((double)f5, (double)par3 + 0.015625D, (double)f8, (double)icon3.getMinU(), (double)icon3.getMaxV());
        }
        else
        {
            tessellator.addVertexWithUV((double)f6, (double)par3 + 0.015625D, (double)f8, (double)icon1.getMaxU(), (double)icon1.getMaxV());
            tessellator.addVertexWithUV((double)f6, (double)par3 + 0.015625D, (double)f7, (double)icon1.getMinU(), (double)icon1.getMaxV());
            tessellator.addVertexWithUV((double)f5, (double)par3 + 0.015625D, (double)f7, (double)icon1.getMinU(), (double)icon1.getMinV());
            tessellator.addVertexWithUV((double)f5, (double)par3 + 0.015625D, (double)f8, (double)icon1.getMaxU(), (double)icon1.getMinV());
            tessellator.setColorOpaque_F(f, f, f);
            tessellator.addVertexWithUV((double)f6, (double)par3 + 0.015625D, (double)f8, (double)icon3.getMaxU(), (double)icon3.getMaxV());
            tessellator.addVertexWithUV((double)f6, (double)par3 + 0.015625D, (double)f7, (double)icon3.getMinU(), (double)icon3.getMaxV());
            tessellator.addVertexWithUV((double)f5, (double)par3 + 0.015625D, (double)f7, (double)icon3.getMinU(), (double)icon3.getMinV());
            tessellator.addVertexWithUV((double)f5, (double)par3 + 0.015625D, (double)f8, (double)icon3.getMaxU(), (double)icon3.getMinV());
        }

        if (!renderer.blockAccess.getBlock(par2, par3 + 1, par4).isBlockNormalCube())
        {
            float f9 = 0.021875F;

            if (renderer.blockAccess.getBlock(par2 - 1, par3, par4).isBlockNormalCube() && renderer.blockAccess.getBlock(par2 - 1, par3 + 1, par4) == ModBlocks.thermitewire)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV((double)par2 + 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1), (double)icon1.getMaxU(), (double)icon1.getMinV());
                tessellator.addVertexWithUV((double)par2 + 0.015625D, (double)(par3 + 0), (double)(par4 + 1), (double)icon1.getMinU(), (double)icon1.getMinV());
                tessellator.addVertexWithUV((double)par2 + 0.015625D, (double)(par3 + 0), (double)(par4 + 0), (double)icon1.getMinU(), (double)icon1.getMaxV());
                tessellator.addVertexWithUV((double)par2 + 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 0), (double)icon1.getMaxU(), (double)icon1.getMaxV());
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV((double)par2 + 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1), (double)icon3.getMaxU(), (double)icon3.getMinV());
                tessellator.addVertexWithUV((double)par2 + 0.015625D, (double)(par3 + 0), (double)(par4 + 1), (double)icon3.getMinU(), (double)icon3.getMinV());
                tessellator.addVertexWithUV((double)par2 + 0.015625D, (double)(par3 + 0), (double)(par4 + 0), (double)icon3.getMinU(), (double)icon3.getMaxV());
                tessellator.addVertexWithUV((double)par2 + 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 0), (double)icon3.getMaxU(), (double)icon3.getMaxV());
            }

            if (renderer.blockAccess.getBlock(par2 + 1, par3, par4).isBlockNormalCube() && renderer.blockAccess.getBlock(par2 + 1, par3 + 1, par4) == ModBlocks.thermitewire)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)(par3 + 0), (double)(par4 + 1), (double)icon1.getMinU(), (double)icon1.getMaxV());
                tessellator.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1), (double)icon1.getMaxU(), (double)icon1.getMaxV());
                tessellator.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 0), (double)icon1.getMaxU(), (double)icon1.getMinV());
                tessellator.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)(par3 + 0), (double)(par4 + 0), (double)icon1.getMinU(), (double)icon1.getMinV());
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)(par3 + 0), (double)(par4 + 1), (double)icon3.getMinU(), (double)icon3.getMaxV());
                tessellator.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1), (double)icon3.getMaxU(), (double)icon3.getMaxV());
                tessellator.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 0), (double)icon3.getMaxU(), (double)icon3.getMinV());
                tessellator.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)(par3 + 0), (double)(par4 + 0), (double)icon3.getMinU(), (double)icon3.getMinV());
            }

            if (renderer.blockAccess.getBlock(par2, par3, par4 - 1).isBlockNormalCube() && renderer.blockAccess.getBlock(par2, par3 + 1, par4 - 1) == ModBlocks.thermitewire)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)par4 + 0.015625D, (double)icon1.getMinU(), (double)icon1.getMaxV());
                tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 1) + 0.021875F), (double)par4 + 0.015625D, (double)icon1.getMaxU(), (double)icon1.getMaxV());
                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 1) + 0.021875F), (double)par4 + 0.015625D, (double)icon1.getMaxU(), (double)icon1.getMinV());
                tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)par4 + 0.015625D, (double)icon1.getMinU(), (double)icon1.getMinV());
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)par4 + 0.015625D, (double)icon3.getMinU(), (double)icon3.getMaxV());
                tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 1) + 0.021875F), (double)par4 + 0.015625D, (double)icon3.getMaxU(), (double)icon3.getMaxV());
                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 1) + 0.021875F), (double)par4 + 0.015625D, (double)icon3.getMaxU(), (double)icon3.getMinV());
                tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)par4 + 0.015625D, (double)icon3.getMinU(), (double)icon3.getMinV());
            }

            if (renderer.blockAccess.getBlock(par2, par3, par4 + 1).isBlockNormalCube() && renderer.blockAccess.getBlock(par2, par3 + 1, par4 + 1) == ModBlocks.thermitewire)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1) - 0.015625D, (double)icon1.getMaxU(), (double)icon1.getMinV());
                tessellator.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)(par4 + 1) - 0.015625D, (double)icon1.getMinU(), (double)icon1.getMinV());
                tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 1) - 0.015625D, (double)icon1.getMinU(), (double)icon1.getMaxV());
                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1) - 0.015625D, (double)icon1.getMaxU(), (double)icon1.getMaxV());
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1) - 0.015625D, (double)icon3.getMaxU(), (double)icon3.getMinV());
                tessellator.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)(par4 + 1) - 0.015625D, (double)icon3.getMinU(), (double)icon3.getMinV());
                tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 1) - 0.015625D, (double)icon3.getMinU(), (double)icon3.getMaxV());
                tessellator.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1) - 0.015625D, (double)icon3.getMaxU(), (double)icon3.getMaxV());
            }
        }

        return true;
    }

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return ClientProxy.thermiteWireID;
	}

}
