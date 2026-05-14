package net.muttnes.sunlightweakness.events;

import net.muttnes.sunlightweakness.MuttnesSunlightWeakness;
import net.muttnes.sunlightweakness.system.TimeStateManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MuttnesSunlightWeakness.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DaytimeHandler {

    private static boolean lastDayState = false;
    private static boolean initialized = false;

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {

        if (!(event.level instanceof ServerLevel level)) return;
        if (event.phase != TickEvent.Phase.END) return;
        if (level.dimension() != Level.OVERWORLD) return;

        boolean isDay = level.isDay();

        // INIT DEBUG
        if (!initialized) {
            initialized = true;
            lastDayState = !isDay;

            System.out.println("[SunWeakness] INIT");
            System.out.println("[SunWeakness] Starting state -> isDay: " + isDay);
        }

        // TICK LOG
        if (level.getGameTime() % 100 == 0) {
            System.out.println("[SunWeakness] Tick OK | isDay=" + isDay);
        }

        // CHANGE LOG
        if (isDay != lastDayState) {
            System.out.println("[SunWeakness] DAY/NIGHT CHANGE DETECTED -> " + isDay);
        }

        // SKIP IF NO CHANGE
        if (isDay == lastDayState && level.getGameTime() > 20) return;

        lastDayState = isDay;
        TimeStateManager.IS_DAY = isDay;

        if (isDay) {
            System.out.println("[SunWeakness] APPLYING DAY EFFECTS");
        } else {
            System.out.println("[SunWeakness] REMOVING DAY EFFECTS");
        }

        for (var entity : level.getAllEntities()) {

            if (!(entity instanceof Monster mob)) continue;

            System.out.println("[SunWeakness] Found mob: " + mob.getName().getString());

            if (isDay) {
                MobSpeedHandler.applySlownessEffect(mob);
            } else {
                MobSpeedHandler.removeSlownessEffect(mob);
            }
        }
    }
}