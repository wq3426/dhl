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
		handler.xmlhttp.onreadystatechange=function()
		{
			if (handler.xmlhttp.readyState==4 && handler.xmlhttp.status==200)
			{
				handler.Succeed(handler.xmlhttp.responseText);
				
			}else{
				handler.Failed(handler.xmlhttp.responseText);
			}
		}
		handler.Headers=new Array();
		//添加请求头
		handler.AddHeader=function(key,value){
			this.Headers[key]=value;
			return this;
		}
		handler.Succeed=function(result){};
		handler.Failed=function(result){};
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
		handler.Submit=function(data="{}"){
			this.xmlhttp.open(this.RequestMethod,this.RequestUrl,false);
			this.xmlhttp.setRequestHeader("TimeStamp",new Date().getTime());
			for(var key in this.Headers)
			{
				this.xmlhttp.setRequestHeader(key,this.Headers[key]);
			}
			if(this.RequestMethod=="GET"){
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
			this.Result = this.xmlhttp.responseText;
			return this;
		
		}
		//发送请求
		handler.AsyncSubmit=function(data="{}"){
			this.xmlhttp.open(this.RequestMethod,this.RequestUrl,true);
			this.xmlhttp.setRequestHeader("TimeStamp",new Date().getTime());
			for(var key in this.Headers)
			{
				this.xmlhttp.setRequestHeader(key,this.Headers[key]);
			}
			if(this.RequestMethod=="GET"){
				
				this.xmlhttp.send();
			}else{
				
				this.xmlhttp.send(data);
			}	
			return this;
		
		}
		
		return handler;
	}
}

