package com.frz.template.mulisource.business.service;

import com.frz.template.mulisource.business.entity.BankUserInfo;
import com.frz.template.mulisource.business.entity.City;
import com.frz.template.mulisource.business.mapper.BankUserInfoMapper;
import com.frz.template.mulisource.business.mapper.CityMapper;
import com.frz.template.mulisource.util.QueryJDBCUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengrongze on 2017/6/21.
 */
@Service
public class CityService {

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private QueryJDBCUtils queryJDBCUtils;

    @Autowired
    private BankUserInfoMapper bankUserInfoMapper;

    public List<City> queryCity(){
        Map<String,String> param = new HashMap<>();
        param.put("cityName","石家庄");

        City city = new City();
        city.setName("沙坪坝");
        city.setState("重庆");
        cityMapper.insert(city);

        BankUserInfo bankUserInfo = new BankUserInfo();
        bankUserInfo.setUserClassName("三年级二班");
        bankUserInfo.setUserName("周杰伦2");


        bankUserInfoMapper.insert(bankUserInfo);


        return cityMapper.queryCityByName(param);
    }



    public BankUserInfo queryUser(){
        Example example = new Example(BankUserInfo.class);
        example.createCriteria().andEqualTo("userName","周杰伦");

        List<BankUserInfo> bankUserInfo =  bankUserInfoMapper.selectByExample(example);
        return bankUserInfo.get(0);
    }




    public void  queryTables() throws Exception {
        Map<String, Object> result= queryJDBCUtils.queryForMap("select * from split_source",false);
        System.out.println("》》》"+result.get("dbname"));
    }





}
