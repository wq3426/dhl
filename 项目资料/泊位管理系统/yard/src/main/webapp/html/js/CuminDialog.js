var CuminDialog = {
	CreateInstance:function(nodeName){
		
		var handler = {};
		
		handler.Root = CuminSingleNode.CreateInstance(nodeName);
		
		handler.BindDom=function(parameterName){
						
			var showNode = CuminSingleNode.CreateInstance(parameterName);
			
			var coverNode = CuminSingleNode.CreateInstance("#cumin-dialog-cover").UseMultiClick();
			if(coverNode.Root.MultiEvents.length==0){
				coverNode.AddClick(function(){
					coverNode.Show(false);
				});	
			}
			
			var closeButtons = CuminMultiNode.CreateInstance(parameterName +" [cumin-dialog-close]");
			this.Root.UseMultiClick();
			this.Root.AddClick(function(){
					if(coverNode!=null){
						coverNode.Show(true);
						coverNode.AddClick(function(){
							showNode.Index(0).Show(false);
						});
					}
					showNode.Index(100).Show(true);
			});
			closeButtons.Foreach(function(i,node){
				node.UseMultiClick();
				node.AddClick(function(){
					coverNode.Show(false);
					showNode.Index(0).Show(false);
				});
			});
			return this;
		}

		
		
		return handler;
	}
}