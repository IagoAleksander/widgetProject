package com.iaz.receitas.database.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "step")
public class Step implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String description;

    private String shortDescription;

    private long recipeId;

    public Step(String description, String shortDescription, long recipeId) {
        this.description = description;
        this.shortDescription = shortDescription;
        this.recipeId = recipeId;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getShortDescription ()
    {
        return shortDescription;
    }

    public void setShortDescription (String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.description);
        dest.writeString(this.shortDescription);
        dest.writeLong(this.recipeId);
    }

    protected Step(Parcel in) {
        this.id = in.readInt();
        this.description = in.readString();
        this.shortDescription = in.readString();
        this.recipeId = in.readLong();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
