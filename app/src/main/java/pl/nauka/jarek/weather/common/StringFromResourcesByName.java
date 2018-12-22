package pl.nauka.jarek.weather.common;

import android.content.Context;

public class StringFromResourcesByName {

    public static String getStringFromResourcesByName(String description, Context context){

        String name = "";
        if (description.equals("sand, dust whirls")){
            name = "sand_dust_whirls";
        }else {
            for (int i = 0; i < description.length(); i++) {
                if (description.charAt(i) == ' '){
                    name = name + "_";
                }else {
                    name = name + String.valueOf(description.charAt(i));
                }
            }
        }
        String packageName = context.getPackageName();
        int resourceId = context.getResources().getIdentifier(name,"string", packageName);

        return context.getString(resourceId);
    }

}
