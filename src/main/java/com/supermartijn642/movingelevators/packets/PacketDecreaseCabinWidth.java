package com.supermartijn642.movingelevators.packets;

import com.supermartijn642.core.network.BlockEntityBasePacket;
import com.supermartijn642.core.network.PacketContext;
import com.supermartijn642.movingelevators.blocks.ControllerBlockEntity;
import net.minecraft.core.BlockPos;

/**
 * Created 4/3/2020 by SuperMartijn642
 */
public class PacketDecreaseCabinWidth extends BlockEntityBasePacket<ControllerBlockEntity> {

    public PacketDecreaseCabinWidth(BlockPos pos){
        super(pos);
    }

    public PacketDecreaseCabinWidth(){
    }

    @Override
    protected void handle(ControllerBlockEntity elevatorEntity, PacketContext packetContext){
        elevatorEntity.getGroup().decreaseCageWidth();
    }
}
