package com.example.managestudent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SinhVienAdapter extends BaseAdapter {
    private ActivitySinhVien context;
    private int layout;
    private List<SinhVien> sinhVienList;

    public SinhVienAdapter(ActivitySinhVien context, int layout, List<SinhVien> sinhVienList) {
        this.context = context;
        this.layout = layout;
        this.sinhVienList = sinhVienList;
    }

    @Override
    public int getCount() {
        return sinhVienList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtHoTen, txtNgaySinh, txtDiaChi, txtGioiTinh, txtMaLop, txtMaSv;
        ImageView imgEdit, imgDelete;
    }

    @Override
    public View getView(int i, View view , ViewGroup viewGroup) {

        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtHoTen     = (TextView) view.findViewById(R.id.textviewHoTenCustom);
            holder.txtNgaySinh  = (TextView) view.findViewById(R.id.textviewNamSinhCustom);
            holder.txtDiaChi    = (TextView) view.findViewById(R.id.textviewDiaChiCustom);
            holder.txtGioiTinh  = (TextView) view.findViewById(R.id.textviewGioiTinhCustom);
            holder.txtMaSv      = (TextView) view.findViewById(R.id.textviewMaSvCustom);
            holder.txtMaLop     = (TextView) view.findViewById(R.id.textviewMaLopCustom);
            holder.imgDelete    = (ImageView)view.findViewById(R.id.imageviewDelete);
            holder.imgEdit      = (ImageView)view.findViewById(R.id.imageviewEdit);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        final SinhVien sinhvien = sinhVienList.get(i);

        holder.txtHoTen.setText( sinhvien.getHoTen());
        holder.txtNgaySinh.setText("Ngày sinh: "  + sinhvien.getNgaySinh());
        holder.txtDiaChi.setText(sinhvien.getDiaChi());
        holder.txtGioiTinh.setText("Giới tính: " +sinhvien.getGioiTinh());
        holder.txtMaLop.setText(sinhvien.getMaLop());
        holder.txtMaSv.setText("Mã SV: " +sinhvien.getMaSv());

        // bắt sự kiện xóa và sửa

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateKhoa.class);
                intent.putExtra("dataSinhVien", sinhvien);
                context.startActivity(intent);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XacNhanXoa(sinhvien.getHoTen(), sinhvien.getMaSv());
            }
        });


        return view;
    }

    private void XacNhanXoa(String ten, final String masv ){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa sinh viên " + ten + " không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                context.DeleteSinhVien(masv);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        dialogXoa.show();
    }
}
