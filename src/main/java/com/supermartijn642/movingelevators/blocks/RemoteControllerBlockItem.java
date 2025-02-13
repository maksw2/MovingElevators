package com.supermartijn642.movingelevators.blocks;

import com.supermartijn642.core.TextComponents;
import com.supermartijn642.core.item.BaseBlockItem;
import com.supermartijn642.core.item.ItemProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

/**
 * Created 5/5/2020 by SuperMartijn642
 */
public class RemoteControllerBlockItem extends BaseBlockItem {

    public RemoteControllerBlockItem(Block block, ItemProperties properties){
        super(block, properties);
    }

    @Override
    public ItemUseResult interact(ItemStack stack, Player player, InteractionHand hand, Level level){
        if(player.isShiftKeyDown()){
            if(stack.hasTag() && stack.getTag().contains("controllerDim")){
                if(!level.isClientSide){
                    stack.removeTagKey("controllerDim");
                    stack.removeTagKey("controllerX");
                    stack.removeTagKey("controllerY");
                    stack.removeTagKey("controllerZ");
                    player.displayClientMessage(TextComponents.translation("movingelevators.remote_controller.clear").get(), true);
                }
                return ItemUseResult.success(stack);
            }
        }else{
            if(!level.isClientSide){
                if(stack.hasTag() && stack.getTag().contains("controllerDim")){
                    CompoundTag compound = stack.getTag();
                    Component x = TextComponents.number(compound.getInt("controllerX")).color(ChatFormatting.GOLD).get();
                    Component y = TextComponents.number(compound.getInt("controllerY")).color(ChatFormatting.GOLD).get();
                    Component z = TextComponents.number(compound.getInt("controllerZ")).color(ChatFormatting.GOLD).get();
                    Component dimension = TextComponents.dimension(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(compound.getString("controllerDim")))).color(ChatFormatting.GOLD).get();
                    player.displayClientMessage(TextComponents.translation("movingelevators.remote_controller.tooltip.bound", x, y, z, dimension).get(), true);
                }else
                    player.displayClientMessage(TextComponents.translation("movingelevators.remote_controller.tooltip").get(), true);
            }
            return ItemUseResult.success(stack);
        }
        return super.interact(stack, player, hand, level);
    }

    @Override
    public InteractionFeedback interactWithBlock(ItemStack stack, Player player, InteractionHand hand, Level level, BlockPos hitPos, Direction hitSide, Vec3 hitLocation){
        CompoundTag tag = stack.getTag();
        if(tag == null || !tag.contains("controllerDim")){
            if(player != null && !level.isClientSide)
                player.displayClientMessage(TextComponents.translation("movingelevators.remote_controller.not_bound").color(ChatFormatting.RED).get(), true);
            return InteractionFeedback.CONSUME;
        }
        if(!tag.getString("controllerDim").equals(level.dimension().location().toString())){
            if(player != null && !level.isClientSide)
                player.displayClientMessage(TextComponents.translation("movingelevators.remote_controller.wrong_dimension").color(ChatFormatting.RED).get(), true);
            return InteractionFeedback.CONSUME;
        }
        return super.interactWithBlock(stack, player, hand, level, hitPos, hitSide, hitLocation);
    }
}
