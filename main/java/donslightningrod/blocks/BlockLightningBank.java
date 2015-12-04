package donslightningrod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import donslightningrod.tileentities.TileEntityLightningBank;

public class BlockLightningBank extends Block implements ITileEntityProvider{
	
	public BlockLightningBank(){
		super(Material.iron);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setUnlocalizedName("LightningBank");
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityLightningBank();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state){
		return true;
	}
}
