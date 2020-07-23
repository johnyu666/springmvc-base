package cn.johnyu.mvc01.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Customer  {
    private int id;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date birth;
}
