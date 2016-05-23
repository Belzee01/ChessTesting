package sample.services;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.parser.Scanner;
import lombok.Setter;

import java.time.Duration;
import java.util.TimerTask;
import java.util.Timer;
import java.util.function.Consumer;
import java.lang.IllegalStateException;

/**
 * Created by krzysztof on 23.05.16.
 */
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

    public void startTiming(){
        if(timerRun) {
           stopTiming();
        }

        timer1.schedule(this, 0, 1000);
        timerRun=true;
    }
    public void stopTiming(){
        if(timerRun) {
            timer1.cancel();
            timerRun=false;
        }
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
        stopTiming();
        duration=Duration.ofSeconds(0);
        startTiming();
    }

    public void run(){
        duration=duration.plusSeconds(1);
        if(onTick!=null){
            onTick.accept(duration);
        }

        if(timeOutEnable && !duration.minus(targetDuration).isNegative() ){
            if(onTimeOut!=null) {
                onTimeOut.accept(duration);
            }
            stopTiming();
        }
    }
}

