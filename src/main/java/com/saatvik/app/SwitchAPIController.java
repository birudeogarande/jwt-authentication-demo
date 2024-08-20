package com.saatvik.app;


import com.saatvik.app.exception.InvalidItemCode;
import com.saatvik.app.exception.JavaEnhancedSwitch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/switch")
public class SwitchAPIController {

    private final JavaEnhancedSwitch aSwitch;
    public SwitchAPIController(JavaEnhancedSwitch aSwitch) {
        this.aSwitch = aSwitch;
    }

    @GetMapping("/getName/{itemCode}")
    String getItemNameByCode(@PathVariable int itemCode) throws InvalidItemCode {
        return aSwitch.getItemNameByCode(itemCode);
    }

}
