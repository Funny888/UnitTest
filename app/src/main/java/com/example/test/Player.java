package com.example.test;

import android.content.Context;
import android.content.Intent;

public class Player {
    public static final String ACTION_CRITICAL_DAMAGE = "com.example.test.DAMAGE";
    public static final String PERCENT_DAMAGE = "Percent";
    private Gun mGun;
    private int mCount;
    private Integer mPercentDamage;
    private final Integer mCriticalPercentDamage = 20;
    private final Integer mDamageWithoutProtection = 14;
    private final Integer mDamageWithProtection = 10;
    private String mMessage;
    private Context mContext;

    public Player(){
        mGun = new Gun();
        mPercentDamage = 100;
        mCount = 10;
    }



    public void shoot(){
        if (mCount > 0) {
            mCount--;

        } else {
           recharge();
        }

        if (mPercentDamage > mCriticalPercentDamage){
            if (!ExtraProtection.isProtection()) {
                mPercentDamage -= mDamageWithoutProtection;
            } else {
                mPercentDamage -= mDamageWithProtection;
            }
        } else {
            Intent intent = new Intent(ACTION_CRITICAL_DAMAGE).putExtra(PERCENT_DAMAGE,mPercentDamage);
            mContext.startService(intent);
        }
    }


    public void recharge(){
        if (isAvaliableRecharge()) {
            mMessage = "Recharged";
        } else {
            mMessage = "Not recharged";
        }
    }

    private boolean isAvaliableRecharge(){
        if (mGun.getBullets() > 0) {
            mCount = 10;
            return true;
        }
        return false;
    }

    public int getCount() {
        return mCount;
    }
}
