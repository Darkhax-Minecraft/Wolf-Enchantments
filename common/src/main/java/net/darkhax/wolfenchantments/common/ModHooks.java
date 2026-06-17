package net.darkhax.wolfenchantments.common;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class ModHooks {

    public static Item.Properties modifyVanillaWolfArmor(Item.Properties original) {
        if (WolfEnchantments.CONFIG.modify_vanilla_armor) {
            WolfEnchantments.LOG.info("Making item 'minecraft:wolf_armor' enchantable with enchantability value of '{}'", WolfEnchantments.CONFIG.vanilla_armor_enchantability);
            return original.enchantable(WolfEnchantments.CONFIG.vanilla_armor_enchantability);
        }
        WolfEnchantments.LOG.info("Vanilla wolf armor is not enchantable by default. Your current configuration prevents this mod from modifying the vanilla item to make it enchantable. Unless another mod changes this behavior, vanilla wolf armor cannot be enchanted.");
        return original;
    }

    public static boolean preventAttack(LivingEntity attacker, LivingEntity victim) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            final ItemStack equipped = attacker.getItemBySlot(slot);
            if (!equipped.isEmpty() && EnchantmentHelper.hasTag(equipped, ModTags.PREVENTS_ATTACKING)) {
                return true;
            }
        }
        return false;
    }

    public static int getLootingBoost(Holder<Enchantment> enchantment, LootContext context) {
        if (enchantment.is(ModTags.SCAVENGER_BOOST) && context.getOptionalParameter(LootContextParams.ATTACKING_ENTITY) instanceof LivingEntity killer) {
            return ModTags.getCombinedLevel(ModTags.BOOSTS_LOOTING, killer);
        }
        return 0;
    }

    public static void firePostAttack(ServerLevel serverLevel, Entity victim, DamageSource damageSource) {
        if (damageSource.getEntity() instanceof LivingEntity attacker) {
            final ItemStack bodySlot = attacker.getItemBySlot(EquipmentSlot.BODY);
            if (!bodySlot.isEmpty()) {
                final ItemEnchantments enchantments = bodySlot.get(DataComponents.ENCHANTMENTS);
                if (enchantments != null && !enchantments.isEmpty()) {
                    final EnchantedItemInUse itemContext = new EnchantedItemInUse(bodySlot, EquipmentSlot.BODY, attacker);
                    for (Object2IntMap.Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
                        final Holder<Enchantment> enchantment = entry.getKey();
                        if (entry.getKey().is(ModTags.ATTACK_WITH_BODY) && enchantment.value().matchingSlot(EquipmentSlot.BODY)) {
                            enchantment.value().doPostAttack(serverLevel, entry.getIntValue(), itemContext, EnchantmentTarget.ATTACKER, victim, damageSource);
                        }
                    }
                }
            }
        }
    }
}
