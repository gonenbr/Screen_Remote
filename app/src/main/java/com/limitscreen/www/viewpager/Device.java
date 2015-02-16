package com.limitscreen.www.viewpager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gonen on 12/02/15.
 */
public class Device  implements Parcelable{
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte)(Is_on ? 1:0));
        dest.writeByte((byte)(Is_available ? 1:0));
        dest.writeByte((byte)(Is_selected_for_current_operation ? 1:0));
        dest.writeString(Device_name);

    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel source) {
            return new Device(source);

            //return null;
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    public Device()
    {

    }

    public Device(Device d)
    {
        Is_on=d.Is_on;
        Is_available=d.Is_available;
        Is_selected_for_current_operation= d.Is_selected_for_current_operation;
        Device_name = d.Device_name;
    }

    public Device(Parcel source)
    {
        Is_on= source.readByte() !=0;
        Is_available = source.readByte() !=0;
        Is_selected_for_current_operation = source.readByte() != 0;
        Device_name = source.readString();
    }
    public boolean Is_on=false;
    public boolean Is_available=false;
    public boolean Is_selected_for_current_operation=false;
    public String Device_name="N/A";



}
