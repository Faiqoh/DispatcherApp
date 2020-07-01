package com.example.appdispatcher.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.appdispatcher.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountFragment extends Fragment {

    NestedScrollView nestedScrollView;
    BottomNavigationView navigation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        nestedScrollView = root.findViewById(R.id.nestedaccount);
        navigation = getActivity().findViewById(R.id.nav_view);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            boolean isNavigationHide = false;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < oldScrollY) { // up
                    animateNavigation(false);
                }
                if (scrollY > oldScrollY) { // down
                    animateNavigation(true);
                }
            }

            private void animateNavigation(boolean hide) {
                if (isNavigationHide && hide || !isNavigationHide && !hide) return;
                isNavigationHide = hide;
                int moveY = hide ? (2 * navigation.getHeight()) : 0;
                navigation.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
            }
        });

        return root;
    }

}
