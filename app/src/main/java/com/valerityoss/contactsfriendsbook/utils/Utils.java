package com.valerityoss.contactsfriendsbook.utils;

import android.content.Context;

import com.valerityoss.contactsfriendsbook.R;

/**
 * Created by denis on 29.04.2016.
 */
public class Utils {

    public static boolean checkString(String stringItem) {
        return stringItem != null && !stringItem.equals("");
    }

    public static String getString(Context context, String stringIn) {
        return (stringIn != null && !stringIn.equals("")) ? stringIn : context.getString(R.string.unknown_string);
    }

    public static int getGenderInt(boolean isChecked) {
        return (isChecked) ? 1 : 0;
    }

    public static String getGenderString(Context context, int gender) {
        return (gender == 1) ? context.getString(R.string.gender_male_string) : context.getString(R.string.gender_female_string);
    }

    public static boolean getGenderBoolean(int gender) {
        return (gender == 1);
    }
}
