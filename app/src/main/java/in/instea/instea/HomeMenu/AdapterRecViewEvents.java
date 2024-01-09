package in.instea.instea.HomeMenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import in.instea.instea.ModelClassRec;
import in.instea.instea.R;

public class AdapterRecViewEvents extends FirebaseRecyclerAdapter<ModelClassRec, AdapterRecViewEvents.myViewHolder> {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ModelClassRec model);
    }

    public void setOnItemClickListener(AdapterRecViewEvents.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterRecViewEvents(@NonNull FirebaseRecyclerOptions<ModelClassRec> options) {
        super(options);
    }
    class myViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle, publishDate, credit, from;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_events);
            credit = itemView.findViewById(R.id.credit_event);
            from = itemView.findViewById(R.id.event_date);
            publishDate = itemView.findViewById(R.id.event_publish_date);
            subtitle = itemView.findViewById(R.id.subtitle_events);
        }
    }
    @Override
    protected void onBindViewHolder(@NonNull AdapterRecViewEvents.myViewHolder holder, int position, @NonNull ModelClassRec model) {
        holder.title.setText(model.getTitle());        //getting name of the unit using model
        holder.subtitle.setText(model.getSubtitle());        //getting creditName of the unit using model

        String cred = model.getCredit();
        if (!cred.equals("")) {
            holder.credit.setText("~"+cred+" ");
        }else {
            holder.credit.setText("");
        }
        String eD = model.getFrom();
        if (!eD.equals("")) {
            holder.from.setText("Event Date: " + eD);
        }
        else {
            holder.from.setText("");
        }
        holder.publishDate.setText(model.getPublishDate());

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
    public AdapterRecViewEvents.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_events, parent, false);
        return new AdapterRecViewEvents.myViewHolder(view);
    }
}
