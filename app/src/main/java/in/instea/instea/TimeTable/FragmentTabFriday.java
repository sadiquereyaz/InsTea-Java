package in.instea.instea.TimeTable;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.instea.instea.R;
import in.instea.instea.ModelClassRec;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentTabFriday extends Fragment {
    private AdapterRecViewTimetable adapterRecViewTimetable;

    public FragmentTabFriday() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_friday, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rec_friday);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Disable RecyclerView item animator
        recyclerView.setItemAnimator(null);

        SharedPreferences preferences = requireActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        // Retrieve data from SharedPreferences
        String FromSharedPrefUniversity = preferences.getString("university","Aspirant");      //the second is a default value that will be returned if the "university" key is not found in the SharedPreferences file. if the value is string, only then it wil be under double quote
        String FromSharedPrefDepartment = preferences.getString("department","Blank");
        String FromSharedPrefSemester = preferences.getString("semester","blank");

        FirebaseRecyclerOptions<ModelClassRec> options =
                new FirebaseRecyclerOptions.Builder<ModelClassRec>()
                        .setQuery(FirebaseDatabase
                                        .getInstance().getReference("university")
                                        .child(FromSharedPrefUniversity)
                                        .child(FromSharedPrefDepartment)
                                        .child(FromSharedPrefSemester)
                                        .child("Time Table")
                                        .child("Friday")
                                , ModelClassRec.class)
                        .build();

        adapterRecViewTimetable = new AdapterRecViewTimetable(options);
        recyclerView.setAdapter(adapterRecViewTimetable);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterRecViewTimetable.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterRecViewTimetable.stopListening();
    }
    @Override
    public void onPause() {
        super.onPause();
        // Pause FirebaseRecyclerAdapter listening when the fragment is paused
        if (adapterRecViewTimetable != null) {
            adapterRecViewTimetable.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume FirebaseRecyclerAdapter listening when the fragment is resumed
        if (adapterRecViewTimetable != null) {
            adapterRecViewTimetable.startListening();
        }
    }

}