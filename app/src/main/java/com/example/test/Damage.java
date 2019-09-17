package com.example.test;

public class Damage {
    private static Damage mDamage;
    private static Integer mPercentDamage = 100;

    private static int CRITICAL_DAMAGE = 20;

    private Damage(){}

    public static Damage damagePlayer(){
        if (mDamage == null) {
            mDamage = new Damage();
        }
        return mDamage;
    }

    public static boolean isCritical(){
        if (mPercentDamage < CRITICAL_DAMAGE){
           return true;
        }
        return false;
    }

    public void damageState(int damage){
        mPercentDamage = damage;
    }

}
