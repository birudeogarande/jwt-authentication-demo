package com.saatvik.app.exception;


import org.springframework.stereotype.Component;

import static java.lang.StringTemplate.STR;

@Component
public class JavaEnhancedSwitch {
    public  String getItemNameByCode(int itemCode) throws InvalidItemCode {
        return switch (itemCode) {
            case 1,4,5 :
                yield "It's a laptop!";
            case 2 :
                yield "It's a desktop!";
            case 3 :
                yield "It's a mobile phone!";
                 default :
                    throw new InvalidItemCode(STR."\{itemCode} is an unknown device!");
        };
    }

    /**
 * Switch with arrows
 * */
    public  String getDay(int day) throws InvalidDayException {
        return switch (day) {
            case 1,7 -> "Weekend";
            case 2,3,4,5,6 ->"Weekday";
            default -> throw new InvalidDayException(STR."Day should be between 1-7, incorrect value: \{day}");
        };
    }
}
