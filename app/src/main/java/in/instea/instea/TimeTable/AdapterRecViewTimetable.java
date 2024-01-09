package in.instea.instea.TimeTable;

import android.graphics.Color;
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
public class AdapterRecViewTimetable extends FirebaseRecyclerAdapter<ModelClassRec, AdapterRecViewTimetable.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterRecViewTimetable(@NonNull FirebaseRecyclerOptions<ModelClassRec> options) {
        super(options);
    }
    class myViewHolder extends RecyclerView.ViewHolder {
        TextView subName, venue, timeFrom, timeTo, teacher, classType, garbageTxt;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            subName = itemView.findViewById(R.id.subject_time_table);
            venue = itemView.findViewById(R.id.venue_txt);
            timeFrom = itemView.findViewById(R.id.time_from);
            timeTo = itemView.findViewById(R.id.time_to);
            teacher = itemView.findViewById(R.id.teacher);
            classType = itemView.findViewById(R.id.class_type);
            garbageTxt = itemView.findViewById(R.id.garbage_txt);
        }
    }
    @Override
    protected void onBindViewHolder(@NonNull AdapterRecViewTimetable.myViewHolder holder, int position, @NonNull ModelClassRec model) {
        holder.subName.setText(model.getSub());
        holder.venue.setText(model.getVenue());
        holder.timeFrom.setText(model.getFrom());
        holder.timeTo.setText(model.getTo());
        holder.teacher.setText(model.getTeacher());
        holder.classType.setText(model.getType());
        holder.garbageTxt.setText(model.getShortSub());

        // Set background color based on class type
        String classType = model.getType();
        if (classType.equals("LAB ")) {
            holder.classType.setBackgroundColor(Color.parseColor("#900C3F")); // Set your desired color here
        } else if (classType.equals("THEORY ")) {
            holder.classType.setBackgroundColor(Color.parseColor("#557A46")); // Set another color
        } else {
            holder.classType.setBackgroundColor(Color.parseColor("#a04029")); // Default color
        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_table_row, parent, false);
        return new myViewHolder(view);
    }
}
