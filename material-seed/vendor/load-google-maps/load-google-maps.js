var loadGoogleMaps=function(o){"use strict";var e,a=o.now();return function(n,l,g,i){if(e)return e;var r=o.Deferred(),t=function(){r.resolve(window.google&&window.google.maps?window.google.maps:!1)},w="loadGoogleMaps_"+a++,d=o.extend({sensor:i||"false"},l?{key:l}:{},g?{language:g}:{});return window.google&&window.google.maps?t():window.google&&window.google.load?window.google.load("maps",n||3,{other_params:o.param(d),callback:t}):(d=o.extend(d,{callback:w}),window[w]=function(){t(),setTimeout(function(){try{delete window[w]}catch(o){}},20)},o.ajax({dataType:"script",data:d,url:"//maps.googleapis.com/maps/api/js"})),e=r.promise()}}(jQuery);