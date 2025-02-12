package net.muttnes.sunlightweakness;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.mojang.logging.LogUtils;
import net.muttnes.sunlightweakness.config.Config;

import org.slf4j.Logger;

@Mod(MuttnesSunlightWeakness.MODID)
public class MuttnesSunlightWeakness
{
    public static final String MODID = "sunlightweakness";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MuttnesSunlightWeakness()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC, "sunlightweakness-server.toml");

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }
}
