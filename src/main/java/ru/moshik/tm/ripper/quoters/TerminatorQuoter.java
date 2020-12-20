package ru.moshik.tm.ripper.quoters;

import ru.moshik.tm.ripper.annotations.InjectRandomInt;
import ru.moshik.tm.ripper.annotations.Profiling;

@Profiling
public class TerminatorQuoter implements Quoter {

    @InjectRandomInt(min = 1, max = 7)
    private int repeat;

    private String message;

    public void sayQuote() {
        for (int i = 0; i < repeat; i++) {
            System.out.println("message = " + message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
