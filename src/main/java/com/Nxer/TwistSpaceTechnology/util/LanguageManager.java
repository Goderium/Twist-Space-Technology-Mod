package com.Nxer.TwistSpaceTechnology.util;

import java.io.File;
import java.util.Locale;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;

import com.Nxer.TwistSpaceTechnology.Tags;
import com.Nxer.TwistSpaceTechnology.TwistSpaceTechnology;
import com.Nxer.TwistSpaceTechnology.config.Config;

import cpw.mods.fml.common.FMLCommonHandler;

public class LanguageManager {

    private LanguageManager() {}

    public static void refreshGregTechLangOnVersionChange(File configFile) {
        String currentVersion = Tags.VERSION;
        // Dev and dirty builds can change language registrations without a visible version bump.
        boolean alwaysRefresh = isDevEnvironment() || isLocalBuildVersion(currentVersion);
        boolean refreshed = true;

        if (alwaysRefresh || !currentVersion.equals(Config.CurrentVersion)) {
            refreshed = deleteGregTechLang(configFile);
        }

        if (alwaysRefresh || refreshed) {
            saveCurrentVersion(configFile, currentVersion);
        }
    }

    private static boolean isDevEnvironment() {
        return Boolean.TRUE.equals(Launch.blackboard.get("fml.deobfuscatedEnvironment"));
    }

    private static boolean isLocalBuildVersion(String version) {
        return version.indexOf('+') >= 0 || version.endsWith("-dirty");
    }

    private static boolean deleteGregTechLang(File configFile) {
        File configDir = configFile.getParentFile();
        if (configDir == null) {
            return true;
        }

        File minecraftRoot = configDir.getParentFile();
        if (minecraftRoot == null) {
            return true;
        }

        File gregTechLang = new File(minecraftRoot, getGregTechLangFileName());
        if (!gregTechLang.isFile()) {
            return true;
        }

        if (gregTechLang.delete()) {
            TwistSpaceTechnology.LOG.info("Deleted " + gregTechLang.getName() + " for material language refresh.");
            return true;
        } else {
            TwistSpaceTechnology.LOG
                .warn("Failed to delete " + gregTechLang.getName() + ": " + gregTechLang.getAbsolutePath());
            return false;
        }
    }

    private static String getGregTechLangFileName() {
        // GT keeps localized overrides in per-language files, while English uses the legacy base file.
        return isChineseLanguage() ? "GregTech_zh_CN.lang" : "GregTech.lang";
    }

    private static boolean isChineseLanguage() {
        String language = FMLCommonHandler.instance()
            .getCurrentLanguage();
        return language != null && language.toLowerCase(Locale.ROOT)
            .startsWith("zh");
    }

    private static void saveCurrentVersion(File configFile, String currentVersion) {
        Config.CurrentVersion = currentVersion;

        // Reopen the same cfg so the version marker is updated after the lang refresh actually succeeds.
        Configuration configuration = new Configuration(configFile);
        configuration.get(Config.VERSION, Config.KEY_CURRENT_VERSION, currentVersion)
            .set(currentVersion);
        configuration.save();
    }
}
