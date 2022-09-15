package com.dcinspirations.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Validation extends AppCompatActivity {
    EditText  status, pass;
    Button bt;
    String selection;
    TextView at;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        TabLayout tabs = findViewById(R.id.tabs);
        tb = findViewById(R.id.toolbar);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        selection = "login";
        status = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        bt = findViewById(R.id.action);

        String state = getIntent().getExtras().getString("state");
        if (state.equalsIgnoreCase("login")) {
            tabs.addTab(tabs.newTab().setText("Login"), true);
            tabs.addTab(tabs.newTab().setText("Register"));
            selection = "login";
        } else {
            tabs.addTab(tabs.newTab().setText("Login"));
            tabs.addTab(tabs.newTab().setText("Register"), true);
            selection = "register";
        }


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selection.equalsIgnoreCase("login")) {

                    SignIn();
                } else {

                    SignUp();
                }
            }
        });
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equalsIgnoreCase("Login")) {
                    bt.setText("Login");
                    selection = "Login";
                } else {
                    bt.setText("Register");
                    selection = "Register";
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void SignIn() {
        String emtext = status.getText().toString().trim();
        String ptext = pass.getText().toString().trim();
        if (emtext.isEmpty()) {
            status.requestFocus();
            status.setError("Enter Username");
            return;
        }
        if (ptext.isEmpty()) {
            pass.requestFocus();
            pass.setError("Enter Password");
            return;
        }

        bt.setText("Logging In...");

        boolean b = new Sp(getApplicationContext()).checkDetails(emtext,ptext);
        if(b){
            new Sp(getApplicationContext()).setLoggedIn(true);
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }else{
            Toast.makeText(this, "Incorrect Username or password", Toast.LENGTH_SHORT).show();
        }

    }

    private void SignUp() {
        final String emtext = status.getText().toString().trim();
        String ptext = pass.getText().toString().trim();

        if (emtext.isEmpty()) {
            status.requestFocus();
            status.setError("Enter Status");
            return;
        }
        if (ptext.isEmpty()) {
            pass.requestFocus();
            pass.setError("Enter Password");
            return;
        }
        bt.setText("Creating User...");
        new Sp(getApplicationContext()).setLoggedIn(true);
        new Sp(getApplicationContext()).setDetails(emtext,ptext);
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

}