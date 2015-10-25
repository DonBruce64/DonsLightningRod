package donslightningrod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import donslightningrod.tileentities.TileEntityLightningBank;

public class BlockLightningBank extends Block implements ITileEntityProvider{
	private IIcon topIcon;
	private IIcon bottomIcon;
	private IIcon sideIcon;
	
	public BlockLightningBank(){
		super(Material.iron);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setBlockName("LightningBank");
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityLightningBank();
	}
	
	@Override
	public boolean hasTileEntity(int metadata){
		return true;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister){
		topIcon=par1IconRegister.registerIcon("dlr:lightningbank_top");
		bottomIcon=par1IconRegister.registerIcon("dlr:lightningbank_bottom");
		sideIcon=par1IconRegister.registerIcon("dlr:lightningbank_side");
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
		if(side==0){
			return bottomIcon;
		}else if(side==1){
			return topIcon;
		}else{
			return sideIcon;
		}
	}
}
