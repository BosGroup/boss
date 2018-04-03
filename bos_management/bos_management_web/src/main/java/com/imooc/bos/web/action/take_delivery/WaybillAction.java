package com.imooc.bos.web.action.take_delivery;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imooc.bos.domain.take_delivery.WayBill;
import com.imooc.bos.service.take_delivery.WaybillService;
import com.imooc.bos.web.action.CommonAction;

/**
 * ClassName:WaybillAction <br/>
 * Function: <br/>
 * Date: 2018年3月25日 下午9:26:57 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class WaybillAction extends CommonAction<WayBill> {

    public WaybillAction() {
        super(WayBill.class);
    }

    @Autowired
    private WaybillService waybillService;
    
    
    //###################### 保存运单 ########################
    @Action("waybillAction_save")
    public String save() throws IOException {
        String msg = "0";
        try {
            //int i =10/0;
            
            waybillService.save(getModel());
        } catch (Exception e) {
            e.printStackTrace();  
            msg = "1";
        }
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(msg);
        return NONE;
    }

    
    //接收文件
    private File upload;


    public void setUpload(File upload) {
        this.upload = upload;
    }

    
    //批量导入运单
    @Action(value = "waybill_batchImport")
    public String batchImport() {
        List<WayBill> list = new ArrayList<>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(upload));
            // 读取工作簿
            HSSFSheet sheetAt = workbook.getSheetAt(0);

            for (Row row : sheetAt) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                String idStr = row.getCell(0).getStringCellValue();
                Long id = Long.parseLong(idStr);
                String goodsType = row.getCell(1).getStringCellValue();
                String sendProNum = row.getCell(2).getStringCellValue();
                String sendName = row.getCell(3).getStringCellValue();
                String sendMobile = row.getCell(4).getStringCellValue();
                String sendAddress = row.getCell(5).getStringCellValue();
                String recName = row.getCell(6).getStringCellValue();
                String recMobile = row.getCell(7).getStringCellValue();
                String recCompany = row.getCell(8).getStringCellValue();
                String recAddress = row.getCell(9).getStringCellValue();
                WayBill wayBill = new WayBill();
                wayBill.setId(id);
                wayBill.setGoodsType(goodsType);
                wayBill.setSendProNum(sendProNum);
                wayBill.setSendName(sendName);
                wayBill.setSendMobile(sendMobile);
                wayBill.setSendAddress(sendAddress);
                wayBill.setRecName(recName);
                wayBill.setRecMobile(recMobile);
                wayBill.setRecCompany(recCompany);
                wayBill.setRecAddress(recAddress);
                list.add(wayBill);
                waybillService.batchImport(list);
                workbook.close();
            }
          
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("success");
           
           
        } catch (IOException e) {

            e.printStackTrace();

        }
        return NONE;

    }
    
}

