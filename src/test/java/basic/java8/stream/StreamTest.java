package basic.java8.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class StreamTest {

  private static class StreamUser { 
    private String name;
    private String dept; 

    public StreamUser(String name, String dept) {
      this.name = name; 
      this.dept = dept; 
    }
    public String getName() {
      return this.name; 
    }
    public void setName(String name) {
      this.name = name; 
    }
    public void setDept(String dept) {
      this.dept = dept; 
    }
    public String getDept() {
      return this.dept;
    }
  };

  @Test
  public void testCreateStream() {
    List<String> list = Arrays.asList("가", "나", "다"); 
    Stream<String> listStream = list.stream();
  }
  
  @Test
  public void testCreateStream2() {
    Stream<String> stream1 = Stream.of("가", "나", "다"); 
    Stream<String> stream2 = Stream.of(new String[] {"가", "나", "다"}); 
    Stream<String> stream3 = Arrays.stream(new String[] {"가", "나", "다"}); 
    Stream<String> stream4 = Arrays.stream(new String[] {"가", "나", "다"}, 0, 3); // 마지막 범위 지정
  }

  @Test
  public void testCreateStream3() {
    List<String> list = Arrays.asList("홍길동", "박희영", "김종호"); 
    Stream<String> stream = list.stream().filter(name -> name.contains("홍길동"));
    stream.forEach(name -> System.out.println(name));
  }

  @Test
  public void testCreateStream4() {
    List<String> list = Arrays.asList("love", "apple", "you"); 
    Stream<String> stream = list.stream().map( s -> s.toUpperCase());
    stream.forEach(name -> System.out.println(name));
  }
  @Test
  public void testCreateStream5() {
    List<String> list = Arrays.asList("love", "apple", "you"); 
    Stream<String> stream = list.stream().sorted(Comparator.reverseOrder());
    stream.forEach(name -> System.out.println(name));
  }

  @Test
  public void testCreateStream6() {
    List<String> list = Arrays.asList("love", "apple", "love", "you"); 
    Stream<String> stream = list.stream().distinct();
    stream.forEach(name -> System.out.println(name));
  }
  @Test
  public void testCreateStream7() {
    int sum = IntStream.of(1, 3, 5, 7, 9)
          .peek(System.out::println) 
          .sum();
  }

  @Test
  public void testCreateStream8() {
    Stream<String> stream = IntStream.range(1, 5).mapToObj(i -> "a" + i);
    stream.forEach(System.out::println);
  }

  @Test
  public void testCreateStream9() {
    // OptionalInt min = IntStream.of(1, 3, 5, 7, 9).min(); 
    // System.out.println(min.getAsInt()); 

    // int max = IntStream.of(1,3,5,7,9).max().orElse(0); 
    // System.out.println(max);

    IntStream.of(1, 3, 5, 7, 9).average().ifPresent(System.out::println);
  }

  @Test
  public void testCreateStream10() {
    long count = IntStream.of(1, 3, 5, 7, 9).count(); 
    long sum = LongStream.of(1, 3, 5, 7, 9).sum();
    System.out.println(count);
    System.out.println(sum);
  }
  

  @Test
  public void testCreateStream11() {
    List<StreamUser> userList = Arrays.asList(
      new StreamUser("a", "가"),
      new StreamUser("b", "가"),
      new StreamUser("c", "나"),
      new StreamUser("d", "나"),
      new StreamUser("e", "다")
    );
    List<String> nameList = userList.stream()
        .map(StreamUser::getName)
        .collect(Collectors.toList());
    nameList.forEach(System.out::println);
  }


  @Test
  public void testCreateStream13() {

    List<StreamUser> userList = Arrays.asList(
      new StreamUser("a", "가"),
      new StreamUser("b", "가"),
      new StreamUser("c", "나"),
      new StreamUser("d", "나"),
      new StreamUser("e", "다")
    );

    String nameString = userList.stream()
        .map(StreamUser::getName)
        .collect(Collectors.joining("/"));
    System.out.println(nameString);

  }


  
  @Test
  public void testCreateStream12() {
    List<StreamUser> userList = Arrays.asList(
      new StreamUser("a", "가"),
      new StreamUser("b", "가"),
      new StreamUser("c", "나"),
      new StreamUser("d", "나"),
      new StreamUser("e", "다")
    );

    Map<String, List<StreamUser>> users = 
      userList.stream().collect(Collectors.groupingBy(StreamUser::getDept));
    System.out.println(users);
  }

  @Test
  public void testCreateStream14() {
    List<StreamUser> userList = Arrays.asList(
      new StreamUser("a", "가"),
      new StreamUser("b", "가"),
      new StreamUser("c", "나"),
      new StreamUser("d", "나"),
      new StreamUser("e", "다")
    );

    Map<Boolean, List<StreamUser>> users = userList.stream()
         .collect(Collectors.partitioningBy(p -> p.getDept().contains("가")));
    System.out.println(users);
  }

  @Test
  public void testCreateStream15() {
    List<StreamUser> userList = Arrays.asList(
      new StreamUser("a", "가"),
      new StreamUser("b", "가"),
      new StreamUser("c", "나"),
      new StreamUser("d", "나"),
      new StreamUser("e", "다")
    );

    boolean anyMatch = userList.stream().anyMatch(p -> p.getName().contains("a"));
    System.out.println(anyMatch);
  }


}
