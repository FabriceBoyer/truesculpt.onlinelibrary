function redirect() 
{
	var strVersion=gup('version')

	var version = 8;
	if (strVersion!="")
	{
		version=parseInt(strVersion);
	}
	if (version>=8)
	{
		window.location = "/main";
	}
	else
	{
	    window.location = "/upgrade.html";
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