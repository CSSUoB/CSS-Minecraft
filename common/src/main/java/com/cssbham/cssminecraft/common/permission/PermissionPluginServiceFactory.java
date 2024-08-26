package com.cssbham.cssminecraft.common.permission;

import java.lang.reflect.InvocationTargetException;

/**
 * Factory methods for getting a {@link PermissionPluginService}
 */
public class PermissionPluginServiceFactory {

    public static PermissionPluginService any() {
         String[] plugins = new String[]{ "LuckPerms" };
        for (String plugin : plugins) {
            try {
                return forPlugin(plugin);
            } catch (RuntimeException ignored) { }
        }
        return new StubPermissionPluginService();
    }

    public static PermissionPluginService forPlugin(String plugin) {
        try {
            switch (plugin) {
                case "LuckPerms":
                    return checkClassAndBuild("net.luckperms.api.LuckPermsProvider", LuckPermsPermissionPluginService.class);
                default:
                    return new StubPermissionPluginService();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(String.format("Permission plugin %s is not available", plugin));
        }
    }

    private static PermissionPluginService checkClassAndBuild(String className, Class<? extends PermissionPluginService> clazz)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class.forName(className);
        return clazz.getDeclaredConstructor().newInstance();
    }

}
