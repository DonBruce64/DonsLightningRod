package donslightningrod.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.TileEnergyHandler;
import donslightningrod.DLR;

public class TileEntityLightningBank extends TileEnergyHandler {
	private int activeTimer=0;
	private boolean hasPower=false;
	public boolean poweringRod=false;
	
	public TileEntityLightningBank(){
		this.storage=new EnergyStorage(DLR.bankBlockCapacity, DLR.lightningStikePower, DLR.bankBlockMaxExtraction);
	}
	
	@Override
	public void updateEntity(){
		if(hasPower){
			poweringRod=true;
		}else{
			poweringRod=false;
		}
		hasPower=false;
		for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS){
			if(!direction.equals(ForgeDirection.DOWN) && !direction.equals(ForgeDirection.UP)){
				TileEntity test = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
				if(test instanceof IEnergyReceiver){
					IEnergyReceiver connector = (IEnergyReceiver) test;
					if(connector.canConnectEnergy(direction)){
						connector.receiveEnergy(direction, storage.extractEnergy(storage.getMaxExtract(), false), false);
						return;
					}
				}
			}
		}
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
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
		if(worldObj.isRemote || from.equals(ForgeDirection.UP) || from.equals(ForgeDirection.DOWN)){
			return 0;
		}else{
			return this.storage.extractEnergy(maxExtract, simulate);
		}
	}
}
