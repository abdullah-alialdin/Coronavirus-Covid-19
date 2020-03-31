package abdullah.alialdin.coronavirus.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import abdullah.alialdin.MyApplication;

public class CasesRepository {
    private static CasesRepository instance;

    @NonNull
    private MutableLiveData<List<Long>> myLiveData = new MutableLiveData<>();

    public static CasesRepository getInstance() {
        if(instance == null) {
            synchronized (CasesRepository.class) {
                if(instance == null) {
                    instance = new CasesRepository();
                }
            }
        }
        return instance;
    }

    @NonNull
    public LiveData<List<Long>> getMyLiveData() {
        return myLiveData;
    }
    public void doSomeStuff() {
        String url = "https://corona.lmao.ninja/all";
        List<Long> cases = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            long totalCases = response.getLong("cases");
                            long deaths = response.getLong("deaths");
                            long recovered = response.getLong("recovered");
                            long date = response.getLong("updated");
                            cases.add(totalCases);
                            cases.add(deaths);
                            cases.add(recovered);
                            cases.add(date);
                            myLiveData.setValue(cases);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}
