package android.fit.ba.rentacar.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.fit.ba.rentacar.data.AuthenticationResultVM;

public class MySession {
    private static final String PREFS_NAME = "SP";
    private static String nekikey = "Key_korisnik";

    public static AuthenticationResultVM getKorisnik() {
        SharedPreferences sharedPreferences = MyApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String strJson = sharedPreferences.getString(nekikey, "");
        if (strJson.length() == 0)
            return null;

        AuthenticationResultVM x = MyGson.build().fromJson(strJson, AuthenticationResultVM.class);
        return x;
    }

    public static void setKorisnik(AuthenticationResultVM x) {
        String strJson = x != null ? MyGson.build().toJson(x) : "";

        SharedPreferences sharedPreferences = MyApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(nekikey, strJson);
        editor.apply();
    }
}
