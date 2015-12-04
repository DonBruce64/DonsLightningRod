package donslightningrod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGagLightningRod extends Block{
	public BlockGagLightningRod(){
		super(Material.iron);
		this.setUnlocalizedName("GagLightningRod");
		this.setBlockBounds(0.4275F, -1.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
	}
	
	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos.offset(EnumFacing.DOWN));
        worldIn.setBlockToAir(pos.offset(EnumFacing.DOWN));
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side){
		   return false;
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
            return -1;
    }
}
