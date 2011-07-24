$('#container').infinitescroll({
 
  navSelector  : "div.navigation",            
                 // selector for the paged navigation (it will be hidden)
 
  nextSelector : "div.navigation a:next",    
                 // selector for the NEXT link (to page 2)
 
  itemSelector : "#container",          
                 // selector for all items you'll retrieve
 
  debug        : true,                        
                 // enable debug messaging ( to console.log )
 
  loadingImg   : "/images/loading.gif",          
                 // loading image.
 
  loadingText  : "Loading ...",      
                 // text accompanying loading image
 
  animate      : true,      
                 // boolean, if the page will do an animated scroll when new content loads
                 // default: false
 
  extraScrollPx: 50,      
                 // number of additonal pixels that the page will scroll 
                 // (in addition to the height of the loading div)
                 // animate must be true for this to matter
                 // default: 150
 
  donetext     : "No more media to be shown" ,
                 // text displayed when all items have been retrieved
                 // default: "<em>Congratulations, you've reached the end of the internet.</em>"
 
  bufferPx     : 40,
                 // increase this number if you want infscroll to fire quicker
                 // (a high number means a user will not see the loading message)
                 // new in 1.2
                 // default: 40
 
  errorCallback: function(){},
                 // called when a requested page 404's or when there is no more content
                 // new in 1.2                   
 
  localMode    : true
                 // enable an overflow:auto box to have the same functionality
                 // demo: http://paulirish.com/demo/infscr
                 // instead of watching the entire window scrolling the element this plugin
                 //   was called on will be watched
                 // new in 1.2
                 // default: false
 
 
    },function(arrayOfNewElems){
 
     // optional callback when new content is successfully loaded in.
 
     // keyword `this` will refer to the new DOM content that was just added.
     // as of 1.5, `this` matches the element you called the plugin on (e.g. #content)
     //                   all the new elements that were found are passed in as an array
 
});