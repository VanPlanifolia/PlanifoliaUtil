package cn.planifolia.van.util;

/**
 * Created by Intellij IDEA<br>
 * int数组的工具类
 * @author Planifolia.Van
 * @version 1.0
 * @date 2022/8/9 15:13
 */
public class ArrayTool {

    /**
     * int数组格式化的方法
     * @param arr 传入数组
     * @return 返回格式化后的数组
     */
    public static  String arrayPrint(int[] arr){
        StringBuilder str= new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            str.append(arr[i]);
            if(i==arr.length-1){
                str.append("]");
            }else {
                str.append(",");
            }
        }
        return str.toString();
    }

    /**
     * int找到当前数组最大值的方法
     * @param arr 传入数组
     * @return 返回数组中的最大值
     */
    public static int getMax(int[] arr){
        int max=arr[0];
        for (int i : arr) {
            if (i>max){
                max=i;
            }
        }
        return max;
    }

    /**
     * 查找传入值在数组中的索引
     * @param arr 传入数组
     * @param data 传入值
     * @return 传入值在数组中的索引
     */
    public static int getIndex(int[] arr,int data){
        if (arr.length==0){
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]==data){
                return i;
            }
        }
        return -1;
    }

    /**
     * 判断两个数组是否完全一致
     * @param arr1 数组1
     * @param arr2 数组2
     * @return 比较结果
     */
    public static boolean equals(int[] arr1,int[] arr2){
        if (arr1==arr2){
            return true;
        }
        if (arr1.length != arr2.length){
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i]!=arr2[i]){
                return false;
            }
        }
        return true;
    }

    public static void sort(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length-i; j++) {
                if (arr[j]>arr[j+1]){
                    int temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
    }

}
