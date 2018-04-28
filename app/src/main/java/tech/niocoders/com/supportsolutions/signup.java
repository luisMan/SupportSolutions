package tech.niocoders.com.supportsolutions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


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
    private FirebaseAuth mUser;

    private EditText languageEditText;


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
        languageEditText = (findViewById(R.id.languageEdit));

        // INITIALIZING THE CONTEXT //
        context = getApplicationContext();
        mUser = FirebaseAuth.getInstance();

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
                String fname = fnEditText.getText().toString();
                String lname = snEditText.getText().toString();
                String phone =  pnEditText.getText().toString();
                String email =  eEditText.getText().toString();
                String password =  pEditText.getText().toString();
                String gender = gSpinner.getSelectedItem().toString();
                String custody = cSpinner.getSelectedItem().toString();
                String language = languageEditText.getText().toString();

                if(validateString(fname)
                        && validateString(lname)
                        && validateString(phone)
                        && validateString(email)
                        && validateString(gender)
                        && validateString(custody)
                        && validateString(language)
                        && validateString(password))
                {
                    Context context = signup.this;
                    Class childClass =  ss_camera_helper.class;
                    Intent ss_camera_intent =  new Intent(context,childClass);
                    ss_camera_intent.putExtra("fname",fname);
                    ss_camera_intent.putExtra("lname",lname);
                    ss_camera_intent.putExtra("phone",phone);
                    ss_camera_intent.putExtra("email",email);
                    ss_camera_intent.putExtra("gender",gender);
                    ss_camera_intent.putExtra("custody",custody);
                    ss_camera_intent.putExtra("language",language);
                    ss_camera_intent.putExtra("password",password);

                    startActivity(ss_camera_intent);

                }


                // TOAST TO SHOW THE USER THAT REGISTRATION WAS SUCCESSFULLY //

            }
        });
    }

    public boolean validateString(String text)
    {return text.length()>0;}

    @Override
    protected void onStart() {
        super.onStart();
        if(mUser.getCurrentUser()!=null)
        {
            Toast.makeText(getApplicationContext(),"The user is logged with account = "+mUser.getCurrentUser().getEmail(),Toast.LENGTH_LONG).show();
            Intent login = new Intent(signup.this,login.class);
            startActivity(login);

        }else{
            Toast.makeText(getApplicationContext(),"Please input all credentials to log in on our end!!",Toast.LENGTH_LONG).show();

        }

    }
}
