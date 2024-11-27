package show.miku.simpletimer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timeDisplay;
    private Button startButton, stopButton, clearButton;

    boolean isTiming = false;
    long startTime = 0L;
    long currentTime = 0L;
    long time = 0L;

    private final Handler timerHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        startButton.setOnClickListener(v -> start());
        stopButton.setOnClickListener(v -> stop());
        clearButton.setOnClickListener(v -> clear());
    }

    private void init(){
        timeDisplay = findViewById(R.id.time_display);
        startButton = findViewById(R.id.button_start);
        stopButton = findViewById(R.id.button_stop);
        clearButton = findViewById(R.id.button_clear);
    }

    private void start(){
        if(isTiming) return;
        isTiming = true;
        buttonStatusToggle();
        startTime = System.currentTimeMillis() - time;

        timerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isTiming){
                    currentTime = System.currentTimeMillis();
                    time = currentTime - startTime;
                    update();
                    timerHandler.postDelayed(this, 1);
                }
            }
        }, 1);
    }

    private void stop() {
        if (!isTiming) return;
        buttonStatusToggle();
        isTiming = false;
        time = currentTime - startTime;
    }

    private void clear(){
        buttonStatusToggle();
        isTiming = false;
        startTime = currentTime = time = 0L;
        update();
    }

    private void update(){
        long ms = time % 1000 / 10;
        long ss = (time / 1000) % 60;
        long mm = (time / 1000 - ss) / 60;
        @SuppressLint("DefaultLocale") String str = String.format("%02d:%02d:%02d", mm, ss, ms);
        timeDisplay.setText(str);
    }

    private void buttonStatusToggle(){
        startButton.setEnabled(!startButton.isEnabled());
        stopButton.setEnabled(!stopButton.isEnabled());
    }
}