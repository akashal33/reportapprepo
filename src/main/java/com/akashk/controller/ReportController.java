package com.akashk.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akashk.binding.Customer;
import com.akashk.binding.RequestReport;
import com.akashk.service.IReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/report")
@CrossOrigin

@Api("This is report controller")
public class ReportController {

	@Autowired
	private IReportService reportService;
	
	@ApiOperation(" this methos used for getting dynamic plan values from table")
	@GetMapping("/plans")
	public List<String> getPlantypes() {
		return reportService.getPlanTypes();
	}
	
	@ApiOperation(" this methos used for getting dynamic plan status values from table")
	@GetMapping("/status")
	public List<String> getStatus() {
		return reportService.getPlanStatus();
	}

	@ApiOperation(" this methos used for serching customers report")
	@PostMapping("/generate")
	public List<Customer> getCustomers(@RequestBody RequestReport reqReport) {
		
		//System.out.println(reqReport);
		return reportService.searchCustomer(reqReport);
	}
	
	@ApiOperation(" this methos used for exporting report to pdf ")
	@PostMapping("/exportpdf")
	public void getPdf(HttpServletResponse response, @RequestBody RequestReport reqReport) {
	
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=report.pdf";
		
		response.setHeader(headerKey, headerValue);
		
		reportService.getPdfRepot(response, reqReport);

		
	}
	
	@ApiOperation(" this methos used for exporting report to excel")
	@PostMapping("/exportexcel")
	public void getExcel(HttpServletResponse response, @RequestBody RequestReport reqReport) throws IOException {
		
		//System.out.println(reqReport);
		
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=report.xls";
		
		response.setHeader(headerKey, headerValue);
		
		reportService.getExcelReport(response, reqReport);
		
	}

}
