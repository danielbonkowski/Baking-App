package android.example.com.bakingapp.listingModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SimpleStep implements Serializable {

    private final int id;
    private final String shortDescription;
    private final String description;
    @SerializedName("videoURL")
    private final String videoUrl;
    @SerializedName("thumbnailURL")
    private final String thumbnailUrl;

    public SimpleStep(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
