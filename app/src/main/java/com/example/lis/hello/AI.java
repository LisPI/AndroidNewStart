package com.example.lis.hello;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
            put("есть ли жизнь на марсе", "конечно есть");
            put("кто президент россии", "Путин");
            put("какое небо", "голубое");
            put("какой сегодня день", getCurrentDate());
            put("сколько сейчас времени", getCurrentTime());
        }};


        for (String db_question : db.keySet()) {
            if(question.contains(db_question))
                answers.add(db.get(db_question));
        }

        if(question.contains("расскажи афоризм")){
            Dictum.get(new Consumer<String>() {
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

    protected static String getCurrentDate(){
        DateFormat fmt = new SimpleDateFormat("d MMMM, EEEE", Locale.forLanguageTag("ru"));

        return fmt.format(new Date());
    }

    protected static String getCurrentTime(){

        DateFormat fmt = new SimpleDateFormat("HH:mm");

        return fmt.format(new Date());
    }
}
