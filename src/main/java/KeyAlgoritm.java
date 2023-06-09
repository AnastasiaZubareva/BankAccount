import java.util.Stack;

public class KeyAlgoritm {


    public static Boolean isKeyValid(String scanNumber, String bik) {
        return calculateKey(
                bik.substring(6) + scanNumber)
                % 10 == 0;
    }

    static int[] q = {7,1,3};

    private static int calculateKey(String bikAndNumber) {
        int key = 0;
        for (int i = 0; i < bikAndNumber.length(); i++) {
            key += Integer.parseInt(String.valueOf(bikAndNumber.charAt(i))) * q[i%q.length];
        }
        return key;
    }

    public static String getValidAccountNumber(String scanNumber, String bik) {
        int resultKey = calculateAccountNumber(bik.substring(6) + scanNumber);
        int keyNumber = (resultKey % 10 * 3) % 10;
        return scanNumber.substring(0, 8) + keyNumber + scanNumber.substring(9);
    }

    private static int calculateAccountNumber(String bikAndNumber) {
        int key = 0;
        for (int i = 0; i < bikAndNumber.length(); i++) {
            if (i == 11) {
                continue;
            }
            key += Integer.parseInt(String.valueOf(bikAndNumber.charAt(i))) * q[i%q.length];
        }
        return key;
    }



    class Solution {
        public String longestCommonPrefix(String[] strs) {
            String prefStr=strs[0];
            String returnStr="";
            for (int i=1; i<strs.length; i++){
                for (int j=0;j<prefStr.length();j++){
                    if (strs[i].contains(prefStr.substring(0,j))) returnStr=prefStr.substring(0,j);
                    else break;
                }

            } return returnStr;
        }
    }

    }