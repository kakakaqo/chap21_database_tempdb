package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 상품 추가
 * 카테고리:식료품 / 상품Id : 21/ 상품명:시금치 / 가격:3500 / 입고일:2023/02/10
 */
public class Database04_Insert {
   
   public static void main(String[] args) {
      // 오라클 드라이버 로딩 문자열
      String driver = "oracle.jdbc.driver.OracleDriver";
      // 데이터베이스 연결 문자열
      String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
      // 데이터베이스명(계정명)
      String dbId = "tempdb";
      // 데이터베이스 비밀번호
      String dbPwd = "1234";
      
      // 데이터베이스 연결 객체
      Connection con = null;   
      //커넥션 객체를 통해서 데이터베이스에 쿼리(SQL)를 실행해주는 객체
      PreparedStatement pstmt = null;
      // 실행된 쿼리문의 결과를 반환 받는 객체
      ResultSet rs = null;   
      
      try {
         // 1. 드라이버 로딩
         Class.forName(driver);
         System.out.println("1. 드라이버 로드 성공!");

         // 2. 데이터베이스 커넥션(연결)
         con = DriverManager.getConnection(url, dbId, dbPwd);
         System.out.println("2. 커넥션 객체 생성 성공!");
         
         // 3. 저장할 상품 관련 변수
         int product_id = 21;
         String product_name = "시금치";
         int price = 3500;
         int category_id = 5;
         String receiptDate = "20230210";
         
         // 4. PreparedStatement 객체에 사용할 SQL문 생성
         String sql = "insert into product(product_id, product_name, price, category_id, receipt_date) ";
         sql += "values(?, ?, ?, ?, to_date(?, 'YYYY/MM/DD'))";
         
         
         // 5. PreparedStatement 객체 얻음(쿼리문 전달) 
         pstmt = con.prepareStatement(sql);
         
         // 5.1 쿼리문에 인자 전달
         pstmt.setInt(1, product_id); 
         pstmt.setString(2, product_name);    
         pstmt.setInt(4, category_id);   
         pstmt.setString(5, receiptDate);   

         // 6. PreparedStatement 객체의 executeQuery() 통해서 쿼리 실행
         //    쿼리 실행 결과로 ResultSet 반환됨
         int resultRows = pstmt.executeUpdate();
         if(resultRows > 0) {
            System.out.println("저장 성공");
         }else {
            System.out.println("저장 실패");
         }
      } catch (ClassNotFoundException e) {
         System.out.println("드라이버 ERR! : " + e.getMessage());
      } catch (SQLException e) {
         System.out.println("SQL ERR! : " + e.getMessage());
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
            if (pstmt != null) {
               pstmt.close();
            }
            if (con != null) {
               con.close();
            }
         } catch (SQLException e) {
            System.out.println("자원해제 ERR! : " + e.getMessage());
         }
      }
   }
}