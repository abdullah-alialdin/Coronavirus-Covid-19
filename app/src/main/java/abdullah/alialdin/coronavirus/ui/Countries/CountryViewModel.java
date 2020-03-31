package abdullah.alialdin.coronavirus.ui.Countries;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import abdullah.alialdin.coronavirus.pojo.CountryDataModel;
import abdullah.alialdin.coronavirus.utils.CountryRepository;

public class CountryViewModel extends AndroidViewModel {

    @NonNull
    private LiveData<List<CountryDataModel>> myLiveData;

    public CountryViewModel(@NonNull Application application) {
        super(application);
        CountryRepository repo = CountryRepository.getInstance();
        myLiveData = repo.getMyLiveData();
    }

    @NonNull
    LiveData<List<CountryDataModel>> getMyLiveData() {
        return myLiveData;
    }
}
