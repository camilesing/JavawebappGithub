package com.zjrc.meeting.common;

import org.apache.axis2.AxisFault;

import com.chinamobile.openmas.client.Mms;
import com.chinamobile.openmas.entity.MmsMessage;

/**
 * Title: MmsProvider.java<br>
 * Description: 彩信接口<br>
 * Copyright (c) 融创信息版权所有 2012	<br>
 * Create DateTime: Jun 6, 2012 10:04:01 AM <br>
 * @author ln
 */
public class MmsProvider {
	private static Mms MMS;
	public static final String APPLICATIONID = "GUOZIWEI";
	public static final String PASSWORD = "GUOZIWEI";
	private static final String WEBSERVICE_URL = "http://20.21.1.201:9090/OpenMasService";

	static {
		try {
			MMS = new Mms(WEBSERVICE_URL);
		} catch (AxisFault e) {
			// TODO 添加日志
			e.printStackTrace();
		}
	}

	/**
	 * 获取彩信接口
	 * @return
	 */
	public static Mms getMms() {
		if (MMS == null) {
			throw new RuntimeException("MMS init failure");
		}
		return MMS;
	}

	/**
	 * 获取彩信详情
	 * @param messageId
	 * @return
	 */
	public static MmsMessage getMmsMessage(String messageId) {
		return getMms().GetMessage(messageId);
	}
}