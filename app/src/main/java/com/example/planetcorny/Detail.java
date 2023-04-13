package com.example.planetcorny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.planetcorny.utilities.Constants;
import com.example.planetcorny.utilities.PreferenceManager;

import render.animations.*;

public class Detail extends AppCompatActivity {

    TextView tvNameDetail, tvDescPlanet;
    Button btnMoreDetail;
    ImageView imagePlanetDetail;
    DbHelper dbHelper = new DbHelper(this);
    Render render = new Render(Detail.this);
    PreferenceManager preferenceManager;

    Items planet = new Items();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        preferenceManager = new PreferenceManager(getApplicationContext());

        map();
        listeners();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id_planet = extras.getInt("pos");
            planet = dbHelper.getPlanet(id_planet + "");
            int[] images = Constants.imagePlanets;

            tvNameDetail.setText(planet.getName());
            imagePlanetDetail.setImageResource(planet.getImage());
            tvDescPlanet.setText(planet.getDesc());

            render.setAnimation(new Zoom().In(imagePlanetDetail));
            render.start();
        }
    }

    public void showAlertDialogSignIn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
        builder.setTitle("Notification!");
        builder.setMessage("You need to log in to see more detail about this planet!");
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setCancelable(true);
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                preferenceManager.clear();
                Intent intent = new Intent(Detail.this, SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public void listeners() {
        btnMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isSigned = preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN);
                String email = preferenceManager.getString(Constants.KEY_USER_EMAIL);
                if (isSigned == true && !email.equals("")) {
                    Intent intent = new Intent(Detail.this, DetailDescriptionActivity.class);
                    intent.putExtra("desc_detail", planet.getDescDetail());
                    startActivity(intent);
                } else {
                    showAlertDialogSignIn();
                }
            }
        });
    }

    public void map() {
        tvNameDetail = findViewById(R.id.tv_name_detail);
        btnMoreDetail = findViewById(R.id.buttonMoreDetail);
        imagePlanetDetail = findViewById(R.id.image_planet_detail);
        tvDescPlanet = findViewById(R.id.tv_desc);
    }
}