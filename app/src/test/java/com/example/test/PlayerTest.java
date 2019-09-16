package com.example.test;

import android.content.Context;
import android.content.Intent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.reflect.Whitebox;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class PlayerTest {

    @Rule
    public MockitoRule mockitoJUnit = MockitoJUnit.rule();

    @Spy
    private Context mContext;

    @Mock
    private Gun mGun;

    @Captor
    ArgumentCaptor mArgumentCaptor;

    @InjectMocks
    private Player mPlayer = new Player();

    @Before
    public void setUp(){
        ShadowExtraProtection.FLAG = null;
    }


    @Test
    public void shoot_mCount_EqIsNine(){
        mPlayer.shoot();

        assertEquals(9, mPlayer.getCount());
    }

    @Test
    public void shoot_recharge_mMessageEqRecharged(){
        Whitebox.setInternalState(mPlayer,"mCount",0);
        when(mGun.getBullets()).thenReturn(1);

        mPlayer.shoot();

        assertTrue(Whitebox.getInternalState(mPlayer,"mMessage").equals("Recharged"));
    }

    @Test
    public void shoot_recharge_mMessageEqNotRecharged(){
        Whitebox.setInternalState(mPlayer,"mCount",0);
        when(mGun.getBullets()).thenReturn(0);

        mPlayer.shoot();

        assertTrue(Whitebox.getInternalState(mPlayer,"mMessage").equals("Not recharged"));
    }

    @Config(shadows = ShadowExtraProtection.class)
    @Test
    public void shoot_mPercentDamage_MoreThanCriticalPercentDamageAndProtectionIsFalse(){
        ShadowExtraProtection.FLAG = false;

        mPlayer.shoot();

        assertEquals(86,Whitebox.getInternalState(mPlayer,"mPercentDamage"));
        verifyZeroInteractions(mContext);
    }

    @Config(shadows = ShadowExtraProtection.class)
    @Test
    public void shoot_mPercentDamage_MoreThanCriticalPercentDamageAndProtectionIsTrue(){
        ShadowExtraProtection.FLAG = true;

        mPlayer.shoot();

        assertEquals(90,Whitebox.getInternalState(mPlayer,"mPercentDamage"));
        verify(mContext,never()).startService((Intent) mArgumentCaptor.capture());
    }

    @Test
    public void shoot_mPercentDamage_LessThanCriticalPercentDamage(){
        Whitebox.setInternalState(mPlayer,"mPercentDamage",19);
        Intent intent = new Intent(Player.ACTION_CRITICAL_DAMAGE).putExtra(Player.PERCENT_DAMAGE,(Integer) Whitebox.getInternalState(mPlayer,"mPercentDamage"));

        mPlayer.shoot();


        verify(mContext).startService((Intent) mArgumentCaptor.capture());
        assertEquals(intent.toString(),mArgumentCaptor.getValue().toString());
    }

}
