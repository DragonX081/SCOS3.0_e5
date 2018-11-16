package es.source.code.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodStockInfo  {
    private String foodName;
    private int foodStock;
    public FoodStockInfo(String foodName,int foodStock){
        this.foodName = foodName;
        this.foodStock = foodStock;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodStock(int foodStock) {
        this.foodStock = foodStock;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodStock() {
        return foodStock;
    }

}
