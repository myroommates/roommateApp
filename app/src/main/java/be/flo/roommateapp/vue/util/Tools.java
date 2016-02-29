package be.flo.roommateapp.vue.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by flo on 29/02/16.
 */
public class Tools {



    public static CharSequence getHelp(Activity activity, int textRef) {
        SpannableString s = new SpannableString(activity.getString(textRef));
        s.setSpan(new android.text.style.LeadingMarginSpan.Standard(70, 0), 0, s.length(), 0);
        return s;
    }

    public static String convertMinuteToString(int minuteDay) {
        int hour = minuteDay / 60;
        int minute = minuteDay - (hour * 60);
        String hourS = (hour >= 10) ? hour + "" : "0" + hour;
        String minuteS = (minute >= 10) ? minute + "" : "0" + minute;
        return hourS + ":" + minuteS;
    }

    public static List<String> getAccountEmails(Activity activity){
        List<String> emails = new ArrayList<>();
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(activity).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                emails.add(account.name);
            }
        }
        return emails;
    }
}
