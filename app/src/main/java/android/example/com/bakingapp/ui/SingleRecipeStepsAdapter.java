package android.example.com.bakingapp.ui;

import android.content.Context;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleRecipeAdapter extends RecyclerView.Adapter<SingleRecipeAdapter.SingleRecipeViewHolder> {

    Context mContext;
    private static SimpleRecipe mSimpleRecipe;
    private OnStepListener mOnStepListener;

    public interface OnStepListener {
        void onStepClick(int position);
    }

    public static void setRecipe(SimpleRecipe simpleRecipe){
        mSimpleRecipe = simpleRecipe;
    }

    public SingleRecipeAdapter(Context context, OnStepListener onStepListener) {
        mContext = context;
        mOnStepListener = onStepListener;
    }

    @NonNull
    @Override
    public SingleRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.steps_item, parent, false);
        return new SingleRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleRecipeViewHolder holder, int position) {
        holder.description.setText(getDescription(position));
    }

    private String getDescription(int position){

        if(position > 0){
            int stepNr = position + 1;
            return position + ". " + mSimpleRecipe.getSteps().get(position).getShortDescription();
        }
        return mSimpleRecipe.getSteps().get(position).getShortDescription();
    }

    @Override
    public int getItemCount() {
        if(mSimpleRecipe == null) return 0;
        return mSimpleRecipe.getSteps().size();
    }

    public class SingleRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView description;

        public SingleRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.step_description_text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnStepListener.onStepClick(getAdapterPosition());
        }
    }
}
