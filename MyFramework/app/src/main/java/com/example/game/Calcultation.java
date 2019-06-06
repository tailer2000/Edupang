package com.example.game;

import android.view.ViewDebug;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.lang.Exception;

public class Calcultation {


    public static final int PLUS = 0;
    public static final int MINUS = -1;
    public static final int MULTI= -2;
    public static final int DIVIDE = -3;

    public ArrayList<Integer> formula = new ArrayList<Integer>();
    protected String str = "";
    protected String str_test = "";
    protected String str_anwser = "";

    public Calcultation() throws Exception {
        MakeAnwser();
    }

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

    public void MakeAnwser() throws Exception {
        Random random = new Random();
        while (true)
        {
            formula.clear();
            str = "";
            str_test = "";
            str_anwser = "";
            int num;

            for(int i=0; i<9; ++i) {
                //숫자 부분일 때
                if(i%2 == 0){
                    num = random.nextInt(6)+1;
                    formula.add(num);
                    str += String.valueOf(num);
                    str_test += String.valueOf(num);
                }
                else{ // 연산자 부분일 때
                    //일단 나누기 보류
                    num = random.nextInt(3);
                    switch (num){
                        case 0:
                            str += "+";
                            formula.add(PLUS);
                            break;
                        case 1:
                            str += "-";
                            formula.add(MINUS);
                            break;
                        case 2:
                            str += "*";
                            formula.add(MULTI);
                            break;
                        case 3:
                            str += "/";
                            formula.add(DIVIDE);
                            break;
                        default:
                            //에러 부분
                            str += "+";
                            break;
                    }
                    str_test += "   ";
                }
            }
            int anwser = Integer.parseInt(bracketCalMain(str));
            if(anwser > 0)
            {
                str_anwser = "=   " + anwser;
                return;
            }
        }
    }

    public String GetFormula()
    {
        return str;
    }

    public String GetTestString(){return str_test;}

    public String GetAnwserString() {return str_anwser;}

    public static String bracketCalMain(String sNumList) throws Exception{
        //0. ( ) 괄호 갯수 검증
        if (checkBracket(sNumList) == false) return "괄호 () 갯수가 Match되지 않습니다.";

        // 1. 1차 문자열 Parsing
        Queue firstQueue = new LinkedList();
        firstQueue = stringParsing(sNumList);
        System.out.println("----------------------------------------------");
        System.out.println("괄호 1차  => " + firstQueue.toString());

        // 2. 2차 괄호안 계산
        Queue secondQueue = new LinkedList();
        String oneChar;
        while(firstQueue.peek() != null){
            oneChar  = firstQueue.poll().toString();
            //System.out.println("괄호  => " + oneChar);

            if ("()".indexOf(oneChar) >= 0){
                //괄호안 계산 (firstQueue)
                secondQueue.offer(braketInCal(firstQueue));
            }else{
                secondQueue.offer(oneChar);
            }
        }

        //3. Queue에 저장 된 값 중  곱셈. 나눗셈 계산
        secondQueue = multiplyDivideCal(secondQueue);

        //4. Queue에 저장 된 값 중  덧셈. 뺄샘 계산
        String sResult = addSubtractCal(secondQueue);
        System.out.println("괄호 최종결과 => " + sResult);
        return sResult;
    }

    //( ) 괄호 갯수 검증
    public static boolean checkBracket(String sNumList){
        int count1=0, count2=0;
        for(int i=0; i<sNumList.length(); i++){
            if ("(".equals(sNumList.substring(i, i+1))){
                count1 ++;
            }else if (")".equals(sNumList.substring(i, i+1))){
                count2 ++;
            }
        }

        if (count1 == count2)
            return true;
        else{
            System.out.println("괄호 () 갯수가 Match되지 않습니다.");
            return false;
        }
    }

    //------------------------------------------------------------------------
    // 1차. 문자열 Parsing
    //------------------------------------------------------------------------
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Queue stringParsing(String sNumList){
        String sOneNum = "";  //하나의 숫자열
        Queue firstQueue = new LinkedList();
        for(int i=0; i<sNumList.length(); i++){
            String oneChar = sNumList.substring(i, i+1);
            if ("+-*/()".indexOf(oneChar) >= 0 ){

                // 3+(3  과 같이   4칙연산자와 괄호가 동시에 존재 하는 경우 때문
                if (!"".equals(sOneNum)) firstQueue.offer(sOneNum);
                firstQueue.offer(oneChar);
                sOneNum = "";
            }else{
                sOneNum += oneChar;

                if (i+1 == sNumList.length()){
                    firstQueue.offer(sOneNum);
                }
            }
        }
        return firstQueue;
    }

    //------------------------------------------------------------------------
    // 2차. 괄호 내부 계산 (재귀적 호출)
    //------------------------------------------------------------------------
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String braketInCal(Queue firstQueue) throws Exception{
        String oneChar;
        //1. 괄호 내부에 존재 하는 값을 Queue에 저장
        Queue secondQueue = new LinkedList();
        while(firstQueue.peek() != null){
            oneChar  = firstQueue.poll().toString();
            if ("(".equals(oneChar)){
                //괄호안 계산 (firstQueue)
                secondQueue.offer(braketInCal(firstQueue));
            }else if (")".equals(oneChar)){
                break; //닫기 괄호가 나오면 stop
            }else{
                secondQueue.offer(oneChar);
            }
        }
        System.out.println("괄호 2차  => " + secondQueue.toString());

        //2. Queue에 저장 된 값 중  곱셈. 나눗셈 계산
        secondQueue = multiplyDivideCal(secondQueue);

        //3. Queue에 저장 된 값 중  덧셈. 뺄샘 계산
        String sResult = addSubtractCal(secondQueue);

        return sResult;
    }


    //------------------------------------------------------------------------
    // 곱셈, 나눗셈 계산
    //------------------------------------------------------------------------
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Queue multiplyDivideCal(Queue inQueue) throws Exception{
        Queue outQueue = new LinkedList();
        String oneChar;
        String sNum1 = "", sNum2 = "", sResult = "";
        String operator = "";
        BigDecimal nResult = new BigDecimal("0");
        while(inQueue.peek() != null){
            oneChar  = inQueue.poll().toString();

            if ("+-".indexOf(oneChar) >= 0 ){
                outQueue.offer(sNum1);
                outQueue.offer(oneChar);
                sNum1 = "";
            }else if ("*/".indexOf(oneChar) >= 0 ) {
                operator = oneChar;
            }else{
                if ("".equals(sNum1)){
                    sNum1 = oneChar;
                }else if ("".equals(sNum2)) {
                    sNum2 = oneChar;
                    if ("*".equals(operator)) {
                        nResult = (new BigDecimal(sNum1)).multiply(new BigDecimal(sNum2));
                    }else if ("/".equals(operator)) {
                        if ("0".equals(sNum2)){
                            throw new Exception("나눗셈 분모에 0 이 존재 합니다.");
                        }

                        nResult = (new BigDecimal(sNum1)).divide(new BigDecimal(sNum2), 6, BigDecimal.ROUND_UP);
                    }
                    sResult  = String.valueOf(nResult);


                    sNum1    = sResult;
                    sNum2    = "";
                    operator = "";
                }
            }

            if (inQueue.peek() == null) outQueue.offer(sNum1);
        }
        return outQueue;
    }


    //------------------------------------------------------------------------
    // 덧셈. 뺄샘 계산
    //------------------------------------------------------------------------
    @SuppressWarnings({ "rawtypes" })
    public static String addSubtractCal(Queue inQueue){
        String operator = "";
        String oneChar  = "";
        BigDecimal nResult  = new BigDecimal(inQueue.poll().toString());
        while(inQueue.peek() != null){
            oneChar  = inQueue.poll().toString();

            if ("+-".indexOf(oneChar) >= 0 ){
                operator = oneChar;
            }else{
                if ("+".equals(operator)){
                    nResult = nResult.add(new BigDecimal(oneChar));
                }else if ("-".equals(operator)){
                    nResult = nResult.subtract(new BigDecimal(oneChar));
                }
                operator = "";
            }
        }
        return nResult.toString();
    }

}
