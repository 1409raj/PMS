jQuery(window).on('load', function($){

    "use strict";

    var map;
    map = new GMaps({
        el: '#gmap',
        lat: 26.167252,
        lng: -97.791510,
        scrollwheel:false,
        zoom: 14,
        zoomControl : true,
        panControl : false,
        streetViewControl : false,
        mapTypeControl: true,
        overviewMapControl: false,
        clickable: false
    });

    var image = 'assets/img/contact/map-marker.png';
    map.addMarker({
        lat: 26.167252,
        lng: -97.791510,
        icon: image,
        animation: google.maps.Animation.DROP,
        verticalAlign: 'bottom',
        horizontalAlign: 'center',
        backgroundColor: '#3e8bff'
    });


    var styles = [ 

    {
        "featureType": "road",
        "stylers": [
        { "color": "#b4b4b4" }
        ]
    },{
        "featureType": "water",
        "stylers": [
        { "color": "#d8d8d8" }
        ]
    },{
        "featureType": "landscape",
        "stylers": [
        { "color": "#f1f1f1" }
        ]
    },{
        "elementType": "labels.text.fill",
        "stylers": [
        { "color": "#000000" }
        ]
    },{
        "featureType": "poi",
        "stylers": [
        { "color": "#d9d9d9" }
        ]
    },{
        "elementType": "labels.text",
        "stylers": [
        { "saturation": 1 },
        { "weight": 0.1 },
        { "color": "#000000" }
        ]
    }

    ];

    map.addStyle({
        styledMapName:"Styled Map",
        styles: styles,
        mapTypeId: "map_style"  
    });

    map.setStyle("map_style");
});