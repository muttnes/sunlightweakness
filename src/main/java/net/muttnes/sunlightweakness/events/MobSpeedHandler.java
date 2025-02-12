package net.muttnes.sunlightweakness.events;

import net.muttnes.sunlightweakness.MuttnesSunlightWeakness;
import net.muttnes.sunlightweakness.config.Config;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MuttnesSunlightWeakness.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobSpeedHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Mob)) return;
        Mob mob = (Mob) event.getEntity();
        Level level = mob.level();
    
        if (level.isClientSide || !Config.isEnabled()) return;
    
        if (level.isDay() && mob instanceof Monster && !Config.isMobExcluded(mob) &&
                (Config.isMobIncludedByNamespace(mob) || Config.isMobIncluded(mob))) {
            applySlownessEffect(mob);
        } else {
            removeSlownessEffect(mob);
        }
    }
    

    public static void applySlownessEffect(Mob mob) {
        int slownessLevel = Config.SPEED_REDUCTION_LEVEL.get();
        MobEffectInstance currentEffect = mob.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
        if (currentEffect == null || currentEffect.getAmplifier() < slownessLevel) {
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, Integer.MAX_VALUE, slownessLevel, false, false));
        }
    }

    public static void removeSlownessEffect(Mob mob) {
        mob.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
    }
}
