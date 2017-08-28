package com.purepoint.receipe.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private ListReceipesViewModel mViewModel;

    @Inject
    public ViewModelFactory(ListReceipesViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListReceipesViewModel.class)) {
            return (T) mViewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
