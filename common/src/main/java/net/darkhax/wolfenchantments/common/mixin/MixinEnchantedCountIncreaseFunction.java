package net.darkhax.wolfenchantments.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.darkhax.wolfenchantments.common.ModHooks;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantedCountIncreaseFunction.class)
public class MixinEnchantedCountIncreaseFunction {

    @Shadow
    @Final
    private Holder<Enchantment> enchantment;

    @ModifyExpressionValue(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getEnchantmentLevel(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/LivingEntity;)I"))
    private int increaseEnchantmentLevel(int original, ItemStack itemStack, LootContext context) {
        return original + ModHooks.getLootingBoost(enchantment, context);
    }
}
