package com.example.planetcorny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.planetcorny.utilities.Constants;
import com.example.planetcorny.utilities.PreferenceManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tvDisplayName;
    ItemAdapter adapter;
    RecyclerView recyclerView;
    DbHelper dbHelper = new DbHelper(this);

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceManager = new PreferenceManager(getApplicationContext());

        map();
        listeners();

        // add data
        addValueToDB();

        init();

        ArrayList<Items> itemArrayList = (ArrayList<Items>) dbHelper.getAll();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(itemArrayList, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public void addValueToDB() {
        int[] arr_image = Constants.imagePlanets;
        String[] arr_name = Constants.namePlanets;
        String[] arr_desc = Constants.descPlanets;
        String[] arr_desc_detail = Constants.descDetailPlanet;

        Boolean isExisted = dbHelper.checkExistedPlanet("0");
        if(isExisted) return;;

        for (int i = 0; i < arr_image.length; i++) {
            dbHelper.addPlanet(new Items(i, arr_name[i], arr_image[i], arr_desc[i], arr_desc_detail[i]));
        }
    }

    public void init() {
        Boolean isSigned = preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN);
        String email = preferenceManager.getString(Constants.KEY_USER_EMAIL);
        if (isSigned == true && !email.equals("")) {
            tvDisplayName.setText("User: " + preferenceManager.getString(Constants.KEY_USER_NAME));
            return;
        }
        tvDisplayName.setText("User: Guest");
    }

    public void showAlertDialogLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Log out!");
        builder.setMessage("Are you sure you want to log out?");
        builder.setIcon(R.drawable.ic_logout);
        builder.setCancelable(true);
        builder.setPositiveButton("Log out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                preferenceManager.clear();
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
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
        tvDisplayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogLogOut();
            }
        });
    }

    public void map() {
        recyclerView = findViewById(R.id.recycler_view);
        tvDisplayName = findViewById(R.id.tv_displayName);
    }
}