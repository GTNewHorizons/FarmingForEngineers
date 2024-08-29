package com.guigs44.farmingforengineers;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class ModSounds {

    /**
     * Location of the falling sound effect.
     */
    public static final ResourceLocation LOC_FALLING = new ResourceLocation(FarmingForEngineers.MOD_ID, "falling");

    /**
     * Location of a 'poof' sound effect.
     *
     * <p>
     * Note: In 1.12, the sound of a fire charge was used, which is unavailable before 1.8.
     */
    public static final ResourceLocation LOC_POOF = new ResourceLocation("fire.ignite");

    /**
     * Location of the digging sound effect.
     */
    public static final ResourceLocation LOC_DIG = new ResourceLocation(Block.soundTypeGravel.getBreakSound());
}
