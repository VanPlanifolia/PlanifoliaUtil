package cn.planifolia.van.util;

import java.util.Arrays;

/**
 * Created by Intellij IDEA
 * 自我封装的数组
 * @author Planifolia.Van
 * @version 1.0
 * @date 2022/8/9 16:46
 */
public class ArrayPlus {
    /**
     * size:数组的长度
     */
    private int[] element;
    private int size;

    public ArrayPlus(){}

    /**
     * 有参构造器，会根据传入的长度来动态的创建一个数组
     * @param count 创建ArrayPlus的长度
     */
    public ArrayPlus(int count){
        element=new int[count];
    }

    /**
     * 数组的添加方法，并且加入了动态扩容的机制，当数组满员的时候会扩充原数组为原来的1.5倍
     * @param a 添加的数组元素
     */
    public void add(int a){
        if (element.length == size){
            element = Arrays.copyOf(element,size+(size>>1));
        }
        element[size++] = a;
    }

    /**
     * 按索引找到对应的元素
     * @param index MyArray的索引下标
     * @return 对应MyArray索引下标的数组元素
     */
    public int get(int index){
        return element[index];
    }

}
