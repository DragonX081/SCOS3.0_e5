package es.source.code.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.source.code.activity2.R;
import es.source.code.activity2.SCOS_Global_Application;
import es.source.code.model.FoodInfo;
import es.source.code.model.User;

public class FoodOrderedItemAdapter extends BaseAdapter {
    private Context mContext;
    private User userInfo;
    private LayoutInflater myLayoutInflater;
    private SCOS_Global_Application myApp;
    private ArrayList<FoodInfo> foodOrderList;

    public FoodOrderedItemAdapter(android.support.v4.app.Fragment fragment){
        myApp = (SCOS_Global_Application)fragment.getActivity().getApplication();
        myLayoutInflater = fragment.getActivity().getLayoutInflater();
        foodOrderList = myApp.getFoodOrderList();
        userInfo = myApp.getUserInfo();
    }
    @Override
    public int getCount() {
        return foodOrderList.size();
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
        FoodInfo mFoodInfo;
        myHolder=new viewHolder();
        convertView = myLayoutInflater.inflate(R.layout.food_ordered_item,null);
        mFoodInfo = foodOrderList.get(position);
        //bound element
        myHolder.foodinfo = mFoodInfo;
        myHolder.tv_title = convertView.findViewById(R.id.food_item_tv_foodName);
        myHolder.tv_descrip = convertView.findViewById(R.id.food_item_tv_food_descrip);
        myHolder.tv_price = convertView.findViewById(R.id.food_list_tv_price);
        myHolder.btn = convertView.findViewById(R.id.food_list_btn);
        convertView.setTag(myHolder);
        //valuate
        mFoodInfo = myHolder.foodinfo;
        myHolder.tv_title.setText(mFoodInfo.getFoodName());
        myHolder.tv_descrip.setText(mFoodInfo.getFoodDescription());
        myHolder.tv_price.setText(mFoodInfo.getFoodPrice()+"");
        myHolder.btn.setOnClickListener(new btnClick(myHolder));
        //todo for food photo
        return convertView;
    }
    static class viewHolder{
        public TextView tv_title,tv_descrip,tv_price;
        FoodInfo foodinfo;
        Button btn;
    }
    //todo improve

    private class btnClick implements View.OnClickListener{
        viewHolder myholder;
        public btnClick(viewHolder holder){
            this.myholder = holder;
        }
        @Override
        public void onClick(View v) {
            if(myholder.foodinfo.getOrderedNum()>=1) myholder.foodinfo.setOrderedNum(0);
            foodOrderList.remove(myholder.foodinfo);
            notifyDataSetChanged();
        }
    }

}
