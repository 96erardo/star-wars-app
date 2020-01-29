package com.example.myfirstapp.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.view.MainActivity;
import com.example.myfirstapp.view.adapters.FilmGridAdapter;
import com.example.myfirstapp.view.model.MoviesListViewModel;

import javax.inject.Inject;

public class MoviesListFragment extends Fragment {
    @Inject
    public MoviesListViewModel model;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    boolean dualPane;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MainActivity) getActivity()).moviesComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout constraintLayout =  (ConstraintLayout) inflater.inflate(R.layout.films_fragment, container, false);
        recyclerView = constraintLayout.findViewById(R.id.recycler_view);

        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        String [] titles = {};

//        adapter = new FilmGridAdapter(titles);
//        recyclerView.setAdapter(adapter);

        return constraintLayout;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        model.fetchFilms();

        model.films.observe(this, films -> {
            String covers[] = new String[films.size()];

            for (int i = 0; i < covers.length; i++) {
                System.out.println(films.get(i).title);
                covers[i] = films.get(i).cover;
            }

            adapter = new FilmGridAdapter(covers);
            recyclerView.setAdapter(adapter);

//            setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, titles));
//
//            View detailsFrame = getActivity().findViewById(R.id.detailAction);
//            dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
//
//            System.out.println("dualPane: " + dualPane);
//
//            if (dualPane) {
//                // In dual-pane mode, the list view highlights the selected item.
//                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//                // Make sure our UI is in the correct state.
//                showDetails(model.selected);
//            }
        });
    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        model.selected = model.films.getValue().get(position).id;
//
//        if (dualPane) {
//            showDetails(position);
//        } else {
//            MoviesListFragmentDirections.DetailAction action = MoviesListFragmentDirections.detailAction();
//            action.setIndex(position);
//            Navigation.findNavController(v).navigate(action);
//        }
//    }
//
//    void showDetails(int index) {
//        getListView().setItemChecked(index, true);
//
//        DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.detailAction);
//
//        if (details == null || details.getShownIndex() != index) {
//            details = DetailsFragment.newInstance(index);
//
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.replace(R.id.detailAction, details);
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            ft.commit();
//        }
//    }
}
