package com.qingcheng.dao;

import com.qingcheng.pojo.system.Menu;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuMapper extends Mapper<Menu> {

    //根据用户名字查询对应的菜单
    @Select("SELECT * FROM tb_menu WHERE id IN(\n" +
            "\n" +
            "SELECT menu_id FROM tb_resource_menu WHERE resource_id IN ( SELECT resource_id FROM tb_role_resource WHERE role_id IN (\n" +
            "\n" +
            "SELECT role_id FROM tb_admin_role WHERE admin_id IN ( SELECT id FROM tb_admin WHERE login_name=#{name} \n" +
            "\n" +
            ")\n" +
            "\n" +
            ")\n" +
            "\n" +
            ")\n" +
            "\n" +
            ")\n" +
            "\n" +
            "UNION\n" +
            "\n" +
            "SELECT * FROM tb_menu WHERE id IN(\n" +
            "\n" +
            "SELECT parent_id FROM tb_menu WHERE id IN(\n" +
            "\n" +
            "SELECT menu_id FROM tb_resource_menu WHERE resource_id IN ( SELECT resource_id FROM tb_role_resource WHERE role_id IN (\n" +
            "\n" +
            "SELECT role_id FROM tb_admin_role WHERE admin_id IN ( SELECT id FROM tb_admin WHERE login_name=#{name} \n" +
            "\n" +
            ")\n" +
            "\n" +
            ")\n" +
            "\n" +
            ")\n" +
            "\n" +
            ")\n" +
            "\n" +
            ")\n" +
            "\n" +
            "UNION\n" +
            "\n" +
            "SELECT * FROM tb_menu WHERE id IN (\n" +
            "\n" +
            "SELECT parent_id FROM tb_menu WHERE id IN( SELECT parent_id FROM tb_menu WHERE id IN(\n" +
            "\n" +
            "SELECT menu_id FROM tb_resource_menu WHERE resource_id IN ( SELECT resource_id FROM tb_role_resource WHERE role_id IN (\n" +
            "\n" +
            "SELECT role_id FROM tb_admin_role WHERE admin_id IN ( SELECT id FROM tb_admin WHERE login_name=#{name} \n" +
            "\n" +
            ")\n" +
            "\n" +
            ")\n" +
            "\n" +
            ")\n" +
            "\n" +
            ")\n" +
            "\n" +
            ")\n" +
            "\n" +
            ")")
    List<Menu> findMenuByName(String name);
}
