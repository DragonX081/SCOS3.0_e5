package es.source.code.activity2;

import android.app.Application;

import java.util.ArrayList;

import es.source.code.data_control.FoodDataControl;
import es.source.code.model.FoodInfo;
import es.source.code.model.User;

public class SCOS_Global_Application extends Application {
    public User userInfo;
    public ArrayList<FoodInfo> foodInfos;
    public ArrayList<FoodInfo> foodOrderList;
    public ArrayList<FoodInfo> foodSubmittedList;
    public ArrayList<FoodInfo> foodUnOrderedList;

    @Override
    public void onCreate() {
        super.onCreate();
        foodInfos = new ArrayList<FoodInfo>();
        foodOrderList = new ArrayList<FoodInfo>();
        foodSubmittedList = new ArrayList<FoodInfo>();
        foodUnOrderedList = new ArrayList<FoodInfo>();
    }

    public User getUserInfo(){
        return userInfo;
    }
    public ArrayList<FoodInfo> getFoodInfos(){
        return foodInfos;
    }
    public void setFoodInfos(ArrayList<FoodInfo> foodInfos) {
        this.foodInfos = foodInfos;
    }
    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public void setFoodUnOrderedList(ArrayList<FoodInfo> foodUnOrderedList) {
        for(FoodInfo foodInfo:foodUnOrderedList){
            this.foodUnOrderedList.add(foodInfo);
        }
    }

    /*public ArrayList<FoodInfo> getFoodOrderedList() {
        return foodOrderedList;
    }*/

    public ArrayList<FoodInfo> getFoodUnOrderedList() {
        return foodUnOrderedList;
    }

    public ArrayList<FoodInfo> getFoodOrderList() {
        foodOrderList.clear();
        for(FoodInfo foodInfo:foodInfos){
            if(foodInfo.getOrderedNum()>0){
                foodOrderList.add(foodInfo);
            }
        }
        return foodOrderList;
    }
    public void updateUnOrderedList(){
        FoodDataControl.ArrayListCopy(foodUnOrderedList,foodInfos);
        FoodDataControl.ArrayListSub(foodUnOrderedList,foodOrderList);
    }

    public ArrayList<FoodInfo> getFoodSubmittedList() {
        foodSubmittedList.clear();
        for(FoodInfo foodInfo:foodInfos){
            if(foodInfo.getSubmitNum()>=1){
                foodSubmittedList.add(foodInfo);
            }
        }
        return foodSubmittedList;
    }

    public void dataInit(){
        for(FoodInfo foodInfo:foodInfos){
            foodInfo.setOrderedNum(0);
            foodInfo.setSubmitNum(0);
            foodOrderList.clear();
            foodSubmittedList.clear();
        }
    }
    public void generateTestData(){
        foodInfos = FoodInfo.getTestFoodInfo(20);
    }
}
