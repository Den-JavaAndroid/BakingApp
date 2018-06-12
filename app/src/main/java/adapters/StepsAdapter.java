package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.denx7.ui.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import recipes.Recipe;
import recipes.Step;


public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private final List<Step> steps;
    private final Context context;
    private ItemClickListener mClickListener;


    public StepsAdapter(Context context, List<Step> steps, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.steps = steps;
        this.context = context;
        mClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.stepName.setText(i + ". " + steps.get(i).getShortDescription());
        if(steps.get(i).getThumbnailURL()!=null)
            holder.playArrow.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView stepName;
        final ImageView playArrow;

        public ViewHolder(View itemView) {
            super(itemView);
            stepName = itemView.findViewById(R.id.step_name);
            playArrow = itemView.findViewById(R.id.play_arrow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener !=null)
            mClickListener.onItemClick(steps.get(getAdapterPosition()));
        }

    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }




    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(Step step);
    }

}
