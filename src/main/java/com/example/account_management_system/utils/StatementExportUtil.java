package com.example.account_management_system.utils;

import com.example.account_management_system.dto.StatementResponse;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class StatementExportUtil {

    public static byte[] exportToCSV(StatementResponse statementResponse){
        StringBuilder sb = new StringBuilder();
        sb.append("Month, Name, Opening Balance, Total Deposits, Total Withdrawals, Closing Balance\n");

        try{
            sb.append(String.format("%d,%s,%s,%.2f,%.2f,%.2f,%.2f",
                    statementResponse.getAccountId(),
                    statementResponse.getName(),
                    statementResponse.getMonth(),
                    statementResponse.getOpeningBalance(),
                    statementResponse.getTotalDeposits(),
                    statementResponse.getTotalWithdrawals(),
                    statementResponse.getClosingBalance()
                    ));
        } catch (Exception e){
            throw new RuntimeException("Error generating CSV",e);
        }
        return sb.toString().getBytes();
    }

    public static byte[] exportToPDF(StatementResponse response){
        try {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document,out);

            document.open();
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD,16);
            Paragraph title = new Paragraph("Monthly Account Statement",font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA,12);
            document.add(new Paragraph("Account ID: "+response.getAccountId(),bodyFont));
            document.add(new Paragraph("Name: "+response.getName(),bodyFont));
            document.add(new Paragraph("Month: "+response.getMonth(),bodyFont));
            document.add(new Paragraph("Opening Balance: "+response.getOpeningBalance(),bodyFont));
            document.add(new Paragraph("Total Deposits: "+response.getTotalDeposits(),bodyFont));
            document.add(new Paragraph("Total Withdrawals: "+response.getTotalWithdrawals(),bodyFont));
            document.add(new Paragraph("Closing Balance: "+response.getClosingBalance(),bodyFont));

            document.close();
            return out.toByteArray();
        }catch(Exception e){
            throw new RuntimeException("Error getting PDF ",e);
        }
    }

}
