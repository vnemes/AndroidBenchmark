package info;

import android.os.Build;

/**
 * Created by Vendetta on 07-Mar-17.
 */

public class DeviceInfo {
    private String manufacturer;
    private String model;

    public DeviceInfo(){
        manufacturer = Build.MANUFACTURER;
        model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            model.replace(manufacturer, "");
        }
        manufacturer = capitalize(manufacturer);
    }
    public String getManufacturer(){
        return this.manufacturer;
    }

    public String getModel(){
        return this.model;
    }

    private String capitalize(String s) {
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
