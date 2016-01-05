package donslightningrod;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = DLR.MODID, name = DLR.MODNAME, version = DLR.MODVER)
public class DLR{
	public static final String MODID="dlr";
	public static final String MODNAME="Don's Lightning Rod";
	public static final String MODVER="2.4.0";
	
	public static int lightningStikePower;
	public static int bankBlockCapacity;
	public static int bankBlockMaxExtraction;
	public static int bankBlockPowerConsumption;
	public static int bankBlockPowerConsumptionCooldown;
	
	@Instance(value = DLR.MODID)
	public static DLR instance;
	@SidedProxy(clientSide="donslightningrod.ClientProxy", serverSide="donslightningrod.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void Init(FMLPreInitializationEvent event){
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		lightningStikePower = config.get(config.CATEGORY_GENERAL, "LightningStrikeEnergyAmount", 5000).getInt();
		bankBlockCapacity = config.get(config.CATEGORY_GENERAL, "BankBlockCapacity", 15000).getInt();
		bankBlockMaxExtraction = config.get(config.CATEGORY_GENERAL, "BankBlockMaxExtraction", 100).getInt();
		bankBlockPowerConsumption = config.get(config.CATEGORY_GENERAL, "BankBlockPowerConsumption", 1).getInt();
		bankBlockPowerConsumptionCooldown = config.get(config.CATEGORY_GENERAL, "BankBlockPowerConsumptionCooldown", 10).getInt();
		config.save();
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event){
		proxy.init();
	}
}
