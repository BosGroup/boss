package com.imooc.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.imooc.bos.bosUtils.FileDownloadUtils;
import com.imooc.bos.domain.base.SubArea;
import com.imooc.bos.service.base.SubAreaService;
import com.imooc.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**
 * ClassName:SubareaAction <br/>
 * Function: <br/>
 * Date: 2018年3月16日 下午7:07:55 <br/>
 */

@Namespace("/") // 等价于struts.xml文件中package节点namespace属性
@ParentPackage("struts-default") // 等价于struts.xml文件中package节点extends属性
@Scope("prototype") // spring 的注解,多例
@Controller // spring 的注解,控制层代码
public class SubareaAction extends CommonAction<SubArea> {

    // 通过构造方法传递模型驱动所需的对象类型
    public SubareaAction() {
        super(SubArea.class);
    }

    @Autowired
    private SubAreaService subAreaService;

    // ################### 分区信息保存 ####################
    @Action(value = "subareaAction_save", results = {
            @Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect")})
    public String save() {
        subAreaService.save(getModel());
        return SUCCESS;
    }

    // ################### 分区分页查询 ####################
    // AJAX请求不需要跳转页面
    @Action(value = "subAreaAction_pageQuery")
    public String pageQuery() throws IOException {

        // EasyUI的页码是从1开始的, SPringDataJPA的页码是从0开始的, 所以要-1
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<SubArea> page = subAreaService.findAll(pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas", "couriers"});

        page2json(page, jsonConfig);

        return NONE;
    }

    // ################### 查询未关联到定区的分区 ####################
    // 定区和分区的关系由分区维护
    @Action(value = "subAreaAction_findUnAssociatedsubAreas")
    public String findUnAssociatedsubAreas() throws IOException {

        List<SubArea> list = subAreaService.findUnAssociatedsubAreas();

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        list2json(list, jsonConfig);
        return NONE;
    }

    // ################### 查询已关联到指定定区的分区 ####################
    @Action(value = "subAreaAction_findAssociatedsubAreas")
    public String findAssociatedsubAreas() throws IOException {

        List<SubArea> list = subAreaService.findAssociatedsubAreas(getModel().getId());

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas", "couriers"});
        list2json(list, jsonConfig);
        return NONE;
    }

    // 导出分区的Excel表格
    @Action(value = "subAreaAction_exportExcel")
    public String exportExcel() throws IOException {

        // 查询出要导出的数据
        Page<SubArea> page = subAreaService.findAll(null);
        List<SubArea> list = page.getContent();

        // 创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 创建工作表
        HSSFSheet sheet = workbook.createSheet();
        // 创建数据表的表头
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("分拣编号");
        headRow.createCell(1).setCellValue("起始号");
        headRow.createCell(2).setCellValue("终止号");
        headRow.createCell(3).setCellValue("单双号");
        headRow.createCell(4).setCellValue("关键字");
        headRow.createCell(5).setCellValue("辅助关键字");
        headRow.createCell(6).setCellValue("所属区域");
        headRow.createCell(7).setCellValue("所属定区");
        // 遍历数据,写入到数据表
        for (SubArea subArea : list) {
            int lastRowNum = sheet.getLastRowNum();
            HSSFRow dataRow = sheet.createRow(lastRowNum + 1);

            dataRow.createCell(0).setCellValue(subArea.getId());
            dataRow.createCell(1).setCellValue(subArea.getStartNum());
            dataRow.createCell(2).setCellValue(subArea.getEndNum());
            if (subArea.getSingle() == '0') {
                dataRow.createCell(3).setCellValue("单双号");
            } else if (subArea.getSingle() == '1') {
                dataRow.createCell(3).setCellValue("单号");
            } else {
                dataRow.createCell(3).setCellValue("双号");
            }
            dataRow.createCell(4).setCellValue(subArea.getKeyWords());
            dataRow.createCell(5).setCellValue(subArea.getAssistKeyWords());
            dataRow.createCell(6).setCellValue(subArea.getArea().getName());
            if (subArea.getFixedArea() != null) {
                dataRow.createCell(7).setCellValue(subArea.getFixedArea().getFixedAreaName());
            } else {
                dataRow.createCell(7).setCellValue("该分区尚未关联定区");
            }

        }
        // 创建文件名
        String filename = "分区数据统计.xls";
        // 一流两头
        HttpServletResponse response = ServletActionContext.getResponse();
        ServletOutputStream outputStream = response.getOutputStream();

        // 获取mimetype
        ServletContext servletContext = ServletActionContext.getServletContext();
        String mimeType = servletContext.getMimeType(filename);

        // 获取浏览器类型
        HttpServletRequest request = ServletActionContext.getRequest();
        String header = request.getHeader("User-Agent");
        // 使用工具类进行编码,解决中文乱码
        filename = FileDownloadUtils.encodeDownloadFilename(filename, header);

        // 设置信息头
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        // 写出文件
        workbook.write(outputStream);
        workbook.close();

        return NONE;
    }

    // 导出图表Action
    @Action(value = "subareaAction_exportCharts")
    public String exportCharts() throws IOException {

        List<Object[]> list = subAreaService.exportCharts();

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas", "couriers"});
        list2json(list, jsonConfig);
        return NONE;
    }

    // ################### 批量删除分区信息 ####################
    // 使用属性驱动获取要删除的快递员的Id
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action(value = "subAreaAction_batchDel", results = {
            @Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect")})
    public String batchDel() {
        subAreaService.batchDel(ids);
        return SUCCESS;
    }
}
