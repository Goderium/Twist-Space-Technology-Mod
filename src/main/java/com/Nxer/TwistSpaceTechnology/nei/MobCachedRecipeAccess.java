package com.Nxer.TwistSpaceTechnology.nei;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MobCachedRecipeAccess {

    // 获取 MobCachedRecipe 类
    public static Class<?> getMobCachedRecipeClass(Class<?> clazz) {
        for (Class<?> innerClass : clazz.getDeclaredClasses()) {
            if (innerClass.getSimpleName()
                .equals("MobCachedRecipe")) {
                return innerClass;
            }
        }
        return null;
    }

    // 获取 cachedRecipes 列表
    public static List<Object> getCachedRecipes(Class<?> clazz) {
        try {
            Field cachedRecipesField = clazz.getDeclaredField("cachedRecipes");
            cachedRecipesField.setAccessible(true); // 取消访问限制
            @SuppressWarnings("unchecked")
            List<Object> cachedRecipes = (List<Object>) cachedRecipesField.get(null);
            return new ArrayList<>(cachedRecipes); // 返回副本
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 获取 MobCachedRecipe 的 mobname 字段
    public static String getMobNameFromRecipe(Class<?> mobCachedRecipeClass, Object recipe) {
        try {
            Field mobnameField = mobCachedRecipeClass.getDeclaredField("mobname");
            mobnameField.setAccessible(true); // 取消访问限制
            return (String) mobnameField.get(recipe);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // public static void main(String[] args) {
    // try {
    // // 假设 MobRecipe 是主类
    // Class<?> mobRecipeClass = MobHandler.class;
    //
    // // 获取 MobCachedRecipe 类
    // Class<?> mobCachedRecipeClass = getMobCachedRecipeClass(mobRecipeClass);
    // if (mobCachedRecipeClass == null) {
    // System.out.println("MobCachedRecipe class not found.");
    // return;
    // }
    //
    // // 获取 cachedRecipes 列表
    // List<Object> cachedRecipes = getCachedRecipes(mobRecipeClass);
    // System.out.println("Total cached recipes: " + cachedRecipes.size());
    //
    // // 遍历每个 MobCachedRecipe 并提取 mobname
    // for (Object recipe : cachedRecipes) {
    // String mobname = getMobNameFromRecipe(mobCachedRecipeClass, recipe);
    // System.out.println("Mob Name: " + mobname);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
}
