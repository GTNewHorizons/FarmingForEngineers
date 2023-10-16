package com.guigs44.farmingforengineers.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.guigs44.farmingforengineers.FarmingForEngineers;
import com.guigs44.farmingforengineers.client.render.block.MarketBlockRenderer;
import com.guigs44.farmingforengineers.entity.EntityMerchant;
import com.guigs44.farmingforengineers.network.GuiHandler;
import com.guigs44.farmingforengineers.tile.TileMarket;

/**
 * A good chunk of the code in this file has been based on Jason Mitchell's (@mitchej123) work. Specifically:
 * https://github.com/GTNewHorizons/CookingForBlockheads/blob/master/src/main/java/net/blay09/mods/cookingforblockheads/block/BlockBaseKitchen.java
 * https://github.com/GTNewHorizons/CookingForBlockheads/blob/master/src/main/java/net/blay09/mods/cookingforblockheads/block/BlockOven.java
 *
 * Licensed under LGPL-3.0
 */
public class BlockMarket extends BlockContainer {

    public EntityMerchant merchant;
    // public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockMarket() {
        super(Material.wood);
        setBlockName(FarmingForEngineers.MOD_ID + ":market"); // TODO: Fix the name
        setStepSound(soundTypeWood);
        setHardness(2f);
        setResistance(10f);
        setCreativeTab(FarmingForEngineers.creativeTab);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {}

    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.log.getIcon(side, 1);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMarket();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
        findOrientation(worldIn, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack itemStack) {
        // See BlockDispenser#onBlockPlacedBy
        int meta = BlockPistonBase.determineOrientation(world, x, y, z, placer);
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
        ForgeDirection facing = ForgeDirection.getOrientation(meta); // facing metadata value -> EnumFacing value
        ForgeDirection merchantFacing = facing.getOpposite();

        // The position of the merchant entity
        int merchantX = x + merchantFacing.offsetX;
        int merchantY = y;
        int merchantZ = z + merchantFacing.offsetZ;

        EntityMerchant.SpawnAnimationType spawnAnimationType = EntityMerchant.SpawnAnimationType.MAGIC;
        if (world.canBlockSeeTheSky(x, y, z)) {
            spawnAnimationType = EntityMerchant.SpawnAnimationType.FALLING;
        } else if (!world.isAirBlock(x, y - 1, z)) {
            spawnAnimationType = EntityMerchant.SpawnAnimationType.DIGGING;
        }
        if (!world.isRemote) {
            merchant = new EntityMerchant(world);
            merchant.setMarket(x, y, z, facing);
            merchant.setToFacingAngle();
            merchant.setSpawnAnimation(spawnAnimationType);

            if (world.canBlockSeeTheSky(merchantX, merchantY, merchantZ)) {
                merchant.setPosition(merchantX + 0.5, merchantY + 172, merchantZ + 0.5);
            } else if (!world.isAirBlock(merchantX, merchantY, merchantZ - 1)) {
                merchant.setPosition(merchantX + 0.5, merchantY + 0.5, merchantZ + 0.5);
            } else {
                merchant.setPosition(merchantX + 0.5, merchantY, merchantZ + 0.5);
            }

            world.spawnEntityInWorld(merchant);
            merchant.onInitialSpawn(null);
        }
        if (spawnAnimationType == EntityMerchant.SpawnAnimationType.FALLING) {
            world.playSound(merchantX + 0.5, merchantY + 1, merchantZ + 0.5, "sounds.falling", 1f, 1f, false);
        } else if (spawnAnimationType == EntityMerchant.SpawnAnimationType.DIGGING) {
            world.playSound(merchantX + 0.5, merchantY + 1, merchantZ, "sounds.falling", 1f, 1f, false);
        } else {
            world.playSound(merchantX + 0.5, merchantY + 1, merchantZ + 0.5, "item.firecharge.use", 1f, 1f, false);
            for (int i = 0; i < 50; i++) {
                world.spawnParticle(
                        "firework",
                        x + 0.5,
                        y + 1,
                        z + 0.5,
                        (Math.random() - 0.5) * 0.5f,
                        (Math.random() - 0.5) * 0.5f,
                        (Math.random() - 0.5) * 0.5f);
            }
            world.spawnParticle("explosion", merchantX + 0.5, merchantY + 1, merchantZ + 0.5, 0, 0, 0);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
            float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(FarmingForEngineers.instance, GuiHandler.MARKET, world, x, y, z);
        }
        return true;
    }

    @Override
    public int getRenderType() {
        return MarketBlockRenderer.RENDER_ID;
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

    @Override
    public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_,
            int p_149664_5_) {
        if (merchant != null) merchant.disappear();
    }
}
