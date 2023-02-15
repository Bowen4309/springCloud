package com.Bibo.job.model.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.Bibo.common.constant.*;
import com.Bibo.common.response.ApiResponse;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.*;
import com.Bibo.job.model.dto.ResourceBaseCarPictureDTO;
import com.Bibo.job.model.service.IPublicJobService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

@Service
public class PublicJobServiceImpl implements IPublicJobService {


    @Override
    public Response checkAnlayseApprove() {
        try {

            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypePublicJobEnum.CHECK_CAR_APPROVE_TIME.getApiType(),""));
            if(apiResponse.isSuccess()){
                return Response.success();
            }
            return Response.error(apiResponse.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error();
    }

    @Override
    public Response updateAnalyseActiveCar() {
        try {

            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypeActiveEnum.SYNCHRONOUS_ACTIVE_DATA.getApiType(),""));
            if(apiResponse.isSuccess()){
                return Response.success();
            }
            return Response.error(apiResponse.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error();
    }

    @Override
    public Response updateAnalyseUnusualCar() {
        try {

            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypeUnusualEnum.SYNCHRONOUS_UNUSUAL_DATA.getApiType(),""));
            if(apiResponse.isSuccess()){
                return Response.success();
            }
            return Response.error(apiResponse.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error();
    }

    @Override
    public Response uploadCarNumberToFtp() {
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypePublicJobEnum.SYNCHRONOUS_FTP_CAR_DATA.getApiType(),""));
            if(apiResponse.isSuccess()){
                List<ResourceBaseCarPictureDTO> data = JSONObject.parseArray(apiResponse.getData().toString(),ResourceBaseCarPictureDTO.class);
                List<List<String>> bookData = new ArrayList<List<String>>();
                List<String> head = Arrays.asList("carNumber","carNumberTypeCode");
                bookData.add(head);
                data.stream().forEach(r ->{
                    List<String> carNumbers = new ArrayList<>();
                    carNumbers.add(r.getCarNumber());
                    carNumbers.add(r.getCarNumberTypeCode());
                    bookData.add(carNumbers);
                });
                HSSFWorkbook workbook =ExportUtil.getExcelFile(bookData);
                try (ByteArrayOutputStream os = new ByteArrayOutputStream()){
                    workbook.write(os);
                    byte[] dataByte = os.toByteArray();
                    try (ByteArrayInputStream is = new ByteArrayInputStream(dataByte)){
                        String dateStr =DateUtils.getDateDayToStr(new Date());
                        UploadFileToFtpUtil.uploadFileToFtp(is,"carNumber_"+dateStr+".xls");
                    }
                }catch (Exception ex){
                    return Response.error(ex.getMessage());
                }
                return Response.success().data(data);
            }
            return Response.error(apiResponse.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error();
    }

    @Override
    public Response downPictureFromFtp() {
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypePublicJobEnum.SYNCHRONOUS_FTP_CAR_DATA.getApiType(),""));
            System.out.println(apiResponse.isSuccess());
            if(apiResponse.isSuccess()){
                List<ResourceBaseCarPictureDTO> data = JSONObject.parseArray(apiResponse.getData().toString(),ResourceBaseCarPictureDTO.class);
                System.out.println(data.size());
                String dateStr = DateUtils.getDateDayToStr1(new Date());
                for(ResourceBaseCarPictureDTO o :data){
                    List<String> carPicturesName = new ArrayList<>();

                    carPicturesName.add(o.getCarNumber().replace("挂","")+"_"+o.getCarNumberTypeCode()+"_"+"0111_"+dateStr+".jpg");
                    carPicturesName.add(o.getCarNumber().replace("挂","")+"_"+o.getCarNumberTypeCode()+"_"+"0112_"+dateStr+".jpg");
                    String isDown = UploadFileToFtpUtil.downPicture(carPicturesName);

                    if(!isDown.equals("")){
                        o.setPath(isDown);
                        o.setUploadTime(new Date());
                        HttpRequestUtil.getByDataArea(
                                RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                                ApiRequestParamsUtils.insertParamsObject(ApiTypeAnalyseApproveEnum.CAR_BASE_PICTURE_UPDATE.getApiType(),o));
                    }
                }
                return Response.success();
            }
            return Response.error(apiResponse.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error();
    }

    @Override
    public Response bussinessGridMap() {
        try {

            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypeGridEnum.BUSSINESS_RESOURCE_GRID_BIND.getApiType(),""));
            if(apiResponse.isSuccess()){
                return Response.success();
            }
            return Response.error(apiResponse.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error();
    }

    @Override
    public Response test() {
        try {

            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypeGridEnum.TEST.getApiType(),""));
            if(apiResponse.isSuccess()){
                return Response.success();
            }
            return Response.error(apiResponse.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error();
    }

    @Override
    public Response synCompanyInfo() {
        try {

            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypeBaseDataEnum.BASE_SYN_COMPANY_INFO.getApiType(),""));
            if(apiResponse.isSuccess()){
                return Response.success();
            }
            return Response.error(apiResponse.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error();
    }

}
