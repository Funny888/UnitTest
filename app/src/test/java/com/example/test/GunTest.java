package com.example.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.verification.VerificationMode;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.powermock.reflect.Whitebox;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implementation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
//@PowerMockRunnerDelegate(PowerMockRunner.class)
@Config(manifest = Config.NONE)
@PrepareForTest(Damage.class)
public class GunTest {

//    @Rule
//    public MockitoRule mockitoJUnit = MockitoJUnit.rule();

    @Rule
    public PowerMockRule mockRule = new PowerMockRule();

    Context mContext = mock(Context.class);

//    Damage mDamageGun = Mockito.spy(Damage.damagePlayer());
    @Mock
    Damage mDamageGun;

   // Bundle b = new Bundle();

    Intent mIntent = mock(Intent.class);


    Intent intent = spy(new Intent()) ;

    @InjectMocks
    Gun mGun = new Gun();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Damage.class);
        intent.putExtra("rewrd","tre");
    }

    @Test
    public void getBullets_return_CriticalIsTrue(){
        when(Damage.isCritical()).thenReturn(true);

        assertEquals(0,mGun.getBullets());
        assertEquals("tre",intent.getStringExtra("rewrd"));
    }

    @Test
    public void getBullets_isCritical_VerifyMethod(){

        mGun.getBullets();
        verifyStatic(times(1));
        Damage.isCritical();
    }

    @Test
    public void getBullets_return_CriticalIsFalse(){
        assertEquals(20,mGun.getBullets());
    }


    @Test
    public void onReceive_ACTION_CRITICAL_DAMAGE_IntentGetActionEqACTION_CRITICAL_DAMAGE(){
//        mIntent = new Intent(Player.ACTION_CRITICAL_DAMAGE).putExtra(Player.PERCENT_DAMAGE,19);
        when(mIntent.getAction()).thenReturn(Player.ACTION_CRITICAL_DAMAGE);
        when(mIntent.getExtras()).thenReturn(mock(Bundle.class));
        when(mIntent.getExtras().getInt(Player.PERCENT_DAMAGE)).thenReturn(19);

        BroadcastReceiver mSetDamage = Whitebox.getInternalState(mGun,"mSetDamage");

        mSetDamage.onReceive(mContext, mIntent);

        Mockito.verify(mDamageGun).damageState(19);
    }

    @Ignore
    @Test
    public void setDamage_callPrivateMethod_VerifyMethod() throws Exception {
        when(mIntent.getAction()).thenReturn(Player.ACTION_CRITICAL_DAMAGE);
        when(mIntent.getExtras()).thenReturn(mock(Bundle.class));
        when(mIntent.getExtras().getInt(Player.PERCENT_DAMAGE)).thenReturn(19);


        BroadcastReceiver mSetDamage = Whitebox.getInternalState(mGun,"mSetDamage");

        mSetDamage.onReceive(mContext, mIntent);
        PowerMockito.verifyPrivate(mGun).invoke("setDamage",Matchers.anyInt());
        verifyNoMoreInteractions(mDamageGun);
    }

}
