package huter.rustyores.main.blocks;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import huter.rustyores.main.items.ModItems;
import huter.rustyores.main.lib.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ThermiteBlock extends Thermite{
	
	public static final String name = "thermiteBlock";
	public static boolean fallInstantly;

	protected ThermiteBlock() {
		super();
		this.setBlockName(Constants.MODID + "_"  + name);
        this.setBlockTextureName(Constants.MODID + ":" + name);
        GameRegistry.registerBlock(this, name);
	}
    
    public int getReactionSpeed(){
        return 40;
    }

    //TODO: eventually this will drop stuff
    public void extraEffects(World w, int x, int y, int z){
        //this.drop(w, x, y, z);
    }

	@Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return ModItems.thermitedust;
    }	
	
	@Override
	public int quantityDropped(Random p_149745_1_)
    {
        return 9;
    }
	
	

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World w, int x, int y, int z)
    {
        w.scheduleBlockUpdate(x, y, z, this, this.tickRate(w));
    }

    private void drop(World w, int x, int y, int z)
    {
        if (canDrop(w, x, y - 1, z) && y >= 0)
        {
            byte b0 = 32;

            if (!fallInstantly && w.checkChunksExist(x - b0, y - b0, z - b0, x + b0, y + b0, z + b0))
            {
                if (!w.isRemote)
                {
                    EntityFallingBlock entityfallingblock = new EntityFallingBlock(w, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), this, w.getBlockMetadata(x, y, z));
                    this.func_149829_a(entityfallingblock);
                    w.spawnEntityInWorld(entityfallingblock);
                }
            } else if(isReacting){ 
            	System.out.println("activated");
            	w.setBlockToAir(x, y, z);
            	
                while (canDrop(w, x, y - 1, z) && y > 0)
                {
                    --y;
                }

                if (y > 0)
                {
                    w.setBlock(x, y, z, this);
                }
                
                this.react(w, x, y, z);
            } else {
                w.setBlockToAir(x, y, z);

                while (canDrop(w, x, y - 1, z) && y > 0)
                {
                    --y;
                }

                if (y > 0)
                {
                    w.setBlock(x, y, z, this);
                }
            }
        }
    }

    protected void func_149829_a(EntityFallingBlock p_149829_1_) {}

    /**
     * How many world ticks before ticking
     */
    public int tickRate(World p_149738_1_)
    {
        return 2;
    }

    public static boolean canDrop(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);

        if (block.isAir(world, x, y, z))
        {
            return true;
        }
        else if (block == Blocks.fire)
        {
            return true;
        }
        else
        {
            //TODO: King, take a look here when doing liquids!
            Material material = block.getMaterial();
            return material == Material.water ? true : material == Material.lava;
        }
    }

    public void func_149828_a(World p_149828_1_, int p_149828_2_, int p_149828_3_, int p_149828_4_, int p_149828_5_) {}
}
