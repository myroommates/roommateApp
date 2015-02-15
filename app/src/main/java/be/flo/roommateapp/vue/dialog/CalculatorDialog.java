package be.flo.roommateapp.vue.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import be.flo.roommateapp.R;

/**
 * Created by florian on 15/02/15.
 */
public class CalculatorDialog extends Dialog {

    private String content = "";
    private EditText output;

    public CalculatorDialog(Context context) {
        super(context);

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_calculator);

        output = (EditText) findViewById(R.id.calculator_output);

        findViewById(R.id.b_calculator_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("0");
            }
        });
        findViewById(R.id.b_calculator_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("1");
            }
        });
        findViewById(R.id.b_calculator_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("2");
            }
        });
        findViewById(R.id.b_calculator_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("3");
            }
        });
        findViewById(R.id.b_calculator_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("4");
            }
        });
        findViewById(R.id.b_calculator_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("5");
            }
        });
        findViewById(R.id.b_calculator_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("6");
            }
        });
        findViewById(R.id.b_calculator_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("7");
            }
        });
        findViewById(R.id.b_calculator_8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("8");
            }
        });
        findViewById(R.id.b_calculator_9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("9");
            }
        });
        findViewById(R.id.b_calculator_decimal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write(".");
            }
        });


    }


    public void write(String val) {
        String tps = content + val;
        try {
            Double.parseDouble(tps);
            content = tps;
            output.setText(content);

        } catch (NumberFormatException e) {

        }
    }

}
