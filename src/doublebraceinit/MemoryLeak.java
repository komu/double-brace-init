package doublebraceinit;

import java.util.ArrayList;
import java.util.List;

/**
 * Processor that creates summary information from big batches of data.
 * Unfortunately the summary information leaks the original batch, thus
 * causing the program to die in OutOfMemoryError.
 */
public final class MemoryLeak {

    static class BatchStatistics {
        public int length;
    }

    static BatchStatistics processBatch(byte[] batchData) {
        // Inner class will keep a reference to batchData and make it
        // non-reclaimable even though we don't need it after initialization.
        return new BatchStatistics() {{
            length = batchData.length;
        }};
    }

    public static void main(String[] args) {
        List<BatchStatistics> stats = new ArrayList<>();

        for (int i = 0; i < 100_000; i++) {
            if (i % 1000 == 0)
                System.out.println(i);

            stats.add(processBatch(new byte[100_000]));
        }

        // We never actually get here because we run out of memory
        System.out.println("stats: " + stats);
    }
}
