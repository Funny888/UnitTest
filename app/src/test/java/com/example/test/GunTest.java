package com.example.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@Config(manifest = Config.NONE)
@PrepareForTest(Damage.class)
public class GunTest {

    @Rule
    public MockitoRule mockitoJUnit = MockitoJUnit.rule();

    Context mContext = mock(Context.class);

    @Mock
    Damage mDamageGun;

    @Spy
    BroadcastReceiver mSetDamage;


    @Mock
    Intent mIntent;

    Gun mGun = new Gun();

    @Before
    public void setUp(){
        PowerMockito.mockStatic(Damage.class);
    }

    @Test
    public void getBullets_return_CriticalIsTrue(){
        when(Damage.isCritical()).thenReturn(true);

        assertEquals(0,mGun.getBullets());
    }

    @Test
    public void getBullets_return_CriticalIsFalse(){
        assertEquals(20,mGun.getBullets());
    }

    @Test
    public void onReceive_ACTION_CRITICAL_DAMAGE_IntentGetActionEqACTION_CRITICAL_DAMAGE(){
//        mIntent = new Intent(Player.ACTION_CRITICAL_DAMAGE).putExtra(Player.PERCENT_DAMAGE,19);
        when(mIntent.getAction()).thenReturn(Player.ACTION_CRITICAL_DAMAGE);
        when(mIntent.getExtras().getInt(Player.PERCENT_DAMAGE)).thenReturn(19);

        mSetDamage.onReceive(mContext, mIntent);

        Mockito.verify(mDamageGun).damageState(19);
    }


}
