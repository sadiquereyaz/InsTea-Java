package in.instea.instea;

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

import in.instea.instea.HomeMenu.AdapterGrid;

public class AdapterAboutRec extends FirebaseRecyclerAdapter<ModelClassRec, AdapterAboutRec.myViewHolder>{

    private AdapterAboutRec.OnItemClickListener onItemClickListener;        //// Declare the OnItemClickListener

    // Define the OnItemClickListener interface
    public interface OnItemClickListener {
        void onItemClick(ModelClassRec model);
    }

    // Setter method to set the OnItemClickListener
    public void setOnItemClickListener(AdapterAboutRec.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterAboutRec(@NonNull FirebaseRecyclerOptions<ModelClassRec> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull AdapterAboutRec.myViewHolder holder, int position, @NonNull ModelClassRec model) {
        //getting name of the subject using model
        holder.name.setText(model.getTitle());
        holder.position.setText(model.getSubtitle());
        holder.bio.setText(model.getSubtitle1());
        holder.linkName.setText(model.getSubtitle2());
        String link = model.getNlink();

        //getting icon of the subject using model
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
    public AdapterAboutRec.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_about, parent, false);
        return new AdapterAboutRec.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImage;
        TextView name, position, bio, linkName;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            position = itemView.findViewById(R.id.position);
            bio = itemView.findViewById(R.id.bio);
            linkName = itemView.findViewById(R.id.linkInAcc);
        }
    }
}