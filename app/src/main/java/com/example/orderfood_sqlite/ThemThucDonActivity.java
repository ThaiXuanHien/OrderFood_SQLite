package com.example.orderfood_sqlite;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.orderfood_sqlite.adapter.LoaiThucDonAdapterSpinner;
import com.example.orderfood_sqlite.dao.LoaiThucDonDAO;
import com.example.orderfood_sqlite.dao.ThucDonDAO;
import com.example.orderfood_sqlite.dto.LoaiThucDonDTO;
import com.example.orderfood_sqlite.dto.ThucDonDTO;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;

public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_THEMLOAITD = 123;
    private static final int REQUEST_CODE_OPEN_GALLERY = 234;


    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int STORAGE_REQUEST_CODE = 102;

    private static final int IMAGE_PICK_CAMERA_CODE = 103;
    private static final int IMAGE_PICK_GALLERY_CODE = 104;

    private String[] cameraPermissions; // camera and storage
    private String[] storagePermissions; // only storage


    ImageView imgThemLoaiThucDon, imgThucDon, imgGallery;
    Spinner spinnerLoaiThucDon;
    LoaiThucDonDAO loaiThucDonDAO;
    ThucDonDAO thucDonDAO;
    List<LoaiThucDonDTO> loaiThucDonDTOList;
    LoaiThucDonAdapterSpinner loaiThucDonAdapter;
    TextInputLayout inputLayoutTenThucDon, inputLayoutGiaThucDon;
    Button btnDongYThemThucDon, btnHuyThemThucDon;
    TextView txtTitleThucDon;


    String duongDanHinh;
    private int maMonAn;
    private boolean modeMonAn;
    private String tenMonAn;
    private int maLoaiTD;
    private String gia;
    private String hinhAnh;

    private Uri uriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thuc_don);

        loaiThucDonDAO = new LoaiThucDonDAO(this);
        thucDonDAO = new ThucDonDAO(this);

        anhXa();

        hienThiLoaiThucDonSpinner();


        Intent intent = getIntent();
        maMonAn = intent.getIntExtra("mamonan", 0);
        maLoaiTD = intent.getIntExtra("loaithucdon", 0);
        modeMonAn = intent.getBooleanExtra("modeMonAn", false);
        tenMonAn = intent.getStringExtra("tenMonAn");
        gia = intent.getStringExtra("gia");
        hinhAnh = intent.getStringExtra("hinhanh");

        if (modeMonAn) {
            txtTitleThucDon.setText("Cập nhật thực đơn");
            inputLayoutTenThucDon.getEditText().setText(tenMonAn);
            inputLayoutGiaThucDon.getEditText().setText(gia);
            imgThucDon.setImageURI(Uri.parse(hinhAnh));
            duongDanHinh = hinhAnh;
            uriImage = Uri.parse(hinhAnh);
        }

        imgThemLoaiThucDon.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
        btnDongYThemThucDon.setOnClickListener(this);
        btnHuyThemThucDon.setOnClickListener(this);
    }

    private void anhXa() {
        imgThemLoaiThucDon = findViewById(R.id.imgThemLoaiThucDon);
        spinnerLoaiThucDon = findViewById(R.id.spinner_loaiThucDon);
        imgThucDon = findViewById(R.id.imgThucDon);
        imgGallery = findViewById(R.id.imgGallery);
        btnDongYThemThucDon = findViewById(R.id.btnDongYThemThucDon);
        btnHuyThemThucDon = findViewById(R.id.btnHuyThemThucDon);
        inputLayoutTenThucDon = findViewById(R.id.inputLayoutTenThucDon);
        inputLayoutGiaThucDon = findViewById(R.id.inputLayoutGiaThucDon);
        txtTitleThucDon = findViewById(R.id.txtTitleThucDon);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    public void hienThiLoaiThucDonSpinner() {
        loaiThucDonDTOList = loaiThucDonDAO.LayDanhSachLoaiThucDon();
        loaiThucDonAdapter = new LoaiThucDonAdapterSpinner(this, R.layout.custom_spinner_loaithucdon, loaiThucDonDTOList);
        spinnerLoaiThucDon.setAdapter(loaiThucDonAdapter);
        loaiThucDonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.imgThemLoaiThucDon:
                Intent intent = new Intent(this, ThemLoaiThucDonActivity.class);
                intent.putExtra("modeLTD", false);
                startActivityForResult(intent, REQUEST_CODE_THEMLOAITD);
                break;
            case R.id.imgGallery:
//                Intent intentOpenGallery = new Intent();
//                intentOpenGallery.setType("image/*");
//                intentOpenGallery.setAction(Intent.ACTION_OPEN_DOCUMENT);
//                startActivityForResult(Intent.createChooser(intentOpenGallery, "Chọn Folder"), REQUEST_CODE_OPEN_GALLERY);
                imagePickDialog();
                break;
            case R.id.btnDongYThemThucDon:

                int viTri = spinnerLoaiThucDon.getSelectedItemPosition();

                String tenThucDon = inputLayoutTenThucDon.getEditText().getText().toString().trim();
                String giaTien = inputLayoutGiaThucDon.getEditText().getText().toString().trim();

                if (!tenThucDon.isEmpty() && !giaTien.isEmpty()) {
                    if (modeMonAn) {
                        ThucDonDTO monAnDTO = new ThucDonDTO();
                        monAnDTO.setGiaTien(giaTien);
                        monAnDTO.setMaThucDon(maMonAn);
                        monAnDTO.setHinhAnh(duongDanHinh);
                        int maLoaiThucDon = loaiThucDonDTOList.get(viTri).getMaLoaiThucDon();
                        monAnDTO.setMaLoaiThucDon(maLoaiThucDon);
                        monAnDTO.setTenThucDon(tenThucDon);
                        Toast.makeText(this, "" + monAnDTO.toString(), Toast.LENGTH_SHORT).show();
                        boolean kiemTra = thucDonDAO.capNhatLaiTenThucDon(monAnDTO);
                        Intent iThemTD = new Intent();
                        iThemTD.putExtra("capNhatMonAn", kiemTra);
                        setResult(Activity.RESULT_OK, iThemTD);
                    } else {
                        ThucDonDTO monAnDTO = new ThucDonDTO();
                        monAnDTO.setGiaTien(giaTien);
                        monAnDTO.setHinhAnh(duongDanHinh);
                        int maLoaiThucDon = loaiThucDonDTOList.get(viTri).getMaLoaiThucDon();
                        monAnDTO.setMaLoaiThucDon(maLoaiThucDon);
                        monAnDTO.setTenThucDon(tenThucDon);

                        boolean kiemTra = thucDonDAO.themThucDon(monAnDTO);
                        Intent iThemTD = new Intent();
                        iThemTD.putExtra("themThucDon", kiemTra);
                        setResult(Activity.RESULT_OK, iThemTD);
                    }


//                    if (kiemTra) {
//                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
//                    }
                    finish();

                } else {
                    Toast.makeText(this, "Lỗi chưa điền đầy đủ", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnHuyThemThucDon:
                finish();
                break;
        }
    }

    private void imagePickDialog() {
        // options to display in dialog
        String[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                // handle clicks
                if (which == 0) {
                    if (!checkCameraPermission()) { // camera clicked
                        requestCameraPermission();
                    } else {
                        // permission already granted
                        pickFromCamera();
                    }
                } else if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        // permission already granted
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void pickFromGallery() {
        // Intent to pick image from gallery, the image will be returned in onActivityResult method
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        startActivityForResult(intentGallery, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        // Intent to pick image from camera, the image will be returned in onActivityResult method
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image description");

        // put image uri
        uriImage = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // Intent to open camera for image
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
        startActivityForResult(intentCamera, IMAGE_PICK_CAMERA_CODE);
    }
    private boolean checkStoragePermission() {
        // check if storage permission enabled or not
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {
        // request the storage permission

        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        // check if camera permission enabled or not
        boolean storage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        boolean camera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return storage && camera;
    }

    private void requestCameraPermission() {
        // request the camera permission

        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // result of permission allowed/denied
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    // if allowed returns true ortherwise false
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted) { // both permission allowed
                        pickFromCamera();
                    } else {
                        Toast.makeText(this, "Camera and Storage permissions are required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    // if allowed returns true ortherwise false
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Storage permissions is required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_THEMLOAITD) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemTra = intent.getBooleanExtra("themLoaiThucDon", false);
                if (kiemTra) {
                    hienThiLoaiThucDonSpinner();
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
//        else if (requestCode == REQUEST_CODE_OPEN_GALLERY && data != null) {
//            if (resultCode == Activity.RESULT_OK) {
//
//                duongDanHinh = data.getData().toString();
//
//                imgThucDon.setImageURI(data.getData());
//            }
//        }
        // image picked from camera or gallery will be received here
        if (resultCode == RESULT_OK) { // image is picked
            if (requestCode == IMAGE_PICK_GALLERY_CODE) { // picked galelry
                // crop image
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1).start(this);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                // crop image
                CropImage.activity(uriImage).setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1).start(this);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                // cropped image received
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    uriImage = resultUri;
                    // set image
                    imgThucDon.setImageURI(uriImage);

                    duongDanHinh = result.getUri().toString();
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}