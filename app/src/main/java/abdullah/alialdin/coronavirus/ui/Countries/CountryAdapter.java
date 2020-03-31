package abdullah.alialdin.coronavirus.ui.Countries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import abdullah.alialdin.coronavirus.R;
import abdullah.alialdin.coronavirus.pojo.CountryDataModel;
import butterknife.BindView;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private List<CountryDataModel> countryList;
    private Context mContext;


    public CountryAdapter(Context context, List<CountryDataModel> countryList) {
        this.mContext = context;
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_item, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        CountryDataModel countryModel = countryList.get(position);
        holder.countryName.setText(countryModel.getCountryName());
        holder.totalCasesNo.setText(String.valueOf(countryModel.getTotalCases()));
        holder.deathsNo.setText(String.valueOf(countryModel.getDeaths()));
        holder.recoveredNo.setText(String.valueOf(countryModel.getRecovered()));
        holder.newCasesNo.setText(String.valueOf(countryModel.getTodayCases()));
        holder.newDeathsNo.setText(String.valueOf(countryModel.getTodayDeaths()));
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {

        TextView countryName, totalCasesNo, deathsNo, recoveredNo, newCasesNo, newDeathsNo;

        CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.country_name);
            totalCasesNo = itemView.findViewById(R.id.totalCasesNo);
            deathsNo = itemView.findViewById(R.id.deathsNo);
            recoveredNo = itemView.findViewById(R.id.recoveredNo);
            newCasesNo = itemView.findViewById(R.id.newCasesNo);
            newDeathsNo = itemView.findViewById(R.id.newDeathsNo);
        }
    }
}