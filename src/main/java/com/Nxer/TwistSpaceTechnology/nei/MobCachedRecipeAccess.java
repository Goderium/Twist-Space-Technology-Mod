package com.Nxer.TwistSpaceTechnology.nei;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MobCachedRecipeAccess {

    // 获取 `cachedRecipes` 列表
    public static List<Object> getCachedRecipes(Class<?> handlerClass) {
        try {
            // 获取 `cachedRecipes` 字段
            Field cachedRecipesField = handlerClass.getDeclaredField("cachedRecipes");
            cachedRecipesField.setAccessible(true); // 取消访问限制

            // 获取字段值
            @SuppressWarnings("unchecked")
            List<Object> cachedRecipes = (List<Object>) cachedRecipesField.get(null);
            return new ArrayList<>(cachedRecipes); // 返回副本，避免直接操作
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 获取 `MobCachedRecipe` 的 `mobname` 字段
    public static String getMobName(Object mobCachedRecipe) {
        try {
            Class<?> recipeClass = mobCachedRecipe.getClass();

            // 获取 `mobname` 字段
            Field mobnameField = recipeClass.getDeclaredField("mobname");
            mobnameField.setAccessible(true);

            // 返回 `mobname` 的值
            return (String) mobnameField.get(mobCachedRecipe);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
