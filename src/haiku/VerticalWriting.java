package haiku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

/**
 * 書字方向が横書の文章を縦書に変換する
 */
@Setter @Getter
public class VerticalWriting {

    // 改行コード
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    // 全角スペース
    private static String FULL_WIDTH_SPACE = "　";
    // 書字方向（列挙）
    private static enum writtenFormDirection {LTR, RTL}
    // 列セパレータ
    private String columnSeparator = "　";
    // 文字セパレータ
    private String charSeparator = "";
    // 書字方向
    private writtenFormDirection wfd = writtenFormDirection.RTL;
    
    /**
     * 文章を助詞で改行し、縦書にする
     * @param sentence
     * @return 
     */
    public String toVerticalWriting(String sentence) {
        return formatSentence(
                punctual(sentence));
    }

    /**
     * 文章を助詞またはスペースで分割する
     * @param sentence
     * @return 
     */
    private List<String> punctual(String sentence) {
        if (sentence.contains(FULL_WIDTH_SPACE)) {
            return punctualByFullWidthSpace(sentence);
        } else {
            return punctualByParticle(sentence);
        }
    }

    /**
     * 文章を助詞で区切ってリスト化する
     * @param sentence
     * @return 
     */
    private List<String> punctualByParticle(String sentence) {
        // 文章を解析する
        Tokenizer tokenizer = Tokenizer.builder().build();
        List<Token> tokens = tokenizer.tokenize(sentence);        
        
        // 行リスト
        List<String> lines = new ArrayList<>();
        
        // 助詞ごとに文章を区切り、行リストに追加していく
        while (tokens.size() > 0) {
            int particlePosition = indexOfParticle(tokens);
            if (particlePosition != tokens.size()) {
                lines.add(join(
                        new ArrayList<>(
                            tokens.subList(0, particlePosition+1))));
                tokens = new ArrayList<>(tokens.subList(particlePosition+1,tokens.size()));
            } else {
                lines.add(join(
                        new ArrayList<>(
                            tokens.subList(0, particlePosition))));
                tokens = new ArrayList<>();
            }
        }
        return lines;
    }
    
    /**
     * 文章を全角スペースで区切ってリスト化する
     * @param sentence
     * @return 
     */
    private List<String> punctualByFullWidthSpace(String sentence) {
        return Arrays.asList(sentence.split(FULL_WIDTH_SPACE));
    }

    /**
     * 助詞の最初の出現位置を返す
     * @param tokens
     * @return 助詞の最初の出現位置
     */
    private int indexOfParticle(List<Token> tokens) {
        // 助詞を見つけるまで繰り返す
        for (int position = 0; position < tokens.size(); ) {
            if (tokens.get(position)
                    .getPartOfSpeech().contains("助詞")) {
                return position;
            }
            position++;
        } 
        // 助詞が無い場合は最後の位置を返す
        return tokens.size();
    }
    
    /**
     * 引数のTokenの文字列をすべて連結する
     * @param tokens
     * @return 
     */
    private String join(List<Token> tokens) {
        StringBuilder sb = new StringBuilder();
        tokens.forEach(t -> sb.append(t.getSurfaceForm()));
        return sb.toString();
    }

    /**
     * 縦書に整形する
     * @param lines
     * @return 縦書の文章
     */
    private String formatSentence(List<String> lines) {
        // 書字方向が右→左であればListの順を入れ替える
        if (wfd == writtenFormDirection.RTL) {
            Collections.reverse(lines);
        }

        // 各行のn文字目を抜き出して連結していく
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < maxLength(lines); i++) {
            for (String line : lines) {
                try {
                    sb.append(line.substring(i, i+1));
                } catch (StringIndexOutOfBoundsException e) {
                    sb.append("　");
                }
                sb.append(columnSeparator);
            }
            sb.append(LINE_SEPARATOR).append(charSeparator);
        }
        return sb.toString();
    }

    /**
     * 引数のリストの要素のうち、最大文字数を返す
     * @param lines
     * @return 最大文字数
     */
    private int maxLength(List<String> lines) {
        return lines.stream().max(
                (a, b) -> a.length() - b.length())
                .get().length();
    }

}