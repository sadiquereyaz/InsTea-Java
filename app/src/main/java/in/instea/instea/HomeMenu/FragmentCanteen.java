package in.instea.instea.HomeMenu;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.instea.instea.CanteenAdapter;
import in.instea.instea.ModelClassRec;
import in.instea.instea.R;

public class FragmentCanteen extends Fragment {

    //    RecyclerView recyclerView;
    private AdapterRecViewFoodItem adapterRecViewFoodItem;
    //    private Button btnDialog;
    //    TextInputLayout canteenLayout;
    private TextInputEditText canteen;
    //    private String[] canteenName = {"FET Canteen", "Castro Cafe"};
    private String[] canteenName;
    DatabaseReference canteenRef;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private AlertDialog dialog; // Declare the dialog variable here

    //    Spinner
    public FragmentCanteen() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_canteen, container, false);

//        btnDialog = view.findViewById(R.id.showDialogButton);
        canteen = view.findViewById(R.id.textInputEditTextCanteen);
//        canteenLayout = view.findViewById(R.id.textInputLayoutCanteen);
        RecyclerView recyclerView = view.findViewById(R.id.rec_canteen_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(null);

        // Clear the focus from the TextInputEditText
        canteen.clearFocus();

        preferences = requireActivity().getSharedPreferences("USER_DATA", MODE_PRIVATE);
        editor = preferences.edit();

        // Retrieve data from SharedPreferences
        String FromSharedPrefUniversity = preferences.getString("university", "Aspirant");      //the second is a default value that will be returned if the "university" key is not found in the SharedPreferences file. if the value is string, only then it wil be under double quote
        String FromSharedPrefCanteen = preferences.getString("canteen", "FET Canteen");

        canteenRef = FirebaseDatabase.getInstance().getReference("canteen").child("Jamia Millia Islamia");

        loadCanteenNames();

        canteen.setText(FromSharedPrefCanteen);

        // Set click listener on the TextInputEditText
        canteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCanteenDialog();
//                Toast.makeText(getContext(), "jdfs", Toast.LENGTH_SHORT).show();
            }

        });

        FirebaseRecyclerOptions<ModelClassRec> options =
                new FirebaseRecyclerOptions.Builder<ModelClassRec>()
                        .setQuery(canteenRef.child(FromSharedPrefCanteen)
                                , ModelClassRec.class)
                        .build();

        adapterRecViewFoodItem = new AdapterRecViewFoodItem(options);
        recyclerView.setAdapter(adapterRecViewFoodItem);
        return view;
    }

    private void loadCanteenNames() {
        // Read canteen names from Firebase Realtime Database
        canteenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Assuming your canteen names are stored as children under "canteens" node
                List<String> canteenList = new ArrayList<>();
                for (DataSnapshot canteenSnapshot : dataSnapshot.getChildren()) {
                    String canteenName = canteenSnapshot.getKey();
//                    String canteenName = canteenSnapshot.getChildren().toString();

                    if (canteenName != null) {
                        canteenList.add(canteenName);
                    }
                }
                // Convert the list to an array
                canteenName = canteenList.toArray(new String[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void showCanteenDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select a Canteen");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

        RecyclerView recyclerView = dialogView.findViewById(R.id.canteenRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CanteenAdapter adapter = new CanteenAdapter(canteenName, new CanteenAdapter.OnCanteenClickListener() {
            @Override
            public void onCanteenClick(String canteenName) {
                SharedPreferences preferences = requireActivity().getSharedPreferences("USER_DATA", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("canteen", canteenName);
                editor.apply();
                canteen.setText(canteenName);
                if (dialog != null) {
                    dialog.dismiss(); // Close the dialog when a canteen is selected
                    updateRecyclerViewBasedOnCanteen(canteenName);
                }
            }
        });
        recyclerView.setAdapter(adapter);
//        Toast.makeText(getContext(), "djfjsd", Toast.LENGTH_SHORT).show();

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = builder.create(); // Initialize the dialog here
        dialog.show();
    }

    private void updateRecyclerViewBasedOnCanteen(String selectedCanteen) {
        // You can update the RecyclerView here based on the selected canteen.
        // For example, you can modify the Firebase query to fetch data specific to the selected canteen.

        // Sample Firebase query:
        FirebaseRecyclerOptions<ModelClassRec> options =
                new FirebaseRecyclerOptions.Builder<ModelClassRec>()
                        .setQuery(canteenRef
                                .child(selectedCanteen), ModelClassRec.class)
                        .build();

        adapterRecViewFoodItem.updateOptions(options);
        adapterRecViewFoodItem.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterRecViewFoodItem.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterRecViewFoodItem.stopListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Pause FirebaseRecyclerAdapter listening when the fragment is paused
        if (adapterRecViewFoodItem != null) {
            adapterRecViewFoodItem.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume FirebaseRecyclerAdapter listening when the fragment is resumed
        if (adapterRecViewFoodItem != null) {
            adapterRecViewFoodItem.startListening();
        }
    }
}