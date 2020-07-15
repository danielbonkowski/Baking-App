package android.example.com.bakingapp.roomModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "step")
public class Step implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "step_id")
    private int stepId;
    @ColumnInfo(name = "parent_recipe_id")
    private final int parentRecipeId;
    @ColumnInfo(name = "short_description")
    private final String shortDescription;
    private final String description;
    @ColumnInfo(name = "video_url")
    private final String videoUrl;
    @ColumnInfo(name = "thumbnail_url")
    private final String thumbnailUrl;

    @Ignore
    public Step(int parentRecipeId, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.parentRecipeId = parentRecipeId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Step(int stepId, int parentRecipeId, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.stepId = stepId;
        this.parentRecipeId = parentRecipeId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getStepId() {
        return stepId;
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

    public int getParentRecipeId() { return parentRecipeId; }
}
