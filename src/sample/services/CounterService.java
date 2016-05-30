package sample.services;

import lombok.Setter;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class CounterService extends TimerTask{
    private Duration duration =Duration.ofSeconds(0);
    private Duration targetDuration = Duration.ofSeconds(0);
    private boolean timeOutEnable=false;
    private boolean timerRun=false;
    private Timer timer1 = new Timer();

    @Setter
    public Consumer<Duration> onTick;

    @Setter
    public Consumer<Duration> onTimeOut;

    public CounterService(){
        timerRun=false;
        timer1.schedule(this, 0, 1000);
    }

    public void startTiming(){
        timerRun=true;
    }
    public void stopTiming(){
        timerRun=false;
    }

    public void enableTimeOutMode(Duration timeOut){
        reset();
        targetDuration=timeOut;
        timeOutEnable=true;
    }

    public void disableTimeOutMode(){
        reset();
        timeOutEnable=false;
    }

    public void reset(){
        timerRun=false;
        duration=Duration.ofSeconds(0);
        timerRun=true;
    }

    public void run(){
        if(timerRun) {
            duration = duration.plusSeconds(1);

            if (timeOutEnable && !duration.minus(targetDuration).isNegative()) {
                if (onTimeOut != null) {
                    onTimeOut.accept(duration);
                }
                stopTiming();
            }
        }

        if (onTick != null) {
            onTick.accept(duration);
        }
    }
}

