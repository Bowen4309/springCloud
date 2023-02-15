package com.Bibo.job.model.service;


import com.Bibo.common.response.Response;

public interface IPublicJobService {


    Response checkAnlayseApprove();

    Response updateAnalyseActiveCar();

    Response updateAnalyseUnusualCar();

    Response uploadCarNumberToFtp();

    Response downPictureFromFtp();

    Response bussinessGridMap();


    Response test();

    Response synCompanyInfo();

}
