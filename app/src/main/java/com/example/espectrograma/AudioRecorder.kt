package com.example.espectrograma

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.content.ContextCompat

class AudioRecorder(
    private val context: Context
) {

    companion object {
        const val SAMPLE_RATE = 44100
    }

    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT

    private val minBufferSize = AudioRecord.getMinBufferSize(
        SAMPLE_RATE,
        channelConfig,
        audioFormat
    )

    private val bufferSize = maxOf(
        minBufferSize,
        4096
    )

    private var audioRecord: AudioRecord? = null

    @Volatile
    private var isRecording = false

    private var recordingThread: Thread? = null

    fun start(onAudioData: (ShortArray) -> Unit) {

        if (isRecording) return

        val permissionGranted =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED

        if (!permissionGranted) {
            return
        }

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            channelConfig,
            audioFormat,
            bufferSize
        )

        val recorder = audioRecord ?: return

        isRecording = true

        recorder.startRecording()

        recordingThread = Thread {

            val buffer = ShortArray(bufferSize)

            while (isRecording) {

                val read = recorder.read(
                    buffer,
                    0,
                    buffer.size
                )

                if (read > 0) {
                    onAudioData(buffer.copyOf(read))
                }
            }

        }

        recordingThread?.start()
    }

    fun stop() {

        if (!isRecording) return

        isRecording = false

        try {
            recordingThread?.join()
        } catch (_: InterruptedException) {
        }

        audioRecord?.let {

            if (it.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                it.stop()
            }

            it.release()
        }

        audioRecord = null
    }

    fun release() {
        stop()
    }
}