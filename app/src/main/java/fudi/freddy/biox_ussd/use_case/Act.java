package fudi.freddy.biox_ussd.use_case;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fudi.freddy.biox_ussd.R;


public class Act extends AppCompatActivity {
    public static TextView result;
    public static boolean processing = false;
    public static EditText phone;
    Button btn;
    Activity context;

    String respuesta1 = "RESPUESTA=OK";
    String respuesta2 = "RESPUESTA=FINAL/LONGITUD=350";
    String respuesta3 = "CODIGO=70006/NOMBRE=GIOLFO LUIS/APELLIDO=CORDOVA VERASTEGUI/DNI=46865746/FECHACADUCIDA=24-06-2020";

    private String RESPUESTA = "RESPUESTA=";
    private String LONGITUD = "LONGITUD=";
    private String CODIGO = "CODIGO=";
    private String NOMBRE = "NOMBRE=";
    private String APELLIDO = "APELLIDO=";
    private String DNI = "DNI=";
    private String FECHACADUCIDA = "FECHACADUCIDA=";


    private static Act instance;

    public static Act getInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        phone = (EditText) findViewById(R.id.phone);
        btn = (Button) findViewById(R.id.btn);
        instance = this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                *121*46#
                String phoneNumber = phone.getText().toString().trim();
                String ussd = "*" + phoneNumber + Uri.encode("#");
                Act.processing = true;
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussd));
                startActivity(intent);
            }
        });

        phone.setText("55100\n");

        result.append(obtenerData(respuesta1, RESPUESTA) + "\n");
        result.append(obtenerData(respuesta2,RESPUESTA )+"\n");
        result.append(obtenerData(respuesta2,LONGITUD)+"\n");
        result.append(obtenerData(respuesta3,CODIGO)+"\n");
        result.append(obtenerData(respuesta3,NOMBRE)+"\n");
        result.append(obtenerData(respuesta3,APELLIDO)+"\n");
        result.append(obtenerData(respuesta3,DNI)+"\n");
        result.append(obtenerData(respuesta3,FECHACADUCIDA)+"\n");

    }

    private String obtenerData(String respuesta1, String respuesta) {
        int index = respuesta1.indexOf(respuesta);
        int end = respuesta1.indexOf("/", index);
        if (end>=0)
            return respuesta1.substring(index+respuesta.length(),end);
        else
            return respuesta1.substring(index+respuesta.length());
    }
}
