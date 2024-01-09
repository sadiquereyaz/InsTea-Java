package in.instea.instea.HomeMenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import in.instea.instea.ModelClassRec;
import in.instea.instea.R;

public class AdapterRecViewFoodItem extends FirebaseRecyclerAdapter<ModelClassRec, AdapterRecViewFoodItem.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterRecViewFoodItem(@NonNull FirebaseRecyclerOptions<ModelClassRec> options) {
        super(options);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView icon;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.dish_name);
            price = itemView.findViewById(R.id.dish_price);
            icon = itemView.findViewById(R.id.dish_icon);
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterRecViewFoodItem.myViewHolder holder, int position, @NonNull ModelClassRec model) {
        holder.title.setText(model.getTitle());
        holder.price.setText("Rs. " +model.getSubtitle());
        Glide.with(holder.icon.getContext())
                .load(model.getIcon())
                .circleCrop()
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.google.firebase.appcheck.interop.R.drawable.common_google_signin_btn_text_disabled)
                .into(holder.icon);// Display the image in the ImageView
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_canteen, parent, false);
        return new myViewHolder(view);
    }
}
