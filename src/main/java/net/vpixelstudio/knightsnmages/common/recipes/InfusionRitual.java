package net.vpixelstudio.knightsnmages.common.recipes;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.vpixelstudio.knightsnmages.common.blocks.blockentities.AltarTileEntity;
import net.vpixelstudio.knightsnmages.common.blocks.blockentities.PedestalTileEntity;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class InfusionRitual implements Inventory {

    private static final Point[] PEDESTALS_MAP = new Point[]{
            new Point(0, 3),
            new Point(2, 2),
            new Point(3, 0),
            new Point(2, -2),
            new Point(0, -3),
            new Point(-2, -2),
            new Point(-3, 0),
            new Point(-2, 2)
    };

    private World world;
    private BlockPos worldPos;
    private InfusionRitualRecipe activeRecipe;
    private boolean isDirty = false;
    private boolean hasRecipe = false;
    private int progress = 0;
    private int time = 0;

    private final PedestalTileEntity[] catalysts = new PedestalTileEntity[8];
    private final AltarTileEntity input;

    public InfusionRitual(AltarTileEntity pedestal, World world, BlockPos pos) {
        this.input = pedestal;
        this.world = world;
        this.worldPos = pos;
    }

    public void configure() {
        if (world == null || worldPos == null) return;
        for (int i = 0; i < catalysts.length; i++) {
            Point point = PEDESTALS_MAP[i];
            MutableBlockPos checkPos = worldPos.mutable().move(Direction.EAST, point.x).move(Direction.NORTH, point.y);
            BlockEntity catalystEntity = world.isClient
                    ? world.getChunkAt(checkPos).getBlockEntity(checkPos, EntityCreationType.CHECK)
                    : world.getBlockEntity(checkPos);
            if (catalystEntity instanceof PedestalTileEntity) {
                catalysts[i] = (PedestalTileEntity) catalystEntity;
            } else {
                catalysts[i] = null;
            }
        }
    }

    public boolean checkRecipe() {
        if (!isValid()) return false;
        InfusionRitualRecipe recipe = world.getRecipeManager().getRecipeFor(InfusionRitualRecipe.TYPE, this, world).orElse(null);
        if (hasRecipe()) {
            if (recipe == null) {
                reset();
                return false;
            } else if (activeRecipe == null || recipe.getInfusionTime() != time) {
                updateRecipe(recipe);
            }
            return true;
        }
        if (recipe != null) {
            updateRecipe(recipe);
            return true;
        }
        return false;
    }

    private void updateRecipe(InfusionRitualRecipe recipe) {
        activeRecipe = recipe;
        hasRecipe = true;
        resetTimer();
        setChanged();
    }

    private void resetTimer() {
        time = activeRecipe != null ? activeRecipe.getInfusionTime() : 0;
        progress = 0;
    }

    public void reset() {
        activeRecipe = null;
        hasRecipe = false;
        resetTimer();
        setChanged();
    }

    public void tick() {
        if (isDirty) {
            configure();
            isDirty = false;
        }
        if (!checkRecipe()) return;
        progress++;
        if (progress == time) {
            clear();
            input.setItem(0, activeRecipe.assemble(this, world.registryAccess()));
            if (world instanceof ServerLevel sl) {
                sl.getPlayers(p -> p.position()
                                .subtract(new Vec3(worldPos.getX(), worldPos.getY(), worldPos.getZ()))
                                .length() < 16)
                        .forEach(p -> BECriteria.INFUSION_FINISHED.trigger(p));
            }
            reset();
        } else if (world instanceof ServerWorld serverLevel) {
            BlockPos target = worldPos.above();
            double tx = target.getX() + 0.5;
            double ty = target.getY() + 0.5;
            double tz = target.getZ() + 0.5;
            for (PedestalTileEntity catalyst : catalysts) {
                ItemStack stack = catalyst.getItem(0);
                if (!stack.isEmpty()) {
                    BlockPos start = catalyst.getBlockPos();
                    double sx = start.getX() + 0.5;
                    double sy = start.getY() + 1.25;
                    double sz = start.getZ() + 0.5;
                    serverLevel.sendParticles(
                            new InfusionParticleType(stack),
                            sx,
                            sy,
                            sz,
                            0,
                            tx - sx,
                            ty - sy,
                            tz - sz,
                            0.5
                    );
                }
            }
        }

    }

    public boolean isValid() {
        if (world == null || worldPos == null || input == null) return false;
        return Arrays.stream(catalysts).noneMatch(Objects::isNull);
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return isValid();
    }

    public boolean hasRecipe() {
        return hasRecipe;
    }

    public void setLocation(World world, BlockPos pos) {
        this.world = world;
        this.worldPos = pos;
        this.isDirty = true;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot > 8) return ItemStack.EMPTY;
        if (slot == 0) {
            return input.getStack(0);
        } else {
            return catalysts[slot - 1].getStack(0);
        }
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return removeStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot) {
        if (slot > 8) return ItemStack.EMPTY;
        if (slot == 0) {
            return input.removeStack(0);
        } else {
            return catalysts[slot - 1].removeStack(0);
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot > 8) return;
        if (slot == 0) {
            input.setStack(0, stack);
        } else {
            catalysts[slot - 1].setStack(0, stack);
        }
    }

    @Override
    public void markDirty() {
        this.isDirty = true;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        if (!isValid()) return;
        input.clear();
        Arrays.stream(catalysts).forEach(PedestalTileEntity::clear);
    }

    @Override
    public void setChanged() {
        if (isValid()) {
            input.setChanged();
            Arrays.stream(catalysts).forEach(PedestalTileEntity::setChanged);
        }
    }

    public void fromTag(NbtCompound tag) {
        if (tag.contains("recipe")) {
            hasRecipe = tag.getBoolean("recipe");
            progress = tag.getInt("progress");
            time = tag.getInt("time");
        }
    }

    public NbtCompound toTag(NbtCompound tag) {
        if (hasRecipe()) {
            tag.putBoolean("recipe", hasRecipe);
            tag.putInt("progress", progress);
            tag.putInt("time", time);
        }
        return tag;
    }

    public static Point[] getMap() {
        return PEDESTALS_MAP;
    }
}