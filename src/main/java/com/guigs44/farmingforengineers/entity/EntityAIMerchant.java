 package com.guigs44.farmingforengineers.entity;

 import net.minecraft.entity.ai.EntityAIBase;

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
		if(true) { //TODO: Check if location is valid
			this.movePosX = entity.posX + 0.5;
			this.movePosY = entity.posY + 1;
			this.movePosZ = entity.posZ + 0.5;
			return true;
		}
		return false;
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
