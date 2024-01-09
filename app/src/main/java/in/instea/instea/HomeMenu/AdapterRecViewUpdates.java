package in.instea.instea.HomeMenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.instea.instea.ModelClassRec;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import in.instea.instea.R;
public class AdapterRecViewUpdates extends FirebaseRecyclerAdapter<ModelClassRec, AdapterRecViewUpdates.myViewHolder> {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ModelClassRec model);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterRecViewUpdates(@NonNull FirebaseRecyclerOptions<ModelClassRec> options) {
        super(options);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView publishDate, title, subtitle, credit;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            publishDate = itemView.findViewById(R.id.date_updates_publish);
            title = itemView.findViewById(R.id.title_update);
            subtitle = itemView.findViewById(R.id.subtitle_update);
            credit = itemView.findViewById(R.id.update_credit);
        }
    }
    @Override
    protected void onBindViewHolder(@NonNull AdapterRecViewUpdates.myViewHolder holder, int position, @NonNull ModelClassRec model) {

        holder.title.setText(model.getTitle());        //getting name of the unit using model
        holder.subtitle.setText(model.getSubtitle());        //getting creditName of the unit using model
        holder.publishDate.setText(model.getPublishDate());

        String cred = model.getCredit();
        if (cred != null){
            holder.credit.setText("Admin: "+cred+" ");
        }


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_updates, parent, false);
        return new myViewHolder(view);
    }
}
