package com.example.game;

import android.view.ViewDebug;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Calcultation {

    public ArrayList<Integer> formula = new ArrayList<Integer>();

    public int GetAnwser(String s) {

        //LinkedList<Integer> numList = new LinkedList<Integer>(); //숫자관련
        LinkedList<Integer> numList = new LinkedList<Integer>(); //숫자관련
        LinkedList<Character> opList = new LinkedList<Character>(); //연산자 관련

        //Scanner sc = new Scanner(System.in);

        //String s = sc.nextLine(); //enter까지 포함한 것까지 입력

        String num = ""; //연사자 외에 숫자들을 임시 저장할 곳

        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i); //string을 char 타입 단위로 나눔

            if(ch == '+' || ch =='-' || ch == '*' || ch == '/') {
                numList.add(Integer.parseInt(num)); //숫자로 바꿔서 숫자배열에 추가
                opList.add(ch); //연산자를 연산자배열에 추가
                num = ""; //임시로 저장된 숫자를 비워준다
                continue; //제일 가까운 명령문으로 이동
            }
            num += ch; //연산자 앞까지의 숫자를 임시로 넣어 놓음
        }
        numList.add(Integer.parseInt(num)); //마지막 숫자

        while(!opList.isEmpty()) { //연산자배열이 빌 때까지
            int prevNum = numList.poll(); //poll : 앞부터 완전히 뺀다
            int nextNum = numList.poll();
            char op = opList.poll();

            if(op == '+') {
                numList.addFirst(prevNum + nextNum); //addFirst 배열 제일 앞에 넣는다
            } else if(op == '-') {
                numList.addFirst(prevNum - nextNum);
            } else if(op == '*') {
                numList.addFirst(prevNum * nextNum);
            } else if(op == '/') {
                numList.addFirst(prevNum / nextNum);
            }
        }
        //System.out.println(numList.poll());
        return numList.poll();
    }

    public void MakeAnwser()
    {
        Random random = new Random();
        while (true)
        {
            String str = "";
            formula.clear();
            int num;

            for(int i=0; i<9; ++i) {
                //숫자 부분일 때
                if(i%2 == 0){
                    num = random.nextInt(6)+1;
                    formula.add(num);
                    str += String.valueOf(num);
                }
                else{ // 연산자 부분일 때
                    num = random.nextInt(4);
                    switch (num){
                        case 0:
                            str += "+";
                            break;
                        case 1:
                            str += "-";
                            break;
                        case 2:
                            str += "*";
                            break;
                        case 3:
                            str += "/";
                            break;
                        default:
                            //에러 부분
                            str += "+";
                            break;
                    }
                }
            }
            int anwser = GetAnwser(str);
            if(anwser > 0)
                return;
        }
    }

}
