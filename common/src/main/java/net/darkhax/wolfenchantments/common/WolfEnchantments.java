package net.darkhax.wolfenchantments.common;

import net.darkhax.pricklemc.common.api.config.ConfigManager;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WolfEnchantments {
    public static final String MOD_ID = "wolfenchantments";
    public static final String MOD_NAME = "Wolf Enchantments";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static Config CONFIG = ConfigManager.load(MOD_ID, new Config());

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}