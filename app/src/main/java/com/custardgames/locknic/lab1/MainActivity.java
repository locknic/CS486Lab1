package com.custardgames.locknic.lab1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Face;

import org.w3c.dom.Text;

import java.util.List;


public class MainActivity extends AppCompatActivity implements CameraDetector.CameraEventListener, CameraDetector.ImageListener {
    private SlidingWindow joyWindow;
    private SlidingWindowTimestamps joyTimestamps;

    private TextView joyThreshold;
    private TextView joyThresholdWeighted;
    private TextView getJoyThresholdTimestamps;

    SurfaceView cameraDetectorSurfaceView;
    CameraDetector cameraDetector;

    int maxProcessingRate = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraDetectorSurfaceView = (SurfaceView) findViewById(R.id.cameraDetectorSurfaceView);

        cameraDetector = new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, cameraDetectorSurfaceView);

        cameraDetector.setMaxProcessRate(maxProcessingRate);

        cameraDetector.setImageListener(this);
        cameraDetector.setOnCameraEventListener(this);

        cameraDetector.setDetectAllEmotions(true);

        cameraDetector.start();

        joyWindow = new SlidingWindow(100);
        joyTimestamps = new SlidingWindowTimestamps();

        joyThreshold = (TextView) findViewById(R.id.joyThreshold);
        joyThresholdWeighted = (TextView) findViewById(R.id.joyThresholdWeighted);
        getJoyThresholdTimestamps = (TextView) findViewById(R.id.joyThresholdTimestamp);
    }

    @Override
    public void onCameraSizeSelected(int cameraHeight, int cameraWidth, Frame.ROTATE rotate) {
        ViewGroup.LayoutParams params = cameraDetectorSurfaceView.getLayoutParams();

        params.height = cameraHeight;
        params.width = cameraWidth;

        cameraDetectorSurfaceView.setLayoutParams(params);
    }

    @Override
    public void onImageResults(List<Face> faces, Frame frame, float timeStamp) {
        if (faces == null)
            return; //frame was not processed

        if (faces.size() == 0)
            return; //no face found

        Face face = faces.get(0);

        float joy = face.emotions.getJoy();
        float anger = face.emotions.getAnger();
        float surprise = face.emotions.getSurprise();

        joyWindow.add(joy);
        joyTimestamps.add(joy, timeStamp);

        float mean = joyWindow.getMean();
        if (mean > 40) {
            joyThreshold.setVisibility(View.VISIBLE);
        } else {
            joyThreshold.setVisibility(View.INVISIBLE);
        }

        float weighted = joyWindow.getWeighted();
        if (weighted > 40) {
            joyThresholdWeighted.setVisibility(View.VISIBLE);
        } else {
            joyThresholdWeighted.setVisibility(View.INVISIBLE);
        }

        float meanTimestamp = joyTimestamps.getMean();
        if (meanTimestamp > 40) {
            getJoyThresholdTimestamps.setVisibility(View.VISIBLE);
        } else {
            getJoyThresholdTimestamps.setVisibility(View.INVISIBLE);
        }

        System.out.println("Joy: " + joy);
        System.out.println("Anger: " + anger);
        System.out.println("Surprise: " + surprise);

        System.out.println("Joy mean: " + mean);
        System.out.println("Joy weighted: " + weighted);
        System.out.println("Joy weighted timestamp: " + weighted);
    }
}
