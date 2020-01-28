package com.example.myfirstapp.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.navigation.Navigation;

import com.example.myfirstapp.R;
import com.example.myfirstapp.view.MainActivity;
import com.example.myfirstapp.view.model.MoviesListViewModel;

import javax.inject.Inject;

public class MoviesListFragment extends ListFragment {
    @Inject
    public MoviesListViewModel model;

    boolean dualPane;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MainActivity) getActivity()).moviesComponent.inject(this);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        model.fetchFilms();

        model.films.observe(this, films -> {
            String titles[] = new String[films.size()];

            for (int i = 0; i < titles.length; i++) {
                titles[i] = films.get(i).title;
            }

            setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, titles));

            View detailsFrame = getActivity().findViewById(R.id.detailAction);
            dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

            System.out.println("dualPane: " + dualPane);

            if (dualPane) {
                // In dual-pane mode, the list view highlights the selected item.
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                // Make sure our UI is in the correct state.
                showDetails(model.selected);
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        model.selected = model.films.getValue().get(position).id;

        if (dualPane) {
            showDetails(position);
        } else {
            MoviesListFragmentDirections.DetailAction action = MoviesListFragmentDirections.detailAction();
            action.setIndex(position);
            Navigation.findNavController(v).navigate(action);
        }
    }

    void showDetails(int index) {
        getListView().setItemChecked(index, true);

        DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.detailAction);

        if (details == null || details.getShownIndex() != index) {
            details = DetailsFragment.newInstance(index);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.detailAction, details);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }
}
