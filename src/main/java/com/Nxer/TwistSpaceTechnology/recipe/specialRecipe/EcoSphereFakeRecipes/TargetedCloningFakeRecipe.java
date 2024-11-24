package com.Nxer.TwistSpaceTechnology.recipe.specialRecipe.EcoSphereFakeRecipes;

import static com.Nxer.TwistSpaceTechnology.nei.MobCachedRecipeAccess.getMobCachedRecipeClass;
import static com.Nxer.TwistSpaceTechnology.nei.MobCachedRecipeAccess.getMobNameFromRecipe;
import static com.kuba6000.mobsinfo.api.MobRecipe.MobNameToRecipeMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;

import com.Nxer.TwistSpaceTechnology.recipe.IRecipePool;
import com.kuba6000.mobsinfo.api.MobDrop;
import com.kuba6000.mobsinfo.api.MobRecipe;
import com.kuba6000.mobsinfo.nei.MobHandler;

public class TargetedCloningFakeRecipe implements IRecipePool {

    private static final Map<Integer, List<DropEntry>> dropConfigurations = new HashMap<>();
    private static final Map<Integer, String> MobNameConfigurations = new HashMap<>();

    private static class DropEntry {

        final ItemStack item; // 掉落物
        final int chance; // 掉落概率（以 10000 为基准）
        final int minAmount; // 最小掉落数量
        final int maxAmount; // 最大掉落数量
        final boolean lootable; // 是否受 Looting 附魔影响
        final boolean playerOnly; // 是否仅限玩家击杀掉落

        DropEntry(ItemStack item, int chance, int minAmount, int maxAmount, boolean lootable, boolean playerOnly) {
            this.item = item;
            this.chance = chance;
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
            this.lootable = lootable;
            this.playerOnly = playerOnly;
        }
    }

    public static List<Object> getCachedRecipes(Class<?> clazz) {
        try {
            // 获取 private static final 字段 cachedRecipes
            Field cachedRecipesField = clazz.getDeclaredField("cachedRecipes");

            // 取消 Java 访问权限检查
            cachedRecipesField.setAccessible(true);

            // 获取字段值（因为是 static，传入 null）
            @SuppressWarnings("unchecked")
            List<Object> cachedRecipes = (List<Object>) cachedRecipesField.get(null);

            return new ArrayList<>(cachedRecipes); // 返回副本，避免直接修改
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return new ArrayList<>(); // 返回空列表，表示无法访问
        }
    }

    private static void preprocessDrops() {
        int pageIndex = 0;
        try {
            // 假设 MobRecipe 是主类
            Class<?> mobRecipeClass = MobHandler.class;

            // 获取 MobCachedRecipe 类
            Class<?> mobCachedRecipeClass = getMobCachedRecipeClass(mobRecipeClass);
            if (mobCachedRecipeClass == null) {
                System.out.println("MobCachedRecipe class not found.");
                return;
            }

            // 获取 cachedRecipes 列表
            List<Object> cachedRecipes = getCachedRecipes(mobRecipeClass);
            System.out.println("Total cached recipes: " + cachedRecipes.size());

            // 遍历每个 MobCachedRecipe 并提取 mobname
            for (Object recipe : cachedRecipes) {
                String mobname = getMobNameFromRecipe(mobCachedRecipeClass, recipe);
                MobNameConfigurations.put(++pageIndex, mobname);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!MobNameConfigurations.isEmpty()) {
            for (int i = 1; i <= MobNameConfigurations.size(); i++) {
                MobRecipe recipe = MobNameToRecipeMap.get(MobNameConfigurations.get(1));
                List<DropEntry> dropEntries = new ArrayList<>();
                for (MobDrop drop : recipe.mOutputs) {
                    int chance = drop.chance; // 直接使用 MobDrop 的概率
                    int minAmount = 1; // 默认最小数量
                    int maxAmount = drop.stack.stackSize; // 使用定义的堆叠数量

                    dropEntries
                        .add(new DropEntry(drop.stack, chance, minAmount, maxAmount, drop.lootable, drop.playerOnly));
                }

                // 保存到配置映射中
                dropConfigurations.put(++pageIndex, dropEntries);
            }
        }
    }

    public static ItemStack[] generateDrops(int page, Random rand, int lootingLevel, boolean includePlayerOnlyLoot) {
        List<DropEntry> dropEntries = dropConfigurations.get(page);
        if (dropEntries == null) return new ItemStack[0];

        List<ItemStack> drops = new ArrayList<>();
        for (DropEntry entry : dropEntries) {
            if (entry.playerOnly && !includePlayerOnlyLoot) continue; // 玩家限定掉落
            int adjustedChance = entry.chance;

            // 考虑 Looting 附魔
            if (entry.lootable && lootingLevel > 0) {
                adjustedChance += lootingLevel * 500; // 每级 Looting 增加掉落概率
            }

            if (rand.nextInt(10000) < adjustedChance) {
                ItemStack drop = entry.item.copy();
                drop.stackSize = entry.minAmount + rand.nextInt(entry.maxAmount - entry.minAmount + 1); // 随机数量
                drops.add(drop);
            }
        }

        return drops.toArray(new ItemStack[0]);
    }

    @Override
    public void loadRecipes() {
        preprocessDrops();
    }
}
