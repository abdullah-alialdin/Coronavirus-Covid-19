package abdullah.alialdin.coronavirus.utils;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import abdullah.alialdin.MyApplication;
import abdullah.alialdin.coronavirus.pojo.CountryDataModel;

public class CountryRepository {

    @NonNull
    private MutableLiveData<List<CountryDataModel>> myLiveData = new MutableLiveData<>();
    private static CountryRepository instance;
    private final String BASE_URL = "https://corona.lmao.ninja/countries?";
    private String sortingMethod = "cases";

    public static CountryRepository getInstance() {
        if(instance == null) {
            synchronized (CasesRepository.class) {
                if(instance == null) {
                    instance = new CountryRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<CountryDataModel>> getMyLiveData() {
        List<CountryDataModel> countryList = new ArrayList<>();
        String url = buildUrl(BASE_URL);
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject currentCountry = response.getJSONObject(i);
                                String countryName = currentCountry.getString("country");
                                int totalCases = currentCountry.getInt("cases");
                                int deaths = currentCountry.getInt("deaths");
                                int recovered = currentCountry.getInt("recovered");
                                int todayCases = currentCountry.getInt("todayCases");
                                int todayDeaths = currentCountry.getInt("todayDeaths");
                                CountryDataModel country = new CountryDataModel(countryName
                                , totalCases, deaths, recovered, todayCases, todayDeaths);
                                Log.v("TestList", country.toString());
                                countryList.add(country);
                            }
                            myLiveData.setValue(countryList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonArrayRequest);
        return myLiveData;
    }

    private String buildUrl(String url){
        Uri baseUri = Uri.parse(url);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("sort", sortingMethod);
        return uriBuilder.toString();
    }
}
