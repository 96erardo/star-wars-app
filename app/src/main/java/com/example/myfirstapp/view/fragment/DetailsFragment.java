package com.example.myfirstapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myfirstapp.view.MainActivity;
import com.example.myfirstapp.view.model.MoviesListViewModel;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.w3c.dom.Text;

public class DetailsFragment extends Fragment {
    private final int TEXT_VIEW_ID = 1;

    public static DetailsFragment newInstance (int index) {
        DetailsFragment f = new DetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist. The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // isn't displayed. Note this isn't needed -- we could just
            // run the code below, where we would create and return the
            // view hierarchy; it would just never be used.
            return null;
        }

        // Getting arguments when created
        Bundle bundle = getArguments();
        String description = bundle.getString(MainActivity.EXTRA_MESSAGE, null);
        int index = bundle.getInt("index", -1);

        ScrollView scroller = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());
        text.setPadding(padding, padding, padding, padding);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        if (description != null) {
            text.setText(description);
        } else {
            MoviesListViewModel model = ViewModelProviders.of(getActivity()).get(MoviesListViewModel.class);
            text.setText(model.films.getValue().get(index).openingCrawl);
        }

        scroller.addView(text);

        return scroller;
    }
}
