package com.watools.directmessage;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hbb20.CountryCodePicker;
import com.onesignal.OneSignal;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {
    EditText whatsapp_no, msg;
    Button btnSend;
    CountryCodePicker ccp;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        whatsapp_no = findViewById(R.id.whatsapp_no);
        msg = findViewById(R.id.msg);
        btnSend = findViewById(R.id.btnSend);
        ccp = findViewById(R.id.ccp);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        Admob();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                method();
            }
        });

    }

    private void Admob() {
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void method() {
        String name = ccp.getSelectedCountryCode();
        String mobile = whatsapp_no.getText().toString().trim();
        String text = msg.getText().toString().trim();

        String Whatsapp_No = name + "" + mobile;
        if (mobile.isEmpty()) {
            whatsapp_no.setError("Enter the Mobile");
            whatsapp_no.requestFocus();
            return;
        }
        try {
            Uri uri = Uri.parse("whatsapp://send?phone=" + Whatsapp_No + "&text=" + text);
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "WhatsApp not installed.", Toast.LENGTH_SHORT).show();
        }

    }




}


