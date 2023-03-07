package io.takima.master3.store.article.models;

public class VideoGameBuilder {
    private Product p;
    private VideoGame v;
    private String developer;
    private String platform;
    private int pegi;

    public VideoGameBuilder setP(Product p) {
        this.p = p;
        return this;
    }

    public VideoGameBuilder setV(VideoGame v) {
        this.v = v;
        return this;
    }

    public VideoGameBuilder setDeveloper(String developer) {
        this.developer = developer;
        return this;
    }

    public VideoGameBuilder setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public VideoGameBuilder setPegi(int pegi) {
        this.pegi = pegi;
        return this;
    }

    public VideoGame createVideoGame() {
        return new VideoGBuilder().setP(p).setV(v).createVideoGame();
    }
}