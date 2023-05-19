package basic.java8.generic;

import java.util.ArrayList;
import java.util.List;

public class GenericTestBean {
  /** Generic Method  */
  public static <T> List<T> getList(T[] arr) {
    List<T> list = new ArrayList<T>();
    for(T element : arr) {
      list.add(element); 
    }
    return list; 
  }//:
  
}

