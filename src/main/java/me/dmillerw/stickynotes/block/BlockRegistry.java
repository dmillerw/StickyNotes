package me.dmillerw.stickynotes.block;

import me.dmillerw.stickynotes.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author dmillerw
 */
@GameRegistry.ObjectHolder(value = ModInfo.ID)
public class BlockRegistry {

    public static final BlockStickyNote sticky_note = null;
    @GameRegistry.ObjectHolder(ModInfo.ID + ":sticky_note")
    public static final ItemBlock sticky_note_item = null;

    @Mod.EventBusSubscriber
    public static class Registration {

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().registerAll(
                    new BlockStickyNote().setRegistryName(ModInfo.ID, "sticky_note")
            );
        }

        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(
                    new ItemBlock(sticky_note).setRegistryName(ModInfo.ID, "sticky_note")
            );
        }
    }
}
