//<script src="https://www.google.com/jsapi?key=ABQIAAAAFd95zHgAzSKx0-3KNUj8ghTQ_LLL_oOGhNpJ5wVz0z8LLclTghRXzmcD0KOZSsoVGxcZ4gH33lsGhw" type="text/javascript"></script>
//<script type="text/javascript" src="/scripts/search.js" ></script>
//<div id="searchcontrol">Loading...</div>

google.load("search", "1");

function OnLoad() {	

  var searchControl = new google.search.SearchControl();
  searchControl.addSearcher(new google.search.WebSearch());
  searchControl.draw(document.getElementById("searchcontrol"));

}
google.setOnLoadCallback(OnLoad);
