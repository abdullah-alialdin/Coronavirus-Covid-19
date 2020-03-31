package abdullah.alialdin.coronavirus.ui.totalCases;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import abdullah.alialdin.coronavirus.utils.CasesRepository;

public class CasesViewModel extends AndroidViewModel {

    @NonNull
    private LiveData<List<Long>> myLiveData;

    public CasesViewModel(@NonNull Application application) {
        super(application);
        CasesRepository repo = CasesRepository.getInstance();
        myLiveData = repo.getMyLiveData();
    }

    @NonNull
    LiveData<List<Long>> getMyLiveData() {
        return myLiveData;
    }
}