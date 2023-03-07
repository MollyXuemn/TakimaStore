package io.takima.master3.store.article.models;

public class VideoGBuilder {
    private Product p;
    private VideoGame v;
    private String developer;
    private String platform;
    private int pegi;

    public VideoGBuilder setP(Product p) {
        this.p = p;
        return this;
    }

    public VideoGBuilder setV(VideoGame v) {
        this.v = v;
        return this;
    }

    public VideoGBuilder setDeveloper(String developer) {
        this.developer = developer;
        return this;
    }

    public VideoGBuilder setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public VideoGBuilder setPegi(int pegi) {
        this.pegi = pegi;
        return this;
    }

    public VideoGame createVideoGame() {
        return new VideoGame(p, v);
    }
}