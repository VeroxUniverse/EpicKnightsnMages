package net.vpixelstudio.knightsnmages.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class AltarBlock extends Block {
    public AltarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return makeShape();
    }

    public VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.625, 0, 1, 0.75, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.25, 0.125, 0.875, 0.625, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.125, 0.0625, 0.9375, 0.25, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0, 1, 0.125, 1));

        return shape;
    }
}