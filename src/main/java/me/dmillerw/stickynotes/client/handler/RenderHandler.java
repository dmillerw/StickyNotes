package me.dmillerw.stickynotes.client.handler;

import com.google.common.collect.Lists;
import me.dmillerw.stickynotes.block.BlockRegistry;
import me.dmillerw.stickynotes.block.tile.TileStickyNote;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

/**
 * @author dmillerw
 */
public class RenderHandler {

    private static final int LINE_SPACING = 4;
    private static final int NOTE_RENDER_DElAY = 10;
    private static final int MAX_NOTE_WIDTH = 350;

    private static BlockPos mousedOver = null;
    private static int mousedOverCounter = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;

        Minecraft minecraft = Minecraft.getMinecraft();
        RayTraceResult result = minecraft.objectMouseOver;

        if (result == null) {
            mousedOverCounter = 0;
            mousedOver = null;
            return;
        }

        if (result.typeOfHit != RayTraceResult.Type.BLOCK) {
            mousedOverCounter = 0;
            mousedOver = null;
            return;
        }

        BlockPos pos = result.getBlockPos();
        if (pos != null && mousedOver == null) {
            mousedOverCounter = 0;
            mousedOver = pos;
        } else if (pos == null && mousedOver != null) {
            mousedOverCounter = 0;
            mousedOver = pos;
        } else {
            if (!pos.equals(mousedOver)) {
                mousedOverCounter = 0;
                mousedOver = pos;
            } else {
                if (mousedOverCounter < NOTE_RENDER_DElAY)
                    mousedOverCounter++;
            }
        }
    }

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;

        Minecraft minecraft = Minecraft.getMinecraft();
        RayTraceResult result = minecraft.objectMouseOver;

        if (result == null)
            return;

        if (result.typeOfHit != RayTraceResult.Type.BLOCK)
            return;

        IBlockState state = minecraft.world.getBlockState(result.getBlockPos());
        if (state.getBlock() != BlockRegistry.sticky_note)
            return;

        FontRenderer fontRenderer = minecraft.fontRendererObj;
        ScaledResolution resolution = new ScaledResolution(minecraft);

        double centerX = resolution.getScaledWidth_double() / 2;
        double centerY = resolution.getScaledHeight_double() / 2;

        if (mousedOverCounter < NOTE_RENDER_DElAY) {
            fontRenderer.drawString(Integer.toString(mousedOverCounter), (int)centerX, (int)centerY, 0xFFFFFF);
            return;
        }

        TileStickyNote tile = (TileStickyNote) minecraft.world.getTileEntity(result.getBlockPos());

        int maxStringWidth = 0;
        int height = 0;

        List<String> note = Lists.newArrayList();
        for (String string : tile.note) {
            note.addAll(fontRenderer.listFormattedStringToWidth(string, MAX_NOTE_WIDTH));
        }

        for (String string : note) {
            int width = fontRenderer.getStringWidth(string);
            if (width > maxStringWidth) maxStringWidth = width;
            height += fontRenderer.FONT_HEIGHT + LINE_SPACING;
        }

        maxStringWidth += LINE_SPACING * 2;

        double halfWidth = (double)maxStringWidth / (double)2;
        double halfHeight = (double)height / (double)2;

        drawRectangle(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight, 0, 0, 0, 200);

        int index = 0 ;
        for (String string : note) {
            int width = (int)(fontRenderer.getStringWidth(string) / 2F);

            int x = (int)(centerX - width);
            int y = (int)((centerY - halfHeight + LINE_SPACING) + (fontRenderer.FONT_HEIGHT + LINE_SPACING) * index);

            fontRenderer.drawString(string, x, y, 0xFFFFFF);

            index++;
        }
    }

    private static void drawRectangle(double left, double top, double right, double bottom, float r, float g, float b, float a) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        if (r > 1) r = r / 255F;
        if (g > 1) g = g / 255F;
        if (b > 1) b = b / 255F;
        if (a > 1) a = a / 255F;

        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        GlStateManager.color(r, g, b, a);

        vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(left, bottom, 0.0D).endVertex();
        vertexbuffer.pos(right, bottom, 0.0D).endVertex();
        vertexbuffer.pos(right, top, 0.0D).endVertex();
        vertexbuffer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
