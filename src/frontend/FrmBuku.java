package frontend;

import backend.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FrmBuku {
    private JPanel panel1;
    private JTextField txtIdBuku;
    private JComboBox cmbKategori;
    private JTextField txtJudul;
    private JTextField txtPenerbit;
    private JTextField txtPenulis;
    private JButton btnSimpan;
    private JButton btnHapus;
    private JButton btnTambahBaru;
    private JTextField txtCari;
    private JButton btnCari;
    private JTable tblBuku;

    public static void main(String[] args) {
        JFrame frame = new JFrame("FrmBuku");
        frame.setContentPane(new FrmBuku().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public FrmBuku() {
        tampilkanData();
        tampilkanCmbKategori();
        kosongkanForm();
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Buku buku = new Buku();
                buku.setIdbuku(Integer.parseInt(txtIdBuku.getText()));
                buku.setKategori((Kategori) cmbKategori.getSelectedItem());
                buku.setJudul(txtJudul.getText());
                buku.setPenulis(txtPenulis.getText());
                buku.setPenerbit(txtPenerbit.getText());
                buku.save();

                txtIdBuku.setText(Integer.toString(buku.getIdbuku()));

                tampilkanData();
            }
        });
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) tblBuku.getModel();
                int row = tblBuku.getSelectedRow();

                Buku buku = new Buku()
                        .getById(Integer.parseInt(model
                                .getValueAt(row, 0)
                                .toString()));
                buku.delete();
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
        tblBuku.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model = (DefaultTableModel) tblBuku.getModel();
                int row = tblBuku.getSelectedRow();
                Buku buku = new Buku();

                buku = buku.getById(Integer
                        .parseInt(model
                                .getValueAt(row, 0)
                                .toString()));

                txtIdBuku.setText(String.valueOf(buku.getIdbuku()));
                cmbKategori.getModel().setSelectedItem(buku.getKategori());
                txtJudul.setText(buku.getJudul());
                txtPenerbit.setText(buku.getPenerbit());
                txtPenulis.setText(buku.getPenulis());
            }
        });
    }

    public void kosongkanForm() {
        txtIdBuku.setText("0");
        cmbKategori.setSelectedIndex(0);
        txtJudul.setText("");
        txtPenulis.setText("");;
        txtPenerbit.setText("");
    }

    public void tampilkanData() {
        String[] kolom = {"ID", "Kategori", "Judul", "Penulis", "Penerbit"};
        ArrayList<Buku> list = new Buku().getAll();
        Object rowData[] = new Object[5];

        tblBuku.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).getIdbuku();
            rowData[1] = list.get(i).getKategori().getNama();
            rowData[2] = list.get(i).getJudul();
            rowData[3] = list.get(i).getPenulis();
            rowData[4] = list.get(i).getPenerbit();

            ((DefaultTableModel) tblBuku.getModel()).addRow(rowData);
        }
    }

    public void cari(String keyword) {
        String[] kolom = {"ID", "Kategori", "Judul", "Penulis", "Penerbit"};
        ArrayList<Buku> list = new Buku().search(keyword);
        Object rowData[] = new Object[5];

        tblBuku.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (Buku buku : list) {
            rowData[0] = buku.getIdbuku();
            rowData[1] = buku.getKategori().getNama();
            rowData[2] = buku.getJudul();
            rowData[3] = buku.getPenulis();
            rowData[4] = buku.getPenerbit();

            ((DefaultTableModel) tblBuku.getModel()).addRow(rowData);
        }
    }

    public void tampilkanCmbKategori() {
        cmbKategori.setModel
                (new DefaultComboBoxModel
                        (new Kategori().getAll().toArray())
                );
    }
}
