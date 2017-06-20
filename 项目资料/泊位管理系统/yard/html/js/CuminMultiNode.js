var CuminMultiNode = {
	CreateInstance:function(nodes){
		//初始化
		var handler = {};
		handler.Roots =null;
		if(typeof(nodes)=="string"){
			handler.Roots = document.querySelectorAll(nodes);
		}else{
			handler.Roots = nodes;
		}
		
		//选择单一元素
		handler.Select=function(index){
			if(this.Roots.length>index){
				return CuminSingleNode.CreateInstance(this.Roots[index]);
			}
			return null;
		}
		//遍历处理元素
		handler.Foreach=function(func){
			for(var i=0;i<this.Roots.length;i++){
				if(this.Roots[i].CuminIndex==null){
					this.Roots[i].CuminIndex = i;
				}	
				func(i,CuminSingleNode.CreateInstance(this.Roots[i]));				
			}
			
		}
		//去除元素
        handler.Remove = function (cuminMultiNodes) {
            if (cuminMultiNodes == null) { return;}
            var nodes = cuminMultiNodes.Roots;
            var nodeArray = new Array();
            for (var i = 0; i < this.Roots.length; i++)
            {
                var node = this.Roots[i];
                for (var j = 0; j < nodes.length; j++) {
                    if (this.Roots[i] == nodes[j]) {
                        node = null;
                        break;
                    }
                }
                if (node != null) {
                    nodeArray[nodeArray.length] = node;
                }
            }
            handler.Roots = nodeArray;
        }
		//-------------------------样式-------------------------------
		handler.Show=function(isShow){
			if(isShow){
				this.Root.style.display="block";
			}else{
				this.Root.style.display="none";
			}
			return this;
		}
		handler.Index=function(index){
			this.Root.style.zIndex = index;
			return this;
		}
		handler.Inner=function(text){
			this.Root.innerHTML=text;
			return this;
		}
		handler.CssText=function(text){
			this.Root.style.cssText=text;
			return this;
		}
		return handler;
	}
}