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

public class LogIn extends AppCompatActivity {
    private EditText username;
    private EditText password;

    public void createAccount(View v){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
        setContentView(R.layout.activity_signup);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

    }


    public void signIn(View v){
        RequestParams parameters = new RequestParams();
        parameters.add("username", username.getText().toString());
        parameters.add("password", password.getText().toString());

        HttpUtils.get("/signin", parameters, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //clear fields for next login
                username.setText("");
                password.setText("");
                //Redirect to customer page
                setContentView(R.layout.activity_login);
            }

            @Override
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


