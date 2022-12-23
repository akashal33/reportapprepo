package com.akashk.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.akashk.binding.Customer;
import com.akashk.binding.RequestReport;

@Service
public interface IReportService {
	
	public List<String> getPlanTypes();
	public List<String> getPlanStatus();
	public List<Customer> searchCustomer(RequestReport requestReport);
	public void getPdfRepot(HttpServletResponse response, RequestReport form);
	public void getExcelReport(HttpServletResponse response,RequestReport form) throws IOException;

}
