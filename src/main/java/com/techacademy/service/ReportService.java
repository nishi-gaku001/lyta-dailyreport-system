package com.techacademy.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

  // 1件を検索_日報の日付
  public Report findByEmployeeAndReportDate(Employee employee,LocalDate reportDate) {
      // findByIdで検索
      Report report = reportRepository.findByEmployeeAndReportDate(employee,reportDate);
      // 取得できなかった場合はnullを返す
      return report;
  }

    // 日報を保存
    @Transactional
    public ErrorKinds save(@AuthenticationPrincipal UserDetail userDetail,Report report) {

        // ログイン中の従業員かつ入力した日付のデータが既にあるかどうか
        if (findByEmployeeAndReportDate(userDetail.getEmployee(),report.getReportDate()) != null){
            return ErrorKinds.DATECHECK_ERROR;
        }

//        System.out.println(report.getReportDate());
//        System.out.println(report.getTitle());
//        System.out.println(report.getContent());
//        System.out.println(report.getEmployee().getCode());

        // ログイン中のユーザーの社員番号を取る
//        System.out.println(userDetail.getEmployee().getCode());
        report.setEmployee(userDetail.getEmployee());

        report.setDeleteFlg(false);

        LocalDateTime now = LocalDateTime.now();
        // System.out.println(now);
        report.setCreatedAt(now);
        report.setUpdatedAt(now);

        reportRepository.save(report);
        return ErrorKinds.SUCCESS;
    }

    // 1件を検索_日報詳細
    public Report findById(String id) {
        // findByIdで検索
        Optional<Report> option = reportRepository.findById(id);
        // 取得できなかった場合はnullを返す
        Report report = option.orElse(null);
        return report;
    }

    // 日報削除
    @Transactional
    public ErrorKinds delete(String id) {

        System.out.println(id);

        Report report = findById(id);
        LocalDateTime now = LocalDateTime.now();
        report.setUpdatedAt(now);
        report.setDeleteFlg(true);

        return ErrorKinds.SUCCESS;
    }

    // 従業員更新
    @Transactional
    public ErrorKinds update(@AuthenticationPrincipal UserDetail userDetail,Report report) {

        // ログイン中の従業員かつ入力した日付のデータが既にあるかどうか
        if (findByEmployeeAndReportDate(userDetail.getEmployee(),report.getReportDate()) != null){
            return ErrorKinds.DATECHECK_ERROR;
        }

        // データ型変換すればOKか？ idはintだけど、getIdはStringなので
        Report reportUpdate = findById(String.valueOf(report.getId()));

        reportUpdate.setReportDate(report.getReportDate());

        reportUpdate.setTitle(report.getTitle());

        reportUpdate.setContent(report.getContent());

        LocalDateTime now = LocalDateTime.now();
        reportUpdate.setUpdatedAt(now);

        reportRepository.save(reportUpdate);
        return ErrorKinds.SUCCESS;
    }

    // 1件を検索_その日報の登録日
    public Report findByEmployeeAndCreatedAt(Employee employee,LocalDate CreatedAt) {
        // findByIdで検索
        Report report = reportRepository.findByEmployeeAndReportDate(employee,CreatedAt);
        // 取得できなかった場合はnullを返す
        return report;
    }

}
