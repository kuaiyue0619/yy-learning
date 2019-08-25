package my.yy.learning.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * collect测试
 *
 * @author kuaiyue
 * @date 2019-08-11 10:56
 */
public class CollectTest {

    @Getter
    @ToString
    @AllArgsConstructor
    static class Number {

        private int value;

        private String type;

        private Number(int value) {
            this.value = value;
        }
    }

    @Test
    public void testMax() {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        String max = list.stream()
                .max(Comparator.naturalOrder())
                .orElse(null);
        System.out.println(max);
    }

    @Test
    public void testSumming() {
        List<Number> numbers = new ArrayList<>();
        numbers.add(new Number(1));
        numbers.add(new Number(2));
        int sum = numbers.stream()
                .collect(Collectors.summingInt(Number::getValue));
        System.out.println(sum);

        sum = numbers.stream().mapToInt(Number::getValue).sum();
        System.out.println(sum);
    }

    @Test
    public void testGroupingBy() {
        List<Number> numbers = new ArrayList<>();
        numbers.add(new Number(1, "a"));
        numbers.add(new Number(2, "b"));
        numbers.add(new Number(3, "a"));
        numbers.add(new Number(4, "b"));
        Map<String, List<Number>> numsByType = numbers.stream()
                .collect(Collectors.groupingBy(Number::getType));
        System.out.println(numsByType);
    }

    @Test
    public void testPartitionPrimes() {
        int n = 1000;
        Map<Boolean, List<Integer>> primeMap = IntStream.rangeClosed(2, n).boxed()
                .collect(Collectors.partitioningBy(candidate -> {
                    // 仅计算小于等于待计算数平方根的因子
                    int candidateRoot = (int) Math.sqrt(candidate);
                    return IntStream.rangeClosed(2, candidateRoot)
                            // 不能被该范围内任意数整除
                            .noneMatch(i -> candidate % i == 0);
                }));
        System.out.println("质数: " + primeMap.get(true));
        System.out.println("非质数: " + primeMap.get(false));
    }
}
