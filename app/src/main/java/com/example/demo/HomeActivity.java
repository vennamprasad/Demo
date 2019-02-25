package com.example.demo;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.demo.menu.DrawerAdapter;
import com.example.demo.menu.DrawerItem;
import com.example.demo.menu.SimpleItem;

import com.google.android.material.tabs.TabLayout;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, DrawerAdapter.OnItemSelectedListener {
    private SlidingRootNav slidingRootNav;
    private static final int POS_Home = 0;
    private static final int POS_Properties = 1;
    private static final int POS_Tenant = 2;
    private static final int POS_Transactions = 3;
    private static final int POS_Tasks = 4;
    private static final int POS_Reports = 5;
    private static final int POS_Inspection = 6;
    private static final int POS_Contacts = 7;
    private static final int POS_Help = 9;
    private static final int POS_Feedback = 10;
    private static final int POS_Tickets = 11;
    private static final int POS_Contact_Us = 12;
    private static final int POS_Settings = 14;
    private static final int POS_Update = 15;
    private static final int POS_Logout = 16;
    private String[] screenTitles;
    private Drawable[] screenIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Utils.setCustomTitle(this);
        //NAVIGATION
        slidingRootNav = new SlidingRootNavBuilder(this).withToolbarMenuToggle(toolbar).withMenuOpened(false).withContentClickableWhenMenuOpened(true).withSavedState(savedInstanceState).withMenuLayout(R.layout.menu_left_drawer).inject();
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();
        DrawerAdapter drawadapter = new DrawerAdapter(Arrays.asList(createItemFor(POS_Home).setChecked(true),
                createItemFor(POS_Properties),
                createItemFor(POS_Tenant),
                createItemFor(POS_Transactions),
                createItemFor(POS_Tasks),
                createItemFor(POS_Reports),
                createItemFor(POS_Inspection),
                createItemFor(POS_Contacts),
                createItemFor(POS_Help),
                createItemFor(POS_Feedback),
                createItemFor(POS_Tickets),
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
        TextView add_tenant = findViewById(R.id.add_tenant);
    }


    @Override
    public void onItemSelected(int position) {
        slidingRootNav.closeMenu();
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
        startActivity(new Intent(HomeActivity.this, AddProperty.class));
    }

    public void addTenant(View view) {
        startActivity(new Intent(HomeActivity.this, AddTenant.class));
    }
}