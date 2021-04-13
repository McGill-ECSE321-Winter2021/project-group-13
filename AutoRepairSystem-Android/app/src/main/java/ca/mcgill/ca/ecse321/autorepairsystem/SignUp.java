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

public class SignUp extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private EditText email;
    private EditText name;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        name=findViewById(R.id.name);

    }

    public void signUp(View v){
        RequestParams pr = new RequestParams();
        pr.add("password", password.getText().toString());
        pr.add("email", email.getText().toString());
        pr.add("name", name.getText().toString());
        final TextView username = (TextView) findViewById(R.id.username);

        HttpUtils.post("/customers/"+username, pr, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //clear fields for next login
                username.setText("");
                password.setText("");
                email.setText("");
                name.setText("");
                //Redirect to customer page
                setContentView(R.layout.activity_login);


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                final TextView displayError = (TextView) findViewById(R.id.login_error);
                //clear for next login
                username.setText("");
                password.setText("");
                email.setText("");
                name.setText("");
                displayError.append("Invalid Credentials");
            }
        });


    }


}


