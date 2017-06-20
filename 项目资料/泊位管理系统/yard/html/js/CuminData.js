var CuminDataDict=new Array();

var CuminData = {
	CreateInstance:function(nodeName,index=0){
		
		if(CuminDataDict.hasOwnProperty(nodeName)){
			return CuminDataDict[nodeName];
		}
		
		var handler = {};
		
		handler.InstanceString = nodeName;	
		handler.Root = CuminSingleNode.CreateInstance(handler.InstanceString);
        handler.RealRoot = null;
        handler.Index = 0;
        if (handler.Root == null) {
            return handler;
        } else {
			CuminDataDict[nodeName]=handler;
            handler.RealRoot = handler.Root.Root;
            Index = index;
        }
		
		var RootElement = handler.Root.Root;
		
		if(RootElement.BindEvents==null){
			
			RootElement.BindEvents=new Array();
		}
		if(RootElement.CloseStates==null){
			
			RootElement.CloseStates=new Array();
		}
		
		//模板标签
		handler.Header="header";
		handler.Template="template";
		handler.Display="SourceDisplay";
		handler.Content="field";
		handler.Unique="unique";
		handler.SelectValue ="cuminValue";
		handler.SelectText = "cuminText";
		handler.IsSelect=false;
		handler.Operator="operator";
		handler.OutLinkValue="out";
		handler.NeedJson="json";
        handler.SpliteChar = ";";
        handler.Flag = "CuminFilter";
		handler.Query="query";
		
		//设置拼接
		handler.SetSpliteChar=function(spliteChar){
			this.SpliteChar=spliteChar;
			var result = handler.OutValues=handler.Root.GetAttribute(this.OutLinkValue);
			handler.OutValues = result.split(spliteChar);
		}
		
		//获取保留模板
		handler.Model = CuminSingleNode.CreateInstance(handler.InstanceString+" ["+handler.Template+"]");
		if(handler.Model!=null){
			handler.ModelFlag = handler.Model.GetAttribute(handler.Template);
		}
		
		
		//如果是SELECT
		if(handler.Root.IsSelect){
			handler.IsSelect=true;
		}else{
			handler.IsSelect=false;	
        }

        //*****************转换器*******************
        if (handler.Root.Root.Converts == null) {
            handler.Root.Root.Converts = new Array();
        }

        handler.AddConvert = function (key, func) {
            this.Root.Root.Converts[key] = func;
			return this;
        }
        //*****************************************

		
		
		
		
		
		
        //绑定数据
        handler.BindData = function (data, outData = null, to = null) {
            this.RealRoot.BindData = data;
            this.RealRoot.OutLinkValue = outData;
            if (to == null) {
                to = this.InstanceString;
            }
            this.Clear();
            this.ModelDisplay = this.Model.GetDisplay();

            if (data instanceof Array) {
                if (this.IsSelect) {

                    this.Private_BindEvent(this.Root, this.Root);
                    for (var i = 0; i < data.length; i++) {
                        this.SelectBind(data[i], to);
                    }

                } else {
                    for (var i = 0; i < data.length; i++) {
                        this.NormalBind(data[i], outData, to);
                    }
                }
            } else {
                if (this.IsSelect) {
                    this.Private_BindEvent(this.Root, this.Root);
                    this.SelectBind(data, to);

                } else {
                    this.NormalBind(data, outData, to);
                }
            }
            this.Model.SetDisplay("none");
            this.Model.SetAttribute(this.Display, this.ModelDisplay);
            return this;
        }
		
		 //绑定正常节点
        handler.NormalBind = function (data, outData = null, to = null) {
            var realData = this.Private_GetData(data);
            var cloneNode = this.Model.Clone();
            this.Private_SetNormalNode(cloneNode, realData, to);
            var recoder = to + " [" + this.Flag + "=\"" + this.Index + "\"] [" + this.Content + "]";                                //Show:"#Dialog [CuminFlag=1] [field]"
			var outlink = to + " [" + this.Flag + "=\"" + this.Index + "\"] [" + this.OutLinkValue + "]";    						//Show:"#Dialog [CuminFlag=1] [out]"
            var remove = to + " [" + this.Flag + "=\"" + this.Index + "\"] [" + this.OutLinkValue + "] [" + this.Content + "]";     //Show:"#Dialog [CuminFlag=1] [out] [field]"
			
            var nodes = CuminMultiNode.CreateInstance(recoder); 
			var removeNodes = CuminMultiNode.CreateInstance(remove);
			var outNodes = CuminMultiNode.CreateInstance(outlink);
			
            nodes.Remove(removeNodes);
			nodes.Remove(outNodes);
			
			var outLinkKey = this.Root.GetAttribute(this.OutLinkValue);
			var outValues = null;
			if(outLinkKey!=null){
				outValues = outLinkKey.split(this.SpliteChar);
			}
            nodes.Foreach(function (index, node) {
                handler.SetUnique(node, data);
                var key = handler.Private_SetContent(node, data);
				handler.Private_BindEvent(handler.Root, node);
				if(outLinkKey!=null){
					if(key==handler.ModelFlag){
						handler.Private_ShowOut(outValues, data, node);
					}
				}
                
            });
			
            if (outData!=null){
                outNodes.Foreach(function (index, node) {
                    var key = handler.Private_SetOutlink(node, data);
					if(outData.hasOwnProperty(key)){
						CuminData.CreateInstance(to + " [" + handler.Flag + "=\"" + handler.Index + "\"] ["+handler.Content+"=\""+key+"\"]",handler.Index).BindData(outData[key], outData);
					}
                });
            }
			var HeadData = data[this.ModelFlag];
            this.Private_BindOperator(to + " [" + this.Flag + "=\"" + this.Index + "\"]",HeadData,cloneNode);
        }

		 //显示已有元素
        handler.Private_ShowOut = function (values, data, node) {
				if (node.IsCheckBox || node.IsRadioBox) {
					if (values != null) {
						var nodeData = node.GetInner();
						for (var j = 0; j < values.length; j++) {
							if (nodeData == values[j]) {
								node.Checked(true);
							}
						}
					}
				}
            
        }
        //获取数组数据或者单个数据
        handler.Private_GetData = function (flag) {
            if (typeof (flag) == "int") {
                return this.RealRoot.BindData[flag];
            } else {
                return flag;
            }
        }




        //绑定SELECT
        handler.SelectBind = function(data, to=null) {
            var realData = this.Private_GetData(data);
            var cloneNode = this.Model.Clone();
            this.Private_SetSelectNode(cloneNode, realData, to);
            this.Private_SetSelectStatus(cloneNode, realData);
            return cloneNode;
        }
        //设置Select节点
        handler.Private_SetSelectNode = function (clone, data, to) {
           
            this.SetUnique(clone, data);	
            var tempData = data[this.ModelFlag];
            var valueKey = clone.GetAttribute(this.Content);        //Get   :   "field"
            var textKey = clone.GetAttribute(this.SelectText);      //Get   :   "cuminText"

            clone.RemoveAttribute(this.SelectText);                 //Remove:   "cuminText" 
            clone.RemoveAttribute(this.Template);                   //Remove:   "template"

            clone.SetAttribute(this.Header, tempData);              //Set   :   "header"
            clone.Root.value =  data[valueKey];  					//Set   :   "value"
            clone.Root.text = this.Data(textKey, data, clone);   	//Set   :   "text"
            this.Priavate_SetUniqueFlag(clone);                     //Set   :   "CuminFilter"
			clone.AppendIn(to);
        }
        //设置Select选中状态
        handler.Private_SetSelectStatus = function (clone,data) {
            var outValue = this.Root.GetAttribute(this.OutLinkValue);
            if (outValue != null) {
                if (outValue == data[this.ModelFlag]) {
                    clone.Root.selected = true;
                }
            }
        }



        //设置内容
        handler.Private_SetContent = function (clone,data) {
            var key = clone.GetAttribute(this.Content);
            clone.SetInner(this.Data(key, data, clone));
            return key;
        }
        //设置外联
        handler.Private_SetOutlink = function (clone, data) {
            var key = clone.GetAttribute(this.Content);
            clone.SetAttribute(this.OutLinkValue, data[key]);
            return key;
        }
        //绑定事件
        handler.Private_BindEvent = function (fNode,cNode) {
            if (cNode.IsSelect || cNode.IsRadioBox) {
                cNode.Changed(function (pNode) {
                    fNode.SetAttribute(handler.OutLinkValue, pNode.GetInner());
                });
            } else if (cNode.IsCheckBox) {
                cNode.Changed(function (pNode) {
                    if (pNode.Root.checked) {
                        var tempOutValue = fNode.GetAttribute(handler.OutLinkValue);
                        if (tempOutValue == null || tempOutValue == "undefined") {
                            tempOutValue = pNode.GetInner() + handler.SpliteChar;
                        } else {
                            tempOutValue = tempOutValue + pNode.GetInner() + handler.SpliteChar;
                        }
                        pNode.SetAttribute(handler.OutLinkValue, tempOutValue);
                    } else 
					{
                        var tempOutValue = fNode.GetAttribute(handler.OutLinkValue).replace(cNode.GetInner() + handler.SpliteChar, "");
                        if (tempOutValue != null && tempOutValue != "undefined") {
                            fNode.SetAttribute(handler.OutLinkValue, tempOutValue);
                        }
                    }
                });
            }
        }
       
        //绑定按钮事件
        handler.Private_BindOperator = function (unique,flag,clone) {
            var operatorNodes = CuminMultiNode.CreateInstance(unique+" [" + this.Operator + "]");
            operatorNodes.Foreach(function (i, node) {
                node.SetAttribute(handler.Operator, handler.Index);
				node.SetAttribute(handler.Query, flag);
				node.Tag = clone;
            });
        }

        
       
        //设置Normal节点
        handler.Private_SetNormalNode = function (clone, data, to) {
            this.SetUnique(clone, data);
            var tempData = data[this.ModelFlag];
            var valueKey = clone.GetAttribute(this.Content);    //Get   :   "field"
            clone.SetAttribute(this.Header, tempData);          //Set   :   "header"
            clone.RemoveAttribute(this.Template);               //Remove:   "template"
            this.Priavate_SetUniqueFlag(clone);                 //Set   :   "CuminFilter"
            clone.AppendIn(to);
        }

        handler.Priavate_SetUniqueFlag = function (clone) {
            this.Index += 1;
            clone.SetAttribute(this.Flag, this.Index);          //Set   :   "CuminFilter"
        }

		//添加数据
		handler.AddData=function(data,outData=null,farther=null){
			if(farther==null){
				farther = this.InstanceString;
			}
			var src = this.Model.GetAttribute(this.Display);
			this.Model.SetDisplay(src);
			var flag ="";
			if(this.IsSelect){
				this.FillSelectCuminData(data,farther);
			}else{
				flag=this.FillModelCuminData(data,outData,farther);
				this.BindSingleNode(flag);
			}
			
			this.Model.SetDisplay("none");
			this.Model.SetAttribute(this.Display,this.ModelDisplay);
		}
		
		handler.BindSingleNode=function(uniqueFlag){
			var operatorNodes = CuminMultiNode.CreateInstance(uniqueFlag+" ["+this.Operator+"]");
			operatorNodes.Foreach(function(index,tNode){
				var name= tNode.GetInner();
				var RootElement = handler.Root.Root;
				if(RootElement.BindEvents.hasOwnProperty(name)){
					tNode.SingleClick(function(){
							var flag = tNode.GetAttribute(handler.Operator);
							var tData = handler.GetJson(flag);
							RootElement.BindEvents[name](flag,tData);
							if(RootElement.CloseStates[name]){
								var nodes = CuminMultiNode.CreateInstance(handler.InstanceString+" ["+handler.Header+"=\""+flag+"\"] [json]");
								nodes.Foreach(function(i,dataNode){
									if(!dataNode.IsSelect && !dataNode.IsCheckBox && dataNode.Root.tagName!="TBODY" && !dataNode.IsRadioBox){
										dataNode.SetInner("");
									}
								});
							}
					});
				}
			});
		}
	
	
		handler.Data=function(key,data,node){
			var result = data[key];
			if(this.Root.Root.Converts.hasOwnProperty(key)){
				
				result = this.Root.Root.Converts[key](result,node);
			}
			return result;
		}
	

		
		handler.DataValue=null;
		handler.SetDataValue=function(value){
			this.DataValue= value;
			return this;
		}
		
		handler.GetJson=function(flag){
			var result = {};
			if(this.IsSelect){
				var value = this.Model.GetAttribute(this.SelectValue);
					var text = this.Model.GetAttribute(this.SelectText);
					var tNode= this.Root.Root.options;
					for(var i=0;i<tNode.length;i++){
						var headerName = tNode[i].getAttribute(this.Header);
						if(headerName===flag){
							result[value]=tNode[i].value;
							result[text]=tNode[i].innerHTML;
						}
					}
				return result;
			}else{
				result = GetNodeJson(nodeName+" ["+this.Flag+"=\""+flag+"\" ]"); 
			}
			return result; 
		}
		
		
		
		handler.Clear=function(){
			if(this.Model!=null){
				var src = this.Model.GetAttribute(this.Display);
				this.Model.SetDisplay(src);
			}
			
			var nodes = CuminMultiNode.CreateInstance(this.InstanceString+" ["+this.Header+"]");
			nodes.Foreach(function(i,dataNode){
				dataNode.Root.NotifyData=null;
				dataNode.Root.MultiEvents=null;
				dataNode.Root.BindEvents=null;
				dataNode.Root.CloseStates=null;
				dataNode.Delete();
			});
			return this;
		}
		
		
        handler.BindClick = function (name, func, IsClear = false) {
            var RootElement = this.RealRoot;
			RootElement.BindEvents[name]=func;
			RootElement.CloseStates[name]=IsClear;
			var operatorNodes = CuminMultiNode.CreateInstance(this.InstanceString+" ["+this.Header+"] ["+this.Operator+"]");
			operatorNodes.Foreach(function(index,tNode){
				if(tNode.GetInner()==name){
					tNode.UseMultiClick();
                    tNode.AddClick(function () {
                        var flag = tNode.GetAttribute(handler.Operator);
						var head = tNode.GetAttribute(handler.Query);
						var tData = handler.GetJson(flag);
						func(head,tData,tNode);
						if(IsClear){
							var nodes = CuminMultiNode.CreateInstance(handler.InstanceString+" ["+handler.Flag+"=\""+flag+"\"] [json]");
							nodes.Foreach(function(i,dataNode){
								if(!dataNode.IsSelect && !dataNode.IsCheckBox && dataNode.Root.tagName!="TBODY" && !dataNode.IsRadioBox){
									dataNode.SetInner("");
								}
							});
						}
					});
				}
			});
			return this;
		}
		
		handler.Foreach=function(name,func){
			var operatorNodes = CuminMultiNode.CreateInstance(this.InstanceString+" ["+this.Header+"] ["+this.Operator+"]");
			operatorNodes.Foreach(function(index,tNode){
				if(tNode.GetInner()==name){
					func(index,tNode);
				}
			});
			return this;
		}
		

		handler.SetUnique=function(node,data){
			var uniqueValue = node.GetAttribute(this.Unique);
			if(uniqueValue!=null && uniqueValue!="undefined" && uniqueValue!=""){
				node.Root.NotifyData = data
			}
		}
		
		handler.PropertyChanged=function(key,outData){
			var nodes = CuminMultiNode.CreateInstance(key);
			if(nodes.Roots.length>0)
			{
				var node = nodes.Select(0);
				key = node.GetAttribute(this.Content);
                var values = node.GetAttribute(this.Unique).split(this.SpliteChar);
				nodes.Foreach(function(nodeIndex,notifyNode){
					if(nodeIndex==0){return;}
					for(var i=0;i<outData.length;i++){
						(function(index){
							var nData = notifyNode.Root.NotifyData;
							if(handler.UniqueIsMatch(values,nData,outData[index])){
								notifyNode.SetInner(handler.Data(key,outData[index]));
							}
						})(i);
					}
				});
				
			}
			return this;
		}
		
		
		handler.UniqueIsMatch=function(values,data,outData){
			for(var j=0;j<values.length;j++){
				if(data[values[j]]!=outData[values[j]]){
					return false;
				}
			}
			return true;
		}
		
		return handler;
	}
}

function GetNodeJson(nodeName){
	var result = {};
	var hasKey = new Array();
	var outNodes = CuminMultiNode.CreateInstance(nodeName+" [out]");
	outNodes.Foreach(function(i,dataNode){
		var key = dataNode.GetAttribute("field");
		if(dataNode.IsSelect){
			result[key]=dataNode.GetInner();
		}else{
			result[key]=dataNode.GetAttribute("out");
		}
			hasKey[key]=true;
		});
		var nodes = CuminMultiNode.CreateInstance(nodeName+" [json]");
		nodes.Foreach(function(i,dataNode){
			var key = dataNode.GetAttribute("field");
			if(hasKey[key]==null){
				result[key]=dataNode.GetInner();
			}
	});
	return result;
}