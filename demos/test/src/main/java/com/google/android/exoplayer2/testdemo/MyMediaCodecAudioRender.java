package com.google.android.exoplayer2.testdemo;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.audio.AudioCapabilities;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;

public class MyMediaCodecAudioRender extends MediaCodecAudioRenderer {
    public MyMediaCodecAudioRender(Context context, MediaCodecSelector mediaCodecSelector) {
        super(context, mediaCodecSelector);
    }

    public MyMediaCodecAudioRender(Context context, MediaCodecSelector mediaCodecSelector, @Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener) {
        super(context, mediaCodecSelector, eventHandler, eventListener);
    }

    public MyMediaCodecAudioRender(Context context, MediaCodecSelector mediaCodecSelector, @Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener, @Nullable AudioCapabilities audioCapabilities, AudioProcessor... audioProcessors) {
        super(context, mediaCodecSelector, eventHandler, eventListener, audioCapabilities, audioProcessors);
    }

    public MyMediaCodecAudioRender(Context context, MediaCodecSelector mediaCodecSelector, @Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener, AudioSink audioSink) {
        super(context, mediaCodecSelector, eventHandler, eventListener, audioSink);
    }

    public MyMediaCodecAudioRender(Context context, MediaCodecSelector mediaCodecSelector, boolean enableDecoderFallback, @Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener, AudioSink audioSink) {
        super(context, mediaCodecSelector, enableDecoderFallback, eventHandler, eventListener, audioSink);
    }
}
