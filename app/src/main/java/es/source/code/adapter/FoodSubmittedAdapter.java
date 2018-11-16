package es.source.code.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.source.code.activity2.R;
import es.source.code.activity2.SCOS_Global_Application;
import es.source.code.fragments.fragment_food_submitted;
import es.source.code.model.FoodInfo;

public class FoodSubmittedAdapter extends BaseAdapter {
    private LayoutInflater myLayoutInflater;
    private SCOS_Global_Application myApp;
    private ArrayList<FoodInfo> foodSubmittedList;
    private ListView listView;
    private fragment_food_submitted fragment;

    public FoodSubmittedAdapter( android.support.v4.app.Fragment fragment,ListView listView){
        myApp = (SCOS_Global_Application)fragment.getActivity().getApplication();
        myLayoutInflater = fragment.getActivity().getLayoutInflater();
        this.fragment = (fragment_food_submitted) fragment;
        foodSubmittedList = myApp.getFoodSubmittedList();
        this.listView = listView;
    }
    @Override
    public int getCount() {
        return foodSubmittedList.size();
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
        convertView = myLayoutInflater.inflate(R.layout.food_submitted_item,null);
        mFoodInfo = foodSubmittedList.get(position);
        //bound element
        myHolder.foodinfo = mFoodInfo;
        myHolder.tv_title = convertView.findViewById(R.id.food_submitted_item_tv_foodName);
        myHolder.tv_descrip = convertView.findViewById(R.id.food_submitted_item_tv_food_descrip);
        myHolder.tv_price = convertView.findViewById(R.id.food_submitted_list_tv_price);
        //myHolder.tv_quant = convertView.findViewById(R.id.food_list_tv_quant);
        myHolder.btn = convertView.findViewById(R.id.btn_food_list_submitted);
        convertView.setTag(myHolder);
        //valuate
        mFoodInfo = myHolder.foodinfo;
        myHolder.tv_title.setText(mFoodInfo.getFoodName());
        myHolder.tv_descrip.setText(mFoodInfo.getFoodDescription());
        myHolder.tv_price.setText(mFoodInfo.getFoodPrice()+"");
        //myHolder.tv_quant.setText(mFoodInfo.getOrderedNum()+"");
        //change type tag;
        //todo for food photo
        //todo for click listener
        myHolder.btn.setOnClickListener(new btnCancelSubmittedClickListener(myHolder));
        return convertView;
    }
    static class viewHolder{
        public TextView tv_title,tv_descrip,tv_price;
        public Button btn;
        FoodInfo foodinfo;
    }
    //todo improve
    private class btnCancelSubmittedClickListener implements View.OnClickListener {
        private viewHolder mViewHolder;
        public btnCancelSubmittedClickListener(viewHolder itemHolder){
            mViewHolder = itemHolder;
        }

        @Override
        public void onClick(View v) {
            FoodInfo mfoodInfo = mViewHolder.foodinfo;
            if(mfoodInfo.getSubmitNum()>=1)mfoodInfo.setSubmitNum(mfoodInfo.getSubmitNum()-1);
            mfoodInfo.setStock(mfoodInfo.getStock()+1);
            updateList();
        }
    }
    private void updateList(){
        //fragment.updateList();
        myApp.getFoodSubmittedList();
        notifyDataSetChanged();
        fragment.updatePrice();
    }
}
