package com.guigs44.farmingforengineers.block;

import com.guigs44.farmingforengineers.FarmingForEngineers;
import com.guigs44.farmingforengineers.network.GuiHandler;
import com.guigs44.farmingforengineers.tile.TileMarket;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * A good chunk of the code in this file has been based on Jason Mitchell's (@mitchej123) work.
 * Specifically:
 * https://github.com/GTNewHorizons/CookingForBlockheads/blob/master/src/main/java/net/blay09/mods/cookingforblockheads/block/BlockBaseKitchen.java
 * https://github.com/GTNewHorizons/CookingForBlockheads/blob/master/src/main/java/net/blay09/mods/cookingforblockheads/block/BlockOven.java
 *
 * Licensed under LGPL-3.0
 */
public class BlockMarket extends BlockContainer {

    // public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockMarket() {
        super(Material.wood);
        setBlockName(FarmingForEngineers.MOD_ID + ":market"); //TODO: Fix the name
        setStepSound(soundTypeWood);
        setHardness(2f);
        setCreativeTab(FarmingForEngineers.creativeTab);
    }

    //	@Override
    //	protected BlockStateContainer createBlockState() {
    //		return new BlockStateContainer(this, FACING);
    //	}

    //	@Override
    //	@SuppressWarnings("deprecation")
    //	public IBlockState getStateFromMeta(int meta) {
    //		EnumFacing facing = EnumFacing.getFront(meta);
    //		if (facing.getAxis() == EnumFacing.Axis.Y) {
    //			facing = EnumFacing.NORTH;
    //		}
    //		return getDefaultState().withProperty(FACING, facing);
    //	}

    //	@Override
    //	public int getMetaFromState(IBlockState state) {
    //		return state.getValue(FACING).getIndex();
    //	}

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMarket();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube() {
        return false;
    }

    //	@Override
    //	@SuppressWarnings("deprecation")
    //	public boolean isFullCube(IBlockState state) {
    //		return false;
    //	}

    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
        findOrientation(worldIn, x, y, z);
    }

    //	@Override
    //	@SuppressWarnings("deprecation")
    //	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float
    // hitZ, int meta, EntityLivingBase placer) {
    //		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    //	}

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack itemStack) {
        //		EnumFacing facing = state.getValue(FACING);
        //		BlockPos entityPos = pos.offset(facing.getOpposite());
        //		EntityMerchant.SpawnAnimationType spawnAnimationType = EntityMerchant.SpawnAnimationType.MAGIC;
        //		if(world.canBlockSeeTheSky(x,y,z)) {
        //			spawnAnimationType = EntityMerchant.SpawnAnimationType.FALLING;
        //		} else if(!world.isAirBlock(x,y-1,z)) {
        //			spawnAnimationType = EntityMerchant.SpawnAnimationType.DIGGING;
        //		}
        //		if(!world.isRemote) {
        //			EntityMerchant merchant = new EntityMerchant(world);
        //			merchant.setMarket(x,y,z, facing);
        //			merchant.setToFacingAngle();
        //			merchant.setSpawnAnimation(spawnAnimationType);
        //
        //			if(world.canBlockSeeTheSky(entityPos)) {
        //				merchant.setPosition(entityPos.getX() + 0.5, entityPos.getY() + 172, entityPos.getZ() + 0.5);
        //			} else if(!world.isAirBlock(entityPos.down())) {
        //				merchant.setPosition(entityPos.getX() + 0.5, entityPos.getY(), entityPos.getZ() + 0.5);
        //			} else {
        //				merchant.setPosition(entityPos.getX() + 0.5, entityPos.getY(), entityPos.getZ() + 0.5);
        //			}
        //
        //			world.spawnEntityInWorld(merchant);
        //			merchant.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        //		}
        //		if(spawnAnimationType == EntityMerchant.SpawnAnimationType.FALLING) {
        //			world.playSound(x + 0.5, y + 1, z + 0.5, "ModSounds.falling", 1f, 1f, false);
        //		} else if(spawnAnimationType == EntityMerchant.SpawnAnimationType.DIGGING) {
        //			world.playSound(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, ModSounds.falling,
        // SoundCategory.NEUTRAL, 1f, 1f, false);
        //		} else {
        //			world.playSound(x + 0.5, y + 1, z + 0.5, "SoundEvents.ITEM_FIRECHARGE_USE", 1f, 1f, false);
        //			for (int i = 0; i < 50; i++) {
        //				world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
        // (Math.random() - 0.5) * 0.5f, (Math.random() - 0.5) * 0.5f, (Math.random() - 0.5) * 0.5f);
        //			}
        //			world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
        // 0, 0, 0);
        //		}
    }

    @Override
    public boolean onBlockActivated(
            World world, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        if (!world.isRemote) {
            player.openGui(FarmingForEngineers.instance, GuiHandler.MARKET, world, x, y, z);
        }
        return true;
    }

    //	@Override
    //	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
    //		return false;
    //	}

    @Override
    public int getRenderType() {
        return RenderingRegistry.getNextAvailableRenderId();
    }

    // Helper Methods
    protected void findOrientation(World world, int x, int y, int z) {
        if (!world.isRemote) {
            Block block = world.getBlock(x, y, z - 1);
            Block block1 = world.getBlock(x, y, z + 1);
            Block block2 = world.getBlock(x - 1, y, z);
            Block block3 = world.getBlock(x + 1, y, z);
            byte side = 3;
            if (block.isOpaqueCube() && !block1.isOpaqueCube()) {
                side = 3;
            }
            if (block1.isOpaqueCube() && !block.isOpaqueCube()) {
                side = 2;
            }
            if (block2.isOpaqueCube() && !block3.isOpaqueCube()) {
                side = 5;
            }
            if (block3.isOpaqueCube() && !block2.isOpaqueCube()) {
                side = 4;
            }
            world.setBlockMetadataWithNotify(x, y, z, side, 2);
        }
    }
}
