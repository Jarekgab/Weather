package pl.nauka.jarek.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class WaitingFragmentActivity extends Fragment {

    ProgressBar pbWaiting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_waiting_fragment, container, false);

        pbWaiting = view.findViewById(R.id.pb_waiting);
        pbWaiting.setVisibility(View.VISIBLE);

        return view;
    }
}
