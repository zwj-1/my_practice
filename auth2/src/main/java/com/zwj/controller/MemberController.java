package com.zwj.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 余胜军
 * @ClassName MemberService
 * @qq 644064779
 * @addres www.mayikt.com
 * 微信:yushengjun644
 */
@RequestMapping("/zwj")
@RestController
public class MemberController {
    /**
     * 增加用户
     *
     * @return
     */
    @PostMapping("/addMember")
    public String addMember() {
        return "addMember";
    }

    /**
     * 删除用户
     *
     * @return
     */
    @PostMapping("/delMember")
    public String delMember() {
        return "delMember";
    }

    /**
     * updateMember
     *
     * @return
     */
    @PostMapping("/updateMember")
    public String updateMember() {
        return "updateMember";
    }

    /**
     * showMember
     *
     * @return
     */
    @GetMapping("/showMember")
    public String showMember() {
        return "带token查询showMember成功!";
    }

}
