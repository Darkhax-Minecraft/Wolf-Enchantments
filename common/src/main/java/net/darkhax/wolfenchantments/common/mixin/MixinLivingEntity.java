package net.darkhax.wolfenchantments.common.mixin;

import net.darkhax.wolfenchantments.common.ModHooks;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "canAttack", at = @At("HEAD"), cancellable = true)
    public void setTarget(LivingEntity victim, CallbackInfoReturnable<Boolean> cir) {
        if (ModHooks.preventAttack((LivingEntity) (Object) this, victim)) {
            cir.setReturnValue(false);
        }
    }
}