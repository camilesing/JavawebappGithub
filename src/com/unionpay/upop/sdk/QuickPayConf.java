package com.unionpay.upop.sdk;

/**
 * 名称：支付配置类
 * 功能：配置类
 * 类属性：公共类
 * 版本：1.0
 * 日期：2011-03-11
 * 作者：中国银联UPOP团队
 * 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */

public class QuickPayConf {

	// 版本号
	public final static String version = "1.0.0";

	// 编码方式
	public final static String charset = "UTF-8";

	// 基础网址（请按相应环境切换）

	/* 前台交易测试环境 */
	private final static String UPOP_BASE_URL = "http://58.246.226.99/UpopWeb/api/";

	/* 前台交易PM环境（准生产环境） */
	//private final static String UPOP_BASE_URL = "https://www.epay.lxdns.com/UpopWeb/api/";

	/* 前台交易生产环境 */
	//private final static String UPOP_BASE_URL = "https://unionpaysecure.com/api/";
	
	/* 后台交易测试环境 */
	private final static String UPOP_BSPAY_BASE_URL = "http://58.246.226.99/UpopWeb/api/";
	
	/* 后台交易PM环境（准生产环境） */
//	private final static String UPOP_BSPAY_BASE_URL = "https://www.epay.lxdns.com/UpopWeb/api/";
	
	/* 后台交易生产环境 */
	//private final static String UPOP_BSPAY_BASE_URL = "https://besvr.unionpaysecure.com/api/";
	
	/* 查询交易测试环境 */
	private final static String UPOP_QUERY_BASE_URL = "http://58.246.226.99/UpopWeb/api/";
	
	/* 查询交易PM环境（准生产环境） */
//	private final static String UPOP_QUERY_BASE_URL = "https://www.epay.lxdns.com/UpopWeb/api/";
	
	/* 查询交易生产环境 */
	//private final static String UPOP_QUERY_BASE_URL = "https://query.unionpaysecure.com/api/";

	// 支付网址
	public final static String gateWay = UPOP_BASE_URL + "Pay.action";

	// 后续交易网址
	public final static String backStagegateWay = UPOP_BSPAY_BASE_URL + "BSPay.action";

	// 查询网址
	public final static String queryUrl = UPOP_QUERY_BASE_URL + "Query.action";
	
	// 认证支付2.0网址
	public final static String authPayUrl = UPOP_BASE_URL + "AuthPay.action";
	
	// 发短信网址
	public final static String smsUrl = UPOP_BASE_URL + "Sms.action";

	// 商户代码
	public final static String merCode = "105550149170027";

	// 商户名称
	public final static String merName = "用户商城名称";

	public final static String merFrontEndUrl = "http://www.baidu.com";

	public final static String merBackEndUrl = "http://www.baidu.com";

	// 加密方式
	public final static String signType = "MD5";
	public final static String signType_SHA1withRSA = "SHA1withRSA";

	// 商城密匙，需要和银联商户网站上配置的一样
	public final static String securityKey = "88888888";

	// 签名
	public final static String signature = "signature";
	public final static String signMethod = "signMethod";

	//组装消费请求包
	public final static String[] reqVo = new String[]{
			"version",//协议版本
            "charset",//字符编码
            "transType",//交易类型
            "origQid",//原始交易流水号
            "merId",//商户代码
            "merAbbr",//商户简称
            "acqCode",//收单机构代码（仅收单机构接入需要填写）
            "merCode",//商户类别（收单机构接入需要填写）
            "commodityUrl",//商品URL
            "commodityName",//商品名称
            "commodityUnitPrice",//商品单价 单位：分
            "commodityQuantity",//商品数量
            "commodityDiscount",//折扣 单位：分
            "transferFee",//运费 单位：分
            "orderNumber",//订单号（需要商户自己生成）
            "orderAmount",//交易金额 单位：分
            "orderCurrency",//交易币种
            "orderTime",//交易时间
            "customerIp",//用户IP
            "customerName",//用户真实姓名
            "defaultPayType",//默认支付方式
            "defaultBankNumber",//默认银行编号
            "transTimeout",//交易超时时间
            "frontEndUrl",// 前台回调商户URL
            "backEndUrl",// 后台回调商户URL
            "merReserved"//商户保留域
	};

	public final static String[] notifyVo = new String[]{
            "charset",
            "cupReserved",
            "exchangeDate",
            "exchangeRate",
            "merAbbr",
            "merId",
            "orderAmount",
            "orderCurrency",
            "orderNumber",
            "qid",
            "respCode",
            "respMsg",
            "respTime",
            "settleAmount",
            "settleCurrency",
            "settleDate",
            "traceNumber",
            "traceTime",
            "transType",
            "version"
	};

	public final static String[] queryVo = new String[]{
		"version",
		"charset",
		"transType",
		"merId",
		"orderNumber",
		"orderTime",
		"merReserved"
	};
	
	public final static String[] smsVo = new String[]{
		"version",
		"charset",
		"acqCode",
		"merId",
		"merAbbr",
		"orderNumber",
		"orderAmount",
        "orderCurrency",
		"merReserved"
	};


}
