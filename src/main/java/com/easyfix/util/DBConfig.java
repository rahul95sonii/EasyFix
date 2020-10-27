package com.easyfix.util;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class DBConfig {
        static javax.sql.DataSource myDB=null;
      
        
         public synchronized static DataSource getContextDataSource() {
              if(myDB==null)
              {
               try {
                               Context initContext = new InitialContext();
                               Context envContext = (Context) initContext.lookup("java:comp/env");
                               myDB = (DataSource) envContext.lookup("jdbc/UsersDB");
               }
               catch (NamingException e) {
                               // TODO Auto-generated catch block
                               e.printStackTrace();
                       }
              }
               return myDB;
           }
         
}