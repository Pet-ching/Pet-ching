var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3
};

var map = new kakao.maps.Map(container, options);

//var circle = new kakao.maps.Circle({
//    center : new kakao.maps.LatLng(33.450701, 126.570667),
//    radius: 50,
//    strokeWeight: 5,
//    strokeColor: '#75B8FA',
//    strokeOpacity: 1,
//    strokeStyle: 'dashed',
//    fillColor: '#CFE7FF',
//    fillOpacity: 0.7
//});
//
//circle.setMap(map);

var geocoder = new kakao.maps.services.Geocoder();

var address = "서울특별시 강남구 언주로 508";
//var address = [[${petSitter.workingArea}]];

geocoder.addressSearch(address, function(result, status) {

     if (status === kakao.maps.services.Status.OK) {

        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        var circle = new kakao.maps.Circle({
            center : coords,
            radius: 50,
            strokeWeight: 3,
            strokeColor: '#75B8FA',
            strokeOpacity: 1,
            strokeStyle: 'solid',
            fillColor: '#CFE7FF',
            fillOpacity: 0.7
        });
        circle.setMap(map);

        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>'
        });


        map.setCenter(coords);
    } else {
        var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });
        var infowindow = new kakao.maps.InfoWindow({
                    content: '<div style="width:150px;text-align:center;padding:6px 0;">설정 주소가 없어요!</div>'
                });
        infowindow.open(map, marker);
    }
});