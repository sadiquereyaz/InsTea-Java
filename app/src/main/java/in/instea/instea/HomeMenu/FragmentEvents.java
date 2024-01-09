package in.instea.instea.HomeMenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import in.instea.instea.ModelClassRec;
import in.instea.instea.R;

public class FragmentEvents extends Fragment {
    RecyclerView recyclerView;
    AdapterRecViewEvents adapterRecViewEvents;

    public FragmentEvents() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        if (savedInstanceState == null) {
            recyclerView = view.findViewById(R.id.rec_events);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            // arrange in descending order
            layoutManager.setReverseLayout(true);
            layoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(layoutManager);
            // Disable RecyclerView item animator
            recyclerView.setItemAnimator(null);

////         Retrieve SharedPreferences
            SharedPreferences preferences = requireActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
//            // Retrieve data from SharedPreferences
            String FromSharedPrefUniversity = preferences.getString("university", "Aspirant");      //the second is a default value that will be returned if the "university" key is not found in the SharedPreferences file. if the value is string, only then it wil be under double quote
//            String FromSharedPrefDepartment = preferences.getString("department", "Blank");
//            String FromSharedPrefSemester = preferences.getString("semester", "blank");

            FirebaseRecyclerOptions<ModelClassRec> options =
                    new FirebaseRecyclerOptions.Builder<ModelClassRec>()
                            .setQuery(FirebaseDatabase
                                            .getInstance().getReference("events")
                                            .child("Jamia Millia Islamia")
                                    , ModelClassRec.class)
                            .build();

            adapterRecViewEvents = new AdapterRecViewEvents(options);
            recyclerView.setAdapter(adapterRecViewEvents);

            adapterRecViewEvents.setOnItemClickListener(new AdapterRecViewEvents.OnItemClickListener() {
                @Override
                public void onItemClick(ModelClassRec model) {
                    String link = model.getNlink();
                    if (!(link.equals(""))) {
                        Uri uri = Uri.parse(link);
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    } else {
                        Toast.makeText(getContext(), "No attached document found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterRecViewEvents.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterRecViewEvents.stopListening();
    }
}