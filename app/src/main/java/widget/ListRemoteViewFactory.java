package widget;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.example.denx7.ui.R;

import java.util.ArrayList;
import java.util.List;

import recipes.Ingredient;
import ui.StepsActivity;

public class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "widget";
    private Context mContext;
    private List<Ingredient> modelList = new ArrayList<>();

    public ListRemoteViewFactory(Context context) {
        Log.d(TAG, "ListRemoteViewFactory");
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged");
        modelList = StepsActivity.ingredientList;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount");
        if (modelList == null) return 0;
        return modelList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Log.d(TAG, "getViewAt");
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_view_item);
        remoteViews.setTextViewText(R.id.widget_list_view_text_ingredient, modelList.get(i).getIngredient());
        remoteViews.setTextViewText(R.id.widget_list_view_text_quantity, modelList.get(i).getQuantity()+"");
        remoteViews.setTextViewText(R.id.widget_list_view_text_measure, modelList.get(i).getMeasure());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
