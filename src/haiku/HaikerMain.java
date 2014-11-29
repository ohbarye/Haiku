/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package haiku;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Token;

/**
 *
 * @author ohbamasato
 */
public class HaikerMain {
    
    public static void main (String args[]) {
            
        String sentence = "エボラ熱かもしれないから射殺するたぬ";
        VerticalWriting vw = new VerticalWriting();
        System.out.println(vw.toVerticalWriting(sentence));
        
        
//        Haiku haiku = formatSentenceInHaiku(sentence);

//        System.out.println(haiku.toString());

//        Tokenizer tokenizer = Tokenizer.builder().build();
//        List<Token> tokens = tokenizer.tokenize(sentence);
//        
//        for (Token token : tokens) {
//            
//            
//            System.out.println("==================================================");
//            System.out.println("allFeatures : " + token.getAllFeatures());
//            System.out.println("partOfSpeech : " + token.getPartOfSpeech());
//            System.out.println("position : " + token.getPosition());
//            System.out.println("reading : " + token.getReading());
//            System.out.println("surfaceFrom : " + token.getSurfaceForm());
//            System.out.println("辞書にある言葉? : " + token.isKnown());
//            System.out.println("未知語? : " + token.isUnknown());
//            System.out.println("ユーザ定義? : " + token.isUser());
//        }
  
    }
    
}
