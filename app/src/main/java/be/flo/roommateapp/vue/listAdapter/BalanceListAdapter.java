package be.flo.roommateapp.vue.listAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import be.flo.roommateapp.R;
import be.flo.roommateapp.model.dto.RoommateDTO;
import be.flo.roommateapp.model.dto.TicketDTO;
import be.flo.roommateapp.model.dto.TicketDebtorDTO;
import be.flo.roommateapp.model.util.Storage;
import be.flo.roommateapp.model.util.StringUtil;
import be.flo.roommateapp.model.util.UserIcon;

import java.util.List;

/**
 * Created by florian on 18/11/14.
 * build a roommate
 */
public class BalanceListAdapter extends ArrayAdapter<RoommateDTO> {

    private List<RoommateDTO> items;
    private Context context;


    public BalanceListAdapter(Context context, List<RoommateDTO> items) {
        super(context, R.layout.list_element_balance, items);
        this.context = context;
        this.items = items;
    }

    /**
     * compute the balance between spend and dept
     *
     * @param roommateDTO
     * @return
     */
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RoommateDTO dto = items.get(position);

        MenuElement element = new MenuElement(dto);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.list_element_balance, parent, false);

        double difference = computeBalance(dto);

        //set text
        ((TextView) convertView.findViewById(R.id.text1)).setText(dto.getString());
        ((TextView) convertView.findViewById(R.id.text_value)).setText(StringUtil.toDouble(difference) + Storage.getHome().getMoneySymbol());
        if (difference > 0) {
            ((TextView) convertView.findViewById(R.id.text_value)).setTextColor(context.getResources().getColor(R.color.positive_value));
        } else if (difference < 0) {
            ((TextView) convertView.findViewById(R.id.text_value)).setTextColor(context.getResources().getColor(R.color.negative_value));
        }

        //generate icon
        View userIconView = UserIcon.generateUserIcon(context, dto);

        ((ViewGroup) convertView.findViewById(R.id.icon_content)).addView(userIconView, 0);

        element.setContent(convertView.findViewById(R.id.icon_content));

        return convertView;
    }

    public static class MenuElement {
        private RoommateDTO dto;
        private View content;

        public MenuElement(RoommateDTO dto) {
            this.dto = dto;
        }

        public View getContent() {
            return content;
        }

        public void setContent(View content) {
            this.content = content;
        }

        public RoommateDTO getDto() {
            return dto;
        }

        public void setDto(RoommateDTO dto) {
            this.dto = dto;
        }
    }


}
