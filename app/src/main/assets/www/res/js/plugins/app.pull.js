(function($) {

	$.fn.pull = function(settings, y) {
    var _y = 125;
    if (y > 0) {
      _y = y; 
    }
    var win = $(window);
    var isScrollTop = true;
    var runPullDown = false;
    var runPullUp = false;
    var touchstartY, touchendY;
    $(document).on("touchstart",function(event){
      touchstartY = event.originalEvent.changedTouches[0].clientY;
    });
    $(document).on("touchend",function(event){
      touchendY = event.originalEvent.changedTouches[0].clientY;
      console.info("touchend: " + (touchendY - touchstartY));
      if (isScrollTop == true && (touchendY - touchstartY) > _y) {
        if (runPullDown == false) {
          runPullDown = true;
          try {
            settings.pullDown();
          } catch(e) {
          }
          runPullDown = false;
        }
      }
    });
    win.scroll(function() {
      if ($(document).scrollTop() <= 0) {
        isScrollTop = true;
      } else {
        isScrollTop = false;
      }
      if ($(document).height() - win.height() == win.scrollTop()) {
          if (runPullUp == false) {
            runPullUp = true;
            try {
              settings.pullUp();
            } catch(e) {
            }
            runPullUp = false;
          }
      }
    });
	};
})(jQuery)