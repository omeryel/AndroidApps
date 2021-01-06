package com.example.instagramclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


public class SharePictureTab extends Fragment implements  View.OnClickListener{

        private ImageView imageView;
        private EditText editText;
        private Button button;
        Bitmap receivedImageBitmap;

    public SharePictureTab() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        imageView  = view.findViewById(R.id.img_share);
        editText = view.findViewById(R.id.txt_image);
        button = view.findViewById(R.id.btn_image_share);

        imageView.setOnClickListener(SharePictureTab.this);
        button.setOnClickListener(SharePictureTab.this);


        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_share:
                if(Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1000);
            }
                else{
                    getChosenImage();
                }
                break;
            case R.id.btn_image_share:
                if ( receivedImageBitmap != null){
                    if (editText.getText().toString().equals("")){
                        FancyToast.makeText(getContext(),"Error: You must enter description for image!!",FancyToast.ERROR,FancyToast.LENGTH_LONG,false).show();
                    }
                    else{

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("pic.png",bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("image_des",editText.getText().toString());
                        parseObject.put("username",ParseUser.getCurrentUser().getUsername());
                        final ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.setMessage("Loading...");
                        dialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if ( e == null){
                                    FancyToast.makeText(getContext(),"Done!!",FancyToast.SUCCESS,FancyToast.LENGTH_LONG,false).show();
                                }
                                else{
                                    FancyToast.makeText(getContext(),"Unknown Error:",FancyToast.WARNING,FancyToast.LENGTH_SHORT,false).show();
                                }
                                dialog.dismiss();

                            }
                        });

                    }


                }
                else{
                    FancyToast.makeText(getContext(),"Error: You must select an image!!",FancyToast.ERROR,FancyToast.LENGTH_LONG,false).show();
                }
                break;
        }
    }

    private void getChosenImage() {

        //FancyToast.makeText(getContext(),"Now we can access the images",FancyToast.SUCCESS,FancyToast.LENGTH_SHORT,false).show();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1000);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getChosenImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                try{
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);

                    imageView.setImageBitmap(receivedImageBitmap);

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }



    }
}