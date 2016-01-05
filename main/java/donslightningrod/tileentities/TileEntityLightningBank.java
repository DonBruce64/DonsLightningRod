package donslightningrod.tileentities;

import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.TileEnergyHandler;
import donslightningrod.DLR;

public class TileEntityLightningBank extends TileEnergyHandler implements IUpdatePlayerListBox{
	private int activeTimer=0;
	private boolean hasPower=false;
	public boolean poweringRod=false;
	
	public TileEntityLightningBank(){
		this.storage=new EnergyStorage(DLR.bankBlockCapacity, DLR.lightningStikePower, DLR.bankBlockMaxExtraction);
	}
	
	@Override
	public void update(){
		if(hasPower){
			poweringRod=true;
		}else{
			poweringRod=false;
		}
		hasPower=false;
		for(EnumFacing facing : EnumFacing.VALUES){
			if(!facing.equals(EnumFacing.DOWN) && !facing.equals(EnumFacing.UP)){
				TileEntity test = worldObj.getTileEntity(this.pos.offset(facing));
				if(test instanceof IEnergyReceiver){
					IEnergyReceiver connector = (IEnergyReceiver) test;
					if(connector.canConnectEnergy(facing)){
						connector.receiveEnergy(facing, storage.extractEnergy(storage.getMaxExtract(), false), false);
						return;
					}
				}
			}
		}
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
			if(activeTimer==0){
				if(!simulate){
					activeTimer=DLR.bankBlockPowerConsumptionCooldown;
					if(maxReceive < DLR.bankBlockPowerConsumption){
						hasPower = false;
					}else{
						hasPower = true;
						return DLR.bankBlockPowerConsumption;
					}
				}
			}else{
				activeTimer--;
			}
		}
		return 0;
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
