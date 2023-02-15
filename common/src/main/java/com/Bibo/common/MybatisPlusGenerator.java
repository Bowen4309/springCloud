package com.Bibo.common;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MybatisPlusGenerator {

    public static final String BASE_PROJECT_PATH = "D:\\mybatisPlus-generator";

    public static final String BASE_PACKAGE = "com.xdh.data_api_model";

    public static final String AUTHOR = "CodeGenerator";

    public static String[] TABLES = {"tf_analyse_approve_result"};

    //JDBC 连接信息
    public static final String JDBC_PGSQL_URL = "jdbc:postgresql://172.16.247.253:5432/traffic";

    public static final String JDBC_DRIVER_NAME = "org.postgresql.Driver";

    public static final String JDBC_USERNAME = "postgres";

    public static final String JDBC_PASSWORD = "meiya@123";

    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();

        /**
         * 数据库配置
         */
        autoGenerator.setDataSource(new DataSourceConfig()
                .setDbType(DbType.POSTGRE_SQL)
                .setUrl(JDBC_PGSQL_URL)
                .setDriverName(JDBC_DRIVER_NAME)
                .setUsername(JDBC_USERNAME)
                .setPassword(JDBC_PASSWORD)
                .setTypeConvert(new PostgreSqlTypeConvert(){
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
                        return super.processTypeConvert(config, fieldType);
                    }
                })
        );

        /**
         * 全局配置
         */
        autoGenerator.setGlobalConfig(new GlobalConfig()
                .setOutputDir(BASE_PROJECT_PATH+"/src/main/java")
                .setFileOverride(true)
                .setActiveRecord(true)
                .setEnableCache(false)
                .setBaseColumnList(true)
                .setBaseResultMap(true)
                .setOpen(false)
                .setAuthor(AUTHOR)
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController")

        );

        /**
         * 策略设定
         */
        autoGenerator.setStrategy(new StrategyConfig()
                .setTablePrefix("tf_")
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(TABLES)
                .setRestControllerStyle(true)
                .setEntityLombokModel(true)
        );

        /**
         * 设置package
         */
        autoGenerator.setPackageInfo(new PackageConfig()
                .setParent(BASE_PACKAGE)
                .setEntity("entity")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl")
                .setController("controller")
        );

        autoGenerator.setTemplate(new TemplateConfig());

        autoGenerator.execute();
    }





}
