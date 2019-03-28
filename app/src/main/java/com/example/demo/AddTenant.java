package com.example.demo;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SearchView;

import com.example.demo.adapter.CommonAdapter1;
import com.example.demo.database.DatabaseClient;
import com.example.demo.decor.GridSpacingItemDecoration;
import com.example.demo.fragment.AddTenantBottomFragment;
import com.example.demo.tables.TenantDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class AddTenant extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout = null;
    RecyclerView recyclerView = null;
    SearchView searchView;
    CommonAdapter1 adapter = null;
    List<TenantDetails> tenantDetails = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DemoUtils.setCustomTitle(this);
        // toolbar fancy stuff
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(), true));
        getTasks();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTasks();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTenants();
            }
        });
    }

    private void getTasks() {
        @SuppressLint("StaticFieldLeak")
        class GetTasks extends AsyncTask<Void, Void, List<TenantDetails>> implements CommonAdapter1.TenantAdapterListener {

            @Override
            protected List<TenantDetails> doInBackground(Void... voids) {
                tenantDetails = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .tenant_details_dao()
                        .getAll();
                return tenantDetails;
            }

            @Override
            protected void onPostExecute(List<TenantDetails> tenantDetails) {
                super.onPostExecute(tenantDetails);
                adapter = new CommonAdapter1(AddTenant.this, this, tenantDetails);
                recyclerView.setAdapter(adapter);
                // row click listener
                recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    @Override
                    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                        return false;
                    }

                    @Override
                    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                    }

                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                    }
                });
            }

            @Override
            public void onSelected(TenantDetails tenantDetails) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("TENANT_DETAILS", tenantDetails);
                AddTenantBottomFragment addTenantBottomFragment = new AddTenantBottomFragment();
                addTenantBottomFragment.setArguments(bundle);
                addTenantBottomFragment.show(getSupportFragmentManager(), addTenantBottomFragment.getTag());
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getTasks();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void addTenants() {
        AddTenantBottomFragment itemListDialogFragment = new AddTenantBottomFragment();
        itemListDialogFragment.show(getSupportFragmentManager(), itemListDialogFragment.getTag());

    }

    private int dpToPx() {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, r.getDisplayMetrics()));
    }
}