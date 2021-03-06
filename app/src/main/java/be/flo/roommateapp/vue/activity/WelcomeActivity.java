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
import be.flo.roommateapp.model.dto.*;
import be.flo.roommateapp.model.service.AccountService;
import be.flo.roommateapp.model.util.Storage;
import be.flo.roommateapp.model.util.exception.MyException;
import be.flo.roommateapp.model.util.externalRequest.RequestEnum;
import be.flo.roommateapp.model.util.externalRequest.WebClient;
import be.flo.roommateapp.vue.dialog.DialogConstructor;
import be.flo.roommateapp.vue.technical.AbstractActivity;

import java.util.ArrayList;
import java.util.Date;
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
            //Storage.store(this, generateFakeData());
            startActivity(new Intent(WelcomeActivity.this, MAIN_ACTIVITY));
        } else {
            if (false){//!ni.isConnected() || !ni.isAvailable()) {
                if (DEV_MODE) {
                    //Storage.store(this, generateFakeData());
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
/*
    public static LoginSuccessDTO generateFakeData() {

        LoginSuccessDTO loginSuccessDTO = new LoginSuccessDTO();


        RoommateDTO currentRoommate = new RoommateDTO();
        currentRoommate.setId(1L);
        currentRoommate.setAdmin(true);
        currentRoommate.setEmail("aa@zz.ee");
        currentRoommate.setIconColor(0F);
        currentRoommate.setKeepSessionOpen(true);
        currentRoommate.setLanguageCode("en");
        currentRoommate.setName("Florian");
        currentRoommate.setNameAbrv("Flo");
        currentRoommate.setPassword("pqpskdkd");


        loginSuccessDTO.setCurrentRoommate(currentRoommate);

        List<RoommateDTO> rommates = new ArrayList<>();
        rommates.add(currentRoommate);

        //create other roommate
        for(int i = 0;i<3;i++){
            RoommateDTO roommate = new RoommateDTO();
            roommate.setId((long) (i+2));
            roommate.setAdmin(false);
            roommate.setEmail("aa@zz"+i+".ee");
            roommate.setIconColor(i*15F);
            roommate.setKeepSessionOpen(true);
            roommate.setLanguageCode("en");
            if(i==0){
                roommate.setName("Coco");
                roommate.setNameAbrv("Coc");
            }
            else if(i==1){
                roommate.setName("Coco");
                roommate.setNameAbrv("Coc");
            }
            else if(i==2){
                roommate.setName("Popol");
                roommate.setNameAbrv("Pop");
            }
            roommate.setPassword("pqpskdkd");
            rommates.add(roommate);
        }

        loginSuccessDTO.setRoommates(rommates);

        HomeDTO home = new HomeDTO();
        home.setId(1L);
        home.setMoneySymbol("€");

        loginSuccessDTO.setHome(home);

        loginSuccessDTO.setAuthenticationKey("pokpokdsdfsdfsdfsdfx");

        //build list shopping
        List<ShoppingItemDTO> shop = new ArrayList<>();
        for(int i=0;i<2;i++){
            ShoppingItemDTO s = new ShoppingItemDTO();
            s.setId(Long.parseLong(i+""));
            s.setCreationDate(new Date());
            s.setCreatorId(currentRoommate.getId());
            s.setHomeId(home.getId());
            s.setDescription("article "+i);
        }

        loginSuccessDTO.setShoppingItems(shop);

        List<TicketDTO> ticketDTOs = new ArrayList<>();

        //build list ticket
        for(int i=1;i<10;i++){
            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO.setId((long) i);
            ticketDTO.setDescription("Ticket " + i);
            ticketDTO.setCategory(((i>5)?"Course":"Sortie"));
            ticketDTO.setDate(new Date());
            ticketDTO.setPayerId(1L);
            for(int j=1;j<5;j++){
                TicketDebtorDTO ticketDebtorDTO = new TicketDebtorDTO();
                ticketDebtorDTO.setRoommateId((long) j);
                ticketDebtorDTO.setValue(5.9);
                ticketDTO.addTicketDebtor(ticketDebtorDTO);
            }
            ticketDTOs.add(ticketDTO);

        }
        loginSuccessDTO.setTickets(ticketDTOs);

        return loginSuccessDTO;
    }
*/
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
