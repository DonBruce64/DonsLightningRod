package donslightningrod.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import donslightningrod.blocks.BlockLightningBank;
import donslightningrod.blocks.BlockLightningRod;

public class TileEntityLightningRod extends TileEntity{
	private static long lastCaughtStrikeTime;
	
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
					if(++timeSinceLastBolt-(100*(256-rodHeight))>0){
						worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, xCoord, yCoord+1, zCoord));
						timeSinceLastBolt=0;
					}
				}
			}
			
			if(worldObj.weatherEffects.size()>0){
				for(int i=0; i<worldObj.weatherEffects.size(); ++i){
					if(worldObj.weatherEffects.get(i) instanceof EntityLightningBolt){
						EntityLightningBolt bolt=(EntityLightningBolt) worldObj.weatherEffects.get(i);
						double yDist = this.yCoord - bolt.posY;
						System.out.format("Effects:%d DistY:%f DistX:%f DistZ:%f\n", worldObj.weatherEffects.size(), yDist, Math.abs(this.xCoord - bolt.posX), Math.abs(this.zCoord - bolt.posZ));
						if(yDist <= rodHeight && yDist > 0 && Math.abs(this.xCoord - bolt.posX) <= yDist && Math.abs(this.zCoord - bolt.posZ) <= yDist){
							if(!(worldObj.getBlock((int) bolt.posX, (int) bolt.posY - 1, (int) bolt.posZ) instanceof BlockLightningRod)){
								if(lastCaughtStrikeTime != worldObj.getTotalWorldTime()){
									lastCaughtStrikeTime = worldObj.getTotalWorldTime();
									spawnRodStrike();
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
	}
	
	
	private void extinguishFire(EntityLightningBolt bolt){
		for(int i=-2; i<=2; ++i){
			for(int j=-2; j<=2; ++j){
				for(int k=-2; k<=2; ++k){
					if(worldObj.getBlock(MathHelper.floor_double(bolt.posX)+i, MathHelper.floor_double(bolt.posY)+j, MathHelper.floor_double(bolt.posZ)+k).equals(Blocks.fire)){
						worldObj.setBlockToAir(MathHelper.floor_double(bolt.posX)+i, MathHelper.floor_double(bolt.posY)+j, MathHelper.floor_double(bolt.posZ)+k);
					}
				}
			}	
		}
	}
	
	private void spawnRodStrike(){
		worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, xCoord, yCoord+1, zCoord));
		if(connectedBank != null){
			timeSinceLastBolt=0;
			if(connectedBank.receiveEnergy(null, 5000, false)<1000){
				worldObj.removeTileEntity(connectedBank.xCoord, connectedBank.yCoord, connectedBank.zCoord);
				worldObj.createExplosion(null, connectedBank.xCoord, connectedBank.yCoord, connectedBank.zCoord, 1.0F, true);
				worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, connectedBank.xCoord, connectedBank.yCoord, connectedBank.zCoord));
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
