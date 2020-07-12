package android.example.com.bakingapp.network;

import com.google.android.exoplayer2.ExoPlayer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor networkIO;

    public AppExecutors(Executor diskIO, Executor networkIO) {

        this.diskIO = diskIO;
        this.networkIO = networkIO;
    }

    public static AppExecutors getInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3));
            }
        }
        return sInstance;
    }

    public Executor diskIO() {return diskIO; }
    public Executor networkIO() {return networkIO; }
}
