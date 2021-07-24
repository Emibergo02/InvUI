package de.studiocode.inventoryaccess.util;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static de.studiocode.inventoryaccess.util.ReflectionRegistry.*;

@SuppressWarnings({"unchecked", "unused"})
public class ReflectionUtils {
    
    protected static String getCB() {
        String path = Bukkit.getServer().getClass().getPackage().getName();
        String version = path.substring(path.lastIndexOf(".") + 1);
        return "org.bukkit.craftbukkit." + version + ".";
    }
    
    protected static int getVersionNumber() {
        String version = Bukkit.getVersion();
        version = version.substring(version.indexOf("MC: "), version.length() - 1).substring(4);
        return Integer.parseInt(version.split("\\.")[1]);
    }
    
    protected static String getInventoryAccessVersion() {
        String version = Bukkit.getVersion();
        version = version.substring(version.indexOf("MC: "), version.length() - 1).substring(4);
        
        if (version.equals("1.17.1")) {
            return "v1_17_R2"; // TODO: find a better solution
        } else {
            String path = Bukkit.getServer().getClass().getPackage().getName();
            return path.substring(path.lastIndexOf(".") + 1);
        }
    }
    
    public static <T> Class<T> getImplClass(String path) {
        try {
            return (Class<T>) Class.forName("de.studiocode.inventoryaccess." + INV_ACCESS_VERSION + "." + path);
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException("Your version (" + INV_ACCESS_VERSION + ") is not supported by InventoryAccess");
        }
    }
    
    public static <T> Class<T> getBukkitClass(String path) {
        return getClass(BUKKIT_PACKAGE_PATH + path);
    }
    
    public static <T> Class<T> getCBClass(String path) {
        return getClass(CRAFT_BUKKIT_PACKAGE_PATH + path);
    }
    
    public static <T> Class<T> getClass(String path) {
        try {
            return (Class<T>) Class.forName(path);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static Field getField(Class<?> clazz, boolean declared, String name) {
        try {
            Field field = declared ? clazz.getDeclaredField(name) : clazz.getField(name);
            if (declared) field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static <T> Constructor<T> getConstructor(Class<T> clazz, boolean declared, Class<?>... parameterTypes) {
        try {
            return declared ? clazz.getDeclaredConstructor(parameterTypes) : clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static <T> T constructEmpty(Class<?> clazz) {
        try {
            return (T) clazz.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static <T> T construct(Constructor<T> constructor, Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static Method getMethod(Class<?> clazz, boolean declared, String name, Class<?>... parameterTypes) {
        try {
            return declared ? clazz.getDeclaredMethod(name, parameterTypes) : clazz.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static <T> T invokeMethod(Method method, Object obj, Object... args) {
        try {
            return (T) method.invoke(obj, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static void setFieldValue(Field field, Object obj, Object value) {
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Field field, Object obj) {
        try {
            return (T) field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}