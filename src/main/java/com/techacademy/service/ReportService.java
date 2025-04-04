package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
//    private final PasswordEncoder passwordEncoder;

    public ReportService(ReportRepository reportRepository, PasswordEncoder passwordEncoder) {
        this.reportRepository = reportRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    // 日報一覧表示処理（管理者権限の場合）
    public List<Report> findAll() {
            return reportRepository.findAll();
        }

    // 日報一覧表示処理（一般権限の場合）
    public List<Report> findByEmployee(Employee employee) {
        // 検索
        return reportRepository.findByEmployee(employee);
    }

//  // 1件を検索
//  public Report findByCode(String employee_code) {
//      // findByIdで検索
//      Optional<Report> option = reportRepository.findById(employee_code);
//      // 取得できなかった場合はnullを返す
//      Report report = option.orElse(null);
//      return report;
//  }

    // 日報保存
    @Transactional
    public ErrorKinds save(@AuthenticationPrincipal UserDetail userDetail,Report report) {

        // ログイン中の従業員かつ入力した日付のデータが既にあるかどうか（後で作る）
//        if (findByCode(employee.getCode()) != null) {
//            return ErrorKinds.DUPLICATE_ERROR;
//        }
        System.out.println(report.getReportDate());
        System.out.println(report.getTitle());
        System.out.println(report.getContent());
//        System.out.println(report.getEmployee().getCode());

        // ログイン中のユーザーの社員番号を取る
        System.out.println(userDetail.getEmployee().getCode());
        report.setDeleteFlg(false);

        LocalDateTime now = LocalDateTime.now();
        // System.out.println(now);
        report.setUpdatedAt(now);
        report.setUpdatedAt(now);

        reportRepository.save(report);
        return ErrorKinds.SUCCESS;
    }

}
