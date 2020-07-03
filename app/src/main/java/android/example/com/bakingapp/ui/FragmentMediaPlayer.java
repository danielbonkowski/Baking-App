package android.example.com.bakingapp.ui;

import android.example.com.bakingapp.R;
import android.example.com.bakingapp.databinding.FragmentMediaPlayerBinding;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class FragmentMediaPlayer extends Fragment {

    private String mVideoUrl;

    public FragmentMediaPlayer(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentMediaPlayerBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_media_player, container, false);

        binding.mediaPlayerTextView.setText(mVideoUrl);

        return binding.getRoot();
    }

    public void setVideoUrl(String urlString){
        mVideoUrl = urlString;
    }
}
