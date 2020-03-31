package abdullah.alialdin.coronavirus.ui.totalCases;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


import java.util.Objects;

import abdullah.alialdin.coronavirus.utils.CasesRepository;
import abdullah.alialdin.coronavirus.R;

public class CasesFragment extends Fragment {

    private TextView confirmedNumbers, deathNumbers, confirmedLabel, deathLabel, recoveredNumbers, recoveredLabel;
    private ProgressBar loadingIndicator;
    private CasesViewModel viewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @SuppressLint({"SourceLockedOrientationActivity"})
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cases, container, false);
        confirmedNumbers = root.findViewById(R.id.confirmed_textView);
        deathNumbers = root.findViewById(R.id.death_textView);
        confirmedLabel = root.findViewById(R.id.confirmed_textView_label);
        deathLabel = root.findViewById(R.id.death_textView_label);
        recoveredNumbers = root.findViewById(R.id.recovered_textView);
        recoveredLabel = root.findViewById(R.id.recovered_textView_label);
        loadingIndicator = root.findViewById(R.id.progressBar);
        AdView adView = root.findViewById(R.id.adView);
        MobileAds.initialize(getContext(), initializationStatus -> {
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CasesViewModel.class);
        networking();
        return root;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void networking(){
        if (isNetworkConnected()){
            CasesRepository.getInstance().doSomeStuff();
            viewModel.getMyLiveData().observe(getViewLifecycleOwner(), strings -> {
                if (strings != null && !strings.isEmpty()){
                    loadingIndicator.setVisibility(View.INVISIBLE);
                    confirmedNumbers.setVisibility(View.VISIBLE);
                    deathNumbers.setVisibility(View.VISIBLE);
                    confirmedLabel.setVisibility(View.VISIBLE);
                    deathLabel.setVisibility(View.VISIBLE);
                    recoveredLabel.setVisibility(View.VISIBLE);
                    recoveredNumbers.setVisibility(View.VISIBLE);
                    confirmedNumbers.setText(String.valueOf(strings.get(0)));
                    deathNumbers.setText(String.valueOf(strings.get(1)));
                    recoveredNumbers.setText(String.valueOf(strings.get(2)));
                }else{
                    loadingIndicator.setVisibility(View.INVISIBLE);
                    confirmedNumbers.setVisibility(View.VISIBLE);
                    confirmedNumbers.setText(R.string.error_message);
                }
            });

        }else {
            loadingIndicator.setVisibility(View.INVISIBLE);
            confirmedNumbers.setText(R.string.no_connection_message);
            confirmedNumbers.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            networking();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
