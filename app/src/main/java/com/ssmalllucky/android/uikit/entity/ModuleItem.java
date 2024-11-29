package com.ssmalllucky.android.uikit.entity;

/**
 * @ClassName ModuleItem
 * @Author shuaijialin
 * @Date 2024/10/15
 * @Description
 */
public class ModuleItem {

    private String title;
    private String icon;
    private String colorString;

    public ModuleItem(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public ModuleItem(String title, String icon, String colorString) {
        this.title = title;
        this.icon = icon;
        this.colorString = colorString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColorString() {
        return colorString;
    }

    public void setColorString(String colorString) {
        this.colorString = colorString;
    }
}
