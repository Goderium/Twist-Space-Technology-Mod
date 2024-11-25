package com.Nxer.TwistSpaceTechnology.recipe.specialRecipe.EcoSphereFakeRecipes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import com.Nxer.TwistSpaceTechnology.recipe.IRecipePool;
import com.kuba6000.mobsinfo.api.MobDrop;
import com.kuba6000.mobsinfo.api.MobRecipe;

import kubatech.config.Config;
import kubatech.loaders.MobHandlerLoader;

public class TargetedCloningFakeRecipe implements IRecipePool {

    private static final Map<Integer, List<DropEntry>> dropConfigurations = new HashMap<>();
    private static final Map<Integer, List<MobDrop>> dropConfigurations2 = new HashMap<>();
    private static final Map<Integer, String> MobNameConfigurations = new HashMap<>();
    private static final Map<Integer, MobRecipe> MobRecipeConfigurations = new HashMap<>();

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

        for (Map.Entry<String, MobHandlerLoader.MobEECRecipe> entry : MobHandlerLoader.recipeMap.entrySet()) {
            String mobType = entry.getKey();
            MobHandlerLoader.MobEECRecipe recipe = entry.getValue();

            List<DropEntry> dropEntries = new ArrayList<>();

            // 遍历常规掉落物
            for (MobDrop drop : recipe.mOutputs) {
                int chance = drop.chance; // 直接使用 MobDrop 的概率
                int minAmount = 1; // 默认最小数量
                int maxAmount = drop.stack.stackSize; // 使用定义的堆叠数量

                dropEntries
                    .add(new DropEntry(drop.stack, chance, minAmount, maxAmount, drop.lootable, drop.playerOnly));
            }
            dropConfigurations2.put(++pageIndex, recipe.mOutputs);
            // 保存到配置映射中
            dropConfigurations.put(++pageIndex, dropEntries);
        }
    }

    public static ItemStack[] generateDrops1(int page, Random rand, int lootingLevel, boolean includePlayerOnlyLoot) {
        List<DropEntry> dropEntries = dropConfigurations.get(page);
        if (dropEntries == null) return new ItemStack[0];

        List<ItemStack> drops = new ArrayList<>();
        for (DropEntry entry : dropEntries) {
            // if (entry.playerOnly && !includePlayerOnlyLoot) continue; // 玩家限定掉落
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

    public static ItemStack[] generateDrops(int page, Random rand, int lootingLevel, boolean includePlayerOnlyLoot) {
        // List<DropEntry> dropEntries = dropConfigurations.get(page);
        // if (dropEntries == null) return new ItemStack[0];
        // MobRecipe recipe = MobRecipeConfigurations.get(page);
        List<MobDrop> drops = dropConfigurations2.get(page);
        // for (DropEntry aDrop : dropEntries) {
        // if (aDrop.playerOnly && !includePlayerOnlyLoot) continue; // 玩家限定掉落
        // int adjustedChance = aDrop.chance;
        //
        // // 考虑 Looting 附魔
        // if (aDrop.lootable && lootingLevel > 0) {
        // adjustedChance += lootingLevel * 500; // 每级 Looting 增加掉落概率
        // }
        //
        // if (rand.nextInt(10000) < adjustedChance) {
        // ItemStack drop = aDrop.item.copy();
        // drop.stackSize = aDrop.minAmount + rand.nextInt(aDrop.maxAmount - aDrop.minAmount + 1); // 随机数量
        // drops.add(drop);
        // }
        // }
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (MobDrop o : drops) {
            if ((o.damages != null || o.enchantable != null)) continue;
            int chance = o.chance;

            double dChance = (double) chance / 100d;
            chance = (int) (dChance * 100d);
            if (chance == 0) continue;

            if (o.playerOnly) {
                chance = (int) ((double) chance * Config.MobHandler.playerOnlyDropsModifier);
                if (chance < 1) chance = 1;
            }
            int amount = o.stack.stackSize;
            if (o.lootable && lootingLevel > 0) {
                chance += lootingLevel * 5000;
                if (chance > 10000) {
                    int div = (int) Math.ceil(chance / 10000d);
                    amount *= div;
                    chance /= div;
                }
            }
            if (chance == 10000 || rand.nextInt(10000) < chance) {
                ItemStack s = o.stack.copy();
                s.stackSize = amount;
                if (o.enchantable != null) EnchantmentHelper.addRandomEnchantment(rand, s, o.enchantable);
                if (o.damages != null) {
                    int rChance = rand.nextInt(10);
                    int cChance = 0;
                    for (Map.Entry<Integer, Integer> damage : o.damages.entrySet()) {
                        cChance += damage.getValue();
                        if (rChance <= cChance) {
                            s.setItemDamage(damage.getKey());
                            break;
                        }
                    }
                }
                stacks.add(s);
            }
        }

        return stacks.toArray(new ItemStack[0]);
    }

    @Override
    public void loadRecipes() {
        preprocessDrops();
    }
}
