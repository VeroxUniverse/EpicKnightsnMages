package net.vpixelstudio.knightsnmages.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PedestalBlock extends Block {
    public PedestalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return makeShape();
    }

    public VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.125, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.125, 0.25, 0.75, 0.625, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.125, 0.1875, 0.8125, 0.1875, 0.8125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.625, 0.125, 0.875, 0.75, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.5625, 0.1875, 0.8125, 0.625, 0.8125));

        return shape;
    }
}