package fudi.freddy.biox_ussd.use_case;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fudi.freddy.biox_ussd.R;
import fudi.freddy.ussdlibrary.USSDController;

public class CP1 extends Fragment {

    TextView result;
    EditText phone;
    Button btn ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_op1, container, false);
        result = (TextView) view.findViewById(R.id.result);
        phone = (EditText) view.findViewById(R.id.phone);
        btn = (Button) view.findViewById(R.id.btn);
        setHasOptionsMenu(false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.getText().toString().trim();
                USSDController ussdController = USSDController.getInstance(getActivity());
                ussdController.callUSSDInvoke(phoneNumber,result);
            }
        });

        return view;
    }
}

