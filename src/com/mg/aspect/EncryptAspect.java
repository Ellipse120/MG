package com.mg.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.mg.util.MD5Util;
import com.mg.vo.Seller;
@Aspect
@Component
public class EncryptAspect {
	private final String regMd = "execution(* com.mg.service.SellerService.regist(..))";
	private final String logMd = "execution(* com.mg.service.SellerService.login(..))";
	@Around(regMd)
	public Object encryptRegist(ProceedingJoinPoint point) {
		Object o = null;
		Seller user = (Seller) point.getArgs()[0];
		String mdReg = MD5Util.GetMD5Code(user.getPassword());
		user.setPassword(mdReg);
		try {
			o = point.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return o;
	}
	@Around(logMd)
	public Object encryptLogin(ProceedingJoinPoint point) {
		Object o = null;
		String logPsd = (String) point.getArgs()[1];
		String mdLog = MD5Util.GetMD5Code(logPsd);

		try {
			o = point.proceed(new Object[] {point.getArgs()[0], mdLog});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return o;
	}
}
