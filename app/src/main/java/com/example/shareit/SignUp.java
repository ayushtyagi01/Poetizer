package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {

    AutoCompleteTextView atv_user,atv_email,atv_password,atv_phone;
    Button btn_signIn;
    TextView tv_login;
    ImageView ivlogo;
    Uri uri;
    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    int Image_Request_Code=1;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);
        atv_email=findViewById(R.id.atv_email);
        atv_user=findViewById(R.id.atv_user);
        atv_password=findViewById(R.id.atv_password);
        atv_phone=findViewById(R.id.atv_phone);
        btn_signIn=findViewById(R.id.btn_signIn);
        tv_login=findViewById(R.id.tv_log);
        ivlogo=findViewById(R.id.ivLogo);

        auth= FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        ivlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Image_Request_Code);
            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReference("USERS");

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);

                try {
                    Intent intent = new Intent(SignUp.this, Login.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);

                String email=atv_email.getText().toString();
                String user=atv_user.getText().toString();
                String password=atv_password.getText().toString();
                String phone=atv_phone.getText().toString();

                if(email.isEmpty()||user.isEmpty()||password.isEmpty()||phone.isEmpty()){
                    Toast.makeText(SignUp.this, "ENTER ALL FIELDS!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if (uri != null) {
                                        StorageReference reference = storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        Users users = new Users(email, user, password, phone, FirebaseAuth.getInstance().getCurrentUser().getUid(), uri.toString());
                                                        FirebaseDatabase.getInstance().getReference("USERS")
                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(users)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                                                                        startActivity(intent);
                                                                        Toast.makeText(SignUp.this, "Account created Successfully!", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }else {
                                    Toast.makeText(SignUp.this, "Sign Up Failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            uri = data.getData();
            ivlogo.setImageURI(uri);
        } else {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }
}