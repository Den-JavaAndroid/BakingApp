package ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.denx7.ui.R;

import java.util.ArrayList;
import java.util.List;

import adapters.ReciepsAdapter;
import adapters.StepsAdapter;
import keys.IntentKeys;
import recipes.Recipe;
import recipes.Step;


public class StepsActivity extends AppCompatActivity implements StepsAdapter.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        FragmentManager fragmentManager = getSupportFragmentManager();

        StepsFragment stepsFragment = new StepsFragment();

        Recipe recipe = getIntent().getParcelableExtra(IntentKeys.RECIPE);
        stepsFragment.setRecipe(recipe);
        setTitle(recipe.getName());

        fragmentManager.beginTransaction()
                .add(R.id.steps_fragment, stepsFragment)
                .commit();





    }


    @Override
    public void onItemClick(List<Step> steps, int intex) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IntentKeys.STEPS, (ArrayList<? extends Parcelable>) steps);
        bundle.putInt(IntentKeys.STEP_INDEX, intex);
        final Intent passDataIntent = new Intent(this, DetailViewActivity.class);
        passDataIntent.putExtras(bundle);
        startActivity(passDataIntent);
    }
}
