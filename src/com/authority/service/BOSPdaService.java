package com.authority.service;

import java.util.List;
import java.util.Map;

public interface BOSPdaService {

	String m_outitem_ac(String docno,String tiaom,String qty);
	
	String m_out_submit(String docno,String chayyy);
	
	String m_initem_ac(String docno,String tiaom,String qty);
	
	String m_in_submit(String docno,String chayyy);
	
}
