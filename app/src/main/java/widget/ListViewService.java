package widget;


 import android.content.Intent;
         import android.util.Log;
         import android.widget.RemoteViewsService;
         
         
         
         

public class ListViewService extends RemoteViewsService {
 
         
    private static final String TAG = "widget";
 
         
    @Override
 

    public RemoteViewsFactory onGetViewFactory(Intent intent) {
         Log.d(TAG, "onGetViewFactory");
          return new ListRemoteViewFactory(this.getApplicationContext());
         
    }
 
}