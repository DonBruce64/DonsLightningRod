package donslightningrod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = DLR.MODID, name = DLR.MODNAME, version = DLR.MODVER)
public class DLR{
	public static final String MODID="dlr";
	public static final String MODNAME="Don's Lightning Rod";
	public static final String MODVER="2.3.2";
	
	@Instance(value = DLR.MODID)
	public static DLR instance;
	@SidedProxy(clientSide="donslightningrod.ClientProxy", serverSide="donslightningrod.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void Init(FMLInitializationEvent event){
		proxy.init();
	}
}
