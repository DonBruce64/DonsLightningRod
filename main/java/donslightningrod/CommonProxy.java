package donslightningrod;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy{
	public static final Block lightningRod = new BlockLightningRod();
	public static final Block gagLightningRod = new GagLightningRod();
	public void init(){
		GameRegistry.registerBlock(lightningRod, "LightningRod");
		GameRegistry.registerBlock(gagLightningRod, "GagLightningRod");
		GameRegistry.registerTileEntity(TileEntityLightningRod.class, "Lightning Rod Tile Entity");
		GameRegistry.addShapedRecipe(new ItemStack(Item.getItemFromBlock(lightningRod)),
		" A ",
		" A ",
		"ABA",
		'A', new ItemStack(Items.iron_ingot), 'B', new ItemStack(Blocks.iron_block)
		);
	}
}
