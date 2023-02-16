package com.Bibo.common.util;

import com.Bibo.common.annotation.ExportAnnotation;
import com.Bibo.common.annotation.ExportTypeAnnotation;
import com.Bibo.common.pojo.vo.FileDownVO;
import com.Bibo.common.servie.ExportTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
public class ExportUtil {

    private static final Map<Class<? extends ExportTypeHandler>,ExportTypeHandler> TYPE_HANDLER_MAP = new HashMap<>();

    public static FileDownVO export(List<?> list, HttpServletResponse response,String fileName){


        if(null == list || list.isEmpty()){
            log.error("没有可以导出的数据");
        }
        //数据集合
        List<Map<String,Object>> data = new ArrayList<>();
        list.forEach(object ->{

            Map<String,Object> linkedMap = new LinkedHashMap<>();
            //获取集合的对象
            Field[] fields = object.getClass().getDeclaredFields();

            for(Field field:fields){
                field.setAccessible(true);
                //判断对象的属性是否有导出的注解
                if(!field.isAnnotationPresent(ExportAnnotation.class)){
                    continue;
                }
                //有导出注解的获取该注解的值
                ExportAnnotation annotation = field.getAnnotation(ExportAnnotation.class);
                String exportName = annotation.value();
                String type = annotation.type();
                if(StringUtils.isBlank(exportName)){
                    exportName = field.getName();
                }
                //获取属性的值
                String value = null;
                try {
                    if(null != field.get(object)){
                        value = field.get(object).toString();
                    }else{
                        value="";
                    }
                    if(!type.equals("") && null != field.get(object)) {
                        value = DateUtils.getDateToStr((Date) field.get(object));
                    }
//                    }else{
//                        value = getValue(field,value);
//                    }
                    linkedMap.put(exportName,value);
                }catch (IllegalAccessException e){
                    log.error("获取属性失败:",e);
                }
            }
            data.add(linkedMap);
        });
        FileDownVO fileDownVO =mapToExcel(data,response,fileName);
        return fileDownVO;
    }


    private static String getValue(Field filed,String value){
        if(Objects.isNull(value)){
            value ="";
        }else if(filed.isAnnotationPresent(ExportAnnotation.class)){
            Class<? extends ExportTypeHandler> aClass = filed.getAnnotation(ExportTypeAnnotation.class).typeHandler();
            ExportTypeHandler typeHandler;
            try {
                //判断是否有枚举类型转换
                if (TYPE_HANDLER_MAP.containsKey(aClass)){
                    typeHandler = TYPE_HANDLER_MAP.get(aClass);
                }else {
                    typeHandler = aClass.newInstance();
                    TYPE_HANDLER_MAP.put(aClass,typeHandler);
                }
                value = typeHandler.getValue(value);
            }catch (Exception e){
                log.error("ExportTypeHandler转换失败,value:{}失败异常",value,e);
            }
        }
        return value;
    }


    public static FileDownVO mapToExcel(List<Map<String,Object>> data,HttpServletResponse response,String fileName){
        List<List<String>> result = new ArrayList<>();
        List<String> menu = new ArrayList<>();
        Set<String> m = new HashSet<>();
        result.add(menu);
        data.forEach(map ->{
            List<String> list = new ArrayList<>();
            for(String amenu :menu){
                if(map.containsKey(amenu)){
                    Object o = map.get(amenu);
                    if( null != o){
                        list.add(o.toString());
                    }else{
                        list.add("");
                    }
                }else{
                    list.add("");
                }
            }
            for(Map.Entry<String,Object> entry :map.entrySet()){
                if(!m.contains(entry.getKey())){
                    m.add(entry.getKey());
                    menu.add(entry.getKey());
                    Object o = entry.getValue();
                    if( null != o){
                        list.add(o.toString());
                    }else{
                        list.add("");
                    }
                }
            }
            result.add(list);
        });
        FileDownVO fileDownVO =excel(result,response,fileName);
        return fileDownVO;
    }

    private static FileDownVO excel(List<List<String>> data,HttpServletResponse response,String fileName){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        for(int i=0;i<data.size();i++){
            HSSFRow row = sheet.createRow(i);
            List<String> list = data.get(i);
            for(int j=0;j<list.size();j++){
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(list.get(j));
            }
        }
        try {
            String path = RequestParamsUtil.getFilePath()+DateUtils.getDateDayToStr(new Date());
            File fileFold = new File(path);
            if(!fileFold.exists() && !fileFold.isDirectory()){
                System.out.println("文件夹不存在");
                fileFold.mkdir();
            }else{
                System.out.println("文件夹存在");
            }
            FileDownVO fileDownVO = new FileDownVO();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            int length = baos.size();
            System.out.println(length);

            String filePath = path +"/"+fileName+".xls";
            FileOutputStream fout = new FileOutputStream(filePath);
            workbook.write(fout);
            fout.flush();
            fout.close();
            fileDownVO.setUrl(filePath);
            fileDownVO.setStatus("已生成");
            fileDownVO.setSize(length/1024);
            return fileDownVO;
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new FileDownVO();
        /*try {
           // response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName+".xls","utf-8"));
            response.setContentType("application/msexcel");
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    public static int getFileSize(String filePath){
        FileInputStream file = null;
        try {
            file = new FileInputStream(filePath);
            int size =file.available();
            file.close();
            return size;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static HSSFWorkbook getExcelFile(List<List<String>> data){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        for(int i=0;i<data.size();i++){
            HSSFRow row = sheet.createRow(i);
            List<String> list = data.get(i);
            for(int j=0;j<list.size();j++){
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(list.get(j));
            }
        }
        return workbook;
    }

    public static void downFile(String url,HttpServletResponse response,String name){

        try{
            InputStream inputStream = new FileInputStream(url);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(name,"utf-8"));
            response.setContentType("application/msexcel");
            byte[] b =new byte[100];
            int len;
            while ((len = inputStream.read(b))>0){
                response.getOutputStream().write(b,0,len);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
