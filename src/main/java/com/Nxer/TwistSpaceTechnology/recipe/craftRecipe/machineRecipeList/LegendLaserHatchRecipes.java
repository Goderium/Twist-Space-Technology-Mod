package com.Nxer.TwistSpaceTechnology.recipe.craftRecipe.machineRecipeList;

import com.Nxer.TwistSpaceTechnology.common.GTCMItemList;
import com.Nxer.TwistSpaceTechnology.recipe.IRecipePool;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.IItemContainer;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gtPlusPlus.core.material.MaterialMisc;
import net.minecraftforge.fluids.FluidStack;
import tectech.recipe.TTRecipeAdder;
import tectech.thing.CustomItemList;

import static com.Nxer.TwistSpaceTechnology.common.GTCMItemList.AdvancedHighPowerCoilBlock;
import static com.Nxer.TwistSpaceTechnology.util.enums.TierEU.RECIPE_UMV;
import static com.Nxer.TwistSpaceTechnology.util.enums.TierEU.RECIPE_UXV;
import static gregtech.api.enums.ItemList.ZPM6;
import static gregtech.api.enums.Mods.GTPlusPlus;
import static gregtech.api.enums.Mods.GalaxySpace;
import static gregtech.api.enums.Mods.GoodGenerator;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.HOURS;
import static gregtech.api.util.GTRecipeConstants.AssemblyLine;
import static gregtech.api.util.GTRecipeConstants.RESEARCH_ITEM;
import static gregtech.api.util.GTRecipeConstants.RESEARCH_TIME;

public class LegendLaserHatchRecipes implements IRecipePool {
    @Override
    public void loadRecipes() {
        IItemContainer LegendTarget = CustomItemList.eM_energyTunnel9001;
        IItemContainer LegendSource = CustomItemList.eM_dynamoTunnel9001;
        IItemContainer UXVTarget104 = CustomItemList.eM_energyTunnel7_UXV;
        IItemContainer UXVSource104 = CustomItemList.eM_dynamoTunnel7_UXV;
        IItemContainer HomoStructureTime = CustomItemList.EOH_Reinforced_Temporal_Casing;
        IItemContainer HomoStructureSpace = CustomItemList.EOH_Reinforced_Spatial_Casing;
        IItemContainer HomoStructureMain = CustomItemList.EOH_Infinite_Energy_Casing;
        GTValues.RA
            .stdBuilder()
            .metadata(RESEARCH_ITEM, UXVTarget104.get(1))
            .metadata(RESEARCH_TIME, 640 * HOURS)
            .itemInputs(
                UXVTarget104.get(64),
                ItemList.Field_Generator_UXV.get(64),
                UXVTarget104.get(64),
                HomoStructureTime.get(64),

                UXVTarget104.get(64),
                GTOreDictUnificator.get(OrePrefixes.lens, Materials.Forcillium, 64),
                UXVTarget104.get(64),
                ZPM6.get(1),

                UXVTarget104.get(64),
                GTOreDictUnificator.get(OrePrefixes.lens, Materials.Forcicium, 64),
                UXVTarget104.get(64),
                GTModHandler.getModItem(GTPlusPlus.ID, "item.itemBufferCore10", 16),

                UXVTarget104.get(64),
                ItemList.Field_Generator_UXV.get(64),
                UXVTarget104.get(64),
                HomoStructureTime.get(64)
            )
            .fluidInputs(
                MaterialsUEVplus.Time.getMolten(2000000000),
                MaterialsUEVplus.Eternity.getMolten(2000000000),
                MaterialsUEVplus.SpaceTime.getMolten(2000000000),
                Materials.Infinity.getMolten(2000000000)
            )
            .itemOutputs(LegendTarget.get(1))
            .eut(RECIPE_UXV)
            .duration(20 * 512)
            .addTo(AssemblyLine);

        GTValues.RA
            .stdBuilder()
            .metadata(RESEARCH_ITEM, UXVSource104.get(1))
            .metadata(RESEARCH_TIME, 640 * HOURS)
            .itemInputs(
                UXVSource104.get(64),
                ItemList.Field_Generator_UXV.get(64),
                UXVSource104.get(64),
                HomoStructureTime.get(64),

                UXVSource104.get(64),
                GTOreDictUnificator.get(OrePrefixes.lens, Materials.Forcillium, 64),
                UXVSource104.get(64),
                ZPM6.get(1),

                UXVSource104.get(64),
                GTOreDictUnificator.get(OrePrefixes.lens, Materials.Forcicium, 64),
                UXVSource104.get(64),
                GTModHandler.getModItem(GTPlusPlus.ID, "item.itemBufferCore10", 16),

                UXVSource104.get(64),
                ItemList.Field_Generator_UXV.get(64),
                UXVSource104.get(64),
                HomoStructureTime.get(64)
            )
            .fluidInputs(
                MaterialsUEVplus.Time.getMolten(2000000000),
                MaterialsUEVplus.Eternity.getMolten(2000000000),
                MaterialsUEVplus.SpaceTime.getMolten(2000000000),
                Materials.Infinity.getMolten(2000000000)
            )
            .itemOutputs(LegendSource.get(1))
            .eut(RECIPE_UXV)
            .duration(20 * 512)
            .addTo(AssemblyLine);

        TTRecipeAdder.addResearchableAssemblylineRecipe(
            LegendTarget.get(1),
            256_000_000,
            2048,
            512_000_000,
            1_048_576,
            new Object[]{
                LegendTarget.get(1),
                getModItem(GoodGenerator.ID, "compactFusionCoil", 1, 4),
                getModItem(GalaxySpace.ID, "dysonswarmparts", 1, 4),
                CustomItemList.Machine_Multi_Transformer.get(1),

                CustomItemList.eM_Power.get(64),
                GTOreDictUnificator.get(OrePrefixes.wireGt16, MaterialsUEVplus.SpaceTime, 64),
                GTOreDictUnificator.get(OrePrefixes.plateDense, MaterialsUEVplus.Eternity, 32),
                GTOreDictUnificator.get(OrePrefixes.plateDense, MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter, 16),

                GTOreDictUnificator.get(OrePrefixes.circuit.get(Materials.UXV), 16),
                ItemList.EnergisedTesseract.get(1)
            },
            new FluidStack[] {
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack( 1_296 * 64 * 4),
                MaterialsUEVplus.ExcitedDTSC.getFluid(500L * 64)
            },
            GTCMItemList.LegendaryWirelessEnergyHatch.get(1),
            20*60,
            (int) RECIPE_UMV
        );

        TTRecipeAdder.addResearchableAssemblylineRecipe(
            GTCMItemList.LegendaryWirelessEnergyHatch.get(1),
            2048_000_000,
            16384,
            512_000_000,
            512_000_000,
            new Object[]{
                GTCMItemList.LegendaryWirelessEnergyHatch.get(16),
                AdvancedHighPowerCoilBlock.get(64),
                ZPM6.get(64),
                GTCMItemList.MassFabricatorGenesis.get(1),

                HomoStructureMain.get(64),
                GTOreDictUnificator.get(OrePrefixes.wireGt16, MaterialsUEVplus.SpaceTime, 64),
                GTOreDictUnificator.get(OrePrefixes.plateDense, MaterialsUEVplus.Eternity, 32),
                GTOreDictUnificator.get(OrePrefixes.plateDense, MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter, 16),

                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.Transcendent, 16L),
                ItemList.EnergisedTesseract.get(64)
            },
            new FluidStack[] {
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack( 1_296 * 256 * 4),
                MaterialsUEVplus.ExcitedDTSC.getFluid(500L * 256)
            },
            GTCMItemList.HarmoniousWirelessEnergyHatch.get(1),
            20*60,
            (int) RECIPE_UXV
        );
    }
}
