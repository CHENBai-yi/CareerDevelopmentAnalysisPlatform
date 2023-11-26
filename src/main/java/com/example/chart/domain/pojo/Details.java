package com.example.chart.domain.pojo;

import java.util.Date;
import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.Builder;

/**
 * (Details)实体类
 *
 * @author ChenBaiYi
 * @since 2023-11-23 15:52:11
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Details implements Serializable {
    private static final long serialVersionUID = -95983057219653371L;
    private String jobid;
    private String jobtype;
    private String jobname;
    private String jobtags;
    private String jobnumstring;
    private String workareacode;
    private String jobareacode;
    private String jobareastring;
    private String hrefareapinyin;
    private String jobarealeveldetail;
    private String providesalarystring;
    private Date issuedatestring;
    private Date confirmdatestring;
    private String workyear;
    private String workyearstring;
    private String degreestring;
    private String industrytype1;
    private String industrytype2;
    private String industrytype1str;
    private String industrytype2str;
    private String functype1code;
    private String functype2code;
    private String major1str;
    private String major2str;
    private String enccoid;
    private String companyname;
    private String fullcompanyname;
    private String companylogo;
    private String companytypestring;
    private String companysizestring;
    private String companysizecode;
    private String hruid;
    private String hrname;
    private String smallhrlogourl;
    private String hrposition;
    private String hractivestatusgreen;
    private String hrmedaltitle;
    private String hrmedallevel;
    private Integer showhrmedaltitle;
    private Integer hrisonline;
    private String hrlabels;
    private Date updatedatetime;
    private String lon;
    private String lat;
    private Integer iscommunicate;
    private Integer isfromxyx;
    private Integer isintern;
    private Integer ismodelemployer;
    private Integer isquickfeedback;
    private Integer ispromotion;
    private Integer isapply;
    private Integer isexpire;
    private String jobhref;
    private String companyhref;
    private Integer allowchatonline;
    private Integer ctmid;
    private String term;
    private String termstr;
    private String landmarkid;
    private String landmarkstring;
    private String retrievername;
    private String exrinfo02;
    private Integer hrinfotype;
    private String jobscheme;
    private String coid;

}
