
var CuminSingleNode = {
	
	CreateInstance:function(node){
		
		//初始化
		var handler = {};		
		
		if(node==null){return null;}
		
		if(typeof(node)=="string"){
			handler.Root = document.querySelector(node);
		}else{
			handler.Root = node;
		}
		
		if(handler.Root==null){
			return null;
		}
		handler.Tag=null;
		handler.AppendIn=function(nodeInfo){
			var node = null;
			if(typeof(nodeInfo)=="string"){
				node = CuminSingleNode.CreateInstance(nodeInfo);
			}else{
				node = nodeInfo;
			}
			node.AppendNode(this.Root);
			return this;
		}
		
		handler.IsInput = false;
		handler.IsRadioBox=false;
		handler.IsCheckBox=false;
		handler.IsSelect = false;
		handler.IsOption = false;
		if(handler.Root.tagName=="INPUT"){
			handler.IsInput = true;
			var inputType = handler.Root.getAttribute("type").toLowerCase();
			if(inputType=="checkbox"){
				handler.IsCheckBox=true;
			}else if(inputType=="radio"){
				handler.IsRadioBox=true;
			}
		}
		if(handler.Root.tagName=="SELECT"){
			handler.IsSelect = true;
		}
		if(handler.Root.tagName=="OPTION"){
			handler.IsOption = true;
		}
		
		//增加当前节点
		handler.AppendNode=function(nodeInfo,nodeStyle=null){
			
			var newNode = null;
			if(typeof(nodeInfo)=="string"){
				newNode = document.createElement(nodeInfo);
			}else{
				newNode = nodeInfo;
			}
			
			if(nodeStyle!=null){
				newNode.setAttribute("class", nodeStyle);
			}
			this.Root.appendChild(newNode);
			return CuminSingleNode.CreateInstance(newNode);
		}
		
		
		//删除当前节点
		handler.Delete=function(){
			if(this.Root.onclick!=null){
				this.Root.onclick = null;
				this.Root.MultiEvents=null;
			}
			if(this.Root.parentNode!=null){
				this.Root.parentNode.removeChild(this.Root);
			}
			return this;
		}

		//获取子节点
		handler.Children=function(childName){
			return document.querySelectorAll("#"+show+' .childName');
		}
		
		//克隆节点
		handler.Clone=function(){
			var node = this.Root.cloneNode(true);
			return CuminSingleNode.CreateInstance(node);
		}
		
		//单击事件
		handler.SingleClick=function(func){
			this.Root.MultiEvents=new Array();
			this.Root.onclick=null;
			this.Root.onclick=func;
		}
		//变化事件
		handler.Changed=function(func){
			if(this.IsSelect){
				this.Root.onchanged=null;
				this.Root.onchanged=function(){func(handler);};
			}else if(this.IsCheckBox || this.IsRadioBox){
				this.Root.onclick=null;
				this.Root.onclick=function(){func(handler);};
			}
		}
		
		//定义多路事件
		if(handler.Root.MultiEvents==null){
			handler.Root.MultiEvents = new Array();
		}
		//使用多路事件
		handler.UseMultiClick=function(){
			if(this.Root.MultiEvents.length==0){
				this.Root.onclick=null;
				this.Root.onclick=function(){
					var eventArray = handler.Root.MultiEvents;
					for(var i=0;i<eventArray.length;i++)
					{
						eventArray[i]();
					}
				}
			}
			return this;
		}
		//添加多路事件
		handler.AddClick=function(nodeFunc){
			this.Root.MultiEvents[this.Root.MultiEvents.length]=nodeFunc;
			return this;
		}
		//选中事件
		handler.Checked=function(selected){
			if(this.IsCheckBox || this.IsRadioBox){
				this.Root.checked=selected;
			}
		}
		//Select文本显示
		handler.SelectText=function(text){
			if(this.IsSelect){
				this.Root.Text=text;
			}
		}
		
		
		//-------------------------设置样式-------------------------------
		handler.Show=function(isShow){
			var value = this.GetDisplay();
			var sourceValue = this.GetAttribute("sourcedisplay");
			if(isShow){
				if(value=="none"){
					if(sourceValue==null){
						sourceValue="block";
					}
					this.RemoveAttribute("sourcedisplay");
					this.SetDisplay(sourceValue);
				}
				
			}else{
				if(value!="none"){
					if(sourceValue==null){
						sourceValue="none";
					}
					this.SetAttribute("sourcedisplay",value);
					this.SetDisplay(sourceValue);
				}
			}
			return this;
		}
		handler.SetIndex=function(index){
			this.Root.style.zIndex = index;
			return this;
		}
		handler.GetIndex=function(index){
			return this.Root.style.zIndex;
		}
		handler.SetInner=function(text){
			if(this.IsInput){
				this.Root.value=text;
			}else{
				this.Root.innerHTML=text;
			}
			return this;
		}
		handler.GetInner =function(){
			if(this.IsSelect || this.IsInput){
				return this.Root.value;
			}else{
				return this.Root.innerHTML;
			}
		}
		handler.SetCssText=function(text){
			this.Root.style.cssText=text;
			return this;
		}
		handler.GetCssText=function(text){
			return this.Root.style.cssText;
		}
		handler.Css=function(css){
			this.Root.setAttribute("class", css);
			return this;
		}
		handler.SetColor=function(color){
			this.Root.style.color=color;
			return this;
		}
		handler.GetColor=function(){
			return this.Root.style.color;
		}
		handler.SetBackColor=function(color){
			this.Root.style.background=color;
			return this;
		}
		handler.GetBackColor=function(color){
			return this.Root.style.background;
		}
		handler.SetBackImage=function(style){
			this.Root.style.backgroundImage=style;
		}
		handler.GetBackImage=function(){
			return this.Root.style.backgroundImage;
		}
		
		handler.SetValue=function(data){
			this.Root.value=data;
			return this;
		}
		handler.GetValue=function(data){
			return this.Root.value;
		}
		handler.SetId=function(idName){
			this.Root.id=idName;
			return this;
		}
		handler.GetId=function(idName){
			return this.Root.id;
		}
		
		handler.SetDisplay=function(value){
			this.Root.style.display=value;
			return this;
		}
		handler.GetDisplay=function(){
			var tempDisplay="display";
			if (this.Root.currentStyle) //IE
			{
			  return this.Root.currentStyle[tempDisplay];
			}
			else if (window.getComputedStyle)//非IE
			{
			  var propprop = tempDisplay.replace(/([A-Z])/g, "-$1");
			  propprop = tempDisplay.toLowerCase();
			  return document.defaultView.getComputedStyle(this.Root,null)[propprop];
			}
		}
		
		//---------------------设置属性--------------------
		handler.SetAttribute=function(attrName,value){
			this.Root.setAttribute(attrName,value);
			return this;
		}
		//---------------------获取属性--------------------
		handler.GetAttribute=function(attrName){
			return this.Root.getAttribute(attrName);
		}
		//---------------------删除属性--------------------
		handler.RemoveAttribute=function(attrName){
			this.Root.removeAttribute(attrName);
			return this;
		}
		
		//------------------Html5-------------------
		handler.GetContext=function(type){
			var context =  this.Root.getContext(type);
			context.globalCompositeOperation='destination-over';
			var devicePixelRatio = window.devicePixelRatio || 1;
			var backingStoreRatio = context.webkitBackingStorePixelRatio ||
									context.mozBackingStorePixelRatio ||
									context.msBackingStorePixelRatio ||
									context.oBackingStorePixelRatio ||
									context.backingStorePixelRatio || 1;
			var ratio = devicePixelRatio / backingStoreRatio;
						
			var oldWidth = parseInt(window.getComputedStyle(this.Root).width);
			this.Root.width = oldWidth * ratio;
			this.Root.style.width = oldWidth + 'px';
			return context;
		}

		return handler;
	}
}