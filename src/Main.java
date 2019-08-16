import com.sun.xml.internal.fastinfoset.util.CharArray;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
//        System.out.println(main.lengthOfLongestSubstring("pwwkew"));
        System.out.println(main.reverse(-123));
    }

    public int reverse(int x) {
        int value = 0;
        if(x > 0){
            value = x;
        }else{
            value = -1*x;
        }
        int size = 0;
        while(value>0){
            size++;
            value /= 10;
        }
        value = x>0?x:(-1*x);
        int[] array = new int[size];

        int count=0;
        while(value>0){
            array[count++]=value%10;
            value /= 10;
        }

        int sum = 0;
        int i = 0;
        while(count > 0){
            sum += (array[--count]*Math.pow(10,i++));
        }
        if(sum >= Integer.MAX_VALUE){
            return 0;
        }

        return x>0?sum:(-1*sum);
    }


    /**
     * 返回重复最长字串
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        String sub ;
        char[] chars = s.toCharArray();
        int length = 0;
        for (int i = 0;i<chars.length;i++){
            sub = "";
            sub += chars[i];
            for (int j = i+1;j<chars.length;j++){
                if(sub.contains(""+chars[j])){
                    break;
                }
                sub += chars[j];
            }
            if(sub.length() > length){
                length = sub.length();
            }
        }
        return length;
    }

    /**
     * 最长回文字符串
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {

        String longestPalin = "";
        String sub = "";
        char[] chars = s.toCharArray();

        for(int i = 0;i<chars.length;i++){
            for (int j=i;j<chars.length;j++){
                if(chars[j] != chars[i]){
                    continue;
                }
                sub = String.copyValueOf(chars,i,j-i+1);
                if(isPalin(sub)){
                    if(sub.length()>longestPalin.length()){
                        longestPalin = sub;
                    }
                }
            }


        }
        return longestPalin;
    }

    public boolean isPalin(String s){
        char[] chars = s.toCharArray();
        for (int i=0,j=chars.length-1;i<j;i++,j--){
            if (chars[i] != chars[j]){
                return false;
            }
        }
        return true;
    }

    /**
     * 之子字符串转换
     * @param s
     * @param numRows
     * @return
     */
    public String convert(String s, int numRows) {

        String res = "";

        if(numRows == 1){
            return s;
        }else{
            int span = numRows + 1;
            int count= 0;

            int size = 0;
            if(s.length()%span==0){
                size = s.length()/span;
            }else{
                size = s.length()/span+1;
            }

            String[] ss = new String[size];
            for(int i = 0;i<s.length();i+=span){
                if(i+span > s.length()){
                    ss[count] = s.substring(i+1,s.length());
                }else{
                    ss[count] = s.substring(i+1,i+span);
                }
                count++;
                res += s.charAt(i);
            }



            if(ss.length>0){
                for(String str:ss){
                    res += convert(str,numRows--);
                }
            }

        }

        return res;

    }
}
