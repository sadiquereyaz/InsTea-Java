package in.instea.instea.TimeTable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterViewPagerTimeTable extends FragmentStateAdapter {

    public AdapterViewPagerTimeTable(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentTabMonday();     // Pass the subjectName to NoteTabFragment
            case 1:
                return new FragmentTabTuesday();
            case 2:
                return new FragmentTabWednesday();
            case 3:
                return new FragmentTabThursday();
            case 4:
                return new FragmentTabFriday();
        }
        return new FragmentTabMonday();   //defaultFragment
    }

    @Override
    public int getItemCount() {
        return 5;// no of tabs
    }
}


