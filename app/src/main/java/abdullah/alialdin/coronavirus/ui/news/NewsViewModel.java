package abdullah.alialdin.coronavirus.ui.news;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import abdullah.alialdin.coronavirus.pojo.NewsModel;
import abdullah.alialdin.coronavirus.utils.NewsRepository;


public class NewsViewModel extends AndroidViewModel {

    @NonNull
    private LiveData<List<NewsModel>> myLiveData;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        NewsRepository repo = NewsRepository.getInstance();
        myLiveData = repo.getMyLiveData();
    }

    @NonNull
    LiveData<List<NewsModel>> getMyLiveData() {
        return myLiveData;
    }
}
