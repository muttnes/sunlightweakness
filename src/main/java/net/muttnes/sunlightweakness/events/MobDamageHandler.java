package net.muttnes.sunlightweakness.events;

import net.muttnes.sunlightweakness.MuttnesSunlightWeakness;
import net.muttnes.sunlightweakness.config.Config;
import net.muttnes.sunlightweakness.system.TimeStateManager;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MuttnesSunlightWeakness.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobDamageHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {

        System.out.println("[SunWeakness] Hurt event fired");

        if (!(event.getSource().getEntity() instanceof Mob mob)) return;

        if (mob.level().isClientSide()) return;

        if (Config.isEnabled()
                && TimeStateManager.IS_DAY
                && !Config.isMobExcluded(mob)
                && (Config.isMobIncludedByNamespace(mob)
                || Config.isMobIncluded(mob))) {

            System.out.println("[SunWeakness] Damage BEFORE: " + event.getAmount());

            double damageReduction = Config.DAMAGE_REDUCTION.get();

            event.setAmount(
                    (float) (event.getAmount() * (1.0 - damageReduction))
            );

            System.out.println("[SunWeakness] Damage AFTER: " + event.getAmount());
        }
    }
}
