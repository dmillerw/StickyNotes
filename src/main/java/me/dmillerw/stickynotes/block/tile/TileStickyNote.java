package me.dmillerw.stickynotes.block.tile;

import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import java.util.List;

/**
 * @author dmillerw
 */
public class TileStickyNote extends TileEntity {

    public List<String> note = Lists.newArrayList();

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        NBTTagList list = new NBTTagList();
        for (String n : note) list.appendTag(new NBTTagString(n));
        compound.setTag("note", list);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        note.clear();

        NBTTagList list = compound.getTagList("note", Constants.NBT.TAG_STRING);
        for (int i=0; i<list.tagCount(); i++) {
            note.add(((NBTTagString)list.get(i)).getString());
        }
    }

    public TileStickyNote() {
        note.add("STICKY NOTES!");
        note.add("They're notes, and they're sticky, and they can be stuck to blocks and will display their contents when looked at (after a configurable delay to prevent spam)");
        note.add("Bacon ipsum dolor amet pork loin strip steak tri-tip brisket. Ribeye shank short loin, kielbasa pancetta swine jowl meatloaf. Drumstick jowl sausage kevin pork belly ham hock ground round bacon. Alcatra biltong bacon, meatball strip steak cupim pig cow picanha chuck tri-tip pancetta sausage andouille pastrami. Frankfurter shankle filet mignon bresaola, picanha rump strip steak. Landjaeger pork chop tail meatball pork, cow short loin filet mignon. Fatback pork loin turkey sausage.");
    }
}
