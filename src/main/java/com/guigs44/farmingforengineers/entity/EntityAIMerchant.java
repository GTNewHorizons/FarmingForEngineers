package com.guigs44.farmingforengineers.entity;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityAIMerchant extends EntityAIBase {

    private final EntityMerchant entity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private final double movementSpeed;

    public EntityAIMerchant(EntityMerchant entity, double movementSpeed) {
        this.entity = entity;
        this.movementSpeed = movementSpeed;
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (entity.isAtMarket()) {
            entity.setToFacingAngle();
            return false;
        }

        ForgeDirection opposite = entity.facing.getOpposite();
        this.movePosX = entity.marketX + opposite.offsetX;
        this.movePosY = entity.marketY;
        this.movePosZ = entity.marketZ + opposite.offsetY;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        return !entity.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        entity.getNavigator().tryMoveToXYZ(movePosX, movePosY, movePosZ, movementSpeed);
    }

}
