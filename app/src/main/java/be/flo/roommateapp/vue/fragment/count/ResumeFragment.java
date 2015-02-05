package be.flo.roommateapp.vue.fragment.count;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import be.flo.roommateapp.R;
import be.flo.roommateapp.model.dto.ListTicketDTO;
import be.flo.roommateapp.model.dto.RoommateDTO;
import be.flo.roommateapp.model.dto.TicketDTO;
import be.flo.roommateapp.model.dto.TicketDebtorDTO;
import be.flo.roommateapp.model.util.Storage;
import be.flo.roommateapp.model.util.StringUtil;
import be.flo.roommateapp.model.util.UserIcon;
import be.flo.roommateapp.model.util.exception.MyException;
import be.flo.roommateapp.model.util.externalRequest.RequestEnum;
import be.flo.roommateapp.model.util.externalRequest.WebClient;
import be.flo.roommateapp.vue.listAdapter.BalanceListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 2/11/14.
 */
public class ResumeFragment extends Fragment {

    private View view;
    private BalanceListAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        getActivity().invalidateOptionsMenu();

        //build layout
        view = inflater.inflate(R.layout.fragment_count_resume, container, false);

        adapter = new BalanceListAdapter(this.getActivity(), Storage.getRoommateList());

        //create the adapter
        LinearLayout listContent = (LinearLayout) view.findViewById(R.id.list_roommate_insertion);
        for (RoommateDTO roommate : Storage.getRoommateList()) {
            listContent.addView(generateRoomateView(roommate, inflater, container));
        }

        computeResult(inflater);

        return view;
    }

    private View generateRoomateView(RoommateDTO roommate, LayoutInflater inflater, ViewGroup container) {

        View convertView = inflater.inflate(R.layout.list_element_balance, container, false);

        double difference = computeBalance(roommate);

        //set text
        ((TextView) convertView.findViewById(R.id.text1)).setText(roommate.getString());
        ((TextView) convertView.findViewById(R.id.text_value)).setText(StringUtil.toDouble(difference) + Storage.getHome().getMoneySymbol());
        if (difference > 0) {
            ((TextView) convertView.findViewById(R.id.text_value)).setTextColor(this.getResources().getColor(R.color.positive_value));
        } else if (difference < 0) {
            ((TextView) convertView.findViewById(R.id.text_value)).setTextColor(this.getResources().getColor(R.color.negative_value));
        }

        //generate icon
        View userIconView = UserIcon.generateUserIcon(getActivity(), roommate);

        ((ViewGroup) convertView.findViewById(R.id.icon_content)).addView(userIconView, 0);

        return convertView;
    }

    private double computeBalance(RoommateDTO roommateDTO) {

        double payed = 0.0, mustPay = 0.0;

        for (TicketDTO ticketDTO : Storage.getTicketList()) {
            for (TicketDebtorDTO ticketDebtorDTO : ticketDTO.getDebtorList()) {
                if (ticketDTO.getPayerId().equals(roommateDTO.getId())) {
                    payed += ticketDebtorDTO.getValue();
                }
                if (ticketDebtorDTO.getRoommateId().equals(roommateDTO.getId())) {
                    mustPay += ticketDebtorDTO.getValue();
                }
            }
        }
        return payed - mustPay;
    }

    //  getActivity().getActionBar().setTitle(R.string.menu_result);

    private void computeResult(LayoutInflater inflater) {

        List<ResultDetail> resultDetailList = new ArrayList<>();

        for (RoommateDTO roommateDTO : Storage.getRoommateList()) {

            ResultDetail resultDetail = new ResultDetail(roommateDTO);

            for (TicketDTO ticketDTO : Storage.getTicketList()) {
                for (TicketDebtorDTO ticketDebtorDTO : ticketDTO.getDebtorList()) {

                    if (ticketDTO.getPayerId().equals(roommateDTO.getId())) {
                        resultDetail.payed += ticketDebtorDTO.getValue();
                    }
                    if (ticketDebtorDTO.getRoommateId().equals(roommateDTO.getId())) {
                        resultDetail.mustPay += ticketDebtorDTO.getValue();
                    }
                }
            }
            resultDetail.resetToReceive = resultDetail.payed - resultDetail.mustPay;

            resultDetailList.add(resultDetail);
        }

        List<Balance> balanceList = new ArrayList<>();

        for (ResultDetail resultDetail : resultDetailList) {
            double toPay = resultDetail.payed - resultDetail.mustPay;
            if (toPay < 0) {
                //I must to pay
                for (ResultDetail resultDetail2 : resultDetailList) {
                    if (resultDetail2.resetToReceive > 0) {
                        Balance balance = null;
                        for (Balance balanceToFind : balanceList) {
                            if (balanceToFind.from.equals(resultDetail.getRoommateDTO()) &&
                                    balanceToFind.to.equals(resultDetail2.getRoommateDTO())) {
                                balance = balanceToFind;
                            }
                        }
                        if (balance == null) {
                            balance = new Balance();
                            balance.from = resultDetail.getRoommateDTO();
                            balance.to = resultDetail2.getRoommateDTO();
                        }
                        balanceList.add(balance);
                        if (resultDetail2.resetToReceive >= Math.abs(toPay)) {
                            balance.value += Math.abs(toPay);
                            resultDetail2.resetToReceive -= Math.abs(toPay);
                            break;
                        } else {
                            balance.value += resultDetail2.resetToReceive;
                            toPay += resultDetail2.resetToReceive;
                            resultDetail2.resetToReceive = 0;
                        }
                    }
                }
            }
        }

        for (Balance balance : balanceList) {
            //for each field : create a view and insert it
            View listElement = inflater.inflate(R.layout.list_element_result_balance, null);

            ((ViewGroup) listElement.findViewById(R.id.roommate_from)).addView(UserIcon.generateUserIcon(getActivity(), balance.from, false));
            ((ViewGroup) listElement.findViewById(R.id.roommate_to)).addView(UserIcon.generateUserIcon(getActivity(), balance.to, false));
            ((TextView) listElement.findViewById(R.id.text2)).setText(StringUtil.toDouble(balance.value) + "" + Storage.getHome().getMoneySymbol());


            ((TableLayout) view.findViewById(R.id.balance_container)).addView(listElement);
        }

    }

    public static class ResultDetail {
        private final RoommateDTO roommateDTO;

        private double payed = 0;
        private double mustPay = 0;
        private double resetToReceive = 0;

        public ResultDetail(RoommateDTO roommateDTO) {
            this.roommateDTO = roommateDTO;
        }

        public RoommateDTO getRoommateDTO() {
            return roommateDTO;
        }

        public double getPayed() {
            return payed;
        }

        public double getMustPay() {
            return mustPay;
        }
    }

    private static class Balance {
        private RoommateDTO from;
        private RoommateDTO to;
        private Double value = 0.0;

        @Override
        public String toString() {
            return "Balance{" +
                    "from=" + from +
                    ", to=" + to +
                    ", value=" + value +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Balance &&
                    ((Balance) o).from.equals(this.from) &&
                    ((Balance) o).to.equals(this.to);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        assert getActivity().getActionBar() != null;

        getActivity().getActionBar().setTitle(R.string.nav_count_resume);

        menu.clear();

        MenuInflater menuInflater = this.getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_count_balance, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.b_refresh_roommate:
                RefreshRequest request = new RefreshRequest();
                request.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Refresh task.
     */
    private class RefreshRequest extends AsyncTask<String, Void, Void> {

        private ListTicketDTO listTicketDTO = null;
        private String errorMessage = null;

        @Override
        protected Void doInBackground(String... params) {

            WebClient<ListTicketDTO> webClient = new WebClient<>(RequestEnum.TICKET_GET, ListTicketDTO.class);
            try {
                listTicketDTO = webClient.sendRequest();

            } catch (MyException e) {
                e.printStackTrace();
                errorMessage = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (errorMessage != null) {
                view.findViewById(R.id.error_message_container).setVisibility(View.VISIBLE);
                ((TextView) view.findViewById(R.id.error_message)).setText(errorMessage);
            } else {
                Storage.setTickets(listTicketDTO.getList());
                //computeResult();
            }
        }

        @Override
        protected void onPreExecute() {
            view.findViewById(R.id.error_message_container).setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //display
        }

    }
}


