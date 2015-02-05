package be.flo.roommateapp.vue.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import be.flo.roommateapp.R;
import be.flo.roommateapp.model.dto.HomeDTO;
import be.flo.roommateapp.model.dto.LoginSuccessDTO;
import be.flo.roommateapp.model.dto.RoommateDTO;
import be.flo.roommateapp.model.service.AccountService;
import be.flo.roommateapp.model.util.Storage;
import be.flo.roommateapp.model.util.exception.MyException;
import be.flo.roommateapp.model.util.externalRequest.RequestEnum;
import be.flo.roommateapp.model.util.externalRequest.WebClient;
import be.flo.roommateapp.vue.dialog.DialogConstructor;
import be.flo.roommateapp.vue.technical.AbstractActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 23/11/14.
 */
public class WelcomeActivity extends AbstractActivity {

    public static final boolean DEV_MODE = false;

    /**
     * build
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_welcome);

        //test authentication
        String authenticationKey = AccountService.getAuthenticationKey(this);

        //test connections
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();


        if (DEV_MODE) {
            Storage.store(this, generateFakeData());
            startActivity(new Intent(WelcomeActivity.this, MAIN_ACTIVITY));
        } else {
            if (!ni.isConnected() || !ni.isAvailable()) {
                if (DEV_MODE) {
                    Storage.store(this, generateFakeData());
                    startActivity(new Intent(WelcomeActivity.this, MAIN_ACTIVITY));
                } else {
                    DialogConstructor.dialogWarning(this,"you are not connected").show();
                }
            } else {
                if (authenticationKey != null) {
                    Storage.setAuthenticationKey(authenticationKey);
                    LoadDataRequest loadDataRequest = new LoadDataRequest();
                    loadDataRequest.execute();


                } else {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
            }
        }
    }

    public static LoginSuccessDTO generateFakeData() {

        LoginSuccessDTO loginSuccessDTO = new LoginSuccessDTO();


        RoommateDTO currentRoommate = new RoommateDTO();
        currentRoommate.setAdmin(true);
        currentRoommate.setEmail("aa@zz.ee");
        currentRoommate.setIconColor(0F);
        currentRoommate.setId(1L);
        currentRoommate.setKeepSessionOpen(true);
        currentRoommate.setLanguageCode("en");
        currentRoommate.setName("Florian");
        currentRoommate.setNameAbrv("Flo");
        currentRoommate.setPassword("pqpskdkd");

        loginSuccessDTO.setCurrentRoommate(currentRoommate);

        List<RoommateDTO> rommates = new ArrayList<>();
        rommates.add(currentRoommate);

        loginSuccessDTO.setRoommates(rommates);

        HomeDTO home = new HomeDTO();
        home.setId(1L);
        home.setMoneySymbol("€");

        loginSuccessDTO.setHome(home);

        loginSuccessDTO.setAuthenticationKey("pokpokdsdfsdfsdfsdfx");

        return loginSuccessDTO;
    }

    /**
     * login request
     */
    private class LoadDataRequest extends AsyncTask<String, Void, Void> {

        private LoginSuccessDTO loginSuccessDTO;
        private String errorMessage;

        private LoadDataRequest() {
        }

        /**
         * send request
         */
        @Override
        protected Void doInBackground(String... params) {

            WebClient<LoginSuccessDTO> webClient = new WebClient<>(RequestEnum.LOAD_DATA, LoginSuccessDTO.class);

            try {
                loginSuccessDTO = webClient.sendRequest();
            } catch (MyException e) {
                e.printStackTrace();
                errorMessage = e.getMessage();
            }
            return null;
        }

        /**
         * after execution
         */
        @Override
        protected void onPostExecute(Void result) {

            if (errorMessage != null) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            } else {
                //store data and change interface
                Storage.store(WelcomeActivity.this, loginSuccessDTO);
                startActivity(new Intent(WelcomeActivity.this, MAIN_ACTIVITY));
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //display
        }

    }
}
