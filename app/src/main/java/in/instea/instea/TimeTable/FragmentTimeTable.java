package in.instea.instea.TimeTable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalDate;

import in.instea.instea.R;

public class FragmentTimeTable extends Fragment {

    public FragmentTimeTable() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_time_table, container, false);

        TabLayout tabLayout = root.findViewById(R.id.tab_layout_time_table);
        ViewPager2 viewPager2 = root.findViewById(R.id.view_pager_time_table);
        String[] tabTitle = {"Mon", "Tues", "Wednes", "Thurs", "Fri"};

        AdapterViewPagerTimeTable adapter = new AdapterViewPagerTimeTable(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(tabTitle[position])).attach();

        int currentDayIndex = 0;        // 0 index is for sunday in this library
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDayIndex = LocalDate.now().getDayOfWeek().getValue();
        }
        int tabIndex;
        if (currentDayIndex == 2) {
            tabIndex = 1;
        } else if (currentDayIndex == 3) {
            tabIndex = 2;
        } else if (currentDayIndex == 4) {
            tabIndex = 3;
        } else if (currentDayIndex == 5) {
            tabIndex = 4;
        } else if (currentDayIndex == 6) {
            tabIndex = 0;
        } else {
            tabIndex = 0;
        }

        // Set the initially displayed tab (for example, Monday)
        viewPager2.setCurrentItem(tabIndex);

        return root;
    }
}