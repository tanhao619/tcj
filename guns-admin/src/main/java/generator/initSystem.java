package generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.stylefeng.guns.core.template.config.ContextConfig;
import com.stylefeng.guns.core.template.engine.SimpleTemplateEngine;
import com.stylefeng.guns.core.template.engine.base.GunsTemplateEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monkey on 2017/11/7.
 */
public class initSystem {

    public static void first (String sysPath, String author, String[] tables) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(sysPath + "\\src\\main\\java");//这里写你自己的java目录,到guns-admin
        gc.setFileOverride(false);//是否覆盖
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor(author);// 自己的名字
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("1qaz@WSX");
        dsc.setUrl("jdbc:mysql://192.168.30.245:3306/tcjdb?characterEncoding=utf8");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //strategy.setTablePrefix(new String[]{"_"});// 此处可以修改为您的表前缀

        if (tables.length > 0) {
            strategy.setInclude(tables);// 需要生成的表
        }
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        // 生成model中的字段如@TableField("fillTel") private String fillTel;
        // 驼峰命名的数据库必须不能少这个配置！！
        strategy.setDbColumnUnderline(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);
        pc.setEntity("com.stylefeng.guns.common.persistence.model");
        pc.setMapper("com.stylefeng.guns.common.persistence.dao");
        pc.setXml("com.stylefeng.guns.common.persistence.dao.mapping");
        pc.setService("TTT");       //本项目没用，生成之后删掉
        pc.setServiceImpl("TTT");   //本项目没用，生成之后删掉
        pc.setController("TTT");    //本项目没用，生成之后删掉
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);

        // 执行生成
        mpg.execute();

        // 打印注入设置
        System.err.println(mpg.getCfg().getMap().get("abc"));

    }

    public static void second (String sysPath, String author ) {
        String bizChNames[] = {"常规项目","导出配置","项目跟踪","项目地址及用地","投资方公司","履约信息","项目操作","洽谈信息","责任单位", "附件信息"};
        String bizEnNames[] = {"normalProject", "normalExportConfig", "followProject", "proArea", "proCompany", "proConvention", "proHistory", "proTalk", "unitLiable","proAttachment"} ;
        String entityNames[] = {"NormalProject", "ExportConfig", "FollowProject", "ProArea", "ProCompany", "ProConvention", "ProHistory", "ProTalk", "UnitLiable","ProAttachment"};

        String projectPath = sysPath;
        String moduleName = "api";

        for (int i = 0; i < bizChNames.length; i++) {
            ContextConfig contextConfig = new ContextConfig(projectPath, bizChNames[i], bizEnNames[i], moduleName, entityNames[i], author);
            GunsTemplateEngine gunsTemplateEngine = new SimpleTemplateEngine();
            gunsTemplateEngine.setContextConfig(contextConfig);
            gunsTemplateEngine.start();
        }
    }
    //删除磊哥制造的垃圾
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                    System.out.println("删除:" + files[i]);
                }
                file.delete();
            }
        } else {
            System.out.println("所删除的文件不存在");
        }

    }

    public static void main(String[] args) {
        //项目路径
        String sysPath = "E:\\tcj\\guns-admin";
        //作者
        String author = "monkey";
        first(sysPath, author, new String[]{});
        second(sysPath, author);
        //垃圾路径
        File file = new File(sysPath + "\\src\\main\\java\\TTT");
        deleteFile(file);
    }
}
