package basic.java8.generic;

public class MyFilterImpl implements MyFilter {

  @Override
  public void doFilter(String val) {
    System.out.println("=====Filter:" + val);
  }
}
