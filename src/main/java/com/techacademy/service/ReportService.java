package com.techacademy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    // 日報一覧表示処理
    public List<Report> findAll() {
            return reportRepository.findAll();
        }

    // 1件を検索
    public Report findByCode(String employee_code) {
        // findByIdで検索
        Optional<Report> option = reportRepository.findById(employee_code);
        // 取得できなかった場合はnullを返す
        Report report = option.orElse(null);
        return report;
    }

    // ログイン中のユーザー情報を検索
    public Report findByEmployee(Employee employee) {
        // 検索
        List<Report> option = reportRepository.findByEmployee(employee);
        Report report = list.orElse(null);
        return report;
    }
}
