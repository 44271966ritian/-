package org.example;


import org.example.hsp.TargerObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings({"ALL"})
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Class<?> aClass = Class.forName("org.example.hsp.TargerObject");
        TargerObject targerObject = (TargerObject) aClass.newInstance();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        Method publicMethod = aClass.getDeclaredMethod("publicMethod", String.class);
        publicMethod.invoke(targerObject,"javaGuide");

        Method privateMethod = aClass.getDeclaredMethod("privateMethod");
        privateMethod.setAccessible(true);
        privateMethod.invoke(targerObject);


    }

}


class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int sum=0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
        }
        sum+=target;
        if(sum%2!=0){
            return 0;
        }
        //找出0~n中和为sum/2的组合
        int t=sum/2;
        int dp[]=new int[target+1];
        dp[0]=1;
        for(int n:nums){
            for(int i=t;i>=n;i--){
                dp[i]+=dp[i-n];
            }
        }

        return dp[t];
    }
}

class A2 implements A{

    A a;
    public A2(A a){
        this.a=a;
    }
    @Override
    public void cx() {
        long s = System.currentTimeMillis();
        a.cx();
        long e = System.currentTimeMillis();
        long t = e - s;
        System.out.println("t = " + t);
    }
}
interface A{
    void cx();
}


abstract class C{

    abstract void say();

}

class D extends C{

    @Override
    void say() {

    }
}


class A1 implements A{

    @Override
    public void cx() {
        System.out.println("A实现方法");
    }
}

