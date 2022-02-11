package com.luke.rockpaperscissorsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView handAnimImageView; // 이미지 뷰 변수 선언
    ImageView setHandImageView;

    ImageButton gaweButton; // 이미지 버튼 변수 선언
    ImageButton baweButton;
    ImageButton boButton;
    ImageButton replayButton;

    AnimationDrawable animationDrawable; // 애니메이션 변수 선언

    TextToSpeech textToSpeech; // 문자를 음성으로 변환해주는 TextToSpeech 변수 선언
    // 리스너도 바로 선언
    TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int i) {
            if(i != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.KOREAN); // 언어 한국어로 설정
                textToSpeech.setPitch(1.0f); // Pitch 는 음성의 높낮이, 실수형이라 뒤에 f 붙여준다.
                textToSpeech.setSpeechRate(1.0f); // SpeechRate 는 음성의 속도
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 항상 findViewById 는 이 코드 이후에 사용.

        handAnimImageView = findViewById(R.id.hand_anim_image_view); // 이미지뷰 사용
        setHandImageView = findViewById(R.id.set_hand_image_view);

        gaweButton = findViewById(R.id.gawe_button);
        baweButton = findViewById(R.id.bawe_button);
        boButton = findViewById(R.id.bo_button);
        replayButton = findViewById(R.id.replay_button);

        animationDrawable = (AnimationDrawable) handAnimImageView.getDrawable(); // 형 변환

        textToSpeech = new TextToSpeech(getApplicationContext(),onInitListener); // 시작될 때 textToSpeech 등록

    }


    public void button_click(View view) {
        switch (view.getId()) {
            case R.id.replay_button: // Replay 버튼을 누르면 실행되는 코드들
                setHandImageView.setVisibility(View.GONE); // Gone 하면 해당 이미지뷰가 없어진다.
                handAnimImageView.setVisibility(View.VISIBLE); // Visible 하면 해당 이미지뷰뷰가보이게 된다.
                animationDrawable.start();
                voicePlay("가위바위보"); // Replay 버튼을 누르면 "가위바위보" 라는 음성이 들리게 하였다.

                // 예외처리
                replayButton.setEnabled(false);
                gaweButton.setEnabled(true);
                baweButton.setEnabled(true);
                boButton.setEnabled(true);

                break;

            // 가위,바위,보 버튼 이미지가 선택되면 아래 코딩이 실행된다.
            case R.id.gawe_button:
            case R.id.bawe_button:
            case R.id.bo_button:
                // 예외처리
                replayButton.setEnabled(true);
                gaweButton.setEnabled(false);
                baweButton.setEnabled(false);
                boButton.setEnabled(false);
                animationDrawable.stop();
                handAnimImageView.setVisibility(View.GONE);
                setHandImageView.setVisibility(View.VISIBLE);

                int userHand = Integer.parseInt(view.getTag().toString()); // 사용자 손
                int comHand = setComHand(); // 컴퓨터 손
                winCheck(userHand,comHand); // 유저와 컴퓨터 비교해서 승자 체크
                break;

            default:
                break;
                }
    }

    // TextToSpeech 는 사용이 끝나면 shutdown 을 해줘야 하기 때문에, 이곳곳서 textToSpeech 리소스 해제
    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.shutdown();
    }

    // 음성이 나오도록 하는 코딩 간결하게 메소드로 만들어 중복처리
    public void voicePlay(String voiceText){
        textToSpeech.speak(voiceText,TextToSpeech.QUEUE_FLUSH,null,null);
    }

    // 컴퓨터가 뽑은 랜덤의 수가 뭔지만 확인하는 메소드
    public int setComHand() {
        int getComHand = new Random().nextInt(3)+1; // (정수 1,2,3) 3개 중 랜덤한 값 나온다.
        // Random 메소드가 실수형이라 정수형으로 형변환.
        switch (getComHand){
            case 1: // 랜덤 값이 1 일때 가위
                setHandImageView.setImageResource(R.drawable.com_gawe);
                break;
            case 2: // 랜덤 값이 2 일때 바위
                setHandImageView.setImageResource(R.drawable.com_bawe);
                break;
            case 3: // 랜덤 값이 3 일때 보
                setHandImageView.setImageResource(R.drawable.com_bo);
                break;

        }
        return getComHand;
    }

//   가위바위보 알고리즘
//   result = (3+you-com) % 3;를 설명하자면...
//   가위 = 1, 바위 = 2, 보 = 3 일때 (3+you-com) % 3을 계산해서
//   0이 나오면 비긴 것이고(예 가위(1) - 가위(1) = 0),
//   1이 나오면 내가 이긴것이고(예 바위(2) - 가위(1) = 1),
//   2가 나오면 내가 진 것입니다(예 보(3) - 가위(1) = 2).
//   앞에 3+ 와 뒤에 %3이 붙는 이유는 두 값을 뺐을때 음수가 나올수 있기때문에,
//   +3 을 해서 음수 값이 안나오게 하고, %3을 해서 결과값이 0~2 사이로 나오게 한 것입니다.

    // 이겼는지 졌는지 비겼는지 체크
    public void winCheck (int userHand, int comHand){
            int result = (3 + userHand - comHand ) % 3;
            switch (result) {
                case 0: // 비겼습니다.
                    voicePlay("비겼습니다. 다시 시작하세요");
                    break;
                case 1: // 사용자가 이겼습니다.
                    voicePlay("당신이 이겼습니다. 축하합니다");
                    break;
                case 2: // 컴퓨터가 이겼습니다.
                    voicePlay("제가 이겼습니다. 메롱");
                    break;
            }
    }

}