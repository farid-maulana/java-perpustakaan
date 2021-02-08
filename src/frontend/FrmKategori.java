package frontend;

import backend.Kategori;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FrmKategori {
    private JPanel panel1;
    private JButton btnSimpan;
    private JButton btnHapus;
    private JButton btnTambahBaru;
    private JTextField txtCari;
    private JButton btnCari;
    private JTextField txtNama;
    private JTextField txtKeterangan;
    private JTextField txtIdKategori;
    private JLabel idKategori;
    private JLabel namaKategori;
    private JLabel keterangan;
    private JTable tblKategori;

    public static void main(String[] args) {
        JFrame frame = new JFrame("FrmKategori");
        frame.setSize(500, 500);
        frame.setContentPane(new FrmKategori().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public FrmKategori() {
        tampilkanData();
        kosongkanForm();
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Kategori kat = new Kategori();
                kat.setIdkategori(Integer.parseInt(txtIdKategori.getText()));
                kat.setNama(txtNama.getText());
                kat.setKeterangan(txtKeterangan.getText());
                kat.save();
                txtIdKategori.setText(Integer.toString(kat.getIdkategori()));
                tampilkanData();
            }
        });
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) tblKategori.getModel();
                int row = tblKategori.getSelectedRow();

                Kategori kat = new Kategori()
                        .getById(Integer
                                .parseInt(model.getValueAt(row, 0).toString()));
                kat.delete();
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
        tblKategori.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model = (DefaultTableModel) tblKategori.getModel();
                int row = tblKategori.getSelectedRow();

                txtIdKategori.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtKeterangan.setText(model.getValueAt(row, 2).toString());
            }
        });
    }

    public void kosongkanForm() {
        txtIdKategori.setText("0");
        txtNama.setText("");
        txtKeterangan.setText("");
    }

    public void tampilkanData() {
        String[] kolom = {"ID", "Nama", "Keterangan"};
        ArrayList<Kategori> list = new Kategori().getAll();
        Object rowData[] = new Object[3];

        tblKategori.setModel(new DefaultTableModel(new Object[][]{}, kolom));

        for (Kategori kat : list) {
            rowData[0] = kat.getIdkategori();
            rowData[1] = kat.getNama();
            rowData[2] = kat.getKeterangan();

            ((DefaultTableModel) tblKategori.getModel()).addRow(rowData);
        }
    }

    public void cari(String keyword) {
        String[] kolom = {"ID", "Nama", "Keterangan"};
        ArrayList<Kategori> list = new Kategori().search(keyword);
        Object rowData[] = new Object[3];

        tblKategori.setModel(new DefaultTableModel(new Object[][]{}, kolom));

        for (Kategori kat : list) {
            rowData[0] = kat.getIdkategori();
            rowData[1] = kat.getNama();
            rowData[2] = kat.getKeterangan();

            ((DefaultTableModel) tblKategori.getModel()).addRow(rowData);
        }
    }
}
