package com.techacademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.constants.ErrorMessage;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
@RequestMapping("reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // 日報一覧画面を表示
    @GetMapping
    public String list(@AuthenticationPrincipal UserDetail userDetail,Model model) {

        // ログインユーザーの権限を取得し、判定して処理を分岐
        //　管理者権限の場合、全件表示
        if(Employee.Role.ADMIN.equals(userDetail.getEmployee().getRole())) {
//            System.out.println(userDetail.getEmployee().getRole());
            model.addAttribute("listSize", reportService.findAll().size());
            model.addAttribute("reportList", reportService.findAll());
        // 一般の場合、ログイン中のユーザーの情報のみ表示
        }else {
            // System.out.println(userDetail.getEmployee().getRole());
            model.addAttribute("listSize", reportService.findByEmployee(userDetail.getEmployee()).size());
            model.addAttribute("reportList", reportService.findByEmployee(userDetail.getEmployee()));
        }

        return "reports/list";
    }

    // 日報新規登録画面を表示
    @GetMapping(value = "/add")
    public String create(@AuthenticationPrincipal UserDetail userDetail,Model model, @ModelAttribute Report report) {
            // ここでユーザーの情報を取得して渡す？
            // 社員名
            model.addAttribute("name", userDetail.getEmployee().getName());
            // 社員番号
//            model.addAttribute("employee_code", userDetail.getEmployee().getCode());

        return "reports/new";
    }

    // 従業員新規登録処理
    @PostMapping(value = "/add")
    public String add(@AuthenticationPrincipal UserDetail userDetail,@Validated Report report, BindingResult res, Model model) {
        // System.out.println(model);

//        // 入力チェック
//        if (res.hasErrors()) {
//            return create(null, model, report);
//        }

        // 日報を保存
//        ErrorKinds result = reportService.save(report);
        reportService.save(userDetail,report);

//        if (ErrorMessage.contains(result)) {
//            model.addAttribute(ErrorMessage.getErrorName(result), ErrorMessage.getErrorValue(result));
//            return create(null, model, report);
//        }

        return "redirect:/reports";
    }
}
