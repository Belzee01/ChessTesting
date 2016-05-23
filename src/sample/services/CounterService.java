package sample.services;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.parser.Scanner;
import lombok.Setter;

import java.time.Duration;
import java.util.TimerTask;
import java.util.Timer;
import java.util.function.Consumer;

/**
 * Created by krzysztof on 23.05.16.
 */
public class CounterService extends TimerTask{
    private Duration duration =Duration.ofSeconds(0);
    private Timer timer1 = new Timer();

    @Setter
    public Consumer<Duration> onTimeUp;

    public void startTiming(){
        timer1.schedule (this, 0, 1000);
    }
    public void stopTiming(){
        timer1.cancel();
    }


    public void reset(){
        duration=Duration.ofSeconds(0);
    }

    public void run(){
        duration=duration.plusSeconds(1);
        if(onTimeUp!=null){
            onTimeUp.accept(duration);
        }
    }
}

