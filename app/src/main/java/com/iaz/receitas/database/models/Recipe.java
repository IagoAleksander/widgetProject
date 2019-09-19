package com.iaz.receitas.database.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class Recipe implements Parcelable
{
    private String servings;

    private String name;

    @PrimaryKey
    private long id;

    public Recipe(long id, String servings, String name) {
        this.id = id;
        this.servings = servings;
        this.name = name;
    }

    public String getServings ()
    {
        return servings;
    }

    public void setServings (String servings)
    {
        this.servings = servings;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public long getId ()
    {
        return id;
    }

    public void setId (long id)
    {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.servings);
        dest.writeString(this.name);
        dest.writeLong(this.id);
    }

    @Ignore
    protected Recipe(Parcel in) {
        this.servings = in.readString();
        this.name = in.readString();
        this.id = in.readLong();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}