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
import donslightningrod.DLR;
import donslightningrod.tileentities.TileEntityLightningRod;

public class BlockLightningRod extends Block implements ITileEntityProvider{
	private IIcon icon;
	
	public BlockLightningRod(){
		super(Material.iron);
		this.setBlockName("LightningRod");
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setBlockBounds(0.4275F, 0.0F, 0.4375F, 0.5625F, 2.0F, 0.5625F);
	}
	
	@Override
    public void onBlockAdded(World world, int x, int y, int z){
        super.onBlockAdded(world, x, y, z);
        world.setBlock(x, y+1, z, DLR.proxy.gagLightingRod);
    }
	
	@Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata){
        super.breakBlock(world, x, y, z, block, metadata);
        world.setBlockToAir(x, y+1, z);
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityLightningRod();
		
	}
	
    @Override
    public boolean hasTileEntity(int metadata){
        return true;
    }
    
    @Override
    public boolean renderAsNormalBlock(){
    	return false;
    }
    
    @Override
    public boolean isOpaqueCube(){
    	return false;
    }
    
    @Override
    public int getRenderType() {
            return -1;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister){
    	icon = par1IconRegister.registerIcon("minecraft:iron_block");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2){
    	return icon;
	}
}
