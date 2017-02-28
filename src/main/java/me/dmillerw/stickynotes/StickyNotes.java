package me.dmillerw.stickynotes;

import me.dmillerw.stickynotes.client.handler.RenderHandler;
import me.dmillerw.stickynotes.lib.ModInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author dmillerw
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, acceptedMinecraftVersions = "1.11, 1.11.2")
public class StickyNotes {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(RenderHandler.class);
    }
}
