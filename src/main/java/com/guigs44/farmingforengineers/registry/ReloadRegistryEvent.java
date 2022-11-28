package com.guigs44.farmingforengineers.registry;

import cpw.mods.fml.common.eventhandler.Event;

public class ReloadRegistryEvent extends Event {

    private final AbstractRegistry registry;

    public ReloadRegistryEvent(AbstractRegistry registry) {
        this.registry = registry;
    }

    public AbstractRegistry getRegistry() {
        return registry;
    }
}
