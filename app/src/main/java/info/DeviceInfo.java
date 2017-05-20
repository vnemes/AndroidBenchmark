package info;

import android.os.Build;

/**
 * Created by Vendetta on 07-Mar-17.
 */

public class DeviceInfo {
    private static String manufacturer = capitalize(Build.MANUFACTURER);
    private static String model = Build.MODEL.startsWith(manufacturer) ? Build.MODEL.replace(manufacturer, "") : Build.MODEL;

    public static String getManufacturer() {
        return manufacturer;
    }

    public static String getModel() {
        return model;
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
