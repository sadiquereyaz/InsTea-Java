package in.instea.instea.BottomSheet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterViewPagerBottomSheet extends FragmentStateAdapter {
    private String sub;

    public AdapterViewPagerBottomSheet(@NonNull FragmentBottomSheet fragmentActivity, String subject) {
        super(fragmentActivity);
        this.sub = subject;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return FragmentTabNote.newInstance(sub);     // Pass the subjectName to NoteTabFragment
            case 1:
                return FragmentTabPyq.newInstance(sub);
            case 2:
                return FragmentTabPlaylist.newInstance(sub);
        }
        return new FragmentTabNote();   //defaultFragment
    }

    @Override
    public int getItemCount() {
        return 3;// no of tabs
    }
}
