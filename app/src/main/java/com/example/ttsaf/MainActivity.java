package com.example.ttsaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    String contentVN = "Ở một cái giếng nọ có một con ếch sống lâu năm dưới đáy giếng, xung quanh nó chỉ toàn là những con nhái, ốc, cua bé nhỏ. Ở dưới đáy giếng nhìn lên trời, chú ếch chỉ có thể thấy được một khoảng trời rất bé như cái vung vậy.\n" +
            "\n" +
            "Mỗi lần ếch cất tiếng kêu ồm ộp đều làm các con vật khác trong giếng hoảng sợ nên ếch hênh hoang tự coi mình là chúa tể. Nó đã nghĩ thầm trong đầu rằng: “Tất cả vũ trụ chỉ có như thế, trời bé bằng vung”.\n" +
            "\n" +
            "Vì có suy nghĩ như thế nên nó cứ nhìn lên bầu trời bé xíu ấy và nghĩ nó thì oai phong giống như một vị chúa tể. Ngày nào cũng thấy như vậy nên nó đã khẳng định bầu trời chỉ to bằng cái vung mà thôi.\n" +
            "Và rồi một năm trời mưa rất to làm nước trong giếng đầy lên tràn bờ đưa ếch lên miệng giếng. Vẫn quen thói cũ nên ếch câng câng nhìn lên trời. Một điều bất ngờ đập vào mắt ếch chính là nó bỗng thấy cả một bầu trời rộng lớn gấp nhiều lần so với bầu trời bé như vung mà nó vẫn thấy khi ở bên dưới đáy giếng.\n" +
            "\n" +
            "Ếch không tin vào mắt mình và cảm thấy rất bực bội vì điều đó. Nó đã cất tiếng kêu ồm ộp để ra oai, ếch hi vọng sau những tiếng kêu của mình mọi thứ sẽ phải trở lại như ban đầu.\n" +
            "\n" +
            "Thế nhưng hiển nhiên là sau tiếng kêu của ếch mọi thứ vẫn vậy, bầu trời to lớn vẫn là bầu trời to lớn. Ếch càng lấy làm lạ và bực bội hơn nữa nên mải nhìn lên bầu trời không thèm để ý đến xung quanh nên nó đã bị một chú trâu đi ngang qua đó dẫm chết.";

    String contentEN = "The methods by which the Chinese communicate are deeply rooted in their history and culture. While it may not be easy for Westerners to accept these communication styles, it is important to remember that the Chinese, after all, understand each other perfectly. This author feels that the Chinese should not be called on to change the way they speak simply for the convenience of Westerners. Many Chinese have already made an effort to learn some English and Western communication styles. Perhaps Westerners have a responsibility in this increasingly globalized world to respond in kind.";

    Button btnVN;
    Button btnEN;
    Button btnSpeak;
    Button btnStop;
    TextView textviewContent;
    final Context context = this;
    TextToSpeech speak;
    String currentLang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEN = (Button) findViewById(R.id.btnEN);
        btnVN = (Button) findViewById(R.id.btnVn);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnSpeak = (Button) findViewById(R.id.btnSPEAK);
        textviewContent = (TextView) findViewById(R.id.textView);
        Locale vietnamese = new Locale("vi", "VN");
        speak = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    int result = speak.isLanguageAvailable(new Locale("vi", "VN"));
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        String text = "Xin chào, bạn có khỏe không?";
                        File file = new File(Environment.getExternalStorageDirectory(), "vietnamese_speech.wav");
                        speak.synthesizeToFile(text, null, file, null);
                    }
                    speak.setLanguage(vietnamese);

                }
            }
        });


        // Print list of locales to console



        currentLang = "VN";
        btnVN.setEnabled(false);
        btnVN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLang = "VN";
                textviewContent.setText(contentVN);
                btnVN.setEnabled(false);
                btnEN.setEnabled(true);
            }
        });
        btnEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentLang = "EN";
                textviewContent.setText(contentEN);
                btnEN.setEnabled(false);
                btnVN.setEnabled(true);
            }
        });
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLang == "VN")
                {
                    speak.setLanguage(vietnamese);
                }else {
                    speak.setLanguage(Locale.ENGLISH);
                }
                CharSequence toSpeak = textviewContent.getText();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                speak.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null,currentLang);
                btnSpeak.setEnabled(false);
                btnStop.setEnabled(true);
            }
        });
        textviewContent.setText(contentVN);
        btnStop.setEnabled(false);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak.stop();
                btnSpeak.setEnabled(true);
                btnStop.setEnabled(false);
            }
        });
    }

    @Override
    protected void onStop() {

        super.onStop();
        speak.shutdown();
    }
}