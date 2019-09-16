package com.example.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@Config(manifest = Config.NONE)
@PrepareForTest(Damage.class)
public class DamageTest {

    @Rule
    public MockitoRule mockitoJUnit = MockitoJUnit.rule();

    @Spy
    Damage mDamage;

    @Before
    public void setUp(){
        spy(Damage.class);
    }

    @Test
    public void isCritical_mPercentDamage_LessThenCRITICAL_DAMAGE(){
        Whitebox.setInternalState(Damage.class,"mPercentDamage",19);

        assertTrue(Damage.isCritical());
    }

    @Test
    public void isCritical_mPercentDamage_MoreThenCRITICAL_DAMAGE(){
        Whitebox.setInternalState(Damage.class,"mPercentDamage",21);

        assertFalse(Damage.isCritical());
    }

    @Test
    public void damageState_mPercentDamage_DamageEqPercentDamage(){
        mDamage.damageState(40);

        assertEquals(40,Whitebox.getInternalState(Damage.class,"mPercentDamage"));
    }


}
