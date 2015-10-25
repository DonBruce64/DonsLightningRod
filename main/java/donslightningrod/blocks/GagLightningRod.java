package donslightningrod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GagLightningRod extends Block{
	private IIcon icon;
	
	public GagLightningRod(){
		super(Material.iron);
		this.setBlockBounds(0.4275F, -1.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
	}
	
	@Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata){
        super.breakBlock(world, x, y, z, block, metadata);
        world.removeTileEntity(x, y, z);
        world.setBlockToAir(x, y-1, z);
    }
	
    @Override
    public boolean shouldSideBeRendered(IBlockAccess block, int x, int y, int z, int metadata){
            return false;
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
