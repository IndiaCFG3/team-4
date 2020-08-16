package com.dev5151.educate.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.dev5151.educate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DashboardFragment extends Fragment {

    private String courseId;
    FirebaseFirestore userRef = FirebaseFirestore.getInstance();
    Button generateCertificate;

    private FirebaseFirestore mFirestore;
    private ProgressBar quiz;
    private ProgressBar videos;
    private FirebaseAuth mAuth;
    private Double quizProgress;
    private Double videosProgress;


    public static DashboardFragment getInstance(String courseId) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString("courseId", courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courseId = getArguments().getString("courseId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        generateCertificate = view.findViewById(R.id.generate_certificate);
        generateCertificate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    return;
                } else {
                    createPdf(courseId);
                }
            }
        });
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        quiz = view.findViewById(R.id.progressBarQuiz);
        videos = view.findViewById(R.id.progressBar);
        System.out.println(mAuth.getUid() + " # " + courseId);
        mFirestore.collection("Progress").document(mAuth.getUid() + " # " + courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        quizProgress = doc.getDouble("quiz");
                        videosProgress = doc.getDouble("video");
                        System.out.println(quizProgress);
                        System.out.println(videosProgress);
                        quiz.setProgress((int) (quizProgress * 100));
                        videos.setProgress((int) (videosProgress * 100));
                        if(quizProgress+videosProgress!=2.0) {
                            generateCertificate.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createPdf(courseId);
                } else {
                    Toast.makeText(getActivity(), "Access to read messages needed", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void createPdf(final String courseId) {

        userRef.collection("Users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String username = (String) document.get("uname");
                        String email = (String) document.get("email");

                        Bitmap bitmapSmall = BitmapFactory.decodeResource(getResources(), R.drawable.tiss);
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmapSmall, 1200, 518, false);

                        PdfDocument pdfDocument = new PdfDocument();
                        Paint paint = new Paint();
                        Paint titlePaint = new Paint();

                        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                        Canvas canvas = page.getCanvas();
                        canvas.drawBitmap(scaledBitmap, 0, 0, paint);

                        titlePaint.setTextAlign(Paint.Align.CENTER);
                        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                        titlePaint.setTextSize(70);
                        canvas.drawText("Course Completion Certificate", 1200 / 2, 270, titlePaint);
                        paint.setColor(Color.rgb(0, 113, 138));
                        paint.setTextSize(30f);

                        titlePaint.setTextAlign(Paint.Align.RIGHT);
                        titlePaint.setTextSize(50);
                        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                        Date date = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/YYYY");
                        canvas.drawText("Date: " + simpleDateFormat.format(date), 1200 - 20, 640, paint);

                        titlePaint.setTextAlign(Paint.Align.LEFT);
                        titlePaint.setTextSize(100);
                        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                        canvas.drawText("Congratulations for Successfully Completing the course, We hope you throughly enjoyed the course, Best of luck for your Future", 1200 / 2, 270, titlePaint);


                        titlePaint.setTextAlign(Paint.Align.LEFT);
                        titlePaint.setTextSize(50);
                        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                        canvas.drawText("Name: " + username, 1200 - 20, 640, paint);

                        titlePaint.setTextAlign(Paint.Align.LEFT);
                        titlePaint.setTextSize(50);
                        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                        canvas.drawText("Email: " + email, 1200 - 20, 640, paint);


                        pdfDocument.finishPage(page);

                        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                        System.out.println(baseDir);
                        String fileName = "Certificate_" + courseId + ".pdf";
                        try {
                            File file = new File(baseDir + File.separator + fileName);
                            if(file.exists()) {
                                file.mkdirs();
                                System.out.println("Exits");
                            }
                            pdfDocument.writeTo(new FileOutputStream(file));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        pdfDocument.close();
                    }
                }
            }
        });
    }
}
