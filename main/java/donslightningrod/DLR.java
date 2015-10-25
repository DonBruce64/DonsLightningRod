package donslightningrod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;


@Mod(modid = DLR.MODID, name = DLR.MODNAME, version = DLR.MODVER)
public class DLR{
	public static final String MODID="dlr";
	public static final String MODNAME="Don's Lightning Rod";
	public static final String MODVER="2.1.0";
	
	@Instance(value = DLR.MODID)
	public static DLR instance;
	@SidedProxy(clientSide="donslightningrod.ClientProxy", serverSide="donslightningrod.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void Init(FMLInitializationEvent event){
		proxy.init();
	}
}