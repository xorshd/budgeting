package com.dcinspirations.homepage;

public class General {
   public static String editAmount(String amount){
        String[] amArr = amount.split("");
        String newAmount="";
        int c = 0;
        if(amArr.length>4){
            for(int i=amArr.length-1;i>=0;i--){
                c+=1;
                if(c%3==0){
                    if(i==0){
                        newAmount=amArr[i]+newAmount;
                    }else{
                        newAmount=","+amArr[i]+newAmount;
                    }

                }else{
                    newAmount = amArr[i]+newAmount;
                }
            }
        }else{
            newAmount=amount;
        }

        return newAmount;
    }
}
