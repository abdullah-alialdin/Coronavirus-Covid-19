package abdullah.alialdin.coronavirus.ui.reports;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import abdullah.alialdin.coronavirus.R;

public class ReportFragment extends Fragment {

    private WebView mWebView;
    private TextView noDataTV;
    private ProgressBar progressBar;
    private String fetchedUrl;
    private InterstitialAd mInterstitialAd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_report, container, false);
        AdView adView = root.findViewById(R.id.adView);
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
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
        mWebView = root.findViewById(R.id.webview);
        mWebView.setVisibility(View.INVISIBLE);
        noDataTV = root.findViewById(R.id.noData_textView);
        noDataTV.setText(R.string.loading);
        progressBar = root.findViewById(R.id.progress_bar);
        networking();
        return root;
    }

    private void networking() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reportRef = rootRef.child("report");
        {
            reportRef.child("url").addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetJavaScriptEnabled")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        fetchedUrl = dataSnapshot.getValue(String.class);
                        final String googleDocsUrl = "http://docs.google.com/viewer?url=";
                        mWebView.getSettings().setJavaScriptEnabled(true);
                        mWebView.setWebViewClient(new WebViewClient() {
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return false;
                            }
                        });
                        mWebView.loadUrl((googleDocsUrl + fetchedUrl));
                        mWebView.setWebChromeClient(new WebChromeClient() {
                            public void onProgressChanged(WebView view, int progress) {
                                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                                    noDataTV.setVisibility(View.VISIBLE);
                                }
                                progressBar.setProgress(progress);
                                if (progress == 100) {
                                    mWebView.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(ProgressBar.GONE);
                                    noDataTV.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else {
                        mWebView.setVisibility(View.INVISIBLE);
                        noDataTV.setText("No data found");
                        noDataTV.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
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
