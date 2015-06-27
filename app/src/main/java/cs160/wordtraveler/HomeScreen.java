package cs160.wordtraveler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;


public class HomeScreen extends ActionBarActivity {

    Context thisContext;
    Spinner speakSpinner;
    Spinner fromSpinner;
    Spinner toSpinner;
    Button transButton;
    EditText transInEditText;
    TextView changeTextView;
    String[] SpeakLangArray;
    String[] SpeakLangTexts;
    String[] TransLangArray;
    String[] TextSelect = {"text_eng", "text_span", "text_man"};
    String[] LangSelect = {"langs_eng", "langs_span", "langs_man"};
    String fromLang = "text_eng";
    String toLang = "text_eng";


    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        thisContext = this;
        transButton = (Button) findViewById(R.id.trans_button);
        transInEditText = (EditText) findViewById(R.id.trans_in);

        speakSpinner = (Spinner)findViewById(R.id.speak_spinner);
        SpeakLangArray = getResources().getStringArray(R.array.speak_lang);
        ArrayAdapter<String> speakAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, SpeakLangArray);
        speakSpinner.setAdapter(speakAdapter);

        fromSpinner = (Spinner)findViewById(R.id.trans_from_spinner);
        TransLangArray = getResources().getStringArray(R.array.langs_eng);
        ArrayAdapter<String> transAdapter = new ArrayAdapter<String>(
                thisContext, android.R.layout.simple_spinner_item, TransLangArray);
        fromSpinner.setAdapter(transAdapter);

        toSpinner = (Spinner)findViewById(R.id.trans_to_spinner);
        toSpinner.setAdapter(transAdapter);

        speakSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = speakSpinner.getSelectedItemPosition();

                        String SelectText = TextSelect[position];
                        int holderint = getResources().getIdentifier(SelectText, "array",
                                "cs160.wordtraveler");
                        SpeakLangTexts = getResources().getStringArray(holderint);

                        changeTextView = (TextView) findViewById(R.id.i_speak);
                        changeTextView.setText(SpeakLangTexts[0]);
                        changeTextView = (TextView) findViewById(R.id.trans_from);
                        changeTextView.setText(SpeakLangTexts[1]);
                        changeTextView = (TextView) findViewById(R.id.trans_to);
                        changeTextView.setText(SpeakLangTexts[2]);
                        transInEditText.setHint(SpeakLangTexts[3]);
                        transButton.setText(SpeakLangTexts[4]);
                        changeTextView = (TextView) findViewById(R.id.display1);
                        changeTextView.setText(SpeakLangTexts[5]);
                        changeTextView = (TextView) findViewById(R.id.display2);
                        changeTextView.setText(SpeakLangTexts[6]);

                        String SelectLang = LangSelect[position];
                        holderint = getResources().getIdentifier(SelectLang, "array",
                                "cs160.wordtraveler");
                        TransLangArray = getResources().getStringArray(holderint);

                        ArrayAdapter<String> changeAdapter = new ArrayAdapter<String>(
                                thisContext, android.R.layout.simple_spinner_item, TransLangArray);
                        fromSpinner.setAdapter(changeAdapter);
                        toSpinner.setAdapter(changeAdapter);

                        //Toast.makeText(getApplicationContext(), "You have selected " + SpeakLangTexts[3], Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );

        fromSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = fromSpinner.getSelectedItemPosition();
                        fromLang = TextSelect[position];
                        //Toast.makeText(getApplicationContext(), "You have selected " + fromLang, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {}
                }
        );

        toSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = toSpinner.getSelectedItemPosition();
                        toLang = TextSelect[position];
                        //Toast.makeText(getApplicationContext(), "You have selected " + toLang, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {}
                }
        );

        transButton = (Button) findViewById(R.id.trans_button);
        transButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText enter = (EditText) findViewById(R.id.trans_in);
                CharSequence transChar = enter.getText();
                String transText = transChar.toString();
                changeTextView = (TextView) findViewById(R.id.display1);
                changeTextView.setText(transText);
                String translation = transText;
                if (checkCorrectTransAll(fromLang, transText)){
                    translation = translateAll(toLang, transText);
                }
                changeTextView = (TextView) findViewById(R.id.display2);
                changeTextView.setText(translation);
            }
        });

    }

    public boolean checkCorrectTransAll(String transLang, String transText){
        switch (transLang){
            case "text_eng":
                return checkCorrectTransEng(transText.toUpperCase());
            case "text_span":
                return checkCorrectTransSpan(transText.toUpperCase());
            case "text_man":
                return checkCorrectTransMan(transText);
            default:
                return false;
        }
    }

    public boolean checkCorrectTransEng(String transText){
        switch (transText){
            case "HELLO":case "GOODBYE":case "THANK YOU":case "PLEASE":case "HOW MUCH":case "HOW MUCH?":
                return true;
            default:
                return false;
        }
    }

    public boolean checkCorrectTransSpan(String transText){
        switch (transText){
            case "HOLA":case "ADIOS":case "ADIÓS":case "GRACIAS":case "POR FAVOR":case "CUANTO":case "CUÁNTO":case "CUANTO?":case "¿CUANTO?":case "CUÁNTO?":case "¿CUÁNTO?":
                return true;
            default:
                return false;
        }
    }

    public boolean checkCorrectTransMan(String transText){
        switch (transText){
            case "您好":case "再见":case "谢谢":case "请":case "多少钱":case "多少钱?":
                return true;
            default:
                return false;
        }
    }

    public String translateAll(String transLang, String transText){
        if (!fromLang.equals("text_man")){
            transText = transText.toUpperCase();
        }
        switch (transLang){
            case "text_eng":
                return translateEng(transText);
            case "text_span":
                return translateSpan(transText);
            case "text_man":
                return translateMan(transText);
            default:
                return transText;
        }
    }

    public String translateEng(String transText){
        switch (transText){
            case "HELLO":case "HOLA":case "您好":
                return "hello";
            case "GOODBYE":case "ADIOS":case "ADIÓS":case "再见":
                return "goodbye";
            case "THANK YOU":case "GRACIAS":case "谢谢":
                return "thank you";
            case "PLEASE":case "POR FAVOR":case "请":
                return "please";
            case "HOW MUCH":case "CUANTO":case "CUÁNTO":case "多少钱":case "HOW MUCH?":case "CUANTO?":case "CUÁNTO?":case "多少钱?":case "¿CUANTO?":case "¿CUÁNTO?":
                return "how much";
            default:
                return transText;
        }
    }

    public String translateSpan(String transText){
        switch (transText){
            case "HELLO":case "HOLA":case "您好":
                return "hola";
            case "GOODBYE":case "ADIOS":case "ADIÓS":case "再见":
                return "adiós";
            case "THANK YOU":case "GRACIAS":case "谢谢":
                return "gracias";
            case "PLEASE":case "POR FAVOR":case "请":
                return "por favor";
            case "HOW MUCH":case "CUANTO":case "CUÁNTO":case "多少钱":case "HOW MUCH?":case "CUANTO?":case "CUÁNTO?":case "多少钱?":case "¿CUANTO?":case "¿CUÁNTO?":
                return "cuánto";
            default:
                return transText;
        }
    }

    public String translateMan(String transText){
        switch (transText){
            case "HELLO":case "HOLA":case "您好":
                return "您好";
            case "GOODBYE":case "ADIOS":case "ADIÓS":case "再见":
                return "再见";
            case "THANK YOU":case "GRACIAS":case "谢谢":
                return "谢谢";
            case "PLEASE":case "POR FAVOR":case "请":
                return "请";
            case "HOW MUCH":case "CUANTO":case "CUÁNTO":case "多少钱":case "HOW MUCH?":case "CUANTO?":case "CUÁNTO?":case "多少钱?":case "¿CUANTO?":case "¿CUÁNTO?":
                return "多少钱";
            default:
                return transText;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
