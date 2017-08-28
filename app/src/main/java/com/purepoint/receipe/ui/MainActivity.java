package com.purepoint.receipe.ui;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.purepoint.receipe.R;
import com.purepoint.receipe.RecipeApplication;
import com.purepoint.receipe.adapters.DataAdapter;
import com.purepoint.receipe.entities.Receipe;
import com.purepoint.receipe.viewmodels.ListReceipesViewModel;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private final String TAG = MainActivity.class.getName();
    private RecyclerView recycleView;
    private DataAdapter dataAdapter;
    private SearchView searchView;
    private ListReceipesViewModel viewModel;
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Required by Dagger2 for field injection
        ((RecipeApplication) getApplication()).getAppComponent().inject(this);
        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(ListReceipesViewModel.class);
        setupView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.length() > 0) {
                    viewModel.loadReceipes(query);
                }
                return false;
            }
        });

        viewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse != null) {
                if (apiResponse.getError() != null) {
                    handleError(apiResponse.getError());
                } else {
                    handleResponse(apiResponse.getReceipes());
                }
            } else {
                Toast.makeText(
                        this,
                        getResources().getString(R.string.receipe_not_found),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupView() {
        recycleView = findViewById(R.id.recycle_view);
        searchView = findViewById(R.id.search_view);

        recycleView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleView.hasFixedSize();
        recycleView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recycleView.getContext(), LinearLayoutManager.VERTICAL);
        recycleView.addItemDecoration(mDividerItemDecoration);
        dataAdapter = new DataAdapter(getLayoutInflater());
        dataAdapter.setHasStableIds(true);
        recycleView.hasFixedSize();
        recycleView.setAdapter(dataAdapter);

        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.receipe_hit));
        //Clear focus from search view and hide the keyboard
        searchView.clearFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        }
    }

    private void handleResponse(List<Receipe> receipesList) {
        if (receipesList != null && receipesList.size() > 0) {
            dataAdapter.addReceipes(receipesList);
        } else {
            dataAdapter.clearReceipes();
            Toast.makeText(
                    this,
                    getResources().getString(R.string.receipe_not_found),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void handleError(Throwable error) {
        dataAdapter.clearReceipes();
        Log.e(TAG, "error occured: " + error.toString());
        Toast.makeText(this, getResources().getString(R.string.receipe_error), Toast.LENGTH_SHORT).show();
    }
}
