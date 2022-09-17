    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import com.controller.controller_siswa;
import com.view.form_siswa;
import com.koneksi.koneksi;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author astse
 */
public class model_siswa implements controller_siswa{
    String jk;

    @Override
    public void Simpan(form_siswa siswa) throws SQLException {
        if (siswa.rbLaki.isSelected()) {
            jk = "Laki-laki";
        } else {
            jk = "Perempuan";
        }
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "Insert into siswa Values(?,?,?,?)";
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(1, siswa.txtNIS.getText());
            prepare.setString(2, siswa.txtNama.getText());
            prepare.setString(3, jk);
            prepare.setString(4, (String) siswa.cbJurusan.getSelectedItem());
            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diSimpan");
            prepare.close();
            Baru(siswa);
            
        } catch (Exception e) {
            System.out.println(e);
        } finally{
            Tampil(siswa);
            siswa.setLebarKolom();
        }
    }

    @Override
    public void Ubah(form_siswa siswa) throws SQLException {
        if (siswa.rbLaki.isSelected()) {
            jk = "Laki-laki";
        } else {
            jk = "Perempuan";
        }
            try {
                Connection con = koneksi.getKoneksi();
                String sql = "UPDATE siswa SET nama=?, jenis_kelamin=?, " + "jurusan=? WHERE NIS=?";
                PreparedStatement prepare = con.prepareStatement(sql);

                prepare.setString(4, siswa.txtNIS.getText());
                prepare.setString(1, siswa.txtNama.getText());
                prepare.setString(2, jk);
                prepare.setString(3, (String) siswa.cbJurusan.getSelectedItem());
                prepare.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil diubah");
                prepare.close();
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                Tampil(siswa);
                siswa.setLebarKolom();
                Baru(siswa);
            }
    }

    @Override
    public void Hapus(form_siswa siswa) throws SQLException {
        
        String sql = "DELETE FROM siswa WHERE NIS=?";
//        String resetno = "ALTER TABLE siswa DROP NIS";
//        String consecutivenumbers = "ALTER TABLE siswa ADD NIS INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST";
        try{
            Connection con = koneksi.getKoneksi();
            PreparedStatement prepare = con.prepareStatement(sql);
//            con.createStatement().execute(resetno);
//            con.createStatement().execute(consecutivenumbers);
            
            prepare.setString(1, siswa.txtNIS.getText());
            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            prepare.close();
        } catch (Exception e){
            System.out.println(e);
        } finally{
            Tampil(siswa);
            siswa.setLebarKolom();
            Baru(siswa);
        }
    }

    @Override
    public void Tampil(form_siswa siswa) throws SQLException {
        siswa.tblmodel.getDataVector().removeAllElements();
        siswa.tblmodel.fireTableDataChanged();;
            try {
            Connection con = koneksi.getKoneksi();
            Statement stt = con.createStatement();
            // Query menampilkan semua data pada tabel siswa
            // dengan urutan NIS dari kecil ke besar
            String sql = "SELECT * FROM siswa ORDER BY NIS ASC";
            ResultSet res = stt.executeQuery(sql);
            while (res.next()) {
                Object[] ob = new Object[8];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                ob[2] = res.getString(3);
                ob[3] = res.getString(4);
                siswa.tblmodel.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void Baru(form_siswa siswa) throws SQLException {
        siswa.txtNama.setText("");
        siswa.rbLaki.setSelected(true);
        siswa.cbJurusan.setSelectedIndex(0);
    }

    @Override
    public void KlikTabel(form_siswa siswa) throws SQLException {
        try {
            int pilih = siswa.tabel.getSelectedRow();
            if (pilih == -1) {
               return;
            }
            siswa.txtNIS.setText(siswa.tblmodel.getValueAt(pilih, 0).toString());
            siswa.txtNama.setText(siswa.tblmodel.getValueAt(pilih, 1).toString());
            siswa.cbJurusan.setSelectedItem(siswa.tblmodel.getValueAt(pilih, 3));
            jk = String.valueOf(siswa.tblmodel.getValueAt(pilih, 2));
        } catch (Exception e) {
            System.out.println(e);
        }
        
        // memberi nilai jk pada radio button
        if (siswa.rbLaki.getText().equals(jk)) {
            siswa.rbLaki.setSelected(true);
        } else {
            siswa.rbPerempuan.setSelected(true);
        }
    }

    @Override
    public void AutoNumber(form_siswa siswa) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            Statement stt = con.createStatement();
            String sql = "SELECT MAX(NIS) FROM siswa";
            ResultSet res = stt.executeQuery(sql);

            while (res.next()) {
                int a = res.getInt(1);
                siswa.txtNIS.setText(Integer.toString(a + 1));
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
}
