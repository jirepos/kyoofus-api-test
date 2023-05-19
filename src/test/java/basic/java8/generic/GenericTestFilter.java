package basic.java8.generic;

public class GenericTestFilter<T> {
  
  private T filter;
  
  public GenericTestFilter() {
  }
  public  T getFilter() {
    return this.filter;
  }
  public  void setFilter(T filter) {
    this.filter = filter; 
  }
}
