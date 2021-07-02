package com.zwj.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileVo implements Serializable {
    private static final long serialVersionUID = 910318428120202723L;
    private String id;
    private String path;
    private String fileName;
}
