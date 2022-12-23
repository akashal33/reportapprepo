package com.akashk.service;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.akashk.binding.Customer;
import com.akashk.binding.RequestReport;
import com.akashk.repository.ReportRepository;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ReportServiceImpl implements IReportService {

	@Autowired
	ReportRepository reportRepo;

	@Override
	public List<String> getPlanTypes() {

		return reportRepo.getDistinctByPlanType();
	}

	@Override
	public List<String> getPlanStatus() {

		return reportRepo.getDistinctPlanStatus();
	}

	@Override
	public List<Customer> searchCustomer(RequestReport form) {

		List<Customer> customers = null;

		/*
		 * if ( (form.getPlanType() != null) && form.getPlanStatus() != null &&
		 * form.getStartDate() != null && form.getEnddate() != null) {
		 * 
		 * customers =
		 * reportRepo.findByPlanTypeAndPlanStatusAndStartDateBetween(form.getPlanType(),
		 * form.getPlanStatus(), form.getStartDate(), form.getEnddate());
		 * 
		 * } else if (form.getPlanType() != null && form.getPlanStatus() != null &&
		 * form.getStartDate() != null && form.getEnddate() == null) {
		 * 
		 * customers =
		 * reportRepo.findByPlanTypeAndPlanStatusAndStartDate(form.getPlanType(),
		 * form.getPlanStatus(), form.getStartDate());
		 * 
		 * } else if (form.getPlanStatus() != null && form.getPlanType() != null &&
		 * form.getStartDate() == null && form.getEnddate() == null) {
		 * 
		 * customers = reportRepo.findByPlanTypeAndPlanStatus(form.getPlanType(),
		 * form.getPlanStatus());
		 * 
		 * } else if (form.getPlanStatus() != null && form.getPlanType() == null &&
		 * form.getStartDate() != null && form.getEnddate() != null) {
		 * 
		 * customers =
		 * reportRepo.findByPlanStatusAndStartDateBetween(form.getPlanStatus(),
		 * form.getStartDate(), form.getEnddate());
		 * 
		 * } else if (form.getPlanStatus() == null && form.getPlanType() != null &&
		 * form.getStartDate() != null && form.getEnddate() != null) {
		 * 
		 * return reportRepo.findByPlanTypeAndStartDateBetween(form.getPlanType(),
		 * form.getStartDate(), form.getEnddate());
		 * 
		 * } else {
		 * 
		 * if (form.getEnddate() != null) { customers =
		 * reportRepo.findByPlanTypeOrPlanStatusOrStartDateBetween(form.getPlanType(),
		 * form.getPlanStatus(), form.getStartDate(), form.getEnddate()); } customers =
		 * reportRepo.findByPlanTypeOrPlanStatusOrStartDate(form.getPlanType(),
		 * form.getPlanStatus(), form.getStartDate());
		 * 
		 * }
		 */
		/*
		 * StringBuilder buildQuiery = new StringBuilder();
		 * 
		 * buildQuiery.append("select * from customer");
		 * 
		 * boolean quieryFlag = false;
		 * 
		 * if (form.getPlanType() != null && !form.getPlanType().equals("")) {
		 * 
		 * if (quieryFlag == false) { buildQuiery.append(" where planType = " +
		 * form.getPlanType()); quieryFlag = true; }else {
		 * buildQuiery.append(" and planType = " + form.getPlanType()); }
		 * 
		 * } if (form.getPlanStatus() != null && !form.getPlanStatus().equals("")) { if
		 * (quieryFlag == false) { buildQuiery.append(" where planStatus = " +
		 * form.getPlanStatus()); quieryFlag = true; }else {
		 * buildQuiery.append(" and planStatus = " + form.getPlanStatus()); }
		 * 
		 * 
		 * }
		 */

		Customer customer = new Customer();

		if (form.getPlanType() != null && !form.getPlanType().equals("")) {
			customer.setPlanType(form.getPlanType());
		}

		if (form.getPlanStatus() != null && !form.getPlanStatus().equals("")) {
			customer.setPlanStatus(form.getPlanStatus());
		}

		Example<Customer> example = Example.of(customer);

		customers = reportRepo.findAll(example);

		return customers;

	}

	@Override
	public void getPdfRepot(HttpServletResponse response, RequestReport form) {

		List<Customer> customers = null;

		customers = searchCustomer(form);

		Document document = new Document(PageSize.A4);
		try {
			PdfWriter.getInstance(document, response.getOutputStream());
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.open();

		Font paraFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
		paraFont.setSize(20);
		paraFont.setColor(Color.BLACK);

		Paragraph p = new Paragraph(" Insurance Report ", paraFont);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		
		PdfPTable pdftable = new PdfPTable(8);

		pdftable.setWidthPercentage(100f);
		pdftable.setWidths(new float[] { 2f, 4.5f, 6f, 6f, 4f, 4f, 5.5f, 5f });
		pdftable.setSpacingBefore(10);

		PdfPCell headercell = new PdfPCell();
		headercell.setBackgroundColor(Color.BLUE);
		headercell.setPadding(3f);
		Font headerFont = FontFactory.getFont(FontFactory.COURIER_BOLD);
		headerFont.setColor(Color.BLACK);
	
		headercell.setPhrase(new Phrase("cid", headerFont));
		pdftable.addCell(headercell);

		headercell.setPhrase(new Phrase("Customer Name", headerFont));
		pdftable.addCell(headercell);

		headercell.setPhrase(new Phrase("Customer Email", headerFont));
		pdftable.addCell(headercell);

		headercell.setPhrase(new Phrase("Customer Number", headerFont));
		pdftable.addCell(headercell);

		headercell.setPhrase(new Phrase("Plan Type", headerFont));
		pdftable.addCell(headercell);

		headercell.setPhrase(new Phrase("Plan Status", headerFont));
		pdftable.addCell(headercell);

		headercell.setPhrase(new Phrase("Start Date", headerFont));
		pdftable.addCell(headercell);

		headercell.setPhrase(new Phrase("End Date", headerFont));
		pdftable.addCell(headercell);

		for (Customer customer : customers) {

			pdftable.addCell(String.valueOf(customer.getCustomerId()));
			pdftable.addCell(customer.getCustomerName());
			pdftable.addCell(customer.getCustomerEmail());
			pdftable.addCell(String.valueOf(customer.getCustomerNumber()));
			pdftable.addCell(customer.getPlanType());
			pdftable.addCell(customer.getPlanStatus());
			pdftable.addCell(new SimpleDateFormat("dd-MM-yyyy").format(customer.getStartDate()));
			pdftable.addCell(new SimpleDateFormat("dd-MM-yyyy").format(customer.getEndDate()));

		}

		document.add(pdftable);
		
		document.close();

	}

	@Override
	public void getExcelReport(HttpServletResponse response, RequestReport form) throws IOException {

		List<Customer> customers = null;

		customers = searchCustomer(form);

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("report");
		XSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("customer id");
		row.createCell(1).setCellValue("customer name");
		row.createCell(2).setCellValue("customer email");
		row.createCell(3).setCellValue("customer number");
		row.createCell(4).setCellValue("plan type");
		row.createCell(5).setCellValue("plan status");
		row.createCell(6).setCellValue("start date");
		row.createCell(7).setCellValue("end date");

		int count = 1;

		for (Customer customer : customers) {

			XSSFRow newRow = sheet.createRow(count);
			newRow.createCell(0).setCellValue(customer.getCustomerId());
			newRow.createCell(1).setCellValue(customer.getCustomerName());
			newRow.createCell(2).setCellValue(customer.getCustomerEmail());
			newRow.createCell(3).setCellValue(customer.getCustomerNumber());
			newRow.createCell(4).setCellValue(customer.getPlanType());
			newRow.createCell(5).setCellValue(customer.getPlanStatus());
			newRow.createCell(6).setCellValue(customer.getStartDate());
			newRow.createCell(7).setCellValue(customer.getEndDate());
			count++;
		}

		ServletOutputStream outputStream = response.getOutputStream();

		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}

}
