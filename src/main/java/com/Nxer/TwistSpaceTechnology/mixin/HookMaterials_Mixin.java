package com.Nxer.TwistSpaceTechnology.mixin;

import java.io.File;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.Nxer.TwistSpaceTechnology.TwistSpaceTechnology;
import com.Nxer.TwistSpaceTechnology.common.material.MaterialsTST;
import com.Nxer.TwistSpaceTechnology.config.Config;
import com.Nxer.TwistSpaceTechnology.util.LanguageManager;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregtech.GTMod;

@Mixin(value = GTMod.class, remap = false)
public class HookMaterials_Mixin {

    @Inject(
        method = "onPreInitialization",
        at = @At(value = "INVOKE", target = "Lgregtech/api/enums/Materials;init()V"))
    private void tst$runBeforeMaterialsInit(FMLPreInitializationEvent aEvent, CallbackInfo ci) {
        File configFile = new File(aEvent.getModConfigurationDirectory(), "TwistSpaceTechnology.cfg");
        Config.synchronizeVersionConfiguration(configFile);
        LanguageManager.refreshMaterialLocalizationsOnVersionChange(configFile);
        MaterialsTST.init();
        TwistSpaceTechnology.LOG.info("Materials Hooked!");
    }

}
