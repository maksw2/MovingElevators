package com.supermartijn642.movingelevators.packets;

import com.supermartijn642.core.network.BlockEntityBasePacket;
import com.supermartijn642.core.network.PacketContext;
import com.supermartijn642.movingelevators.blocks.ControllerBlockEntity;
import com.supermartijn642.movingelevators.gui.ElevatorScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Created 4/21/2020 by SuperMartijn642
 */
public class PacketSetFloorName extends BlockEntityBasePacket<ControllerBlockEntity> {

    public String name;

    public PacketSetFloorName(BlockPos pos, String name){
        super(pos);
        this.name = name;
    }

    public PacketSetFloorName(){
    }

    @Override
    public void write(FriendlyByteBuf buffer){
        super.write(buffer);
        buffer.writeBoolean(this.name == null);
        if(this.name != null)
            buffer.writeUtf(this.name);
    }

    @Override
    public void read(FriendlyByteBuf buffer){
        super.read(buffer);
        this.name = buffer.readBoolean() ? null : buffer.readUtf(32767);
    }

    @Override
    public boolean verify(PacketContext context){
        return this.name == null || this.name.length() <= ElevatorScreen.MAX_NAME_LENGTH;
    }

    @Override
    protected void handle(ControllerBlockEntity blockEntity, PacketContext context){
        blockEntity.setFloorName(this.name == null || this.name.trim().isEmpty() ? null : this.name);
    }
}
