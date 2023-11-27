package com.example.db.domain.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (DatabaseConnection)实体类
 *
 * @author ChenBaiYi
 * @since 2023-11-27 18:58:41
 */
@Data
@Builder
@Accessors(chain = true)
public class DatabaseConnection implements Serializable {
    private static final long serialVersionUID = -11154983746086087L;
    private Integer id;
    private String url;
    private String username;
    private String password;
    private String driver;
    private String key;

}
