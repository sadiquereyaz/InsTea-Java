package in.instea.instea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import in.instea.instea.HomeMenu.FragmentEvents;
import in.instea.instea.SideNavMenu.FragmentProfile;


import in.instea.instea.SideNavMenu.FragmentAbout;
import in.instea.instea.SideNavMenu.FragmentPlacement;
import in.instea.instea.TimeTable.FragmentTimeTable;

public class NavFragmentContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_fragment_container);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Get the title from the intent's extra
        String title = getIntent().getStringExtra("activity_title");
        String fragmentName_ = getIntent().getStringExtra("FRAGMENT_TO_LOAD");

        if (title != null) {
            // Set the title of the Toolbar
            getSupportActionBar().setTitle(title);
        } else {
            getSupportActionBar().setTitle("InsT");
        }

        if ("scheduleFragment".equals(fragmentName_)) {
            loadFragment(new FragmentTimeTable());
        } else if ("fragmentProfile".equals(fragmentName_)) {
            loadFragment(new FragmentProfile());
        } else if ("fragmentPlacement".equals(fragmentName_)) {
            loadFragment(new FragmentPlacement());
        } else if ("fragmentAbout".equals(fragmentName_)) {
            loadFragment(new FragmentAbout());
        } else if ("articleFragment".equals(fragmentName_)) {
            loadFragment(new FragmentEvents());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_nav, fragment);
        fragmentTransaction.commit();
    }
}