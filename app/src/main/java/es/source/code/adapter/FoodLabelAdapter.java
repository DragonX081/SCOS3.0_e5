package es.source.code.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.source.code.activity2.R;
import es.source.code.model.FoodInfo;

public class FoodLabelAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater myLayoutInflater;
    private ArrayList<String> foodTypeLabels;

    public FoodLabelAdapter(ArrayList<String> foodTypeLabels, android.support.v4.app.Fragment fragment){
        this.foodTypeLabels = foodTypeLabels;
        myLayoutInflater = fragment.getActivity().getLayoutInflater();
    }
    @Override
    public int getCount() {
        return foodTypeLabels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder myHolder;
        String foodTypeLabel = foodTypeLabels.get(position);
            myHolder=new viewHolder();
            convertView = myLayoutInflater.inflate(R.layout.food_order_label_item,null);
            foodTypeLabel = foodTypeLabels.get(position);
            //bound element
            myHolder.tv_title = convertView.findViewById(R.id.food_type_item_title_tv);
            myHolder.iv_decor = convertView.findViewById(R.id.food_type_item_decor_iv);
            myHolder.iv_decor.setVisibility(View.INVISIBLE);
            //todo for click listener
            convertView.setTag(myHolder);
        //valuate
        myHolder.tv_title.setText(foodTypeLabel);
        myHolder.label = foodTypeLabel;
        //todo for food photo
        return convertView;
    }
    static class viewHolder{
        public TextView tv_title;
        public ImageView iv_decor;
        String label;
    }
}
