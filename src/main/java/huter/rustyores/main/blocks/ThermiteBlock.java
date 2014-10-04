package huter.rustyores.main.blocks;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import huter.rustyores.main.items.ModItems;
import huter.rustyores.main.lib.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class ThermiteBlock extends Block {
	
	public static final String name = "thermiteBlock";

	protected ThermiteBlock() {
		super(Material.sand);
		this.setBlockName(Constants.MODID + "_"  + name);
        this.setBlockTextureName(Constants.MODID + ":" + name);
        GameRegistry.registerBlock(this, name);
	}
	
	@Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return ModItems.thermitedust;
    }	
	
	
}
