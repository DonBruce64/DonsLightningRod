package donslightningrod.tileentities;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class TileEntityLightningRodItemRenderer implements IItemRenderer {
	private static final IModelCustom lightningRod = AdvancedModelLoader.loadModel(new ResourceLocation("dlr", "models/lightningrod.obj"));
	private static final ResourceLocation texture = new ResourceLocation("minecraft:textures/blocks/iron_block.png");
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type){
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data){
		GL11.glPushMatrix();
		GL11.glTranslatef(0, -.9F, 0);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		lightningRod.renderAll();
		GL11.glTranslatef(0, .9F, 0);
		GL11.glPopMatrix();
	}

}
