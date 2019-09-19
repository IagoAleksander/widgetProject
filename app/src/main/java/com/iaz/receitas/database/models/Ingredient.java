package com.iaz.receitas.database.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient")
public class Ingredient implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String quantity;

    private String measure;

    private String ingredient;

    private long recipeId;

    public Ingredient(String quantity, String measure, String ingredient, long recipeId) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
        this.recipeId = recipeId;
    }

    public String getQuantity ()
    {
        return quantity;
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

    public void setQuantity (String quantity)
    {
        this.quantity = quantity;
    }

    public String getMeasure ()
    {
        return measure;
    }

    public void setMeasure (String measure)
    {
        this.measure = measure;
    }

    public String getIngredient ()
    {
        return ingredient;
    }

    public void setIngredient (String ingredient)
    {
        this.ingredient = ingredient;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [quantity = "+quantity+", measure = "+measure+", ingredient = "+ingredient+"]";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
        dest.writeLong(this.recipeId);
    }

    protected Ingredient(Parcel in) {
        this.id = in.readInt();
        this.quantity = in.readString();
        this.measure = in.readString();
        this.ingredient = in.readString();
        this.recipeId = in.readLong();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}