package com.example.demoapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.demoapp.Fragments.AddPatientProblemFragment;
import com.example.demoapp.Fragments.CategoryFragment;
import com.example.demoapp.Fragments.ChangePinFragment;
import com.example.demoapp.Fragments.PatientListingFragment;
import com.example.demoapp.Fragments.ProblemListingFragment;
import com.example.demoapp.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        defaultFragment();
        setNavigationDrawerSelection();
    }

    private void setupUI() {
        toolbar = findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    private void defaultFragment() {
        Fragment fragment = null;
        fragment = new CategoryFragment();
        this.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    private void setNavigationDrawerSelection() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.categories:
                fragment = new CategoryFragment();
                openFragment(fragment);
                break;

            case R.id.manage_patients:
                fragment = new PatientListingFragment();
                openFragment(fragment);
                break;

            case R.id.add_new_patients:
                fragment = new ProblemListingFragment();
                openFragment(fragment);
                break;

            case R.id.change_pin:
                fragment = new ChangePinFragment();
                openFragment(fragment);
                break;

            case R.id.logout:
                doLogout();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void doLogout() {

    }

    private void openFragment(Fragment fragment) {
        this.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }
}
