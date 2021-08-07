/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.client.lib.LibResources;
import vazkii.botania.common.block.tile.corporea.TileCorporeaIndex;

import javax.annotation.Nullable;

public class RenderTileCorporeaIndex implements BlockEntityRenderer<TileCorporeaIndex> {
	private static final RenderType LAYER = RenderType.entityCutoutNoCull(new ResourceLocation(LibResources.MODEL_CORPOREA_INDEX));
	private static final float ANGLE = (float) Math.sin(Math.toRadians(45));
	private final ModelPart ring;
	private final ModelPart cube;

	public RenderTileCorporeaIndex(BlockEntityRendererProvider.Context ctx) {
		this.ring = new ModelPart(64, 32, 0, 0);
		this.ring.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
		this.cube = new ModelPart(64, 32, 32, 0);
		this.cube.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
	}

	@Override
	public void render(@Nullable TileCorporeaIndex index, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
		ms.pushPose();
		ms.translate(0.5, 0, 0.5);
		float translation = index != null ? (float) ((Math.cos((index.ticksWithCloseby + (index.hasCloseby ? partialTicks : 0)) / 10F) * 0.5 + 0.5) * 0.25) : 0F;
		float rotation = index != null ? index.ticks * 2 + partialTicks : 0F;
		VertexConsumer buffer = buffers.getBuffer(LAYER);
		ms.pushPose();
		ms.translate(0.0D, -1, 0.0D);

		ms.mulPose(Vector3f.YP.rotationDegrees(rotation));
		ms.translate(0.0D, 1.5F + translation / 2.0F, 0.0D);
		ms.mulPose(new Quaternion(new Vector3f(ANGLE, 0.0F, ANGLE), 60.0F, true));
		this.ring.render(ms, buffer, light, overlay);
		ms.scale(0.875F, 0.875F, 0.875F);
		ms.mulPose(new Quaternion(new Vector3f(ANGLE, 0.0F, ANGLE), 60.0F, true));
		ms.mulPose(Vector3f.YP.rotationDegrees(rotation));
		this.ring.render(ms, buffer, light, overlay);
		ms.scale(0.875F, 0.875F, 0.875F);
		ms.mulPose(new Quaternion(new Vector3f(ANGLE, 0.0F, ANGLE), 60.0F, true));
		ms.mulPose(Vector3f.YP.rotationDegrees(rotation));
		this.cube.render(ms, buffer, light, overlay);
		ms.popPose();

		if (index != null && index.closeby > 0F) {
			float starScale = 0.02F;
			float starRadius = (float) TileCorporeaIndex.RADIUS * index.closeby + (index.closeby == 1F ? 0F : index.hasCloseby ? partialTicks : -partialTicks) * 0.2F;
			double rads = (index.ticksWithCloseby + partialTicks) * 2 * Math.PI / 180;
			double starX = Math.cos(rads) * starRadius;
			double starZ = Math.sin(rads) * starRadius;
			int color = 0xFF00FF;
			int seed = index.getBlockPos().getX() ^ index.getBlockPos().getY() ^ index.getBlockPos().getZ();

			ms.translate(starX, 0.3, starZ);
			RenderHelper.renderStar(ms, buffers, color, starScale, starScale, starScale, seed);
			ms.translate(-starX * 2, 0, -starZ * 2);
			RenderHelper.renderStar(ms, buffers, color, starScale, starScale, starScale, seed);
			ms.translate(starX, 0, starZ);

			rads = -rads;
			starX = Math.cos(rads) * starRadius;
			starZ = Math.sin(rads) * starRadius;
			ms.translate(starX, 0, starZ);
			RenderHelper.renderStar(ms, buffers, color, starScale, starScale, starScale, seed);
			ms.translate(-starX * 2, 0, -starZ * 2);
			RenderHelper.renderStar(ms, buffers, color, starScale, starScale, starScale, seed);
			ms.translate(starX, 0, starZ);
		}
		ms.popPose();
	}

}
