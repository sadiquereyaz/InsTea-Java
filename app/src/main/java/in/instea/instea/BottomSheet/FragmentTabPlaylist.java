package in.instea.instea.BottomSheet;

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

public class FragmentTabPlaylist extends Fragment {
    private AdapterRecViewBottomSheet tabRecViewAdapter;
    private static final String ARG_NAME = "SUBJECT_NAME";
    String subjName;

    public FragmentTabPlaylist() {
        // Required empty public constructor
    }

    // Add a method to create a new instance of PlaylistTabFragment with subject name as an argument
    public static FragmentTabPlaylist newInstance(String subjectName) {
        FragmentTabPlaylist fragment = new FragmentTabPlaylist();
        Bundle args = new Bundle();
        args.putString("SUBJECT_NAME", subjectName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subjName = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        RecyclerView playlistRecyclerView = view.findViewById(R.id.recycler_view_playlist);
        playlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Disable RecyclerView item animator
        playlistRecyclerView.setItemAnimator(null);

        //         Retrieve SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        // Retrieve data from SharedPreferences
        String FromSharedPrefUniversity = preferences.getString("university", "Aspirant");      //the second is a default value that will be returned if the "university" key is not found in the SharedPreferences file. if the value is string, only then it wil be under double quote
        String FromSharedPrefDepartment = preferences.getString("department", "Blank");
        String FromSharedPrefSemester = preferences.getString("semester", "blank");

        FirebaseRecyclerOptions<ModelClassRec> options =
                new FirebaseRecyclerOptions.Builder<ModelClassRec>()
                        .setQuery(FirebaseDatabase
                                        .getInstance().getReference("university")
                                        .child(FromSharedPrefUniversity)
                                        .child(FromSharedPrefDepartment)
                                        .child(FromSharedPrefSemester)
                                        .child("sub")
                                        .child(subjName)
                                        .child("playlist")
                                , ModelClassRec.class)
                        .build();

        tabRecViewAdapter = new AdapterRecViewBottomSheet(options);

        // Set the MainAdapter with the OnItemClickListener
        tabRecViewAdapter.setOnItemClickListener(new AdapterRecViewBottomSheet.OnItemClickListener() {
            @Override
            public void onItemClick(ModelClassRec model) {

                String link = model.getPllink();
                if (!(link.equals(""))) {
                    Uri uri = Uri.parse(link);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else {
                    Toast.makeText(getContext(), "No link found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playlistRecyclerView.setAdapter(tabRecViewAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        tabRecViewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        tabRecViewAdapter.stopListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Pause FirebaseRecyclerAdapter listening when the fragment is paused
        if (tabRecViewAdapter != null) {
            tabRecViewAdapter.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume FirebaseRecyclerAdapter listening when the fragment is resumed
        if (tabRecViewAdapter != null) {
            tabRecViewAdapter.startListening();
        }
    }
}