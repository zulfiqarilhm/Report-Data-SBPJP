package com.example.datareports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView pleaides, spot, radar, landsat, modis, noaa, npp, jpss, storage;

    ImageView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pleaides = (CardView) findViewById(R.id.pleaides);
        spot = (CardView) findViewById(R.id.spot);
        radar = (CardView) findViewById(R.id.radar);
        landsat = (CardView) findViewById(R.id.landsat);
        modis = (CardView) findViewById(R.id.modis);
        noaa = (CardView) findViewById(R.id.noaa);
        npp = (CardView) findViewById(R.id.npp);
        jpss = (CardView) findViewById(R.id.jpss);
        storage = (CardView) findViewById(R.id.storage);
        about = (ImageView) findViewById(R.id.abtt);


        pleaides.setOnClickListener(this);
        spot.setOnClickListener(this);
        radar.setOnClickListener(this);
        landsat.setOnClickListener(this);
        modis.setOnClickListener(this);
        noaa.setOnClickListener(this);
        npp.setOnClickListener(this);
        jpss.setOnClickListener(this);
        storage.setOnClickListener(this);
        about.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent i ;

        switch (v.getId()) {
            case R.id.pleaides : i = new Intent(this,Pleaides.class);startActivity(i); break;
            case R.id.spot : i =  new Intent(this,Spot.class);startActivity(i); break;
            case R.id.radar : i = new Intent(this,Radar.class);startActivity(i); break;
            case R.id.landsat : i = new Intent(this,Landsat.class);startActivity(i);break;
            case R.id.modis : i = new Intent(this,Modis.class);startActivity(i);break;
            case R.id.noaa : i = new Intent(this,Noaa.class);startActivity(i);break;
            case R.id.npp : i = new Intent(this,Npp.class);startActivity(i);break;
            case R.id.jpss: i = new Intent(this,Jpss.class);startActivity(i);break;
            case R.id.storage: i = new Intent(this,Storage.class);startActivity(i);break;
            case R.id.abtt: i = new Intent (this,aboutt.class);startActivity(i);break;
            default:break;

        }
    }
}
