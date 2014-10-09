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
	
	protected boolean isReacting;
    private long reactionStartTime;
	
	protected Thermite() {
		super(Material.circuits);
        //setTickRandomly(true);
	}
	
    /**
     * How many world ticks before ticking
     */
    public int tickRate(World p_149738_1_)
    {
        return 2;
    }
    
    protected void onReactionNotification(World w, int x, int y, int z){
        System.out.println("Thermite notified of reaction.");
    	startReaction(w, x, y, z);
    }

    protected abstract int getReactionSpeed(); 
    
    protected abstract void extraEffects(World w, int x, int y, int z);

    protected void react(World w, int x, int y, int z){
        System.out.println("Thermite item now reacting");
        
        extraEffects(w, x, y, z);

     	// Destroy the current block
        w.func_147480_a(x, y, z, false);
        // Destroy the block below this
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
                System.out.println("Thermite activated with flint and steel");
                this.startReaction(world, x, y, z);
    			//this.react(world, x, y, z);
    			player.getEquipmentInSlot(0).damageItem(1, player);
    			return true;
    		}
    		return false;
    	}
        return false;
    }
    
    public void updateTick(World w, int x, int y, int z, Random r)
    {
        System.out.println("Thermite tick updated."); 
        if (isReacting && 
            w.getWorldTime()-reactionStartTime > 2){
            react(w, x, y, z);
        }
    }

    public void startReaction(World w, int x, int y, int z){
        isReacting = true;
        reactionStartTime = w.getWorldTime();
    }
}

