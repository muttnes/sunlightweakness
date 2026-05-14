package net.muttnes.sunlightweakness.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.muttnes.sunlightweakness.MuttnesSunlightWeakness;

@Mod.EventBusSubscriber(modid = MuttnesSunlightWeakness.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue ENABLED = BUILDER
        .comment("Whether the mod's functionality is enabled. Disable to make mobs stronger during the day.")
        .define("enabled", true);

    public static final ForgeConfigSpec.DoubleValue DAMAGE_REDUCTION = BUILDER
        .comment("The amount of damage reduction that mobs receive when in sunlight.")
        .defineInRange("damageReduction", 0.5, 0.0, 1.0);

    public static final ForgeConfigSpec.DoubleValue SPEED_REDUCTION = BUILDER
        .comment("Movement speed reduction percentage during daytime. 0.30 = 30% slower.")
        .defineInRange("speedReduction", 0.30, 0.0, 1.0);

    public static final ForgeConfigSpec.ConfigValue<List<String>> EXCLUDED_MOBS = BUILDER
        .comment("A list of mob names that are excluded from the sunlight weakness effect.")
        .define("excludedMobs", List.of("minecraft:iron_golem", "minecraft:snow_golem"));

    public static final ForgeConfigSpec.ConfigValue<List<String>> INCLUDED_MODS = BUILDER
        .comment("A list of mod namespaces whose mobs are included for sunlight weakness effects. By default all minecraft hostile mobs are included.")
        .define("includedMods", new ArrayList<>(List.of("iceandfire", "minecraft", "mymod")));

    public static final ForgeConfigSpec.ConfigValue<List<String>> INCLUDED_MOBS = BUILDER
        .comment("A list of specific mob IDs (namespace:name) to include in sunlight weakness effect.")
        .define("includedMobs", new ArrayList<>(List.of("minecraft:zombie", "minecraft:skeleton")));

    public static boolean isMobExcluded(Mob mob) {

        var key = ForgeRegistries.ENTITY_TYPES.getKey(mob.getType());

        if (key == null) return false;

        String fullId = key.toString();
        String path = key.getPath();

        return EXCLUDED_MOBS.get().contains(fullId)
                || EXCLUDED_MOBS.get().contains(path);
    }

    public static boolean isMobIncludedByNamespace(Mob mob) {
        String mobID = ForgeRegistries.ENTITY_TYPES.getKey(mob.getType()).getNamespace();
        return INCLUDED_MODS.get().contains(mobID);
    }

    public static boolean isMobIncluded(Mob mob) {

        var key = ForgeRegistries.ENTITY_TYPES.getKey(mob.getType());

        if (key == null) return false;

        String fullId = key.toString();
        String path = key.getPath();

        return INCLUDED_MOBS.get().contains(fullId)
                || INCLUDED_MOBS.get().contains(path);
    }

    public static boolean isEnabled() {
        return ENABLED.get();
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        System.out.println("Speed Reduction Level: " + SPEED_REDUCTION.get());
        System.out.println("Damage Reduction: " + DAMAGE_REDUCTION.get());
        System.out.println("Excluded Mobs: " + EXCLUDED_MOBS.get());
        System.out.println("Included Mods: " + INCLUDED_MODS.get());
        System.out.println("Included Mobs: " + INCLUDED_MOBS.get());
    }
}
