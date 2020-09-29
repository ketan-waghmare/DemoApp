package com.example.demoapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.Fragments.AddPatientFragment;
import com.example.demoapp.Fragments.AddPatientProblemFragment;
import com.example.demoapp.Fragments.CategoryFragment;
import com.example.demoapp.Fragments.ChangePinFragment;
import com.example.demoapp.Fragments.EditPatientFragment;
import com.example.demoapp.Fragments.PatientListingFragment;
import com.example.demoapp.Fragments.ProblemListingFragment;
import com.example.demoapp.R;
import com.example.demoapp.Utils.Utils;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;

/**
 * created by ketan 26-9-2020
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //region variables
    private Toolbar toolbar;
    private DrawerLayout drawer;
    public static TextView tvHeader;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Context context = MainActivity.this;
    //endregion

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
        tvHeader = findViewById(R.id.tv_header);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    private void defaultFragment() {
        Utils.replaceFragment(context,new CategoryFragment());
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
                Utils.replaceFragment(context,new CategoryFragment());
                break;

            case R.id.manage_patients:
                Utils.replaceFragment(context,new PatientListingFragment());
                break;

            case R.id.add_new_patients:
                Utils.replaceFragment(context,new ProblemListingFragment());
                break;

            case R.id.change_pin:
                Utils.replaceFragment(context,new ChangePinFragment());
                break;

            case R.id.logout:
                doLogout();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * handle the logout action
     */
    private void doLogout() {
        context.startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finishAffinity();
    }

    /**
     * handle the backpress action
     * show the dialog box for exit
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String tag = getCurrentFragment();
        Log.e("Fragment_Tag_Log", ":" + tag + "   :   " + CategoryFragment.class.getName());
        int count = getSupportFragmentManager().getBackStackEntryCount();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

        if (tag != null && tag.equals(CategoryFragment.class.getName())) {
            defaultFragment();
        } else if (tag != null && tag.equals(ProblemListingFragment.class.getName())) {
            Utils.replaceFragment(context,new ProblemListingFragment());
        }else if(tag != null && tag.equals(EditPatientFragment.class.getName())){
            Utils.replaceFragment(context,new PatientListingFragment());
        }else if(tag != null && tag.equals(PatientListingFragment.class.getName())){
            Utils.replaceFragment(context,new PatientListingFragment());
        }else if(tag != null && tag.equals(ChangePinFragment.class.getName())){
            Utils.replaceFragment(context,new CategoryFragment());
        }else if(tag != null && tag.equals(ProblemListingFragment.class.getName())){
            Utils.replaceFragment(context,new ProblemListingFragment());
        } else if (tag != null && tag.equals(CategoryFragment.class.getName())) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit App")
                    .setMessage("Do you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            if (fragment instanceof CategoryFragment) {
                for (int i = 0; i < count; ++i) {
                    getFragmentManager().popBackStackImmediate();
                }
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return;
            } else {
                if (count == 1) {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Exit App")
                            .setMessage("Do you want to exit? ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finishAffinity();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                } else {
                    getSupportFragmentManager().popBackStack();
                }
            }
        }
    }

    /**
     * get the current fragment from the stack
     * @return
     */
    private String getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = null;
        String fragmentTag = null;
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
            currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
        }
        return fragmentTag;
    }
}
