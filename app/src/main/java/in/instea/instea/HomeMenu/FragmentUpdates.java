package in.instea.instea.HomeMenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import in.instea.instea.BottomSheet.AdapterViewPagerBottomSheet;
import in.instea.instea.R;

public class FragmentUpdates extends Fragment {

    public FragmentUpdates() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vw = inflater.inflate(R.layout.fragment_updates, container, false);

        TabLayout tabLayout = vw.findViewById(R.id.tab_layout_updates);
        ViewPager2 viewPager2 = vw.findViewById(R.id.view_pager_updates);
        String[] tabTitle = {"Departmental", "University"};

        AdapterViewPagerUpdates adapterViewPagerUpdates = new AdapterViewPagerUpdates(this);
        viewPager2.setAdapter(adapterViewPagerUpdates);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(tabTitle[position])).attach();

        return vw;
    }
}