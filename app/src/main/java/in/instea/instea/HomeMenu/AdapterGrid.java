package in.instea.instea.HomeMenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import in.instea.instea.ModelClassRec;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import in.instea.instea.R;
public class AdapterGrid extends FirebaseRecyclerAdapter<ModelClassRec, AdapterGrid.myViewHolder> {

    private OnItemClickListener onItemClickListener;        //// Declare the OnItemClickListener

    // Define the OnItemClickListener interface
    public interface OnItemClickListener {
        void onItemClick(ModelClassRec model);
    }

    // Setter method to set the OnItemClickListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterGrid(@NonNull FirebaseRecyclerOptions<ModelClassRec> options) {
        super(options);
    }

    //    Bind Data to Adapters: In each adapter, bind the data from your Firebase query to the corresponding model class and populate the views accordingly.
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ModelClassRec model) {
        //getting name of the subject using model
        holder.subName.setText(model.getTitle());

        //getting icon of the subject using model
//      Picasso.get().load("https://example.com/image.jpg").into(imageView);
//      Glide.with(holder.itemView.getContext()).load(model.getIcon()).into(holder.subIcon);
        Glide.with(holder.iconImage.getContext())
                .load(model.getIcon())// Load the image from the specified URL
                .centerCrop()
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.google.firebase.appcheck.interop.R.drawable.common_google_signin_btn_text_disabled)
                .into(holder.iconImage);// Display the image in the ImageView

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the OnItemClickListener is set and call its onItemClick method
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(model);
                }
            }
        });
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_grid_subject, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImage;
        TextView subName;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.subject_icon);
            subName = itemView.findViewById(R.id.subject_name);
        }
    }
}
