package com.idealessidealist.nudger.model;

import android.text.TextUtils;

/**
 * Created by Kenny Tsui on 10/9/2016.
 */

public class Validator {

    public static boolean isValidEmail(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
