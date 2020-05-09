package cn.cjx;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: xiaoxiang.zhang
 * @Description:返回对象封装
 * @Date: Create in 6:50 下午 2020/3/17
 */
@Setter
@Getter
@ToString
public class ResponseMsg {

  private String code;

  private String msg;

  private Object data;

  public ResponseMsg(String code, String msg, Object data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }
}
