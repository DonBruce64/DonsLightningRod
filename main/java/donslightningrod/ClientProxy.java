package donslightningrod;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import donslightningrod.tileentities.TileEntityLightningRod;
import donslightningrod.tileentities.TileEntityLightningRodItemRenderer;
import donslightningrod.tileentities.TileEntityLightningRodRender;

public class ClientProxy extends CommonProxy{
	public void init(){
		super.init();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLightningRod.class, new TileEntityLightningRodRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(lightingRod), new TileEntityLightningRodItemRenderer());
	}
}
