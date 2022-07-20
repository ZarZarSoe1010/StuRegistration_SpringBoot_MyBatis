package com.studentRegistration.mapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import com.studentRegistration.model.User;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class UserReport {
    @Autowired
    UserMapper userMapper;
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException{
        String path="D:\\ACE(OJT)\\StuRegistration_SpringBoot_MyBatis";
        List<User>user =userMapper.selectAllUser();
        File file=ResourceUtils.getFile("classpath:user.jrxml");
        JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
        System.out.println(user);
        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(user);
        
        Map<String,Object> parameters=new HashMap<>();
        parameters.put("User List report", "user");
        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport, parameters,dataSource);
        System.out.println(jasperPrint);
        if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\user.html");
        }
        if(reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\user.pdf");

        }
        return "report generated in path : "+ path;
    }
    
}
