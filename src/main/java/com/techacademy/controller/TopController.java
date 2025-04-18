
package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopController {

    // ログイン画面表示
    @GetMapping(value = "/login")
    public String login() {
        return "login/login";
    }

    // ログイン後のトップページ表示
    @GetMapping(value = "/")
    public String top() {
        // 元は従業員画面に移動する仕様
//        return "redirect:/employees";

        // 日報一覧に変更
        return "redirect:/reports";
    }

}
