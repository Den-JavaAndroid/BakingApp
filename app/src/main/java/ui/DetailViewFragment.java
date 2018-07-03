package ui;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.denx7.ui.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import recipes.Step;
import utils.NetworkUtils;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;


public class DetailViewFragment extends Fragment {

    private Step step;
    @BindView(R.id.instructions)
    TextView instructions;
    @BindView(R.id.media_player)
    SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.player_container)
    LinearLayout playerContainer;
    @BindView(R.id.thumbnailImage)
    ImageView thumbnailImage;

    private boolean playWhenReady = true;
    private long playbackPosition;
    private final static String PLAY_WHEN_READY = "playWhenReady";
    private final static String PLAYBACK_POSITION = "playbackPosition";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_view, container, false);
        ButterKnife.bind(this, view);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fullScreen();
            playerContainer.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT)
            );
        }

            if (savedInstanceState == null) {
                if (!TextUtils.isEmpty(step.getVideoURL()) && NetworkUtils.isOnline(getContext())) {
                initializePlayer();
                } else {
                    mPlayerView.setVisibility(View.GONE);
                }
            } else {
                playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
                playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
                step = (Step) savedInstanceState.getSerializable("savedStep");
                if (!TextUtils.isEmpty(step.getVideoURL())) {
                initializePlayer();
                } else {
                    mPlayerView.setVisibility(View.GONE);
                }
            }

            instructions.setText(step.getDescription());
            setThumbnailImage();

        return view;


    }

    public void setStep(Step step) {
        this.step = step;
    }

    private void initializePlayer() {
        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector(), new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(step.getVideoURL()), new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(playbackPosition);
        }
    }

    private void releasePlayer() {
        if (!TextUtils.isEmpty(step.getVideoURL()) && mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void setThumbnailImage() {
        String recipeImage = step.getThumbnailURL();
        if (!TextUtils.isEmpty(recipeImage)) {
            thumbnailImage.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(recipeImage)
                    .placeholder(R.drawable.recipe_placeholder)
                    .error(R.drawable.not_found)
                    .into(thumbnailImage);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("savedStep", step);
        outState.putBoolean(PLAY_WHEN_READY, mExoPlayer.getPlayWhenReady());
        outState.putLong(PLAYBACK_POSITION, mExoPlayer.getCurrentPosition());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void hideSystemUI() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
        getActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void fullScreen() {
        hideSystemUI();
        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
    }





}
