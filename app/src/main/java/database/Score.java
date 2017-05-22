package database;

import info.DeviceInfo;

/**
 * Created by Vendetta on 22-May-17.
 */

public class Score {
    private static final String device = DeviceInfo.getFullDeviceName();
    private String benchName;
    private String uid;
    private String result;
    private String extra;

    public Score(){}

    public Score(String benchName, String uid, String result, String extra) {
        this.benchName = benchName;
        this.uid = uid;
        this.result = result;
        this.extra = extra;
    }

    public String getBenchName() {
        return benchName;
    }

    public String getUid() {
        return uid;
    }

    public String getResult() {
        return result;
    }

    public String getExtra() {
        return extra;
    }
    public static String getDevice() {
        return device;
    }
}
