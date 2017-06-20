var BindDict=new Array();

function JumpTo(url){
	 window.location.href=url; 
}


function AddConvert(handler,convertor){
	if(convertor!=null){
		for(var i=0;i<convertor.length;i++){
			handler.AddConvert(convertor[i].name,convertor[i].func);
		}
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


function BindDataTo(templateName,data,outData=null,convertor=null,bindTo=null){

	var handler = CuminData.CreateInstance(templateName);
	if(convertor!=null){
		for(var key in convertor){
			handler.AddConvert(key,convertor[key]);
		}
	}
	if(bindTo==null){
		bindTo = templateName;
	}
	CuminData.CreateInstance(bindTo).Clear();
	handler.BindData(data,outData,bindTo);
}

function CuminUpdate(name,json,outData){
	CuminData.CreateInstance(name).UpdateCurrentNode(json,outData);
}
function CuminClear(name){
	CuminData.CreateInstance(name).ClearCurrentNode();
}
function CuminDelete(name){
	CuminData.CreateInstance(name).DeleteCurrentNode();
}
function BindClear(name){
	CuminData.CreateInstance(name).Clear();
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

function AjaxGet(url,data,succeed=null,failed=null){
	try{
		
		CuminAjax.CreateInstance().SetFailed(function(status,msg){
			if(Error!=null){

				Error(status,msg);
			}
			if(failed!=null)
			{
				failed(status,msg);
			}
		}).SetSucceed(function(status,msg){
			if(Error!=null){
				
				Error(status,msg);
			}
			if(succeed!=null)
			{
				succeed(status,msg);
			}
		}).UseGet(url).Submit(data);
		
	}catch(e){
		e.message="请检查地址和网络！";
		Error(e);
	}	
}

function AjaxPost(url,data,succeed=null,failed=null){
	var username = GetParameter("username");
	try{
		CuminAjax.CreateInstance().SetFailed(function(status,msg){
			if(Error!=null){
				Error(status,msg);
			}
			if(failed!=null)
			{
				failed(status,msg);
			}
		}).SetSucceed(function(status,msg){
			if(Error!=null){
				Error(status,msg);
			}
			if(succeed!=null)
			{
				succeed(status,msg);
			}
		}).UsePost(url).Submit(data);
		
	}catch(e){
		e.message="请检查地址和网络！";
		Error(e);
	}
}


function CloseBootstrapModal(name=null){
	if(name!=null){
		CuminSingleNode.CreateInstance(name).SetDisplay("none");
	}
	CuminMultiNode.CreateInstance(".modal-backdrop").Foreach(function(index,node){
		node.SetDisplay("none");
	})
}



