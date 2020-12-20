package ru.moshik.tm.ripper.quoters;

import ru.moshik.tm.ripper.annotations.InjectRandomInt;
import ru.moshik.tm.ripper.annotations.PostProxy;
import ru.moshik.tm.ripper.annotations.Profiling;

import javax.annotation.PostConstruct;

@Profiling
@DeprecatedClass(newImpl = T1000.class)
public class TerminatorQuoter implements Quoter {

    @InjectRandomInt(min = 1, max = 7)
    private int repeat;

    private String message;

    TerminatorQuoter() {
        System.out.println("Phase 1");
        System.out.println("repeat=" + repeat);
    }

    @PostConstruct
    public void init() {
        System.out.println("Phase 2");
        System.out.println("repeat=" + repeat);
    }

    @PostProxy
    public void sayQuote() {
        System.out.println("Phase 3");
        for (int i = 0; i < repeat; i++) {
            System.out.println("message = " + message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
}
