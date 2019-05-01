package com.example.lis.hello;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AI {
    @SuppressLint("NewApi")
    public static String getAnswer(String question) {

        question = question.toLowerCase();
        ArrayList<String> answers = new ArrayList<>();
        String full_answer = "";

        Map<String, String> db = new HashMap<String, String>() {{
            put("привет", "и вам здрасьте");
            put("как дела", "вроде ничего");
            put("чем занят", "отвечаю на вопросы");
        }};


        for (String db_question : db.keySet()) {
            if(question.contains(db_question))
                answers.add(db.get(db_question));
        }

        for(String answer: answers){
            full_answer += answer + ", ";
        }

        return full_answer.isEmpty() ? "ok" : full_answer.substring(0, full_answer.length()-2);

    }
}
