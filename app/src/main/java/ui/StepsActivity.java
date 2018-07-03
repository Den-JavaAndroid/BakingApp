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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        FragmentManager fragmentManager = getSupportFragmentManager();
        StepsFragment stepsFragment = new StepsFragment();

        if(savedInstanceState == null) {
            recipe = getIntent().getParcelableExtra(IntentKeys.RECIPE);
        }else {
            recipe = savedInstanceState.getParcelable(IntentKeys.RECIPE);
        }
        stepsFragment.setRecipe(recipe);
        setTitle(recipe.getName());

        fragmentManager.beginTransaction()
                .add(R.id.steps_fragment, stepsFragment)
                .commit();

        tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            DetailViewFragment detailViewFragment = new DetailViewFragment();
            detailViewFragment.setStep(recipe.getSteps().get(0));
            fragmentManager.beginTransaction()
                    .add(R.id.detail_view_fragment, detailViewFragment)
                    .commit();
        }


    }


    @Override
    public void onItemClick(List<Step> steps, int intex) {

        tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            final FragmentManager fragmentManager = getSupportFragmentManager();

            DetailViewFragment detailViewFragment = new DetailViewFragment();
            detailViewFragment.setStep(steps.get(intex));
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_view_fragment, detailViewFragment)
                    .commit();
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(IntentKeys.STEPS, (ArrayList<? extends Parcelable>) steps);
            bundle.putInt(IntentKeys.STEP_INDEX, intex);
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
    }

}
