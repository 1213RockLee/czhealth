package com.service.health.service;

import java.util.ArrayList;
import java.util.Map;

public interface ReportService {
    Map getMemberReport(ArrayList<String> monthList);

    Map findSetmeal();

    Map<String,Object> getBusinessReportData();

}
