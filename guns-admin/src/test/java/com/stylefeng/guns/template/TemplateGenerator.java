package com.stylefeng.guns.template;

import com.stylefeng.guns.common.persistence.model.Menu;
import com.stylefeng.guns.core.template.config.ContextConfig;
import com.stylefeng.guns.core.template.engine.SimpleTemplateEngine;
import com.stylefeng.guns.core.template.engine.base.GunsTemplateEngine;
import com.stylefeng.guns.modular.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 测试Guns模板引擎
 *
 * @author fengshuonan
 * @date 2017-05-09 20:27
 */
public class TemplateGenerator {
    public static void main(String[] args) throws IOException {

/*
        String bizChNames[] = {"常规项目","导出配置","项目跟踪","项目地址及用地","投资方公司","履约信息","项目操作","洽谈信息","责任单位"};
        String bizEnNames[] = {"normalProject", "exportConfig", "followProject", "proArea", "proCompany", "proConvention", "proHistory", "proTalk", "unitLiable"} ;
        String entityNames[] = {"NormalProject", "ExportConfig", "FollowProject", "ProArea", "ProCompany", "ProConvention", "ProHistory", "ProTalk", "UnitLiable"};

        String projectPath = "E:\\tcj\\guns-admin";
        String moduleName = "api";
        String author = "monkey";


        for (int i = 0; i < bizChNames.length; i++) {
            ContextConfig contextConfig = new ContextConfig(projectPath, bizChNames[i], bizEnNames[i], moduleName, entityNames[i], author);
            GunsTemplateEngine gunsTemplateEngine = new SimpleTemplateEngine();
            gunsTemplateEngine.setContextConfig(contextConfig);
            gunsTemplateEngine.start();
        }
*/



        ContextConfig contextConfig = new ContextConfig();
        contextConfig.setBizChName("项目管理");
        contextConfig.setBizEnName("myPlatform");
        contextConfig.setModuleName("api");
        // 自定义 实体名：先用MyBatisPlusGenerator生成实体，在使用此生成js等
        // 名字跟实体名相同即可--------------
        contextConfig.setEntityName("MyPlatform");
        contextConfig.setAuthor("lgg");// 自己的名字

        // 自己项目的guns-admin路径
        contextConfig.setProjectPath("D:\\tcj\\guns-admin");

        //contextConfig.setAddPageSwitch(false);
        //contextConfig.setEditPageSwitch(false);

        GunsTemplateEngine gunsTemplateEngine = new SimpleTemplateEngine();
        gunsTemplateEngine.setContextConfig(contextConfig);
        gunsTemplateEngine.start();

/*        Menu menu = new Menu();
        menu.setCode(contextConfig.getBizEnName());
        menu.setPcode("0");
        menu.setPcodes("[0],");
        menu.setName(contextConfig.getBizChName());
        menu.setIcon("");
        menu.setUrl("/"+contextConfig.getBizEnName());
        menu.setNum(1);
        menu.setLevels(1);
        menu.setIsmenu(1);
        menu.setTips("");
        menu.setStatus(1);
        menu.setIsopen(null);
        menuService.insert(menu);*/
    }

}
