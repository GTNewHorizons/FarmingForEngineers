package com.guigs44.farmingforengineers.utilities;

import net.minecraftforge.common.util.ForgeDirection;

public class RenderUtils {

    public static float getAngle(int metadata) {
        return getAngle(ForgeDirection.getOrientation(metadata));
    }

    public static float getAngle(ForgeDirection direction) {
        switch (direction) {
            case NORTH:
                return 180;
            case EAST:
                return -90;
            case SOUTH:
                return 0;
            case WEST:
                return 90;
            default:
                return -90;
        }
    }
}
