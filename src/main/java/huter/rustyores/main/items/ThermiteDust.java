package huter.rustyores.main.items;

import huter.rustyores.main.blocks.ModBlocks;
import huter.rustyores.main.blocks.ThermiteWire;
import huter.rustyores.main.lib.Constants;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ThermiteDust extends Item {

	public static final String name = "thermitedust";
	
	public ThermiteDust(){
		setUnlocalizedName(Constants.MODID + "_" + name);
		setTextureName(Constants.MODID + ":" + name);
		GameRegistry.registerItem(this, name);
		setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (world.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) != Blocks.snow_layer)
        {
            if (p_77648_7_ == 0)
            {
                --p_77648_5_;
            }

            if (p_77648_7_ == 1)
            {
                ++p_77648_5_;
            }

            if (p_77648_7_ == 2)
            {
                --p_77648_6_;
            }

            if (p_77648_7_ == 3)
            {
                ++p_77648_6_;
            }

            if (p_77648_7_ == 4)
            {
                --p_77648_4_;
            }

            if (p_77648_7_ == 5)
            {
                ++p_77648_4_;
            }

            if (!world.isAirBlock(p_77648_4_, p_77648_5_, p_77648_6_))
            {
                return false;
            }
        }

        if (!player.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, stack))
        {
            return false;
        }
        else
        {
            if (ModBlocks.thermitewire.canPlaceBlockAt(world, p_77648_4_, p_77648_5_, p_77648_6_))
            {
                --stack.stackSize;
                world.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, ModBlocks.thermitewire);
            }

            return true;
        }
    }
}
