package com.muzika.app_quiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.CountDownTimer;
import android.os.MemoryFile;
import android.os.StrictMode;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionsFragment extends Fragment {

    private RequestQueue requestQueue;
    private int pageNumber;
    private TextView question;
    private TextView resultText;
    private RadioButton button1;
    private RadioButton button2;
    private RadioButton button3;
    private RadioButton button4;
    private ImageView imageView;
    private Button buttonHelp;
    private Button buttonHelpFriend;
    private Button buttonCallFriend;
    private Button buttonCompleteTheGame;
    private RadioGroup radGrp;
    private static int btnHelp=0;
    private static int btnHelpFriend=0;
    private static int btnCallFriend=0;
    private String correctAnswer;
    private CallDialog dialog;

    public QuestionsFragment() {
    }


    public static QuestionsFragment newInstance(int page) {
        QuestionsFragment fragment = new QuestionsFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_question, container, false);
        elementInitialization(view);
        methodForLogic();
        if (pageNumber < 7) {
            methodGroupForQuestions();
        } else {
            methodForTotalFragment();
        }
        return view;
    }

    private void elementInitialization(View view) {
        question = view.findViewById(R.id.question);
        resultText = view.findViewById(R.id.result);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        radGrp = view.findViewById(R.id.answers);
        buttonHelp = view.findViewById(R.id.buttonHelp);
        buttonHelpFriend = view.findViewById(R.id.buttonHelpFriend);
        buttonCallFriend = view.findViewById(R.id.buttonCallFriend);
        buttonCompleteTheGame = view.findViewById(R.id.completeTheGame);
        imageView = view.findViewById(R.id.question_iv);
    }

    private void methodForLogic() {
        if (pageNumber < 7){
            buttonCompleteTheGame.setVisibility(View.GONE);
            resultText.setVisibility(View.GONE);
        }   else {
            buttonHelp.setVisibility(View.GONE);
            buttonHelpFriend.setVisibility(View.GONE);
            buttonCallFriend.setVisibility(View.GONE);
            radGrp.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            question.setVisibility(View.GONE);
            getActivity().findViewById(R.id.timer).setVisibility(View.GONE);
        }
        if (btnHelp == 3){
            buttonHelp.setVisibility(View.GONE);
        }
        if (btnHelpFriend == 1){
            buttonHelpFriend.setVisibility(View.GONE);
        }
        if (btnCallFriend == 1){
            buttonCallFriend.setVisibility(View.GONE);
        }
    }

    private void methodGroupForQuestions() {
        getQuestion();
        listenerForAnswerButtons();
        listenerPerButton50by50();
        listenerForFriendHelpButton();
        listenerForFriendCallButton();
    }

    private void methodForTotalFragment() {
        resultText.setText(Result.username + " ваш результат: " + Result.result);
        buttonCompleteTheGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2:8080/authorization/username";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        response -> { }
                        ,
                        error -> {}
                )
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError{
                        Map<String, String> params = new HashMap<>();
                        params.put("username", Result.username);
                        params.put("result", String.valueOf(Result.result));
                        return params;
                    }
                };
                requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
    }

    private void getQuestion() {
        String url = "http://10.0.2.2:8080/question/"+pageNumber;
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            question.setText(response.getString("question"));
                            button1.setText(response.getString("answer1"));
                            button2.setText(response.getString("answer2"));
                            button3.setText(response.getString("answer3"));
                            button4.setText(response.getString("answer4"));
                            correctAnswer = response.getString("correctAnswer");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        try {
                            URL url = new URL("http://10.0.2.2:8080/question/image/" + pageNumber + ".png");
                            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            imageView.setImageBitmap(image);
                        } catch(IOException e) {
                            System.out.println(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObject);
    }

    private void listenerForAnswerButtons() {
        radGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.button1:
                        switchToNewFragment(1);
                        SystemClock.sleep(500);
                        goToANewPage();
                        break;
                    case R.id.button2:
                        switchToNewFragment(2);
                        SystemClock.sleep(500);
                        goToANewPage();
                        break;
                    case R.id.button3:
                        switchToNewFragment(3);
                        SystemClock.sleep(500);
                        goToANewPage();
                        break;
                    case R.id.button4:
                        switchToNewFragment(4);
                        SystemClock.sleep(500);
                        goToANewPage();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void listenerPerButton50by50() {
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnHelp < 3) {
                    int[] numberOfAttempts = new int[3];
                    for (int i = 0; i < numberOfAttempts.length; i++) {
                        boolean flag = false;
                        int randomButton = (int) ((Math.random() * 4) + 1);
                        for (int y = 0; y < numberOfAttempts.length; y++) {
                            if (numberOfAttempts[y] == randomButton) {
                                i--;
                                flag = true;
                            }
                        }
                        if (!flag) numberOfAttempts[i] = randomButton;
                    }
                    int i = 0;
                    for (int z = 0; z < numberOfAttempts.length; z++) {
                        if (Integer.parseInt(correctAnswer) != numberOfAttempts[z] & i < 2) {
                            if (numberOfAttempts[z] == 1) button1.setVisibility(View.INVISIBLE);
                            if (numberOfAttempts[z] == 2) button2.setVisibility(View.INVISIBLE);
                            if (numberOfAttempts[z] == 3) button3.setVisibility(View.INVISIBLE);
                            if (numberOfAttempts[z] == 4) button4.setVisibility(View.INVISIBLE);
                            i++;
                        }
                    }
                    btnHelp++;
                }
                buttonHelp.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void listenerForFriendHelpButton() {
        buttonHelpFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Integer.parseInt(correctAnswer)){
                    case 1:
                        button1.setTextColor(Color.GREEN);
                        break;
                    case 2:
                        button2.setTextColor(Color.GREEN);
                        break;
                    case 3:
                        button3.setTextColor(Color.GREEN);
                        break;
                    case 4:
                        button4.setTextColor(Color.GREEN);
                        break;
                }
                buttonHelpFriend.setVisibility(View.INVISIBLE);
                btnHelpFriend++;
            }
        });
    }

    private void listenerForFriendCallButton() {
        buttonCallFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new CallDialog();
                dialog.show(requireActivity().getSupportFragmentManager(), null);
                btnCallFriend++;
            }
        });
    }

    private void goToANewPage() {
        ViewPager2 pager = getActivity().findViewById(R.id.pager);
        FragmentStateAdapter pageAdapter = new MyAdapter(getActivity());
        pager.setCurrentItem(++pageNumber, false);
        pager.setAdapter(pageAdapter);
    }

    private void switchToNewFragment(int i) {
        if (Integer.parseInt(correctAnswer) == i){
            Result.result++;
            Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
    }

}