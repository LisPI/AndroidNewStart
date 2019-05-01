package com.example.lis.hello;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AI {
    @SuppressLint("NewApi")
    public static void getAnswer(String question, final Consumer<String> callback) {

        question = question.toLowerCase();
        final ArrayList<String> answers = new ArrayList<>();
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


        Pattern cityPattern = Pattern.compile("какая погода в городе (\\p{L}+)",Pattern.CASE_INSENSITIVE);
        Matcher matcher = cityPattern.matcher(question);
        if(matcher.find()){
            String cityName = matcher.group(1);
            Weather.get(cityName, new Consumer<String>() {
                @Override
                public void accept(String s) {
                    answers.add(s);
                    String full_answer = "";
                    for(String answer: answers){
                        full_answer += answer + ", ";
                    }
                    callback.accept(full_answer.substring(0, full_answer.length()-2));
                }
            });
        }
        else {
            for(String answer: answers){
                full_answer += answer + ", ";
            }
            callback.accept(full_answer.isEmpty() ? "ok" : full_answer.substring(0, full_answer.length()-2));
            return;
        }




    }
}
