package net.muttnes.sunlightweakness.events;

import net.muttnes.sunlightweakness.MuttnesSunlightWeakness;
import net.muttnes.sunlightweakness.config.Config;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MuttnesSunlightWeakness.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobDamageHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof Mob)) return;

        Mob mob = (Mob) event.getSource().getEntity();
        
        if (Config.isEnabled() && !Config.isMobExcluded(mob) && mob.level().isDay() &&
            (Config.isMobIncludedByNamespace(mob) || Config.isMobIncluded(mob))) {
            
            double damageReduction = Config.DAMAGE_REDUCTION.get();
            event.setAmount((float) (event.getAmount() * (1.0 - damageReduction)));
        }
    }
}
