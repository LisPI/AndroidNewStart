package com.example.lis.hello;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AI {
    public static void getAnswer(String question, final MyConsumer callback) {

        question = question.toLowerCase();
        final ArrayList<String> answers = new ArrayList<>();
        String full_answer = "";

        Map<String, String> db = new HashMap<String, String>() {{
            put("привет", "И вам здрасьте");
            put("как дела", "Вроде ничего");
            put("чем занят", "Отвечаю на дурацкие вопросы");
            put("есть ли жизнь на марсе", "Конечно есть");
            put("кто президент россии", "Путин");
            put("какое небо", "Голубое");
            put("какой сегодня день", getCurrentDate());
            put("сколько времени", getCurrentTime());
        }};


        for (String db_question : db.keySet()) {
            if(question.contains(db_question))
                answers.add(db.get(db_question));
        }

        if(question.contains("афоризм")){
            Dictum.get(new MyConsumer() {
                @Override
                public void myAccept(String s) {
                    /*answers.add(s);
                    String full_answer = "";
                    for(String answer: answers){
                        full_answer += answer + ", ";
                    }*/
                    callback.myAccept(s);
                }
            });
        }


        Pattern cityPattern = Pattern.compile("какая погода в городе (\\p{L}+)",Pattern.CASE_INSENSITIVE);
        Matcher matcher = cityPattern.matcher(question);
        if(matcher.find()){
            String cityName = matcher.group(1);
            Weather.get(cityName, new MyConsumer() {
                @Override
                public void myAccept(String s) {
                    /*answers.add(s);
                    String full_answer = "";
                    for(String answer: answers){
                        full_answer += answer + ", ";
                    }*/
                    callback.myAccept(s);
                }
            });
        }

        for(String answer: answers){
            full_answer += answer + ", ";
        }
        callback.myAccept(full_answer.isEmpty() ? "ok" : full_answer.substring(0, full_answer.length()-2));

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
