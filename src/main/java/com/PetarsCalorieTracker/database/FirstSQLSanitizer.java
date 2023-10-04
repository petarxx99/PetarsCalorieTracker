package com.PetarsCalorieTracker.database;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FirstSQLSanitizer implements SQLSanitizer{

    @Override
    public String sanitize(@NonNull String sql){
        char[] charArray = sql.toCharArray();
        var result = new ArrayList<Character>();

        for (int i=0; i<charArray.length; i++){
            Character nextCharacter = (Character) charArray[i];
            switch (nextCharacter){
                case '\\': {
                    result.add('\\');
                    result.add('\\');
                    break;
                }
                case '\'': {
                    result.add('\\');
                    result.add('\'');
                    break;
                }
                case '"': {
                    result.add('\\');
                    result.add('"');
                    break;
                }
                default: {
                    result.add(nextCharacter);
                }
            }
        }
        int resultSize = result.size();
        char[] resultCharArray = new char[resultSize];
        for (int i=0; i<resultSize; i++){
            resultCharArray[i] = result.get(i);
        }

        return new String(resultCharArray);

       /* return sql.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\""); */
    }
}
