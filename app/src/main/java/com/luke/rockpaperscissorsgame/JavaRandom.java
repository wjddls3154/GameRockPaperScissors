package com.luke.rockpaperscissorsgame;

import java.util.Random;

public class JavaRandom {
    public static void main(String[] args) {

        // Math.random 을 사용해서, 랜덤한 숫자를 발생시키는 방법
        double randomDouble = Math.random(); // Math.random 은 실수형이라, 실수형 변수 선언
        System.out.println("randomDouble = " + randomDouble);
        int randomInt = (int) (randomDouble * 3) + 1; // 먼저 계산을 해주고, 실수형을 정수형으로 형변환 해준다.

        // 아래 두 가지 출력은 똑같지만, 다르게 표현한 방법
        System.out.println("randomInt = " + randomInt);
        System.out.println("randomInt2" + ((int) (Math.random() * 3) + 1));

        // 랜덤 클래스를 가지고, 랜덤 숫자를 발생시킬수도 있다.
        int randomClass = new Random().nextInt(3)+1; // +1을 해줘서 0,1,2 가 아닌 (1,2,3) 3가지 랜덤 숫자를 발생한다
        System.out.println("randomClass = " + randomClass);

    }
}
