package donslightningrod;

import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class TileEntityLightningRod extends TileEntity  implements IUpdatePlayerListBox{

	@Override
	public void update(){
		if(worldObj.weatherEffects.size()>0){
			int rodHeight=0;
			Block thisBlock = worldObj.getChunkFromBlockCoords(pos).getBlock(pos);
			for(int i=this.pos.getY()-1; i>0; --i){
				if(worldObj.getChunkFromBlockCoords(pos).getBlock(pos.getX(), i, pos.getZ()).equals(Blocks.iron_bars)){
					rodHeight++;
				}else if(worldObj.getChunkFromBlockCoords(pos).getBlock(pos.getX(), i, pos.getZ()).isOpaqueCube()){
					rodHeight++;
				}else if(worldObj.getChunkFromBlockCoords(pos).getBlock(pos.getX(), i, pos.getZ()).equals(Blocks.air)){
					break;
				}
			}
			
			for(int i=0; i<worldObj.weatherEffects.size(); ++i){
				EntityLightningBolt bolt=(EntityLightningBolt) worldObj.weatherEffects.get(i);
				for(int j=1; j<=rodHeight; ++j){
					if(worldObj.getChunkFromBlockCoords(pos).getBlock(bolt.getPosition().down()).equals(DLR.proxy.lightningRod)){break;}
					if(bolt.getEntityBoundingBox().intersectsWith(thisBlock.getCollisionBoundingBox(worldObj, pos, thisBlock.getStateFromMeta(0)).offset(0, -j, 0).expand(j, 0, j))){
						this.worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, pos.getX(), pos.getY()+1, pos.getZ()));
						extinguishFire(bolt);
						bolt.setDead();
					}
				}
			}
		}
	}
	
	private void extinguishFire(EntityLightningBolt bolt){
		for(int j=-2; j<=2; ++j){
			for(int k=-2; k<=2; ++k){
				for(int l=-2; l<=2; ++l){
					if(worldObj.getChunkFromBlockCoords(pos).getBlock(MathHelper.floor_double(bolt.posX)+j, MathHelper.floor_double(bolt.posY)+k, MathHelper.floor_double(bolt.posZ)+l).equals(Blocks.fire)){
						worldObj.setBlockToAir(new BlockPos(MathHelper.floor_double(bolt.posX)+j, MathHelper.floor_double(bolt.posY)+k, MathHelper.floor_double(bolt.posZ)+l));
					}
				}
			}	
		}
	}
}
