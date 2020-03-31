package abdullah.alialdin.coronavirus.pojo;

public class NewsModel {

    private String publishedAt;
    private String urlToImage;
    private String title;
    private String url;
    private String source;


    public NewsModel(String title, String date, String url, String imageUrl, String source){
        this.title = title;
        this.publishedAt = date;
        this.url = url;
        this.urlToImage = imageUrl;
        this.source = source;
    }


    public String getPublishedAt() {
        return publishedAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getSource(){
        return source;
    }

}
