package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denx7.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import recipes.Recipe;


public class ReciepsAdapter extends RecyclerView.Adapter<ReciepsAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private final ArrayList<Recipe> recipes;
    private final Context context;
    private ItemClickListener mClickListener;


    public ReciepsAdapter(Context context, ArrayList<Recipe> recipes, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.recipes = recipes;
        this.context = context;
        mClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        String imageUrl = recipes.get(i).getImage();

        //set default if empty url
        if(!imageUrl.equals(""))
//            imageUrl = "http://placehold.it/120x120&text=image1";

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.recipe_placeholder)
                .error(R.drawable.not_found)
                .into(holder.recipeView);

        holder.recipeName.setText(recipes.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView recipeView;
        final TextView recipeName;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeView = itemView.findViewById(R.id.recipe_image);
            recipeName = itemView.findViewById(R.id.recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener !=null)
            mClickListener.onItemClick(getAdapterPosition());
        }

    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }




    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(int position);
    }

}
