package org.arslansah.android.model;

import org.arslansah.android.api.RequiredField;
import org.parceler.Parcel;

@Parcel
public class CustomLocalTimeline extends BaseModel{
    @RequiredField
    public String domain;

    @Override
    public String toString(){
        return "Hashtag{"+
                ", url='"+domain+'\''+
                '}';
    }
}
