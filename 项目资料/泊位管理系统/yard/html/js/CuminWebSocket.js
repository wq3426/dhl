var CuminWebSocket = {
	CreateInstance:function(url){
		
		var handler={};
		
		handler.WebSocket=new WebSocket(url);
		handler.EOpen=function(evt){};
		handler.EClose=function(evt){};
		handler.EMessage=function(evt){};
		handler.EError=function(evt){};
		
		handler.WebSocket.onopen=function(evt){
			handler.EOpen(evt);
		}
		handler.WebSocket.onclose=function(evt){
			handler.EClose(evt);
		}
		handler.WebSocket.onmessage=function(evt){
			handler.EMessage(evt);
		}
		handler.WebSocket.onerror=function(evt){
			handler.EError(evt);
		}
		
		handler.GetSocektState=function(){
			return this.WebSocket.readyState;
		}
		
		handler.BindOpenEvent=function(func){
			this.EOpen = func;
			return this;
		}
		handler.BindCloseEvent=function(func){
			this.EClose = func;
			return this;
		}
		handler.BindMessageEvent=function(func){
			this.EMessage = func;
			return this;
		}
		handler.BindErrorEvent=function(func){
			this.EError = func;
			return this;
		}
		
		handler.SendHandler=null;
		
		handler.Queue=new Array();
		handler.Index=0;
		
		handler.Send=function(message){
			this.Queue[this.Queue.length]=message;
			if(this.SendHandler==null){
				
				this.PostMessage();
			}
		}
		
		handler.SendTime=200;
		handler.SetSendDelayTime=function(timsSpan){
			this.SendTime = timsSpan;
			return this;
		}
		
		handler.QueueLimit=5;
		handler.SetQueueLimit=function(count){
			this.QueueLimit=count;
			return this;
		}
		
		handler.PostMessage=function(){
			var tHandler = this;
			this.SendHandler = window.setInterval(function(){
				if(tHandler.WebSocket.readyState==1){
					if(tHandler.Queue.length>0)
					{
						console.log(tHandler.Queue[0]);
						tHandler.WebSocket.send(tHandler.Queue[0]);
						tHandler.Queue.shift();
					}else{
						tHandler.Index+=1;
						if(tHandler.Index==tHandler.QueueLimit){
							this.Index=0;
							window.clearInterval(tHandler.SendHandler);
							tHandler.SendHandler=null;
						}
					}
				}
				
			},this.SendTime);
		}

		
		handler.CloseHandler=null;
		
		handler.Close=function(){
			var tHandler = this;
			this.CloseHandler = window.setInterval(function(){
				if(tHandler.Queue.length==0)
				{
					tHandler.WebSocket.close();
					window.clearInterval(tHandler.SendHandler);
					window.clearInterval(tHandler.CloseHandler);
					tHandler.SendHandler=null;
					tHandler.CloseHandler=null;
				}
			},500);
		}
		return handler;
	}
}