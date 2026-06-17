package net.darkhax.wolfenchantments.common;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class ModTags {

    public static final TagKey<Enchantment> ATTACK_WITH_BODY = enchantment("attacks_with_body");
    public static final TagKey<Enchantment> PREVENTS_ATTACKING = enchantment("prevents_attacking");
    public static final TagKey<Enchantment> SCAVENGER_BOOST = enchantment("scavenger_boost");
    public static final TagKey<Enchantment> BOOSTS_LOOTING = enchantment("boosts_looting");

    private static TagKey<Enchantment> enchantment(String name) {
        return TagKey.create(Registries.ENCHANTMENT, WolfEnchantments.id(name));
    }

    public static int getCombinedLevel(TagKey<Enchantment> targetEnchantment, LivingEntity entity) {
        int level = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            final ItemStack stack = entity.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                final ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
                if (enchantments != null && !enchantments.isEmpty()) {
                    for (Holder<Enchantment> enchant : enchantments.keySet()) {
                        if (enchant.is(targetEnchantment)) {
                            level += enchantments.getLevel(enchant);
                        }
                    }
                }
            }
        }
        return level;
    }
}