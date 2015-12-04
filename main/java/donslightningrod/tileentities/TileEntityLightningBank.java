package donslightningrod.tileentities;

import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.EnumFacing;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.TileEnergyHandler;

public class TileEntityLightningBank extends TileEnergyHandler implements IUpdatePlayerListBox{
	private int activeTimer=0;
	private boolean hasPower=false;
	public boolean poweringRod=false;
	
	public TileEntityLightningBank(){
		this.storage=new EnergyStorage(15000, 5000, 100);
	}
	
	@Override
	public void update(){
		if(hasPower){
			poweringRod=true;
		}else{
			poweringRod=false;
		}
		hasPower=false;
	}
	
	@Override
	public boolean canConnectEnergy(EnumFacing facing){
		if(facing.equals(EnumFacing.UP)){
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public int receiveEnergy(EnumFacing facing, int maxReceive, boolean simulate){
		if(facing==null){
			return this.storage.receiveEnergy(maxReceive, false);
		}else if(!worldObj.isRemote && facing.equals(EnumFacing.DOWN)){
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
	public int extractEnergy(EnumFacing facing, int maxExtract, boolean simulate){
		if(worldObj.isRemote || facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)){
			return 0;
		}else{
			return this.storage.extractEnergy(maxExtract, simulate);
		}
	}

	@Override
	public int getEnergyStored(EnumFacing facing){
		return this.storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing facing){
		return this.storage.getMaxEnergyStored();
	}

}
