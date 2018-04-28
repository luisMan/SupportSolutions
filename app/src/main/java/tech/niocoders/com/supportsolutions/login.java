package tech.niocoders.com.supportsolutions;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity{

    // VARIABLES //
    private ImageView logo;
    private EditText  email;
    private EditText  password;
    private TextView  email_textview;
    private TextView  password_textview;
    private Button    btn;

    // CONTEXT VARIABLE //
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // INITIALIZING THE VARIABLES //
        logo = findViewById(R.id.img_logo);
        email = findViewById(R.id.email_editText);
        password = findViewById(R.id.password_editText);
        email_textview = findViewById(R.id.email_text);
        password_textview = findViewById(R.id.password_text);
        btn = findViewById(R.id.login_btn);

        // INITIALIZING THE CONTEXT //
        context = getApplicationContext();

        // SETTING THE BUTTON LISTENER TO BE ABLE TO LOGIN //
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // SHOWING THE USER A TOAST FOR THEM TO KNOW THAT THEY LOGIN //
                Toast.makeText(context, "LOGIN SUCCESSFULLY", Toast.LENGTH_SHORT).show();

            }
        });
    }

    // METHOD FOR THE ACTIVITY TO LOGIN //
    public void login() {

        // CREATING NEW INTENT FOR THE ACTIVITY //
//        Intent intent = new Intent(this, class name);
    }
}
