package com.dcinspirations.homepage;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.widget.Toast;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Sp {
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor spe;

    public Sp(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences("default", 0);
        spe = sharedPreferences.edit();
    }

    public static void setMonth(String m){
        spe.putString("month",m);
        spe.commit();
    }
    public static String getMonth(){
        String m = sharedPreferences.getString("month","0/0000");
        return m;
    }
    public static void setLoggedIn(boolean state){
        spe.putBoolean("loggedIn",state);
        spe.commit();
    }
    public static boolean getLoggedIn(){
        boolean li = sharedPreferences.getBoolean("loggedIn",false);
        return li;
    }
    public static void setDetails(String email,String pass){
        spe.putString("email",email);
        spe.putString("pass",pass);
        spe.commit();
    }
    public static boolean checkDetails(String email,String password){
        String li = sharedPreferences.getString("email","");
        String li2 = sharedPreferences.getString("pass","");
        if(li.equalsIgnoreCase(email)&&li2.equalsIgnoreCase(password)){
            return true;
        }
        else {
            return false;
        }
    }

}
