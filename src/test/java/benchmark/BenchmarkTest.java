package benchmark;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class BenchmarkTest {

    @Mock
    BenchmarkDef benchmarkDefMock;

    @Before
    public void setUp() {
	initMocks(this);

	doNothing().when(benchmarkDefMock).beforeAll();
	doNothing().when(benchmarkDefMock).before();
	doNothing().when(benchmarkDefMock).after();
	doNothing().when(benchmarkDefMock).afterAll();
    }

    @Test
    public void shouldRunBenchmarkWithoutWarmupRuns() {
	executeBenchmark(0, 30);

	verifyInteractionsWithBenchmarkDef(0, 30);
    }

    @Test
    public void shouldRunBenchmarkWithBothWarmupAndTimedRuns() {
	executeBenchmark(10, 30);

	verifyInteractionsWithBenchmarkDef(10, 30);
    }

    @Test
    public void shouldRunBenchmarkWithoutTimedRuns() {
	executeBenchmark(10, 0);

	verifyInteractionsWithBenchmarkDef(10, 0);
    }

    @Test
    public void shouldRunBenchmarkWithoutAnyRuns() {
	executeBenchmark(0, 0);

	verifyInteractionsWithBenchmarkDef(0, 0);
    }

    private void executeBenchmark(int numWarmupRuns, int numTimedRuns) {
	BenchmarkConfig config = new BenchmarkConfig(numWarmupRuns, numTimedRuns);

	Benchmark benchmark = new Benchmark(config);

	benchmark.execute(benchmarkDefMock);
    }

    private void verifyInteractionsWithBenchmarkDef(int numWarmupRuns, int numTimedRuns) {
	verify(benchmarkDefMock).beforeAll();
	verify(benchmarkDefMock).afterAll();

	verify(benchmarkDefMock, times(numWarmupRuns + numTimedRuns)).before();
	verify(benchmarkDefMock, times(numWarmupRuns + numTimedRuns)).execute();
	verify(benchmarkDefMock, times(numWarmupRuns + numTimedRuns)).after();

	verify(benchmarkDefMock, times(numTimedRuns)).addResult(anyLong(), any());
    }
}
