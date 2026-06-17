package net.darkhax.wolfenchantments.common;

import net.darkhax.pricklemc.common.api.annotations.Value;

public class Config {

    @Value(comment = "Should this mod modify the vanilla wolf armor to make it enchantable?")
    public boolean modify_vanilla_armor = true;

    @Value(comment = "The enchantability of the vanilla wolf armor. Higher means better enchantments. Mojang sets this to 10, but did not apply it.")
    public int vanilla_armor_enchantability = 10;
}