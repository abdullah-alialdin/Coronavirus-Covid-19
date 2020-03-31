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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import abdullah.alialdin.MyApplication;
import abdullah.alialdin.coronavirus.R;
import abdullah.alialdin.coronavirus.pojo.NewsModel;

public class NewsRepository {
    private static final String LOG_TAG = NewsRepository.class.getSimpleName();
    private static NewsRepository instance;
    private final String NEWS_URL = "https://newsapi.org/v2/everything?";
    private String searchQuery = "coronavirus";
    private String pageSorting = "publishedAt";
    private String pageSize = "100";
    private String language = MyApplication.getContext().getString(R.string.search_lang);
    private String apiKey = "api_key";

    @NonNull
    private MutableLiveData<List<NewsModel>> myLiveData = new MutableLiveData<>();
    private ArrayList<NewsModel> newsFeed = new ArrayList<>();

    public static NewsRepository getInstance() {
        if(instance == null) {
            synchronized (CasesRepository.class) {
                if(instance == null) {
                    instance = new NewsRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<NewsModel>> getMyLiveData() {
        String url = buildUrl(NEWS_URL);
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(LOG_TAG, response.toString());
                        try {
                            JSONArray newsArray = response.getJSONArray("articles");
                            for (int i = 0; i < newsArray.length(); i++) {
                                JSONObject currentNews = newsArray.getJSONObject(i);
                                String title = currentNews.getString("title");
                                String date = currentNews.getString("publishedAt");
                                String url = currentNews.getString("url");
                                String thumbUrl = currentNews.getString("urlToImage");
                                JSONObject sourceObject = currentNews.getJSONObject("source");
                                String sourceName = sourceObject.getString("name");
                                Log.v(LOG_TAG, title + date+ url+ thumbUrl);
                                NewsModel newsItem = new NewsModel(title, date, url, thumbUrl, sourceName);
                                newsFeed.add(newsItem);
                            }
                            myLiveData.setValue(newsFeed);
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
        return myLiveData;
    }

    private String buildUrl(String url){
        Uri baseUri = Uri.parse(url);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", searchQuery);
        uriBuilder.appendQueryParameter("language", language);
        uriBuilder.appendQueryParameter("sortBy", pageSorting);
        uriBuilder.appendQueryParameter("pageSize", pageSize);
        uriBuilder.appendQueryParameter("apiKey", apiKey);
        Log.v("fuck", uriBuilder.toString());
        return uriBuilder.toString();
    }

}
