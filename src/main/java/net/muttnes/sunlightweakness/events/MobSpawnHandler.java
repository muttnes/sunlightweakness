package net.muttnes.sunlightweakness.events;

import net.muttnes.sunlightweakness.MuttnesSunlightWeakness;
import net.muttnes.sunlightweakness.config.Config;
import net.muttnes.sunlightweakness.system.TimeStateManager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = MuttnesSunlightWeakness.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class MobSpawnHandler {

    @SubscribeEvent
    public static void onMobSpawn(MobSpawnEvent.FinalizeSpawn event) {

        if (!(event.getEntity() instanceof Monster mob)) return;

        if (!Config.isEnabled()) return;

        if (!TimeStateManager.IS_DAY) return;

        if (Config.isMobExcluded(mob)) return;

        if (!(Config.isMobIncludedByNamespace(mob)
                || Config.isMobIncluded(mob))) {
            return;
        }

        MobSpeedHandler.applySlownessEffect(mob);
    }
}