package basic.external.mybatis;

import org.apache.ibatis.jdbc.SQL;
import org.junit.jupiter.api.Test;

public class MyBatisQueryBuilderTest {



  /** DELETE */
  public static String deleteSQL() {
    return new SQL(){{
      DELETE_FROM("PERSON");
      WHERE("ID = #{id}");
    }}.toString();
    // DELETE FROM PERSON
    // WHERE (ID = #{id})
  }
  @Test 
  public void testDeleteSQL() {
    String sql = deleteSQL();
    System.out.println(sql);
  }

  public static String insertPersonSql() {
    return new SQL() {{
      INSERT_INTO("PERSON");
      VALUES("ID, FIRST_NAME", "#{id}, #{firstName}");
      VALUES("LAST_NAME", "#{lastName}");
    }}.toString();
  //   INSERT INTO PERSON
  //   (ID, FIRST_NAME, LAST_NAME)
  //  VALUES (#{id}, #{firstName}, #{lastName})
  }

  @Test 
  public void testInsertSQL() {
    String sql = insertPersonSql();
    System.out.println(sql);
  }

  public static String insertSQL() {
    return new SQL() {{
      INSERT_INTO("PERSON");
      INTO_COLUMNS("ID, FIRST_NAME");
      INTO_VALUES("#{id}, #{firstName}");
    }}.toString();
  //   INSERT INTO PERSON
  //   (ID, FIRST_NAME)
  //  VALUES (#{id}, #{firstName})
  }

  
  @Test 
  public void testInsertSQL2() {
    String sql = insertSQL();
    System.out.println(sql);
  }



  public static String updatePersonSql() {
    return new SQL() {{
      UPDATE("PERSON");
      SET("FIRST_NAME = #{firstName}");
      WHERE("ID = #{id}");
    }}.toString();
    // UPDATE PERSON
    // SET FIRST_NAME = #{firstName}
    // WHERE (ID = #{id})
  }

  
  @Test 
  public void testUpdateSQL() {
    String sql = updatePersonSql();
    System.out.println(sql);
  }

  /** 시작하기. 기본 샘플 */
  public static String buildGetUsersByName(final String name) {
    return new SQL(){{
      SELECT("*");
      FROM("users");
      if (name != null) {
        WHERE("name like #{value} || '%'");
      }
      ORDER_BY("id");
    }}.toString();

    // SELECT *
    // FROM users
    // WHERE (name like #{value} || '%')
    // ORDER BY id
  }
  @Test 
  public void testBasic() {
    String sql = buildGetUsersByName("Hong");
    System.out.println(sql);
  }


  /** FROM에 두 개의 테이블  */
  public static String twoFromsSQL() {
    return new SQL() {
      {
        SELECT("SELECT *");
        FROM("USERS A");
        FROM("DEPT B");
      }
    }.toString();

    // SELECT SELECT *
    // FROM USERS A, DEPT B
  }

  @Test 
  public void testTwoFromsSQL() {
    System.out.println(twoFromsSQL());
  }

  /** FROM에 두 개의 테이블2  */
  public static String twoFromsSQL2() {
    return new SQL() {
      {
        SELECT("SELECT *");
        FROM("USERS A, DEPT B");
      }
    }.toString();

    // SELECT SELECT *
    // FROM USERS A, DEPT B
  }

  @Test 
  public void testTwoFromsSQL2() {
    System.out.println(twoFromsSQL2());
  }

  public static String innerJoinSQL() {
    return new SQL() {
      {
        SELECT("SELECT *");
        FROM("USERS A");
        INNER_JOIN("DEPT B on B.deptId = A.deptId");
        WHERE("A.userId = #{userId}");
      }
    }.toString();
    // SELECT SELECT *
    // FROM USERS A
    // INNER JOIN DEPT B on B.deptId = A.deptId
    // WHERE (A.userId = #{userId})
        
  }


  @Test 
  public void testInnerJoinSQL() {
    System.out.println(innerJoinSQL());
  }

  /** 두개의 INNER_JOIN,  두개의 WHERE */
  public static String innerJoinSQLTwo() {
    return new SQL() {
      {
        SELECT("SELECT *");
        FROM("USERS A");
        INNER_JOIN("DEPT B on B.deptId = A.deptId");
        INNER_JOIN("DEPT_DETAIL C on C.deptID = C.deptId");
        WHERE("A.userId = #{userId}");
        WHERE("A.userStat = '1'");
      }
    }.toString();
    // SELECT SELECT *
    // FROM USERS A
    // INNER JOIN DEPT B on B.deptId = A.deptId
    // INNER JOIN DEPT_DETAIL C on C.deptID = C.deptId
    // WHERE (A.userId = #{userId} AND A.userStat = '1')
  }

  @Test 
  public void testInnerJoinSQLTwo() {
    System.out.println(innerJoinSQLTwo());
  }



    /** LEFT JOIN, RIGHT JOIN*/
    public static String leftRightJoinSQL() {
      return new SQL() {
        {
          SELECT("SELECT *");
          FROM("USERS A");
          INNER_JOIN("DEPT B on B.deptId = A.deptId");
          LEFT_OUTER_JOIN("DEPT_DETAIL C on C.deptID = C.deptId");
          RIGHT_OUTER_JOIN("DEPT_LOG D on D.deptId = B.deptId");
          WHERE("A.userId = #{userId}");
          WHERE("A.userStat = '1'");
        }
      }.toString();
      // SELECT SELECT *
      // FROM USERS A
      // INNER JOIN DEPT B on B.deptId = A.deptId
      // LEFT OUTER JOIN DEPT_DETAIL C on C.deptID = C.deptId
      // RIGHT OUTER JOIN DEPT_LOG D on D.deptId = B.deptId
      // WHERE (A.userId = #{userId} AND A.userStat = '1')
      
    }
  
    @Test 
    public void testLeftRightJoinSQL() {
      System.out.println(leftRightJoinSQL());
    }
  


  /** OR() 사용 */
  public static String orSQL() {
    return new SQL() {
      {
        SELECT("SELECT *");
        FROM("USERS A");
        INNER_JOIN("DEPT B on B.deptId = A.deptId");
        WHERE("A.userId = #{userId}");
        OR();
        WHERE("A.userId = #{admind}");
      }
    }.toString();

    // SELECT SELECT *
    // FROM USERS A
    // INNER JOIN DEPT B on B.deptId = A.deptId
    // WHERE (A.userId = #{userId})
    // OR (A.userId = #{admind})
    
  }


  @Test 
  public void testOrSQL() {
    System.out.println(orSQL());
  }


    /** AND() 사용 */
    public static String andSQL() {
      return new SQL() {
        {
          SELECT("SELECT *");
          FROM("USERS A");
          INNER_JOIN("DEPT B on B.deptId = A.deptId");
          WHERE("A.userId = #{userId}");
          WHERE("A.flag = #{flag}");
          AND();
          WHERE("B.flag = #{bFlag}");
          WHERE("B.stat = #{bStat}");
        }
      }.toString();

      // SELECT SELECT *
      // FROM USERS A
      // INNER JOIN DEPT B on B.deptId = A.deptId
      // WHERE (A.userId = #{userId} AND A.flag = #{flag})
      // AND (B.flag = #{bFlag} AND B.stat = #{bStat})
      
    }

    @Test 
    public void testAndSQL() {
      System.out.println(andSQL());
    }

    /** AND()와 OR() 혼용 사용 */
    public static String andOrSQL() {
      return new SQL() {
        {
          SELECT("SELECT *");
          FROM("USERS A");
          INNER_JOIN("DEPT B on B.deptId = A.deptId");
          WHERE("A.userId = #{userId}");
          WHERE("A.flag = #{flag}");
          AND();
          WHERE("B.flag = #{bFlag}");
          WHERE("B.stat = #{bStat}");
          OR();
          WHERE("B.delYn = #{delYn}");
          WHERE("B.regDt >= #{regDt}");        }
      }.toString();
    }
        
    @Test 
    public void testAndOrSQL() {
      System.out.println(andOrSQL());
    }

    /** AND()와 OR() 혼용 사용 */
    public static String andOrSQL2() {
      return new SQL() {
        {
          SELECT("SELECT *");
          FROM("USERS A");
          INNER_JOIN("DEPT B on B.deptId = A.deptId");
          WHERE("A.userId = #{userId}");
          WHERE("A.flag = #{flag}");
          AND();
          WHERE("B.flag = #{bFlag}  OR B.stat = #{bStat}");
          OR();
          WHERE("B.delYn = #{delYn}");
          WHERE("B.regDt >= #{regDt}");        }
      }.toString();
    }
        
    @Test 
    public void testAndOrSQL2() {
      System.out.println(andOrSQL2());
    }
    /** ORDER BY 사용 */
    public static String orderBySQL() {
      return new SQL() {
        {
          SELECT("SELECT *");
          FROM("USERS A");
          WHERE("A.userId = #{userId}");
          ORDER_BY("A.userId");
          ORDER_BY("A.regDt desc");
        }
      }.toString();
      // SELECT SELECT *
      // FROM USERS A
      // WHERE (A.userId = #{userId})
      // ORDER BY A.userId, A.regDt desc
    }

    @Test 
    public void testOrderBy() {
      System.out.println(orderBySQL());
    }
    /** GroupBy  사용 */
    public static String groupBySQL() {
      return new SQL() {
        {
          SELECT("SELECT *");
          FROM("USERS A");
          WHERE("A.userId = #{userId}");
          GROUP_BY("A.userId");
          GROUP_BY("A.user_age");
        }
      }.toString();
      // SELECT SELECT *
      // FROM USERS A
      // WHERE (A.userId = #{userId})
      // GROUP BY A.userId, A.user_age
      
    }
    @Test 
    public void testGroupBy() {
      System.out.println(groupBySQL());
    }
    /** Having  사용 */
    public static String havingSQL() {
      return new SQL() {
        {
          SELECT("SELECT *");
          FROM("USERS A");
          WHERE("A.userId = #{userId}");
          GROUP_BY("A.userId");
          HAVING("count(A.userId) > 1");
        }
      }.toString();
      // SELECT SELECT *
      // FROM USERS A
      // WHERE (A.userId = #{userId})
      // GROUP BY A.userId
      // HAVING (count(A.userId) > 1)
      
    }

    @Test 
    public void testHhavingSQL() {
      System.out.println(havingSQL());
    }

    /** Limit  사용 */
    public static String limitSQL() {
      return new SQL() {
        {
          SELECT("SELECT *");
          FROM("USERS");
          WHERE("A.userId = #{userId}");
          LIMIT("5, 10");
        }
      }.toString();
      // SELECT SELECT *
      // FROM USERS
      // WHERE (A.userId = #{userId}) LIMIT 5, 10
    }    

    @Test 
    public void testLimitSQL() {
      System.out.println(limitSQL());
    }

    


}///~