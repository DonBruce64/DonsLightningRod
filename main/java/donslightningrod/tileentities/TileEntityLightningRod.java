package donslightningrod.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import donslightningrod.blocks.BlockLightningBank;

public class TileEntityLightningRod extends TileEntity{
	private int rodHeight;
	private long timeSinceLastBolt;
	private TileEntityLightningBank connectedBank;
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		if(!worldObj.isRemote){
			if(worldObj.getWorldTime()%10 == 0){
				refreshRodHeight();
			}
			
			if(connectedBank != null  && worldObj.isThundering()){
				if(connectedBank.poweringRod){
					if(timeSinceLastBolt-(100*(256-rodHeight))>0){
						worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, xCoord, yCoord+1, zCoord));
						timeSinceLastBolt=0;
					}else{
						++timeSinceLastBolt;
					}
				}
			}
			
			if(worldObj.weatherEffects.size()>0){
				for(int i=0; i<worldObj.weatherEffects.size(); ++i){
					for(int j=1; j<rodHeight; ++j){
						if(worldObj.weatherEffects.get(i) instanceof EntityLightningBolt){
							EntityLightningBolt bolt=(EntityLightningBolt) worldObj.weatherEffects.get(i);
							if(bolt.boundingBox.intersectsWith(worldObj.getBlock(xCoord, yCoord, zCoord).getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord - j, zCoord).expand(j, 0, j))){
								System.out.println(bolt.boundingBox.minY+"  "+bolt.boundingBox.maxY+"  "+worldObj.weatherEffects.size());
								worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, xCoord, yCoord+1, zCoord));
								if(connectedBank != null){
									timeSinceLastBolt=0;
									if(connectedBank.receiveEnergy(null, 5000, false)<1000){
										worldObj.removeTileEntity(connectedBank.xCoord, connectedBank.yCoord, connectedBank.zCoord);
										worldObj.createExplosion(null, connectedBank.xCoord, connectedBank.yCoord, connectedBank.zCoord, 1.0F, true);
										worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, connectedBank.xCoord, connectedBank.yCoord, connectedBank.zCoord));
									}
								}
								extinguishFire(bolt);
								bolt.setDead();
								return;
							}
						}
					}
				}
			}
		}
	}
	
	private void extinguishFire(EntityLightningBolt bolt){
		for(int j=-2; j<=2; ++j){
			for(int k=-2; k<=2; ++k){
				for(int l=-2; l<=2; ++l){
					if(worldObj.getBlock(MathHelper.floor_double(bolt.posX)+j, MathHelper.floor_double(bolt.posY)+k, MathHelper.floor_double(bolt.posZ)+l).equals(Blocks.fire)){
						worldObj.setBlockToAir(MathHelper.floor_double(bolt.posX)+j, MathHelper.floor_double(bolt.posY)+k, MathHelper.floor_double(bolt.posZ)+l);
					}
				}
			}	
		}
	}
	
	private void refreshRodHeight(){
		rodHeight=0;
		boolean grounded=false;
		for(int i=this.yCoord-1; i>0; --i){
			Block block = worldObj.getBlock(this.xCoord, i, this.zCoord);
			if(!block.isOpaqueCube() && !block.getMaterial().equals(Material.iron)){
				break;
			}else if(block instanceof BlockLightningBank){
				if(!grounded){
					connectedBank=(TileEntityLightningBank) worldObj.getTileEntity(this.xCoord, i, this.zCoord);
				}
				break;
			}else if(!block.equals(Blocks.iron_bars)){
				grounded = true;
			}
			++rodHeight;
		}
	}
}
