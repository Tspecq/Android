/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

  public void showUserList(){
    Intent intent = new Intent(getApplicationContext(), requestList.class);
    startActivity(intent);
  }

  @Override
  public void onClick(View view) {

    if(view.getId() == R.id.backgroundRelativeLayout || view.getId() == R.id.logoImageView){

      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle("Instagram Basic Clone");


    TextView username0 = (TextView) findViewById(R.id.userName);
    RelativeLayout layout = (RelativeLayout) findViewById(R.id.backgroundRelativeLayout);
    ImageView logo = (ImageView) findViewById(R.id.logoImageView);


    layout.setOnClickListener(this);
    logo.setOnClickListener(this);

    if(ParseUser.getCurrentUser() != null){
      showUserList();

    }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());


  }

  public void signUp(View view){
    TextView username0 = (TextView) findViewById(R.id.userName);
    TextView password0 = (TextView) findViewById(R.id.password);

    String username = username0.getText().toString();
    String password = password0.getText().toString();

    if(username.equals("") || password.equals("")){
      Toast.makeText(getApplicationContext(), "Please provide credentials",Toast.LENGTH_SHORT).show();


    }else{

    ParseUser user = new ParseUser();
    user.setUsername(username);
    user.setPassword(password);

    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if(e == null){
          Log.i("Info","Success");
          Toast.makeText(getApplicationContext(), "Sign Up Successful",Toast.LENGTH_SHORT).show();
          showUserList();

        } else {
          Log.i("Info","Failure");
          Toast.makeText(getApplicationContext(), "Failure to sign up",Toast.LENGTH_SHORT).show();
        }
      }
    });}}

  public void logIn(View view){

    TextView username0 = (TextView) findViewById(R.id.userName);
    TextView password0 = (TextView) findViewById(R.id.password);
    String username = username0.getText().toString();
    String password = password0.getText().toString();

    if(username.equals("") || password.equals("")) {
      Toast.makeText(getApplicationContext(), "Please provide credentials", Toast.LENGTH_SHORT).show();
    }else{

    ParseUser.logInInBackground(username, password, new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if(e == null){
          Log.i("Info","Success");
          Toast.makeText(getApplicationContext(), "Log In Successful",Toast.LENGTH_SHORT).show();
          showUserList();


        } else {
          Log.i("Info","Failure "+e.toString());
          Toast.makeText(getApplicationContext(), "Failure to log in",Toast.LENGTH_SHORT).show();
        }
      }
    });

  }}

  @Override
  public boolean onKey(View view, int i, KeyEvent keyEvent) {

    if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN);
    signUp(view);
    return false;
  }
}