package com.souvi.task.ediTextTask.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.souvi.sysmanager.productManagement.entity.Product;
import com.souvi.sysmanager.productManagement.entity.ProductUpdate;

public class ediTextTaskUtil {
	public List<Product>  ediTextSplit(String[] str) {		
		Product Pro=new Product();
		List<Product> Prolist = new ArrayList<Product>();
		for(int i=0;i<str.length-1;i++){
			String[] strc=str[i+1].split("\\|", -1);
			//strlist.(str[i+1].split("\\|"));
			//strlist.set(i, str[i+1].split("\\|"));
//			        Pro.setProjectName(strlist.get(i)[1]) ;
//			        System.out.println(strlist.get(i)[1]);
//			        Prolist.get(i).setProjectName("");
					            Pro.setProjectName(strc[1]);
								Pro.setMaterial(strc[2]);
								Pro.setMaterialDescription(strc[3]);
								Pro.setMateralType(strc[4]);
								Pro.setProductName(strc[5]);
								Pro.setModel(strc[6]);
								Pro.setJanCode(strc[7]);
								Pro.setInplantFlag(strc[8]);
								Pro.setShippingCondition(strc[9]);
								Pro.setWeight(strc[10]);
								Pro.setSize(strc[11]);
								Pro.setTraceType(strc[12]);
								Pro.setCfdaStatecode(strc[13]);
								Pro.setValidManage(strc[14]);
								Pro.setValidManageDays(strc[15]);
								Pro.setIsitColdChainStorage(strc[16]);
								Pro.setStorageCondition(strc[17]);
								Pro.setValidityPeriod(strc[18]);
								Pro.setProductionCompanyLicense(strc[19]);
								Pro.setProductionLicenseHolder(strc[20]);
								Pro.setProductionCompany(strc[21]);
								Pro.setProductType(strc[22]);
								Pro.setRemark1(strc[23]);
								Pro.setRemark2(strc[24]);
								Pro.setRemark3(strc[25]);
								Pro.setRemark4(strc[26]);
								Pro.setRemark5(strc[27]);
								Pro.setRemark6(strc[28]);
								Pro.setRemark7(strc[29]);
								Pro.setRemark8(strc[30]);
								Pro.setRemark9(strc[31]);
								Pro.setRemark10(strc[32]); 	                                      	
			   					Prolist.add(Pro);
		}
		return Prolist;
			

	}
	public List<ProductUpdate>  ediTextSplitProductUpdate(String[] str){		
		ProductUpdate Pte = new ProductUpdate();
		List<ProductUpdate> ptelist=new ArrayList<ProductUpdate>();
		for(int i=0;i<=str.length;i++){
			String[] strc=str[i+1].split("\\|", -1);	
	         Pte.setFileName(strc[1]);
				Pte.setFilePath(strc[2]);
				Pte.setUpdateStatus(strc[3]);
				Pte.setProductId(strc[4]);
				Pte.setProjectId(strc[5]);
				Pte.setMaterial(strc[6]);
				Pte.setMaterialDescription(strc[7]);
				Pte.setMateralType(strc[8]);
				Pte.setProductName(strc[9]);
				Pte.setModel(strc[10]);
				Pte.setJanCode(strc[11]);
				Pte.setInplantFlag(strc[12]);
				Pte.setShippingCondition(strc[13]);
				Pte.setWeight(strc[14]);
				Pte.setSize(strc[15]);
				Pte.setTraceType(strc[16]);
				Pte.setCfdaStatecode(strc[17]);
				Pte.setValidManage(strc[18]);
				Pte.setValidManageDays(strc[19]);
				Pte.setIsitColdChainStorage(strc[20]);
				Pte.setStorageCondition(strc[21]);
				Pte.setValidityPeriod(strc[22]);
				Pte.setProductionCompanyLicense(strc[23]);
				Pte.setProductionLicenseHolder(strc[24]);
				Pte.setProductionCompany(strc[25]);
				Pte.setProductType(strc[26]);
				Pte.setAuditStatus(strc[27]);
				Pte.setDataStatus(strc[28]);
				Pte.setDataSources(strc[29]);
				Pte.setLockReason(strc[30]);
				Pte.setCreateBy(strc[31]);
				Pte.setLockReason(strc[32]);
				Pte.setLastUpdateBy(strc[33]);
				Pte.setLastUpdateDate(new Date(strc[34])); 
				Pte.setRemark1(strc[35]);
				Pte.setRemark2(strc[36]);
				Pte.setRemark3(strc[37]);
				Pte.setRemark4(strc[38]);
				Pte.setRemark5(strc[39]);
				Pte.setRemark6(strc[40]);
				Pte.setRemark7(strc[41]);
				Pte.setRemark8(strc[42]);
				Pte.setRemark9(strc[43]);
				Pte.setRemark10(strc[44]); 
				ptelist.add(Pte);
				
		}
		return ptelist;
	}
	
	public static void sortFiles(File[] files) {
		if(null==files) return;
		Arrays.sort(files,new FileComparator());
	}

}

