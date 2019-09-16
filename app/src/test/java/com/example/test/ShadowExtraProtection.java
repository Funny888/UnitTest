package com.example.test;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(ExtraProtection.class)
public class ShadowExtraProtection {
    public static Boolean FLAG;


    public void __constructor__(){}

    @Implementation
    public static boolean isProtection(){
        return FLAG;
    }
}
