package donslightningrod.tileentities;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import donslightningrod.DLR;

public class TileEntityLightningRod extends TileEntity{
	private int rodHeight=getRodHeight();
	private long timeSinceLastBolt=0;
	private boolean isConnectedToBank=false;
	private TileEntityLightningBank connectedBank;
	
	@Override
	public void updateEntity(){
		timeSinceLastBolt++;
		rodHeight=getRodHeight();
		if(rodHeight==-1){return;}
		if(this.isConnectedToBank  && worldObj.isThundering()){
			if(connectedBank.poweringRod){
				//System.out.println(timeSinceLastBolt-(100*(256-rodHeight)));
				if(timeSinceLastBolt-(100*(256-rodHeight))>0){
					timeSinceLastBolt=0;
					this.worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, xCoord, yCoord+1, zCoord));
				}
			}else{
				timeSinceLastBolt=0;
			}
		}else{
			timeSinceLastBolt=0;
		}
		
		if(worldObj.weatherEffects.size()>0){
			rodHeight=getRodHeight();
			for(int i=0; i<worldObj.weatherEffects.size(); ++i){
				for(int j=1; j<=rodHeight; ++j){
					EntityLightningBolt bolt=(EntityLightningBolt) worldObj.weatherEffects.get(i);
					if(worldObj.getBlock((int) bolt.posX, (int) bolt.posY-1, (int) bolt.posZ).equals(DLR.proxy.lightingRod)){break;}
					if(bolt.boundingBox.intersectsWith(worldObj.getBlock(xCoord, yCoord, zCoord).getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord).getOffsetBoundingBox(0, -j, 0).expand(j, 0, j))){
						timeSinceLastBolt=0;
						this.worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, xCoord, yCoord+1, zCoord));
						if(this.isConnectedToBank && !worldObj.isRemote){
							if(this.connectedBank.receiveEnergy(null, 5000, false)<1000){
								worldObj.removeTileEntity(connectedBank.xCoord, connectedBank.yCoord, connectedBank.zCoord);
								worldObj.createExplosion(null, connectedBank.xCoord, connectedBank.yCoord, connectedBank.zCoord, 1.0F, true);
								this.worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, connectedBank.xCoord, connectedBank.yCoord, connectedBank.zCoord));
							}
						}
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
					if(worldObj.getBlock(MathHelper.floor_double(bolt.posX)+j, MathHelper.floor_double(bolt.posY)+k, MathHelper.floor_double(bolt.posZ)+l).equals(Blocks.fire)){
						worldObj.setBlockToAir(MathHelper.floor_double(bolt.posX)+j, MathHelper.floor_double(bolt.posY)+k, MathHelper.floor_double(bolt.posZ)+l);
					}
				}
			}	
		}
	}
	
	private int getRodHeight(){
		int rodHeight=0;
		boolean grounded=false;
		this.isConnectedToBank=false;
		for(int i=this.yCoord-1; i>0; --i){
			if(worldObj.getBlock(this.xCoord, i, this.zCoord).equals(DLR.proxy.lightningBank)){
				if(!grounded){
					rodHeight++;
					this.isConnectedToBank=true;
					this.connectedBank=(TileEntityLightningBank) worldObj.getTileEntity(this.xCoord, i, this.zCoord);
				}	
				break;
			}else if(worldObj.getBlock(this.xCoord, i, this.zCoord).equals(Blocks.iron_bars)){
				rodHeight++;
			}else if(worldObj.getBlock(this.xCoord, i, this.zCoord).isOpaqueCube()){
				rodHeight++;
				grounded=true;
			}else if(worldObj.getBlock(this.xCoord, i, this.zCoord).equals(Blocks.air)){
				break;				
			}
		}
		return rodHeight;
	}
}
