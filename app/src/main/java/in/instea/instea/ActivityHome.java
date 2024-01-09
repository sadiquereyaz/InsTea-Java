package in.instea.instea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import in.instea.instea.HomeMenu.FragmentCanteen;
import in.instea.instea.HomeMenu.FragmentEvents;
import in.instea.instea.HomeMenu.FragmentResource;
import in.instea.instea.HomeMenu.FragmentUpdates;

public class ActivityHome extends AppCompatActivity {

    SwitchCompat themeSwitch;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        bottomNavigationView = findViewById(R.id.bottom_bar);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
//        themeSwitch = headerView.findViewById(R.id.switch_theme);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        FirebaseMessaging.getInstance().subscribeToTopic("all");        //notification

//        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if(isChecked){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                }else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                }
//            }
//        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.profile) {
                    String title = "Profile";
                    moveToNavActivity(title, "fragmentProfile");

                } else if (id == R.id.placement) {
                    String title = "Placement Stats";
                    moveToNavActivity(title, "fragmentPlacement");

                } else if (id == R.id.about_us) {
                    String title = "About Us";
                    moveToNavActivity(title, "fragmentAbout");

                } else if (id == R.id.website) {
                    Toast.makeText(ActivityHome.this, "Under Development...", Toast.LENGTH_LONG).show();

                } else if (id == R.id.whatsapp) {
                    String url = "https://chat.whatsapp.com/HutellGvzPv5kpIDguJj6l";
                    Uri uri = Uri.parse(url);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));

                } else if (id == R.id.share) {
//                    String url = "https://play.google.com/store/apps/details?id=in.instea.instea";
                    String url = "https://drive.google.com/drive/folders/1zBk2vIT3xJ0q6JCCKHNrfxfiHhqXMpv2?usp=drive_link";
                    Uri uri = Uri.parse(url);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));

                } else if (id == R.id.feedback) {
                    String url = "https://forms.gle/PTfJy7QGpMeNi1iR8";
                    Uri uri = Uri.parse(url);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));

                } else if (id == R.id.admin) {
                    String url = "https://play.google.com/store/apps/details?id=in.instea.insteaadmins";
                    Uri uri = Uri.parse(url);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));

                } else if (id == R.id.signout) {
                    // Clear SharedPreferences data
                    SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();

                    // Navigate back to the login screen
                    Intent intent = new Intent(getApplicationContext(), Signin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
//                    finish();
                }
                drawerLayout.closeDrawer(GravityCompat.START);      //automatically close the drawer after clicking on its item
                return false;
            }
        });

        //default bottomNavigation selected item
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new FragmentResource());
        fragmentTransaction.commit();

        // Bottom navigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (itemId == R.id.resource) {
                    fragmentTransaction.replace(R.id.frame_layout, new FragmentResource()).commit();
                } else if (itemId == R.id.events) {
                    fragmentTransaction.replace(R.id.frame_layout, new FragmentEvents()).commit();
                } else if (itemId == R.id.updates) {
                    fragmentTransaction.replace(R.id.frame_layout, new FragmentUpdates()).commit();
                } else if (itemId == R.id.canteen) {
                    fragmentTransaction.replace(R.id.frame_layout, new FragmentCanteen()).commit();
                }

                // Update the selected indicator here
                item.setChecked(true); // This will visually indicate the selected item.
                return true;
            }
        });
    }

    private void moveToNavActivity(String title, String fragmentName) {
        Intent intent = new Intent(getApplicationContext(), NavFragmentContainer.class);
        intent.putExtra("activity_title", title);                     // activity_title is the key for the lock lock title which contain "About Us"
        intent.putExtra("FRAGMENT_TO_LOAD", fragmentName);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}