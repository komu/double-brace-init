package doublebraceinit;

import java.util.ArrayList;
import java.util.List;

/**
 * Processor that creates summary information from big batches of data.
 * Unfortunately the summary information leaks the original batch, thus
 * causing the program to die in OutOfMemoryError.
 */
public final class MemoryLeak {

    static final class BatchProcessor {
        private final String batchData;

        public BatchProcessor(String batchData) {
            this.batchData = batchData;
        }

        public BatchStatistics leakyProcess() {
            // The inner class referenced by double brace initialization will refer the processor
            // and make 'batchData' non-reclaimable even though we don't need it anymore.
            return new BatchStatistics() {{
                length = batchData.length();
            }};
        }
    }

    static class BatchStatistics {
        public int length;
    }

    private static String createBigString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append("x");
        return sb.toString();
    }

    public static void main(String[] args) {
        List<BatchStatistics> stats = new ArrayList<>();

        for (int i = 0; i < 100_000; i++) {
            if (i % 1000 == 0)
                System.out.println(i);
            String data = createBigString(100_000);
            stats.add(new BatchProcessor(data).leakyProcess());
        }

        // We never actually get here because we run out of memory
        System.out.println("stats: " + stats);
    }
}
