package abdullah.alialdin.coronavirus.pojo;

public class CountryDataModel {

    private String countryName;
    private int totalCases;
    private int deaths;
    private int recovered;
    private int todayCases;
    private int todayDeaths;

    public CountryDataModel(String countryName, int totalCases, int deaths, int recovered,
                            int todayCases, int todayDeaths) {
        this.countryName = countryName;
        this.totalCases = totalCases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.todayCases = todayCases;
        this.todayDeaths = todayDeaths;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getTodayCases() {
        return todayCases;
    }

    public int getTodayDeaths() {
        return todayDeaths;
    }
}
