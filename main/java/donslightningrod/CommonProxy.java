package donslightningrod;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import donslightningrod.blocks.BlockLightningBank;
import donslightningrod.blocks.BlockLightningRod;
import donslightningrod.blocks.BlockGagLightningRod;
import donslightningrod.tileentities.TileEntityLightningBank;
import donslightningrod.tileentities.TileEntityLightningRod;

public class CommonProxy{
	public static final Block lightningRod = new BlockLightningRod();
	public static final Block gagLightningRod = new BlockGagLightningRod();
	public static final Block lightningBank = new BlockLightningBank();
	public void init(){
		GameRegistry.registerBlock(lightningRod, "LightningRod");
		GameRegistry.registerBlock(gagLightningRod, "GagLightningRod");
		GameRegistry.registerBlock(lightningBank, "LightningBank");
		GameRegistry.registerTileEntity(TileEntityLightningRod.class, "Lightning Rod Tile Entity");
		GameRegistry.registerTileEntity(TileEntityLightningBank.class, "Lightning Bank Tile Entity");
		GameRegistry.addShapedRecipe(new ItemStack(Item.getItemFromBlock(lightningRod)),
		" A ",
		" A ",
		"ABA",
		'A', new ItemStack(Items.iron_ingot), 'B', new ItemStack(Blocks.iron_block)
		);
		GameRegistry.addShapedRecipe(new ItemStack(Item.getItemFromBlock(lightningBank)),
		" A ",
		"CBC",
		" C ",
		'A', new ItemStack(lightningRod),
		'B', new ItemStack(Blocks.iron_block),
		'C', new ItemStack(Items.redstone)
		);
	}
}
