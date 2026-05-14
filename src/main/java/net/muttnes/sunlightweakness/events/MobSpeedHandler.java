package net.muttnes.sunlightweakness.events;

import net.muttnes.sunlightweakness.config.Config;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class MobSpeedHandler {

    private static final UUID SUNLIGHT_SLOW_UUID =
            UUID.fromString("91aeaa56-376b-4498-935b-2f7f68070635");

    public static void applySlownessEffect(Mob mob) {

        AttributeInstance speed =
                mob.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speed == null) return;

        // Evita duplicados
        if (speed.getModifier(SUNLIGHT_SLOW_UUID) != null) {
            return;
        }

        double level = Config.SPEED_REDUCTION.get();

        // 0 = -15%
        // 1 = -30%
        // 2 = -45%
        // etc
        double reduction = -0.15D * (level + 1);

        AttributeModifier modifier = new AttributeModifier(
                SUNLIGHT_SLOW_UUID,
                "sunlight_weakness_slow",
                reduction,
                AttributeModifier.Operation.MULTIPLY_TOTAL
        );

        speed.addTransientModifier(modifier);
    }

    public static void removeSlownessEffect(Mob mob) {

        AttributeInstance speed =
                mob.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speed == null) return;

        speed.removeModifier(SUNLIGHT_SLOW_UUID);
    }
}