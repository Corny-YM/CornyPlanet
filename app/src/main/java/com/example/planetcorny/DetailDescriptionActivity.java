package com.example.planetcorny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailDescriptionActivity extends AppCompatActivity {
    TextView tvDescDetail;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_description);
        map();
        listeners();
        loadData();
    }

    public void loadData() {
        Intent intent = getIntent();
        String desc_detail = intent.getStringExtra("desc_detail");
        tvDescDetail.setText(desc_detail);
    }

    public void listeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailDescriptionActivity.this.onBackPressed();
                finish();
            }
        });
    }

    public void map() {
        tvDescDetail = findViewById(R.id.tv_desc_detail);
        btnBack = findViewById(R.id.buttonBack);
    }
}