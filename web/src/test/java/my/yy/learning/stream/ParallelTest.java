package my.yy.learning.stream;

import org.junit.Test;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 并行测试
 *
 * @author kuaiyue
 * @date 2019-08-23 14:10
 */
public class ParallelTest {

    @Test
    public void testParallelReduce() {
        long n = 10_000_000L;
        long start = System.currentTimeMillis();
        Long l = Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
        long end = System.currentTimeMillis();
        System.out.println(String.format("串行耗时:%s毫秒,结果:%s", end - start, l));
        start = System.currentTimeMillis();
        // 有拆箱装箱逻辑,性能较差
        l = Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
        end = System.currentTimeMillis();
        System.out.println(String.format("并行耗时:%s毫秒,结果:%s", end - start, l));
    }

    @Test
    public void testParallelRangedSum() {
        long n = 1_000_000_000L;
        long start = System.currentTimeMillis();
        long l = LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
        long end = System.currentTimeMillis();
        System.out.println(String.format("串行耗时:%s毫秒,结果:%s", end - start, l));
        start = System.currentTimeMillis();
        // 无拆箱装箱逻辑,性能好
        l = LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
        end = System.currentTimeMillis();
        System.out.println(String.format("并行耗时:%s毫秒,结果:%s", end - start, l));
    }
}
