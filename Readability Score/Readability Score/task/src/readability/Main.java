package readability;

import java.io.IOException;

public class Main {
    public static void main(String[] args)  {
        ReadabilityEstimator readabilityEstimator = new ReadabilityEstimator(args[0]);
        readabilityEstimator.run();
    }
}
