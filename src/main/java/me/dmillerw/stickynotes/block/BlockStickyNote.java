package me.dmillerw.stickynotes.block;

import me.dmillerw.stickynotes.block.tile.TileStickyNote;
import me.dmillerw.stickynotes.lib.ModInfo;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author dmillerw
 */
public class BlockStickyNote extends BlockContainer {

    protected BlockStickyNote() {
        super(Material.CARPET);

        setCreativeTab(CreativeTabs.MISC);
        setUnlocalizedName(ModInfo.ID + ":sticky_note");
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileStickyNote();
    }
}
