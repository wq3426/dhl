var CuminGet = {
	CreateInstance:function(){
		
		var handler = {};
		
		var url = location.search; //获取url中"?"符后的字串
		handler.Request = new Array();
		if (url.indexOf("?") != -1) {
			var str = url.substr(1);
			strs = str.split("&");
			for(var i = 0; i < strs.length; i ++) {
				handler.Request[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
			}
		}

		handler.Get=function(index){
			return this.Request[index];
		}
		
		return handler;
	}
}

function SetCookie(c_name,value,expiredays)
{
	var exdate=new Date()
	exdate.setDate(exdate.getDate()+expiredays)
	document.cookie=c_name+ "=" +escape(value)+
	((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}
function GetCookie(c_name)
{
	if (document.cookie.length>0)
	{
		c_start=document.cookie.indexOf(c_name + "=")
		if (c_start!=-1)
		{ 
			c_start=c_start + c_name.length+1 
			c_end=document.cookie.indexOf(";",c_start)
			if (c_end==-1) c_end=document.cookie.length
			return unescape(document.cookie.substring(c_start,c_end))
		} 
	}
	return ""
}