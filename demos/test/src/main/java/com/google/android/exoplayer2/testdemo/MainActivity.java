/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.exoplayer2.testdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.surfacedemo.R;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import java.io.File;
import java.util.UUID;

/**
 * Activity that demonstrates use of {@link SurfaceControl} with ExoPlayer.
 */
public final class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final String DEFAULT_MEDIA_URI =
            "https://storage.googleapis.com/exoplayer-test-media-1/mkv/android-screens-lavf-56.36.100-aac-avc-main-1280x720.mkv";
    private static final String SURFACE_CONTROL_NAME = "surfacedemo";

    private static final String ACTION_VIEW = "com.google.android.exoplayer.surfacedemo.action.VIEW";
    private static final String EXTENSION_EXTRA = "extension";
    private static final String DRM_SCHEME_EXTRA = "drm_scheme";
    private static final String DRM_LICENSE_URL_EXTRA = "drm_license_url";
    private static final String OWNER_EXTRA = "owner";

    private boolean isOwner;
    /*    @Nullable
        private PlayerControlView playerControlView;*/
    @Nullable
    private SurfaceView fullScreenView;
    @Nullable
    private SurfaceView nonFullScreenView;
    @Nullable
    private SurfaceView currentOutputView;

    @Nullable
    private static SimpleExoPlayer player;
    @Nullable
    private static SurfaceControl surfaceControl;
    @Nullable
    private static Surface videoSurface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
//        playerControlView = findViewById(R.id.player_control_view);
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        new Handler().postDelayed(()-> {
            initPlayer();
        }, 2000);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }

    private void initPlayer() {
        if (isFinishing()) return;
        AspectRatioFrameLayout layout = findViewById(R.id.layout_aspect_Ratio);
        player = new SimpleExoPlayer.Builder(this).build();
        player.setMediaSource(new DefaultMediaSourceFactory(this).createMediaSource(MediaItem
                .fromUri(Uri.fromFile(
                        new File("/sdcard/DCIM/Camera/song.mp4")))));
        MySurfaceView surfaceView = findViewById(R.id.surface_view);
        surfaceView.getHolder().addCallback(surfaceView);
        player.setVideoSurfaceView(surfaceView);
        player.prepare();
        player.play();
        player.addVideoListener(new VideoListener() {
            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
                    float pixelWidthHeightRatio) {
                Log.d(TAG, "onVideoSizeChanged: width = " + width + " height = " + height);
                float videoAspectRatio =
                        (height == 0 || width == 0) ? 1 : (width * pixelWidthHeightRatio) / height;
                layout.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                layout.setAspectRatio(videoAspectRatio);
            }

            @Override
            public void onSurfaceSizeChanged(int width, int height) {
                Log.d(TAG, "onSurfaceSizeChanged: width = " + width + " height = " + height);
            }
        });
        PlayerControlView playerControlView = findViewById(R.id.surface_view_control);
        playerControlView.setPlayer(player);
        playerControlView.show();
        findViewById(R.id.button).setOnClickListener(v -> {
            if (playerControlView.isVisible()) {
                playerControlView.hide();
            } else {
                playerControlView.show();
            }
        });
        findViewById(R.id.button_black).setOnClickListener(v -> {
            surfaceView.black();
        });
    }

}
