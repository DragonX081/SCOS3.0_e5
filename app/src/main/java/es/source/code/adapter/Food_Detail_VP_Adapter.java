package es.source.code.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import es.source.code.fragments.Food_Detail_Model;
import es.source.code.model.FoodInfo;

public class Food_Detail_VP_Adapter extends FragmentPagerAdapter {
    Food_Detail_Model fragment_model;
    ArrayList<FoodInfo> foodInfos;
    public Food_Detail_VP_Adapter(FragmentManager fm,ArrayList<FoodInfo> foodInfos) {
        super(fm);
        this.foodInfos = foodInfos;
    }

    @Override
    public Fragment getItem(int i)
    {
        fragment_model = new Food_Detail_Model();
        fragment_model.setMfoodInfo(foodInfos.get(i));
        return fragment_model;
    }

    @Override
    public int getCount() {
        return foodInfos.size();
    }
}
