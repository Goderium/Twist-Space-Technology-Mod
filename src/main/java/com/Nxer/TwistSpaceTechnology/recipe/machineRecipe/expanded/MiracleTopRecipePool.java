package com.Nxer.TwistSpaceTechnology.recipe.machineRecipe.expanded;

import static com.Nxer.TwistSpaceTechnology.common.api.ModItemHandler.ModItem.getModItem;
import static com.Nxer.TwistSpaceTechnology.util.TstUtils.removeIntegratedCircuitFromStacks;
import static com.Nxer.TwistSpaceTechnology.util.TstUtils.setStackSize;
import static gregtech.api.enums.TierEU.RECIPE_MAX;
import static gregtech.api.enums.TierEU.RECIPE_UEV;
import static gregtech.api.enums.TierEU.RECIPE_UMV;
import static gregtech.api.recipe.RecipeMaps.circuitAssemblerRecipes;
import static gregtech.api.util.GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTUtility.copyAmount;
import static gregtech.api.util.GTUtility.copyAmountUnsafe;
import static gtPlusPlus.core.material.Material.mComponentMap;
import static gtnhintergalactic.recipe.IGRecipeMaps.spaceAssemblerRecipes;
import static net.minecraft.item.ItemStack.areItemStacksEqual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.Nxer.TwistSpaceTechnology.TwistSpaceTechnology;
import com.Nxer.TwistSpaceTechnology.common.GTCMItemList;
import com.Nxer.TwistSpaceTechnology.common.material.MaterialPool;
import com.Nxer.TwistSpaceTechnology.common.recipeMap.GTCMRecipe;
import com.Nxer.TwistSpaceTechnology.config.Config;
import com.Nxer.TwistSpaceTechnology.util.recipes.TST_RecipeBuilder;
import com.Nxer.TwistSpaceTechnology.util.rewrites.TST_ItemID;
import com.dreammaster.item.NHItemList;
import com.github.bsideup.jabel.Desugar;

import bartworks.API.recipe.BartWorksRecipeMaps;
import bartworks.util.BWUtil;
import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.items.CircuitComponentFakeItem;
import gregtech.api.objects.ItemData;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.common.tileentities.machines.multi.nanochip.util.CircuitComponent;
import gtPlusPlus.core.material.Material;
import gtPlusPlus.core.material.MaterialsElements;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;

public class MiracleTopRecipePool {

    private static final RecipeMap<?> MT = GTCMRecipe.MiracleTopRecipes;
    private static RecipeMap<?>[] NAC_UNWRAP_RECIPE_MAPS;
    public static final HashMap<ItemStack, ItemStack> circuitItemsToWrapped = new HashMap<>();
    private static final HashSet<Materials> superConductorMaterialList = new HashSet<>();
    private static final HashSet<OrePrefixes> targetModifyOreDict = new HashSet<>();
    private static final HashSet<String> circuitGTOreDict = new HashSet<>();
    private static final HashMap<ItemStack, FluidStack> specialMaterialCantAutoModify = new HashMap<>();
    private static CircuitItemVariant[] circuitItems;

    public static void loadRecipes() {
        TwistSpaceTechnology.LOG.info("MiracleTopRecipePool loading recipes.");
        initStatics();
        loadNACRecipes();
        loadCircuitAssemblerRecipes();
        loadCircuitAssemblyLineRecipes();
        loadAssemblyLineRecipes();
        loadSpaceAssemblerRecipes();
        // loadCustomRecipes();
    }

    private static void loadCircuitAssemblerRecipes() {
        HashSet<TST_ItemID> IgnoreRecipeOutputs = new HashSet<>();
        // spotless:off
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 220)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 461)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 462)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 463)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 466)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 467)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 468)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 470)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("appliedenergistics2", "item.ItemMultiPart", 1, 472)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("ae2fc", "part_fluid_storage_bus", 1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("dreamcraft", "SchematicsAstroMiner", 1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("dreamcraft", "SchematicsMoonBuggy", 1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("dreamcraft", "SchematicsCargoRocket", 1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("dreamcraft", "SchematicsTier1", 1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("dreamcraft", "SchematicsTier2", 1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("dreamcraft", "SchematicsTier3", 1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("dreamcraft", "SchematicsTier4", 1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("dreamcraft", "SchematicsTier5", 1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("dreamcraft", "SchematicsTier6", 1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("dreamcraft", "SchematicsTier7", 1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("dreamcraft", "SchematicsTier8", 1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(tectech.thing.CustomItemList.parametrizerMemory.get(1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Board_Wetware.get(1)));
        IgnoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Board_Bio.get(1)));
        // spotless:on

        // Exclude low-level solder recipe
        List<GTRecipe> generatedRecipes = new ArrayList<>();
        Map<GTRecipe, Integer> integratedCircuitMap = new IdentityHashMap<>();
        for (GTRecipe originalRecipe : circuitAssemblerRecipes.getAllRecipes()) {
            if (IgnoreRecipeOutputs.contains(TST_ItemID.createNoNBT(originalRecipe.mOutputs[0]))) continue;
            // Do not generate some Mod recipe
            String itemName = Item.itemRegistry.getNameForObject(originalRecipe.mOutputs[0].getItem());
            if (itemName == null || itemName.contains(Mods.Railcraft.ID)
                || itemName.contains(Mods.Forestry.ID)
                || itemName.contains(Mods.StevesCarts2.ID)
                || itemName.contains(Mods.ProjectRedCore.ID)
                || itemName.contains(Mods.ProjectRedIllumination.ID)
                || itemName.contains(Mods.ProjectRedIntegration.ID)
                || itemName.contains(Mods.ProjectRedTransportation.ID)) continue;

            // Skip GT circuit
            if (hasCircuitOreDict(originalRecipe.mOutputs[0])) continue;

            for (GTRecipe recipeVariant : expandPreferredRecipeVariants(originalRecipe, false)) {
                GTRecipe recipeCopy = new GTRecipe(
                    false,
                    removeIntegratedCircuitFromStacks(recipeVariant.mInputs),
                    recipeVariant.mOutputs == null ? null : recipeVariant.mOutputs.clone(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    recipeVariant.mFluidInputs == null ? null : recipeVariant.mFluidInputs.clone(),
                    recipeVariant.mFluidOutputs == null ? null : recipeVariant.mFluidOutputs.clone(),
                    recipeVariant.mDuration,
                    recipeVariant.mEUt,
                    0);
                int integratedCircuitNum = 16;
                if (recipeVariant.mInputs != null) {
                    for (ItemStack itemStack : recipeVariant.mInputs) {
                        if (itemStack.getItem() == ItemList.Circuit_Integrated.getItem()) {
                            integratedCircuitNum += itemStack.getItemDamage();
                            if (integratedCircuitNum > 24) integratedCircuitNum -= 24;
                            break;
                        }
                    }
                }
                integratedCircuitMap.put(recipeCopy, integratedCircuitNum);
                generatedRecipes.add(recipeCopy);
            }
        }

        generatedRecipes = selectPreferredCircuitRecipes(generatedRecipes);

        for (GTRecipe aRecipe : generatedRecipes) {
            addRecipeMT(
                addIntegratedCircuitToRecipe(
                    reduplicateRecipe(ModifyRecipe(aRecipe, true), 3, 3, 4, 4, 1, 3),
                    integratedCircuitMap.getOrDefault(aRecipe, 16)));
        }

    }

    private static void loadCircuitAssemblyLineRecipes() {
        HashSet<TST_ItemID> ignoreRecipeOutputs = new HashSet<>();
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Crystalprocessor.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Crystalcomputer.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Ultimatecrystalcomputer.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Crystalmainframe.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Neuroprocessor.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Wetwarecomputer.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Wetwaresupercomputer.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Wetwaremainframe.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Bioprocessor.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Biowarecomputer.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Biowaresupercomputer.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Biomainframe.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_OpticalProcessor.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_OpticalAssembly.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_OpticalComputer.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_OpticalMainframe.get(1)));
        ignoreRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.HighEnergyFlowCircuit.get(1)));

        List<GTRecipe> generatedRecipes = new ArrayList<>();
        for (GTRecipe originalRecipe : BartWorksRecipeMaps.circuitAssemblyLineRecipes.getAllRecipes()) {
            if (originalRecipe == null || originalRecipe.mOutputs == null
                || originalRecipe.mOutputs.length == 0
                || ignoreRecipeOutputs.contains(TST_ItemID.createNoNBT(originalRecipe.mOutputs[0]))) continue;

            String itemName = Item.itemRegistry.getNameForObject(originalRecipe.mOutputs[0].getItem());
            if (itemName == null || itemName.contains(Mods.Railcraft.ID) || itemName.contains(Mods.Forestry.ID))
                continue;

            for (GTRecipe recipeVariant : expandPreferredRecipeVariants(originalRecipe, false)) {
                generatedRecipes.add(preprocessCircuitAssemblyLineRecipe(recipeVariant));
            }
        }

        generatedRecipes = selectPreferredCircuitRecipes(generatedRecipes);

        for (GTRecipe aRecipe : generatedRecipes) {
            addRecipeMT(
                addIntegratedCircuitToRecipe(reduplicateRecipe(ModifyRecipe(aRecipe, true), 3, 3, 4, 4, 1, 3), 16));
        }
    }

    private static void loadAssemblyLineRecipes() {
        HashSet<TST_ItemID> GenerateRecipeOutputs = new HashSet<>();
        // GenerateRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Wetwaremainframe.get(1)));
        // GenerateRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Biowaresupercomputer.get(1)));
        // GenerateRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Biomainframe.get(1)));
        // GenerateRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_OpticalAssembly.get(1)));
        // GenerateRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_OpticalComputer.get(1)));
        // GenerateRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_OpticalMainframe.get(1)));
        GenerateRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Chip_NeuroCPU.get(1)));
        GenerateRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Circuit_Chip_BioCPU.get(1)));
        // GenerateRecipeOutputs.add(TST_ItemID.createNoNBT(NHItemList.PikoCircuit.get(1)));
        // GenerateRecipeOutputs.add(TST_ItemID.createNoNBT(NHItemList.QuantumCircuit.get(1)));

        for (var aRecipe : sAssemblylineRecipes) {
            if (GenerateRecipeOutputs.contains(TST_ItemID.createNoNBT(aRecipe.mOutput))) {
                if (aRecipe.mOreDictAlt != null && aRecipe.mOreDictAlt.length > 0) {
                    List<List<ItemStack>> choiceList = new ArrayList<>();

                    for (int i = 0; i < aRecipe.mInputs.length; i++) {
                        boolean hasCircuit = false;

                        // Check if the Alt includes circuit.
                        // If stack has not itemData, it is a regular item and skip Ore Dict inspection
                        // If stack equals circuit, not process
                        if (i < aRecipe.mOreDictAlt.length && aRecipe.mOreDictAlt[i] != null) {
                            for (ItemStack stack : aRecipe.mOreDictAlt[i]) {

                                ItemData stackData = GTOreDictUnificator.getAssociation(stack);
                                if (stackData == null) break;
                                OrePrefixes prefix = stackData.mPrefix;
                                if (prefix == OrePrefixes.circuit) {
                                    hasCircuit = true;
                                    break;
                                }
                            }
                        }

                        if (hasCircuit) {
                            // If is tiered circuit, add ore dict form, and it will be modified to warp next.
                            ItemStack circuitStack = aRecipe.mOreDictAlt[i][0];
                            choiceList.add(
                                Collections.singletonList(
                                    GTOreDictUnificator.get(
                                        OrePrefixes.circuit,
                                        Objects.requireNonNull(
                                            GTOreDictUnificator.getAssociation(circuitStack)).mMaterial.mMaterial,
                                        circuitStack.stackSize)));
                        } else if (i < aRecipe.mOreDictAlt.length && aRecipe.mOreDictAlt[i] != null) {
                            // Regular Alt input
                            choiceList.add(filterPreferredAlternatives(Arrays.asList(aRecipe.mOreDictAlt[i]), false));
                        } else {
                            // Only one input
                            choiceList.add(Collections.singletonList(aRecipe.mInputs[i]));
                        }
                    }

                    List<ItemStack[]> validRecipes = new ArrayList<>();
                    int totalSlots = choiceList.size();
                    int[] indexArray = new int[totalSlots];

                    // check for valid recipe
                    while (true) {
                        List<ItemStack> currentCombination = new ArrayList<>();
                        boolean illegalRubber = false;
                        Materials usedMaterial = null;

                        for (int i = 0; i < totalSlots; i++) {
                            ItemStack aChoice = choiceList.get(i)
                                .get(indexArray[i]);
                            currentCombination.add(aChoice);

                            // Check rubber for same (Material.AnyRubber)
                            ItemData stackData = GTOreDictUnificator.getAssociation(aChoice);
                            if (stackData != null) {
                                Materials material = stackData.mMaterial.mMaterial;

                                if (material == Materials.StyreneButadieneRubber
                                    || material == Materials.RubberSilicone) {
                                    if (usedMaterial == null) {
                                        usedMaterial = material;
                                    } else if (usedMaterial != material) {
                                        illegalRubber = true;
                                    }
                                }

                            }
                        }

                        if (!illegalRubber) {
                            validRecipes.add(currentCombination.toArray(new ItemStack[0]));
                        }

                        int slot = totalSlots - 1;
                        while (slot >= 0) {
                            indexArray[slot]++;
                            if (indexArray[slot] < choiceList.get(slot)
                                .size()) break;
                            indexArray[slot] = 0;
                            slot--;
                        }
                        if (slot < 0) break;
                    }

                    for (ItemStack[] newInputs : validRecipes) {
                        addRecipeMT(
                            addIntegratedCircuitToRecipe(
                                ModifyRecipe(
                                    new GTRecipe(
                                        false,
                                        newInputs,
                                        new ItemStack[] { aRecipe.mOutput },
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        aRecipe.mFluidInputs,
                                        null,
                                        aRecipe.mDuration,
                                        aRecipe.mEUt,
                                        0)),
                                4));
                    }
                } else {
                    addRecipeMT(
                        addIntegratedCircuitToRecipe(
                            ModifyRecipe(
                                new GTRecipe(
                                    false,
                                    aRecipe.mInputs,
                                    new ItemStack[] { aRecipe.mOutput },
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    aRecipe.mFluidInputs,
                                    null,
                                    aRecipe.mDuration,
                                    aRecipe.mEUt,
                                    0)),
                            4));
                }
            }
        }
    }

    private static void loadNACRecipes() {
        List<GTRecipe> generatedRecipes = new ArrayList<>();
        for (GTRecipe aRecipe : RecipeMaps.nanochipAssemblyMatrixRecipes.getAllRecipes()) {
            for (GTRecipe recipeVariant : expandPreferredRecipeVariants(aRecipe, true)) {
                GTRecipe unwrappedRecipe = unwrapNACRecipe(recipeVariant);
                ItemStack output = unwrappedRecipe.mOutputs[0];
                if (getNACComponent(output) != null) continue;

                generatedRecipes.add(
                    new GTRecipe(
                        false,
                        unwrappedRecipe.mInputs,
                        new ItemStack[] { output },
                        null,
                        null,
                        null,
                        null,
                        null,
                        unwrappedRecipe.mFluidInputs,
                        null,
                        unwrappedRecipe.mDuration,
                        unwrappedRecipe.mEUt,
                        0));
            }
        }

        generatedRecipes = selectPreferredCircuitRecipes(generatedRecipes);

        for (GTRecipe cachedRecipe : generatedRecipes) {
            addRecipeMT(
                addIntegratedCircuitToRecipe(reduplicateRecipe(ModifyRecipe(cachedRecipe), 3, 3, 4, 4, 1, 3), 4));
        }
    }

    private static void loadSpaceAssemblerRecipes() {
        HashSet<TST_ItemID> generateRecipeOutputs = new HashSet<>();
        generateRecipeOutputs.add(TST_ItemID.createNoNBT(getModItem("OpenComputers", "item", 1, 39)));
        generateRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Optically_Perfected_CPU.get(1)));
        generateRecipeOutputs.add(TST_ItemID.createNoNBT(ItemList.Optically_Compatible_Memory.get(1)));
        List<GTRecipe> generatedRecipes = new ArrayList<>();
        for (GTRecipe aRecipe : spaceAssemblerRecipes.getAllRecipes()) {
            if (generateRecipeOutputs.contains(TST_ItemID.createNoNBT(aRecipe.mOutputs[0]))) {
                generatedRecipes.addAll(expandPreferredRecipeVariants(aRecipe, false));
            }
        }

        generatedRecipes = selectPreferredCircuitRecipes(generatedRecipes);

        for (GTRecipe aRecipe : generatedRecipes) {
            addRecipeMT(addIntegratedCircuitToRecipe(reduplicateRecipe(ModifyRecipe(aRecipe), 4, 1), 16));
        }
    }

    // Modify the circuit part to warp, others turn 16 times. All Material to molten.
    // Fluid inputs are multiplied by 16 by default; pass true to skip this for recipes already scaled later.
    public static GTRecipe ModifyRecipe(GTRecipe baseRecipe) {
        return ModifyRecipe(baseRecipe, false);
    }

    public static GTRecipe ModifyRecipe(GTRecipe baseRecipe, boolean skipFluidInputMultiply) {
        ArrayList<ItemStack> inputItems = new ArrayList<>();
        ArrayList<FluidStack> inputFluids = new ArrayList<>();

        if (baseRecipe.mFluidInputs != null && baseRecipe.mFluidInputs.length > 0) {
            if (skipFluidInputMultiply) Collections.addAll(inputFluids, baseRecipe.mFluidInputs);
            else Collections.addAll(inputFluids, reduplicateRecipe(baseRecipe, 16, 1).mFluidInputs);
        }

        if (baseRecipe.mInputs != null && baseRecipe.mInputs.length > 0) {
            for (ItemStack aStack : removeIntegratedCircuitFromStacks(baseRecipe.mInputs)) {
                boolean isItemModified = false;
                boolean isNeedTraverse = true;

                // Circuit parts are wrapped first; wrapped items keep the recipe compact without changing ratios.
                ItemStack wrappedCircuitStack = wrapCircuitComponent(aStack);
                if (wrappedCircuitStack != null) {
                    inputItems.add(copyAmountUnsafe(aStack.stackSize, wrappedCircuitStack));
                    isItemModified = true;
                }

                if (!isItemModified && BWUtil.checkStackAndPrefix(aStack)) {
                    ItemData Data = Objects.requireNonNull(GTOreDictUnificator.getAssociation(aStack));
                    Materials Material = Data.mMaterial.mMaterial;
                    OrePrefixes OreDict = Data.mPrefix;
                    // Melt standard GT material parts into fluids, but keep special superconductors as packed wires.
                    if (Material.getMolten(1) != null && targetModifyOreDict.contains(OreDict)) {
                        if (Material == Materials.TengamAttuned) Material = Materials.TengamPurified;
                        inputFluids.add(
                            Material
                                .getMolten(OreDict.getMaterialAmount() * INGOTS * aStack.stackSize / GTValues.M * 16));
                        isItemModified = true;
                    } else if (superConductorMaterialList.contains(Material) && OreDict != OrePrefixes.circuit) {
                        inputItems.add(
                            copyAmountUnsafe(
                                (int) (OreDict.getMaterialAmount() * aStack.stackSize * 2 / GTValues.M),
                                GTOreDictUnificator.get(OrePrefixes.wireGt16, Material, 1)));
                        isItemModified = true;
                    }
                    // if an item has GT ore dict, It not requires additional processing (like GTPP Materials)
                    isNeedTraverse = false;
                }

                if (!isItemModified && isNeedTraverse) {
                    // Fallback for non-GT items whose molten material cannot be inferred from ore dict data.
                    for (Map.Entry<ItemStack, FluidStack> entry : specialMaterialCantAutoModify.entrySet()) {
                        if (GTUtility.areStacksEqual(entry.getKey(), aStack)) {
                            inputFluids
                                .add(copyAmount(entry.getValue().amount * aStack.stackSize * 16, entry.getValue()));
                            isItemModified = true;
                            break;
                        }
                    }
                }

                if (!isItemModified) inputItems.add(copyAmountUnsafe(aStack.stackSize * 16, aStack));
            }

        }
        ItemStack outputStack = baseRecipe.mOutputs[0];
        outputStack = copyAmountUnsafe(outputStack.stackSize * 16, outputStack);
        return new GTRecipe(
            false,
            mergeSameItem(inputItems.toArray(new ItemStack[0])),
            new ItemStack[] { outputStack },
            null,
            null,
            null,
            null,
            null,
            mergeSameFluid(inputFluids.toArray(new FluidStack[0])),
            null,
            baseRecipe.mDuration * 3,
            baseRecipe.mEUt,
            0);
    }

    public static GTRecipe reduplicateRecipe(GTRecipe oRecipe, int inputItemMultiTimes, int inputFluidMultiTimes,
        int outputItemMultiTimes, int outputFluidMultiTimes, int eutMultiTimes, int durationMultiTimes) {
        ArrayList<ItemStack> inputItems = new ArrayList<>();
        ArrayList<FluidStack> inputFluids = new ArrayList<>();
        ArrayList<ItemStack> outputItems = new ArrayList<>();
        ArrayList<FluidStack> outputFluids = new ArrayList<>();

        if (oRecipe == null) return null;

        if (oRecipe.mInputs != null) {
            for (ItemStack aStack : oRecipe.mInputs) {
                inputItems.add(copyAmountUnsafe(aStack.stackSize * inputItemMultiTimes, aStack));
            }
        }
        if (oRecipe.mFluidInputs != null) {
            for (FluidStack aStack : oRecipe.mFluidInputs) {
                inputFluids.add(copyAmount(aStack.amount * inputFluidMultiTimes, aStack));
            }
        }

        if (oRecipe.mOutputs != null) {
            for (ItemStack aStack : oRecipe.mOutputs) {
                outputItems.add(copyAmountUnsafe(aStack.stackSize * outputItemMultiTimes, aStack));
            }
        }
        if (oRecipe.mFluidOutputs != null) {
            for (FluidStack aStack : oRecipe.mFluidOutputs) {
                outputFluids.add(copyAmount(aStack.amount * outputFluidMultiTimes, aStack));
            }
        }

        return new GTRecipe(
            false,
            inputItems.toArray(new ItemStack[0]),
            outputItems.toArray(new ItemStack[0]),
            null,
            null,
            null,
            null,
            null,
            inputFluids.toArray(new FluidStack[0]),
            outputFluids.toArray(new FluidStack[0]),
            oRecipe.mDuration * durationMultiTimes,
            oRecipe.mEUt * eutMultiTimes,
            0);
    }

    public static GTRecipe reduplicateRecipe(GTRecipe oRecipe, int n, int eut) {
        return reduplicateRecipe(oRecipe, n, n, n, n, eut, n);
    }

    public static GTRecipe addIntegratedCircuitToRecipe(GTRecipe oRecipe, int circuitNum) {
        ArrayList<ItemStack> inputItems = new ArrayList<>();
        inputItems.add(GTUtility.getIntegratedCircuit(circuitNum));

        if (oRecipe == null) return null;
        Collections.addAll(inputItems, oRecipe.mInputs);

        return new GTRecipe(
            false,
            inputItems.toArray(new ItemStack[0]),
            oRecipe.mOutputs,
            null,
            null,
            null,
            null,
            null,
            oRecipe.mFluidInputs,
            oRecipe.mFluidOutputs,
            oRecipe.mDuration,
            oRecipe.mEUt,
            0);
    }

    public static boolean isRecipeInputItemSame(GTRecipe a, GTRecipe b) {
        if (!areItemStacksEqual(a.mOutputs[0], b.mOutputs[0])) return false;
        if (a.mInputs.length != b.mInputs.length) return false;
        for (int i = 0; i < a.mInputs.length; i++) {
            if (!areItemStacksEqual(a.mInputs[i], b.mInputs[i])) {
                return false;
            }
        }
        return true;
    }

    public static FluidStack[] mergeSameFluid(FluidStack[] fluidStacks) {

        Map<Fluid, Integer> fluidMap = new LinkedHashMap<>();

        for (FluidStack aStack : fluidStacks) {
            fluidMap.put(aStack.getFluid(), fluidMap.getOrDefault(aStack.getFluid(), 0) + aStack.amount);
        }

        ArrayList<FluidStack> mergedList = new ArrayList<>();
        for (Map.Entry<Fluid, Integer> entry : fluidMap.entrySet()) {
            mergedList.add(new FluidStack(entry.getKey(), entry.getValue()));
        }

        return mergedList.toArray(new FluidStack[0]);
    }

    public static ItemStack[] mergeSameItem(ItemStack[] itemStacks) {

        Map<TST_ItemID, ItemStack> itemMap = new LinkedHashMap<>();

        for (ItemStack aStack : itemStacks) {
            if (aStack == null) continue;
            TST_ItemID key = TST_ItemID.create(aStack);
            ItemStack mergedStack = itemMap.get(key);
            if (mergedStack == null) {
                itemMap.put(key, aStack.copy());
            } else {
                mergedStack.stackSize += aStack.stackSize;
            }
        }

        ArrayList<ItemStack> mergedList = new ArrayList<>(itemMap.values());

        return mergedList.toArray(new ItemStack[0]);
    }

    private static void addRecipeMT(GTRecipe aRecipe) {
        if (aRecipe == null) return;
        TST_RecipeBuilder.builder()
            .itemInputs(aRecipe.mInputs)
            .fluidInputs(aRecipe.mFluidInputs)
            .itemOutputs(aRecipe.mOutputs)
            .eut(aRecipe.mEUt)
            .duration(aRecipe.mDuration)
            .addTo(MT);
    }

    /**
     * NAC stores inputs as fake items identified by CircuitComponent metadata.
     * For regular processed parts and nested circuit tiers, restore the backing real item so the existing MT
     * conversion logic can keep working. NAC-only special intermediates without a real backing item stay untouched.
     */
    private static ItemStack tryUnwrapNACComponent(ItemStack stack) {
        CircuitComponent component = getNACComponent(stack);
        if (component == null) return stack;

        if (component.isProcessed && component.componentForProcessed != null) {
            component = component.componentForProcessed.get();
        }

        if (component == null || component.realComponent == null) return stack;

        ItemStack realStack = component.realComponent.get();
        if (realStack == null) {
            realStack = switch (component) {
                case ProcessedPicoCircuitCasing -> NHItemList.PikoCircuit.get(1);
                case ProcessedQuantumCircuitCasing -> NHItemList.QuantumCircuit.get(1);
                case ProcessedPlanckCircuitCasing -> getModItem("dreamcraft", "PlanckCircuit", 1);
                default -> null;
            };
        }
        if (realStack == null) return stack;

        return copyAmountUnsafe(stack.stackSize, realStack);
    }

    private static CircuitComponent getNACComponent(ItemStack stack) {
        if (stack == null || stack.getItem() != CircuitComponentFakeItem.INSTANCE) return null;
        return CircuitComponent.tryGetFromFakeStack(stack);
    }

    private static GTRecipe unwrapNACRecipe(GTRecipe recipe) {
        ArrayList<ItemStack> unwrappedInputs = new ArrayList<>();
        ArrayList<FluidStack> extraFluids = new ArrayList<>();
        int extraDuration = 0;

        if (recipe.mInputs != null) {
            for (ItemStack input : recipe.mInputs) {
                NACUnwrapResult result = unwrapNACInputStack(input, new HashSet<>(), recipe.mEUt);
                result.appendTo(unwrappedInputs, extraFluids);
                extraDuration += result.extraDuration;
            }
        }

        ItemStack[] unwrappedOutputs = recipe.mOutputs == null ? null : recipe.mOutputs.clone();
        if (unwrappedOutputs != null) {
            for (int i = 0; i < unwrappedOutputs.length; i++) {
                unwrappedOutputs[i] = tryUnwrapNACComponent(unwrappedOutputs[i]);
            }
        }

        ArrayList<FluidStack> mergedFluids = new ArrayList<>(extraFluids);
        if (recipe.mFluidInputs != null && recipe.mFluidInputs.length > 0) {
            Collections.addAll(mergedFluids, recipe.mFluidInputs.clone());
        }

        return new GTRecipe(
            false,
            mergeSameItem(unwrappedInputs.toArray(new ItemStack[0])),
            unwrappedOutputs,
            null,
            null,
            null,
            null,
            null,
            mergeSameFluid(mergedFluids.toArray(new FluidStack[0])),
            null,
            recipe.mDuration + extraDuration,
            recipe.mEUt,
            0);
    }

    private static NACUnwrapResult unwrapNACInputStack(ItemStack stack, HashSet<String> visiting, int targetRecipeEUt) {
        if (stack == null) return NACUnwrapResult.empty();

        CircuitComponent component = getNACComponent(stack);
        if (component == null) {
            return NACUnwrapResult.of(copyAmountUnsafe(stack.stackSize, stack));
        }

        String visitKey = TST_ItemID.create(stack) + ":" + stack.stackSize;
        if (!visiting.add(visitKey)) {
            return NACUnwrapResult.of(copyAmountUnsafe(stack.stackSize, tryUnwrapNACComponent(stack)));
        }

        try {
            GTRecipe subRecipe = findNACUnwrapRecipe(stack);
            if (subRecipe == null || subRecipe.mOutputs == null || subRecipe.mOutputs.length == 0) {
                return NACUnwrapResult.of(copyAmountUnsafe(stack.stackSize, tryUnwrapNACComponent(stack)));
            }

            int outputAmount = Math.max(1, subRecipe.mOutputs[0].stackSize);
            int multiplier = Math.max(1, stack.stackSize / outputAmount);
            if (stack.stackSize % outputAmount != 0) multiplier++;

            ArrayList<ItemStack> itemInputs = new ArrayList<>();
            ArrayList<FluidStack> fluidInputs = new ArrayList<>();
            // Scale wrapped sub-step time against the final target recipe tier before folding it back in.
            int duration = scaleUnwrapDuration(subRecipe.mDuration * multiplier, subRecipe.mEUt, targetRecipeEUt);

            if (subRecipe.mInputs != null) {
                for (ItemStack subInput : subRecipe.mInputs) {
                    NACUnwrapResult childResult = unwrapNACInputStack(
                        copyAmountUnsafe(subInput.stackSize * multiplier, subInput),
                        visiting,
                        targetRecipeEUt);
                    childResult.appendTo(itemInputs, fluidInputs);
                    duration += childResult.extraDuration;
                }
            }

            if (subRecipe.mFluidInputs != null) {
                for (FluidStack fluid : subRecipe.mFluidInputs) {
                    // Internal NAC wrapping fluid is folded back into the final recipe at 8x cost.
                    fluidInputs.add(copyAmount(fluid.amount * multiplier * 8, fluid));
                }
            }

            return new NACUnwrapResult(
                mergeSameItem(itemInputs.toArray(new ItemStack[0])),
                mergeSameFluid(fluidInputs.toArray(new FluidStack[0])),
                duration);
        } finally {
            visiting.remove(visitKey);
        }
    }

    private static GTRecipe findNACUnwrapRecipe(ItemStack output) {
        for (RecipeMap<?> recipeMap : NAC_UNWRAP_RECIPE_MAPS) {
            for (GTRecipe recipe : recipeMap.getAllRecipes()) {
                if (recipe.mOutputs != null && recipe.mOutputs.length > 0
                    && GTUtility.areStacksEqual(recipe.mOutputs[0], output)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    private static int scaleUnwrapDuration(int duration, int recipeEUt, int targetRecipeEUt) {
        if (duration <= 0 || recipeEUt <= 0 || targetRecipeEUt <= 0) return duration;

        int recipeTier = getRecipeVoltageTier(recipeEUt);
        int targetTier = getRecipeVoltageTier(targetRecipeEUt);
        int tierDiff = targetTier - recipeTier;
        if (tierDiff <= 0) return duration;

        long scaledDuration = duration;
        for (int i = 0; i < tierDiff; i++) {
            scaledDuration = (scaledDuration + 1L) / 2L;
            if (scaledDuration <= 1L) return 1;
        }
        return (int) scaledDuration;
    }

    @Desugar
    private record NACUnwrapResult(ItemStack[] itemInputs, FluidStack[] fluidInputs, int extraDuration) {

        private static final NACUnwrapResult EMPTY = new NACUnwrapResult(new ItemStack[0], new FluidStack[0], 0);

        private static NACUnwrapResult empty() {
            return EMPTY;
        }

        private static NACUnwrapResult of(ItemStack stack) {
            return new NACUnwrapResult(
                stack == null ? new ItemStack[0] : new ItemStack[] { stack },
                new FluidStack[0],
                0);
        }

        private void appendTo(List<ItemStack> itemList, List<FluidStack> fluidList) {
            Collections.addAll(itemList, itemInputs);
            Collections.addAll(fluidList, fluidInputs);
        }
    }

    // Groups define which items compete with each other during tier-based deduplication.
    private enum CircuitItemGroup {

        // NONE entries are still wrapped/unwrapped, but never used for tier preference.
        NONE(false),
        BOARD(true),
        RESISTOR(true),
        DIODE(true),
        TRANSISTOR(true),
        CAPACITOR(true),
        INDUCTOR(true);

        private final boolean rankedVariantGroup;

        CircuitItemGroup(boolean rankedVariantGroup) {
            this.rankedVariantGroup = rankedVariantGroup;
        }

        private boolean isRankedVariantGroup() {
            return rankedVariantGroup;
        }
    }

    // Single source of truth for wrapped item lookup and tier comparison metadata.
    @Desugar
    private record CircuitItemVariant(ItemStack original, WrappedCircuitItem wrapped, CircuitItemGroup group,
        int tier) {

        private CircuitItemVariant(ItemStack original, WrappedCircuitItem wrapped, CircuitItemGroup group) {
            this(original, wrapped, group, -1);
        }

    }

    private static int getRecipeVoltageTier(int eut) {
        if (eut <= 0) return -1;
        if (eut <= 8) return 0;
        return (int) Math.floor(Math.log(eut / 8.0D) / Math.log(4.0D)) + 1;
    }

    private static GTRecipe preprocessCircuitAssemblyLineRecipe(GTRecipe recipe) {
        ArrayList<ItemStack> inputItems = new ArrayList<>();
        ItemStack[] inputs = recipe.mInputs == null ? null : removeIntegratedCircuitFromStacks(recipe.mInputs);
        if (inputs != null) {
            for (ItemStack stack : inputs) {
                ItemStack unwrapped = unwrapWrappedCircuitComponent(stack);
                int amount = unwrapped.stackSize;
                if (GTUtility.areStacksEqual(unwrapped, stack)) {
                    amount = amount / 16;
                }

                inputItems.add(copyAmountUnsafe(amount, unwrapped));
            }
        }

        FluidStack[] fluidInputs = recipe.mFluidInputs == null ? null : recipe.mFluidInputs.clone();
        ItemStack[] outputs = recipe.mOutputs == null ? null : recipe.mOutputs.clone();
        if (outputs != null) {
            for (int i = 0; i < outputs.length; i++) {
                if (outputs[i] != null) {
                    outputs[i] = copyAmountUnsafe(Math.max(1, outputs[i].stackSize / 16), outputs[i]);
                }
            }
        }

        return new GTRecipe(
            false,
            inputItems.toArray(new ItemStack[0]),
            outputs,
            null,
            null,
            null,
            null,
            null,
            fluidInputs,
            recipe.mFluidOutputs == null ? null : recipe.mFluidOutputs.clone(),
            Math.max(1, recipe.mDuration / 3),
            recipe.mEUt,
            0);
    }

    private static List<GTRecipe> selectPreferredCircuitRecipes(List<GTRecipe> recipes) {
        List<GTRecipe> selectedRecipes = recipes;

        // Step 1: same item/fluid types, but different solder fluids. Keep the best solder.
        Map<String, GTRecipe> recipeCache = new LinkedHashMap<>();
        for (GTRecipe recipe : selectedRecipes) {
            String recipeKey = buildRecipeTypeKey(recipe, null, true, false);
            GTRecipe cachedRecipe = recipeCache.get(recipeKey);
            if (cachedRecipe == null || getSolderRank(recipe.mFluidInputs) > getSolderRank(cachedRecipe.mFluidInputs)) {
                recipeCache.put(recipeKey, recipe);
            }
        }
        selectedRecipes = new ArrayList<>(recipeCache.values());

        // Step 2: same recipe except ranked board/component tiers. Keep the highest complete combination.
        recipeCache = new LinkedHashMap<>();
        HashSet<CircuitItemGroup> rankedVariantGroups = new HashSet<>();
        for (CircuitItemVariant item : circuitItems) {
            if (item.group()
                .isRankedVariantGroup()) {
                rankedVariantGroups.add(item.group());
            }
        }
        for (GTRecipe recipe : selectedRecipes) {
            String recipeKey = buildRecipeTypeKey(recipe, rankedVariantGroups, false, false);
            GTRecipe cachedRecipe = recipeCache.get(recipeKey);
            if (cachedRecipe == null || isPreferredRecipeForGroups(recipe, cachedRecipe, rankedVariantGroups)) {
                recipeCache.put(recipeKey, recipe);
            }
        }
        selectedRecipes = new ArrayList<>(recipeCache.values());

        // Step 3: copper and annealed copper wires are equivalent choices; prefer annealed copper.
        recipeCache = new LinkedHashMap<>();
        for (GTRecipe recipe : selectedRecipes) {
            String recipeKey = buildRecipeTypeKey(recipe, null, false, true);
            GTRecipe cachedRecipe = recipeCache.get(recipeKey);
            if (cachedRecipe == null) {
                recipeCache.put(recipeKey, recipe);
                continue;
            }

            boolean recipeHasAnnealedCopper = false;
            if (recipe.mInputs != null) {
                for (ItemStack stack : recipe.mInputs) {
                    if (!BWUtil.checkStackAndPrefix(stack)) continue;
                    ItemData data = GTOreDictUnificator.getAssociation(stack);
                    if (data == null || data.mMaterial == null || data.mMaterial.mMaterial == null) continue;
                    OrePrefixes prefix = data.mPrefix;
                    if (prefix != OrePrefixes.wireFine && prefix != OrePrefixes.wireGt01
                        && prefix != OrePrefixes.wireGt02
                        && prefix != OrePrefixes.wireGt04
                        && prefix != OrePrefixes.wireGt08
                        && prefix != OrePrefixes.wireGt12
                        && prefix != OrePrefixes.wireGt16) continue;
                    if (data.mMaterial.mMaterial == Materials.AnnealedCopper) {
                        recipeHasAnnealedCopper = true;
                        break;
                    }
                }
            }

            boolean cachedRecipeHasAnnealedCopper = false;
            if (cachedRecipe.mInputs != null) {
                for (ItemStack stack : cachedRecipe.mInputs) {
                    if (!BWUtil.checkStackAndPrefix(stack)) continue;
                    ItemData data = GTOreDictUnificator.getAssociation(stack);
                    if (data == null || data.mMaterial == null || data.mMaterial.mMaterial == null) continue;
                    OrePrefixes prefix = data.mPrefix;
                    if (prefix != OrePrefixes.wireFine && prefix != OrePrefixes.wireGt01
                        && prefix != OrePrefixes.wireGt02
                        && prefix != OrePrefixes.wireGt04
                        && prefix != OrePrefixes.wireGt08
                        && prefix != OrePrefixes.wireGt12
                        && prefix != OrePrefixes.wireGt16) continue;
                    if (data.mMaterial.mMaterial == Materials.AnnealedCopper) {
                        cachedRecipeHasAnnealedCopper = true;
                        break;
                    }
                }
            }

            if (recipeHasAnnealedCopper && !cachedRecipeHasAnnealedCopper) {
                recipeCache.put(recipeKey, recipe);
            }
        }
        return new ArrayList<>(recipeCache.values());
    }

    private static String buildRecipeTypeKey(GTRecipe recipe, Set<CircuitItemGroup> ignoredInputGroups,
        boolean ignoreSolderFluids, boolean normalizeCopperWireMaterial) {
        // The key compares recipe shapes only: item/fluid types matter, amounts, EUt and duration do not.
        StringBuilder key = new StringBuilder();
        appendItemTypes(key, recipe.mOutputs, null, false);
        key.append('|');
        appendItemTypes(key, recipe.mInputs, ignoredInputGroups, normalizeCopperWireMaterial);
        key.append('|');
        appendFluidTypes(key, recipe.mFluidInputs, ignoreSolderFluids);
        return key.toString();
    }

    private static void appendItemTypes(StringBuilder key, ItemStack[] stacks, Set<CircuitItemGroup> ignoredGroups,
        boolean normalizeCopperWireMaterial) {
        ArrayList<String> tokens = new ArrayList<>();
        for (ItemStack stack : uniqueStacksByType(stacks)) {
            // Ignored groups are the variant axis being selected in the current pass.
            CircuitItemVariant variant = getCircuitItemVariant(stack);
            CircuitItemGroup group = variant == null ? CircuitItemGroup.NONE : variant.group();
            if (ignoredGroups != null && ignoredGroups.contains(group)) continue;
            String token = null;
            if (normalizeCopperWireMaterial && BWUtil.checkStackAndPrefix(stack)) {
                ItemData data = GTOreDictUnificator.getAssociation(stack);
                if (data != null && data.mMaterial != null && data.mMaterial.mMaterial != null) {
                    Materials material = data.mMaterial.mMaterial;
                    if (material == Materials.Copper || material == Materials.AnnealedCopper) {
                        ItemStack annealedCopperWire = GTOreDictUnificator
                            .get(data.mPrefix, Materials.AnnealedCopper, 1L);
                        if (annealedCopperWire != null) {
                            token = TST_ItemID.createNoNBT(annealedCopperWire) + ";";
                        }
                    }
                }
            }
            tokens.add(token != null ? token : TST_ItemID.createNoNBT(stack) + ";");
        }
        Collections.sort(tokens);
        for (String token : tokens) {
            key.append(token);
        }
    }

    private static void appendFluidTypes(StringBuilder key, FluidStack[] stacks, boolean ignoreSolderFluids) {
        ArrayList<String> tokens = new ArrayList<>();
        for (FluidStack stack : uniqueFluidsByType(stacks)) {
            if (ignoreSolderFluids && stack != null
                && (stack.isFluidEqual(Materials.Lead.getMolten(1)) || stack.isFluidEqual(Materials.Tin.getMolten(1))
                    || stack.isFluidEqual(Materials.SolderingAlloy.getMolten(1))))
                continue;
            if (stack != null) {
                tokens.add(
                    stack.getFluid()
                        .getName() + ';');
            }
        }
        Collections.sort(tokens);
        for (String token : tokens) {
            key.append(token);
        }
    }

    private static List<ItemStack> uniqueStacksByType(ItemStack[] stacks) {
        if (stacks == null || stacks.length == 0) return Collections.emptyList();
        LinkedHashMap<TST_ItemID, ItemStack> uniqueStacks = new LinkedHashMap<>();
        for (ItemStack stack : stacks) {
            if (stack == null) continue;
            uniqueStacks.putIfAbsent(TST_ItemID.createNoNBT(stack), stack);
        }
        return new ArrayList<>(uniqueStacks.values());
    }

    private static List<FluidStack> uniqueFluidsByType(FluidStack[] stacks) {
        if (stacks == null || stacks.length == 0) return Collections.emptyList();
        LinkedHashMap<String, FluidStack> uniqueFluids = new LinkedHashMap<>();
        for (FluidStack stack : stacks) {
            if (stack == null) continue;
            uniqueFluids.putIfAbsent(
                stack.getFluid()
                    .getName(),
                stack);
        }
        return new ArrayList<>(uniqueFluids.values());
    }

    private static boolean isPreferredRecipeForGroups(GTRecipe candidate, GTRecipe cachedRecipe,
        Set<CircuitItemGroup> groups) {
        boolean candidateHasHigherTier = false;
        boolean cachedHasHigherTier = false;
        for (CircuitItemGroup group : groups) {
            int candidateRank = getHighestCircuitGroupRank(candidate.mInputs, group);
            int cachedRank = getHighestCircuitGroupRank(cachedRecipe.mInputs, group);
            if (candidateRank > cachedRank) candidateHasHigherTier = true;
            if (cachedRank > candidateRank) cachedHasHigherTier = true;
        }
        // Mixed wins and losses mean the recipes are different combinations, so keep both.
        return candidateHasHigherTier && !cachedHasHigherTier;
    }

    private static int getHighestCircuitGroupRank(ItemStack[] stacks, CircuitItemGroup group) {
        if (stacks == null) return -1;
        int highestRank = -1;
        for (ItemStack stack : uniqueStacksByType(stacks)) {
            CircuitItemVariant variant = getCircuitItemVariant(stack);
            if (variant != null && variant.group() == group && variant.tier() > highestRank) {
                highestRank = variant.tier();
            }
        }
        return highestRank;
    }

    private static int getSolderRank(FluidStack[] stacks) {
        if (stacks == null) return 0;
        int rank = 0;
        for (FluidStack stack : stacks) {
            if (stack == null) continue;
            if (stack.isFluidEqual(Materials.SolderingAlloy.getMolten(1))) return 3;
            if (stack.isFluidEqual(Materials.Tin.getMolten(1))) rank = 2;
            else if (stack.isFluidEqual(Materials.Lead.getMolten(1))) rank = Math.max(rank, 1);
        }
        return rank;
    }

    private static List<ItemStack> filterPreferredAlternatives(List<ItemStack> alternatives,
        boolean unwrapNACComponents) {
        if (alternatives == null || alternatives.isEmpty()) return Collections.emptyList();

        int bestCircuitPartRank = -1;
        ItemStack bestCircuitPart = null;
        List<ItemStack> preservedAlternatives = new ArrayList<>(alternatives.size());

        for (ItemStack stack : alternatives) {
            ItemStack unwrapped = unwrapNACComponents ? tryUnwrapNACComponent(stack) : stack;
            preservedAlternatives.add(stack);

            CircuitItemVariant variant = getCircuitItemVariant(unwrapped);
            int rank = variant == null ? -1 : variant.tier();
            if (rank > bestCircuitPartRank) {
                bestCircuitPartRank = rank;
                bestCircuitPart = stack;
            }
        }

        if (bestCircuitPart != null) return Collections.singletonList(bestCircuitPart);
        return preservedAlternatives;
    }

    private static List<GTRecipe> expandPreferredRecipeVariants(GTRecipe recipe, boolean unwrapNACComponents) {
        if (!(recipe instanceof GTRecipe.GTRecipe_WithAlt recipeWithAlt) || recipeWithAlt.mOreDictAlt == null
            || recipeWithAlt.mOreDictAlt.length == 0) {
            return Collections.singletonList(recipe);
        }

        // Build a cartesian product of remaining alternatives after ranking circuit-part choices.
        List<List<ItemStack>> choiceList = new ArrayList<>();
        for (int i = 0; i < recipe.mInputs.length; i++) {
            if (i < recipeWithAlt.mOreDictAlt.length && recipeWithAlt.mOreDictAlt[i] != null
                && recipeWithAlt.mOreDictAlt[i].length > 0) {
                choiceList
                    .add(filterPreferredAlternatives(Arrays.asList(recipeWithAlt.mOreDictAlt[i]), unwrapNACComponents));
            } else {
                choiceList.add(Collections.singletonList(recipe.mInputs[i]));
            }
        }

        List<GTRecipe> expandedRecipes = new ArrayList<>();
        int totalSlots = choiceList.size();
        int[] indexArray = new int[totalSlots];

        // Iterate the cartesian product without recursion to avoid deep call stacks on large alt lists.
        while (true) {
            ItemStack[] inputs = new ItemStack[totalSlots];
            for (int i = 0; i < totalSlots; i++) {
                inputs[i] = choiceList.get(i)
                    .get(indexArray[i]);
            }

            expandedRecipes.add(
                new GTRecipe(
                    false,
                    inputs,
                    recipe.mOutputs == null ? null : recipe.mOutputs.clone(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    recipe.mFluidInputs == null ? null : recipe.mFluidInputs.clone(),
                    recipe.mFluidOutputs == null ? null : recipe.mFluidOutputs.clone(),
                    recipe.mDuration,
                    recipe.mEUt,
                    0));

            int slot = totalSlots - 1;
            while (slot >= 0) {
                indexArray[slot]++;
                if (indexArray[slot] < choiceList.get(slot)
                    .size()) break;
                indexArray[slot] = 0;
                slot--;
            }
            if (slot < 0) break;
        }

        return expandedRecipes;
    }

    private static CircuitItemVariant getCircuitItemVariant(ItemStack stack) {
        if (stack == null) return null;
        TST_ItemID id = TST_ItemID.createNoNBT(stack);
        for (CircuitItemVariant variant : circuitItems) {
            if (id.equals(TST_ItemID.createNoNBT(variant.original))
                || id.equals(TST_ItemID.createNoNBT(variant.wrapped.get(1)))) {
                return variant;
            }
        }
        return null;
    }

    private static ItemStack unwrapWrappedCircuitComponent(ItemStack stack) {
        if (stack == null) return null;
        for (CircuitItemVariant variant : circuitItems) {
            if (GTUtility.areStacksEqual(variant.wrapped.get(1), stack)) {
                return copyAmountUnsafe(stack.stackSize, variant.original);
            }
        }
        for (Map.Entry<ItemStack, ItemStack> entry : circuitItemsToWrapped.entrySet()) {
            if (GTUtility.areStacksEqual(entry.getValue(), stack)) {
                return copyAmountUnsafe(stack.stackSize, entry.getKey());
            }
        }
        return stack;
    }

    private static ItemStack wrapCircuitComponent(ItemStack stack) {
        if (stack == null) return null;
        for (CircuitItemVariant variant : circuitItems) {
            if (GTUtility.areStacksEqual(variant.original, stack)) {
                return variant.wrapped.get(1);
            }
        }
        for (Map.Entry<ItemStack, ItemStack> entry : circuitItemsToWrapped.entrySet()) {
            if (GTUtility.areStacksEqual(entry.getKey(), stack)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private static void addGTCircuitOreDictNames(ItemStack stack) {
        if (stack == null) return;

        for (int oreId : OreDictionary.getOreIDs(stack)) {
            circuitGTOreDict.add(OreDictionary.getOreName(oreId));
        }
    }

    private static boolean hasCircuitOreDict(ItemStack stack) {
        if (stack == null) return false;

        for (int oreId : OreDictionary.getOreIDs(stack)) {
            if (circuitGTOreDict.contains(OreDictionary.getOreName(oreId))) {
                return true;
            }
        }

        return false;
    }

    private static void initStatics() {
        NAC_UNWRAP_RECIPE_MAPS = new RecipeMap<?>[] { RecipeMaps.nanochipSMDProcessorRecipes,
            RecipeMaps.nanochipBoardProcessorRecipes, RecipeMaps.nanochipEtchingArray,
            RecipeMaps.nanochipCuttingChamber, RecipeMaps.nanochipWireTracer, RecipeMaps.nanochipSuperconductorSplitter,
            RecipeMaps.nanochipOpticalOrganizer, RecipeMaps.nanochipEncasementWrapper,
            RecipeMaps.nanochipBiologicalCoordinator };

        // spotless:off

        // One ordered source drives both tier comparison and wrapped-item lookup.
        circuitItems = new CircuitItemVariant[] {
            // GT circuit ore-dict tiers are wrapped, but they are not compared by this selection logic.
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 1), WrappedCircuitItem.Wrapped_Circuit_ULV, CircuitItemGroup.NONE, 0),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1), WrappedCircuitItem.Wrapped_Circuit_LV, CircuitItemGroup.NONE, 1),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1), WrappedCircuitItem.Wrapped_Circuit_MV, CircuitItemGroup.NONE, 2),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1), WrappedCircuitItem.Wrapped_Circuit_HV, CircuitItemGroup.NONE, 3),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1), WrappedCircuitItem.Wrapped_Circuit_EV, CircuitItemGroup.NONE, 4),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1), WrappedCircuitItem.Wrapped_Circuit_IV, CircuitItemGroup.NONE, 5),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 1), WrappedCircuitItem.Wrapped_Circuit_LuV, CircuitItemGroup.NONE, 6),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ZPM, 1), WrappedCircuitItem.Wrapped_Circuit_ZPM, CircuitItemGroup.NONE, 7),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UV, 1), WrappedCircuitItem.Wrapped_Circuit_UV, CircuitItemGroup.NONE, 8),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UHV, 1), WrappedCircuitItem.Wrapped_Circuit_UHV, CircuitItemGroup.NONE, 9),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UEV, 1), WrappedCircuitItem.Wrapped_Circuit_UEV, CircuitItemGroup.NONE, 10),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UIV, 1), WrappedCircuitItem.Wrapped_Circuit_UIV, CircuitItemGroup.NONE, 11),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UMV, 1), WrappedCircuitItem.Wrapped_Circuit_UMV, CircuitItemGroup.NONE, 12),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UXV, 1), WrappedCircuitItem.Wrapped_Circuit_UXV, CircuitItemGroup.NONE, 13),
            new CircuitItemVariant(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MAX, 1), WrappedCircuitItem.Wrapped_Circuit_MAX, CircuitItemGroup.NONE, 14),

            // Board rank must follow the real progression order used by recipe selection.
            new CircuitItemVariant(ItemList.Circuit_Board_Coated_Basic.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Coated_Basic, CircuitItemGroup.BOARD, 0),
            new CircuitItemVariant(ItemList.Circuit_Board_Phenolic_Good.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Phenolic_Good, CircuitItemGroup.BOARD, 1),
            new CircuitItemVariant(ItemList.Circuit_Board_Plastic_Advanced.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Plastic_Advanced, CircuitItemGroup.BOARD, 2),
            new CircuitItemVariant(ItemList.Circuit_Board_Epoxy_Advanced.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Epoxy_Advanced, CircuitItemGroup.BOARD, 3),
            new CircuitItemVariant(ItemList.Circuit_Board_Fiberglass_Advanced.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Fiberglass_Advanced, CircuitItemGroup.BOARD, 4),
            new CircuitItemVariant(ItemList.Circuit_Board_Multifiberglass_Elite.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Multifiberglass_Elite, CircuitItemGroup.BOARD, 5),
            new CircuitItemVariant(ItemList.Circuit_Board_Wetware_Extreme.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Wetware_Extreme, CircuitItemGroup.BOARD, 6),
            new CircuitItemVariant(ItemList.Circuit_Board_Bio_Ultra.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Bio_Ultra, CircuitItemGroup.BOARD, 7),
            new CircuitItemVariant(ItemList.Circuit_Board_Optical.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Optical, CircuitItemGroup.BOARD, 8),

            // Legacy boards and special chips are only wrapped/unwrapped; they do not define tier preference.
            new CircuitItemVariant(ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_Crystal_Chip_Elite, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Parts_Crystal_Chip_Master.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_Crystal_Chip_Master, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Board_Coated.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Coated, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Board_Phenolic.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Phenolic, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Board_Epoxy.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Epoxy, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Board_Fiberglass.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Fiberglass, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Board_Multifiberglass.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Multifiberglass, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Board_Wetware.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Wetware, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Board_Plastic.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Plastic, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Board_Bio.get(1), WrappedCircuitItem.Wrapped_Circuit_Board_Bio, CircuitItemGroup.NONE),

            // Ranked basic circuit components. Higher tier entries replace lower tier variants during selection.
            new CircuitItemVariant(ItemList.Circuit_Parts_ResistorSMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_ResistorSMD, CircuitItemGroup.RESISTOR, 0),
            new CircuitItemVariant(ItemList.Circuit_Parts_ResistorASMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_ResistorASMD, CircuitItemGroup.RESISTOR, 1),
            new CircuitItemVariant(ItemList.Circuit_Parts_ResistorXSMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_ResistorXSMD, CircuitItemGroup.RESISTOR, 2),
            new CircuitItemVariant(ItemList.Circuit_Parts_DiodeSMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_DiodeSMD, CircuitItemGroup.DIODE, 0),
            new CircuitItemVariant(ItemList.Circuit_Parts_DiodeASMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_DiodeASMD, CircuitItemGroup.DIODE, 1),
            new CircuitItemVariant(ItemList.Circuit_Parts_DiodeXSMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_DiodeXSMD, CircuitItemGroup.DIODE, 2),
            new CircuitItemVariant(ItemList.Circuit_Parts_TransistorSMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_TransistorSMD, CircuitItemGroup.TRANSISTOR, 0),
            new CircuitItemVariant(ItemList.Circuit_Parts_TransistorASMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_TransistorASMD, CircuitItemGroup.TRANSISTOR, 1),
            new CircuitItemVariant(ItemList.Circuit_Parts_TransistorXSMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_TransistorXSMD, CircuitItemGroup.TRANSISTOR, 2),
            new CircuitItemVariant(ItemList.Circuit_Parts_CapacitorSMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_CapacitorSMD, CircuitItemGroup.CAPACITOR, 0),
            new CircuitItemVariant(ItemList.Circuit_Parts_CapacitorASMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_CapacitorASMD, CircuitItemGroup.CAPACITOR, 1),
            new CircuitItemVariant(ItemList.Circuit_Parts_CapacitorXSMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_CapacitorXSMD, CircuitItemGroup.CAPACITOR, 2),
            new CircuitItemVariant(ItemList.Circuit_Parts_InductorSMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_InductorSMD, CircuitItemGroup.INDUCTOR, 0),
            new CircuitItemVariant(ItemList.Circuit_Parts_InductorASMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_InductorASMD, CircuitItemGroup.INDUCTOR, 1),
            new CircuitItemVariant(ItemList.Circuit_Parts_InductorXSMD.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_InductorXSMD, CircuitItemGroup.INDUCTOR, 2),

            // Advanced IC-like inputs are preserved as exact wrapped mappings, not ranked alternatives.
            new CircuitItemVariant(ItemList.Circuit_Chip_ILC.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_ILC, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_Ram.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_Ram, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_NAND.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_NAND, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_NOR.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_NOR, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_CPU.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_CPU, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_SoC.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_SoC, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_SoC2.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_SoC2, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_PIC.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_PIC, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_Simple_SoC.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_Simple_SoC, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_HPIC.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_HPIC, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_UHPIC.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_UHPIC, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_ULPIC.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_ULPIC, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_LPIC.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_LPIC, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_NPIC.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_NPIC, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_PPIC.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_PPIC, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_QPIC.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_QPIC, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_NanoCPU.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_NanoCPU, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_QuantumCPU.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_QuantumCPU, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_CrystalCPU.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_CrystalCPU, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_CrystalSoC.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_CrystalSoC, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_CrystalSoC2.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_CrystalSoC2, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_NeuroCPU.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_NeuroCPU, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_BioCPU.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_BioCPU, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_Stemcell.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_Stemcell, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_Biocell.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_Biocell, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Parts_Crystal_Chip_Wetware.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_Crystal_Chip_Wetware, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Parts_Chip_Bioware.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_Chip_Bioware, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Circuit_Chip_Optical.get(1), WrappedCircuitItem.Wrapped_Circuit_Chip_Optical, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Optically_Perfected_CPU.get(1), WrappedCircuitItem.Wrapped_Optically_Perfected_CPU, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Optical_Cpu_Containment_Housing.get(1), WrappedCircuitItem.Wrapped_Optical_Cpu_Containment_Housing, CircuitItemGroup.NONE),
            new CircuitItemVariant(ItemList.Optically_Compatible_Memory.get(1), WrappedCircuitItem.Wrapped_Optically_Compatible_Memory, CircuitItemGroup.NONE),

            // Plain components map to their SMD wrapped forms so old recipes share the same path.
            new CircuitItemVariant(ItemList.Circuit_Parts_Resistor.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_ResistorSMD, CircuitItemGroup.RESISTOR, 0),
            new CircuitItemVariant(ItemList.Circuit_Parts_Coil.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_InductorSMD, CircuitItemGroup.INDUCTOR, 0),
            new CircuitItemVariant(ItemList.Circuit_Parts_Diode.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_DiodeSMD, CircuitItemGroup.DIODE, 0),
            new CircuitItemVariant(ItemList.Circuit_Parts_Transistor.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_TransistorSMD, CircuitItemGroup.TRANSISTOR, 0),
            new CircuitItemVariant(ItemList.Circuit_Parts_Capacitor.get(1), WrappedCircuitItem.Wrapped_Circuit_Parts_CapacitorSMD, CircuitItemGroup.CAPACITOR, 0),

            // These custom circuits reuse existing wrapped circuit tiers for NAC/Piko/Quantum compatibility.
            new CircuitItemVariant(ItemList.Circuit_OpticalMainframe.get(1), WrappedCircuitItem.Wrapped_Circuit_UIV, CircuitItemGroup.NONE, 11),
            new CircuitItemVariant(NHItemList.PikoCircuit.get(1), WrappedCircuitItem.Wrapped_Circuit_UMV, CircuitItemGroup.NONE, 12),
            new CircuitItemVariant(NHItemList.QuantumCircuit.get(1), WrappedCircuitItem.Wrapped_Circuit_UXV, CircuitItemGroup.NONE, 13) };

        // spotless:on

        int count = 0;
        for (WrappedCircuitItem item : WrappedCircuitItem.values()) {
            if (count < 15) {
                item.set(getModItem("GoodGenerator", "circuitWrap", 1, count));
            } else {
                item.set(getModItem("bartworks", "gt.bwMetaGeneratedItem0", 1, 32778 - count));
            }
            count++;
        }

        circuitItemsToWrapped.clear();
        for (CircuitItemVariant item : circuitItems) {
            if (item.original != null && item.wrapped.get(1) != null) {
                circuitItemsToWrapped.put(item.original, item.wrapped.get(1));
            }
        }

        // init GTPP Material Map
        for (Map.Entry<String, Map<String, ItemStack>> outerEntry : mComponentMap.entrySet()) {
            String materialName = outerEntry.getKey();
            Map<String, ItemStack> innerMap = outerEntry.getValue();

            Material material = null;

            for (Material aMaterial : Material.mMaterialMap) {
                if (aMaterial.getUnlocalizedName()
                    .equals(materialName)) {
                    material = aMaterial;
                }
            }

            if (material == null) continue;
            // if (!generateMaterialList.contains(material)) continue;

            for (Map.Entry<String, ItemStack> innerEntry : innerMap.entrySet()) {
                String orePrefixName = innerEntry.getKey();
                ItemStack aStack = innerEntry.getValue();

                OrePrefixes OreDict = OrePrefixes.getPrefix(orePrefixName);

                int amount = (int) (OreDict.getMaterialAmount() * INGOTS * aStack.stackSize / GTValues.M);
                FluidStack fluidStack = material.getFluidStack(amount);

                if (fluidStack != null) {
                    specialMaterialCantAutoModify.put(aStack, fluidStack);
                }
            }
        }

        specialMaterialCantAutoModify
            .put(ItemList.Circuit_Parts_Reinforced_Glass_Tube.get(1), Materials.ReinforcedGlass.getMolten(288));

        superConductorMaterialList.add(Materials.SuperconductorMV);
        superConductorMaterialList.add(Materials.SuperconductorHV);
        superConductorMaterialList.add(Materials.SuperconductorEV);
        superConductorMaterialList.add(Materials.SuperconductorIV);
        superConductorMaterialList.add(Materials.SuperconductorLuV);
        superConductorMaterialList.add(Materials.SuperconductorZPM);
        superConductorMaterialList.add(Materials.SuperconductorUV);
        superConductorMaterialList.add(Materials.SuperconductorUHV);
        superConductorMaterialList.add(Materials.SuperconductorUEV);
        superConductorMaterialList.add(Materials.SuperconductorUIV);
        superConductorMaterialList.add(Materials.SuperconductorUMV);

        targetModifyOreDict.add(OrePrefixes.wireGt01);
        targetModifyOreDict.add(OrePrefixes.wireGt02);
        targetModifyOreDict.add(OrePrefixes.wireGt04);
        targetModifyOreDict.add(OrePrefixes.wireGt08);
        targetModifyOreDict.add(OrePrefixes.wireGt12);
        targetModifyOreDict.add(OrePrefixes.wireGt16);
        targetModifyOreDict.add(OrePrefixes.frameGt);
        targetModifyOreDict.add(OrePrefixes.dust);
        targetModifyOreDict.add(OrePrefixes.nugget);
        targetModifyOreDict.add(OrePrefixes.ingot);
        targetModifyOreDict.add(OrePrefixes.plate);
        targetModifyOreDict.add(OrePrefixes.plateDouble);
        targetModifyOreDict.add(OrePrefixes.plateDense);
        targetModifyOreDict.add(OrePrefixes.rod);
        targetModifyOreDict.add(OrePrefixes.round);
        targetModifyOreDict.add(OrePrefixes.bolt);
        targetModifyOreDict.add(OrePrefixes.screw);
        targetModifyOreDict.add(OrePrefixes.ring);
        targetModifyOreDict.add(OrePrefixes.foil);
        targetModifyOreDict.add(OrePrefixes.itemCasing);
        targetModifyOreDict.add(OrePrefixes.wireFine);
        targetModifyOreDict.add(OrePrefixes.gearGt);
        targetModifyOreDict.add(OrePrefixes.gearGtSmall);
        targetModifyOreDict.add(OrePrefixes.rotor);
        targetModifyOreDict.add(OrePrefixes.stickLong);
        targetModifyOreDict.add(OrePrefixes.spring);
        targetModifyOreDict.add(OrePrefixes.springSmall);
        targetModifyOreDict.add(OrePrefixes.plateSuperdense);
        targetModifyOreDict.add(OrePrefixes.pipeTiny);
        targetModifyOreDict.add(OrePrefixes.pipeSmall);
        targetModifyOreDict.add(OrePrefixes.pipeMedium);
        targetModifyOreDict.add(OrePrefixes.pipeLarge);
        targetModifyOreDict.add(OrePrefixes.pipeHuge);
        targetModifyOreDict.add(OrePrefixes.pipeQuadruple);
        targetModifyOreDict.add(OrePrefixes.pipeNonuple);

        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ZPM, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UHV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UEV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UIV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UMV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UXV, 1));
        addGTCircuitOreDictNames(GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MAX, 1));
    }

    // spotless:off
    public static void loadCustomRecipes() {
        // Do Not Add Messy Recipe to MT

        final ItemStack ringBlock = getModItem("SGCraft", "stargateRing", 1, 0);
        final ItemStack chevronBlock = getModItem("SGCraft", "stargateRing", 1, 1);
        final ItemStack irisUpgrade = getModItem("SGCraft", "sgIrisUpgrade", 1, 0);

        // region Proof Of Heroes
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.SpaceWarper.get(64),
                getModItem("eternalsingularity", "eternal_singularity", 64),
                getModItem("eternalsingularity", "combined_singularity", 64, 15),
                ItemList.Timepiece.get(64),
                ItemList.GigaChad.get(64),
                tectech.thing.CustomItemList.SpacetimeCompressionFieldGeneratorTier8.get(64),
                tectech.thing.CustomItemList.TimeAccelerationFieldGeneratorTier8.get(64),
                tectech.thing.CustomItemList.StabilisationFieldGeneratorTier8.get(64),
                new Object[]{OrePrefixes.circuit.get(Materials.UXV), 64},
//                getModItem("dreamcraft", "item.QuantumCircuit", 64),
//                getModItem(GTPlusPlus.ID, "particleBase", 64, 15),
//                getModItem(GTPlusPlus.ID, "particleBase", 64, 16),
//                getModItem(GTPlusPlus.ID, "particleBase", 64, 20),
//                getModItem(GTPlusPlus.ID, "particleBase", 64, 21),
//                getModItem(GTPlusPlus.ID, "particleBase", 64, 17),
                ItemList.ZPM6.get(64),
                GTCMItemList.IndistinctTentacle.get(64)
            )
            .fluidInputs(
                Materials.Time.getMolten(1000 * 114514),
                Materials.Space.getMolten(1000 * 114514),
                Materials.MHDCSM.getMolten(1000 * 114514), // MagnetohydrodynamicallyConstrainedStarMatter
                GGMaterial.shirabon.getMolten(1000 * 114514),
                Materials.Universium.getMolten(1000 * 114514),
                Materials.Eternity.getMolten(1000 * 114514),
                Materials.PrimordialMatter.getFluid(1000 * 114514)
            )
            .itemOutputs(GTCMItemList.ProofOfHeroes.get(1))
            .specialValue(13500)
            .eut(RECIPE_MAX)
            .duration(20 * 1919810)
            .addTo(MT);

        // endregion

        // Optical SoC Shield
        TST_RecipeBuilder.builder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                GregtechItemList.InfinityInfusedShieldingCore.get(0),
                ItemList.Optical_Cpu_Containment_Housing.get(1),
                Materials.Glowstone.getNanite(4))
            .fluidInputs(Materials.Space.getMolten(36), Materials.Time.getMolten(36))
            .itemOutputs(GTCMItemList.ParticleTrapTimeSpaceShield.get(1))
            .eut(RECIPE_UMV)
            .duration(20 * 64)
            .addTo(MT);

        TST_RecipeBuilder.builder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                GregtechItemList.SpaceTimeBendingCore.get(0),
                ItemList.Optical_Cpu_Containment_Housing.get(2),
                Materials.Glowstone.getNanite(4))
            .fluidInputs(
                Materials.Space.getMolten(144),
                Materials.Time.getMolten(144),
                Materials.SpaceTime.getMolten(288))
            .itemOutputs(GTCMItemList.ParticleTrapTimeSpaceShield.get(16))
            .fluidOutputs(Materials.DTR.getFluid(2500))
            .eut(RECIPE_UMV)
            .duration(20 * 64)
            .addTo(MT);

        // region Endgame Challenge content

        // Liquid Stargate
        GTValues.RA.stdBuilder()
            .itemInputs(
                copyAmount(1, ringBlock),
                copyAmount(1, chevronBlock),
                copyAmount(1, chevronBlock),
                copyAmount(1, ringBlock),

                copyAmount(1, chevronBlock),
                copyAmount(1, irisUpgrade),
                copyAmount(1, irisUpgrade),
                copyAmount(1, chevronBlock),

                copyAmount(1, ringBlock),
                copyAmount(1, irisUpgrade),
                copyAmount(1, irisUpgrade),
                copyAmount(1, ringBlock),

                copyAmount(1, chevronBlock),
                copyAmount(1, ringBlock),
                copyAmount(1, ringBlock),
                copyAmount(1, chevronBlock)
            )
            .fluidInputs(
                MaterialPool.StabiliseVoidMatter.getFluidOrGas(1_000)
            )
            .fluidOutputs(MaterialPool.LiquidStargate.getFluidOrGas(1000))
            .specialValue(13500)
            .eut(RECIPE_MAX)
            .duration(20 * 99_999_999)
            .addTo(MT);

        // StabiliseVoidMatter
        TST_RecipeBuilder
            .builder()
            .itemInputs(
                setStackSize(Materials.CosmicNeutronium.getDust(1), 10_000_000),
                setStackSize(Materials.Bedrockium.getDust(1), 10_000_000),
                setStackSize(Materials.Carbon.getDust(1), 10_000_000),
                setStackSize(Materials.Oilsands.getDust(1), 10_000_000),
                setStackSize(Materials.NiobiumTitanium.getDust(1), 10_000_000),
                setStackSize(MaterialsElements.STANDALONE.BLACK_METAL.getDust(1), 10_000_000),
                setStackSize(Materials.Naquadria.getDust(1), 10_000_000),
                setStackSize(Materials.Obsidian.getDust(1), 10_000_000),
                setStackSize(Materials.Coal.getDust(1), 10_000_000),
                setStackSize(Materials.NaquadahAlloy.getDust(1), 10_000_000),
                setStackSize(Materials.Tungsten.getDust(1), 10_000_000),
                setStackSize(Materials.TranscendentMetal.getDust(1), 10_000_000),
                setStackSize(Materials.Perlite.getDust(1), 10_000_000),
                setStackSize(Materials.AshDark.getDust(1), 10_000_000),
                setStackSize(Materials.GraniticMineralSand.getDust(1), 10_000_000),
                setStackSize(MaterialsElements.STANDALONE.CELESTIAL_TUNGSTEN.getDust(1), 10_000_000)
            )
            .fluidInputs(
                Materials.Polycaprolactam.getMolten(10_000_000),
                Materials.NickelZincFerrite.getMolten(10_000_000),
                Materials.DarkSteel.getMolten(10_000_000),
                Materials.Polybenzimidazole.getMolten(10_000_000),
                GGMaterial.tairitsu.getMolten(10_000_000),
                Materials.Tungsten.getMolten(10_000_000),
                GGMaterial.marM200.getMolten(10_000_000),
                Materials.Vanadium.getMolten(10_000_000),
                MaterialsElements.STANDALONE.BLACK_METAL.getFluidStack(10_000_000),
                Materials.ShadowIron.getMolten(10_000_000),
                Materials.NaquadahAlloy.getMolten(10_000_000),
                Materials.ShadowSteel.getMolten(10_000_000),
                Materials.Cadmium.getMolten(10_000_000),
                Materials.Desh.getMolten(10_000_000),
                Materials.BlackPlutonium.getMolten(10_000_000),
                Materials.BlackSteel.getMolten(10_000_000),
                MaterialsElements.STANDALONE.CELESTIAL_TUNGSTEN.getFluidStack(10_000_000)
            )
            .fluidOutputs(MaterialPool.StabiliseVoidMatter.getFluidOrGas(1))
            .eut(RECIPE_MAX)
            .duration(20 * 99_999_999)
            .addTo(MT);

        // ProofOfGods
        // TODO -- Temporarily, be revised in the next version
        TST_RecipeBuilder
            .builder()
            .itemInputs(
                GTCMItemList.UxvFlask.get(1),
                GTCMItemList.ProofOfHeroes.get(64),
                setStackSize(Materials.Silver.getNanite(1), 1_000),
                setStackSize(Materials.Gold.getNanite(1), 1_000),
                setStackSize(Materials.Neutronium.getNanite(1), 1_000),
                setStackSize(Materials.Universium.getNanite(1), 1_000),
                setStackSize(Materials.Eternity.getNanite(1), 1_000),
                setStackSize(Materials.TranscendentMetal.getNanite(1), 1_000),
                setStackSize(Materials.Glowstone.getNanite(1), 1_000),
                setStackSize(Materials.WhiteDwarfMatter.getNanite(1), 1_000),
                setStackSize(Materials.BlackDwarfMatter.getNanite(1), 1_000)
            )
            .fluidInputs(
                MaterialPool.LiquidStargate.getFluidOrGas(50_000),
                MaterialPool.StabiliseVoidMatter.getFluidOrGas(1_000)
            )
            .itemOutputs(
                GTCMItemList.ProofOfGods.get(1)
            )
            .eut(RECIPE_MAX)
            .duration(20 * 99_999_999)
            .addTo(MT);

        // FLASK
        loadFlaskRecipe();

        // endregion
        if (Config.activateMegaSpaceStation) {
            loadMaxRecipe();
        }
    }

    public static void loadMaxRecipe() {
        ItemStack[] inStack = new ItemStack[]{
            ItemList.Circuit_Parts_ResistorXSMD.get(16),
            ItemList.Circuit_Parts_DiodeXSMD.get(16),
            ItemList.Circuit_Parts_TransistorXSMD.get(16),
            ItemList.Circuit_Parts_CapacitorXSMD.get(16),
            ItemList.Circuit_Parts_InductorXSMD.get(16)
        };
        ItemStack[] outStack = new ItemStack[]{
            GTCMItemList.HighDimensionalResistor.get(64),
            GTCMItemList.HighDimensionalDiode.get(64),
            GTCMItemList.HighDimensionalTransistor.get(64),
            GTCMItemList.HighDimensionalCapacitor.get(64),
            GTCMItemList.HighDimensionalInterface.get(64),
        };
        for (int i = 0; i < 5; i++) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTUtility.getIntegratedCircuit(12),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.TranscendentMetal, 4),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Universium, 2),
                    inStack[i],
                    GTCMItemList.HighDimensionalExtend.get(1)
                )
                .fluidInputs(
                    Materials.Time.getMolten(144)
                )
                .itemOutputs(
                    outStack[i]
                )
                .eut(RECIPE_UEV)
                .duration(20)
                .addTo(MT);
        }


    }

    public static void loadFlaskRecipe() {
        final int ITEMS_FLASK_COUNT = 100_000;
        // TODO -- Temporarily, be revised in the next version
        // LV FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                setStackSize(ItemList.Electric_Motor_LV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_LV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_LV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_LV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_LV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_LV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_LV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_LV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_Microprocessor.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedstoneAlloy, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Iron.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.LvFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(32 * 20)
            .addTo(MT);

        // MV FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.LvFlask.get(1),
                setStackSize(ItemList.Electric_Motor_MV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_MV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_MV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_MV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_MV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_MV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_MV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_MV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_Processor.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorMV, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Copper.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.MvFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(128 * 20)
            .addTo(MT);

        // HV FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.MvFlask.get(1),
                setStackSize(ItemList.Electric_Motor_HV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_HV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_HV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_HV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_HV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_HV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_HV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_HV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_Nanoprocessor.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorHV, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Nickel.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.HvFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(512 * 20)
            .addTo(MT);

        // EV FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.HvFlask.get(1),
                setStackSize(ItemList.Electric_Motor_EV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_EV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_EV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_EV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_EV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_EV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_EV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_EV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_Quantumprocessor.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorEV, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Titanium.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.EvFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(2_048 * 20)
            .addTo(MT);

        // IV FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.EvFlask.get(1),
                setStackSize(ItemList.Electric_Motor_IV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_IV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_IV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_IV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_IV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_IV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_IV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_IV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_Crystalprocessor.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorIV, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Tungsten.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.IvFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(8_192 * 20)
            .addTo(MT);

        // LUV FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.IvFlask.get(1),
                setStackSize(ItemList.Electric_Motor_LuV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_LuV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_LuV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_LuV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_LuV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_LuV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_LuV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_LuV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_Neuroprocessor.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Osmium.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.LuvFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(32_768 * 20)
            .addTo(MT);

        // ZPM FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.LuvFlask.get(1),
                setStackSize(ItemList.Electric_Motor_ZPM.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_ZPM.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_ZPM.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_ZPM.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_ZPM.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_ZPM.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_ZPM.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_ZPM.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_Bioprocessor.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorZPM, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Naquadah.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.ZpmFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(131_072 * 20)
            .addTo(MT);

        // UV FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.ZpmFlask.get(1),
                setStackSize(ItemList.Electric_Motor_UV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_UV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_UV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_UV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_UV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_UV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_UV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_UV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_OpticalProcessor.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUV, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Neutronium.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.UvFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(524_288 * 20)
            .addTo(MT);

        // UHV FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.UvFlask.get(1),
                setStackSize(ItemList.Electric_Motor_UHV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_UHV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_UHV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_UHV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_UHV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_UHV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_UHV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_UHV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_OpticalAssembly.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUHV, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Samarium.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.UhvFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(2_097_152 * 20)
            .addTo(MT);

        // UEV FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.UhvFlask.get(1),
                setStackSize(ItemList.Electric_Motor_UEV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_UEV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_UEV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_UEV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_UEV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_UEV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_UEV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_UEV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_CosmicProcessor.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUEV, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Americium.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.UevFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(8_388_608 * 20)
            .addTo(MT);

        // UIV FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.UevFlask.get(1),
                setStackSize(ItemList.Electric_Motor_UIV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_UIV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_UIV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_UIV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_UIV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_UIV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_UIV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_UIV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_CosmicAssembly.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Thorium.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.UivFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(33_554_432 * 20)
            .addTo(MT);

        // UMV FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.UivFlask.get(1),
                setStackSize(ItemList.Electric_Motor_UMV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_UMV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_UMV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_UMV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_UMV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_UMV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_UMV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_UMV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_CosmicComputer.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUMV, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Plutonium241.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.UmvFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(100_000_000 * 20)
            .addTo(MT);

        // UXV FLASK
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTCMItemList.UmvFlask.get(1),
                setStackSize(ItemList.Electric_Motor_UXV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Piston_UXV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Electric_Pump_UXV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Field_Generator_UXV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Conveyor_Module_UXV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Robot_Arm_UXV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Emitter_UXV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Sensor_UXV.get(1), ITEMS_FLASK_COUNT),
                setStackSize(ItemList.Circuit_CosmicMainframe.get(1), ITEMS_FLASK_COUNT),
                setStackSize(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Infinity, 1), ITEMS_FLASK_COUNT)
            )
            .fluidInputs(
                Materials.Radon.getPlasma(1_000_000_000)
            )
            .itemOutputs(GTCMItemList.UxvFlask.get(1))
            .eut(RECIPE_MAX)
            .duration(100_000_000 * 20)
            .addTo(MT);

    }

    public enum WrappedCircuitItem {
        Wrapped_Circuit_ULV,
        Wrapped_Circuit_LV,
        Wrapped_Circuit_MV,
        Wrapped_Circuit_HV,
        Wrapped_Circuit_EV,
        Wrapped_Circuit_IV,
        Wrapped_Circuit_LuV,
        Wrapped_Circuit_ZPM,
        Wrapped_Circuit_UV,
        Wrapped_Circuit_UHV,
        Wrapped_Circuit_UEV,
        Wrapped_Circuit_UIV,
        Wrapped_Circuit_UMV,
        Wrapped_Circuit_UXV,
        Wrapped_Circuit_MAX,

        Wrapped_Circuit_Parts_Crystal_Chip_Elite,
        Wrapped_Circuit_Parts_Crystal_Chip_Master,
        Wrapped_Circuit_Board_Coated,
        Wrapped_Circuit_Board_Coated_Basic,
        Wrapped_Circuit_Board_Phenolic,
        Wrapped_Circuit_Board_Phenolic_Good,
        Wrapped_Circuit_Board_Epoxy,
        Wrapped_Circuit_Board_Epoxy_Advanced,
        Wrapped_Circuit_Board_Fiberglass,
        Wrapped_Circuit_Board_Fiberglass_Advanced,
        Wrapped_Circuit_Board_Multifiberglass_Elite,
        Wrapped_Circuit_Board_Multifiberglass,
        Wrapped_Circuit_Board_Wetware,
        Wrapped_Circuit_Board_Wetware_Extreme,
        Wrapped_Circuit_Board_Plastic,
        Wrapped_Circuit_Board_Plastic_Advanced,
        Wrapped_Circuit_Board_Bio,
        Wrapped_Circuit_Board_Bio_Ultra,
        Wrapped_Circuit_Parts_ResistorSMD,
        Wrapped_Circuit_Parts_InductorSMD,
        Wrapped_Circuit_Parts_DiodeSMD,
        Wrapped_Circuit_Parts_TransistorSMD,
        Wrapped_Circuit_Parts_CapacitorSMD,
        Wrapped_Circuit_Parts_ResistorASMD,
        Wrapped_Circuit_Parts_DiodeASMD,
        Wrapped_Circuit_Parts_TransistorASMD,
        Wrapped_Circuit_Parts_CapacitorASMD,
        Wrapped_Circuit_Chip_ILC,
        Wrapped_Circuit_Chip_Ram,
        Wrapped_Circuit_Chip_NAND,
        Wrapped_Circuit_Chip_NOR,
        Wrapped_Circuit_Chip_CPU,
        Wrapped_Circuit_Chip_SoC,
        Wrapped_Circuit_Chip_SoC2,
        Wrapped_Circuit_Chip_PIC,
        Wrapped_Circuit_Chip_Simple_SoC,
        Wrapped_Circuit_Chip_HPIC,
        Wrapped_Circuit_Chip_UHPIC,
        Wrapped_Circuit_Chip_ULPIC,
        Wrapped_Circuit_Chip_LPIC,
        Wrapped_Circuit_Chip_NPIC,
        Wrapped_Circuit_Chip_PPIC,
        Wrapped_Circuit_Chip_QPIC,
        Wrapped_Circuit_Chip_NanoCPU,
        Wrapped_Circuit_Chip_QuantumCPU,
        Wrapped_Circuit_Chip_CrystalCPU,
        Wrapped_Circuit_Chip_CrystalSoC,
        Wrapped_Circuit_Chip_CrystalSoC2,
        Wrapped_Circuit_Chip_NeuroCPU,
        Wrapped_Circuit_Chip_BioCPU,
        Wrapped_Circuit_Chip_Stemcell,
        Wrapped_Circuit_Chip_Biocell,
        Wrapped_Circuit_Parts_ResistorXSMD,
        Wrapped_Circuit_Parts_DiodeXSMD,
        Wrapped_Circuit_Parts_TransistorXSMD,
        Wrapped_Circuit_Parts_CapacitorXSMD,
        Wrapped_Circuit_Parts_InductorASMD,
        Wrapped_Circuit_Parts_InductorXSMD,
        Wrapped_Circuit_Chip_Optical,
        Wrapped_Circuit_Board_Optical,
        Wrapped_Optically_Perfected_CPU,
        Wrapped_Optical_Cpu_Containment_Housing,
        Wrapped_Optically_Compatible_Memory,
        Wrapped_Circuit_Parts_Crystal_Chip_Wetware,
        Wrapped_Circuit_Parts_Chip_Bioware;
        private ItemStack itemStack;

        public ItemStack get(int amount) {
            return copyAmount(amount, this.itemStack);
        }

        public void set(ItemStack itemStack) {
            this.itemStack = itemStack;
        }
    }
    // spotless:on
}
