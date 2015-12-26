package com.udacity.moviespot.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.udacity.moviespot.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Boolean doubleBackToExitPressedOnce = false;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().performIdentifierAction(R.id.item_navigation_drawer_movies, 0);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_navigation_drawer_movies) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, new PopularMoviesFragment())
                    .commit();
            item.setChecked(true);
        } else if (id == R.id.item_navigation_drawer_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        } else if (id == R.id.item_navigation_drawer_about)

        {
            new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Dialog_Alert)
                    .setTitle("About " + getString(R.string.app_name))
                    .setMessage(getString(R.string.app_name) + "\n------------------------\n"
                            + "Version:" + getString(R.string.app_version)
                            + "\n\nDeveloped By:\n" + getString(R.string.app_developed_by)
                            + "\n\nSupport by Email:\n" + getString(R.string.support_email)
                            + "\n\nDISCLAIMER:\n" + getString(R.string.app_disclaimer)
                            + "\n\nGood Luck!!\n" + getString(R.string.app_developed_by))
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        } else if (id == R.id.item_navigation_drawer_share)

        {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            share.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_description) + "\n" +
                    "Website : " + getString(R.string.website_url) + "\n" +
                    "FaceBook Page : " + getString(R.string.facebook_url));
            startActivity(Intent.createChooser(share, getString(R.string.app_name)));
        } else if (id == R.id.item_navigation_drawer_contact_us)

        {
            try {
                Intent contactusIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.contactus_url)));
                startActivity(contactusIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No browser is installed", Toast.LENGTH_LONG);
            }
        } else if (id == R.id.item_navigation_drawer_help_and_feedback)

        {
            try {
                Intent helpIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.contactus_url)));
                startActivity(helpIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No browser is installed", Toast.LENGTH_LONG);
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(this, "Press Back again to Exit", Toast.LENGTH_SHORT).show();
            doubleBackToExitPressedOnce = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }
}
