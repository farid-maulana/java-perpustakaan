package frontend;

import backend.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FrmPeminjaman {
    private JTextField txtId;
    private JTextField txtIdAnggota;
    private JTextField txtIdBuku;
    private JTextField txtTanggalPinjam;
    private JTextField txtTanggalKembali;
    private JButton btnCariAnggota;
    private JButton btnCariBuku;
    private JButton btnSimpan;
    private JButton btnHapus;
    private JButton btnTambahBaru;
    private JTable tblPeminjaman;
    private JLabel idAnggota;
    private JLabel idBuku;
    private JLabel id;
    private JPanel panel1;
    private JLabel tanggalPinjam;
    private JLabel tanggalKembali;
    private JLabel namaAnggota;
    private JLabel judulBuku;
    private JLabel formatTanggalPinjam;
    private JLabel formatTanggalKembali;

    public static void main(String[] args) {
        JFrame frame = new JFrame("FrmPeminjaman");
        frame.setContentPane(new FrmPeminjaman().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public FrmPeminjaman() {
        kosongkanForm();
        tampilkanData();
        btnCariAnggota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cariAnggota(txtIdAnggota.getText());
            }
        });
        btnCariBuku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cariBuku(txtIdBuku.getText());
            }
        });
        btnTambahBaru.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kosongkanForm();
            }
        });
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) tblPeminjaman.getModel();
                int row = tblPeminjaman.getSelectedRow();

                Peminjaman peminjaman = new Peminjaman()
                        .getById(Integer.parseInt
                                (model.getValueAt(row, 0).toString()));
                peminjaman.delete();
                kosongkanForm();
                tampilkanData();
            }
        });
        tblPeminjaman.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model = (DefaultTableModel) tblPeminjaman.getModel();
                int row = tblPeminjaman.getSelectedRow();
                Peminjaman peminjaman = new Peminjaman();

                peminjaman = peminjaman
                        .getById(Integer.parseInt
                                (model.getValueAt(row, 0).toString()));

                txtId.setText(String.valueOf(peminjaman.getIdpeminjaman()));
                txtIdAnggota.setText(String.valueOf(peminjaman.getAnggota().getIdanggota()));
                txtIdBuku.setText(String.valueOf(peminjaman.getBuku().getIdbuku()));
                txtTanggalPinjam.setText(String.valueOf(peminjaman.getTanggalpinjam()));
                txtTanggalKembali.setText(String.valueOf(peminjaman.getTanggalkembali()));
            }
        });
    }

    public void kosongkanForm() {
        txtId.setText("0");
        txtIdBuku.setText("0");
        txtIdAnggota.setText("0");
        txtTanggalPinjam.setText("");
        txtTanggalKembali.setText("");
    }

    public void tampilkanData() {
        String[] kolom = {"ID", "Nama Anggota", "Judul", "Tanggal Pinjam", "Tanggal Kembali"};
        ArrayList<Peminjaman> list = new Peminjaman().getAll();
        Object rowData[] = new Object[5];

        tblPeminjaman.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).getIdpeminjaman();
            rowData[1] = list.get(i).getAnggota().getNama();
            rowData[2] = list.get(i).getBuku().getJudul();
            rowData[3] = list.get(i).getTanggalpinjam();
            rowData[4] = list.get(i).getTanggalkembali();

            ((DefaultTableModel) tblPeminjaman.getModel()).addRow(rowData);
        }
    }

    public void cariAnggota(String keyword) {
        String nama = new Peminjaman().searchAnggota(keyword);
        namaAnggota.setText(nama);
    }

    public void cariBuku(String keyword) {
        String judul = new Peminjaman().searchBuku(keyword);
        judulBuku.setText(judul);
    }
}
