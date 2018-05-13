package com.example.denx7.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;

import adapters.ReciepsAdapter;
import recipes.Recipe;
import retrofit.RestClient;


public class MainActivity extends AppCompatActivity implements ReciepsAdapter.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "click position " + position, Toast.LENGTH_SHORT).show();
    }
}
