package huter.rustyores.main.blocks;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import huter.rustyores.main.lib.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public abstract class Thermite extends Block {
	
	public boolean isReacting;
	
	protected Thermite() {
		super(Material.circuits);
	}
	
    protected void onReactionNotification(World w, int x, int y, int z){
    	react(w, x, y, z);
    }

    protected abstract int getReactionSpeed(); 

    protected void react(World w, int x, int y, int z){
        isReacting = true;

     	// Destroy the current block
		w.func_147480_a(x, y, z, false);
		w.func_147480_a(x, y-1, z, true);
		
		
		// notify neighbors of reaction 
		notifyNeighborsOfReaction(w, x, y, z);
    }
    
    protected void notifyReaction(World w, int x, int y, int z){
    	Block target = w.getBlock(x, y, z);
    	if (target == ModBlocks.thermitewire || target == ModBlocks.thermiteBlock){
    		Thermite twTarget = (Thermite) target;
    		twTarget.onReactionNotification(w, x, y, z);
    	}
    }
    
    protected void notifyNeighborsOfReaction(World w, int x, int y, int z){
    	notifyReaction(w, x, y-1, z+1);
    	notifyReaction(w, x, y-1, z-1);
    	notifyReaction(w, x+1, y-1, z);
    	notifyReaction(w, x-1, y-1, z);
    	notifyReaction(w, x, y+1, z+1);
    	notifyReaction(w, x, y+1, z-1);
    	notifyReaction(w, x+1, y+1, z);
    	notifyReaction(w, x-1, y+1, z);
    	notifyReaction(w, x, y, z+1);
    	notifyReaction(w, x, y, z-1);
    	notifyReaction(w, x+1, y, z);
    	notifyReaction(w, x-1, y, z);
    }
    
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
    	if(!world.isRemote){
    		if(player.getEquipmentInSlot(0).getItem() == Items.flint_and_steel){
    			this.react(world, x, y, z);
    			player.getEquipmentInSlot(0).damageItem(1, player);
    			return true;
    		}
    		return false;
    	}
        return false;
    }
    
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, this.tickRate(p_149695_1_));
    }
    
    public void updateTick(World w, int x, int y, int z, Random r)
    {
        if (isReacting && (w.getWorldTime() % 40 == 0)){ 
            react(w, x, y, z);
        }
    }
}
