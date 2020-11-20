package xyz.duguqiubai.handledata.entity;

import com.alibaba.excel.annotation.ExcelProperty;

public class RNUpdateENtity {

    @ExcelProperty(value = "platform", index = 6)
    private byte platform;
    @ExcelProperty(value = "appCode", index = 5)
    private String appCode;
    @ExcelProperty(value = "RN版本号", index = 1)
    private String version;
    @ExcelProperty(value = "外壳依赖版本号", index = 2)
    private String minVersion;
    @ExcelProperty(value = "迭代内容", index = 3)
    private String developerDesc;

    public byte getPlatform() {
        return platform;
    }

    public void setPlatform(byte platform) {
        this.platform = platform;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(String minVersion) {
        this.minVersion = minVersion;
    }

    public String getDeveloperDesc() {
        return developerDesc;
    }

    public void setDeveloperDesc(String developerDesc) {
        this.developerDesc = developerDesc;
    }

    @Override
    public String toString() {
        return "RNUpdateENtity{" +
                "platform=" + platform +
                ", appCode='" + appCode + '\'' +
                ", version='" + version + '\'' +
                ", minVersion='" + minVersion + '\'' +
                ", developerDesc='" + developerDesc + '\'' +
                '}';
    }
}
