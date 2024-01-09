package in.instea.instea;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.ViewHolder> {
    private String[] universityName;
    private OnUniversityClickListener onUniversityClickListener;

    public UniversityAdapter(String[] universityName, OnUniversityClickListener onUniversityClickListener) {
        this.universityName = universityName;
        this.onUniversityClickListener = onUniversityClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityAdapter.ViewHolder holder, int position) {
        final String universityName = this.universityName[position];
        holder.universityNameTextView.setText(universityName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUniversityClickListener != null) {
                    onUniversityClickListener.onUniversityClick(universityName);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (universityName != null) {
            return universityName.length;
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView universityNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            universityNameTextView = itemView.findViewById(R.id.dialog_option_name);
        }
    }
    public interface OnUniversityClickListener {
        void onUniversityClick(String universityName);
    }
}
