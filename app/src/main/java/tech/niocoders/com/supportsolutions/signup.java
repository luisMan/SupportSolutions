package tech.niocoders.com.supportsolutions;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.*;
import android.widget.Toast;


public class signup extends AppCompatActivity {

    // ALL MY VARIABLES //
    private TextView firstN;
    private EditText fnEditText;
    private TextView secondN;
    private EditText snEditText;
    private TextView phoneNumer;
    private EditText pnEditText;
    private TextView email;
    private EditText eEditText;
    private TextView password;
    private EditText pEditText;
    private TextView gender;
    private Spinner  gSpinner;
    private TextView custody;
    private Spinner  cSpinner;
    private Button   button;

    // VARIABLE FOR THE CONTEXT //
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singup);

        // INITIALIZING ALL MY VARIABLES //
        firstN     = findViewById(R.id.fNameText);
        fnEditText = findViewById(R.id.fNameEditText);
        secondN    = findViewById(R.id.sNameText);
        snEditText = findViewById(R.id.sNameEditText);
        phoneNumer = findViewById(R.id.pNumberText);
        pnEditText = findViewById(R.id.pNumberEditText);
        email      = findViewById(R.id.email_text);
        eEditText  = findViewById(R.id.emailEditText);
        password   = findViewById(R.id.password_text);
        pEditText  = findViewById(R.id.passwordEditText);
        gender     = findViewById(R.id.genderText);
        gSpinner   = findViewById(R.id.genderSpinner);
        custody    = findViewById(R.id.custodyText);
        cSpinner   = findViewById(R.id.custodySpinner);
        button     = findViewById(R.id.btnsingup);

        // INITIALIZING THE CONTEXT //
        context = getApplicationContext();

        // SPINNER FOR THE GENDER //
        // ********************** //

        // ADAPTER//
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.gender)
        );

        // SETTING THE ADAPTER //
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gSpinner.setAdapter(genderAdapter);



        // SPINNER FOR THE TYPES OF CUSTODY //
        // ******************************** //

        // ADAPTER //
        ArrayAdapter<String> custodyAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.custody)
        );

        // SETTING THE ADAPTER //
        custodyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cSpinner.setAdapter(custodyAdapter);

        // IMPLEMENTING THE SIGNUP BUTTON //
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // OPENING THE ACTIVITY THAT GOES AFTER REGISTRATION //



                // TOAST TO SHOW THE USER THAT REGISTRATION WAS SUCCESSFULLY //
                Toast.makeText(context, "DONE", Toast.LENGTH_LONG).show();
            }
        });
    }
}
