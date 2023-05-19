package basic.java8.generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class GenericTypeTest {


    /**
   * Type parameter 'T'를 사용하는 Generic class를 정의한다. 
   */
  static class MySuper<T> {
    T value; 
    T get() { return value; }
    void set(T t) {value = t; }
  }
  
  /**
   * List{@literal < }String{@literal > }을 타입 파라미터로 전달하는 테스트 케이스이다. 
   * @throws Exception
   */
  @Test
  public void testSafeMap8() throws Exception {

    
    // List<String>을 타입 파라미터로 전달하여 인스턴스를 생성한다. 
    MySuper<List<String>> mysuper =  new MySuper<>() {};
    // Generic super class을 구한다. 
    Type t = mysuper.getClass().getGenericSuperclass();
    // 파라미터화된 형을 표현 ex) List<T> 
    ParameterizedType ptype = (ParameterizedType) t;
    System.out.println(ptype.toString());   //basic.java8.generic.GenericTest$MySuper<java.util.List<java.lang.String>>
    System.out.println(ptype.getActualTypeArguments()[0]);  //java.util.List<java.lang.String>

    //TypeVariable<?> type = (TypeVariable<?>) boundList.getActualTypeArguments()[0];

  }




  static class MyGeneric<T> {
    T value;
    void set(T t) {}
    T get() { return null; }
  }

  //강제캐스팅이 없는 TypeSafe 한 Map
  static class TypeSafeMap {
    
    Map<Class<?>, Object> map = new HashMap<>();

    <T> void put(Class<T> clazz, T value) {
        map.put(clazz, value);
    }

    
    <T> T get(Class<T> clazz) {
        return clazz.cast(map.get(clazz));
    }
  }





  /**
   * MySupser String type을 파라미터로 전달하는 class를 정의한다. 
   */
  static class MySubclass extends MySuper<String> {
  }






  @Test
  public void testSafeMap7() throws Exception {
    
    class MySubclass2 extends MySuper<List<String>> {
    }
    MySubclass2 mysub = new MySubclass2();
    Type t = mysub.getClass().getGenericSuperclass();
    ParameterizedType ptype = (ParameterizedType) t;
    System.out.println(ptype.getActualTypeArguments()[0]);
  }
  @Test
  public void testSafeMap6() throws Exception {
    MySubclass mysub = new MySubclass();
    Type t = mysub.getClass().getGenericSuperclass();
    ParameterizedType ptype = (ParameterizedType) t;
    System.out.println(ptype.getActualTypeArguments()[0]);
  }

  @Test
  public void testSafeMap5() throws Exception {
    MySuper<String> mysup = new MySuper<>();
    System.out.println(mysup.getClass().getDeclaredField("value").getType());
  }


  @Test
  public void testSafeMap4() {
    TypeSafeMap m = new TypeSafeMap();
    // m.put(List<String>.class, Arrays.asList("aa", "bb", "cc")); // 오류 발생함 
  }

  @Test
  public void testSafeMap3() {
    TypeSafeMap m = new TypeSafeMap();
    m.put(Integer.class, 1);
    m.put(String.class, "String");
    m.put(List.class, Arrays.asList(1, 2, 3));

    System.out.println(m.get(Integer.class));
    System.out.println(m.get(String.class));
    System.out.println(m.get(List.class));
  }

  

  @Test
  public void testG() {
    MyGeneric<String> s = new MyGeneric<String>();
    s.value = "latte";  // 컴파일 시에 컴파일러가 미리 체크하고 (String)"latter"와 같이 캐스팅한다. 
    // 런타임 시에 value 의 타입은 Object이다. 
    // type erasure가 타입 파라미터를 지운다. 
    System.out.println(s.value);
  }

  
  @Test
  public void testSafeMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("a", "a");
    map.put("b", 1); 
    // 아래 코드는 강제 타입 변환을 하므로 안전하지 않다. 
    String a = (String)map.get("a");   
    Integer b = (Integer)map.get("b");   
  }

  @Test
  public void testSafeMap2() {
    Map<String, Object> map = new HashMap<>();
    map.put("a", "a");
    map.put("b", 1); 
    // 아래 코드는 강제 타입 변환을 하므로 안전하지 않다. 
    String a = (String)map.get("a");   
    Integer b = (Integer)map.get("b");   
  }
  






  @Test 
  public void testGenericMethod() {
    String[] arr = new String[] { "Apple", "Pear"};
    List<String> list = GenericTestBean.getList(arr); 
    list.forEach( element -> {
      System.out.println(element);
    });
  }//:

  @Test
  public void tessWildCard1() throws Exception {
    String name = new String("latte");
    Class<?> c = name.getClass();
    System.out.println(c.getTypeName());

    Class<?> c2 = String.class; 
    System.out.println(c2.getTypeName());
  }

  @Test
  public void testInstance() throws Exception {
    Class<?> c = String.class;
    //Class<?> parameterType = String.class; 
    //Constructor<?> constructor = c.getConstructor(String.class);
    //Constructor<?> constructor = c.getConstructor(parameterType);
    //String s = (String)constructor.newInstance("latte@gmail.com");

    //Class<?> parameterType = null;
    Constructor<?> constructor = c.getConstructor();
    String s = (String)constructor.newInstance();
    System.out.println(s.getClass().toString());
  }

  @Test
  public void testTypeRef() throws Exception { 
    //String json = "[ { \"userName\": \"latte\" }, { \"userName\": \"terrace\" } ";
    //ObjectMapper mapper = new ObjectMapper(); 
    
    System.out.println(TypeReference.class.getGenericSuperclass());
  }


  @Test
  public void testClassLoader() throws Exception  {
    String className = "com.sogood.test.java.generic.MyFilterImpl"; // 필터 구현체 이름
    ClassLoader classLoader = this.getClass().getClassLoader(); // 클래스로더 구함
    Class<?> aClass = classLoader.loadClass(className.trim()); // 클래스 정보 가져옴 
    Constructor<?> constructor = aClass.getConstructor(); // 생성자 구함
    Object obj = constructor.newInstance();  // 인스턴스 생성
    System.out.println(obj.getClass().toString()); // 클래스 정보 출력
    GenericTestFilter<MyFilter> filter = new GenericTestFilter<>(); // 제네릭타입은 인터페이스로 넘겨줌
    //MyFilter fimpl = new MyFilterImpl();
    filter.setFilter((MyFilter)obj);  // object를 인터페이스로 캐스팅
    filter.getFilter().doFilter("hello2222"); // 다시 오브젝트를 구하고 메소드 호출 
  }

  
}
