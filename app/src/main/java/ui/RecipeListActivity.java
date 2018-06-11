package ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.denx7.ui.R;

import adapters.ReciepsAdapter;
import keys.IntentKeys;
import recipes.Recipe;


public class RecipeListActivity extends AppCompatActivity implements ReciepsAdapter.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    public void onItemClick(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentKeys.RECIPE, recipe);
        final Intent passDataIntent = new Intent(this, StepsActivity.class);
        passDataIntent.putExtras(bundle);
        startActivity(passDataIntent);
    }

}
