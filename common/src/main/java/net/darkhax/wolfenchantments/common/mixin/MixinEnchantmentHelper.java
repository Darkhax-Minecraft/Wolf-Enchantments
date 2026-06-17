package net.darkhax.wolfenchantments.common.mixin;

import net.darkhax.wolfenchantments.common.ModHooks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper {

    @Inject(method = "doPostAttackEffectsWithItemSourceOnBreak", at = @At("RETURN"))
    private static void onPostAttack(ServerLevel serverLevel, Entity victim, DamageSource damageSource, @Nullable ItemStack source, @Nullable Consumer<Item> attackerlessOnBreak, CallbackInfo ci) {
        ModHooks.firePostAttack(serverLevel, victim, damageSource);
    }
}