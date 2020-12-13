package com.example.wannad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class TestActivity extends AppCompatActivity {

    String strNickname, strProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvNickname = findViewById(R.id.tvNickname);
        ImageView ivProfile = findViewById(R.id.ivProfile); //전 강의의 tvProfile 대신 추가된 부분. ImageView 선언.

        Intent intent = getIntent();
        strNickname = intent.getStringExtra("name");
        strProfile = intent.getStringExtra("profile");

        tvNickname.setText(strNickname);
        Glide.with(this).load(strProfile).into(ivProfile); //전 강의의 tvProfile.setText(strProfile); 대신 추가된 부분. ImageView에 strProfile의 URL에 해당하는 이미지를 표시해준다.

    }
}