package donslightningrod.tileentities;

import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.TileEnergyHandler;

public class TileEntityLightningBank extends TileEnergyHandler {
	private int activeTimer=0;
	private boolean hasPower=false;
	public boolean poweringRod=false;
	
	public TileEntityLightningBank(){
		this.storage=new EnergyStorage(15000, 5000, 100);
	}
	
	@Override
	public void updateEntity(){
		if(hasPower){
			poweringRod=true;
		}else{
			poweringRod=false;
		}
		hasPower=false;
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from){
		if(from.equals(ForgeDirection.UP)){
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
		if(from==null){
			return this.storage.receiveEnergy(maxReceive, false);
		}else if(!worldObj.isRemote && from.equals(ForgeDirection.DOWN)){
			hasPower=true;
			if(activeTimer==0){
				if(!simulate){activeTimer=10;}
				return 1;
			}else{
				activeTimer--;
				return 0;
			}
		}else{
			return 0;
		}
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
		if(worldObj.isRemote || from.equals(ForgeDirection.UP) || from.equals(ForgeDirection.DOWN)){
			return 0;
		}else{
			return this.storage.extractEnergy(maxExtract, simulate);
		}
	}

	@Override
	public int getEnergyStored(ForgeDirection from){
		return this.storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from){
		return this.storage.getMaxEnergyStored();
	}

}
