package com.barl_inc.unusual_prehistory.client.inventory;

import com.barl_inc.unusual_prehistory.blocks.entity.TransmogrifierBlockEntity;
import com.barl_inc.unusual_prehistory.registry.UP2MenuTypes;
import com.barl_inc.unusual_prehistory.registry.UP2Recipes;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TransmogrifierMenu extends AbstractContainerMenu {

    private final Container container;
    public final ContainerData data;
    protected final Level level;

    public TransmogrifierMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(4), new SimpleContainerData(4));
    }

    public TransmogrifierMenu(int syncId, Inventory playerInventory, Container container, ContainerData data) {
        super(UP2MenuTypes.TRANSMOGRIFIER.get(), syncId);
        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level();
        this.addSlot(new Slot(container, 0, 37, 31));
        this.addSlot(new TransmogrifierOozeSlot(this, container, 1, 82, 59));
        this.addSlot(new TransmogrifierOutputSlot(playerInventory.player, container, 2, 125, 31));
        this.addPlayerInventory(playerInventory);
        this.addPlayerHotbar(playerInventory);
        this.addDataSlots(data);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 1 && index != 0) {
                if (this.canTransmogrify(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.container.stillValid(player);
    }

    protected boolean canTransmogrify(ItemStack stack) {
        return this.level.getRecipeManager().getRecipeFor(UP2Recipes.TRANSMOGRIFICATION.get(), new SingleRecipeInput(stack), this.level).isPresent();
    }

    protected boolean isFuel(ItemStack stack) {
        return stack.is(UP2ItemTags.TRANSMOGRIFIER_FUEL);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledFuel(int scale) {
        int fuel = this.data.get(0);
        int maxFuel = this.data.get(1);
        if (fuel == 0 || maxFuel == 0) {
            return 0;
        }
        return Mth.ceil((float) scale * (float) fuel / (float) maxFuel);
    }

    public int getScaledProgress(int scale) {
        int progress = this.data.get(2);
        int maxProgress = this.data.get(3);
        if (progress == 0 || maxProgress == 0) {
            return 0;
        }
        return Mth.ceil((float) scale * (float) progress / (float) maxProgress);
    }

    public static class TransmogrifierOutputSlot extends Slot {

        private final Player player;
        private int removeCount;

        public TransmogrifierOutputSlot(Player player, Container container, int slot, int xPosition, int yPosition) {
            super(container, slot, xPosition, yPosition);
            this.player = player;
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return false;
        }

        @Override
        public @NotNull ItemStack remove(int amount) {
            if (this.hasItem()) {
                this.removeCount += Math.min(amount, this.getItem().getCount());
            }
            return super.remove(amount);
        }

        @Override
        public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
            this.checkTakeAchievements(stack);
            super.onTake(player, stack);
        }

        @Override
        protected void onQuickCraft(@NotNull ItemStack stack, int amount) {
            this.removeCount += amount;
            this.checkTakeAchievements(stack);
        }

        @Override
        protected void checkTakeAchievements(ItemStack stack) {
            stack.onCraftedBy(this.player.level(), this.player, this.removeCount);
            if (this.player instanceof ServerPlayer serverplayer) {
                if (this.container instanceof TransmogrifierBlockEntity blockEntity) {
                    blockEntity.awardUsedRecipesAndPopExperience(serverplayer);
                }
            }
            this.removeCount = 0;
        }
    }

    public static class TransmogrifierOozeSlot extends Slot {

        private final TransmogrifierMenu menu;

        public TransmogrifierOozeSlot(TransmogrifierMenu menu, Container inventory, int index, int x, int y) {
            super(inventory, index, x, y);
            this.menu = menu;
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return menu.isFuel(stack);
        }
    }
}