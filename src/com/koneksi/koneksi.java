/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author user
 */
public class koneksi {
    
    private static Connection koneksi;
    public static Connection getKoneksi()
    {
        if(koneksi== null)
        {
            try {
                String url="jdbc:mysql://localhost/sekolah";
                String username= "root";
                String password= "";
                
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi =DriverManager.getConnection(url, username, password);
                System.out.println(koneksi);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        return koneksi;
    }
    

}
