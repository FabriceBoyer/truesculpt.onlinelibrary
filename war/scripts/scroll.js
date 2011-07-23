function scroll(){
	
	if(navigator.appName == "Microsoft Internet Explorer")
		scrollPosition = document.documentElement.scrollTop;
	else
		scrollPosition = window.pageYOffset;		
	
	if((contentHeight - pageHeight - scrollPosition) < 500){
				
		if(window.XMLHttpRequest)
			xmlhttp = new XMLHttpRequest();
		else
			if(window.ActiveXObject)
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			else
				alert ("Bummer! Your browser does not support XMLHTTP!");		  
		  
		var url="getImages.php?n="+n;
		
		xmlhttp.open("GET",url,true);
		xmlhttp.send();
		
		n += 9;
		xmlhttp.onreadystatechange=putImages;		
		contentHeight += 800;		
	}
}