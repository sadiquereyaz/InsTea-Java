package in.instea.instea.HomeMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterViewPagerUpdates extends FragmentStateAdapter {
    public AdapterViewPagerUpdates(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentUpdatesDep();
            case 1:
                return new FragmentUpdatesUni();
        }
        return new FragmentUpdatesDep();   //defaultFragment
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
