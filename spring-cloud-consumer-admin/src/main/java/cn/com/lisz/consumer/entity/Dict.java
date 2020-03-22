package cn.com.lisz.consumer.entity;

import cn.com.lisz.common.model.BaseModel;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class Dict extends BaseModel {
    private Long pid;

    @NotEmpty(message = "字典值不能为空")
    private String value;

    @NotEmpty(message = "字典标签不能为空")
    private String label;

    private String type;

    private String description;

    private Integer sort;

    public Dict(Long id, Long pid, String value, String label, String type, String description, Integer sort, Long createBy, Date createTime, Long updateBy, Date updateTime, String remarks, String delFlag) {
        this.id = id;
        this.pid = pid;
        this.value = value;
        this.label = label;
        this.type = type;
        this.description = description;
        this.sort = sort;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.remarks = remarks;
        this.delFlag = delFlag;
    }

    public Dict() {
        super();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}