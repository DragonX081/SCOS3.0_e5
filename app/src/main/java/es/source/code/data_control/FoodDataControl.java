package es.source.code.data_control;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import es.source.code.model.FoodInfo;

public class FoodDataControl {
    public static boolean FoodDelete(String foodName, ArrayList<FoodInfo> foodInfos){
        for (FoodInfo foodInfo:foodInfos){
            if(foodName.equals(foodInfo.getFoodName())){
                foodInfos.remove(foodInfo);
                return true;
            }
        }
        return false;
    }

    public static ArrayList typeLabelize(ArrayList<FoodInfo> foodInfos){
        /*return a result ArrayList and update food position in foodinfo
         * index 0: for label
         * index 1: for label position
         */

        ArrayList<String> foodTypeLabel = new ArrayList<String>();
        ArrayList<Integer> foodLabelPosition = new ArrayList<Integer>();
        ArrayList resultList = new ArrayList();
        int count = 0;
        String curLabel = null;
        for(int i=0;i<foodInfos.size();i++){
            FoodInfo foodInfo = foodInfos.get(i);
            if(curLabel==null){
                curLabel = foodInfo.getFoodtype();
                foodLabelPosition.add(new Integer(i));
                foodTypeLabel.add(curLabel);
            }else{//for not null curlabel
                if(!curLabel.equals(foodInfo.getFoodtype())){//for new label
                    count++;
                    foodInfo.setFoodPos(count);
                    curLabel = foodInfo.getFoodtype();
                    foodTypeLabel.add(curLabel);
                    foodLabelPosition.add(new Integer(i));
                }
                else{
                    foodInfo.setFoodPos(count);
                }

            }
        }
        resultList.add(foodTypeLabel);
        resultList.add(foodLabelPosition);
        return resultList;
    }

    public static int[] toListInt(ArrayList<Integer> intArrayList){
        int[] list = new int[intArrayList.size()];
        for(int i=0;i<intArrayList.size();i++){
            list[i] = intArrayList.get(i).intValue();
        }
        return list;
    }
    public static void ArrayListSub(ArrayList<FoodInfo> A,ArrayList<FoodInfo> B){
        for(FoodInfo foodInfo:B){
            try{
                A.remove(foodInfo);
            }catch (Exception e){
                //todo catch Exception
            }
        }
    }
    public static void ArrayListCopy(ArrayList<FoodInfo> dest,ArrayList<FoodInfo> src){
        dest.clear();
        for(FoodInfo foodInfo:src){
            dest.add(foodInfo);
        }
    }
    public static float calPriceSum(ArrayList<FoodInfo> foodInfos){
        float sum = 0f;
        for(FoodInfo foodInfo: foodInfos){
            sum+=foodInfo.getFoodPrice();
        }
        return sum;
    }
}
