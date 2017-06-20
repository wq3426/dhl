var AjaxFilter=new Array();

var CuminAjax = {
	CreateInstance:function(){
		
		var handler={};
		handler.xmlhttp=null;
		
		if (window.XMLHttpRequest)
		{
			// code for IE7+, Firefox, Chrome, Opera, Safari
			handler.xmlhttp=new XMLHttpRequest();
		}
		else
		{
			// code for IE6, IE5
			handler.xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		handler.AddFilter=function(func){
			AjaxFilter[AjaxFilter.length]=func;
		}
		
		
		handler.xmlhttp.onreadystatechange=function()
		{
			var status=handler.xmlhttp.status;
			var json = null;
			try{
				
				json = JSON.parse(handler.xmlhttp.responseText);
				
			}catch(e){
				
				json = handler.xmlhttp.responseText;
			}
			
			if (handler.xmlhttp.readyState==4 && status==200)
			{
				for(var i=0; AjaxFilter.length;i++){
					AjaxFilter[i](status,json);
				}
				handler.Succeed(status,json);
				
			}else{
				for(var i=0; AjaxFilter.length;i++){
					AjaxFilter[i](status,json);
				}
				handler.Failed(status,json);
			}
		}
		handler.Headers=new Array();
		//添加请求头
		handler.AddHeader=function(key,value){
			this.Headers[key]=value;
			return this;
		}
		handler.Succeed=function(status,result){};
		handler.Failed=function(status,result){};
		//绑定成功事件
		handler.SetSucceed=function(func){
			this.Succeed=func;
			return this;
		}
		//绑定失败事件
		handler.SetFailed=function(func){
			this.Failed=func;
			return this;
		}
		//设置请求方法GET并设置URL
		handler.UseGet=function(url){
			this.RequestMethod = "GET";
			this.RequestUrl = url;
			return this;
		}
		//设置请求方法POST并设置URL
		handler.UsePost=function(url){
			this.RequestMethod = "POST";
			this.RequestUrl = url;
			return this;
		}
		//发送请求
		handler.Submit=function(data=null){
			if(this.RequestMethod=="GET"){
				if(data!=null){;
					this.RequestUrl+="?";
					for(var key in data){
						this.RequestUrl+=key+"="+data[key]+"&";
					}
					this.RequestUrl=this.RequestUrl.substring(0,this.RequestUrl.length-1);
				}
			}
			this.xmlhttp.open(this.RequestMethod,this.RequestUrl,false);
			this.xmlhttp.setRequestHeader("TimeStamp",new Date().getTime());
			for(var key in this.Headers)
			{
				this.xmlhttp.setRequestHeader(key,this.Headers[key]);
			}
			if(this.RequestMethod=="GET"){
				this.xmlhttp.setRequestHeader("Content-type","text/xml;charset=utf-8");				
				this.xmlhttp.send();
			}else{
				//以json的格式post
				this.xmlhttp.setRequestHeader("Content-type","application/json");
				if(typeof(data)!="string"){
					this.xmlhttp.send(JSON.stringify(data));
				}else{
					this.xmlhttp.send(data);
				}
			}	
			return this;
		}
		//发送请求
		handler.AsyncSubmit=function(data=null){
			if(this.RequestMethod=="GET"){
				if(data!=null){;
					this.RequestUrl+="?";
					for(var key in data){
						this.RequestUrl+=key+"="+data[key]+"&";
					}
					this.RequestUrl=this.RequestUrl.substring(0,this.RequestUrl.length-1);
				}
			}
			this.xmlhttp.open(this.RequestMethod,this.RequestUrl,true);
			this.xmlhttp.setRequestHeader("TimeStamp",new Date().getTime());
			for(var key in this.Headers)
			{
				this.xmlhttp.setRequestHeader(key,this.Headers[key]);
			}
			if(this.RequestMethod=="GET"){
				this.xmlhttp.setRequestHeader("Content-type","text/xml;charset=utf-8");				
				this.xmlhttp.send();
			}else{
				this.xmlhttp.setRequestHeader("Content-type","application/json");
				if(typeof(data)!="string"){
					this.xmlhttp.send(JSON.stringify(data));
				}else{
					this.xmlhttp.send(data);
				}
			}	
			return this;
		}
		
		return handler;
	}
}

