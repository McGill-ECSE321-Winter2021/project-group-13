package ca.mcgill.ca.ecse321.autorepairsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import androidx.appcompat.app.AppCompatActivity;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LogIn extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private String user="";

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
                try {
                    //store user email locally to be passed to other activities
                    user = response.getString("username");
                    //Redirect to customer page
                    toCustomerPage();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                final TextView displayError = (TextView) findViewById(R.id.login);
                //clear text boxes for next login attempt
                username.setText("");
                password.setText("");
                displayError.append("Invalid Credentials");
            }
        });


        }

    public void toCustomerPage(){
        System.exit(0);
        //setContentView(R.layout.activity_login);
        Intent intent= new Intent(this, LogIn.class);
        startActivity(intent);
    }


    }


