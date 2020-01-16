package com.example.myfirstapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myfirstapp.R;
import com.example.myfirstapp.db.models.Film;
import com.example.myfirstapp.view.DisplayMessageActivity;
import com.example.myfirstapp.view.MainActivity;
import com.example.myfirstapp.view.fragment.DetailsFragment;
import com.example.myfirstapp.view.model.MoviesListViewModel;

import java.util.ArrayList;

public class MoviesListFragment extends ListFragment {
    boolean dualPane;
    int curCheckPosition = 0;
    MoviesListViewModel model;

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        model = ViewModelProviders.of(getActivity()).get(MoviesListViewModel.class);

        model.films.observe(this, new Observer<ArrayList<Film>>() {
            @Override
            public void onChanged(ArrayList<Film> films) {
                String titles[] = new String[films.size()];

                for (int i = 0; i < titles.length; i++) {
                    titles[i] = films.get(i).title;
                }

                setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, titles));
                System.out.println("1");
                View detailsFrame = getActivity().findViewById(R.id.details);
                System.out.println("1");
                dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

                if (savedInstanceState != null) {
                    curCheckPosition = savedInstanceState.getInt("curChoice", 0);
                }

                if (dualPane) {
                    // In dual-pane mode, the list view highlights the selected item.
                    getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    // Make sure our UI is in the correct state.
                    showDetails(curCheckPosition);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", curCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
        curCheckPosition = index;
        String description = model.films.getValue().get(index).openingCrawl;


        if (dualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            getListView().setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
            DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details);
            if (details == null || details.getShownIndex() != index) {
                // Make new fragment to show this selection.
                details = DetailsFragment.newInstance(index);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                // if (index == 0) {
                    ft.replace(R.id.details, details);
                // } else {
                    //ft.replace(R.id.a_item, details);
                // }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent(getActivity(), DisplayMessageActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE, description);

            startActivity(intent);
        }
    }
}
