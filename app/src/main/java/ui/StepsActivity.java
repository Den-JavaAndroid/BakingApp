package ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.denx7.ui.R;

import java.util.ArrayList;
import java.util.List;

import adapters.StepsAdapter;
import keys.IntentKeys;
import recipes.Ingredient;
import recipes.Recipe;
import recipes.Step;
import widget.IngredientListService;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class StepsActivity extends AppCompatActivity implements StepsAdapter.ItemClickListener {
    public static String recipeTitle = "Recipe Title";
    public static List<Ingredient> ingredientList = new ArrayList<>();

    private Recipe recipe;
    private boolean tabletSize;

    private int stepIndex;
    FragmentManager fragmentManager = getSupportFragmentManager();
    private String tag = "stepsFragment";
    private String tagDetailViewFragment = "detailViewFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);



        StepsFragment stepsFragment = (StepsFragment) fragmentManager.findFragmentByTag(tag);

        if(savedInstanceState == null) {
            recipe = getIntent().getParcelableExtra(IntentKeys.RECIPE);
            stepIndex= 0;
        }else {
            recipe = savedInstanceState.getParcelable(IntentKeys.RECIPE);
            stepIndex = savedInstanceState.getInt(IntentKeys.STEP_INDEX);
        }
        setTitle(recipe.getName());

        if(stepsFragment == null) {
            stepsFragment = new StepsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_fragment, stepsFragment, tag)
                    .commit();
        }
        stepsFragment.setRecipe(recipe);

        tabletSize = getResources().getBoolean(R.bool.isTablet);

        if (tabletSize) {

            DetailViewFragment detailViewFragment = (DetailViewFragment) fragmentManager.findFragmentByTag(tagDetailViewFragment);
            if(detailViewFragment == null){
                detailViewFragment = new DetailViewFragment();
                detailViewFragment.setStep(recipe.getSteps().get(stepIndex));
                fragmentManager.beginTransaction()
                        .replace(R.id.detail_view_fragment, detailViewFragment, tagDetailViewFragment)
                        .commit();
            }

        }


    }


    @Override
    public void onItemClick(List<Step> steps, int index) {

        tabletSize = getResources().getBoolean(R.bool.isTablet);
        stepIndex = index;
        if (tabletSize) {


            DetailViewFragment detailViewFragment = null;
            if(detailViewFragment == null){
                detailViewFragment = new DetailViewFragment();
                detailViewFragment.setStep(recipe.getSteps().get(stepIndex));
                fragmentManager.beginTransaction()
                        .replace(R.id.detail_view_fragment, detailViewFragment)
                        .commit();
            }
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(IntentKeys.STEPS, (ArrayList<? extends Parcelable>) steps);
            bundle.putInt(IntentKeys.STEP_INDEX, index);
            final Intent passDataIntent = new Intent(this, DetailViewActivity.class);
            passDataIntent.putExtras(bundle);
            startActivity(passDataIntent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_recipe_detail, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_add) {
            recipeTitle = recipe.getName();
            ingredientList = recipe.getIngredients();
            IngredientListService.startActionChangeIngredientList(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(IntentKeys.RECIPE, recipe);
        outState.putInt(IntentKeys.STEP_INDEX, stepIndex);
    }

}
