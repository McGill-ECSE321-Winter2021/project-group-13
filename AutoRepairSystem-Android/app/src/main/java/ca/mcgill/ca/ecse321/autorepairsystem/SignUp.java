package ca.mcgill.ca.ecse321.autorepairsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import androidx.appcompat.app.AppCompatActivity;
import com.loopj.android.http.RequestParams;


import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignUp extends AppCompatActivity {
    //our four input fields
    private EditText password;
    private EditText email;
    private EditText name;
    private EditText username;


    @Override
    //on create we set view and set users input to parameters
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username=findViewById(R.id.usernamesignup);
        password=findViewById(R.id.passwordsignup);
        email=findViewById(R.id.email);
        name=findViewById(R.id.name);

    }

    //we get the parameters for backend call
    public void signUp(View v){
        RequestParams pr = new RequestParams();
        pr.add("password", password.getText().toString());
        pr.add("name", name.getText().toString());
        pr.add("email", email.getText().toString());


        HttpUtils.post("/customers/"+(username.getText().toString()), pr, new JsonHttpResponseHandler(){
            @Override
            //on success we go to customers home page
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //clear fields for next login
                username.setText("");
                password.setText("");
                email.setText("");
                name.setText("");
                //Redirect to customer page
                Intent intent = new Intent(getApplicationContext(), CustomerHomeActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_customerhome);


            }
            @Override
            //on failiure we print out error message and empty boxes for next attempt
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                final TextView displayError = (TextView) findViewById(R.id.login_error);
                //clear for next login
                username.setText("");
                password.setText("");
                email.setText("");
                name.setText("");
                displayError.setText("");
                displayError.setText("Please Fill Out All Fields");
            }
        });


    }


}


