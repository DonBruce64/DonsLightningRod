package donslightningrod;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;



public class BlockLightningRod extends BlockContainer implements ITileEntityProvider{
	
	public BlockLightningRod(){
		super(Material.iron);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setUnlocalizedName("LightningRod");
		this.setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 2.0F, 0.5625F);
	}
	
	@Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
        super.onBlockAdded(worldIn, pos, state);
        worldIn.setBlockState(pos.offset(EnumFacing.UP), DLR.proxy.gagLightningRod.getDefaultState(), 3);
    }
	
	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        super.breakBlock(worldIn, pos, state);
        worldIn.setBlockToAir(pos.offset(EnumFacing.UP));
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityLightningRod();
	}
	
    @Override
    public boolean hasTileEntity(IBlockState state){
        return true;
    }
    
    @Override
    public boolean isFullCube(){
    	return false;
    }
    
    @Override
    public boolean isOpaqueCube(){
    	return false;
    }
    
    @Override
    public int getRenderType() {
            return 3;
    }
}
