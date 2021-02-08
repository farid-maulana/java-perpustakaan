package frontend;

import backend.Anggota;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FrmAnggota {
    private JTextField txtIdAnggota;
    private JTextField txtNama;
    private JTextField txtAlamat;
    private JTextField txtTelepon;
    private JLabel idAnggota;
    private JLabel namaAnggota;
    private JLabel alamat;
    private JLabel telepon;
    private JButton btnSimpan;
    private JButton btnHapus;
    private JButton btnTambahBaru;
    private JTextField txtCari;
    private JButton btnCari;
    private JTable tblAnggota;
    private JPanel panel1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("FrmAnggota");
        frame.setContentPane(new FrmAnggota().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public FrmAnggota() {
        kosongkanForm();
        tampilkanData();
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Anggota anggota = new Anggota();
                anggota.setIdanggota(Integer.parseInt(txtIdAnggota.getText()));
                anggota.setNama(txtNama.getText());
                anggota.setAlamat(txtAlamat.getText());
                anggota.setTelepon(txtTelepon.getText());
                anggota.save();
                txtIdAnggota.setText(Integer.toString(anggota.getIdanggota()));
                tampilkanData();
            }
        });
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) tblAnggota.getModel();
                int row = tblAnggota.getSelectedRow();

                Anggota anggota = new Anggota()
                        .getById(Integer.parseInt(model.getValueAt(row, 0).toString()));
                anggota.delete();
                kosongkanForm();
                tampilkanData();
            }
        });
        btnTambahBaru.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kosongkanForm();
            }
        });
        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cari(txtCari.getText());
            }
        });
        tblAnggota.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model = (DefaultTableModel) tblAnggota.getModel();
                int row = tblAnggota.getSelectedRow();

                txtIdAnggota.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtAlamat.setText(model.getValueAt(row, 2).toString());
                txtTelepon.setText(model.getValueAt(row, 3).toString());
            }
        });
    }

    public void kosongkanForm() {
        txtIdAnggota.setText("0");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
    }

    public void tampilkanData() {
        String[] kolom = {"ID", "Nama", "Alamat", "Telepon"};
        ArrayList<Anggota> list = new Anggota().getAll();
        Object rowData[] = new Object[4];

        tblAnggota.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (Anggota anggota : list) {
            rowData[0] = anggota.getIdanggota();
            rowData[1] = anggota.getNama();
            rowData[2] = anggota.getAlamat();
            rowData[3] = anggota.getTelepon();

            ((DefaultTableModel) tblAnggota.getModel()).addRow(rowData);
        }
    }

    public void cari(String keyword) {
        String[] kolom = {"ID", "Nama", "Alamat", "Telepon"};
        ArrayList<Anggota> list = new Anggota().search(keyword);
        Object rowData[] = new Object[4];

        tblAnggota.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (Anggota anggota : list) {
            rowData[0] = anggota.getIdanggota();
            rowData[1] = anggota.getNama();
            rowData[2] = anggota.getAlamat();
            rowData[3] = anggota.getTelepon();

            ((DefaultTableModel) tblAnggota.getModel()).addRow(rowData);
        }
    }
}
