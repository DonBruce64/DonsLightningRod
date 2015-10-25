package donslightningrod.tileentities;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class TileEntityLightningRodRender extends TileEntitySpecialRenderer {
	private static final IModelCustom lightningRod = AdvancedModelLoader.loadModel(new ResourceLocation("dlr", "models/lightningrod.obj"));
	private static final ResourceLocation texture = new ResourceLocation("minecraft:textures/blocks/iron_block.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {
		GL11.glPushMatrix();
		GL11.glTranslated(x+0.5, y, z+0.5);
		bindTexture(texture);
		lightningRod.renderAll();
		GL11.glTranslated(-x-0.5, y, -z-0.5);
		GL11.glPopMatrix();
	}

}
