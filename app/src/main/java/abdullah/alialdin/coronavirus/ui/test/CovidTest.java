package abdullah.alialdin.coronavirus.ui.test;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Objects;

import abdullah.alialdin.coronavirus.R;

public class CovidTest extends Fragment {

    private TextView question;
    private Button yesButton, noButton, startTesting;
    private EditText ageEntry;
    private int count, score, age;
    private InterstitialAd mInterstitialAd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("SourceLockedOrientationActivity")
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
        View root = inflater.inflate(R.layout.fragment_test, container, false);
        AdView adView = root.findViewById(R.id.adView);
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(Objects.requireNonNull(getContext()));
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        question = root.findViewById(R.id.question_text);
        yesButton = root.findViewById(R.id.yes_button);
        noButton = root.findViewById(R.id.no_button);
        ageEntry = root.findViewById(R.id.age_editText);
        startTesting = root.findViewById(R.id.age_button);
        ageEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                startTesting.setEnabled(!editable.toString().trim().isEmpty());
            }
        });
        startTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                age = Integer.parseInt(ageEntry.getText().toString());
                startTesting.setVisibility(View.INVISIBLE);
                ageEntry.setVisibility(View.INVISIBLE);
                question.setVisibility(View.VISIBLE);
                yesButton.setVisibility(View.VISIBLE);
                noButton.setVisibility(View.VISIBLE);
            }
        });

        question.setText(R.string.question_1);
        count = 0;
        score = 0;
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                setQuestion();
                if (question.getText() == getString(R.string.question_1)){
                    score += 5;
                }else if (question.getText() == getString(R.string.question_2)){
                    score += 4;
                }else if (question.getText() == getString(R.string.question_3)){
                    score += 3;
                }else if (question.getText() == getString(R.string.question_4)){
                    score += 2;
                }else if (question.getText() == getString(R.string.question_5)){
                    if (age > 12){
                        score += 2;
                    }else {
                        score += 1;
                    }
                }else if (question.getText() == getString(R.string.question_6)){
                    if (age > 12){
                        score += 2;
                    }else {
                        score += 1;
                    }
                }else if (question.getText() == getString(R.string.question_7)){
                    if (age > 12){
                        score += 2;
                    }else {
                        score += 1;
                    }
                }else if (question.getText() == getString(R.string.question_8)){
                    score += 1;
                }else if (question.getText() == getString(R.string.question_10)){
                    if (age > 12){
                        score += 1;
                    }
                }
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                setQuestion();
            }
        });
        return root;
    }

    private void setQuestion(){
        switch (count){
            case 1:
                question.setText(R.string.question_2);
                break;
            case 2:
                question.setText(R.string.question_3);
                break;
            case 3:
                question.setText(R.string.question_4);
                break;
            case 4:
                question.setText(R.string.question_5);
                break;
            case 5:
                question.setText(R.string.question_6);
                break;
            case 6:
                question.setText(R.string.question_7);
                break;
            case 7:
                question.setText(R.string.question_8);
                break;
            case 8:
                question.setText(R.string.question_9);
                break;
            case 9:
                question.setText(R.string.question_10);
                break;
            default:
                yesButton.setVisibility(View.INVISIBLE);
                noButton.setVisibility(View.INVISIBLE);
                question.setText(result());
        }
    }

    private String result(){
        String result;
        if (score >= 4 && score < 6){
            result = getString(R.string.result_1);
        }else if (score >= 6){
            result = getString(R.string.result_2);
        }else {
            result = getString(R.string.result_3);
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
