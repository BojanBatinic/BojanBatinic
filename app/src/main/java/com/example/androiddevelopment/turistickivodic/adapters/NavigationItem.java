package com.example.androiddevelopment.turistickivodic.adapters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by androiddevelopment on 29.11.17..
 */

public class NavigationItem implements Parcelable {
    private String title;
    private String subtitle;
    private int icon;

    public NavigationItem(String title, String subtitle, int icon) {
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
    }

    protected NavigationItem(Parcel in) {
        title = in.readString();
        subtitle = in.readString();
        icon = in.readInt();
    }

    public static final Creator<NavigationItem> CREATOR = new Creator<NavigationItem>() {
        @Override
        public NavigationItem createFromParcel(Parcel in) {
            return new NavigationItem(in);
        }

        @Override
        public NavigationItem[] newArray(int size) {
            return new NavigationItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeInt(icon);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

