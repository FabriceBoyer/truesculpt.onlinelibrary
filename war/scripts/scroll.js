var elemContentHeight = 800;
var updateOffset = 400;

var totalContentHeight = 0;
var pageHeight = document.documentElement.clientHeight;
var scrollPosition;
var xmlhttp =null;

var sortBy=gup("sortBy");
var orderBy=gup("orderBy");
var strPage=gup("page");
if (strPage=="")
{
	strPage="0";
}
var page=parseInt(strPage);

function putImages()
{
	if (xmlhttp.readyState==4) 
	{
		  if(xmlhttp.responseText)
		  {
			 var resp = xmlhttp.responseText;
			 document.getElementById("container").innerHTML += resp;	
			 
			xmlhttp=null;						
			totalContentHeight += elemContentHeight;
			page+=1;
		  }
	}
}		

function gup( name )
{
		 name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
		 var regexS = "[\\?&]"+name+"=([^&#]*)";
		 var regex = new RegExp( regexS );
		 var results = regex.exec( window.location.href );
		if( results == null )
		   return "";
		 else
		   return results[1];
}

function scroll()
{	
	if (xmlhttp==null)
	{
		if(navigator.appName == "Microsoft Internet Explorer")
			scrollPosition = document.documentElement.scrollTop;
		else
			scrollPosition = window.pageYOffset;		
		
		if((totalContentHeight - pageHeight - scrollPosition) < updateOffset)
		{				
			if(window.XMLHttpRequest)
				xmlhttp = new XMLHttpRequest();
			else
				if(window.ActiveXObject)
					xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
				else
					alert ("Bummer! Your browser does not support XMLHTTP!");		  
			  
			
			var url="?page="+page;		
		
			if (sortBy!="")
			{
				url+="&sortBy="+sortBy;
			}
	
			if (orderBy!="")
			{
				url+="&orderBy="+orderBy;
			}	
			url+="&rawpage=true";
			
			xmlhttp.open("GET",url,true);
			xmlhttp.send();
	
			xmlhttp.onreadystatechange=putImages;	
		}
	}
}