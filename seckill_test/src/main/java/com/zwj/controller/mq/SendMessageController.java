package com.zwj.controller.mq;

import com.zwj.service.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rabbit")
@RestController
public class SendMessageController {

    @Autowired
    private RabbitmqService rabbitmqService;

    @GetMapping("/sendMassage")
    public boolean sendMassage() {
       return rabbitmqService.sendDirectMessage();
    }
}
