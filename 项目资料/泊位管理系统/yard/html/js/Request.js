document.write("<script language='javascript' src='js/CuminAjax.js'></script>");
document.write("<script language='javascript' src='js/CuminGet.js'></script>");
document.write("<script language='JavaScript' src='js/CuminDialog.js'></script>");
document.write("<script language='JavaScript' src='js/CuminSingleNode.js'></script>");
document.write("<script language='JavaScript' src='js/CuminMultiNode.js'></script>");	
document.write("<script language='JavaScript' src='js/CuminData.js'></script>");

var BindDict=new Array();

function Init(data){
	if(data.hasOwnProperty("AnalysisModel")){
		if(data.AnalysisModel!=null){
			for(var i=0;i<data.AnalysisModel.length;i++){
				var json = data.AnalysisModel[i];
				AnalysisTemplate(json);
			}
		}
	}else{
		AnalysisTemplate(data);
	}
}
function AnalysisTemplate(json){
	if(json.BindFrom!=null){
			var bindType = json.BindType;
				if(bindType=="AJAX"){
					BindFromAjax(json,json.BindFrom);
				}else if(bindType=="NODE"){
					BindFromNode(json,json.BindFrom);
				}else if(bindType=="DATA"){
					BindFromData(json,json.BindFrom);
				}else if(bindType=="EVENT"){
					BindFromEvent(json,json.BindFrom)
				}
			}
			if(json.Functions!=null){
				for(var j=0;j<json.Functions.length;j++){
					BindFunction(CreateBind(json),json.Functions[j]);
				}
			}
}

function CreateBind(json){
	var handler;
	var name = json.Handler;
	if(BindDict.hasOwnProperty(name)){
		handler = BindDict[name];
	}else{
		handler = CuminData.CreateInstance(name);
		BindDict[name]=handler;
		if(json.hasOwnProperty("Convertor")){
			AddConvert(json.Convertor);
		}
		
	}
	return handler;
}

function BindFromAjax(json,bindNode){
	var node = CuminSingleNode.CreateInstance(bindNode.Source);
	var flag = "";
	var data = null;
	var handelr = CreateBind(json);
	if(bindNode.Flag!=null){
		flag = CuminSingleNode.CreateInstance(bindNode.Flag).GetInner();
	}
	if(bindNode.DataFrom!=null){
		data = GetNodeJson(bindNode.DataFrom);
	}
	if(bindNode.Method=="GET"){
		CuminAjax.CreateInstance().SetSucceed(function(get_data){
			handler.BindData(get_data,bindNode.OutLinkData,bindNode.BindTo);
		}).UseGet(bindNode.RequestUrl+flag).Submit();
	}else{
		CuminAjax.CreateInstance().SetSucceed(function(get_data){
			handler.BindData(get_data,bindNode.OutLinkData,bindNode.BindTo);
		}).UsePost(bindNode.RequestUrl+flag).Submit(data);
	}
	return handler;
}


function BindFromNode(json,bindNode){
	var handler = CreateBind(json);
	if(bindNode.HandlerKey==null)
	{
		handler.BindData(GetNodeJson(bindNode.HandlerKey),bindNode.OutLinkData,json.BindTo);
	}else{
		var bindFrom = CreateBind(bindNode);
		bindFrom.BindClick(bindNode.HandlerKey,function(flag,data){
			handler.BindData(data,bindNode.OutLinkData,bindNode.BindTo);
		});
	}
}


function BindFromData(json,bindNode){
	var handler = CreateBind(json);
	handler.BindData(bindNode.Data,bindNode.OutLinkData,bindNode.BindTo);
}


function BindFromEvent(json,bindNode){
	var node = CreateBind(json);
	var handler = CreateBind(bindNode);
	CuminData.CreateInstance(json.Handler)
	node.BindClick(bindNode.HandlerKey,GetBindFunction(handler,bindNode));
}

function GetBindFunction(handler,json){
	if(json.Method=="GET"){
		if(json.SetFlag){
			return function(flag,data){
				CuminData.CreateInstance(json.BindTo).Clear();
				Get(json.RequestUrl+flag,function(get_data){
					if(template.error!=null){
						template.error(msg);
					}
					handler.BindData(get_data,json.OutLinkData,json.BindTo);
				});
			}
		}else{
			return function(flag,data){
				CuminData.CreateInstance(json.BindTo).Clear();
				Get(json.RequestUrl,function(get_data){
					if(template.error!=null){
						template.error(msg);
					}
					handler.BindData(get_data,json.OutLinkData,json.BindTo);
				});
			}
		}
	}else{
		if(json.SetFlag){
			return function(flag,data){
				CuminData.CreateInstance(json.BindTo).Clear();
				Post(json.RequestUrl+flag,data,function(get_data){
					if(template.error!=null){
						template.error(msg);
					}
					handler.BindData(get_data,json.OutLinkData,json.BindTo);
				});
			}
		}else{
			return function(flag,data){
				CuminData.CreateInstance(json.BindTo).Clear();
				Post(json.RequestUrl,data,function(get_data){
					if(template.error!=null){
						template.error(msg);
					}
					handler.BindData(get_data,json.OutLinkData,json.BindTo);	
				});
				handler.BindData(data,json.OutLinkData,json.BindTo);
			}
		}
	}
}


function BindFunction(handler,json){
	if(json.IsDirect){
		handler.BindClick(json.ButtonName,GetRequestFunction(json));
	}else{
		var node = CuminSingleNode.CreateInstance(json.ButtonName).SingleClick(GetSingelFunction(json));
	}
}

function GetRequestFunction(json){
	if(json.Method=="GET"){
		if(json.SetFlag){
			return function(flag,data){
				Get(json.RequestUrl+flag,function(get_data){
					if(template.error!=null){
						template.error(msg);
					}
				});
			};
		}else{
			return function(flag,data){
				Get(json.RequestUrl,function(get_data){
					if(template.error!=null){
						template.error(msg);
					}
				});
			};
		}
	}else{
		if(json.SetFlag){
			return function(flag,data){
				Post(json.RequestUrl+flag,data,function(get_data){
					if(template.error!=null){
						template.error(msg);
					}
				});
			}
		}else{
			return function(flag,data){
				Post(json.RequestUrl,data,function(get_data){
					if(template.error!=null){
						template.error(msg);
					}
				});
			}
		}
	}
}

function GetSingelFunction(json){
	if(json.Method=="GET"){
		if(json.SetFlag){
			return function(){
				Get(json.RequestUrl,function(get_data){
					if(template.error!=null){
						template.error(msg);
					}
				});
			}
		}else{
			return function(){
				Get(json.RequestUrl,function(get_data){
					if(template.error!=null){
						template.error(msg);
					}
				});
			}
		}
	}else{
		if(json.SetFlag){
			return function(){
				Post(json.RequestUrl,GetNodeJson(json.DataFrom),function(get_data){
					if(template.error!=null){
						template.error(msg);
					}
				});
			}
		}else{
			return function(){
				Post(json.RequestUrl,GetNodeJson(json.DataFrom),function(get_data){
					if(template.error!=null){
						template.error(msg);
					}
				});
			}
		}
	}
}

function AddConvert(handler,convertor){
	if(convertor!=null){
		for(var i=0;i<convertor.length;i++){
			handler.AddConvert(convertor[i].name,convertor[i].func);
		}
	}
}

function Get(url,func){
	try{
		
		CuminAjax.CreateInstance().SetSucceed(func).UseGet(url).Submit();
		
	}catch(e){
		
		template.error(e);
	}	
}
	
function Post(url,data,func){
	var username = GetParameter("username");
	try{
		CuminAjax.CreateInstance().SetSucceed(func).UsePost(url).Submit(data);
		
	}catch(e){
		
		template.error(e);
	}
}

function GetParameter(key){
	return CuminGet.CreateInstance().Get(key);
}

function CoverBind(node,dialogName){
			
	if(typeof(node)=="string")
	{
		CuminDialog.CreateInstance(node).BindDom(dialogName);
					
	}else{
				
		CuminDialog.CreateInstance(node.Root).BindDom(dialogName);
	}
}


function GetContent(nodeName){
	var handler = CuminSingleNode.CreateInstance(nodeName);
	if(handler!=null){
		return handler.GetInner();
	}
	return "";
}


function BindDataTo(templateName,data,bindTo,outData=null,convertor=null){

	var handelr = CuminData.CreateInstance(templateName);
	if(convertor!=null){
		for(var key in convertor){
			handelr.AddConvert(key,convertor[key]);
		}
	}
	if(bindTo==null){
		bindTo = templateName;
	}
	CuminData.CreateInstance(bindTo).Clear();
	handelr.BindData(data,outData,bindTo);
}

function BindFunction(templateName,templateKey,func){
	var handler = CuminData.CreateInstance(templateName);
	if(handler.Model==null){
		handler = CuminSingleNode.CreateInstance(templateName);
		if(templateKey!=null){
			var tempNode = CuminSingleNode.CreateInstance(templateKey);
			tempNode.Tag=handler;
			handler.SingleClick(function(){
				func(null,GetNodeJson(templateKey),tempNode);
			})
		}else{
			handler.SingleClick(func);
		}
		
	}else{
		handler.BindClick(templateKey,func);
	}
}

function AjaxGet(url,succeed,failed){
	try{
		
		CuminAjax.CreateInstance().SetFailed(function(msg){
			if(template.error!=null){
				template.error(msg);
			}
			if(failed!=null)
			{
				failed(msg);
			}
		}).SetSucceed(function(msg){
			if(template.error!=null){
				template.error(msg);
			}
			if(succeed!=null)
			{
				succeed(msg);
			}
		}).UseGet(url).Submit();
		
	}catch(e){
		
		template.error(e);
	}	
}

function AjaxPost(url,data,succeed,failed){
	var username = GetParameter("username");
	try{
		CuminAjax.CreateInstance().SetFailed(function(msg){
			if(template.error!=null){
				template.error(msg);
			}
			if(failed!=null)
			{
				failed(msg);
			}
		}).SetSucceed(function(msg){
			if(template.error!=null){
				template.error(msg);
			}
			if(succeed!=null)
			{
				succeed(msg);
			}
		}).UsePost(url).Submit(data);
		
	}catch(e){
		
		template.error(e);
	}
}






