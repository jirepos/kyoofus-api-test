package basic.java8.anno;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AnnoTest {

 
  public  interface UserBaseInfo { 
    String getName(); 
  }


  /** 추상 클래스를 이용한 익명 클래스 선언 */
   @Test
   public void testAnno1(){ 
     AnnoAbstract ab = new AnnoAbstract() {
       @Override 
       public int getAge() {
         return 10; 
       }
     };
     System.out.println(ab.getAge());
   }//:

   /** 인터페이스를 이용한 익명 클래스 선언 */
   @Test 
   public void testAnno2(){ 
     UserBaseInfo ub = new UserBaseInfo() { 
       @Override 
       public String getName(){ 
         return "Hong";
       }
     };

     System.out.println(ub.getName());

   }


   public static class InnoTwo { 
    private List<String> list = new ArrayList<String>();

    /** code 블럭  */
    {
      // 코드 블럭은 InnoTwo 클래스가 생성될 때 호출 된다. 
       ONE("FIRST");
       TWO("SECOND");
    }
    public List<String> getList(){ 
      return this.list; 
    }
    private void ONE(String str){ 
      list.add(str);
    }
    private void TWO(String str){ 
      System.out.println(str);
    }
   }


   @Test 
   public void testAnno3(){ 
     InnoTwo two = new InnoTwo();
     two.getList().forEach( item -> { System.out.println(item); });
   }
  
}////~
