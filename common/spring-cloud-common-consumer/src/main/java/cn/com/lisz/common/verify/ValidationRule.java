package cn.com.lisz.common.verify;

public enum ValidationRule {
    /**
     * 未定义
     */
    None("", "", ""),

    /**
     * 用户名
     */
    UserName("UserName", "用户名", ""),

    /**
     * 密码
     */
    Password("Password", "密码", ""),

    /**
     * 企业名
     */
    CorporationName("CorporationName", "企业名", ""),

    /**
     * 企业简称
     */
    CorporationShort("CorporationShort", "企业简称", ""),

    /**
     * 人名
     */
    PersonalName("PersonalName", "人名", ""),

    /**
     * 联系人
     */
    Contacts("contacts","联系人",""),

    /**
     * 手机
     */
    Mobile("Mobile", "手机", ""),

    /**
     * 电话
     */
    Telephone("Telephone", "手机", ""),

    /**
     * 验证码
     */
    Captcha("Captcha", "验证码", ""),

    /**
     * 工商营业执照、组织机构代码证和税务登记证
     */
    IC("IC", "组织机构代码证", ""),

    /**
     * 道路运输许可证
     */
    TIR("TIR", "道路运输许可证", ""),

    /**
     * 身份证
     */
    IdCard("IdCard", "身份证", ""),

    /**
     * 详细地址
     */
    DetailedAddress("DetailedAddress", "详细地址", ""),

    /**
     * 企业介绍
     */
    CompanyProfile("CompanyProfile", "企业介绍", ""),

    /**
     * 客户名称
     */
    CustomerName("CustomerName", "客户名称", ""),

    /**
     * 车牌号
     */
    CarNumber("CarNumber", "车牌号", ""),

    /**
     * 驾驶证号码
     */
    DriverLicenseNumber("DriverLicenseNumber", "驾驶证号码", ""),

    /**
     * 备注
     */
    Remark("Remark", "备注", ""),

    /**
     * 金额
     */
    Amount("Amount", "金额", ""),

    /**
     * 数量
     */
    Number("Number", "数量", ""),

    /**
     * 税率
     */
    TaxRate("TaxRate", "税率", ""),

    /**
     * 银行名称
     */
    BankName("BankName", "银行名称", ""),

    /**
     * 电话或手机
     */
    MobileOrPhone("MobileOrPhone", "电话或手机", ""),

    /**
     * 银行账户
     */
    BankAccount("BankAccount", "银行账户", ""),

    /**
     * 邮寄地址
     */
    MailAddress("MailAddress", "邮寄地址", ""),

    /**
     * 货源名称
     */
    GoodsName("GoodsName", "货源名称", ""),

    /**
     * 货源数量
     */
    GoodsNumber("GoodsNumber", "货源数量", ""),

    /**
     * 货物价值
     */
    GoodsWorth("GoodsWorth", "货物价值", ""),

    /**
     * 预付比例
     */
    PrepayRatio("PrepayRatio", "预付比例", ""),

    /**
     * 预付金额
     */
    PrepayAmount("PrepayRatio", "预付金额", ""),

    /**
     * 支付密码
     */
    payPass("payPass", "支付密码", ""),

    /**
     * 企业邮箱
     */
    mail("mail", "企业邮箱","");

    private String name;
    private String message;
    private String description;

    ValidationRule(String name, String message, String description) {
        this.name = name;
        this.message = message;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
