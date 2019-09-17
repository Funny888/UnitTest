package com.example.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Gun {
    private Damage mDamageGun;
    private SetDamageClass mSetDamage;

    private int BULLETS = 20;

    public Gun(){
        mDamageGun = Damage.damagePlayer();
        mSetDamage = new SetDamageClass();
    }

    public int getBullets(){
        if (Damage.isCritical()) {
            return 0;
        }
        return BULLETS;
    }



    private void setDamage(int damage){
        mDamageGun.damageState(damage);
    }

   private class SetDamageClass extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Player.ACTION_CRITICAL_DAMAGE.equals(intent.getAction())) {
                setDamage(intent.getExtras().getInt(Player.PERCENT_DAMAGE));
            }
        }
    }
}
