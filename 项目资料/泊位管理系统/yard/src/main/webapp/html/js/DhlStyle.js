document.write("<script language='JavaScript' src='js/CuminSingleNode.js'></script>");
var errorMessage=null;
var errorHandler=null;
function UseDhlStyle(){
	
	//document.body.style.backgroundImage = "url(img/bg.png)";
	
	var flag = document.querySelector("#dhlflag");
	flag.style.height="50px";
	flag.setAttribute("src","img/flag.gif");
	
	
	var menu = document.querySelector("#dhlMenu");
	menu.style.cssText="border-bottom: 1px solid #D40511;width:100%;";
	menu.style.marginBottom="15px";
	
	var menuChild = document.querySelector("#dhlMenu div");
	menuChild.style.cssText="background-color:rgb(255, 204, 0)";
	
	var menuButtons = document.querySelectorAll("#dhlMenuButton a");
	for(var i=0;i<menuButtons.length;i++){
		menuButtons[i].style.color="#413939";
		menuButtons[i].style.fontSize="15px";
	}
	
	errorMessage= CuminSingleNode.CreateInstance("#errorMessage");
	errorHandler= CuminSingleNode.CreateInstance("#errorNode");
	errorHandler.SetCssText("color:white;height:35px;position:fixed; bottom:0px; left:0px; width:100%; overflow:auto; background-image: linear-gradient(to bottom,rgb(255, 204, 0) 100%,rgba(241, 3, 3, 0.6) 100%);");
	errorHandler.SetIndex(1041);
	errorMessage.SetCssText("position:relative;top:50%;left:50%;transform:translate(-50%,-50%);heigth:100%;text-Align:center");
	//errorMessage.SetDisplay("inline-block");
	Error = erroShow;
}

function erroShow(status,requestData){
	
	
	if(requestData.hasOwnProperty("result"))
	{
		if(requestData.result==0){
			//失败
			errorMessage.SetInner(requestData.msg);
			errorHandler.SetBackImage("linear-gradient(to bottom,rgba(239, 72, 72, 0.88) 100%,rgba(113, 3, 3, 0.76) 100%)");
		}else{
			//成功
			errorMessage.SetInner("执行成功！");
			errorHandler.SetBackImage("linear-gradient(to bottom,rgb(25, 255, 0) 100%,rgba(45, 99, 5, 0.6) 100%)");
		}
	}else if(requestData["name"]!=null && requestData["message"]){
		errorMessage.SetInner("Name:"+requestData.name+"<br/> Message:"+requestData.message);
		errorHandler.SetBackImage("linear-gradient(to bottom,rgba(239, 72, 72, 0.88) 100%,rgba(113, 3, 3, 0.76) 100%)");
	}else{
		if(status==404){
			errorMessage.SetInner("错误代码:404.	请检测服务器是否开启,并核对URL.");
			errorHandler.SetBackImage("linear-gradient(to bottom,rgba(239, 72, 72, 0.88) 100%,rgba(113, 3, 3, 0.76) 100%)");
		}else if(status==400){
			errorMessage.SetInner("错误代码:400.	提交方法或参数有误.");
			errorHandler.SetBackImage("linear-gradient(to bottom,rgba(239, 72, 72, 0.88) 100%,rgba(113, 3, 3, 0.76) 100%)");
		}else if(status==500){
			errorMessage.SetInner("错误代码:500.	服务器错误.");
			errorHandler.SetBackImage("linear-gradient(to bottom,rgba(239, 72, 72, 0.88) 100%,rgba(113, 3, 3, 0.76) 100%)");
		}
	}
	if(errorHandler.Root.Timer!=null){
		clearTimeout(errorHandler.Root.Timer);
	}
	errorHandler.Root.Timer=window.setTimeout(function(){
						errorMessage.SetInner("");
						errorHandler.SetBackImage("linear-gradient(to bottom,rgb(255, 204, 0) 100%,rgba(241, 3, 3, 0.6) 100%)");
					},1500
	); 
}