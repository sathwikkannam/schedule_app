package com.example.schedule_app;

import android.widget.TextView;

import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class FirebaseTranslator {
    private Translator translator;
    private static FirebaseTranslator firebaseTranslator = null;

    private FirebaseTranslator(String source, String target){
        createTranslatorModel(source, target);
        downloadModel();
    }

    public static FirebaseTranslator getInstance(String source, String target){
        if(firebaseTranslator == null){
            firebaseTranslator = new FirebaseTranslator(source, target);
        }
        return firebaseTranslator;
    }


    private void createTranslatorModel(String source, String target){
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(source)
                        .setTargetLanguage(target)
                        .build();
        this.translator = com.google.mlkit.nl.translate.Translation.getClient(options);

    }

    private void downloadModel(){
        DownloadConditions conditions = new DownloadConditions.Builder().requireWifi().build();
        this.translator.downloadModelIfNeeded(conditions);
    }

    public void closeTranslationModel(){
        this.translator.close();
    }

    public void translate(TextView view, String text){
        this.translator.translate(text)
                .addOnSuccessListener(view::setText)
                .addOnFailureListener(exception -> view.setText(text));

    }


    public Translator getTranslator(){
        return this.translator;
    }

}
