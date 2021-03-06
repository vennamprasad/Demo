package com.example.demo;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.database.DatabaseClient;
import com.example.demo.menu.DrawerAdapter;
import com.example.demo.menu.DrawerItem;
import com.example.demo.menu.SimpleItem;
import com.google.android.material.tabs.TabLayout;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, DrawerAdapter.OnItemSelectedListener {
    private static final int POS_Home = 0;
    private static final int POS_Properties = 1;
    private static final int POS_Tenant = 2;
    private static final int POS_Transactions = 3;
    private static final int POS_Reports = 4;
    private static final int POS_Inspection = 5;
    private static final int POS_Contacts = 6;
    private static final int POS_About = 7;
    private static final int POS_Feedback = 8;
    private static final int POS_Contact_Us = 9;
    private static final int POS_Settings = 10;
    private static final int POS_Update = 11;
    private static final int POS_Logout = 12;
    private SlidingRootNav slidingRootNav;
    private String[] screenTitles;
    private Drawable[] screenIcons;

    @Override
    protected void onRestart() {
        super.onRestart();
        GetCount getCount = new GetCount();
        getCount.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DemoUtils.setCustomTitle(this);
        //NAVIGATION
        slidingRootNav = new SlidingRootNavBuilder(this).withToolbarMenuToggle(toolbar).withMenuOpened(false).withContentClickableWhenMenuOpened(true).withSavedState(savedInstanceState).withMenuLayout(R.layout.menu_left_drawer).inject();
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();
        DrawerAdapter drawadapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_Home).setChecked(true),
                createItemFor(POS_Properties),
                createItemFor(POS_Tenant),
                createItemFor(POS_Transactions),
                createItemFor(POS_Reports),
                createItemFor(POS_Inspection),
                createItemFor(POS_Contacts),
                createItemFor(POS_About),
                createItemFor(POS_Feedback),
                createItemFor(POS_Contact_Us),
                createItemFor(POS_Settings),
                createItemFor(POS_Update),
                createItemFor(POS_Logout)));
        drawadapter.setListener(this);
        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(drawadapter);
        drawadapter.setSelected(POS_Home);
        init();
    }

    private void init() {
        TextView add_property = findViewById(R.id.add_property);
        GetCount getCount = new GetCount();
        getCount.execute();
        TextView add_tenant = findViewById(R.id.add_tenant);
    }


    @Override
    public void onItemSelected(int position) {
        Intent intent = new Intent();
        Fragment fragment = new Fragment();
        switch (position) {
            case POS_Properties:
                startActivity(new Intent(this, AddPropertyActivity.class));
                break;
            case POS_Tenant:
                startActivity(new Intent(this, AddTenantActivity.class));
                break;
            case POS_Transactions:
                startActivity(new Intent(this, AddTenantActivity.class));
                break;
            case POS_Reports:
                startActivity(new Intent(this, AddTenantActivity.class));
                break;
            case POS_Inspection:
                startActivity(new Intent(this, AddTenantActivity.class));
                break;
            case POS_Contacts:
                startActivity(new Intent(this, AddTenantActivity.class));
                break;
            case POS_About:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case POS_Feedback:
                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
                break;
            case POS_Contact_Us:
                startActivity(new Intent(this, ResponsiveLoginActivity.class));
                break;
            case POS_Settings:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case POS_Update:
                final String appPackageName = getPackageName();
                // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;
            case POS_Logout:
                //clearing Pref
                clearPreferences();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }

        slidingRootNav.closeMenu();
    }

    private void clearPreferences() {
        //TO DO
    }


    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorPrimary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorGreen))
                .withSelectedTextTint(color(R.color.colorGreen));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void addProperty(View view) {
        startActivity(new Intent(HomeActivity.this, AddPropertyActivity.class));
    }

    public void addTenant(View view) {
        startActivity(new Intent(HomeActivity.this, AddTenantActivity.class));
    }

    @SuppressLint("StaticFieldLeak")
    private class GetCount extends AsyncTask<Void, Void, Void> {
        int property_Count = 0, tenant_Count = 0;

        @Override
        protected Void doInBackground(Void... voids) {
            property_Count = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().property_details_dao().getNumberOfRows();
            tenant_Count = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().tenant_details_dao().getNumberOfRows();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView propertyCount = findViewById(R.id.propertyCount);
            TextView tenantCount = findViewById(R.id.tenantCount);
            propertyCount.setText(String.valueOf(property_Count));
            tenantCount.setText(String.valueOf(tenant_Count));
        }
    }
}