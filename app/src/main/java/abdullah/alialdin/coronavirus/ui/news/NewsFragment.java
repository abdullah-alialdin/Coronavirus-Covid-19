package abdullah.alialdin.coronavirus.ui.news;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

import abdullah.alialdin.coronavirus.R;
import abdullah.alialdin.coronavirus.pojo.NewsModel;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private NewsViewModel newsViewModel;
    private InterstitialAd mInterstitialAd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }
        });
        AdView adView = root.findViewById(R.id.adView);
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        refreshList();
        mySwipeRefreshLayout = root.findViewById(R.id.swipe_refresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshList();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        return root;
    }

    private void refreshList(){
        newsViewModel.getMyLiveData().observe(getViewLifecycleOwner(), new Observer<List<NewsModel>>() {
            @Override
            public void onChanged(List<NewsModel> newsModels) {
                newsAdapter = new NewsAdapter(getContext(), newsModels);
                recyclerView.setAdapter(newsAdapter);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            refreshList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
