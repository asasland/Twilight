package com.formal.twilight.capability.flowerBag;

import com.formal.twilight.client.ClientModEvents;
import com.formal.twilight.common.CommonEventHandlers;
import com.formal.twilight.container.ContainerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

import static com.mojang.blaze3d.vertex.IVertexBuilder.LOGGER;

public class ContainerFlowerBag extends Container {

    private final ItemStackHandlerFlowerBag itemStackHandlerFlowerBag;
    private final ItemStack itemStackBeingHeld;

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int BAG_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int MAX_EXPECTED_BAG_SLOT_COUNT = 16;

    private static final int BAG_INVENTORY_YPOS = 26;
    private static final int PLAYER_INVENTORY_YPOS = 84;

    /**
     * 新版构造函数：客户端通过此构造函数创建容器，数据通过 PacketBuffer 同步
     */
    public ContainerFlowerBag(int windowId, PlayerInventory playerInventory, PacketBuffer extraData) {

        this(windowId, playerInventory,
                new ItemStackHandlerFlowerBag(extraData.readInt()), // 从网络数据读取背包槽数
                ItemStack.EMPTY); // 客户端无持有物信息，传空
    }

    public static ContainerFlowerBag createContainerClientSide(int windowID, PlayerInventory playerInventory, net.minecraft.network.PacketBuffer extraData) {
        // for this example we use extraData for the server to tell the client how many slots for flower itemstacks the flower bag contains.
        int numberOfFlowerSlots = extraData.readInt();

        try {
            ItemStackHandlerFlowerBag itemStackHandlerFlowerBag = new ItemStackHandlerFlowerBag(numberOfFlowerSlots);

            // on the client side there is no parent ItemStack to communicate with - we use a dummy inventory
            return new ContainerFlowerBag(windowID, playerInventory, itemStackHandlerFlowerBag, ItemStack.EMPTY);
        } catch (IllegalArgumentException iae) {
            LOGGER.warn(iae);
        }
        return null;
    }

    /**
     * 服务器侧构造函数，持有完整的背包数据和物品堆
     */

    public static ContainerFlowerBag createContainerServerSide(int windowID, PlayerInventory playerInventory, ItemStackHandlerFlowerBag bagContents,
                                                               ItemStack flowerBag) {
        return new ContainerFlowerBag(windowID, playerInventory, bagContents, flowerBag);
    }

    public ContainerFlowerBag(int windowId, PlayerInventory playerInventory,
                              ItemStackHandlerFlowerBag itemStackHandlerFlowerBag,
                              ItemStack itemStackBeingHeld) {
        super(ContainerRegistry.CONTAINER_FLOWER_BAG.get(), windowId);
        this.itemStackHandlerFlowerBag = itemStackHandlerFlowerBag;
        this.itemStackBeingHeld = itemStackBeingHeld;

        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;
        final int HOTBAR_XPOS = 8;
        final int HOTBAR_YPOS = 142;

        // 玩家热键栏槽位
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            addSlot(new Slot(playerInventory, x, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }

        final int PLAYER_INVENTORY_XPOS = 8;
        // 玩家主背包槽位
        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlot(new Slot(playerInventory, slotNumber, xpos, ypos));
            }
        }

        int bagSlotCount = itemStackHandlerFlowerBag.getSlots();
        if (bagSlotCount < 1 || bagSlotCount > MAX_EXPECTED_BAG_SLOT_COUNT) {
            LOGGER.warn("Unexpected invalid slot count in ItemStackHandlerFlowerBag(" + bagSlotCount + ")");
            bagSlotCount = MathHelper.clamp(bagSlotCount, 1, MAX_EXPECTED_BAG_SLOT_COUNT);
        }

        final int BAG_SLOTS_PER_ROW = 8;
        final int BAG_INVENTORY_XPOS = 17;
        // 背包内部槽位
        for (int bagSlot = 0; bagSlot < bagSlotCount; ++bagSlot) {
            int bagRow = bagSlot / BAG_SLOTS_PER_ROW;
            int bagCol = bagSlot % BAG_SLOTS_PER_ROW;
            int xpos = BAG_INVENTORY_XPOS + SLOT_X_SPACING * bagCol;
            int ypos = BAG_INVENTORY_YPOS + SLOT_Y_SPACING * bagRow;
            addSlot(new SlotItemHandler(itemStackHandlerFlowerBag, bagSlot, xpos, ypos));
        }
    }

    /**
     * 判断容器是否仍可使用
     * 确保玩家手中物品和打开容器时一致，防止空手或换物品时继续操作
     */
    @Override
    public boolean stillValid(PlayerEntity player) {
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        return (!main.isEmpty() && ItemStack.isSame(main, itemStackBeingHeld)) ||
                (!off.isEmpty() && ItemStack.isSame(off, itemStackBeingHeld));
    }

    /**
     * 快捷移动物品逻辑，实现Shift+点击物品移动
     */
    @Nonnull
    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int sourceSlotIndex) {
        Slot sourceSlot = this.slots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();
        final int BAG_SLOT_COUNT = itemStackHandlerFlowerBag.getSlots();

        if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, BAG_INVENTORY_FIRST_SLOT_INDEX, BAG_INVENTORY_FIRST_SLOT_INDEX + BAG_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (sourceSlotIndex >= BAG_INVENTORY_FIRST_SLOT_INDEX && sourceSlotIndex < BAG_INVENTORY_FIRST_SLOT_INDEX + BAG_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            LOGGER.warn("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

}
