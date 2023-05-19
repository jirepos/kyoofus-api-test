package basic.external.mybatis;

import org.apache.ibatis.jdbc.SQL;
import org.junit.jupiter.api.Test;

public class MyBatisQueryBuilder2Test {

   public static String commonWhere() {
     return "A.NAME = #{name}";
  }
  public static String commonOrderBy(){ 
    return " A.USER_NAME";
  }
  
  public static void commonOrderBy2(SQL sqlObject){ 
    sqlObject.ORDER_BY("A.USER_NAME");
  }

  /** SQL 재사용 방법 (1) */
  @Test 
  public void testCommonWhere() {
    String sql = new SQL() {
      {
        SELECT("*");
        FROM("PERSON A");
        FROM("DEPT B");
        WHERE("A.ID = B.ID");
        WHERE(commonWhere());
        ORDER_BY(commonOrderBy());
      }
    }.toString();
    // SELECT *
    // FROM PERSON A, DEPT B
    // WHERE (A.ID = B.ID AND A.NAME = #{name})
    // ORDER BY  A.USER_NAME
    

    System.out.println(sql);
  }//:
  /** SQL 재사용 방법 (2) */
@Test 
  public void testCommonWhere2() {
    String sql = new SQL() {
      {
        SELECT("*");
        FROM("PERSON A");
        FROM("DEPT B");
        WHERE("A.ID = B.ID");
        WHERE(commonWhere());
        commonOrderBy2(this);
      }
    }.toString();
    // SELECT *
    // FROM PERSON A, DEPT B
    // WHERE (A.ID = B.ID AND A.NAME = #{name})
    // ORDER BY A.USER_NAME
    
    System.out.println(sql);
  }//:


  public String inlineSQL() {
    return new SQL() {
      {
        SELECT("*");
        FROM("EMPLOYEE");
        WHERE("EMP_ID = #{empId}");
      }
    }.toString();
  }
  



  /** INLINE VIEW 사용 */
  @Test 
  public void testInlineView() {
    String sql = new SQL() {
      {
        SELECT("A.*");
        FROM( "(" + inlineSQL() + ") A" );
        WHERE("A.FALG = 'D'");
      }
    }.toString();
    // SELECT A.*
    // FROM (SELECT *
    // FROM EMPLOYEE
    // WHERE (EMP_ID = #{empId})) A
    // WHERE (A.FALG = 'D')
    
    System.out.println(sql);
  }//:

  /** SubQuery 사용 */
  @Test 
  public void testSubQuery(){ 
    String sql = new SQL() {
      {
        SELECT("A.*");
        FROM("EMPLOYEE A");
        WHERE("A.FLAG = 'Y'");
        WHERE("EXISTS ("
            + new SQL() {
              {
                SELECT("1");
                FROM("STAT B");
                WHERE("B.ID = A.ID");
              }
            }.toString()
            +
        ")");
      }
    }.toString();

    System.out.println(sql);

  }//:


  @Test
  public void testAnotherStyle() {
    SQL sb = new SQL();
    sb.SELECT("*");
    sb.FROM("EMPLOYEE");
    sb.WHERE("EMP_ID = #{empId}");
    String sql = sb.toString();
    System.out.println(sql);

    // SELECT *
    // FROM EMPLOYEE
    // WHERE (EMP_ID = #{empId})
    
  }

}////
