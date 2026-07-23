package com.Nxer.TwistSpaceTechnology.util;

import java.io.File;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.ConfigCategory;

import com.Nxer.TwistSpaceTechnology.Tags;
import com.Nxer.TwistSpaceTechnology.TwistSpaceTechnology;
import com.Nxer.TwistSpaceTechnology.config.Config;

import gregtech.api.util.GTLanguageManager;

public class LanguageManager {

    private static final String GT_LANG_CATEGORY = "LanguageFile";
    private static final String[] TST_MATERIAL_LOCALIZATION_KEYS = {
        // Only refresh TST-owned material names/formulas; prefix and fluid names remain GT/BW-managed.
        "Material.neutroniumalloy",
        "Material.axonisalloy",
        "Material.axonium",
        "Material.dubnium",
        "Material.holmiumgarnet",
        "Material.puremana",
        "Material.liquidmana",
        "Material.purifiedmana",
        "Material.stabilisevoidmatter",
        "Material.liquidstargate",
        "Material.concentrateduu-matter",
        "Material.highlyenergeticdimensionalentropicfluid",
        "Material.highlyenergeticdimensionalentropicfluid.ChemicalFormula",
        "Material.chrono-dimensionallyannihilativeentropicfluid",
        "Material.chrono-dimensionallyannihilativeentropicfluid.ChemicalFormula" };

    private LanguageManager() {}

    public static void refreshMaterialLocalizationsOnVersionChange(File configFile) {
        String currentVersion = Tags.VERSION;
        String currentMarker = getCurrentVersionMarker(currentVersion);
        // Dev and dirty builds can change language registrations without a visible version bump.
        boolean alwaysRefresh = isDevEnvironment() || isLocalBuildVersion(currentVersion);
        boolean refreshed = true;

        if (alwaysRefresh || !currentMarker.equals(Config.CurrentVersion)) {
            refreshed = refreshTSTMaterialLocalizations();
        }

        if (alwaysRefresh || refreshed) {
            saveCurrentVersionMarker(configFile, currentMarker);
        }
    }

    private static boolean isDevEnvironment() {
        return Boolean.TRUE.equals(Launch.blackboard.get("fml.deobfuscatedEnvironment"));
    }

    private static boolean isLocalBuildVersion(String version) {
        return version.indexOf('+') >= 0 || version.endsWith("-dirty");
    }

    private static String getCurrentVersionMarker(String version) {
        // GT binds sEnglishFile to the current language file, so remember which language was refreshed too.
        return version + "|" + GTLanguageManager.LanguageCode;
    }

    private static boolean refreshTSTMaterialLocalizations() {
        Configuration gregTechLang = GTLanguageManager.sEnglishFile;
        if (gregTechLang == null) {
            return false;
        }

        int removed = removeTSTMaterialKeys(gregTechLang);
        if (removed > 0) {
            gregTechLang.save();
            TwistSpaceTechnology.LOG
                .info("Refreshed " + removed + " TST material localization entries in GregTech language file.");
        }
        return true;
    }

    private static int removeTSTMaterialKeys(Configuration configuration) {
        if (!configuration.hasCategory(GT_LANG_CATEGORY)) {
            return 0;
        }

        ConfigCategory languageFile = configuration.getCategory(GT_LANG_CATEGORY);
        int removed = 0;
        for (String key : TST_MATERIAL_LOCALIZATION_KEYS) {
            if (languageFile.remove(key) != null) {
                removed++;
            }
        }
        return removed;
    }

    private static void saveCurrentVersionMarker(File configFile, String currentVersionMarker) {
        Config.CurrentVersion = currentVersionMarker;

        // Reopen the same cfg so the version marker is updated after the lang refresh actually succeeds.
        Configuration configuration = new Configuration(configFile);
        configuration.get(Config.VERSION, Config.KEY_CURRENT_VERSION, currentVersionMarker)
            .set(currentVersionMarker);
        configuration.save();
    }
}
