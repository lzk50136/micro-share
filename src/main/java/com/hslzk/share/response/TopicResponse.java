package com.hslzk.share.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TopicResponse implements Serializable {

    private static final long serialVersionUID = 5692253634179305566L;

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 话题名称
     */
    private String name;

    /**
     * 关注人数
     */
    private Integer followNum;

    /**
     * 贴子数量
     */
    private Integer postNum;

}
