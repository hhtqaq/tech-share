package exception;

/**
 * @description: 业务异常
 * @className: com.shopee.study.ratelimiter.exception.BizException
 * @copyRight: www.shopee.com by SZDC-BankingGroup
 * @author: haitao.huang
 * @createDate: 2022/9/19
 */
public class BizException extends Exception{
    public BizException() {super();}
    public BizException(String message) {super(message);}
    public BizException(String message,Exception e) {super(message,e);}
}
