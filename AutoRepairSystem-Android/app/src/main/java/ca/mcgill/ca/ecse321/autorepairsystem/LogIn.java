package ca.mcgill.ca.ecse321.autorepairsystem;

import android.content.Context;
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

public class LogIn extends AppCompatActivity {
    //Our Two Input Fields
    private EditText username;
    private EditText password;

    //Helper function to start new activity and take us to SignUp Page
    public void createAccount(View v){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
        setContentView(R.layout.activity_signup);
    }


    //OnCreate we set layout and set unsername and password to users input
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

    }

//Collects parameters for backend call
    public void signIn(View v){
        RequestParams parameters = new RequestParams();
        parameters.add("username", username.getText().toString());
        parameters.add("password", password.getText().toString());


        HttpUtils.get("/signin", parameters, new JsonHttpResponseHandler(){
            @Override
            //on success we go to home page
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //clear fields for next login
                username.setText("");
                password.setText("");
                //Redirect to customer page
                Intent intent = new Intent(getApplicationContext(), CustomerHomeActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_customerhome);


            }

            @Override
            //on failiure we print out error message and empty the input boxes for next attempt
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                final TextView displayError = (TextView) findViewById(R.id.login_error);
                //clear for next login
                username.setText("");
                password.setText("");
                displayError.setText("");
                displayError.setText("Invalid Username Or Password");
            }
        });


        }

    }


