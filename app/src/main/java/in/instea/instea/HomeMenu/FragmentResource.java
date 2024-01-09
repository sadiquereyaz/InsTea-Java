package in.instea.instea.HomeMenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.instea.instea.BottomSheet.FragmentBottomSheet;
import in.instea.instea.ModelClassRec;
import in.instea.instea.NavFragmentContainer;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import in.instea.instea.R;

public class FragmentResource extends Fragment {

    RecyclerView recyclerView;
    TextView txtFlash;
    RelativeLayout arrowBtn, websiteContainer;
    private AdapterGrid adapterGrid;
    private PieChart pieChart;
    String flashLink;

    public FragmentResource() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resource, container, false);
        arrowBtn = view.findViewById(R.id.arrow_container);
//        arrowBtn.bringToFront();
        websiteContainer = view.findViewById(R.id.website_container);
        pieChart = view.findViewById(R.id.pieChart);
        txtFlash = view.findViewById(R.id.flash_txt);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("flash");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String flashText = snapshot.child("flashText").getValue(String.class);
                    String backColor = snapshot.child("bgColor").getValue(String.class);
                    flashLink = snapshot.child("flashLink").getValue(String.class);

                    if (flashText != null  && backColor != null) {
                        txtFlash.setText(flashText+ " ");

                        int color = Color.parseColor(backColor);
                        txtFlash.setBackgroundColor(color);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });

        arrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NavFragmentContainer.class);
                intent.putExtra("activity_title", "Class Schedule");       // activity_title is the key for the lock lock title which contain "About Us"
                intent.putExtra("FRAGMENT_TO_LOAD", "scheduleFragment");
                startActivity(intent);
            }
        });

        websiteContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(flashLink.equals(""))) {
                    Uri uri = Uri.parse(flashLink);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));

                } else {
                    Toast.makeText(getContext(), "No link Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (savedInstanceState == null) {
//            recyclerView = view.findViewById(R.id.recycler_view_subject);
//            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

            RecyclerView recyclerView = view.findViewById(R.id.recycler_view_subject);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3); // Default span count of 3
            recyclerView.setLayoutManager(layoutManager);
            // Calculate the span count based on available space
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int itemWidth = getResources().getDimensionPixelSize(R.dimen.grid_width); // Define your grid item width
            int spanCount = screenWidth / itemWidth; // Calculate the span count based on screen width and item width

            layoutManager.setSpanCount(spanCount); // Set the calculated span count to the GridLayoutManager

            // Disable RecyclerView item animator
            recyclerView.setItemAnimator(null);

//         Retrieve SharedPreferences
            SharedPreferences preferences = requireActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            // Retrieve data from SharedPreferences
            String FromSharedPrefUniversity = preferences.getString("university", "Aspirant");      //the second is a default value that will be returned if the "university" key is not found in the SharedPreferences file. if the value is string, only then it wil be under double quote
            String FromSharedPrefDepartment = preferences.getString("department", "Blank");
            String FromSharedPrefSemester = preferences.getString("semester", "blank");

//            SharedData sharedData = SharedData.getInstance();
//            String university = sharedData.getUni();
//            String department = sharedData.getDep();
//            String semester = sharedData.getSem();

            FirebaseRecyclerOptions<ModelClassRec> options =
                    new FirebaseRecyclerOptions.Builder<ModelClassRec>()
                            .setQuery(FirebaseDatabase
                                            .getInstance().getReference("university")
                                            .child(FromSharedPrefUniversity)
                                            .child(FromSharedPrefDepartment)
                                            .child(FromSharedPrefSemester)
                                            .child("sub")
                                    , ModelClassRec.class)
                            .build();
            adapterGrid = new AdapterGrid(options);

            // Set the MainAdapter with the OnItemClickListener
            adapterGrid.setOnItemClickListener(new AdapterGrid.OnItemClickListener() {
                @Override
                public void onItemClick(ModelClassRec model) {
                    String subName = model.getTitle();      // Extract the data from the clicked item

                    // Create a new instance of the BottomSheetDialogFragment and pass the data as arguments
                    FragmentBottomSheet fragmentBottomSheet = FragmentBottomSheet.newInstance(subName);
                    // showing bottom-sheet dialog
                    fragmentBottomSheet.show(getParentFragmentManager(), fragmentBottomSheet.getTag());
                }
            });

            recyclerView.setAdapter(adapterGrid);
        }
        populatePieChart();

        return view;
    }

    private void populatePieChart() {
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#4FC0D0")); // Pink
        colors.add(Color.parseColor("#19376D")); // Light Blue
        colors.add(Color.parseColor("#FF52A2")); // Light brinjal
        colors.add(Color.parseColor("#F9D949")); // Light Yellow
        colors.add(Color.parseColor("#606C5D")); // L.Green
        colors.add(Color.parseColor("#116D6E")); // L.Green
        colors.add(Color.parseColor("#DB005B")); // L.Green

        SharedPreferences preferences = requireActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        String FromSharedPrefUniversity = preferences.getString("university", "Aspirant");
        String FromSharedPrefDepartment = preferences.getString("department", "Blank");
        String FromSharedPrefSemester = preferences.getString("semester", "blank");

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);     // Get the current day of the week
        String[] days = { "Sunday", "Monday", "Tuesday","Wednesday","Thursday", "Friday","Saturday"};      // Convert the day of the week to its name
        String dayName = days[dayOfWeek-1]; // Subtract 1 since Calendar uses 1 for Sunday

        DatabaseReference subjectsRef = FirebaseDatabase.getInstance().getReference("university")   //FromSharedPrefUniversity
                .child(FromSharedPrefUniversity)    //"Computer Engineering"
                .child(FromSharedPrefDepartment)    //"Computer Engineering"
                .child(FromSharedPrefSemester)        //"I"
                .child("Time Table")
                .child(dayName);

        subjectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> subjectNames = new ArrayList<>();
                for (DataSnapshot subjectSnapshot : snapshot.getChildren()) {
                    String subjectName = subjectSnapshot.child("shortSub").getValue(String.class);
                    if (subjectName != null && !subjectName.isEmpty()) {
                        subjectNames.add(subjectName);
                    }
                }

                // Create pie entries
                List<PieEntry> pieEntries = new ArrayList<>();
                for (String subjectName : subjectNames) {
                    pieEntries.add(new PieEntry(1, subjectName));
                }

                // Create a data set and customize it
                PieDataSet dataSet = new PieDataSet(pieEntries, "");
                dataSet.setColors(colors);

//                pieChart.setDragDecelerationFrictionCoef(0.99f);
                // Create a PieData object and set it to the pie chart
//                pieChart.setHoleRadius(70f);        // Set hole radius (gap between slices)
//                dataSet.setColors(Color.RED, Color.GREEN, Color.BLUE); // Set colors

                PieData data = new PieData(dataSet);
                pieChart.setData(data);
                dataSet.setDrawValues(false);// Hide percentages
//                pieChart.setEntryLabelColor(Color.BLACK); // Set label color
                pieChart.getDescription().setEnabled(false);
//                pieChart.setEntryLabelTextSize(20f);
                pieChart.setCenterText("Today's\nShedule"); // Set center text
                pieChart.setCenterTextColor(Color.GRAY);
                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                pieChart.setEntryLabelTypeface(boldTypeface);
//                pieChart.setRotationEnabled(false);
//                pieChart.setHighlightPerTapEnabled(true);
                pieChart.setHoleColor(Color.TRANSPARENT);
                //adding friction when rotating the pie chart
                pieChart.setDragDecelerationFrictionCoef(1.2f);
//
//                // to get avoid label overlapping or lebel invisibility
//                pieChart.setExtraTopOffset(5f);
//                pieChart.setExtraBottomOffset(5f);
//                pieChart.setExtraLeftOffset(5f);
//                pieChart.setExtraRightOffset(5f);
                pieChart.getLegend().setEnabled(false);        // Hide the legend(square shape below)
                pieChart.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterGrid.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterGrid.stopListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Pause FirebaseRecyclerAdapter listening when the fragment is paused
        if (adapterGrid != null) {
            adapterGrid.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume FirebaseRecyclerAdapter listening when the fragment is resumed
        if (adapterGrid != null) {
            adapterGrid.startListening();
        }
    }
}