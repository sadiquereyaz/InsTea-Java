package in.instea.instea.BottomSheet;

import android.os.Bundle;

import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import in.instea.instea.R;

public class FragmentBottomSheet extends BottomSheetDialogFragment {

    private static final String ARG_SUB = "subName";
    private String subjectName;

    public FragmentBottomSheet() {
        // Required empty public constructor
    }

    public static FragmentBottomSheet newInstance(String subName) {
        FragmentBottomSheet fragment = new FragmentBottomSheet();
        Bundle args = new Bundle();
        args.putString(ARG_SUB, subName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subjectName = getArguments().getString(ARG_SUB);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_bottom);
        ViewPager2 viewPager2 = view.findViewById(R.id.view_pager_bottomSheet);
        String[] tabTitle = {"Notes", "PYQs", "Playlists"};

        AdapterViewPagerBottomSheet adapterViewPagerBottomSheet = new AdapterViewPagerBottomSheet(this, subjectName);
        viewPager2.setAdapter(adapterViewPagerBottomSheet);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(tabTitle[position])).attach();

        return view;
    }
}