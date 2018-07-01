package widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.denx7.ui.R;

import ui.StepsActivity;

public class IngredientListService extends IntentService {

    public static final String ACTION_CHANGE_INGREDIENT_LIST = "widget.action.change_list";
    private static final String TAG = "widget";


    public IngredientListService() {
        super("IngredientListService");

    }


    @Override


    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHANGE_INGREDIENT_LIST.equals(action)) {
                handleActionChangeIngredientList();

            }

        }

    }


    public static void startActionChangeIngredientList(Context context) {
        Log.d(TAG, "startActionChangeIngredientList");
        Intent intent = new Intent(context, IngredientListService.class);
        intent.setAction(ACTION_CHANGE_INGREDIENT_LIST);
        context.startService(intent);

    }


    private void handleActionChangeIngredientList() {
        Log.d(TAG, "handleActionChangeIngredientList");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeIngredientWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        RecipeIngredientWidgetProvider.updateIngredientWidgets(this, appWidgetManager, StepsActivity.recipeTitle, appWidgetIds);

    }


}