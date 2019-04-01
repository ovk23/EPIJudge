package epi.string;

import java.util.*;
import java.util.stream.*;
import java.util.Collections.*;

public class Paranthesis {
    static void _printParanthesis(char str[], int pos, int n, int open, int close) {
        if(close == n) {
            for(char c: str) 
                System.out.println(c);
            return;
        } else {
            if (open > close){
                str[pos] = '}';
                _printParanthesis(str, pos + 1, n, open, close + 1);
            }

            if (open < n) {
                str[pop] = '{';
                _printParanthesis(str, pos + 1, n, open + 1, close);
            }
        }
    }

    static void printParanthesis(char str[], int n){
        if(n > 0) _printParanthesis(str, 0, n, 0, 0);
        return;
    }

    public static void main(String[] args){
        int n = 3;
        char[] str = new char[n * 2];
        printParanthesis(str, n);
    }
}