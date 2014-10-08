package huter.rustyores.main.blocks;

import huter.rustyores.main.RustyOres;
import huter.rustyores.main.items.ModItems;
import huter.rustyores.main.lib.Constants;
import huter.rustyores.main.proxies.ClientProxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ThermiteWire extends Thermite{
	
	@SideOnly(Side.CLIENT)
	public static IIcon crossicon;
	@SideOnly(Side.CLIENT)
	public static IIcon lineicon;
	@SideOnly(Side.CLIENT)
	public static IIcon cross_overlayicon;
	@SideOnly(Side.CLIENT)
	public static IIcon line_overlayicon;
	public static final String name = "thermitewire";
	private boolean wiresProvidePower = true;
	private boolean reacting = false;
    private Set blocksNeedingUpdate = new HashSet();

	protected ThermiteWire() {
		super();
        this.setBlockTextureName(Constants.MODID + ":" + name);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
        this.disableStats();
        GameRegistry.registerBlock(this, name);
	}

    public int getReactionSpeed(){
        return 40;
    }
    
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
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
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return ClientProxy.thermiteWireID;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return RustyOres.thermiteWireColor;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int x, int y, int z)
    {
        return World.doesBlockHaveSolidTopSurface(par1World, x, y - 1, z) || par1World.getBlock(x, y - 1, z) == Blocks.glowstone;
    }

    /**
     * Sets the strength of the wire current (0-15) for this block based on neighboring blocks and propagates to
     * neighboring redstone wires
     */
    private void updateAndPropagateCurrentStrength(World par1World, int par2, int par3, int par4)
    {
        //this.calculateCurrentChanges(par1World, par2, par3, par4, par2, par3, par4);
        ArrayList arraylist = new ArrayList(this.blocksNeedingUpdate);
        this.blocksNeedingUpdate.clear();

        for (int l = 0; l < arraylist.size(); ++l)
        {
            ChunkPosition chunkposition = (ChunkPosition)arraylist.get(l);
            par1World.notifyBlocksOfNeighborChange(chunkposition.chunkPosX, chunkposition.chunkPosY, chunkposition.chunkPosZ, this);
        }
    }

    private void calculateCurrentChanges(World par1World, int par2, int par3, int par4, int par5, int par6, int par7)
    {
        
    }

    /**
     * Calls World.notifyBlocksOfNeighborChange() for all neighboring blocks, but only if the given block is a redstone
     * wire.
     */
    private void notifyWireNeighborsOfNeighborChange(World world, int x, int y, int z)
    {
        if (world.getBlock(x, y, z) == this)
        {
            world.notifyBlocksOfNeighborChange(x, y, z, this);
            world.notifyBlocksOfNeighborChange(x - 1, y, z, this);
            world.notifyBlocksOfNeighborChange(x + 1, y, z, this);
            world.notifyBlocksOfNeighborChange(x, y, z - 1, this);
            world.notifyBlocksOfNeighborChange(x, y, z + 1, this);
            world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
            world.notifyBlocksOfNeighborChange(x, y + 1, z, this);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);

        if (!world.isRemote)
        {
            this.updateAndPropagateCurrentStrength(world, x, y, z);
            world.notifyBlocksOfNeighborChange(x, y + 1, z, this);
            world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
            this.notifyWireNeighborsOfNeighborChange(world, x - 1, y, z);
            this.notifyWireNeighborsOfNeighborChange(world, x + 1, y, z);
            this.notifyWireNeighborsOfNeighborChange(world, x, y, z - 1);
            this.notifyWireNeighborsOfNeighborChange(world, x, y, z + 1);

            if (world.getBlock(x - 1, y, z).isNormalCube())
            {
                this.notifyWireNeighborsOfNeighborChange(world, x - 1, y + 1, z);
            }
            else
            {
                this.notifyWireNeighborsOfNeighborChange(world, x - 1, y - 1, z);
            }

            if (world.getBlock(x + 1, y, z).isNormalCube())
            {
                this.notifyWireNeighborsOfNeighborChange(world, x + 1, y + 1, z);
            }
            else
            {
                this.notifyWireNeighborsOfNeighborChange(world, x + 1, y - 1, z);
            }

            if (world.getBlock(x, y, z - 1).isNormalCube())
            {
                this.notifyWireNeighborsOfNeighborChange(world, x, y + 1, z - 1);
            }
            else
            {
                this.notifyWireNeighborsOfNeighborChange(world, x, y - 1, z - 1);
            }

            if (world.getBlock(x, y, z + 1).isNormalCube())
            {
                this.notifyWireNeighborsOfNeighborChange(world, x, y + 1, z + 1);
            }
            else
            {
                this.notifyWireNeighborsOfNeighborChange(world, x, y - 1, z + 1);
            }
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);

        if (!par1World.isRemote)
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this);
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this);
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this);
            this.updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3, par4);
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3, par4);
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 - 1);
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 + 1);

            if (par1World.getBlock(par2 - 1, par3, par4).isNormalCube())
            {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 + 1, par4);
            }
            else
            {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 - 1, par4);
            }

            if (par1World.getBlock(par2 + 1, par3, par4).isNormalCube())
            {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 + 1, par4);
            }
            else
            {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 - 1, par4);
            }

            if (par1World.getBlock(par2, par3, par4 - 1).isNormalCube())
            {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 - 1);
            }
            else
            {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 - 1);
            }

            if (par1World.getBlock(par2, par3, par4 + 1).isNormalCube())
            {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 + 1);
            }
            else
            {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 + 1);
            }
        }
    }

    /**
     * Returns the current strength at the specified block if it is greater than the passed value, or the passed value
     * otherwise. Signature: (world, x, y, z, strength)
     */
    private int getMaxCurrentStrength(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par1World.getBlock(par2, par3, par4) != this)
        {
            return par5;
        }
        else
        {
            int i1 = par1World.getBlockMetadata(par2, par3, par4);
            return i1 > par5 ? i1 : par5;
        }
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return ModItems.thermitedust;
    }

    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return 0;
    }

    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par1IBlockAccess.getBlock(par2, par3, par4) != ModBlocks.thermitewire)
        {
            return 0;
        }
        if (!this.wiresProvidePower)
        {
            return 0;
        }
        else
        {
            int i1 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

            if (i1 == 0)
            {
                return 0;
            }
            else if (par5 == 1)
            {
                return i1;
            }
            else
            {
                boolean flag = isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3, par4, 1) || !par1IBlockAccess.getBlock(par2 - 1, par3, par4).isNormalCube() && isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3 - 1, par4, -1);
                boolean flag1 = isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3, par4, 3) || !par1IBlockAccess.getBlock(par2 + 1, par3, par4).isNormalCube() && isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3 - 1, par4, -1);
                boolean flag2 = isPoweredOrRepeater(par1IBlockAccess, par2, par3, par4 - 1, 2) || !par1IBlockAccess.getBlock(par2, par3, par4 - 1).isNormalCube() && isPoweredOrRepeater(par1IBlockAccess, par2, par3 - 1, par4 - 1, -1);
                boolean flag3 = isPoweredOrRepeater(par1IBlockAccess, par2, par3, par4 + 1, 0) || !par1IBlockAccess.getBlock(par2, par3, par4 + 1).isNormalCube() && isPoweredOrRepeater(par1IBlockAccess, par2, par3 - 1, par4 + 1, -1);

                if (!par1IBlockAccess.getBlock(par2, par3 + 1, par4).isNormalCube())
                {
                    if (par1IBlockAccess.getBlock(par2 - 1, par3, par4).isNormalCube() && isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3 + 1, par4, -1))
                    {
                        flag = true;
                    }

                    if (par1IBlockAccess.getBlock(par2 + 1, par3, par4).isNormalCube() && isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3 + 1, par4, -1))
                    {
                        flag1 = true;
                    }

                    if (par1IBlockAccess.getBlock(par2, par3, par4 - 1).isNormalCube() && isPoweredOrRepeater(par1IBlockAccess, par2, par3 + 1, par4 - 1, -1))
                    {
                        flag2 = true;
                    }

                    if (par1IBlockAccess.getBlock(par2, par3, par4 + 1).isNormalCube() && isPoweredOrRepeater(par1IBlockAccess, par2, par3 + 1, par4 + 1, -1))
                    {
                        flag3 = true;
                    }
                }

                return !flag2 && !flag1 && !flag && !flag3 && par5 >= 2 && par5 <= 5 ? i1 : (par5 == 2 && flag2 && !flag && !flag1 ? i1 : (par5 == 3 && flag3 && !flag && !flag1 ? i1 : (par5 == 4 && flag && !flag2 && !flag3 ? i1 : (par5 == 5 && flag1 && !flag2 && !flag3 ? i1 : 0))));
            }
        }
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return false;
    }

    /**
     * Returns true if redstone wire can connect to the specified block. Params: World, X, Y, Z, side (not a normal
     * notch-side, this can be 0, 1, 2, 3 or -1)
     */
    public static boolean isPowerProviderOrWire(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4)
    {
        return par0IBlockAccess.getBlock(par1, par2, par3) == ModBlocks.thermitewire || par0IBlockAccess.getBlock(par1, par2, par3) == ModBlocks.thermiteBlock;
    }

    /**
     * Returns true if the block coordinate passed can provide power, or is a redstone wire, or if its a repeater that
     * is powered.
     */
    public static boolean isPoweredOrRepeater(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4)
    {
        return par0IBlockAccess.getBlock(par1, par2, par3) == ModBlocks.thermitewire || par0IBlockAccess.getBlock(par1, par2, par3) == ModBlocks.thermiteBlock;
    }

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return ModItems.thermitedust;
    }
    
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.crossicon = par1IconRegister.registerIcon("redstone_dust_cross");
        this.lineicon = par1IconRegister.registerIcon("redstone_dust_line");
        this.cross_overlayicon = par1IconRegister.registerIcon("redstone_dust_overlay");
        this.line_overlayicon = par1IconRegister.registerIcon("redstone_dust_overlay");
        this.blockIcon = this.crossicon;
    }

    @SideOnly(Side.CLIENT)
    public static IIcon getRedstoneWireIcon(String par0Str)
    {
        return par0Str.equals("cross") ? crossicon : (par0Str.equals("line") ? lineicon : (par0Str.equals("cross_overlay") ? cross_overlayicon : (par0Str.equals("line_overlay") ? line_overlayicon : null)));
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
    
    @Override
    protected void react(World w, int x, int y, int z){
    }
}
