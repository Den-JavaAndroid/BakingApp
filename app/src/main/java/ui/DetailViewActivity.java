package ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denx7.ui.R;

import java.util.ArrayList;
import java.util.List;

import keys.IntentKeys;
import recipes.Step;


public class DetailViewActivity extends AppCompatActivity {

    private int stepIndex;
    private List<Step> steps;
    private Button nextButton;
    private Button previousButton;
    private Button currentStepTxv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = "detailViewFragment";
        DetailViewFragment detailViewFragment = (DetailViewFragment) fragmentManager.findFragmentByTag(tag);

        if (savedInstanceState == null) {
            steps = getIntent().getParcelableArrayListExtra(IntentKeys.STEPS);
            stepIndex = getIntent().getIntExtra(IntentKeys.STEP_INDEX, 0);
        } else {
            steps = savedInstanceState.getParcelableArrayList(IntentKeys.STEPS);
            stepIndex = savedInstanceState.getInt(IntentKeys.STEP_INDEX);
        }
        setContentView(R.layout.activity_detail_view);

        if (detailViewFragment == null) {
            detailViewFragment = new DetailViewFragment();

            detailViewFragment.setStep(steps.get(stepIndex));
            fragmentManager.beginTransaction()
                    .add(R.id.detail_view_fragment, detailViewFragment, tag)
                    .commit();
        }

        nextButton = findViewById(R.id.next_button);
        previousButton = findViewById(R.id.previous_button);
        currentStepTxv = findViewById(R.id.current_step);

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            nextButton.setVisibility(View.VISIBLE);
            previousButton.setVisibility(View.VISIBLE);
            currentStepTxv.setVisibility(View.VISIBLE);

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (stepIndex < steps.size() - 1) {
                        DetailViewFragment detailViewFragment = new DetailViewFragment();
                        detailViewFragment.setStep(steps.get(stepIndex + 1));
                        stepIndex++;
                        fragmentManager.beginTransaction()
                                .replace(R.id.detail_view_fragment, detailViewFragment)
                                .commit();
                        currentStepTxv.setText(stepIndex + "/" + (steps.size() - 1));

                    } else {
                        Toast.makeText(getApplicationContext(), "This is the last step", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (stepIndex > 1) {
                        DetailViewFragment detailViewFragment = new DetailViewFragment();
                        detailViewFragment.setStep(steps.get(stepIndex - 1));
                        stepIndex--;
                        fragmentManager.beginTransaction()
                                .replace(R.id.detail_view_fragment, detailViewFragment)
                                .commit();
                        currentStepTxv.setText(stepIndex + "/" + (steps.size() - 1));

                    } else {
                        Toast.makeText(getApplicationContext(), "This is the first step", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            currentStepTxv.setText(stepIndex + "/" + steps.size());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(IntentKeys.STEP_INDEX, stepIndex);
        outState.putParcelableArrayList(IntentKeys.STEPS, (ArrayList<? extends Parcelable>) steps);
    }


}
