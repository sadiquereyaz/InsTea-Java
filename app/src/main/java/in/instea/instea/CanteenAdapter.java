package in.instea.instea;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CanteenAdapter extends RecyclerView.Adapter<CanteenAdapter.ViewHolder> {

    private String[] canteenName;
    private OnCanteenClickListener onCanteenClickListener;

    public CanteenAdapter(String[] canteenName, OnCanteenClickListener onCanteenClickListener) {
        this.canteenName = canteenName;
        this.onCanteenClickListener = onCanteenClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String canteenName = this.canteenName[position];
        holder.canteenNameTextView.setText(canteenName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCanteenClickListener != null) {
                    onCanteenClickListener.onCanteenClick(canteenName);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return canteenName.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView canteenNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            canteenNameTextView = itemView.findViewById(R.id.dialog_option_name);
        }
    }

    public interface OnCanteenClickListener {
        void onCanteenClick(String canteenName);
    }
}
