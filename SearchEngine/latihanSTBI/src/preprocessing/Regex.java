/*
 * Contoh Regex.
 */
package preprocessing;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adit
 */
public class Regex {
    
    public static boolean [] isToken(String [] tokens){
        
        String integerExp[] = {"^(\\+|-)?\\d+$"};
        String dateExp[] = {"^(?:\\d{4}|\\d{1,2})[-/.](?:\\d{1,2}|J(anuary|an|AN|une|un|UN|uly|ul|UL)|F(ebruary|eb|EB)|M(arch|ar|AR|ay|AY)|A(pril|pr|PR|ugust|ug|UG)|S(eptember|ep|EP)|O(ctober|ct|CT)|N(ovember|ov|OV)|D(ecember|ec|EC))[-/.](?:\\d{4}|\\d{1,2})$"};
        String timeExp[] = {"^([aApP][mM])?(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?([aApP][mM])?$"};
        String urlExp[] = {"^(?i)((mailto\\:|(news|(ht|f)tp(s?))\\://){1}\\S+)?$", "^[a-zA-Z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\s*)?$", "^(http(s{0,1})://|www\\.)[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*$"};
        String emailExp[] = {"^[\\w-]+(\\.[\\w-]+|\\.)*@[\\w-]+(\\.[\\w-]+)+$"};
        String currencyExp[] = {"^(\\+|-)?(\\$|£)\\d+(\\.\\d*)?$"};
        String percentageExp[] = {"^(\\+|-)?\\d+(\\.\\d*)?%$"};
        String decimalExp[] = {"^(\\+|-)?\\d+\\.\\d+$"};

        Pattern SubContentPattern;
        Matcher matcher;
        
        boolean [] arrIsToken = new boolean [tokens.length];
        
        for (int i = 0; i < arrIsToken.length; i++) {
            arrIsToken[i] = false;
            String token = tokens[i];
        
            SubContentPattern = Pattern.compile(percentageExp[0]);
            matcher = SubContentPattern.matcher(token);
            if (matcher.find()) {
                arrIsToken[i] = true; 
            } else {
                SubContentPattern = Pattern.compile(integerExp[0]);
                matcher = SubContentPattern.matcher(token);
                if (matcher.find()) {
                    arrIsToken[i] = true; 
                } else {
                    SubContentPattern = Pattern.compile(dateExp[0]);
                    matcher = SubContentPattern.matcher(token);
                    if (matcher.find()) {
                        arrIsToken[i] = true; 
                        //System.out.println("Date : ");
                    } else {
                        SubContentPattern = Pattern.compile(timeExp[0]);
                        matcher = SubContentPattern.matcher(token);
                        if (matcher.find()) {
                            arrIsToken[i] = true; 
                        } else {
                            SubContentPattern = Pattern.compile(urlExp[0]);
                            matcher = SubContentPattern.matcher(token);
                            if (matcher.find()) {
                                arrIsToken[i] = true; 
                            } else {
                                SubContentPattern = Pattern.compile(emailExp[0]);
                                matcher = SubContentPattern.matcher(token);
                                if (matcher.find()) {
                                    arrIsToken[i] = true; 
                                } else {
                                    SubContentPattern = Pattern.compile(currencyExp[0]);
                                    matcher = SubContentPattern.matcher(token);
                                    if (matcher.find()) {
                                        arrIsToken[i] = true; 
                                    } else {
                                        SubContentPattern = Pattern.compile(decimalExp[0]);
                                        matcher = SubContentPattern.matcher(token);
                                        if (matcher.find()) {
                                            arrIsToken[i] = true; 
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            /*
            if (arrIsToken[i]){
                System.out.println("Regex : " + tokens[i]);
            }
            */
        }

        return arrIsToken;
    }
}
