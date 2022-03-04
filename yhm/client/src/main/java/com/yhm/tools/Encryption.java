package com.yhm.tools;

public class Encryption {
    public String encryptionPsw(String psw){
        String psw2="";
        for(int i=0;i<psw.length();i++){
            char s=psw.charAt(i);
            if (s>64&&s<91){
                s=(char)(s+4);
                if (s>90){
                    s=(char) (s-26);
                }
                psw2=psw2+s;
            }else if (s>96&&s<123){
                s=(char)(s+6);
                if (s>122){
                    s=(char) (s-26);
                }
                psw2=psw2+s;
            }else {
                psw2=psw2+s;
            }
        }
        return psw2;
    }
}
