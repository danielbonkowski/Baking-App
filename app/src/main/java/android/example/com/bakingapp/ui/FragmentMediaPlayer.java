package android.example.com.bakingapp.ui;

import android.example.com.bakingapp.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentMediaPlayer extends Fragment {

    private String mVideoUrl;

    public FragmentMediaPlayer(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_media_player, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.media_player_text_view);

        textView.setText(mVideoUrl);

        return rootView;
    }

    public void setVideoUrl(String urlString){
        mVideoUrl = urlString;
    }
}
