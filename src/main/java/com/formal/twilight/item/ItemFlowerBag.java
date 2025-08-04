package com.formal.twilight.item;

import com.formal.twilight.capability.flowerBag.CapabilityProviderFlowerBag;
import com.formal.twilight.capability.flowerBag.ContainerFlowerBag;
import com.formal.twilight.capability.flowerBag.ItemStackHandlerFlowerBag;
import com.formal.twilight.container.ContainerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.mojang.blaze3d.vertex.IVertexBuilder.LOGGER;

public class ItemFlowerBag extends Item {
    private static final int MAXIMUM_NUMBER_OF_FLOWER_BAGS = 1;
    public  ItemFlowerBag(){
        super(new Item.Properties().stacksTo(MAXIMUM_NUMBER_OF_FLOWER_BAGS).tab(ItemGroup.TAB_MATERIALS));
    }
    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {  // server only!
            ITextComponent txt = new StringTextComponent("§a[调试] FlowerBag GUI 尝试打开");
            INamedContainerProvider containerProviderFlowerBag = new ContainerProviderFlowerBag(this, stack);
            final int NUMBER_OF_FLOWER_SLOTS = 16;
            NetworkHooks.openGui((ServerPlayerEntity) player,
                    containerProviderFlowerBag,
                    (packetBuffer)->{packetBuffer.writeInt(NUMBER_OF_FLOWER_SLOTS);});
            player.sendMessage(txt, player.getUUID());
            // We use the packetBuffer to send the bag size; not necessary since it's always 16, but just for illustration purposes
        }
        return ActionResult.success(stack);
    }


    private static class ContainerProviderFlowerBag implements INamedContainerProvider {
        private final ItemFlowerBag itemFlowerBag;
        private final ItemStack itemStackFlowerBag;

        public ContainerProviderFlowerBag(ItemFlowerBag itemFlowerBag, ItemStack itemStackFlowerBag) {
            this.itemFlowerBag = itemFlowerBag;
            this.itemStackFlowerBag = itemStackFlowerBag;
        }

        @Override
        public ITextComponent getDisplayName() {
            return itemStackFlowerBag.getDisplayName();
        }

        @Override
        public ContainerFlowerBag createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
            ContainerFlowerBag newContainerServerSide =
                    ContainerFlowerBag.createContainerServerSide(windowID, playerInventory,
                            itemFlowerBag.getItemStackHandlerFlowerBag(itemStackFlowerBag),
                            itemStackFlowerBag);
            return newContainerServerSide;
        }
    }


    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT oldCapNbt) {
        return new CapabilityProviderFlowerBag();
    }

    public static ItemStackHandlerFlowerBag getItemStackHandlerFlowerBag(ItemStack itemStack) {
        return itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(cap -> cap instanceof ItemStackHandlerFlowerBag)
                .map(cap -> (ItemStackHandlerFlowerBag) cap)
                .orElseGet(() -> {
                    LOGGER.error("ItemFlowerBag did not have the expected ITEM_HANDLER_CAPABILITY");
                    return new ItemStackHandlerFlowerBag(1);
                });
    }

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT baseTag = stack.getTag();
        CompoundNBT capabilityTag = getItemStackHandlerFlowerBag(stack).serializeNBT();
        CompoundNBT combinedTag = new CompoundNBT();
        if (baseTag != null) combinedTag.put("base", baseTag);
        if (capabilityTag != null) combinedTag.put("cap", capabilityTag);
        return combinedTag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt == null) {
            stack.setTag(null);
            return;
        }
        stack.setTag(nbt.getCompound("base"));
        getItemStackHandlerFlowerBag(stack).deserializeNBT(nbt.getCompound("cap"));
    }

    private static final Logger LOGGER = LogManager.getLogger();
}
