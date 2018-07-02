package ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import recipes.Step;
import utils.NetworkUtils;


public class DetailViewFragment extends Fragment {

    private Step step;
    @BindView(R.id.instructions)
    TextView instructions;
    @BindView(R.id.media_player)
    SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.thumbnailImage)
    ImageView thumbnailImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_view, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState == null) {
            if (!TextUtils.isEmpty(step.getVideoURL()) && NetworkUtils.isOnline(getContext())) {
                initializePlayer(Uri.parse(step.getVideoURL()));
            } else {
                mPlayerView.setVisibility(View.GONE);
            }
            instructions.setText(step.getDescription());
            setThumbnailImage();
        } else {
            step = (Step) savedInstanceState.getSerializable("savedStep");
            if (!TextUtils.isEmpty(step.getVideoURL())) {
                initializePlayer(Uri.parse(step.getVideoURL()));
            } else {
                mPlayerView.setVisibility(View.GONE);
            }
            instructions.setText(step.getDescription());
            setThumbnailImage();
        }

        return view;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (!TextUtils.isEmpty(step.getVideoURL()) && mExoPlayer != null) {
            mExoPlayer.stop();
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (step != null)
            releasePlayer();
    }


}
