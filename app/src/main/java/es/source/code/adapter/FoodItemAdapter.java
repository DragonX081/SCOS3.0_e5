package es.source.code.adapter;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import es.source.code.activity2.FoodDetailed;
import es.source.code.activity2.MainScreen;
import es.source.code.activity2.R;

import java.util.ArrayList;
import java.util.List;

import es.source.code.activity2.SCOS_Global_Application;
import es.source.code.model.FoodInfo;

public class FoodItemAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater myLayoutInflater;
    private List<FoodInfo> foodInfos;
    private RadioGroup linkedRadioGroup;
    public boolean toChangeTypeButton = true;
    private SCOS_Global_Application myApp;
    private Fragment fragment;
    private ArrayList<FoodInfo> foodOrderList,foodUnOrderedList;

    public FoodItemAdapter(List<FoodInfo> foodInfos, android.support.v4.app.Fragment fragment, RadioGroup linkedRadioGroup){
        myApp = (SCOS_Global_Application)fragment.getActivity().getApplication();
        this.foodInfos = myApp.getFoodInfos();
        this.linkedRadioGroup = linkedRadioGroup;
        this.fragment = fragment;
        myLayoutInflater = fragment.getActivity().getLayoutInflater();
        foodOrderList = myApp.getFoodOrderList();
        foodUnOrderedList = myApp.getFoodUnOrderedList();
    }
    @Override
    public int getCount() {
        return foodInfos.size();
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
        //Log.i("priceAdp",String.valueOf(foodInfos.get(0).getFoodPrice()));
        viewHolder myHolder;
        FoodInfo mFoodInfo;
        myHolder=new viewHolder();
        convertView = myLayoutInflater.inflate(R.layout.food_order_item,null);
        mFoodInfo = foodInfos.get(position);
        //bound element
        myHolder.foodinfo = mFoodInfo;
        myHolder.tv_title = convertView.findViewById(R.id.food_item_tv_foodName);
        myHolder.tv_descrip = convertView.findViewById(R.id.food_item_tv_food_descrip);
        myHolder.tv_price = convertView.findViewById(R.id.food_list_tv_price);
        myHolder.tv_stock = convertView.findViewById(R.id.food_list_tv_stock);
        myHolder.btn = convertView.findViewById(R.id.food_list_btn);
        convertView.setTag(myHolder);
        //valuate
        mFoodInfo = myHolder.foodinfo;
        myHolder.tv_title.setText(mFoodInfo.getFoodName());
        myHolder.tv_descrip.setText(mFoodInfo.getFoodDescription());
        myHolder.tv_price.setText(mFoodInfo.getFoodPrice()+"");
        myHolder.tv_stock.setText(mFoodInfo.getStock()+"");
        //set button
        myHolder.btn.setBackgroundResource(mFoodInfo.getOrderedNum()==0?R.drawable.btn_order:R.drawable.btn_cancel_order);
        myHolder.btn.setText(mFoodInfo.getOrderedNum()==0?"点\n菜":"退\n点");
        if(mFoodInfo.getSubmitNum()>=1){
            myHolder.btn.setBackgroundResource(R.drawable.btn_submitted_order);
            myHolder.btn.setText("已\n下\n单");
        }

        //myHolder.tv_quant.setText(mFoodInfo.getOrderedNum()+"");
        //change type tag;
        try{if(toChangeTypeButton)linkedRadioGroup.check(mFoodInfo.getFoodPos());}catch (Exception e){}
        //todo for food photo
        //todo for click listener
        myHolder.btn.setOnClickListener(new BtnClickListener(myHolder));
        itemClickListener icListener = new itemClickListener();
        icListener.setPosition(position);
        convertView.setOnClickListener(icListener);
        return convertView;
    }
    static class viewHolder{
        public TextView tv_title,tv_descrip,tv_price,tv_stock;
        public Button btn;
        FoodInfo foodinfo;
    }
    //todo improve
    private class BtnClickListener implements View.OnClickListener {
        private final int IS_INC_LISTENER = 0;
        private final int IS_DEC_LISTENER = 1;
        private final int IS_SUBMITTED_LISTENER=2;
        private int listenerType = IS_INC_LISTENER;
        private viewHolder mViewHolder;
        public BtnClickListener(viewHolder itemHolder){
            mViewHolder = itemHolder;
            listenerType = itemHolder.foodinfo.getOrderedNum()==0?IS_INC_LISTENER:IS_DEC_LISTENER;
            if(itemHolder.foodinfo.getSubmitNum()>=1)listenerType = IS_SUBMITTED_LISTENER;
        }

        @Override
        public void onClick(View v) {
            FoodInfo mfoodInfo = mViewHolder.foodinfo;
            if(listenerType==IS_INC_LISTENER) {
                if(mfoodInfo.getStock()>0) {
                    if (mfoodInfo.getOrderedNum() == 0) {
                        mfoodInfo.setOrderedNum(mfoodInfo.getOrderedNum() + 1);
                        foodOrderList.add(mfoodInfo);
                        Toast.makeText(fragment.getActivity(), "点菜成功", Toast.LENGTH_SHORT).show();
                    }
                    mViewHolder.btn.setText("退\n点");
                    mViewHolder.btn.setBackgroundResource(R.drawable.btn_cancel_order);
                    listenerType = IS_DEC_LISTENER;
                }else{
                    Toast.makeText(fragment.getActivity(),"客官没库存啦~",Toast.LENGTH_SHORT).show();
                }
            }else if(listenerType==IS_DEC_LISTENER){
                //set decListener
                if(mfoodInfo.getOrderedNum()==1) {
                    mfoodInfo.setOrderedNum(mfoodInfo.getOrderedNum() - 1);
                    foodOrderList.remove(mfoodInfo);
                    Toast.makeText(fragment.getActivity(),"取消成功", Toast.LENGTH_SHORT).show();
                    //foodUnOrderedList.add(mfoodInfo);
                }
                    //todo background
                    mViewHolder.btn.setText("点\n菜");
                    mViewHolder.btn.setBackgroundResource(R.drawable.btn_order);
                    listenerType = IS_INC_LISTENER;
            }
            else{
                //do nothing for submitted
            }
        }
    }
    private class itemClickListener implements View.OnClickListener {
        private int position;

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(fragment.getActivity(),FoodDetailed.class);
            intent.putExtra("position",position);
            fragment.getActivity().startActivity(intent);
        }
    }
}
