// 카카오 맵
let container = document.getElementById('map');
let options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3
};

let map = new kakao.maps.Map(container, options);

let geocoder = new kakao.maps.services.Geocoder();

function showMap(address,distance){
    geocoder.addressSearch(address, function(result, status) {

         if (status === kakao.maps.services.Status.OK) {

            let coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            let circle = new kakao.maps.Circle({
                center : coords,
                radius: distance,
                strokeWeight: 3,
                strokeColor: '#75B8FA',
                strokeOpacity: 1,
                strokeStyle: 'solid',
                fillColor: '#CFE7FF',
                fillOpacity: 0.7
            });
            circle.setMap(map);
            map.setCenter(coords);
        } else {
            let marker = new kakao.maps.Marker({
                    map: map,
                    position: coords
                });
            let infowindow = new kakao.maps.InfoWindow({
                        content: '<div style="width:150px;text-align:center;padding:6px 0;">설정 주소가 없어요!</div>'
                    });
            infowindow.open(map, marker);
        }
    });
}

// 주소 split

function splitAddress(address) {
    if (address.includes('로 ')) {
        var addressArray = address.split('로 ');
        var splitedAddress = addressArray[0] + "로"
    } else if(address.includes('동 ')) {
        var addressArray = address.split('동 ');
        var splitedAddress = addressArray[0] + "동"
    } else {
        var splitedAddress = address;
    }
    return splitedAddress;
}


