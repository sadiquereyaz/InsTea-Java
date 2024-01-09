package in.instea.instea.BottomSheet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.instea.instea.ModelClassRec;
import in.instea.instea.R;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterRecViewBottomSheet extends FirebaseRecyclerAdapter<ModelClassRec, AdapterRecViewBottomSheet.myViewHolder> {

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
    public AdapterRecViewBottomSheet(@NonNull FirebaseRecyclerOptions<ModelClassRec> options) {
        super(options);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView unitName, creditName;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            unitName = itemView.findViewById(R.id.header);
            creditName = itemView.findViewById(R.id.subtitle);
        }
    }
    @Override
    protected void onBindViewHolder(@NonNull AdapterRecViewBottomSheet.myViewHolder holder, int position, @NonNull ModelClassRec model) {

        holder.unitName.setText(model.getTitle());        //getting name of the unit using model
        holder.creditName.setText(model.getSubtitle());        //getting creditName of the unit using model

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bottom_list, parent, false);
        return new myViewHolder(view);
    }
}
