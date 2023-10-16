package com.guigs44.farmingforengineers.entity;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.guigs44.farmingforengineers.FarmingForEngineers;
import com.guigs44.farmingforengineers.block.ModBlocks;
import com.guigs44.farmingforengineers.network.GuiHandler;
import com.guigs44.farmingforengineers.utilities.RenderUtils;

public class EntityMerchant extends EntityCreature implements INpc {

    /**
     * The minimum distance <em>squared</em> at which the merchant should be considered 'at' the market.
     */
    public static final float MARKET_ARRIVAL_DISTANCE = 1.75f * 1.75f;

    public enum SpawnAnimationType {
        MAGIC,
        FALLING,
        DIGGING
    }

    private static final Random rand = new Random();
    public static final String[] NAMES = new String[] { "Swap-O-Matic", "Emerald Muncher", "Back Alley Dealer",
            "Weathered Salesperson" };

    // private BlockPos marketPos;
    int marketX;
    int marketY;
    int marketZ;
    ForgeDirection facing;
    private boolean spawnDone;
    private SpawnAnimationType spawnAnimation = SpawnAnimationType.MAGIC;

    private int diggingAnimation;
    // private IBlockState diggingBlockState;

    public EntityMerchant(World world) {
        super(world);
        setSize(0.6f, 1.95f);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8f, 0.6, 0.6));
        this.tasks.addTask(5, new EntityAIMerchant(this, 0.6));
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
    }

    @Override
    public boolean interact(EntityPlayer player) {
        if (isMarketValid()) {
            player.openGui(
                    FarmingForEngineers.MOD_ID,
                    GuiHandler.MARKET,
                    worldObj,
                    (int) marketX,
                    (int) marketY,
                    (int) marketZ);
            return true;
        }
        return super.interact(player);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        // I wonder if using an integer won't be a problem
        compound.setInteger("MarketPosX", marketX);
        compound.setInteger("MarketPosY", marketY);
        compound.setInteger("MarketPosZ", marketZ);

        // if(facing != null) {
        // compound.setByte("Facing", (byte) facing.);
        // }
        compound.setBoolean("SpawnDone", spawnDone);
        compound.setByte("SpawnAnimation", (byte) spawnAnimation.ordinal());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (!compound.hasKey("CustomName")) {
            setCustomNameTag(NAMES[rand.nextInt(NAMES.length)]);
        }
        if (compound.hasKey("MarketPosX")) {
            setMarket(marketX, marketY, marketZ, ForgeDirection.getOrientation(compound.getByte("Facing")));
        }
        spawnDone = compound.getBoolean("SpawnDone");
        spawnAnimation = SpawnAnimationType.values()[compound.getByte("SpawnAnimation")];
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected String getLivingSound() {
        return "mob.villager.ambient";
    } // TODO: Figure out what is the correct string

    @Override
    protected String getHurtSound() {
        return "mob.villager.hit";
    } // Works

    @Override
    protected String getDeathSound() {
        return "mob.villager.death";
    }// works

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!worldObj.isRemote) {
            if (ticksExisted % 20 == 0) {
                if (!isMarketValid()) {
                    worldObj.setEntityState(this, (byte) 12);
                    setDead();
                }
            }
        }

        if (!spawnDone && spawnAnimation == SpawnAnimationType.DIGGING) {
            worldObj.setEntityState(this, (byte) 13);
            spawnDone = true;
        }
        if (diggingAnimation > 0) {
            diggingAnimation--;
            for (int i = 0; i < 4; i++) {
                int stateId = 0;

                // Block.getStateId(diggingBlockState != null ? diggingBlockState : Blocks.dirt.get());
                // worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, posX, posY, posZ, Math.random() * 2 - 1,
                // Math.random() * 4, Math.random() * 2 - 1, stateId);
                // worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, posX, posY, posZ, (Math.random() - 0.5) * 0.5,
                // Math.random() * 0.5f, (Math.random() - 0.5) * 0.5, stateId);
            }
            if (diggingAnimation % 2 == 0) {
                worldObj.playSound(posX, posY, posZ, "block.gravel.hit", 1f, (float) (Math.random() + 0.5), false);
            }
        }
    }

    // @Override
    // public void handleStatusUpdate(byte id) {
    // if(id == 12) {
    // disappear();
    // return;
    // } else if(id == 13) {
    // diggingBlockState = worldObj.getBlockState(getPosition().down());
    // diggingAnimation = 60;
    // return;
    // }
    // super.handleStatusUpdate(id);
    // }

    @Override
    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        if (!spawnDone && damageSrc == DamageSource.fall) {
            worldObj.playSound(posX, posY, posZ, getHurtSound(), 1f, 2f, false);
            spawnDone = true;
            return;
        }
        super.damageEntity(damageSrc, damageAmount);
    }

    @Override
    public float getEyeHeight() {
        return 1.62f;
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(@Nullable IEntityLivingData livingData) {
        if (Math.random() < 0.001) {
            setCustomNameTag(Math.random() <= 0.5 ? "Pam" : "Blay");
        } else {
            setCustomNameTag(NAMES[rand.nextInt(NAMES.length)]);
        }
        setAlwaysRenderNameTag(true);
        return livingData;
    }

    // @Override
    // public boolean canBeLeashedTo(EntityPlayer player) {
    // return false;
    // }

    public void setMarket(int marketX, int marketY, int marketZ, ForgeDirection facing) {
        this.marketX = marketX;
        this.marketY = marketY;
        this.marketZ = marketZ;
        this.facing = facing;
    }

    public boolean isAtMarket() {
        return getDistanceSq(marketX, marketY, marketZ) <= MARKET_ARRIVAL_DISTANCE;
    }

    private boolean isMarketValid() {
        return worldObj.getBlock(marketX, marketY, marketZ) == ModBlocks.market;
    }

    public void setToFacingAngle() {
        float facingAngle = RenderUtils.getAngle(facing);
        setRotation(facingAngle, 0f);
        setRotationYawHead(facingAngle);
        // setRenderYawOffset(facingAngle);
    }

    public void disappear() {
        worldObj.playSound(posX, posY, posZ, "item.firecharge.use", 1f, 1f, false);
        for (int i = 0; i < 50; i++) {
            worldObj.spawnParticle(
                    "firework",
                    posX,
                    posY + 1,
                    posZ,
                    (Math.random() - 0.5) * 0.5f,
                    (Math.random() - 0.5) * 0.5f,
                    (Math.random() - 0.5) * 0.5f);
        }
        worldObj.spawnParticle("explosion", posX, posY + 1, posZ, 0, 0, 0);
        setDead();
    }

    public void setSpawnAnimation(SpawnAnimationType spawnAnimation) {
        this.spawnAnimation = spawnAnimation;
    }

    public int getDiggingAnimation() {
        return diggingAnimation;
    }
}
