package in.instea.instea.SideNavMenu;

import android.content.Intent;
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

import in.instea.instea.AdapterAboutRec;
import in.instea.instea.ModelClassRec;
import in.instea.instea.R;

public class FragmentAbout extends Fragment {
    RecyclerView recyclerViewAbout;
    AdapterAboutRec adapterAboutRec;

    public FragmentAbout() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        if (savedInstanceState == null) {
            recyclerViewAbout = view.findViewById(R.id.rec_about);
            recyclerViewAbout.setLayoutManager(new LinearLayoutManager(getContext()));
            // Disable RecyclerView item animator
            recyclerViewAbout.setItemAnimator(null);        //it protects the app from crashing when recycler vciew reloads after return to the same activity

            FirebaseRecyclerOptions<ModelClassRec> options =
                    new FirebaseRecyclerOptions.Builder<ModelClassRec>()
                            .setQuery(FirebaseDatabase
                                            .getInstance().getReference("team")
                                    , ModelClassRec.class)
                            .build();
            adapterAboutRec = new AdapterAboutRec(options);
            recyclerViewAbout.setAdapter(adapterAboutRec);

            // Set the MainAdapter with the OnItemClickListener
            adapterAboutRec.setOnItemClickListener(new AdapterAboutRec.OnItemClickListener() {
                @Override
                public void onItemClick(ModelClassRec model) {
                    String link = model.getNlink();
                    if (!(link.equals(""))) {
                        Uri uri = Uri.parse(link);
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    } else {
                        Toast.makeText(getContext(), "No link found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapterAboutRec.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterAboutRec.stopListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Pause FirebaseRecyclerAdapter listening when the fragment is paused
        if (adapterAboutRec != null) {
            adapterAboutRec.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume FirebaseRecyclerAdapter listening when the fragment is resumed
        if (adapterAboutRec != null) {
            adapterAboutRec.startListening();
        }
    }
}
