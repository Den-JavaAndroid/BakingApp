package ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.denx7.ui.R;

import adapters.StepsAdapter;
import keys.IntentKeys;
import recipes.Recipe;
import recipes.Step;


public class DetailViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        FragmentManager fragmentManager = getSupportFragmentManager();

        DetailViewFragment detailViewFragment = new DetailViewFragment();

        Step step = getIntent().getParcelableExtra(IntentKeys.STEP);
        detailViewFragment.setStep(step);
//        setTitle(step.getId());

        fragmentManager.beginTransaction()
                .add(R.id.detail_view_fragment, detailViewFragment)
                .commit();





    }



}
