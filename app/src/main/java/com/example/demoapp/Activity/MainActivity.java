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
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.Utils.Constants;
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

    /**
     * set up all the UI elements of the screen
     */
    private void setupUI() {
        toolbar = findViewById(R.id.toolbar);
        tvHeader = findViewById(R.id.tv_header);

        //UI elements related to navigation drawer
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    /**
     * show default fragment in the container
     */
    private void defaultFragment() {
        Utils.replaceFragment(context, new CategoryFragment());
    }

    /**
     * set the navigation drawer selection listener
     */
    private void setNavigationDrawerSelection() {
        toolbar.setTitle("");

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * handle the navigation item selected listener
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.categories:
                Utils.replaceFragment(context, new CategoryFragment());
                break;

            case R.id.manage_patients:
                Utils.replaceFragment(context, new PatientListingFragment());
                break;

            case R.id.add_new_patients:
                Utils.replaceFragment(context, new ProblemListingFragment());
                break;

            case R.id.change_pin:
                Utils.replaceFragment(context, new ChangePinFragment());
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
        context.startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
        int count = getSupportFragmentManager().getBackStackEntryCount();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

        if(tag == null && count == 0){
            defaultFragment();
        }else if (tag != null && tag.equals(CategoryFragment.class.getName())) {
            defaultFragment();
        } else if (tag != null && tag.equals(ProblemListingFragment.class.getName())) {
            getFragmentManager().popBackStack();
        } else if (tag != null && tag.equals(EditPatientFragment.class.getName())) {
            getFragmentManager().popBackStack();
        } else if (tag != null && tag.equals(PatientListingFragment.class.getName())) {
            getFragmentManager().popBackStack();
        } else if (tag != null && tag.equals(ChangePinFragment.class.getName())) {
            getFragmentManager().popBackStack();
        } else if (tag != null && tag.equals(ProblemListingFragment.class.getName())) {
            getFragmentManager().popBackStack();
        } else if (tag != null && tag.equals(CategoryFragment.class.getName())) {
            showAlerDialog();
        } else {
            if (fragment instanceof CategoryFragment) {
                Utils.replaceFragment(context,new CategoryFragment());
            } else {
                if (count == 1) {
                    showAlerDialog();
                } else{
                    getSupportFragmentManager().popBackStack();
                }
            }
        }
    }

    /**
     * show exit dialog for exiting from the app
     */
    private void showAlerDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(Constants.MSG_EXIT_APP)
                .setMessage(Constants.MSG_DO_YOU_WANT_TO_EXIT)
                .setPositiveButton(Constants.MSG_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setNegativeButton(Constants.MSG_NO, null)
                .show();
    }

    /**
     * get the current fragment from the stack
     *
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
