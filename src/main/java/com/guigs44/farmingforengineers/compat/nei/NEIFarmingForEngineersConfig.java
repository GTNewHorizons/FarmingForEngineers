package com.guigs44.farmingforengineers.compat.nei;

import codechicken.nei.api.IConfigureNEI;

public class NEIFarmingForEngineersConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {}

    @Override
    public String getName() {
        return "Farming for Engineers";
    }

    @Override
    public String getVersion() {
        return "1.x.x";
    }
}
