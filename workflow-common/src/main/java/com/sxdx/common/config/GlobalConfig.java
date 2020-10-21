package com.sxdx.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 全局配置类
 */
@Component
@ConfigurationProperties(prefix = "workflow")
public class GlobalConfig
{
    /** 项目名称 */
    private static String name;

    /** 版本 */
    private static String version;

    /** 版权年份 */
    private static String copyrightYear;

    /** 上传路径 */
    private static String profile;


    public static String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        GlobalConfig.name = name;
    }

    public static String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        GlobalConfig.version = version;
    }

    public static String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        GlobalConfig.copyrightYear = copyrightYear;
    }


    public static String getProfile()
    {
        return profile;
    }
    public void setProfile(String profile)
    {
        GlobalConfig.profile = profile;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }

}
